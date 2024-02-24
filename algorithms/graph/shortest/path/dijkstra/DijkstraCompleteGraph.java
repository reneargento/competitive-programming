package algorithms.graph.shortest.path.dijkstra;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.Arrays;

/**
 * Created by Rene Argento on 02/12/23.
 */
// Computes the shortest paths from a source vertex to all other vertices in O(V^2).
// This is faster than other approaches when the graph is complete or dense.
public class DijkstraCompleteGraph {

    public static double[] dijkstraCompleteGraph(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
        double[] shortestDistances = new double[edgeWeightedDigraph.vertices()];
        Arrays.fill(shortestDistances, Double.MAX_VALUE);
        boolean[] visited = new boolean[edgeWeightedDigraph.vertices()];

        shortestDistances[source] = 0;

        while (true) {
            int nextVertexID = -1;
            double nextShortestDistance = Double.MAX_VALUE;

            for (int vertexID = 0; vertexID < edgeWeightedDigraph.vertices(); vertexID++) {
                if (!visited[vertexID] && shortestDistances[vertexID] < nextShortestDistance) {
                    nextShortestDistance = shortestDistances[vertexID];
                    nextVertexID = vertexID;
                }
            }

            if (nextVertexID == -1) {
                break;
            }

            // Relax edges
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(nextVertexID)) {
                double totalDistance = shortestDistances[nextVertexID] + edge.weight();
                if (shortestDistances[edge.to()] > totalDistance) {
                    shortestDistances[edge.to()] = totalDistance;
                }
            }
            visited[nextVertexID] = true;
        }
        return shortestDistances;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(4);
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 1, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 2, 6));
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 3, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 0, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 2, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 3, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 0, 6));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 1, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 3, 0));
        edgeWeightedDigraph.addEdge(new DirectedEdge(3, 0, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(3, 1, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(3, 2, 0));

        double[] shortestDistances = dijkstraCompleteGraph(edgeWeightedDigraph, 0);

        System.out.println("Shortest distances:");
        for (int vertexID = 1; vertexID < edgeWeightedDigraph.vertices(); vertexID++) {
            System.out.printf("0->%d %.2f ", vertexID, shortestDistances[vertexID]);
        }
        System.out.println("\nExpected:");
        System.out.println("0->1 1.00 0->2 3.00 0->3 3.00");
    }
}
