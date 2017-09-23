package com.br.algs.reference.algorithms.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by rene on 01/09/17.
 */
@SuppressWarnings("unchecked")
public class StronglyConnectedComponents {

    private static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        /** Call this method to initialize reader for InputStream */
        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        /** Get next word */
        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int edges = FastReader.nextInt();

        //If the vertices are 0-index based, use
        //     new ArrayList[vertices];
        List<Integer>[] adjacent = (List<Integer>[]) new ArrayList[vertices + 1];

        for(int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for(int i = 0; i < edges; i++) {
            int vertex1 = FastReader.nextInt();
            int vertex2 = FastReader.nextInt();

            adjacent[vertex1].add(vertex2);
        }

        System.out.println(countStronglyConnectedComponents(adjacent) + " components\n");

        component = new int[vertices + 1];
        componentSizes = new int[vertices + 1];
        componentCount = 0;

        // Get strongly connected components and their sizes
        getStronglyConnectedComponents(adjacent, vertices);
    }

    /**
     * Count the number of strongly connected components
     */
    private static int countStronglyConnectedComponents(List<Integer>[] adjacent) {
        boolean[] visited = new boolean[adjacent.length];
        Stack<Integer> finishTimes = new Stack<>();

        //If the vertices are 0-index based, start i with value 0
        for(int i = 1; i < adjacent.length; i++) {
            if(!visited[i]) {
                depthFirstSearch(i, adjacent, finishTimes, visited, true);
            }
        }

        List<Integer>[] inverseEdges = invertGraphEdges(adjacent);
        visited = new boolean[inverseEdges.length];

        int stronglyConnectedComponents = 0;

        while (!finishTimes.isEmpty()) {
            int currentVertex = finishTimes.pop();

            if(!visited[currentVertex]) {
                stronglyConnectedComponents++;
                depthFirstSearch(currentVertex, inverseEdges, finishTimes, visited, false);
            }
        }

        return stronglyConnectedComponents;
    }

    // Fast, but recursive
    private static void depthFirstSearch(int sourceVertex, List<Integer>[] adj, Stack<Integer> finishTimes,
                                         boolean[] visited, boolean getFinishTimes) {
        visited[sourceVertex] = true;

        if(adj[sourceVertex] != null) {
            for(int neighbor : adj[sourceVertex]) {
                if(!visited[neighbor]) {
                    depthFirstSearch(neighbor, adj, finishTimes, visited, getFinishTimes);
                }
            }
        }

        if(getFinishTimes) {
            finishTimes.push(sourceVertex);
        }
    }

    // Trade-off between time and memory
    // Takes longer because it has to create the iterators, but avoid stack overflows
    private static void depthFirstSearchIterative(int sourceVertex, List<Integer>[] adjacent, Stack<Integer> finishTimes,
                                                  boolean[] visited, boolean getFinishTimes) {
        Stack<Integer> stack = new Stack<>();
        stack.push(sourceVertex);
        visited[sourceVertex] = true;

        // Used to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adjacentIterators = (Iterator<Integer>[]) new Iterator[adjacent.length];

        //If the vertices are 0-index based, start i with value 0
        for (int vertexId = 1; vertexId < adjacentIterators.length; vertexId++) {
            if(adjacent[vertexId] != null) {
                adjacentIterators[vertexId] = adjacent[vertexId].iterator();
            }
        }

        while (!stack.isEmpty()) {
            int currentVertex = stack.peek();

            if(adjacentIterators[currentVertex].hasNext()) {
                int neighbor = adjacentIterators[currentVertex].next();

                if(!visited[neighbor]) {
                    stack.push(neighbor);
                    visited[neighbor] = true;
                }
            } else {
                stack.pop();

                if(getFinishTimes) {
                    finishTimes.push(currentVertex);
                }
            }
        }
    }

    private static List<Integer>[] invertGraphEdges(List<Integer>[] adj) {
        List<Integer>[] inverseEdges = new ArrayList[adj.length];

        for(int i = 0; i < inverseEdges.length; i++) {
            inverseEdges[i] = new ArrayList<>();
        }

        for(int i = 1; i < adj.length; i++) {
            List<Integer> neighbors = adj[i];

            if(neighbors != null) {
                for(int neighbor : adj[i]) {
                    inverseEdges[neighbor].add(i);
                }
            }
        }

        return inverseEdges;
    }

