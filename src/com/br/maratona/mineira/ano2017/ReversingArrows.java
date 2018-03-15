package com.br.maratona.mineira.ano2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by rene on 27/05/17.
 */

/**
 * Bibi and Bibika are playing a simple game where the judge, with each round,
 * makes a drawing with several circles and arrows linking some of them.

 Bibi must count the least X number of arrows that need to be inverted to exist at least one path from A to B and Bibika must
 count the smallest amount Y of inverted arrows to exist at least one path in the opposite direction from B to A.
 The game who find the lowest value.
 If there is no path between A > B or B > A, the game ends in a draw, regardless of the number of arrows reversed.

 As the judge in some rounds makes a very large drawing, it is quite complicated to check the veracity of the answers given by the girls.
 Your task is to automate this process for him.

 Input
 The first line of each test case contains four integers C ( 1 ≤ C ≤ 104 ) , S ( 0 ≤ S ≤ 5 x 105), A e B, ( 1 ≤ A, B ≤ C ),
 where C is the number of circles, S is the number of arrows, A and B are the ends of the game.
 Each of the next S lines contain two integers C1 and C2, representing an arrow connecting the circle C1 to the circle C2.

 Output
 For each test case, display the winner's name and the amount Q inverted arrows in format Bibi: Q or Bibika: Q.
 If the game ends in a draw, display Bibibibika.
 */

//https://www.urionlinejudge.com.br/judge/en/challenges/view/266/9
//https://s3.amazonaws.com/codechef_shared/download/Solutions/2014/August/Tester/REVERSE.cpp
public class ReversingArrows {

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

    private static class Graph {

        private class Vertex {
            int id;
            boolean processed;
            LinkedList<Edge> edgesAssociated;
        }

        private class Edge {
            Vertex vertex1;
            Vertex vertex2;
            int length;

            public Edge(Vertex vertex1, Vertex vertex2, int length) {
                this.vertex1 = vertex1;
                this.vertex2 = vertex2;
                this.length = length;
            }
        }

        HashMap<Integer, Vertex> vertices;
        LinkedList<Edge> edges;

        public Graph() {
            vertices = new HashMap<>();
            edges = new LinkedList<>();
        }

        //O(1)
        public void addVertex(int vertexId) {
            Vertex vertex = new Vertex();
            vertex.id = vertexId;
            vertex.processed = false;
            vertex.edgesAssociated = new LinkedList<>();

            vertices.put(vertexId, vertex);
        }

        //O(1)
        public void addEdge(int vertexId1, int vertexId2, int length) {
            if (length < 0) {
                throw new UnsupportedOperationException("Edge length cannot be negative");
            }

            if (vertices.get(vertexId1) == null) {
                addVertex(vertexId1);
            }
            if (vertices.get(vertexId2) == null) {
                addVertex(vertexId2);
            }

            Vertex vertex1 = vertices.get(vertexId1);
            Vertex vertex2 = vertices.get(vertexId2);

            Edge edge = new Edge(vertex1, vertex2, length);

            edges.add(edge);

            vertices.get(vertexId1).edgesAssociated.add(edge);
            vertices.get(vertexId2).edgesAssociated.add(edge);
        }

        //O(1)
        public int getVerticesCount() {
            return vertices.size();
        }

        //O(1)
        public void setVertexProcessed(int vertexId) {
            vertices.get(vertexId).processed = true;
        }

        //O(V)
        public void clearProcessedVertices() {
            for(int vertexId : vertices.keySet()) {
                vertices.get(vertexId).processed = false;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int edges = FastReader.nextInt();

        int vertexA = FastReader.nextInt();
        int vertexB = FastReader.nextInt();

        Graph graph = new Graph();
        for(int i = 1; i <= vertices; i++) {
            graph.addVertex(i);
        }

        for(int i = 0; i < edges; i++) {
            int vertex1 = FastReader.nextInt();
            int vertex2 = FastReader.nextInt();

            //Add original edge with cost = 0
            graph.addEdge(vertex1, vertex2, 0);
            //Add reverse edge with cost = 1
            graph.addEdge(vertex2, vertex1, 1);
        }

        computeShortestPath(graph, vertexA);
        int distanceFromAtoB = computedShortestPathDistances[vertexB];

        graph.clearProcessedVertices();
        computeShortestPath(graph, vertexB);
        int distanceFromBtoA = computedShortestPathDistances[vertexA];

        if (distanceFromAtoB == distanceFromBtoA
                || distanceFromAtoB == UNCOMPUTED_DISTANCE
                || distanceFromBtoA == UNCOMPUTED_DISTANCE) {
            System.out.println("Bibibibika");
        } else {
            if (distanceFromAtoB < distanceFromBtoA) {
                System.out.println("Bibi: " + distanceFromAtoB);
            } else {
                System.out.println("Bibika: " + distanceFromBtoA);
            }
        }
    }

    //Djikstra's Shortest Path
    //By definition
    private static final int UNCOMPUTED_DISTANCE = 1000000;

    private static int[] computedShortestPathDistances;

    //O(n * log(m))
    private static void computeShortestPath(Graph graph, int sourceVertex) {

        int verticesCount = graph.getVerticesCount();

        computedShortestPathDistances = new int[verticesCount + 1];

        setDefaultDistances();

        //Distance from S to itself is 0
        computedShortestPathDistances[sourceVertex] = 0;
        graph.vertices.get(sourceVertex).processed = true;

        //Using Java's priority queue
        PriorityQueue<Graph.Edge> heap = new PriorityQueue<Graph.Edge>(10, new Comparator<Graph.Edge>() {
            @Override
            public int compare(Graph.Edge edge1, Graph.Edge edge2) {
                return edge1.length - edge2.length;
            }
        });

        //Add edges associated to the first vertex to the heap
        for(Graph.Edge edge : graph.vertices.get(sourceVertex).edgesAssociated) {
            //Directed graph
            if (edge.vertex1.id == sourceVertex) {
                heap.add(edge);
            }

            //Undirected graph
            //heap.add(edge);
        }

        while (heap.size() > 0) {
            Graph.Edge edge = heap.poll();

            if (!edge.vertex1.processed) {
                graph.setVertexProcessed(edge.vertex1.id);
                computeAndAddEdgeToHeap(graph, heap, edge, edge.vertex1.id);
            } else if (!edge.vertex2.processed) {
                graph.setVertexProcessed(edge.vertex2.id);
                computeAndAddEdgeToHeap(graph, heap, edge, edge.vertex2.id);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private static void computeAndAddEdgeToHeap(Graph graph, PriorityQueue heap, Graph.Edge edge, int vertexId) {
        computedShortestPathDistances[vertexId] = edge.length;

        for(Graph.Edge newEdge : graph.vertices.get(vertexId).edgesAssociated) {
            //if (!newEdge.vertex1.processed || !newEdge.vertex2.processed) { //Undirected graph
            if (!newEdge.vertex2.processed) {
                newEdge.length = edge.length + newEdge.length;
                heap.add(newEdge);
            }
        }
    }

    private static void setDefaultDistances() {
        for (int i = 0; i < computedShortestPathDistances.length; i++) {
            computedShortestPathDistances[i] = UNCOMPUTED_DISTANCE;
        }
    }

}
