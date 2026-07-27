package algorithms.graph.topological.sort;

import java.util.*;

// The simplest topological sort algorithm
// Cannot check for the existence of cycles
// Time complexity: O(V + E)
public class TopologicalSortDFS {

    private static Integer[] topologicalSort(List<Integer>[] adjacencyList) {
        boolean[] visited = new boolean[adjacencyList.length];
        List<Integer> finishingOrder = new ArrayList<>();

        for (int vertexId = 0; vertexId < adjacencyList.length; vertexId++) {
            if (!visited[vertexId]) {
                topologicalSort(adjacencyList, visited, finishingOrder, vertexId);
            }
        }

        Collections.reverse(finishingOrder);
        return finishingOrder.toArray(new Integer[0]);
    }

    private static void topologicalSort(List<Integer>[] adjacencyList, boolean[] visited, List<Integer> finishingOrder,
                                        int vertexId) {
        visited[vertexId] = true;

        for (int neighbor : adjacencyList[vertexId]) {
            if (!visited[neighbor]) {
                topologicalSort(adjacencyList, visited, finishingOrder, neighbor);
            }
        }
        finishingOrder.add(vertexId);
    }

    @SuppressWarnings("unchecked")
    public static void main() {
        List<Integer>[] adjacencyList = new List[5];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        adjacencyList[0].add(1);
        adjacencyList[0].add(2);
        adjacencyList[1].add(3);
        adjacencyList[2].add(3);
        adjacencyList[2].add(4);
        adjacencyList[3].add(4);

        Integer[] topologicalSort = topologicalSort(adjacencyList);
        System.out.println("Topological sort: " + Arrays.toString(topologicalSort));
        System.out.println("Expected: [0, 2, 1, 3, 4]");
    }
}
