package algorithms.graph.shortest.path;

import algorithms.graph.cycle.detection.EdgeWeightedDirectedCycle;
import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by rene on 29/04/17.
 */
public class FloydWarshall {

    private static double[][] distances;     // length of shortest v->w path
    private static DirectedEdge[][] edgeTo;  // last edge on shortest v->w path

    private static boolean hasNegativeCycle;

    public FloydWarshall(double[][] edgeWeightedDigraph) {
        int vertices = edgeWeightedDigraph.length;

        distances = new double[vertices][vertices];
        edgeTo = new DirectedEdge[vertices][vertices];

        // Initialize distances using edge-weighted digraph's
        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                double distance = edgeWeightedDigraph[vertex1][vertex2];

                distances[vertex1][vertex2] = distance;
                edgeTo[vertex1][vertex2] = new DirectedEdge(vertex1, vertex2, distance);
            }

            // In case of self-loops
            if (distances[vertex1][vertex1] >= 0.0) {
                distances[vertex1][vertex1] = 0.0;
                edgeTo[vertex1][vertex1] = null;
            }
        }

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {

                if (edgeTo[vertex2][vertex1] == null) {
                    continue;  // optimization
                }

                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                        edgeTo[vertex2][vertex3] = edgeTo[vertex1][vertex3];
                    }
                }

                if (distances[vertex2][vertex2] < 0.0) {
                    hasNegativeCycle = true;
                    return;
                }
            }
        }

    }

    public static double distance(int source, int target) {
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }

        return distances[source][target];
    }

    public Iterable<DirectedEdge> path(int source, int target) {
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        if (!hasPath(source, target)) {
            return null;
        }

        Deque<DirectedEdge> path = new ArrayDeque<>();
        for(DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.from()]) {
            path.push(edge);
        }

        return path;
    }

    public static boolean hasPath(int source, int target) {
        return distances[source][target] != Double.POSITIVE_INFINITY;
    }

    public static boolean hasNegativeCycle() {
        return hasNegativeCycle;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        for (int vertex = 0; vertex < distances.length; vertex++) {

            // Negative cycle in vertex's predecessor graph
            if (distances[vertex][vertex] < 0.0) {
                int vertices = edgeTo.length;
                EdgeWeightedDigraph shortestPathTree = new EdgeWeightedDigraph(vertices);

                for (int otherVertex = 0; otherVertex < vertices; otherVertex++) {
                    if (edgeTo[vertex][otherVertex] != null) {
                        shortestPathTree.addEdge(edgeTo[vertex][otherVertex]);
                    }
                }

                EdgeWeightedDirectedCycle cycleFinder = new EdgeWeightedDirectedCycle(shortestPathTree);
                return cycleFinder.cycle();
            }
        }

        return null;
    }

}
