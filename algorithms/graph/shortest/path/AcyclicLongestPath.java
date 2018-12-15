package algorithms.graph.shortest.path;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by rene on 28/11/17.
 */
// Computes longest paths in O(E + V) when the graph is acyclic
@SuppressWarnings("unchecked")
public class AcyclicLongestPath {

    private static class Edge {
        int vertex1;
        int vertex2;
        double weight;

        Edge(int vertex1, int vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    public static class TopologicalSort {

        private static int[] topologicalSort(List<Edge>[] adjacent) {
            Stack<Integer> finishTimes = getFinishTimes(adjacent);

            int[] topologicalSort = new int[finishTimes.size()];
            int arrayIndex = 0;

            while (!finishTimes.isEmpty()) {
                topologicalSort[arrayIndex++] = finishTimes.pop();
            }

            return topologicalSort;
        }

        private static Stack<Integer> getFinishTimes(List<Edge>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            Stack<Integer> finishTimes = new Stack<>();

            //If the vertices are 0-index based, start i with value 0
            for(int i = 1; i < adjacent.length; i++) {
                if (!visited[i]) {
                    depthFirstSearch(i, adjacent, finishTimes, visited);
                }
            }

            return finishTimes;
        }

        private static void depthFirstSearch(int sourceVertex, List<Edge>[] adj, Stack<Integer> finishTimes,
                                             boolean[] visited) {
            visited[sourceVertex] = true;

            for(Edge edge : adj[sourceVertex]) {
                int neighbor = edge.vertex2;

                if (!visited[neighbor]) {
                    depthFirstSearch(neighbor, adj, finishTimes, visited);
                }
            }

            finishTimes.push(sourceVertex);
        }
    }

    private static Edge[] edgeTo;
    private static double[] distTo;

    public AcyclicLongestPath(List<Edge>[] adjacent, int source) {
        // To compute the longest paths, negate all edge weights in the graph and then compute the shortest paths
        List<Edge>[] graphWithNegatedWeights = (List<Edge>[]) new ArrayList[adjacent.length];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            graphWithNegatedWeights[vertex] = new ArrayList<>();

            for(Edge edge : adjacent[vertex]) {
                Edge negatedEdge = new Edge(edge.vertex1, edge.vertex2, edge.weight * -1);
                graphWithNegatedWeights[vertex].add(negatedEdge);
            }
        }

        edgeTo = new Edge[graphWithNegatedWeights.length];
        distTo = new double[graphWithNegatedWeights.length];

        for(int vertex = 0; vertex < graphWithNegatedWeights.length; vertex++) {
            distTo[vertex] = Double.POSITIVE_INFINITY;
        }

        distTo[source] = 0;

        int[] topological = TopologicalSort.topologicalSort(graphWithNegatedWeights);

        for(int vertex : topological) {
            relax(graphWithNegatedWeights, vertex);
        }
    }

    private void relax(List<Edge> adjacent[], int vertex) {

        for(Edge edge : adjacent[vertex]) {
            int neighbor = edge.vertex2;

            if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                distTo[neighbor] = distTo[vertex] + edge.weight;
                edgeTo[neighbor] = edge;
            }
        }
    }

    public static double distTo(int vertex) {
        return distTo[vertex];
    }

    public static boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }

    public static Iterable<Edge> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }

        Stack<Edge> path = new Stack<>();
        for(Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
            path.push(edge);
        }

        return path;
    }

    public Iterable<Edge> getLongestPath() {

        int furthestVertex = -1;
        double longestDistance = Double.NEGATIVE_INFINITY;

        for(int vertex = 0; vertex < edgeTo.length; vertex++) {
            if (distTo(vertex) > longestDistance) {
                longestDistance = distTo(vertex);
                furthestVertex = vertex;
            }
        }

        return pathTo(furthestVertex);
    }

}
