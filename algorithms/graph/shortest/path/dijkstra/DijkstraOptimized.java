package algorithms.graph.shortest.path.dijkstra;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;
import datastructures.priority.queue.IndexMinPriorityQueue;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

/**
 * Created by Rene Argento on 09/12/17.
 */
public class DijkstraOptimized {
    private final DirectedEdge[] edgeTo;  // last edge on path to vertex
    private final double[] distTo;        // length of path to vertex
    private final IndexMinPriorityQueue<Double> priorityQueue;

    public DijkstraOptimized(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
        edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
        distTo = new double[edgeWeightedDigraph.vertices()];
        priorityQueue = new IndexMinPriorityQueue<>(edgeWeightedDigraph.vertices());

        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[source] = 0;
        priorityQueue.insert(source, 0.0);

        while (!priorityQueue.isEmpty()) {
            relax(edgeWeightedDigraph, priorityQueue.deleteMin());
            // In a source-sink problem, break the loop if the sink was relaxed
        }
    }

    private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
        for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            int neighbor = edge.to();

            if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                distTo[neighbor] = distTo[vertex] + edge.weight();
                edgeTo[neighbor] = edge;

                if (priorityQueue.contains(neighbor)) {
                    priorityQueue.decreaseKey(neighbor, distTo[neighbor]);
                } else {
                    priorityQueue.insert(neighbor, distTo[neighbor]);
                }
            }
        }
    }

    public double distTo(int vertex) {
        return distTo[vertex];
    }

    public DirectedEdge edgeTo(int vertex) {
        return edgeTo[vertex];
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
}
