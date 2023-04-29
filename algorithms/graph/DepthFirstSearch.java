package algorithms.graph;

import java.util.*;

/**
 * Created by Rene Argento on 07/09/17.
 */
@SuppressWarnings("unchecked")
public class DepthFirstSearch {

    public static void depthFirstSearch(List<Integer>[] adjacent) {
        boolean[] visited = new boolean[adjacent.length];
        for (int vertexId = 0; vertexId < adjacent.length; vertexId++) {
            if (!visited[vertexId]) {
                depthFirstSearch(adjacent, vertexId, visited);
            }
        }
    }

    // Fast, but recursive
    private static void depthFirstSearch(List<Integer>[] adjacent, int sourceVertex, boolean[] visited) {
        visited[sourceVertex] = true;
        System.out.println(sourceVertex);

        if (adjacent[sourceVertex] != null) {
            for (int neighbor : adjacent[sourceVertex]) {
                if (!visited[neighbor]) {
                    depthFirstSearch(adjacent, neighbor, visited);
                }
            }
        }
    }

    // Trade-off between time and memory
    // Takes longer because it has to create the iterators, but avoid stack overflows
    private static void depthFirstSearchIterative(List<Integer>[] adjacent, int sourceVertex, boolean[] visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(sourceVertex);
        visited[sourceVertex] = true;

        System.out.println(sourceVertex);

        // Used to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adjacentIterators = (Iterator<Integer>[]) new Iterator[adjacent.length];
        for (int vertexId = 0; vertexId < adjacentIterators.length; vertexId++) {
            if (adjacent[vertexId] != null) {
                adjacentIterators[vertexId] = adjacent[vertexId].iterator();
            }
        }

        while (!stack.isEmpty()) {
            int currentVertex = stack.peek();

            if (adjacentIterators[currentVertex].hasNext()) {
                int neighbor = adjacentIterators[currentVertex].next();
                if (!visited[neighbor]) {
                    stack.push(neighbor);
                    visited[neighbor] = true;
                }
            } else {
                stack.pop();
            }
        }
    }
}
