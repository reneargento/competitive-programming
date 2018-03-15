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

    // Array that maps vertices to a strongly connected component
    private static int[] component;
    private static int componentCount;
    private static int[] componentSizes;

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int totalEdges = FastReader.nextInt();

        List<Integer>[] adjacent = (List<Integer>[]) new ArrayList[vertices + 1];

        for(int i = 0; i < totalEdges; i++) {
            int vertex1Id = FastReader.nextInt();
            int vertex2Id = FastReader.nextInt();

            if (adjacent[vertex1Id] == null) {
                adjacent[vertex1Id] = new ArrayList<>();
            }
            if (adjacent[vertex2Id] == null) {
                adjacent[vertex2Id] = new ArrayList<>();
            }

            adjacent[vertex1Id].add(vertex2Id);
        }

        component = new int[vertices + 1];
        componentSizes = new int[vertices + 1];
        componentCount = 0;

        // 1 - Get strongly connected components and their sizes
        getStronglyConnectedComponents(adjacent, vertices);

        // 2- Get adjacent components
        Set<Integer>[] adjacentComponents = (HashSet<Integer>[]) new HashSet[componentCount];
        for(int i = 0; i < adjacentComponents.length; i++) {
            adjacentComponents[i] = new HashSet<>();
        }

        for(int vertexId = 1; vertexId < adjacent.length; vertexId++) {
            int currentComponent = component[vertexId];

            for(int neighbor : adjacent[vertexId]) {
                if (currentComponent != component[neighbor]) {
                    adjacentComponents[currentComponent].add(component[neighbor]);
                }
            }
        }

        // 3 - (Reverse) topologically sort the strongly connected components
        // Subtract 1 because the componentCount is equal to the number of components + 1
        List<Integer> reverseTopologicalSort = getReverseTopologicalSort(adjacentComponents, componentCount - 1);

        // 4- Get longest path
        long maxCitiesVisited = 0;
        long numberOfSourceCitiesForMaxPath = 0;

        int[] pathSizes = new int[componentCount];

        for (int currentComponent : reverseTopologicalSort) {
            int maxNeighborComponentSize = 0;
            for(int neighborComponent : adjacentComponents[currentComponent]) {
                maxNeighborComponentSize = Math.max(maxNeighborComponentSize, pathSizes[neighborComponent]);
            }

            int citiesVisited = componentSizes[currentComponent] + maxNeighborComponentSize;
            pathSizes[currentComponent] = citiesVisited;

            maxCitiesVisited = Math.max(maxCitiesVisited, citiesVisited);
        }

        // 5- Get number of longest paths
        for(int i = 0; i < pathSizes.length; i++) {
            if (pathSizes[i] == maxCitiesVisited) {
                numberOfSourceCitiesForMaxPath += componentSizes[i];
            }
        }

        System.out.println(maxCitiesVisited + " " + numberOfSourceCitiesForMaxPath);
    }

    //Strongly connected components

    private static void getStronglyConnectedComponents(List<Integer>[] adjacent, int verticesCount) {
        //If the vertices are 0-index based, no need to add 1
        boolean[] visited = new boolean[verticesCount + 1];

        List<Integer> topologicalSort = topologicalSort(adjacent, verticesCount);

        List<Integer>[] inverseEdges = invertGraphEdges(adjacent);

        for(int currentVertex : topologicalSort) {

            if (!visited[currentVertex]) {
                depthFirstSearchToGetComponent(currentVertex, inverseEdges, visited);
                componentCount++;
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

            if (neighbors != null) {
                for(int neighbor : adj[i]) {
                    inverseEdges[neighbor].add(i);
                }
            }
        }

        return inverseEdges;
    }

    private static List<Integer> topologicalSort(List<Integer>[] adjacent, int verticesCount) {
        //If the vertices are 0-index based, no need to add 1
        boolean[] visited = new boolean[verticesCount + 1];
        Stack<Integer> finishTimes = new Stack<>();

        //If the vertices are 0-index based, start i with value 0
        for(int i = 1; i < visited.length; i++) {
            if (!visited[i]) {
                depthFirstSearchToGetFinishTimes(i, adjacent, finishTimes, visited);
            }
        }

        List<Integer> topologicalSort = new ArrayList<>();

        while (!finishTimes.isEmpty()) {
            topologicalSort.add(finishTimes.pop());
        }

        return topologicalSort;
    }

    private static void depthFirstSearchToGetFinishTimes(int sourceVertex, List<Integer>[] adj,
                                                         Stack<Integer> finishTimes, boolean[] visited) {
        visited[sourceVertex] = true;

        for(int neighbor : adj[sourceVertex]) {
            if (!visited[neighbor]) {
                depthFirstSearchToGetFinishTimes(neighbor, adj, finishTimes, visited);
            }
        }

        finishTimes.push(sourceVertex);
    }

    private static void depthFirstSearchToGetComponent(int sourceVertex, List<Integer>[] adj, boolean[] visited) {
        visited[sourceVertex] = true;
        component[sourceVertex] = componentCount;
        componentSizes[componentCount]++;

        for(int neighbor : adj[sourceVertex]) {
            if (!visited[neighbor]) {
                depthFirstSearchToGetComponent(neighbor, adj, visited);
            }
        }
    }

    private static List<Integer> getReverseTopologicalSort(Set<Integer>[] adjacent, int verticesCount) {
        //If the vertices are 0-index based, no need to add 1
        boolean[] visited = new boolean[verticesCount + 1];
        List<Integer> finishTimes = new ArrayList<>();

        for(int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                depthFirstSearchToGetFinishTimesWithSets(i, adjacent, finishTimes, visited);
            }
        }

        return finishTimes;
    }

    private static void depthFirstSearchToGetFinishTimesWithSets(int sourceVertex, Set<Integer>[] adj,
                                                                 List<Integer> finishTimes, boolean[] visited) {
        visited[sourceVertex] = true;

        for(int neighbor : adj[sourceVertex]) {
            if (!visited[neighbor]) {
                depthFirstSearchToGetFinishTimesWithSets(neighbor, adj, finishTimes, visited);
            }
        }

        finishTimes.add(sourceVertex);
    }

}