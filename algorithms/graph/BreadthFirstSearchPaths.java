package algorithms.graph;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Rene Argento on 16/09/17.
 */
public class BreadthFirstSearchPaths {

    private boolean[] visited;
    private int[] edgeTo;
    private final int source;

    private int[] distTo;

    public BreadthFirstSearchPaths(List<Integer>[] adjacent, int source) {
        visited = new boolean[adjacent.length];
        edgeTo = new int[adjacent.length];
        this.source = source;

        distTo = new int[adjacent.length];

        distTo[source] = 0;
        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            if (vertex == source) {
                continue;
            }
            distTo[vertex] = Integer.MAX_VALUE;
        }
        bfs(adjacent, source);
    }

    private void bfs(List<Integer>[] adjacent, int sourceVertex) {
        Queue<Integer> queue = new LinkedList<>();
        visited[sourceVertex] = true;

        queue.offer(sourceVertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            if (adjacent[currentVertex] != null) {
                for (int neighbor : adjacent[currentVertex]) {
                    if (!visited[neighbor]) {
                        visited[neighbor] = true;

                        edgeTo[neighbor] = currentVertex;
                        distTo[neighbor] = distTo[currentVertex] + 1;

                        queue.offer(neighbor);
                    }
                }
            }
        }
    }

    // O(1)
    public int distTo(int vertex) {
        return distTo[vertex];
    }

    public boolean hasPathTo(int vertex) {
        return visited[vertex];
    }

    public Iterable<Integer> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }
        Stack<Integer> path = new Stack<>();

        for (int currentVertex = vertex; currentVertex != source; currentVertex = edgeTo[currentVertex]) {
            path.push(currentVertex);
        }

        path.push(source);
        return path;
    }
}
