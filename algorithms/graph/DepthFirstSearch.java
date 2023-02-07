package algorithms.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    // Test
    public static void main(String[] args) throws IOException {
        FastReader.init();
        int vertices = FastReader.nextInt();
        int edges = FastReader.nextInt();

        List<Integer>[] adjacent = (List<Integer>[]) new ArrayList[vertices];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges; i++) {
            int vertex1 = FastReader.nextInt();
            int vertex2 = FastReader.nextInt();
            adjacent[vertex1].add(vertex2);
        }
        depthFirstSearch(adjacent);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
