package algorithms.graph.shortest.path;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by rene on 09/12/17.
 */
// O(V * E lg V)
public class JohnsonsAlgorithm {

    private double[][] distances;
    private DirectedEdge[][] edgeTo;

    JohnsonsAlgorithm(EdgeWeightedDigraph edgeWeightedDigraph) {
        distances = new double[edgeWeightedDigraph.vertices()][edgeWeightedDigraph.vertices()];
        edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()][edgeWeightedDigraph.vertices()];
        double[] newWeight = new double[edgeWeightedDigraph.vertices()];

        // 0 - Initialize all distances to Double.POSITIVE_INFINITY
        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            for(int neighbor = 0; neighbor < edgeWeightedDigraph.vertices(); neighbor++) {
                distances[vertex][neighbor] = Double.POSITIVE_INFINITY;
            }
        }

        // 1- Add a new vertex to the graph, connected to all other vertices through edges of weight 0
        // O(V + E)
        EdgeWeightedDigraph edgeWeightedDigraphWithSource = new EdgeWeightedDigraph(edgeWeightedDigraph.vertices() + 1);
        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                edgeWeightedDigraphWithSource.addEdge(edge);
            }
        }

        int newVertexId = edgeWeightedDigraph.vertices();

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            edgeWeightedDigraphWithSource.addEdge(new DirectedEdge(newVertexId, vertex, 0));
        }

        // 2- Run Bellman-Ford to get the distances from the new vertex to every other vertex.
        // Also check if there is any negative cycle
        // O(V * E)
        BellmanFord bellmanFord = new BellmanFord(edgeWeightedDigraphWithSource, newVertexId);

        if (bellmanFord.hasNegativeCycle()) {
            throw new IllegalArgumentException("Graph has a negative cycle");
        }

        // 3- Compute new weights, which are the distance from the new vertex to every other vertex
        // O(V)
        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            newWeight[vertex] = bellmanFord.distTo(vertex);
        }

        // 4- Generate a new graph with the new weights
        // O(V + E)
        EdgeWeightedDigraph edgeWeightedDigraphWithNewWeights = new EdgeWeightedDigraph(edgeWeightedDigraph.vertices());
        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                double edgeWeight = edge.weight() + newWeight[edge.from()] - newWeight[edge.to()];
                edgeWeightedDigraphWithNewWeights.addEdge(new DirectedEdge(edge.from(), edge.to(), edgeWeight));
            }
        }

        // 5- Run Dijkstra to compute all pairs shortest paths on the new graph.
        // Also compute the real all-pairs-shortest-path distances by adjusting the new weights
        // O(V * E lg V) + O(V^2) = O(V * E lg V)
        for(int source = 0; source < edgeWeightedDigraph.vertices(); source++) {
            Dijkstra dijkstra = new Dijkstra(edgeWeightedDigraphWithNewWeights, source);

            for(int target = 0; target < edgeWeightedDigraph.vertices(); target++) {
                double realShortestPathDistance = dijkstra.distTo(target) - newWeight[source] + newWeight[target];
                distances[source][target] = realShortestPathDistance;

                DirectedEdge currentEdge = dijkstra.edgeTo(target);

                if (currentEdge == null) {
                    continue;
                }

                int vertexFrom = currentEdge.from();
                int vertexTo = currentEdge.to();
                double realWeight = currentEdge.weight() - newWeight[vertexFrom] + newWeight[vertexTo];

                DirectedEdge realEdgeTo = new DirectedEdge(vertexFrom, vertexTo, realWeight);
                edgeTo[source][target] = realEdgeTo;
            }
        }
    }

    public Iterable<DirectedEdge> path(int source, int target) {
        if (!hasPath(source, target)) {
            return null;
        }

        Deque<DirectedEdge> path = new ArrayDeque<>();
        for(DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.from()]) {
            path.push(edge);
        }

        return path;
    }

    public double dist(int source, int target) {
        return distances[source][target];
    }

    public boolean hasPath(int source, int target) {
        return distances[source][target] != Double.POSITIVE_INFINITY;
    }

}
