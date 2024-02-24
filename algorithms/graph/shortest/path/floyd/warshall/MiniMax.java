package algorithms.graph.shortest.path.floyd.warshall;

import datastructures.graph.DirectedEdge;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 23/02/24.
 */
// Given a graph, along all paths between vertices i and j determine the minimum maximum-weight edge.
// Time complexity: O(V^3), should be used only for small graphs
public class MiniMax {

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

        double[][] miniMax = miniMax(edgeWeightedDigraph);
        System.out.println("MiniMax 1: " + miniMax[0][3] + " Expected: 5.0");
        System.out.println("MiniMax 2: " + miniMax[0][4] + " Expected: 6.0");
    }

    public static double[][] miniMax(List<DirectedEdge>[] edgeWeightedDigraph) {
        int vertices = edgeWeightedDigraph.length;
        double[][] miniMax = new double[vertices][vertices];
        for (double[] values : miniMax) {
            Arrays.fill(values, Double.POSITIVE_INFINITY);
        }

        for (int vertexID = 0; vertexID < vertices; vertexID++) {
            for (DirectedEdge edge : edgeWeightedDigraph[vertexID]) {
                miniMax[vertexID][edge.to()] = edge.weight();
            }
            miniMax[vertexID][vertexID] = 0;
        }

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    // Reverse min and max for MaxiMin problem
                    miniMax[vertex2][vertex3] = Math.min(miniMax[vertex2][vertex3],
                            Math.max(miniMax[vertex2][vertex1], miniMax[vertex1][vertex3]));
                }
            }
        }
        return miniMax;
    }
}
