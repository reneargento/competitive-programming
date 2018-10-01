package com.br.algs.reference.algorithms.graph.network.flow;

import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 01/10/18.
 */
public class FlowNetwork {

    private final int vertices;
    private int edges;
    private Set<FlowEdge>[] adjacent;

    @SuppressWarnings("unchecked")
    public FlowNetwork(int vertices) {
        if (vertices < 0) {
            throw new IllegalArgumentException("Number of vertices must be nonnegative");
        }

        this.vertices = vertices;
        edges = 0;
        adjacent = new HashSet[vertices];

        for(int vertex = 0; vertex < vertices; vertex++) {
            adjacent[vertex] = new HashSet<>();
        }
    }

    public int vertices() {
        return vertices;
    }

    public int edgesCount() {
        return edges;
    }

    public void addEdge(FlowEdge edge) {
        int vertex1 = edge.from();
        int vertex2 = edge.to();

        adjacent[vertex1].add(edge);
        adjacent[vertex2].add(edge);
        edges++;
    }

    public Iterable<FlowEdge> adjacent(int vertex) {
        return adjacent[vertex];
    }

    public Iterable<FlowEdge> edges() {
        Set<FlowEdge> edges = new HashSet<>();

        for(int vertex = 0; vertex < vertices; vertex++) {
            for(FlowEdge edge : adjacent[vertex]) {
                if (edge.to() != vertex) {
                    edges.add(edge);
                }
            }
        }

        return edges;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(int vertex = 0; vertex < vertices(); vertex++) {
            stringBuilder.append(vertex).append(": ");

            StringJoiner neighbors = new StringJoiner(" ");
            for(FlowEdge edge : adjacent(vertex)) {
                if (edge.from() == vertex) {
                    neighbors.add(edge.toString());
                }
            }
            stringBuilder.append(neighbors.toString()).append("\n");
        }

        return stringBuilder.toString();
    }

}
