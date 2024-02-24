package algorithms.graph.shortest.path.floyd.warshall;

import datastructures.graph.DirectedEdge;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 23/02/24.
 */
// Given a graph, determine if vertices i and j are connected, directly or indirectly.
// Time complexity: O(V^3), should be used only for small graphs
public class TransitiveClosure {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<DirectedEdge>[] edgeWeightedDigraph = new List[5];
        for (int i = 0; i < edgeWeightedDigraph.length; i++) {
            edgeWeightedDigraph[i] = new ArrayList<>();
        }
        edgeWeightedDigraph[0].add(new DirectedEdge(0, 1, 2));
        edgeWeightedDigraph[1].add(new DirectedEdge(1, 2, 2));
        edgeWeightedDigraph[2].add(new DirectedEdge(2, 3, 3));
        edgeWeightedDigraph[3].add(new DirectedEdge(3, 4, 3));

        boolean[][] transitiveClosure = transitiveClosure(edgeWeightedDigraph);
        System.out.println("Connected 1: " + transitiveClosure[0][4] + " Expected: true");
        System.out.println("Connected 2: " + transitiveClosure[2][1] + " Expected: false");
    }

    public static boolean[][] transitiveClosure(List<DirectedEdge>[] edgeWeightedDigraph) {
        int vertices = edgeWeightedDigraph.length;
        boolean[][] transitiveClosure = new boolean[vertices][vertices];

        for (int vertexID = 0; vertexID < vertices; vertexID++) {
            for (DirectedEdge edge : edgeWeightedDigraph[vertexID]) {
                transitiveClosure[vertexID][edge.to()] = true;
            }
            transitiveClosure[vertexID][vertexID] = true;
        }

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    transitiveClosure[vertex2][vertex3] |=
                            (transitiveClosure[vertex2][vertex1] & transitiveClosure[vertex1][vertex3]);
                }
            }
        }
        return transitiveClosure;
    }
}
