package algorithms.graph.shortest.path.floyd.warshall;

import datastructures.graph.DirectedEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 23/02/24.
 */
// Given a directed graph, determine if vertices i and j are in the same strongly connected component (SCC),
// or the list of all vertices in the same SCC as vertex i.
// Time complexity: O(V^3), should be used only for small graphs
public class SameSCC {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<DirectedEdge>[] edgeWeightedDigraph = new List[5];
        for (int i = 0; i < edgeWeightedDigraph.length; i++) {
            edgeWeightedDigraph[i] = new ArrayList<>();
        }
        edgeWeightedDigraph[0].add(new DirectedEdge(0, 3, 2));
        edgeWeightedDigraph[1].add(new DirectedEdge(1, 2, 2));
        edgeWeightedDigraph[2].add(new DirectedEdge(2, 3, 3));
        edgeWeightedDigraph[3].add(new DirectedEdge(3, 4, 3));
        edgeWeightedDigraph[4].add(new DirectedEdge(4, 0, 3));

        boolean[][] transitiveClosure = transitiveClosure(edgeWeightedDigraph);
        boolean sameSCC1 = areVerticesInSameSCC(transitiveClosure, 0, 3);
        System.out.println("Vertices 0 and 3 in same SCC: " + sameSCC1 + " Expected: true");
        boolean sameSCC2 = areVerticesInSameSCC(transitiveClosure, 1, 2);
        System.out.println("Vertices 1 and 2 in same SCC: " + sameSCC2 + " Expected: false");

        List<Integer> verticesInSCC = getAllVerticesInSCC(transitiveClosure, 0);
        System.out.print("Vertices in SCC with vertex 0:");
        for (int vertexID : verticesInSCC) {
            System.out.print(" " + vertexID);
        }
        System.out.println(" Expected: 0 3 4");
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

    private static boolean areVerticesInSameSCC(boolean[][] transitiveClosure, int vertexID1, int vertexID2) {
        return transitiveClosure[vertexID1][vertexID2] && transitiveClosure[vertexID2][vertexID1];
    }

    private static List<Integer> getAllVerticesInSCC(boolean[][] transitiveClosure, int vertexID) {
        List<Integer> verticesInSCC = new ArrayList<>();

        for (int vertexID2 = 0; vertexID2 < transitiveClosure.length; vertexID2++) {
            if (transitiveClosure[vertexID][vertexID2] && transitiveClosure[vertexID2][vertexID]) {
                verticesInSCC.add(vertexID2);
            }
        }
        return verticesInSCC;
    }
}
