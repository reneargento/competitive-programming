package com.br.algs.reference.algorithms.graph.shortest.path;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by rene.argento on 30/05/17.
 */
@SuppressWarnings("unchecked")
public class DijkstraShortestPath {

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

    private static class Vertex {
        int id;
        long distance;

        Vertex(int id, long distance) {
            this.id = id;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object other) {
            if(!(other instanceof Vertex)) {
                return false;
            }

            Vertex otherVertex = (Vertex) other;
            return this.id == otherVertex.id;
        }
    }

    private static class Edge {
        int vertex;
        int length;

        Edge(int vertex, int length) {
            this.vertex = vertex;
            this.length = length;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int totalEdges = FastReader.nextInt();

        List<Edge>[] adjacent = (List<Edge>[]) new ArrayList[vertices + 1];

        for(int i = 0; i < totalEdges; i++) {
            int vertex1 = FastReader.nextInt();
            int vertex2 = FastReader.nextInt();
            int length = FastReader.nextInt();

            Edge edge1 = new Edge(vertex1, length);
            Edge edge2 = new Edge(vertex2, length);
            if(adjacent[vertex1] == null) {
                adjacent[vertex1] = new ArrayList<>();
            }
            if(adjacent[vertex2] == null) {
                adjacent[vertex2] = new ArrayList<>();
            }

            adjacent[vertex1].add(edge2);
            adjacent[vertex2].add(edge1);//undirected graph
        }

        int sourceVertex = FastReader.nextInt();
        long[] computedShortestPathDistances = new long[vertices + 1];
        dijkstra(adjacent, sourceVertex, computedShortestPathDistances);
    }

    //O(E * lg(V))
    private static void dijkstra(List<Edge>[] adjacent, int sourceVertexId, long[] computedShortestPathDistances) {

        //1- Init base case
        Arrays.fill(computedShortestPathDistances, Integer.MAX_VALUE);
        computedShortestPathDistances[sourceVertexId] = 0;

        //2- Create heap and compute distances
        PriorityQueue<Vertex> heap = new PriorityQueue(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex vertex1, Vertex vertex2) {
                return vertex1.distance < vertex2.distance ? -1 : vertex1.distance > vertex2.distance ? 1 : 0;
            }
        });

        heap.offer(new Vertex(sourceVertexId, 0));

        while(heap.size() > 0) {
            //Get nearest vertex
            Vertex nearestVertex = heap.poll();

            for(Edge edge : adjacent[nearestVertex.id]) {
                long newDistance = computedShortestPathDistances[nearestVertex.id] + edge.length;

                if(computedShortestPathDistances[edge.vertex] > newDistance) {
                    heap.remove(new Vertex(edge.vertex, 0)); //In this case, distance is not used

                    computedShortestPathDistances[edge.vertex] = newDistance;
                    heap.add(new Vertex(edge.vertex, newDistance));
                }
            }
        }
    }

    //Optimized version when there is a target
    private static void dijkstra(List<Edge>[] adjacent, int sourceVertexId,
                                 long[] computedShortestPathDistances, int target) {

        //1- Init base case
        Arrays.fill(computedShortestPathDistances, Integer.MAX_VALUE);
        computedShortestPathDistances[sourceVertexId] = 0;

        //2- Create heap and compute distances
        PriorityQueue<Vertex> heap = new PriorityQueue(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex vertex1, Vertex vertex2) {
                return vertex1.distance < vertex2.distance ? -1 : vertex1.distance > vertex2.distance ? 1 : 0;
            }
        });

        heap.offer(new Vertex(sourceVertexId, 0));

        while(heap.size() > 0) {
            //Get nearest vertex
            Vertex nearestVertex = heap.poll();

            //Optimization
            if(nearestVertex.id == target) {
                break;
            }

            for(Edge edge : adjacent[nearestVertex.id]) {
                long newDistance = computedShortestPathDistances[nearestVertex.id] + edge.length;

                if(computedShortestPathDistances[edge.vertex] > newDistance) {
                    //Optimization - do not remove vertices from heap
                    // heap.remove(edge.vertex);

                    computedShortestPathDistances[edge.vertex] = newDistance;
                    heap.add(new Vertex(edge.vertex, newDistance));
                }
            }
        }
    }

}
