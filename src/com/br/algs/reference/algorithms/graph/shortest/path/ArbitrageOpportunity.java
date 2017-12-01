package com.br.algs.reference.algorithms.graph.shortest.path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 28/11/17.
 */
@SuppressWarnings("unchecked")
public class ArbitrageOpportunity {

    public List<BellmanFord.Edge> getArbitrage(List<BellmanFord.Edge>[] adjacent) {

        List<BellmanFord.Edge>[] graphWithNegatedLogEdges = (List<BellmanFord.Edge>[]) new ArrayList[adjacent.length];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {

            for(BellmanFord.Edge edge : adjacent[vertex]) {
                double weight = edge.weight;
                BellmanFord.Edge negatedLogEdge = new BellmanFord.Edge(vertex, edge.vertex2, -Math.log(weight));
                graphWithNegatedLogEdges[vertex].add(negatedLogEdge);
            }
        }

        BellmanFord bellmanFord = new BellmanFord(graphWithNegatedLogEdges, 0);

        if(bellmanFord.hasNegativeCycle()) {
            List<BellmanFord.Edge> arbitrageOpportunity = new ArrayList<>();

            for(BellmanFord.Edge edge : bellmanFord.negativeCycle()) {
                double weight = edge.weight;
                BellmanFord.Edge originalEdge = new BellmanFord.Edge(edge.vertex1, edge.vertex2, Math.exp(-weight));
                arbitrageOpportunity.add(originalEdge);
            }

            return arbitrageOpportunity;
        } else {
            return null;
        }
    }

}
