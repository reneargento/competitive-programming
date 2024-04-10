package algorithms.graph.shortest.path.floyd.warshall;

import datastructures.graph.DirectedEdge;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 29/03/24.
 */
// Given an undirected graph, compute a bitset of all vertices in all the shortest paths between all vertex pairs.
// Time complexity: O(V^3), should be used only for small graphs.
// Based on UVa-1198 - The Geodetic Set Problem
@SuppressWarnings("unchecked")
public class FloydWarshallAllShortestPathVertices {
    private final int[][] distances;              // length of shortest v->w path
    private final List<DirectedEdge>[][] edgeTo;  // last edge on shortest v->w path

    public FloydWarshallAllShortestPathVertices(int[][] edgeWeightedDigraph) {
        int vertices = edgeWeightedDigraph.length;
        distances = new int[vertices][vertices];
        edgeTo = new List[vertices][vertices];

        for (int vertexID1 = 0; vertexID1 < edgeTo.length; vertexID1++) {
            for (int vertexID2 = 0; vertexID2 < edgeTo.length; vertexID2++) {
                edgeTo[vertexID1][vertexID2] = new ArrayList<>();
            }
        }

        // Initialize distances using edge-weighted digraph's
        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                int distance = edgeWeightedDigraph[vertex1][vertex2];
                distances[vertex1][vertex2] = distance;

                if (vertex1 == vertex2) {
                    continue;
                }
                DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                edgeTo[vertex1][vertex2].add(edge);
            }
        }

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                        edgeTo[vertex2][vertex3] = new ArrayList<>();
                        edgeTo[vertex2][vertex3].addAll(edgeTo[vertex1][vertex3]);
                    } else if (distances[vertex2][vertex3] ==
                            distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        edgeTo[vertex2][vertex3].addAll(edgeTo[vertex1][vertex3]);
                    }
                }
            }
        }
    }

    public Long[][] allPathVertices() {
        int vertices = distances.length;
        Long[][] allPathVertices = new Long[vertices][vertices];
        long[][] allPathsDp = new long[vertices][vertices];

        for (int vertexID1 = 0; vertexID1 < distances.length; vertexID1++) {
            for (int vertexID2 = 0; vertexID2 < distances.length; vertexID2++) {
                if (vertexID2 < vertexID1) {
                    allPathVertices[vertexID1][vertexID2] = allPathVertices[vertexID2][vertexID1];
                } else {
                    allPathVertices[vertexID1][vertexID2] = verticesInPaths(vertexID1, vertexID2, allPathsDp);
                }
            }
        }
        return allPathVertices;
    }

    private long verticesInPaths(int source, int target, long[][] allPathsDp) {
        long verticesInPaths = (1L << source);
        verticesInPaths |= getPathVertices(edgeTo[source][target], allPathsDp, source);

        allPathsDp[source][target] = verticesInPaths;
        return verticesInPaths;
    }

    private long getPathVertices(List<DirectedEdge> currentEdges, long[][] allPathsDp, int source) {
        long verticesInPaths = 0L;

        if (currentEdges == null) {
            return 0;
        }
        for (DirectedEdge edge : currentEdges) {
            verticesInPaths |= (1L << edge.to());

            if (allPathsDp[source][edge.from()] != 0) {
                verticesInPaths |= allPathsDp[source][edge.from()];
            } else {
                long intermediateVertices = getPathVertices(edgeTo[source][edge.from()], allPathsDp, source);
                allPathsDp[source][edge.from()] = intermediateVertices;
                verticesInPaths |= intermediateVertices;
            }
        }
        return verticesInPaths;
    }
}
