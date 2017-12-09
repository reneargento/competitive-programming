package com.br.algs.reference.algorithms.graph.cycle.detection;

import java.util.List;
import java.util.Stack;

/**
 * Created by rene on 28/11/17.
 */
public class HasDirectedWeightedCycle {

    private static class Edge {
        int vertex1;
        int vertex2;
        int weight;

        Edge(int vertex1, int vertex2, int weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private boolean visited[];
    private Edge[] edgeTo;
    private Stack<Edge> cycle;    // vertices on  a cycle (if one exists)
    private boolean[] onStack;    // vertices on recursive call stack

    public HasDirectedWeightedCycle(List<Edge>[] adjacent) {
        onStack = new boolean[adjacent.length];
        edgeTo = new Edge[adjacent.length];
        visited = new boolean[adjacent.length];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if(!visited[vertex]) {
                dfs(adjacent, vertex);
            }
        }
    }

    private void dfs(List<Edge>[] adjacent, int vertex) {
        onStack[vertex] = true;
        visited[vertex] = true;

        for(Edge edge : adjacent[vertex]) {
            int neighbor = edge.vertex2;

            if(hasCycle()) {
                return;
            } else if(!visited[neighbor]) {
                edgeTo[neighbor] = edge;
                dfs(adjacent, neighbor);
            } else if(onStack[neighbor]) {
                cycle = new Stack<>();

                Edge edgeInCycle = edge;

                while (edgeInCycle.vertex1 != neighbor) {
                    cycle.push(edgeInCycle);
                    edgeInCycle = edgeTo[edgeInCycle.vertex1];
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

    public Iterable<Edge> cycle() {
        return cycle;
    }

}
