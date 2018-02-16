package com.br.training.unicamp.summer.school2018.contest1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

/**
 * Created by rene on 22/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/L
public class AverageLength {

    private static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

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

        //Used to check EOF
        //If getLine() == null, it is a EOF
        //Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    public static class DirectedEdge {

        private final int vertex1;
        private final int vertex2;
        private final double weight;

        public DirectedEdge(int vertex1, int vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }

        public int from() {
            return vertex1;
        }

        public int to() {
            return vertex2;
        }

        public String toString() {
            return String.format("%d->%d %.2f", vertex1, vertex2, weight);
        }

    }

    public static class FloydWarshall {

        private static double[][] distances;     // length of shortest v->w path
        private static DirectedEdge[][] edgeTo;  // last edge on shortest v->w path

        private static boolean hasNegativeCycle;

        public FloydWarshall(double[][] edgeWeightedDigraph) {
            int vertices = edgeWeightedDigraph.length;

            distances = new double[vertices][vertices];
            edgeTo = new DirectedEdge[vertices][vertices];

            // Initialize distances using edge-weighted digraph's
            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                    double distance = edgeWeightedDigraph[vertex1][vertex2];

                    distances[vertex1][vertex2] = distance;
                    edgeTo[vertex1][vertex2] = new DirectedEdge(vertex1, vertex2, distance);
                }

                // In case of self-loops
                if (distances[vertex1][vertex1] >= 0.0) {
                    distances[vertex1][vertex1] = 0.0;
                    edgeTo[vertex1][vertex1] = null;
                }
            }

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {

                    if (edgeTo[vertex2][vertex1] == null) {
                        continue;  // optimization
                    }

                    for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                        if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                            distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                            edgeTo[vertex2][vertex3] = edgeTo[vertex1][vertex3];
                        }
                    }

                    if (distances[vertex2][vertex2] < 0.0) {
                        hasNegativeCycle = true;
                        return;
                    }
                }
            }

        }

        public static double distance(int source, int target) {
            if (hasNegativeCycle()) {
                throw new UnsupportedOperationException("Negative cost cycle exists");
            }

            return distances[source][target];
        }

        public Iterable<DirectedEdge> path(int source, int target) {
            if (hasNegativeCycle()) {
                throw new UnsupportedOperationException("Negative cost cycle exists");
            }
            if (!hasPath(source, target)) {
                return null;
            }

            Deque<DirectedEdge> path = new ArrayDeque<>();
            for(DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.from()]) {
                path.push(edge);
            }

            return path;
        }

        public static boolean hasPath(int source, int target) {
            return distances[source][target] != Double.POSITIVE_INFINITY;
        }

        public static boolean hasNegativeCycle() {
            return hasNegativeCycle;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int edges = FastReader.nextInt();

        double[][] edgeWeightedDigraph = new double[vertices][vertices];

        for(int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                edgeWeightedDigraph[vertex1][vertex2] = Double.POSITIVE_INFINITY;
            }
        }

        for(int edge = 0; edge < edges; edge++) {
            int vertex1 = FastReader.nextInt() - 1;
            int vertex2 = FastReader.nextInt() - 1;
            int distance = FastReader.nextInt();

            edgeWeightedDigraph[vertex1][vertex2] = distance;
        }

        new FloydWarshall(edgeWeightedDigraph);
        double totalDistances = 0;
        int totalPaths = 0;

        for(int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for(int vertex2 = 0; vertex2 < vertices; vertex2++) {
                if (vertex1 == vertex2) {
                    continue;
                }

                if (FloydWarshall.hasPath(vertex1, vertex2)) {
                    totalPaths++;
                    totalDistances += FloydWarshall.distance(vertex1, vertex2);
                }
            }
        }

        if (totalPaths == 0) {
            System.out.println(0);
        } else {
            System.out.printf("%.6f\n", totalDistances / totalPaths);
        }
    }

}
