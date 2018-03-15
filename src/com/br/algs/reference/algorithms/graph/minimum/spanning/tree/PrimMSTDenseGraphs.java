package com.br.algs.reference.algorithms.graph.minimum.spanning.tree;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by rene on 11/11/17.
 */
// This MST algorithm runs in O(V^2)
    // It is faster for dense graphs because it does not depend on the number of edges
public class PrimMSTDenseGraphs {

    private static class Edge implements Comparable<Edge> {

        private final int vertex1;
        private final int vertex2;
        private final double weight;

        public Edge(int vertex1, int vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public double weight() {
            return weight;
        }

        public int either() {
            return vertex1;
        }

        public int other(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            } else if (vertex == vertex2) {
                return vertex1;
            } else {
                throw new RuntimeException("Inconsistent edge");
            }
        }

        public int compareTo(Edge that) {
            if (this.weight < that.weight) {
                return -1;
            } else if (this.weight > that.weight) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    private static Edge[] edgeTo; // shortest edge from tree vertex
    private static double[] distTo; // distTo[vertex] = edgeTo[vertex].weight()
    private static boolean[] marked; // true if vertex is on the minimum spanning tree

    private double weight;

    public PrimMSTDenseGraphs(List<Edge>[] adjacent) {
        edgeTo = new Edge[adjacent.length];
        distTo = new double[adjacent.length];
        marked = new boolean[adjacent.length];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            distTo[vertex] = Double.POSITIVE_INFINITY;
        }

        distTo[0] = 0;
        int visitedVertices = 0;
        int nextVertexToVisit = 0;

        while (visitedVertices != adjacent.length) {
            nextVertexToVisit = visit(adjacent, nextVertexToVisit);
            visitedVertices++;
        }
    }

    private int visit(List<Edge>[] adjacent, int vertex) {
        // Add vertex to the minimum spanning tree; update data structures
        marked[vertex] = true;

        for(Edge edge : adjacent[vertex]) {
            int otherVertex = edge.other(vertex);
            if (marked[otherVertex]) {
                continue; // vertex-otherVertex is ineligible
            }

            if (edge.weight() < distTo[otherVertex]) {
                // Edge edge is the new best connection from the minimum spanning tree to otherVertex
                if (distTo[otherVertex] != Double.POSITIVE_INFINITY) {
                    weight -= distTo[otherVertex];
                }
                weight += edge.weight();

                edgeTo[otherVertex] = edge;
                distTo[otherVertex] = edge.weight();
            }
        }

        int nextVertexToVisit = -1;
        double minEdgeWeight = Double.POSITIVE_INFINITY;

        for(int vertexToVisit = 0; vertexToVisit < adjacent.length; vertexToVisit++) {
            if (!marked[vertexToVisit] && distTo[vertexToVisit] < minEdgeWeight) {
                nextVertexToVisit = vertexToVisit;
                minEdgeWeight = distTo[vertexToVisit];
            }
        }

        return nextVertexToVisit;
    }

    public static Iterable<Edge> edges() {
        Queue<Edge> minimumSpanningTree = new LinkedList<>();

        for(int vertex = 1; vertex < edgeTo.length; vertex++) {
            minimumSpanningTree.offer(edgeTo[vertex]);
        }

        return minimumSpanningTree;
    }

    public double eagerWeight() {
        return weight;
    }

}
