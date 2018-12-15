package algorithms.graph.network.flow;

/**
 * Created by Rene Argento on 22/10/18.
 */
public class MaxFlowMultipleSourceTargets {

    public void maxFlowMultipleSourcesTargets(FlowNetwork originalFlowNetwork, int[] sources, int[] targets) {
        if (sources == null || targets == null || sources.length == 0 || targets.length == 0) {
            throw new IllegalArgumentException("At least one source and one sink must be specified.");
        }

        FlowNetwork flowNetwork = new FlowNetwork(originalFlowNetwork.vertices() + 2);

        for (FlowEdge edge : originalFlowNetwork.edges()) {
            flowNetwork.addEdge(new FlowEdge(edge.from(), edge.to(), edge.capacity()));
        }

        // Add dummy source and dummy target
        int source = originalFlowNetwork.vertices();
        int target = source + 1;

        for (int s : sources) {
            flowNetwork.addEdge(new FlowEdge(source, s, Double.POSITIVE_INFINITY));
        }

        for (int t : targets) {
            flowNetwork.addEdge(new FlowEdge(t, target, Double.POSITIVE_INFINITY));
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
