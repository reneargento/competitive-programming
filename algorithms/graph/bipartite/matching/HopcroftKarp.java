package algorithms.graph.bipartite.matching;

import datastructures.graph.Graph;

import java.util.*;

/**
 * Created by Rene Argento on 07/02/18.
 */

// Finds a maximum cardinality matching (and minimum cardinality vertex cover) in a bipartite graph.
// O(E * sqrt(V))
// Based on https://algs4.cs.princeton.edu/65reductions/HopcroftKarp.java.html
@SuppressWarnings("unchecked")
public class HopcroftKarp {
    private static final int UNMATCHED = -1;

    private final int vertices;          // number of vertices in the graph
    private BipartiteX bipartition;      // the bipartition
    private int cardinality;             // cardinality of current matching
    private int[] mate;                  // mate[v] =  w if v-w is an edge in current matching
                                         //         = -1 if v is not in current matching
    private boolean[] inMinVertexCover;  // inMinVertexCover[v] = true iff v is in min vertex cover
    private boolean[] marked;            // marked[v] = true iff v is reachable via alternating path
    private int[] distTo;                // distTo[v] = number of edges on shortest path to v

    /**
     * Determines a maximum matching (and a minimum vertex cover) in a bipartite graph.
     *
     * @param graph the bipartite graph
     * @throws IllegalArgumentException if {@code graph} is not bipartite
     */
    public HopcroftKarp(Graph graph) {
        bipartition = new BipartiteX(graph);
        if (!bipartition.isBipartite()) {
            throw new IllegalArgumentException("graph is not bipartite");
        }

        // Initialize empty matching
        this.vertices = graph.vertices();
        mate = new int[vertices];
        for (int vertex = 0; vertex < vertices; vertex++) {
            mate[vertex] = UNMATCHED;
        }

        // The call to hasAugmentingPath() provides enough information to reconstruct level graph
        while (hasAugmentingPath(graph)) {

            // to be able to iterate over each adjacency list, keeping track of which
            // vertex in each adjacency list needs to be explored next
            Iterator<Integer>[] adjacent = (Iterator<Integer>[]) new Iterator[graph.vertices()];
            for (int vertex = 0; vertex < graph.vertices(); vertex++) {
                adjacent[vertex] = graph.adjacent(vertex).iterator();
            }

            // For each unmatched vertex source on one side of bipartition
            for (int source = 0; source < vertices; source++) {
                if (isMatched(source) || !bipartition.color(source)) {
                    continue;   // or use distTo[source] == 0
                }

                // Find augmenting path from source using nonrecursive DFS
                Deque<Integer> path = new ArrayDeque<Integer>();
                path.push(source);
                while (!path.isEmpty()) {
                    int vertex = path.peek();

                    // Retreat, no more edges in level graph leaving vertex
                    if (!adjacent[vertex].hasNext()) {
                        path.pop();
                    } else { // Advance
                        // Process edge vertex-w only if it is an edge in level graph
                        int w = adjacent[vertex].next();
                        if (!isLevelGraphEdge(vertex, w)) {
                            continue;
                        }

                        // Add w to augmenting path
                        path.push(w);

                        // Augmenting path found: update the matching
                        if (!isMatched(w)) {
                            // StdOut.println("augmenting path: " + toString(path));

                            while (!path.isEmpty()) {
                                int x = path.pop();
                                int y = path.pop();
                                mate[x] = y;
                                mate[y] = x;
                            }
                            cardinality++;
                        }
                    }
                }
            }
        }

        // Also find a min vertex cover
        inMinVertexCover = new boolean[vertices];
        for (int vertex = 0; vertex < vertices; vertex++) {
            if (bipartition.color(vertex) && !marked[vertex]) {
                inMinVertexCover[vertex] = true;
            }
            if (!bipartition.color(vertex) && marked[vertex]) {
                inMinVertexCover[vertex] = true;
            }
        }
    }

