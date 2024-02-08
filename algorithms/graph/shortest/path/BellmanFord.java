package algorithms.graph.shortest.path;

import algorithms.graph.cycle.detection.EdgeWeightedDirectedCycle;
import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.*;

/**
 * Created by Rene Argento on 28/11/17.
 */
// This BellmanFord algorithm uses a queue optimization to finish the algorithm earlier if there are no more
// vertices to be relaxed. This leads to O(E * V) but with a typical running time of E + V.
public class BellmanFord {
    private final double[] distTo;         // length of path to vertex
    private final DirectedEdge[] edgeTo;   // last edge on path to vertex
    private final boolean[] onQueue;       // is this vertex on the queue?
    private final Queue<Integer> queue;    // vertices being relaxed
    private int callsToRelax;              // number of calls to relax()
    private Iterable<DirectedEdge> cycle;  // if there is a negative cycle in edgeTo[], return it

    //O(E * V), but typically runs in (E + V)
    public BellmanFord(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
        distTo = new double[edgeWeightedDigraph.vertices()];
        edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
        onQueue = new boolean[edgeWeightedDigraph.vertices()];
        queue = new LinkedList<>();

        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
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
        for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            int neighbor = edge.to();

            if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                distTo[neighbor] = distTo[vertex] + edge.weight();
                edgeTo[neighbor] = edge;

                if (!onQueue[neighbor]) {
                    queue.offer(neighbor);
                    onQueue[neighbor] = true;
                }
            }

            // Check if there is a negative cycle after every V calls to relax()
            if (callsToRelax++ % edgeWeightedDigraph.vertices() == 0) {
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
        if (!hasPathTo(vertex)) {
            return null;
        }

        Deque<DirectedEdge> path = new ArrayDeque<>();
        for (DirectedEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
            path.push(edge);
        }
        return path;
    }

    private void findNegativeCycle() {
        int vertices = edgeTo.length;
        EdgeWeightedDigraph shortestPathsTree = new EdgeWeightedDigraph(vertices);

        for (int vertex = 0; vertex < vertices; vertex++) {
            if (edgeTo[vertex] != null) {
                shortestPathsTree.addEdge(edgeTo[vertex]);
            }
        }

        EdgeWeightedDirectedCycle edgeWeightedCycleFinder = new EdgeWeightedDirectedCycle(shortestPathsTree);
        cycle = edgeWeightedCycleFinder.cycle();
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }
}
