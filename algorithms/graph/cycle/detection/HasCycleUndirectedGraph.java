package algorithms.graph.cycle.detection;

import java.util.List;

/**
 * Created by Rene Argento on 15/09/17.
 */
public class HasCycleUndirectedGraph {
    private final boolean[] visited;
    private boolean hasCycle;

    public HasCycleUndirectedGraph(List<Integer>[] adjacent) {
        visited = new boolean[adjacent.length];

        for (int source = 0; source < adjacent.length; source++) {
            if (!visited[source]) {
                dfs(adjacent, source, source);
            }
        }
    }

    private void dfs(List<Integer>[] adjacent, int vertex, int origin) {
        visited[vertex] = true;

        if (adjacent[vertex] != null) {
            for (int neighbor : adjacent[vertex]) {
                if (!visited[neighbor]) {
                    dfs(adjacent, neighbor, vertex);
                } else if (neighbor != origin) {
                    hasCycle = true;
                }
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }
}
