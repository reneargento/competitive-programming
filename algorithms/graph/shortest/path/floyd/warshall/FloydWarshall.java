package algorithms.graph.shortest.path.floyd.warshall;

import algorithms.graph.cycle.detection.EdgeWeightedDirectedCycle;
import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by Rene Argento on 29/04/17.
 */
public class FloydWarshall {
    private final double[][] distances;     // length of shortest v->w path
    private final DirectedEdge[][] edgeTo;  // last edge on shortest v->w path
    private final List<DirectedEdge> edges;
    private boolean hasNegativeCycle;

    public FloydWarshall(double[][] edgeWeightedDigraph) {
        int vertices = edgeWeightedDigraph.length;
        distances = new double[vertices][vertices];
        edgeTo = new DirectedEdge[vertices][vertices];
        edges = new ArrayList<>();

        // Initialize distances using edge-weighted digraph's
        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                double distance = edgeWeightedDigraph[vertex1][vertex2];
                distances[vertex1][vertex2] = distance;
                DirectedEdge edge = new DirectedEdge(vertex1, vertex2, distance);
                edgeTo[vertex1][vertex2] = edge;
                edges.add(edge);
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

    public double distance(int source, int target) {
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        return distances[source][target];
    }

    public List<Integer> path(int source, int target) {
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        if (!hasPath(source, target)) {
            return null;
        }

        Deque<Integer> pathStack = new ArrayDeque<>();
        for (DirectedEdge edge = edgeTo[source][target]; edge != null; edge = edgeTo[source][edge.from()]) {
            pathStack.push(edge.to());
            // If a cycle exists, break when edge.from == source
        }
        pathStack.push(source);

        List<Integer> path = new ArrayList<>();
        while (!pathStack.isEmpty()) {
            path.add(pathStack.poll());
        }
        return path;
    }

    private List<DirectedEdge> getEdgesInAllShortestPaths(int source, int target) {
        if (hasNegativeCycle()) {
            throw new UnsupportedOperationException("Negative cost cycle exists");
        }
        List<DirectedEdge> edgesInAllShortestPaths = new ArrayList<>();

        for (DirectedEdge edge : edges) {
            double distanceThroughEdge = distances[source][edge.from()] + edge.weight() + distances[edge.to()][target];
            if (distanceThroughEdge == distances[source][target]) {
                edgesInAllShortestPaths.add(edge);
            }
        }
        return edgesInAllShortestPaths;
    }

    public boolean hasPath(int source, int target) {
        return distances[source][target] != Double.POSITIVE_INFINITY;
    }

    public boolean hasNegativeCycle() {
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
