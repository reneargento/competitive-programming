package algorithms.graph.topological.sort;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 07/12/18.
 */
// Based on https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
public class TopologicalSortKhan {

    private static int[] khanTopologicalSort(List<Integer>[] adjacencyList) {
        int[] inDegrees = new int[adjacencyList.length];

        // Traverse adjacency lists to fill in-degrees of vertices. This step takes O(V+E) time
        for (int i = 0; i < adjacencyList.length; i++) {
            for (int neighbor : adjacencyList[i]) {
                inDegrees[neighbor]++;
            }
        }

        // Create a queue and enqueue all vertices with inDegrees 0
        Queue<Integer> queue = new LinkedList<>();
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (inDegrees[vertexID] == 0) {
                queue.add(vertexID);
            }
        }

        // Initialize count of visited vertices
        int count = 0;

        int[] topologicalOrder = new int[adjacencyList.length];
        int topologicalOrderIndex = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            topologicalOrder[topologicalOrderIndex++] = currentVertex;

            // Iterate through all neighbour nodes of the dequeued vertex and decrease their in-degree by 1
            for (int neighbor : adjacencyList[currentVertex]) {
                // If in-degree becomes zero, add it to queue
                inDegrees[neighbor]--;

                if (inDegrees[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
            count++;
        }

        // Check if there was a cycle
        if (count != adjacencyList.length) {
            System.out.println("There exists a cycle in the graph");
            return null;
        }
        return topologicalOrder;
    }
}
