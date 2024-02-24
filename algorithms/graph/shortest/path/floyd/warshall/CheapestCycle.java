package algorithms.graph.shortest.path.floyd.warshall;

import datastructures.graph.DirectedEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 23/02/24.
 */
// Given a graph, find the weight of the cheapest non-negative cycle. Also known as the girth of the graph.
// Time complexity: O(V^3), should be used only for small graphs
public class CheapestCycle {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<DirectedEdge>[] edgeWeightedDigraph = new List[5];
        for (int i = 0; i < edgeWeightedDigraph.length; i++) {
            edgeWeightedDigraph[i] = new ArrayList<>();
        }
        edgeWeightedDigraph[0].add(new DirectedEdge(0, 1, 2));
        edgeWeightedDigraph[1].add(new DirectedEdge(1, 2, 5));
        edgeWeightedDigraph[2].add(new DirectedEdge(2, 3, 3));
        edgeWeightedDigraph[3].add(new DirectedEdge(3, 4, 6));
        edgeWeightedDigraph[4].add(new DirectedEdge(4, 0, 5));
        edgeWeightedDigraph[3].add(new DirectedEdge(3, 1, 1));

        double cheapestCycle = cheapestCycle(edgeWeightedDigraph);
        System.out.print("Cheapest Cycle: ");
        if (cheapestCycle == -1) {
            System.out.print("Negative cycle found.");
        } else if (cheapestCycle == Double.POSITIVE_INFINITY) {
            System.out.print("No cycles exist.");
        } else {
            System.out.print(cheapestCycle);
        }
        System.out.println(" Expected: 9.0");
    }

    public static double cheapestCycle(List<DirectedEdge>[] edgeWeightedDigraph) {
        int vertices = edgeWeightedDigraph.length;
        double[][] distances = new double[vertices][vertices];
        for (double[] values : distances) {
            Arrays.fill(values, Double.POSITIVE_INFINITY);
        }

        for (int vertexID = 0; vertexID < vertices; vertexID++) {
            for (DirectedEdge edge : edgeWeightedDigraph[vertexID]) {
                distances[vertexID][edge.to()] = edge.weight();
            }
        }

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    distances[vertex2][vertex3] = Math.min(distances[vertex2][vertex3],
                            distances[vertex2][vertex1] + distances[vertex1][vertex3]);
                }
            }
        }

        double cheapestCycle = Double.POSITIVE_INFINITY;
        for (int vertexID = 0; vertexID < vertices; vertexID++) {
            if (distances[vertexID][vertexID] < 0) {
                // Negative cycle found
                return -1;
            }
            cheapestCycle = Math.min(cheapestCycle, distances[vertexID][vertexID]);
        }
        return cheapestCycle;
    }
}
