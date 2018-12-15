package algorithms.graph.network.flow;

/**
 * Created by Rene Argento on 22/10/18.
 */
public class MaxFlowVertexCapacities {

    public void maxFlowVertexCapacities(FlowNetwork originalFlowNetwork, int source, int target,
                                        double[] vertexCapacities) {
        int originalNetworkSize = originalFlowNetwork.vertices();
        FlowNetwork flowNetwork = new FlowNetwork(originalNetworkSize * 2);

        // Transform vertex capacities into intermediate edges
        for (int i = 0; i < vertexCapacities.length; i++) {
            flowNetwork.addEdge(new FlowEdge(i, i + originalNetworkSize, vertexCapacities[i]));
        }

        // Add original edges
        for (FlowEdge edge : originalFlowNetwork.edges()) {
            int newOrigin = edge.from() + originalNetworkSize;
            flowNetwork.addEdge(new FlowEdge(newOrigin, edge.to(), edge.capacity()));
        }

        EdmondsKarp edmondsKarp = new EdmondsKarp(flowNetwork, source, target);
        printMaxFlow(flowNetwork, edmondsKarp.maxFlowValue(), source, target);
    }

    private void printMaxFlow(FlowNetwork flowNetwork, double maxFlowValue, int source, int target) {
        System.out.println("\nMax flow from " + source + " to " + target);

        for (int vertex = 0; vertex < flowNetwork.vertices(); vertex++) {
            for (FlowEdge edge : flowNetwork.adjacent(vertex)) {
                if (vertex == edge.from() && edge.flow() > 0) {
                    System.out.println("    " + edge);
                }
            }
        }
        System.out.println("Max flow value = " + maxFlowValue);
    }

}
