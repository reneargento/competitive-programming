package algorithms.graph.topological.sort;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 07/12/18.
 */
// Based on https://www.geeksforgeeks.org/topological-sorting-indegree-based-solution/
public class TopologicalSortKhan {

    private static int[] khanTopologicalSort(List<Integer>[] adjacent, int vertices) {
        int[] inDegree = new int[vertices];

        // Traverse adjacency lists to fill in-degrees of vertices. This step takes O(V+E) time
        for (int i = 0; i < vertices; i++) {
            for (int neighbor : adjacent[i]) {
                inDegree[neighbor]++;
            }
        }

        // Create a queue and enqueue all vertices with inDegree 0
        Queue<Integer> queue = new LinkedList<>();
        for (int vertex = 0; vertex < vertices; vertex++) {
            if (inDegree[vertex] == 0)
                queue.add(vertex);
        }

        // Initialize count of visited vertices
        int count = 0;

        int[] topologicalOrder = new int[vertices];
        int topologicalOrderIndex = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            topologicalOrder[topologicalOrderIndex++] = currentVertex;

            // Iterate through all neighbour nodes of the dequeued vertex and decrease their in-degree by 1
            for (int neighbor : adjacent[currentVertex]) {
                // If in-degree becomes zero, add it to queue
                inDegree[neighbor]--;

                if (inDegree[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
            count++;
        }

        // Check if there was a cycle
        if (count != vertices) {
            System.out.println("There exists a cycle in the graph");
            return null;
        }
        return topologicalOrder;
    }
}
