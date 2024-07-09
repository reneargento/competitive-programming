package algorithms.graph.bipartite.matching;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 15/04/24.
 */
// Finds a maximum cardinality matching in a bipartite graph.
// Time complexity: O(E * V)
// Simpler code than HopcroftKarp, but with a higher time complexity
public class MaximumCardinalityBipartiteMatching {

    // Receives as parameter the graph and the vertex IDs of the left partition
    public static int computeMaximumCardinality(List<Integer>[] adjacencyList, int[] leftPartitionVertexIDs) {
        int maximumMatches = 0;
        int[] match = new int[adjacencyList.length];
        Arrays.fill(match, -1);

        for (int vertexID : leftPartitionVertexIDs) {
            boolean[] visited = new boolean[adjacencyList.length];
            maximumMatches += tryToMatch(adjacencyList, match, visited, vertexID);
        }
        return maximumMatches;
    }

    private static int tryToMatch(List<Integer>[] adjacencyList, int[] match, boolean[] visited, int vertexID) {
        if (visited[vertexID]) {
            return 0;
        }
        visited[vertexID] = true;

        for (int neighbor : adjacencyList[vertexID]) {
            if (match[neighbor] == -1 || tryToMatch(adjacencyList, match, visited, match[neighbor]) == 1) {
                match[neighbor] = vertexID; // flip status
                return 1;
            }
        }
        return 0;
    }
}
