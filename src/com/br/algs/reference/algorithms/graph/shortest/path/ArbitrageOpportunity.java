package com.br.algs.reference.algorithms.graph.shortest.path;

import com.br.algs.reference.datastructures.DirectedEdge;
import com.br.algs.reference.datastructures.EdgeWeightedDigraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 28/11/17.
 */
@SuppressWarnings("unchecked")
public class ArbitrageOpportunity {

    public List<DirectedEdge> getArbitrage(List<DirectedEdge>[] adjacent) {

        EdgeWeightedDigraph graphWithNegatedLogEdges = new EdgeWeightedDigraph(adjacent.length);

        for(int vertex = 0; vertex < adjacent.length; vertex++) {

            for(DirectedEdge edge : adjacent[vertex]) {
                double weight = edge.weight();
                DirectedEdge negatedLogEdge = new DirectedEdge(vertex, edge.to(), -Math.log(weight));
                graphWithNegatedLogEdges.addEdge(negatedLogEdge);
            }
        }

        BellmanFord bellmanFord = new BellmanFord(graphWithNegatedLogEdges, 0);

        if(bellmanFord.hasNegativeCycle()) {
            List<DirectedEdge> arbitrageOpportunity = new ArrayList<>();

            for(DirectedEdge edge : bellmanFord.negativeCycle()) {
                double weight = edge.weight();
                DirectedEdge originalEdge = new DirectedEdge(edge.from(), edge.to(), Math.exp(-weight));
                arbitrageOpportunity.add(originalEdge);
            }

            return arbitrageOpportunity;
        } else {
            return null;
        }
    }

}
