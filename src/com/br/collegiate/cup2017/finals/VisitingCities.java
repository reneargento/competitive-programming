package com.br.collegiate.cup2017.finals;

import java.io.*;
import java.util.*;

/**
 * Created by rene on 17/06/17.
 */
@SuppressWarnings("unchecked")
public class VisitingCities {

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
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int totalEdges = FastReader.nextInt();

        List<Integer>[] adjacent = (List<Integer>[]) new ArrayList[vertices + 1];

        for(int i = 0; i < totalEdges; i++) {
            int vertex1Id = FastReader.nextInt();
            int vertex2Id = FastReader.nextInt();

            if(adjacent[vertex1Id] == null) {
                adjacent[vertex1Id] = new ArrayList<>();
            }
            if(adjacent[vertex2Id] == null) {
                adjacent[vertex2Id] = new ArrayList<>();
            }

            adjacent[vertex1Id].add(vertex2Id);
        }

        List<Integer> topologicalSort = topologicalSort(adjacent);
        List<List<Integer>> stronglyConnectedComponents = getStronglyConnectedComponents(adjacent, topologicalSort);

        Map<Integer, List<Integer>> vertexPerComponent = new HashMap<>();
        for(List<Integer> component : stronglyConnectedComponents) {
            for(int vertex : component) {
                vertexPerComponent.put(vertex, component);
            }
        }

        int maxCitiesVisited = 0;
        int maxComponentLength = 0;

        boolean[] visited = new boolean[adjacent.length];

        for(List<Integer> component : stronglyConnectedComponents) {
            if(!visited[component.get(0)]) {
                for(int vertexInComponent : component) {
                    visited[vertexInComponent] = true;
                }
            }

            for(int vertex : component) {
                int maxVerticesInPathCount = getLongestDistance(vertex, adjacent, visited, vertexPerComponent);

                if(maxVerticesInPathCount > maxCitiesVisited) {
                    maxCitiesVisited = maxVerticesInPathCount;
                    maxComponentLength = component.size();
                }
            }
        }

        System.out.println(maxCitiesVisited + " " + maxComponentLength);
    }

    private static List<Integer> topologicalSort(List<Integer>[] adjacent) {
        boolean[] visited = new boolean[adjacent.length];
        Stack<Integer> finishTimes = new Stack<>();

        //If the vertices are 0-index based, start i with value 0
        for(int i = 1; i < adjacent.length; i++) {
            if(!visited[i]) {
                depthFirstSearchToGetFinishTimes(i, adjacent, finishTimes, visited);
            }
        }

        List<Integer> topologicalSort = new ArrayList<>();

        while (!finishTimes.isEmpty()) {
            topologicalSort.add(finishTimes.pop());
        }

        return topologicalSort;
    }

    private static List<List<Integer>> getStronglyConnectedComponents(List<Integer>[] adjacent, List<Integer> topologicalSort) {
        List<Integer>[] inverseEdges = invertGraphEdges(adjacent);
        boolean[] visited = new boolean[inverseEdges.length];

        List<List<Integer>> stronglyConnectedComponents = new ArrayList<>();

        for(int currentVertex : topologicalSort) {

            if(!visited[currentVertex]) {
                List<Integer> component = depthFirstSearchToGetComponent(currentVertex, inverseEdges, visited);
                stronglyConnectedComponents.add(component);
            }
        }

        return stronglyConnectedComponents;
    }

    private static void depthFirstSearchToGetFinishTimes(int sourceVertex, List<Integer>[] adj, Stack<Integer> finishTimes,
                                                         boolean[] visited) {
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

        while (!stack.isEmpty()) {
            int currentVertex = stack.peek();
            boolean isConnectedToUnvisitedVertex = false;

            if(adjacentIterators[currentVertex].hasNext()) {
                int neighbor = adjacentIterators[currentVertex].next();

                if(!visited[neighbor]) {
                    stack.push(neighbor);
                    visited[neighbor] = true;

                    isConnectedToUnvisitedVertex = true;
                }
            }

            if(!isConnectedToUnvisitedVertex) {
                stack.pop();
                finishTimes.push(currentVertex);
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

    private static List<Integer> depthFirstSearchToGetComponent(int sourceVertex, List<Integer>[] adj, boolean[] visited) {
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

    private static int getLongestDistance(int sourceVertexId, List<Integer>[] adjacent, boolean[] visited,
                                          Map<Integer, List<Integer>> vertexPerComponent) {
        int distance = vertexPerComponent.get(sourceVertexId).size();
        int maxDistance = distance;

        Stack<Integer> stack = new Stack<>();
        stack.push(sourceVertexId);

        visited[sourceVertexId] = true;

        // Used to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adjacentIterators = (Iterator<Integer>[]) new Iterator[adjacent.length];
        for (int vertexId = 1; vertexId < adjacentIterators.length; vertexId++) {
            if(adjacent[vertexId] != null) {
                adjacentIterators[vertexId] = adjacent[vertexId].iterator();
            }
        }

        while (!stack.isEmpty()) {
            int currentVertex = stack.peek();
            boolean isConnectedToUnvisitedVertex = false;

            if(adjacentIterators[currentVertex].hasNext()) {
                int neighbor = adjacentIterators[currentVertex].next();

                if(!visited[neighbor]) {
                    stack.push(neighbor);

                    for(int vertexInComponent : vertexPerComponent.get(neighbor)) {
                        visited[vertexInComponent] = true;
                    }

                    distance += vertexPerComponent.get(neighbor).size();

                    if(distance > maxDistance) {
                        maxDistance = distance;
                    }

                    isConnectedToUnvisitedVertex = true;
                }
            }

            if(!isConnectedToUnvisitedVertex) {
                stack.pop();
                distance -=  vertexPerComponent.get(currentVertex).size();
            }
        }

        return maxDistance;
    }

}
