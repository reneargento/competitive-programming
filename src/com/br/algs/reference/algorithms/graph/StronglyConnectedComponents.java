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

        List<List<Integer>> stronglyConnectedComponents = getStronglyConnectedComponents(adjacent);
        for(List<Integer> component : stronglyConnectedComponents) {
            for(int vertex : component) {
                System.out.print(vertex + " ");
            }
            System.out.println();
        }
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
    private static void depthFirstSearchIterative(int sourceVertex, List<Integer>[] adj, Stack<Integer> finishTimes,
                                                  boolean[] visited, boolean getFinishTimes) {
        Stack<Integer> stack = new Stack<>();
        stack.push(sourceVertex);
        visited[sourceVertex] = true;

        // Used to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adjacentIterators = (Iterator<Integer>[]) new Iterator[adj.length];

        //If the vertices are 0-index based, start i with value 0
        for (int vertexId = 1; vertexId < adjacentIterators.length; vertexId++) {
            if(adj[vertexId] != null) {
                adjacentIterators[vertexId] = adj[vertexId].iterator();
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

    /**
     * To not only count, but also get the strongly connected components
     */
    private static List<List<Integer>> getStronglyConnectedComponents(List<Integer>[] adjacent) {
        boolean[] visited = new boolean[adjacent.length];
        Stack<Integer> finishTimes = new Stack<>();

        //Get finish times
        //If the vertices are 0-index based, start i with value 0
        for(int i = 1; i < adjacent.length; i++) {
            if(!visited[i]) {
                depthFirstSearch(i, adjacent, finishTimes, visited, true);
            }
        }

        List<Integer>[] inverseEdges = invertGraphEdges(adjacent);
        visited = new boolean[inverseEdges.length];

        List<List<Integer>> stronglyConnectedComponents = new ArrayList<>();

        while (!finishTimes.isEmpty()) {
            int currentVertex = finishTimes.pop();

            if(!visited[currentVertex]) {
                List<Integer> component = new ArrayList<>();
                depthFirstSearchToGetComponent(currentVertex, inverseEdges, visited, component);
               // List<Integer> component = depthFirstSearchToGetComponentIterative(currentVertex, inverseEdges, visited);

                stronglyConnectedComponents.add(component);
            }
        }

        return stronglyConnectedComponents;
    }

    // Fast, but recursive
    private static void depthFirstSearchToGetComponent(int sourceVertex, List<Integer>[] adj,
                                                           boolean[] visited, List<Integer> component) {
        visited[sourceVertex] = true;
        component.add(sourceVertex);

        for(int neighbor : adj[sourceVertex]) {
            if(!visited[neighbor]) {
                depthFirstSearchToGetComponent(neighbor, adj, visited, component);
            }
        }
    }

    // Trade-off between time and memory
    // Takes longer because it has to create the iterators, but avoid stack overflows
    private static List<Integer> depthFirstSearchToGetComponentIterative(int sourceVertex, List<Integer>[] adj, boolean[] visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(sourceVertex);
        visited[sourceVertex] = true;

        // Used to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adjacentIterators = (Iterator<Integer>[]) new Iterator[adj.length];
        for (int vertexId = 1; vertexId < adjacentIterators.length; vertexId++) {
            if(adj[vertexId] != null) {
                adjacentIterators[vertexId] = adj[vertexId].iterator();
            }
        }

        List<Integer> component = new ArrayList<>();
        component.add(sourceVertex);

        while (!stack.isEmpty()) {
            int currentVertex = stack.peek();
            boolean isConnectedToUnvisitedVertex = false;

            if(adjacentIterators[currentVertex].hasNext()) {
                int neighbor = adjacentIterators[currentVertex].next();

                if(!visited[neighbor]) {
                    stack.push(neighbor);
                    visited[neighbor] = true;

                    component.add(neighbor);
                    isConnectedToUnvisitedVertex = true;
                }
            }

            if(!isConnectedToUnvisitedVertex) {
                stack.pop();
            }
        }

        return component;
    }

}
