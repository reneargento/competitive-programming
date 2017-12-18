package com.br.algs.reference.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 09/12/17.
 */
@SuppressWarnings("unchecked")
public class EdgeWeightedDigraph {

    private final int vertices;
    private int edges;
    private List<DirectedEdge>[] adjacent;

    public EdgeWeightedDigraph(int vertices) {
        this.vertices = vertices;
        edges = 0;
        adjacent = (List<DirectedEdge>[]) new ArrayList[vertices];

        for(int vertex = 0; vertex < vertices; vertex++) {
            adjacent[vertex] = new ArrayList<>();
        }
    }

    public int vertices() {
        return vertices;
    }

    public int edgesCount() {
        return edges;
    }

    public int outdegree(int vertex) {
        return adjacent[vertex].size();
    }

    public void addEdge(DirectedEdge edge) {
        adjacent[edge.from()].add(edge);
        edges++;
    }

    public Iterable<DirectedEdge> adjacent(int vertex) {
        return adjacent[vertex];
    }

    public Iterable<DirectedEdge> edges() {
        List<DirectedEdge> bag = new ArrayList<>();

        for(int vertex = 0; vertex < vertices; vertex++) {
            bag.addAll(adjacent[vertex]);
        }

        return bag;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(int vertex = 0; vertex < vertices(); vertex++) {
            stringBuilder.append(vertex).append(": ");

            for(DirectedEdge neighbor : adjacent(vertex)) {
                stringBuilder.append(neighbor).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}
