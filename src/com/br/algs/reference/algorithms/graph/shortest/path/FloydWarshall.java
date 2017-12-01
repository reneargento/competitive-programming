package com.br.algs.reference.algorithms.graph.shortest.path;

/**
 * Created by rene on 29/04/17.
 */
public class FloydWarshall {

    // Graph represented by an adjacency matrix
    private static double[][] graph;

    private static boolean negativeCycle;

    public FloydWarshall(int size) {
        graph = new double[size][size];
        initGraph();
    }

    private static void initGraph() {
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph.length; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    graph[i][j] = Double.POSITIVE_INFINITY;
                }
            }
        }
    }

    public static boolean hasNegativeCycle() {
        return negativeCycle;
    }

    public static void addEdge(int from, int to, double weight) {
        graph[from][to] = weight;
    }

    // All-pairs shortest path
    // Considering vertices 1..n -> if they are 0..n change k, i and j starting index to 0
    public static double[][] floydWarshall() {
        double[][] distances = graph;

        for (int k = 1; k < graph.length; k++) {
            for (int i = 1; i < graph.length; i++) {
                for (int j = 1; j < graph.length; j++) {
                    distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
                }
            }

            if (distances[k][k] < 0.0) {
                negativeCycle = true;
            }
        }

        return distances;
    }

}
