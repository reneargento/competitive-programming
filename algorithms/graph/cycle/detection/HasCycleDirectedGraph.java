package algorithms.graph.cycle.detection;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Created by Rene Argento on 24/10/17.
 */
public class HasCycleDirectedGraph {
    private final boolean[] visited;
    private final int[] edgeTo;
    private Deque<Integer> cycle;
    private final boolean[] onStack;

    public HasCycleDirectedGraph(List<Integer>[] adjacent) {
        onStack = new boolean[adjacent.length];
        edgeTo = new int[adjacent.length];
        visited = new boolean[adjacent.length];

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            if (!visited[vertex]) {
                dfs(adjacent, vertex);
            }
        }
    }

    private void dfs(List<Integer>[] adjacent, int vertex) {
        onStack[vertex] = true;
        visited[vertex] = true;

        for (int neighbor : adjacent[vertex]) {
            if (hasCycle()) {
                return;
            } else if (!visited[neighbor]) {
                edgeTo[neighbor] = vertex;
                dfs(adjacent, neighbor);
            } else if (onStack[neighbor]) {
                cycle = new ArrayDeque<>();

                for (int currentVertex = vertex; currentVertex != neighbor; currentVertex = edgeTo[currentVertex]) {
                    cycle.push(currentVertex);
                }
                cycle.push(neighbor);
                cycle.push(vertex);
            }
        }
        onStack[vertex] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }
}