    // String representation of augmenting path (chop off last vertex)
    private String toString(Iterable<Integer> path) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int vertex : path) {
            stringBuilder.append(vertex).append("-");
        }

        String string = stringBuilder.toString();
        string = string.substring(0, string.lastIndexOf('-'));
        return string;
    }

    // Is the edge vertex1-vertex2 in the level graph?
    private boolean isLevelGraphEdge(int vertex1, int vertex2) {
        return (distTo[vertex2] == distTo[vertex1] + 1) && isResidualGraphEdge(vertex1, vertex2);
    }

    // Is the edge vertex1-vertex2 a forward edge not in the matching or a reverse edge in the matching?
    private boolean isResidualGraphEdge(int vertex1, int vertex2) {
        if ((mate[vertex1] != vertex2) && bipartition.color(vertex1)) {
            return true;
        }
        if ((mate[vertex1] == vertex2) && !bipartition.color(vertex1)) {
            return true;
        }
        return false;
    }

    /*
     * Is there an augmenting path?
     *   - if so, upon termination adj[] contains the level graph;
     *   - if not, upon termination marked[] specifies those vertices reachable via an alternating
     *     path from one side of the bipartition
     *
     * An alternating path is a path whose edges belong alternately to the matching and not
     * to the matching.
     *
     * An augmenting path is an alternating path that starts and ends at unmatched vertices.
     */
    private boolean hasAugmentingPath(Graph graph) {

        // Shortest path distances
        marked = new boolean[vertices];
        distTo = new int[vertices];
        for (int vertex = 0; vertex < vertices; vertex++) {
            distTo[vertex] = Integer.MAX_VALUE;
        }

        // Breadth-first search (starting from all unmatched vertices on one side of bipartition)
        Queue<Integer> queue = new LinkedList<>();
        for (int vertex = 0; vertex < vertices; vertex++) {
            if (bipartition.color(vertex) && !isMatched(vertex)) {
                queue.offer(vertex);
                marked[vertex] = true;
                distTo[vertex] = 0;
            }
        }

        // Run BFS until an augmenting path is found
        // (and keep going until all vertices at that distance are explored)
        boolean hasAugmentingPath = false;

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            for (int neighbor : graph.adjacent(vertex)) {

                // Forward edge not in matching or backwards edge in matching
                if (isResidualGraphEdge(vertex, neighbor)) {
                    if (!marked[neighbor]) {
                        distTo[neighbor] = distTo[vertex] + 1;
                        marked[neighbor] = true;
                        if (!isMatched(neighbor)) {
                            hasAugmentingPath = true;
                        }

                        // Stop enqueuing vertices once an alternating path has been discovered
                        // (no vertex on same side will be marked if its shortest path distance longer)
                        if (!hasAugmentingPath) {
                            queue.offer(neighbor);
                        }
                    }
                }
            }
        }

        return hasAugmentingPath;
    }

    /**
     * Returns the vertex to which the specified vertex is matched in
     * the maximum matching computed by the algorithm.
     *
     * @param v the vertex
     * @return the vertex to which vertex {@code v} is matched in the
     * maximum matching; {@code -1} if the vertex is not matched
     * @throws IllegalArgumentException unless {@code 0 <= v < vertices}
     */
    public int mate(int v) {
        return mate[v];
    }

    /**
     * Returns true if the specified vertex is matched in the maximum matching
     * computed by the algorithm.
     *
     * @param v the vertex
     * @return {@code true} if vertex {@code v} is matched in maximum matching;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < vertices}
     */
    public boolean isMatched(int v) {
        return mate[v] != UNMATCHED;
    }

    /**
     * Returns the number of edges in any maximum matching.
     *
     * @return the number of edges in any maximum matching
     */
    public int size() {
        return cardinality;
    }

    /**
     * Returns true if the graph contains a perfect matching.
     * That is, the number of edges in a maximum matching is equal to one half
     * of the number of vertices in the graph (so that every vertex is matched).
     *
     * @return {@code true} if the graph contains a perfect matching;
     * {@code false} otherwise
     */
    public boolean isPerfect() {
        return cardinality * 2 == vertices;
    }

    /**
     * Returns true if the specified vertex is in the minimum vertex cover
     * computed by the algorithm.
     *
     * @param v the vertex
     * @return {@code true} if vertex {@code v} is in the minimum vertex cover;
     * {@code false} otherwise
     * @throws IllegalArgumentException unless {@code 0 <= v < vertices}
     */
    public boolean inMinVertexCover(int v) {
        return inMinVertexCover[v];
    }
}
