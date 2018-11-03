package com.br.algs.reference.algorithms.graph.network.flow;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * Created by Rene Argento on 22/10/18.
 */
// Based on:
// http://codeforces.com/blog/entry/10956
// https://codeforces.com/blog/entry/48611
// http://jeffe.cs.illinois.edu/teaching/algorithms/notes/25-maxflowext.pdf
public class MaxFlowLowerBoundCapacities {

    private class FlowEdgeWithLowerBoundCapacity {
        private final int vertex1;
        private final int vertex2;
        private final double capacity;
        private final double lowerBoundCapacity;
        private double flow;

        public FlowEdgeWithLowerBoundCapacity(int vertex1, int vertex2, double capacity, double lowerBoundCapacity) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.capacity = capacity;
            this.lowerBoundCapacity = lowerBoundCapacity;
            this.flow = 0;
        }

        public int from() {
            return vertex1;
        }

        public int to() {
            return vertex2;
        }

        public double capacity() {
            return capacity;
        }

        public double lowerBoundCapacity() {
            return lowerBoundCapacity;
        }

        public double flow() {
            return flow;
        }

        public int other(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            } else if (vertex == vertex2) {
                return vertex1;
            } else {
                throw new IllegalArgumentException("Invalid endpoint");
            }
        }
    }

    private class NetworkFlowInformation {
        private boolean isFlowFeasible;
        private FlowNetwork flowNetwork;
        private HashMap<FlowEdge, FlowEdgeWithLowerBoundCapacity> edgeInformation;

        NetworkFlowInformation(boolean isFlowFeasible, FlowNetwork flowNetwork,
                               HashMap<FlowEdge, FlowEdgeWithLowerBoundCapacity> edgeInformation) {
            this.isFlowFeasible = isFlowFeasible;
            this.flowNetwork = flowNetwork;
            this.edgeInformation = edgeInformation;
        }
    }

    // Deal with floating-point roundoff errors
    private static final double FLOATING_POINT_EPSILON = 1E-6;

    public void maxFlowLowerBoundCapacities(int vertices, FlowEdgeWithLowerBoundCapacity[] edges,
                                            int source, int target) {
        NetworkFlowInformation maxFlowNetworkFlowInformation = isFlowFeasible(vertices, edges, source, target, 0);

        if (!maxFlowNetworkFlowInformation.isFlowFeasible) {
            System.out.println("\nFlow is not feasible in this network");
            return;
        }

        // Binary search to find the max flow
        double low = 0;
        double high = Double.MAX_VALUE;
        double maxFlow = 0;

        while (low + FLOATING_POINT_EPSILON < high) {
            double middle = low + (high - low) / 2;

            NetworkFlowInformation networkFlowInformation = isFlowFeasible(vertices, edges, source, target, middle);
            if (networkFlowInformation.isFlowFeasible) {
                low = middle;
                maxFlow = middle;
                maxFlowNetworkFlowInformation = networkFlowInformation;
            } else {
                high = middle;
            }
        }

        printMaxFlow(maxFlowNetworkFlowInformation, maxFlow, source, target);
    }

    private NetworkFlowInformation isFlowFeasible(int vertices, FlowEdgeWithLowerBoundCapacity[] edges, int source,
                                                  int target, double lowerBoundCapacitySearch) {
        FlowNetwork flowNetwork = new FlowNetwork(vertices + 2);

        int newSource = vertices;
        int newTarget = vertices + 1;

        double[] totalIngoingDemand = new double[vertices];
        double[] totalOutgoingDemand = new double[vertices];
        double saturatingFlow = 0;
        HashMap<FlowEdge, FlowEdgeWithLowerBoundCapacity> originalEdgeInformation = new HashMap<>();

        // Update all capacities
        for (FlowEdgeWithLowerBoundCapacity edge : edges) {
            if (edge.vertex1 == target && edge.vertex2 == source) {
                continue;
            }

            double newCapacity = edge.capacity - edge.lowerBoundCapacity;

            FlowEdge newEdge = new FlowEdge(edge.vertex1, edge.vertex2, newCapacity);
            flowNetwork.addEdge(newEdge);
            originalEdgeInformation.put(newEdge, edge);

            totalOutgoingDemand[edge.vertex1] += edge.lowerBoundCapacity;
            totalIngoingDemand[edge.vertex2] += edge.lowerBoundCapacity;
        }
        totalOutgoingDemand[target] += lowerBoundCapacitySearch;
        totalIngoingDemand[source] += lowerBoundCapacitySearch;

        // Add edges from the new source and to the new target
        for (int vertex = 0; vertex < vertices; vertex++) {
            double totalDemand = totalIngoingDemand[vertex] - totalOutgoingDemand[vertex];

            if (totalDemand > 0) {
                flowNetwork.addEdge(new FlowEdge(newSource, vertex, totalDemand));
                saturatingFlow += totalDemand;
            } else {
                flowNetwork.addEdge(new FlowEdge(vertex, newTarget, -totalDemand));
            }
        }

        flowNetwork.addEdge(new FlowEdge(target, source, Double.POSITIVE_INFINITY));

        // Check if a flow is feasible: if max flow in the new flow network is equal to the saturating flow
        EdmondsKarp edmondsKarp = new EdmondsKarp(flowNetwork, newSource, newTarget);
        boolean isFlowFeasible = edmondsKarp.maxFlowValue() == saturatingFlow;

        return new NetworkFlowInformation(isFlowFeasible, flowNetwork, originalEdgeInformation);
    }

    private void printMaxFlow(NetworkFlowInformation networkFlowInformation, double maxFlowValue, int source,
                              int target) {
        FlowNetwork flowNetwork = networkFlowInformation.flowNetwork;
        HashMap<FlowEdge, FlowEdgeWithLowerBoundCapacity> originalEdges = networkFlowInformation.edgeInformation;

        System.out.println("\nMax flow from " + source + " to " + target);

        for (int vertex = 0; vertex < flowNetwork.vertices(); vertex++) {
            for (FlowEdge edge : flowNetwork.adjacent(vertex)) {
                FlowEdgeWithLowerBoundCapacity originalEdge = originalEdges.get(edge);

                // Filter invalid edges
                if (originalEdge == null) {
                    continue;
                }

                FlowEdge adjustedEdge = new FlowEdge(edge.from(), edge.to(), originalEdge.capacity());
                adjustedEdge.setFlow(edge.flow() + originalEdge.lowerBoundCapacity());

                if (vertex == adjustedEdge.from() && adjustedEdge.flow() > 0) {
                    System.out.println("    " + adjustedEdge);
                }
            }
        }

        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        decimalFormat.setRoundingMode(RoundingMode.CEILING);
        System.out.println("Max flow value = " + decimalFormat.format(maxFlowValue));
    }

}
