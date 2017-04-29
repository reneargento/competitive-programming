package com.br.algs.reference.algorithms.search;

/**
 * Created by rene on 29/04/17.
 */
public class FloydWarshall {

    // Graph represented by an adjacency matrix
    private double[][] graph;

    private boolean negativeCycle;

    public FloydWarshall(int size) {
        graph = new double[size][size];
        initGraph();
    }

    private void initGraph() {
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

    public boolean hasNegativeCycle() {
        return negativeCycle;
    }

    public void addEdge(int from, int to, double weight) {
        graph[from][to] = weight;
    }

    // All-pairs shortest path
    public double[][] floydWarshall() {
        double[][] distances = graph;

        for (int k = 0; k < graph.length; k++) {
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph.length; j++) {
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
