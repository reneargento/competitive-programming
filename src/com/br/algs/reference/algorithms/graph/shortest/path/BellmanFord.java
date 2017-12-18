package com.br.algs.reference.algorithms.graph.shortest.path;

import com.br.algs.reference.datastructures.DirectedEdge;
import com.br.algs.reference.datastructures.EdgeWeightedDigraph;

import java.util.*;

/**
 * Created by rene on 28/11/17.
 */
@SuppressWarnings("unchecked")
//This BellmanFord algorithm uses a queue optimization to finish the algorithm earlier if there are no more
// vertices to be relaxed. This leads to O(E * V) but with a typical running time of E + V.
public class BellmanFord {

    private double[] distTo;         // length of path to vertex
    private DirectedEdge[] edgeTo;           // last edge on path to vertex
    private boolean[] onQueue;       // is this vertex on the queue?
    private Queue<Integer> queue;    // vertices being relaxed
    private int callsToRelax;        // number of calls to relax()
    private Iterable<DirectedEdge> cycle;    // if there is a negative cycle in edgeTo[], return it

    //O(E * V), but typically runs in (E + V)
    public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
        distTo = new double[edgeWeightedDigraph.vertices()];
        edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
        onQueue = new boolean[edgeWeightedDigraph.vertices()];
        queue = new LinkedList<>();

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
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
            relax(edgeWeightedDigraph, vertex);
        }
    }

    private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {

        for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            int neighbor = edge.to();

            if(distTo[neighbor] > distTo[vertex] + edge.weight()) {
                distTo[neighbor] = distTo[vertex] + edge.weight();
                edgeTo[neighbor] = edge;

                if(!onQueue[neighbor]) {
                    queue.offer(neighbor);
                    onQueue[neighbor] = true;
                }
            }

            // Check if there is a negative cycle after every V calls to relax()
            if(callsToRelax++ % edgeWeightedDigraph.vertices() == 0) {
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

    public Iterable<DirectedEdge> pathTo(int vertex) {
        if(!hasPathTo(vertex)) {
            return null;
        }

        Stack<DirectedEdge> path = new Stack<>();
        for(DirectedEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
            path.push(edge);
        }

        return path;
    }

    private void findNegativeCycle() {
        int vertices = edgeTo.length;
        List<DirectedEdge>[] shortestPathsTree = (List<DirectedEdge>[]) new ArrayList[vertices];

        for(int vertex = 0; vertex < vertices; vertex++) {
            shortestPathsTree[vertex] = new ArrayList<>();
        }

        for(int vertex = 0; vertex < vertices; vertex++) {
            if(edgeTo[vertex] != null) {
                DirectedEdge edge = edgeTo[vertex];
                shortestPathsTree[edge.from()].add(edge);
            }
        }

        new HasDirectedWeightedCycle(shortestPathsTree);
        cycle = HasDirectedWeightedCycle.cycle();
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    public static class HasDirectedWeightedCycle {

        private static boolean visited[];
        private static DirectedEdge[] edgeTo;
        private static Stack<DirectedEdge> cycle;    // vertices on  a cycle (if one exists)
        private static boolean[] onStack;    // vertices on recursive call stack

        public HasDirectedWeightedCycle(List<DirectedEdge>[] adjacent) {
            onStack = new boolean[adjacent.length];
            edgeTo = new DirectedEdge[adjacent.length];
            visited = new boolean[adjacent.length];

            for(int vertex = 0; vertex < adjacent.length; vertex++) {
                if(!visited[vertex]) {
                    dfs(adjacent, vertex);
                }
            }
        }

        private void dfs(List<DirectedEdge>[] adjacent, int vertex) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for(DirectedEdge edge : adjacent[vertex]) {
                int neighbor = edge.to();

                if(hasCycle()) {
                    return;
                } else if(!visited[neighbor]) {
                    edgeTo[neighbor] = edge;
                    dfs(adjacent, neighbor);
                } else if(onStack[neighbor]) {
                    cycle = new Stack<>();

                    cycle.push(edge);

                    for(DirectedEdge edgeInCycle = edgeTo[vertex]; edgeInCycle != null && edgeInCycle.from() != vertex;
                        edgeInCycle = edgeTo[edgeInCycle.from()]) {
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

        public static Iterable<DirectedEdge> cycle() {
            return cycle;
        }
    }

}
