package algorithms.graph.network.flow;

import datastructures.Graph;

/**
 * Created by Rene Argento on 26/10/18.
 */
public class DisjointPaths {

    public int geNumberOfDisjointPaths(Graph graph, int source, int target) {
        FlowNetwork flowNetwork = new FlowNetwork(graph.vertices());

        for (int vertex = 0; vertex < graph.vertices(); vertex++) {
            for (int neighbor : graph.adjacent(vertex)) {
                flowNetwork.addEdge(new FlowEdge(vertex, neighbor, 1));
            }
        }

        EdmondsKarp edmondsKarp = new EdmondsKarp(flowNetwork, source, target);
        return (int) edmondsKarp.maxFlowValue();
    }

}
