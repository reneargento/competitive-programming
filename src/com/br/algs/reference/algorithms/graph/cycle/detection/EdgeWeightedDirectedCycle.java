package com.br.algs.reference.algorithms.graph.cycle.detection;

import com.br.algs.reference.datastructures.DirectedEdge;
import com.br.algs.reference.datastructures.EdgeWeightedDigraph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

/**
 * Created by rene on 28/11/17.
 */
public class EdgeWeightedDirectedCycle {

    private boolean visited[];
    private DirectedEdge[] edgeTo;
    private Deque<DirectedEdge> cycle; // vertices on a cycle (if one exists)
    private boolean[] onStack;         // vertices on recursive call stack

    public EdgeWeightedDirectedCycle(EdgeWeightedDigraph edgeWeightedDigraph) {
        onStack = new boolean[edgeWeightedDigraph.vertices()];
        edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
        visited = new boolean[edgeWeightedDigraph.vertices()];

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            if (!visited[vertex]) {
                dfs(edgeWeightedDigraph, vertex);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
        onStack[vertex] = true;
        visited[vertex] = true;

        for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            int neighbor = edge.to();

            if (hasCycle()) {
                return;
            } else if (!visited[neighbor]) {
                edgeTo[neighbor] = edge;
                dfs(edgeWeightedDigraph, neighbor);
            } else if (onStack[neighbor]) {
                cycle = new ArrayDeque<>();

                DirectedEdge edgeInCycle = edge;

                while (edgeInCycle.from() != neighbor) {
                    cycle.push(edgeInCycle);
                    edgeInCycle = edgeTo[edgeInCycle.from()];
                }

                cycle.push(edgeInCycle);
                return;
            }
        }

        onStack[vertex] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> cycle() {
        return cycle;
    }

}
