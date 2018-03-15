package com.br.training.usp.winterschool2017.campday1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by rene.argento on 27/07/17.
 */
@SuppressWarnings("unchecked")
public class EvenRoads {

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

    private static final int maxVertexValue = 10000;

    private static class Edge {
        int origin;
        int destination;
        int length;

        Edge(int origin, int destination, int length) {
            this.origin = origin;
            this.destination = destination;
            this.length = length;
        }
    }

    private static class Vertex {
        int id;
        long distanceFromSource;
        boolean visited;

        Vertex(int id, long distanceFromSource) {
            this.id = id;
            this.distanceFromSource = distanceFromSource;
            visited = false;
        }
    }

    private static Map<Integer, Vertex> vertices;
    private static List<Edge>[] edges;
    private static int destinationVertexId;

    public static void main(String[] args) throws IOException{
        initGraph();
        System.out.println(dijkstra(destinationVertexId));
    }

    private static void initGraph() throws IOException {
        FastReader.init(System.in);

        int cities = FastReader.nextInt();
        int roads = FastReader.nextInt();

        vertices = new HashMap<>();
        edges = new List[cities + maxVertexValue + 1];

        //The destination is the last city
        destinationVertexId = cities;

        for(int i = 0; i < roads; i++) {
            int originCity = FastReader.nextInt();
            int destinationCity = FastReader.nextInt();
            int length = FastReader.nextInt();

            int originCityNewGraph = originCity + maxVertexValue;
            int destinationCityNewGraph = destinationCity + maxVertexValue;

            if (!vertices.containsKey(originCity)) {
                vertices.put(originCity, new Vertex(originCity, Integer.MAX_VALUE));
            }
            if (!vertices.containsKey(destinationCity)) {
                vertices.put(destinationCity, new Vertex(destinationCity, Integer.MAX_VALUE));
            }
            if (!vertices.containsKey(originCityNewGraph)) {
                vertices.put(originCityNewGraph, new Vertex(originCityNewGraph, Integer.MAX_VALUE));
            }
            if (!vertices.containsKey(destinationCityNewGraph)) {
                vertices.put(destinationCityNewGraph, new Vertex(destinationCityNewGraph, Integer.MAX_VALUE));
            }

            Edge edge1 = new Edge(originCity, destinationCityNewGraph, length);
            Edge edge2 = new Edge(originCityNewGraph, destinationCity, length);

            //Add edge1
            if (edges[originCity] == null) {
                edges[originCity] = new ArrayList<>();
            }
            edges[originCity].add(edge1);

            if (edges[destinationCityNewGraph] == null) {
                edges[destinationCityNewGraph] = new ArrayList<>();
            }
            edges[destinationCityNewGraph].add(edge1);

            //Add edge2
            if (edges[originCityNewGraph] == null) {
                edges[originCityNewGraph] = new ArrayList<>();
            }
            edges[originCityNewGraph].add(edge2);

            if (edges[destinationCity] == null) {
                edges[destinationCity] = new ArrayList<>();
            }
            edges[destinationCity].add(edge2);
        }
    }

    private static long dijkstra(int destinationVertexId) {
        PriorityQueue<Vertex> heap = new PriorityQueue<>(10, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex vertex1, Vertex vertex2) {
                if (vertex1.distanceFromSource < vertex2.distanceFromSource) {
                    return -1;
                } else if (vertex1.distanceFromSource > vertex2.distanceFromSource) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        Vertex sourceVertex = vertices.get(1);
        sourceVertex.distanceFromSource = 0;
        heap.add(sourceVertex);

        while (heap.size() > 0) {
            Vertex currentVertex = heap.poll();

            for(Edge edge : edges[currentVertex.id]) {
                Vertex origin = vertices.get(edge.origin);
                Vertex destination = vertices.get(edge.destination);

                if (origin != currentVertex) {
                    if (origin.distanceFromSource > currentVertex.distanceFromSource + edge.length) {
                        heap.remove(origin);
                        origin.distanceFromSource = currentVertex.distanceFromSource + edge.length;
                        heap.add(origin);
                    }
                } else if (destination != currentVertex) {
                    if (destination.distanceFromSource > currentVertex.distanceFromSource + edge.length) {
                        heap.remove(destination);
                        destination.distanceFromSource = currentVertex.distanceFromSource + edge.length;
                        heap.add(destination);
                    }
                }
            }
        }

        Vertex destinationVertex = vertices.get(destinationVertexId);
        if (destinationVertex.distanceFromSource == Integer.MAX_VALUE) {
            return -1;
        } else {
            return destinationVertex.distanceFromSource;
        }
    }

}