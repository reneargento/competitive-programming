package algorithms.graph.dag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 08/04/24.
 */
// Computes the number of paths from the source vertices to all other vertices in a DAG.
// Time complexity: O(E + V)
public class NumberOfPaths {

    public static long[] countNumberOfPaths(List<Integer>[] adjacencyList) {
        long[] numberOfPaths = new long[adjacencyList.length];

        int[] inDegrees = computeInDegrees(adjacencyList);
        int[] topologicalOrder = khanTopologicalSort(adjacencyList, inDegrees);
        if (topologicalOrder == null) {
            // Graph is not a DAG
            return null;
        }

        for (int vertexID : topologicalOrder) {
            if (inDegrees[vertexID] == 0) {
                numberOfPaths[vertexID] = 1;
            }

            for (int neighbor : adjacencyList[vertexID]) {
                numberOfPaths[neighbor] += numberOfPaths[vertexID];
            }
        }
        return numberOfPaths;
    }

    private static int[] computeInDegrees(List<Integer>[] adjacencyList) {
        int[] inDegrees = new int[adjacencyList.length];

        for (int i = 0; i < adjacencyList.length; i++) {
            for (int neighbor : adjacencyList[i]) {
                inDegrees[neighbor]++;
            }
        }
        return inDegrees;
    }

    private static int[] khanTopologicalSort(List<Integer>[] adjacencyList, int[] inDegrees) {
        int[] inDegreesCopy = new int[inDegrees.length];
        System.arraycopy(inDegrees, 0, inDegreesCopy, 0, inDegrees.length);

        // Create a queue and enqueue all vertices with inDegrees 0
        Queue<Integer> queue = new LinkedList<>();
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (inDegreesCopy[vertexID] == 0) {
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
                inDegreesCopy[neighbor]--;

                // If in-degree becomes zero, add it to queue
                if (inDegreesCopy[neighbor] == 0) {
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

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<Integer>[] adjacencyList = new List[9];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        adjacencyList[0].add(1);
        adjacencyList[1].add(2);
        adjacencyList[1].add(4);
        adjacencyList[2].add(3);
        adjacencyList[2].add(4);
        adjacencyList[3].add(4);
        adjacencyList[4].add(5);
        adjacencyList[4].add(6);
        adjacencyList[5].add(7);
        adjacencyList[6].add(8);
        adjacencyList[7].add(8);

        long[] numberOfPaths = countNumberOfPaths(adjacencyList);
        System.out.println("Number of paths to vertex 8: " + numberOfPaths[8]);
        System.out.println("Expected: 6");
    }
}
