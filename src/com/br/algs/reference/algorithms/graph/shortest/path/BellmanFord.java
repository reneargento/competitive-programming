package com.br.algs.reference.algorithms.graph.shortest.path;

import java.util.*;

/**
 * Created by rene on 28/11/17.
 */
@SuppressWarnings("unchecked")
//This BellmanFord algorithm uses a queue optimization to finish the algorithm earlier if there are no more
// vertices to be relaxed. This leads to a typical running time of E + V.
public class BellmanFord {

    public static class Edge {
        int vertex1;
        int vertex2;
        double weight;

        Edge(int vertex1, int vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private double[] distTo;         // length of path to vertex
    private Edge[] edgeTo;           // last edge on path to vertex
    private boolean[] onQueue;       // is this vertex on the queue?
    private Queue<Integer> queue;    // vertices being relaxed
    private int callsToRelax;        // number of calls to relax()
    private Iterable<Edge> cycle;    // if there is a negative cycle in edgeTo[], return it

    //O(E * V), but typically runs in (E + V)
    public BellmanFord(List<Edge>[] adjacent, int source) {
        distTo = new double[adjacent.length];
        edgeTo = new Edge[adjacent.length];
        onQueue = new boolean[adjacent.length];
        queue = new LinkedList<>();

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            distTo[vertex] = Double.POSITIVE_INFINITY;
        }

        distTo[source] = 0;
        queue.offer(source);
        onQueue[source] = true;

        // The only vertices that could be relaxed are the ones which had their distance from the source updated
        // on the last pass.
        while (!queue.isEmpty() && !hasNegativeCycle()) {
            int vertex = queue.poll();
            onQueue[vertex] = false;
            relax(adjacent, vertex);
        }
    }

    private void relax(List<Edge>[] adjacent, int vertex) {

        for(Edge edge : adjacent[vertex]) {
            int neighbor = edge.vertex2;

            if(distTo[neighbor] > distTo[vertex] + edge.weight) {
                distTo[neighbor] = distTo[vertex] + edge.weight;
                edgeTo[neighbor] = edge;

                if(!onQueue[neighbor]) {
                    queue.offer(neighbor);
                    onQueue[neighbor] = true;
                }
            }

            // Check if there is a negative cycle after every V calls to relax()
            if(callsToRelax++ % adjacent.length == 0) {
                findNegativeCycle();
            }
        }
    }

    public double distTo(int vertex) {
        return distTo[vertex];
    }

    public boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(int vertex) {
        if(!hasPathTo(vertex)) {
            return null;
        }

        Stack<Edge> path = new Stack<>();
        for(Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
            path.push(edge);
        }

        return path;
    }

    private void findNegativeCycle() {
        int vertices = edgeTo.length;
        List<Edge>[] shortestPathsTree = (List<Edge>[]) new ArrayList[vertices];

        for(int vertex = 0; vertex < vertices; vertex++) {
            shortestPathsTree[vertex] = new ArrayList<>();
        }

        for(int vertex = 0; vertex < vertices; vertex++) {
            if(edgeTo[vertex] != null) {
                Edge edge = edgeTo[vertex];
                shortestPathsTree[edge.vertex1].add(edge);
            }
        }

        new HasDirectedWeightedCycle(shortestPathsTree);
        cycle = HasDirectedWeightedCycle.cycle();
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<Edge> negativeCycle() {
        return cycle;
    }

    public static class HasDirectedWeightedCycle {

        private static boolean visited[];
        private static Edge[] edgeTo;
        private static Stack<Edge> cycle;    // vertices on  a cycle (if one exists)
        private static boolean[] onStack;    // vertices on recursive call stack

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

                    cycle.push(edge);

                    for(Edge edgeInCycle = edgeTo[vertex]; edgeInCycle != null && edgeInCycle.vertex1 != vertex;
                        edgeInCycle = edgeTo[edgeInCycle.vertex1]) {
                        cycle.push(edgeInCycle);
                    }

                    cycle.push(edge);
                }
            }

            onStack[vertex] = false;
        }

        public static boolean hasCycle() {
            return cycle != null;
        }

        public static Iterable<Edge> cycle() {
            return cycle;
        }

    }

}
