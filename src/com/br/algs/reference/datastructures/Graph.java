package com.br.algs.reference.datastructures;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 06/02/18.
 */
@SuppressWarnings("unchecked")
public class Graph {

    private final int vertices;
    private int edges;
    private List<Integer>[] adjacent;

    public Graph(int vertices) {
        this.vertices = vertices;
        this.edges = 0;
        adjacent = (List<Integer>[]) new ArrayList[vertices];

        for(int vertex = 0; vertex < vertices; vertex++) {
            adjacent[vertex] = new ArrayList<>();
        }
    }

    public int vertices() {
        return vertices;
    }

    public int edges() {
        return edges;
    }

    public void addEdge(int vertex1, int vertex2) {
        adjacent[vertex1].add(vertex2);
        adjacent[vertex2].add(vertex1);
        edges++;
    }

    public List<Integer>[] getAdjacencyList() {
        return adjacent;
    }

    public void updateAdjacencyList(int vertex, List adjacencyList) {
        adjacent[vertex] = adjacencyList;
    }

    public Iterable<Integer> adjacent(int vertex) {
        return adjacent[vertex];
    }

    public int degree(int vertex) {
        return adjacent[vertex].size();
    }
}