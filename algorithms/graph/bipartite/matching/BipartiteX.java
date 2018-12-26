package algorithms.graph.bipartite.matching;

import datastructures.graph.Graph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 06/02/18.
 */
public class BipartiteX {

    private static final boolean WHITE = false;
   // private static final boolean BLACK = true;

    private boolean isBipartite;   // is the graph bipartite?
    private boolean[] color;       // color[v] gives vertices on one side of bipartition
    private boolean[] marked;      // marked[v] = true if v has been visited in DFS
    private int[] edgeTo;          // edgeTo[v] = last edge on path to v
    private Queue<Integer> cycle;  // odd-length cycle

    /**
     * Determines whether an undirected graph is bipartite and finds either a
     * bipartition or an odd-length cycle.
     *
     * @param  graph the graph
     */
    public BipartiteX(Graph graph) {
        isBipartite = true;
        color  = new boolean[graph.vertices()];
        marked = new boolean[graph.vertices()];
        edgeTo = new int[graph.vertices()];

        for (int vertex = 0; vertex < graph.vertices() && isBipartite; vertex++) {
            if (!marked[vertex]) {
                bfs(graph, vertex);
            }
        }
    }

    private void bfs(Graph graph, int source) {
        Queue<Integer> queue = new LinkedList<>();
        color[source] = WHITE;
        marked[source] = true;
        queue.offer(source);

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int neighbor : graph.adjacent(vertex)) {
                if (!marked[neighbor]) {
                    marked[neighbor] = true;
                    edgeTo[neighbor] = vertex;
                    color[neighbor] = !color[vertex];
                    queue.offer(neighbor);
                } else if (color[neighbor] == color[vertex]) {
                    isBipartite = false;

                    // to form odd cycle, consider s-vertex path and s-neighbor path
                    // and let x be closest node to vertex and neighbor common to two paths
                    // then (neighbor-x path) + (x-vertex path) + (edge vertex-neighbor) is an odd-length cycle
                    // Note: distTo[vertex] == distTo[neighbor];
                    cycle = new LinkedList<>();
                    Deque<Integer> stack = new ArrayDeque<>();
                    int x = vertex;
                    int y = neighbor;

                    while (x != y) {
                        stack.push(x);
                        cycle.offer(y);
                        x = edgeTo[x];
                        y = edgeTo[y];
                    }

                    stack.push(x);
                    while (!stack.isEmpty()) {
                        cycle.offer(stack.pop());
                    }

                    cycle.offer(neighbor);
                    return;
                }
            }
        }
    }

    public boolean isBipartite() {
        return isBipartite;
    }

    /**
     * Returns the side of the bipartite that vertex {@code vertex} is on.
     *
     * @param  vertex the vertex
     * @return the side of the bipartition that vertex {@code vertex} is on; two vertices
     *         are in the same side of the bipartition if and only if they have the
     *         same color
     * @throws IllegalArgumentException unless {@code 0 <= vertex < vertices}
     * @throws UnsupportedOperationException if this method is called when the graph
     *         is not bipartite
     */
    public boolean color(int vertex) {
        validateVertex(vertex);
        if (!isBipartite) {
            throw new UnsupportedOperationException("Graph is not bipartite");
        }
        return color[vertex];
    }


    /**
     * Returns an odd-length cycle if the graph is not bipartite, and
     * {@code null} otherwise.
     *
     * @return an odd-length cycle if the graph is not bipartite
     *         (and hence has an odd-length cycle), and {@code null}
     *         otherwise
     */
    public Iterable<Integer> oddCycle() {
        return cycle;
    }

    // throw an IllegalArgumentException unless {@code 0 <= vertex < vertices}
    private void validateVertex(int vertex) {
        int vertices = marked.length;
        if (vertex < 0 || vertex >= vertices) {
            throw new IllegalArgumentException("vertex " + vertex + " is not between 0 and " + (vertices-1));
        }
    }

}
