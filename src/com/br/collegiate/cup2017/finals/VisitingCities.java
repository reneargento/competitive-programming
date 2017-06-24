package com.br.collegiate.cup2017.finals;

import java.io.*;
import java.util.*;

/**
 * Created by rene on 17/06/17.
 */
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

    private static class Vertex {
        int id;
        Set<Integer> citiesVisitedStartingHere;
        boolean usedAsSource;

        Vertex(int id) {
            this.id = id;
            citiesVisitedStartingHere = new HashSet<>();
        }
    }

    private static class Edge {
        Vertex vertex1;
        Vertex vertex2;

        Edge(Vertex vertex1, Vertex vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }
    }

    private static Vertex getVertex(int id) {
        if(allVertices[id] == null) {
            allVertices[id] = new Vertex(id);
        }

        return allVertices[id];
    }

    private static Vertex[] allVertices;

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int totalEdges = FastReader.nextInt();

        allVertices = new Vertex[vertices + 1];

        List<Edge>[] adjacent = (List<Edge>[]) new ArrayList[vertices + 1];

        for(int i=0; i < totalEdges; i++) {
            int vertex1Id = FastReader.nextInt();
            int vertex2Id = FastReader.nextInt();

            Vertex vertex1 = getVertex(vertex1Id);
            Vertex vertex2 = getVertex(vertex2Id);

            Edge edge = new Edge(vertex1, vertex2);
            if(adjacent[vertex1.id] == null) {
                adjacent[vertex1.id] = new ArrayList<>();
            }
            if(adjacent[vertex2.id] == null) {
                adjacent[vertex2.id] = new ArrayList<>();
            }

            adjacent[vertex1.id].add(edge);
        }

        for(int i=0; i < vertices; i++) {

            if(allVertices[i] != null) {
                Vertex sourceVertex = getVertex(i);

                if(!sourceVertex.usedAsSource) {
                    boolean[] visited = new boolean[adjacent.length];

                    bfs(adjacent, visited, sourceVertex);
                }
            }
        }

        int maxCitiesVisited = 0;
        Map<Integer, Integer> sourceCitiesByMaxCities = new HashMap<>();

        for(int i=0; i < allVertices.length; i++) {
            if(allVertices[i] != null) {
                int citiesVisited = allVertices[i].citiesVisitedStartingHere.size();

                int sourceCities = 0;
                if(sourceCitiesByMaxCities.containsKey(citiesVisited)) {
                    sourceCities = sourceCitiesByMaxCities.get(citiesVisited);
                }
                sourceCities++;
                sourceCitiesByMaxCities.put(citiesVisited, sourceCities);

                if(citiesVisited > maxCitiesVisited) {
                    maxCitiesVisited = citiesVisited;
                }
            }
        }

        System.out.println(maxCitiesVisited + " " + sourceCitiesByMaxCities.get(maxCitiesVisited));
    }

    private static void bfs(List<Edge>[] adjacent, boolean[] visited, Vertex sourceVertex) {

        Queue<Vertex> queue = new LinkedList<>();
        queue.offer(sourceVertex);

        sourceVertex.usedAsSource = true;

        while (!queue.isEmpty()) {
            Vertex currentCity = queue.poll();

            for(Edge edge : adjacent[currentCity.id]) {
                if(!visited[edge.vertex2.id]) {

                    edge.vertex1.citiesVisitedStartingHere.add(edge.vertex2.id);

                    if(!edge.vertex2.usedAsSource) {
                        bfs(adjacent, visited, edge.vertex2);
                    }

                    visited[edge.vertex2.id] = true;
                    for(int cityVisited : edge.vertex2.citiesVisitedStartingHere) {
                        if(cityVisited != edge.vertex1.id) {
                            edge.vertex1.citiesVisitedStartingHere.add(cityVisited);
                        }
                    }

                    queue.offer(edge.vertex2);
                }
            }
        }
    }

}
