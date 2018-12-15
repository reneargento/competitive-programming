package algorithms.graph.shortest.path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 12/09/17.
 */
public class AcyclicLongestPathUnweighted {

    private static long longestPath(List<Integer> adjacent[], int verticesCount) {

        long longestPath = 0;

        int[] pathSizes = new int[verticesCount];
        List<Integer> reverseTopologicalSort = getReverseTopologicalSort(adjacent, verticesCount);

        for (int currentVertex : reverseTopologicalSort) {
            int maxNeighborSize = 0;

            for(int neighbor : adjacent[currentVertex]) {
                maxNeighborSize = Math.max(maxNeighborSize, pathSizes[neighbor]);
            }

            int pathLength = maxNeighborSize + 1;
            pathSizes[currentVertex] = pathLength;

            longestPath = Math.max(longestPath, pathLength);
        }

        return longestPath;
    }

    private static List<Integer> getReverseTopologicalSort(List<Integer>[] adjacent, int verticesCount) {
        //If the vertices are 0-index based, no need to add 1
        boolean[] visited = new boolean[verticesCount + 1];
        List<Integer> finishTimes = new ArrayList<>();

        for(int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                depthFirstSearchToGetFinishTimes(i, adjacent, finishTimes, visited);
            }
        }

        return finishTimes;
    }

    private static void depthFirstSearchToGetFinishTimes(int sourceVertex, List<Integer>[] adj,
                                                         List<Integer> finishTimes, boolean[] visited) {
        visited[sourceVertex] = true;

        for(int neighbor : adj[sourceVertex]) {
            if (!visited[neighbor]) {
                depthFirstSearchToGetFinishTimes(neighbor, adj, finishTimes, visited);
            }
        }

        finishTimes.add(sourceVertex);
    }

}
