package algorithms.graph.topological.sort;

import java.util.*;

/**
 * Created by Rene Argento on 03/02/23.
 */
// Based on https://www.geeksforgeeks.org/all-topological-sorts-of-a-directed-acyclic-graph/
@SuppressWarnings("unchecked")
public class AllTopologicalSorts {

    // Graph
    // 0           4 - 6
    //   \ 2 - 3 /
    //   /       \
    // 1           5
    public static void main(String[] args) {
        List<Integer>[] adjacencyList = new List[7];
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            adjacencyList[vertexID] = new ArrayList<>();
        }
        adjacencyList[0].add(2);
        adjacencyList[1].add(2);
        adjacencyList[2].add(3);
        adjacencyList[3].add(4);
        adjacencyList[3].add(5);
        adjacencyList[4].add(6);

        List<List<Integer>> topologicalSorts = computeAllTopologicalSorts(adjacencyList);
        System.out.println("Number of topological sorts: " + topologicalSorts.size() + " Expected: 6");
        for (List<Integer> topologicalSort : topologicalSorts) {
            Collections.reverse(topologicalSort);
            System.out.print(topologicalSort.get(0));
            for (int i = 1; i < topologicalSort.size(); i++) {
                System.out.print(" - " + topologicalSort.get(i));
            }
            System.out.println();
        }
    }

    private static List<List<Integer>> computeAllTopologicalSorts(List<Integer>[] adjacencyList) {
        List<List<Integer>> topologicalSorts = new ArrayList<>();
        boolean[] visited = new boolean[adjacencyList.length];

        int[] inDegrees = computeInDegrees(adjacencyList);
        Deque<Integer> stack = new ArrayDeque<>();

        computeAllTopologicalSorts(adjacencyList, inDegrees, visited, topologicalSorts, stack);
        return topologicalSorts;
    }

    private static int[] computeInDegrees(List<Integer>[] adjacencyList) {
        int[] inDegrees = new int[adjacencyList.length];
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            for (int neighborVertexID : adjacencyList[vertexID]) {
                inDegrees[neighborVertexID]++;
            }
        }
        return inDegrees;
    }

    private static void computeAllTopologicalSorts(List<Integer>[] adjacencyList, int[] inDegrees, boolean[] visited,
                                                   List<List<Integer>> topologicalSorts, Deque<Integer> stack) {
        for (int vertexID = 0; vertexID < inDegrees.length; vertexID++) {
            if (inDegrees[vertexID] == 0 && !visited[vertexID]) {
                visited[vertexID] = true;
                stack.push(vertexID);
                for (int neighborVertexID : adjacencyList[vertexID]) {
                    inDegrees[neighborVertexID]--;
                }

                computeAllTopologicalSorts(adjacencyList, inDegrees, visited, topologicalSorts, stack);

                visited[vertexID] = false;
                stack.pop();
                for (int neighborVertexID : adjacencyList[vertexID]) {
                    inDegrees[neighborVertexID]++;
                }
            }
        }

        if (stack.size() == adjacencyList.length) {
            List<Integer> topologicalSort = new ArrayList<>(stack);
            topologicalSorts.add(topologicalSort);
        }
    }
}
