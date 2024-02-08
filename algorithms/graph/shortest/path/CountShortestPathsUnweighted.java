package algorithms.graph.shortest.path;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 21/09/17.
 */
public class CountShortestPathsUnweighted {

    private static long countNumberOfShortestPaths(List<Integer>[] adjacent, int vertexCount, int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        int[] shortestPathValue = new int[vertexCount];
        int[] shortestPaths = new int[vertexCount];

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        shortestPaths[source] = 1;

        visited[source] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for (int neighbor : adjacent[currentVertex]) {

                if (!visited[neighbor]) {
                    shortestPathValue[neighbor] = shortestPathValue[currentVertex] + 1;
                    shortestPaths[neighbor] = shortestPaths[currentVertex];

                    queue.offer(neighbor);
                } else {
                    if (shortestPathValue[currentVertex] + 1 == shortestPathValue[neighbor]) {
                        shortestPaths[neighbor] += shortestPaths[currentVertex];
                    }
                }
                visited[neighbor] = true;
            }
        }
        return shortestPaths[destination];
    }
}
