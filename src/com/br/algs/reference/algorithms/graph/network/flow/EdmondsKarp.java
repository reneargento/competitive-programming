package com.br.algs.reference.algorithms.graph.network.flow;

/**
 * Created by Rene Argento on 30/09/18.
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * Edmonds–Karp algorithm implementation for the Ford–Fulkerson method for computing the maximum flow in a flow network.
 * Runtime O(V * E^2) time
 */
public class EdmondsKarp {

    private boolean[] visited;  // Is s -> v path in residual graph?
    private FlowEdge[] edgeTo;  // Last edge on shortest s -> v path
    private double maxFlowValue;

    public EdmondsKarp(FlowNetwork flowNetwork, int source, int target) {
        // Find max flow in flowNetwork from source to target
        while (hasAugmentingPath(flowNetwork, source, target)) {
            // Compute bottleneck capacity
            double bottleneck = Double.POSITIVE_INFINITY;

            for (int vertex = target; vertex != source; vertex = edgeTo[vertex].other(vertex)) {
                bottleneck = Math.min(bottleneck, edgeTo[vertex].residualCapacityTo(vertex));
            }

            // Augment flow
            for (int vertex = target; vertex != source; vertex = edgeTo[vertex].other(vertex)) {
                edgeTo[vertex].addResidualFlowTo(vertex, bottleneck);
            }

            maxFlowValue += bottleneck;
        }
    }

    private boolean hasAugmentingPath(FlowNetwork flowNetwork, int source, int target) {
        visited = new boolean[flowNetwork.vertices()];
        edgeTo = new FlowEdge[flowNetwork.vertices()];

        Queue<Integer> queue = new LinkedList<>();

        visited[source] = true;
        queue.offer(source);

        while (!queue.isEmpty() && !visited[target]) {
            int vertex = queue.poll();

            for (FlowEdge edge : flowNetwork.adjacent(vertex)) {
                int neighbor = edge.other(vertex);

                if (edge.residualCapacityTo(neighbor) > 0 && !visited[neighbor]) {
                    edgeTo[neighbor] = edge;
                    visited[neighbor] = true;
                    queue.offer(neighbor);
                }
            }
        }

        return visited[target];
    }

    public double maxFlowValue() {
        return maxFlowValue;
    }

    // Returns true if the vertex is on the source side of the min cut
    public boolean inCut(int vertex) {
        return visited[vertex];
    }

}
