package com.br.algs.reference.algorithms.graph;

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

    private static class Edge {
        int vertex1;
        int vertex2;
        int length;

        Edge(int vertex1, int vertex2, int length) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.length = length;
        }
    }

    //By definition
    private static final int UNCOMPUTED_DISTANCE = 1000000;

    private static int[] computedShortestPathDistances;

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int totalEdges = FastReader.nextInt();

        List<Edge>[] adjacent = (List<Edge>[]) new ArrayList[vertices + 1];

        for(int i=0; i < totalEdges; i++) {
            int vertex1 = FastReader.nextInt();
            int vertex2 = FastReader.nextInt();
            int length = FastReader.nextInt();

            Edge edge = new Edge(vertex1, vertex2, length);
            if(adjacent[vertex1] == null) {
                adjacent[vertex1] = new ArrayList<>();
            }
            if(adjacent[vertex2] == null) {
                adjacent[vertex2] = new ArrayList<>();
            }

            adjacent[vertex1].add(edge);
            adjacent[vertex2].add(edge);//undirected graph
        }

        int sourceVertex = FastReader.nextInt();

        computeShortestPath(adjacent, sourceVertex);
    }

    //O(n * log(m))
    private static void computeShortestPath(List<Edge>[] adjacent, int sourceVertex) {

        boolean[] visited = new boolean[adjacent.length];
        computedShortestPathDistances = new int[adjacent.length];

        setDefaultDistances();

        //Distance from S to itself is 0
        computedShortestPathDistances[sourceVertex] = 0;
        visited[sourceVertex] = true;

        //Using Java's priority queue
        PriorityQueue<Edge> heap = new PriorityQueue<Edge>(10, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.length - edge2.length;
            }
        });

        //Add edges associated to the first vertex to the heap
        for(Edge edge : adjacent[sourceVertex]) {
            heap.add(edge);
        }

        while (heap.size() > 0) {
            Edge edge = heap.poll();

            if(!visited[edge.vertex1]) {
                visited[edge.vertex1] = true;
                computeAndAddEdgeToHeap(adjacent, visited, heap, edge, edge.vertex1);
            } else if(!visited[edge.vertex2]) {
                visited[edge.vertex2] = true;
                computeAndAddEdgeToHeap(adjacent, visited, heap, edge, edge.vertex2);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void computeAndAddEdgeToHeap(List<Edge>[] adjacent, boolean[] visited, PriorityQueue heap, Edge edge, int vertexId) {
        computedShortestPathDistances[vertexId] = edge.length;

        for(Edge newEdge : adjacent[vertexId]) {
            if (!visited[newEdge.vertex1] || !visited[newEdge.vertex2]) {
                newEdge.length = edge.length + newEdge.length;
                heap.add(newEdge);
            }
        }
    }

    private static void setDefaultDistances() {
        for (int i=0; i < computedShortestPathDistances.length; i++) {
            computedShortestPathDistances[i] = UNCOMPUTED_DISTANCE;
        }
    }

}