    ///////////////////////// Get SCCs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    // Array that maps vertices to a strongly connected component
    private static int[] component;
    private static int componentCount;
    private static int[] componentSizes;

    /**
     * To not only count, but also get the strongly connected components
     */
    private static void getStronglyConnectedComponents(List<Integer>[] adjacent, int verticesCount) {
        //If the vertices are 0-index based, no need to add 1
        boolean[] visited = new boolean[verticesCount + 1];

        List<Integer> topologicalSort = topologicalSort(adjacent, verticesCount);

        List<Integer>[] inverseEdges = invertGraphEdges(adjacent);

        for(int currentVertex : topologicalSort) {

            if(!visited[currentVertex]) {
                depthFirstSearchToGetComponent(currentVertex, inverseEdges, visited);
                componentCount++;
            }
        }
    }

    private static List<Integer> topologicalSort(List<Integer>[] adjacent, int verticesCount) {
        //If the vertices are 0-index based, no need to add 1
        boolean[] visited = new boolean[verticesCount + 1];
        Stack<Integer> finishTimes = new Stack<>();

        //If the vertices are 0-index based, start i with value 0
        for(int i = 1; i < visited.length; i++) {
            if(!visited[i]) {
                depthFirstSearch(i, adjacent, finishTimes, visited, true);
            }
        }

        List<Integer> topologicalSort = new ArrayList<>();

        while (!finishTimes.isEmpty()) {
            topologicalSort.add(finishTimes.pop());
        }

        return topologicalSort;
    }

    private static void depthFirstSearchToGetComponent(int sourceVertex, List<Integer>[] adjacent, boolean[] visited) {
        visited[sourceVertex] = true;
        component[sourceVertex] = componentCount;
        componentSizes[componentCount]++;

        for(int neighbor : adjacent[sourceVertex]) {
            if(!visited[neighbor]) {
                depthFirstSearchToGetComponent(neighbor, adjacent, visited);
            }
        }
    }

    ///////////////////////// Reverse topologically sort SCCs \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    private static void reverseTopologicallySortSCCs(List<Integer> adjacent[]) {

        Set<Integer>[] adjacentComponents = (HashSet<Integer>[]) new HashSet[componentCount];
        for(int i = 0; i < adjacentComponents.length; i++) {
            adjacentComponents[i] = new HashSet<>();
        }

        //If the vertices are 0-index based, start vertexId with value 0
        for(int vertexId = 1; vertexId < adjacent.length; vertexId++) {
            int currentComponent = component[vertexId];

            for(int neighbor : adjacent[vertexId]) {
                if(currentComponent != component[neighbor]) {
                    adjacentComponents[currentComponent].add(component[neighbor]);
                }
            }
        }

        // (Reverse) topologically sort the strongly connected components
        // Subtract 1 because the componentCount is equal to the number of components + 1
        List<Integer> reverseTopologicalSort = getReverseTopologicalSort(adjacentComponents, componentCount - 1);

        for (int currentComponent : reverseTopologicalSort) {
            for (int neighborComponent : adjacentComponents[currentComponent]) {
                //Process
            }
        }
    }

    private static List<Integer> getReverseTopologicalSort(Set<Integer>[] adjacent, int verticesCount) {
        //If the vertices are 0-index based, no need to add 1
        boolean[] visited = new boolean[verticesCount + 1];
        List<Integer> finishTimes = new ArrayList<>();

        for(int i = 0; i < visited.length; i++) {
            if(!visited[i]) {
                depthFirstSearchToGetFinishTimesWithSets(i, adjacent, finishTimes, visited);
            }
        }

        return finishTimes;
    }

    private static void depthFirstSearchToGetFinishTimesWithSets(int sourceVertex, Set<Integer>[] adj,
                                                                 List<Integer> finishTimes, boolean[] visited) {
        visited[sourceVertex] = true;

        for(int neighbor : adj[sourceVertex]) {
            if(!visited[neighbor]) {
                depthFirstSearchToGetFinishTimesWithSets(neighbor, adj, finishTimes, visited);
            }
        }

        finishTimes.add(sourceVertex);
    }

}
