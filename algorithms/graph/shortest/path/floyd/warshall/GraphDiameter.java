package algorithms.graph.shortest.path.floyd.warshall;

import datastructures.graph.DirectedEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 23/02/24.
 */
// Given a graph, find the diameter of the graph: the longest shortest path between all pair of vertices.
// Time complexity: O(V^3), should be used only for small graphs
public class GraphDiameter {

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
        edgeWeightedDigraph[3].add(new DirectedEdge(3, 2, 4));
        edgeWeightedDigraph[3].add(new DirectedEdge(4, 3, 1));

        double diameter = computeDiameter(edgeWeightedDigraph);
        System.out.print("Diameter: ");
        if (diameter == -1) {
            System.out.print("Negative cycle found.");
        } else {
            System.out.print(diameter);
        }
        System.out.println(" Expected: 19.0");
    }

    public static double computeDiameter(List<DirectedEdge>[] edgeWeightedDigraph) {
        int vertices = edgeWeightedDigraph.length;
        double[][] distances = new double[vertices][vertices];
        for (double[] values : distances) {
            Arrays.fill(values, Double.POSITIVE_INFINITY);
        }

        for (int vertexID = 0; vertexID < vertices; vertexID++) {
            for (DirectedEdge edge : edgeWeightedDigraph[vertexID]) {
                distances[vertexID][edge.to()] = edge.weight();
            }
            distances[vertexID][vertexID] = 0;
        }

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    distances[vertex2][vertex3] = Math.min(distances[vertex2][vertex3],
                            distances[vertex2][vertex1] + distances[vertex1][vertex3]);
                }
            }
        }

        double diameter = -1;
        for (int vertexID1 = 0; vertexID1 < vertices; vertexID1++) {
            if (distances[vertexID1][vertexID1] < 0.0) {
                // Negative cycle found
                return -1;
            }

            for (int vertexID2 = 0; vertexID2 < vertices; vertexID2++) {
                if (distances[vertexID1][vertexID2] == Double.POSITIVE_INFINITY) {
                    continue;
                }
                diameter = Math.max(diameter, distances[vertexID1][vertexID2]);
            }
        }
        return diameter;
    }
}
