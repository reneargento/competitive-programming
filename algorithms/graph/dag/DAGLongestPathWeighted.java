package algorithms.graph.dag;

import java.util.*;

/**
 * Created by Rene Argento on 28/11/17.
 */
// Computes longest paths in O(E + V) given a source vertex, when the graph is a DAG
@SuppressWarnings("unchecked")
public class DAGLongestPathWeighted {

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

    private static double[] distTo;
    private static Edge[] edgeTo;

    public DAGLongestPathWeighted(List<Edge>[] adjacent, int source) {
        // To compute the longest paths, negate all edge weights in the graph and then compute the shortest paths
        List<Edge>[] graphWithNegatedWeights = (List<Edge>[]) new ArrayList[adjacent.length];

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            graphWithNegatedWeights[vertex] = new ArrayList<>();

            for (Edge edge : adjacent[vertex]) {
                Edge negatedEdge = new Edge(edge.vertex1, edge.vertex2, edge.weight * -1);
                graphWithNegatedWeights[vertex].add(negatedEdge);
            }
        }

        distTo = new double[graphWithNegatedWeights.length];
        edgeTo = new Edge[graphWithNegatedWeights.length];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[source] = 0;

        int[] topological = topologicalSort(graphWithNegatedWeights);
        for (int vertex : topological) {
            relax(graphWithNegatedWeights, vertex);
        }
    }

    private void relax(List<Edge>[] adjacent, int vertex) {
        for (Edge edge : adjacent[vertex]) {
            int neighbor = edge.vertex2;

            if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                distTo[neighbor] = distTo[vertex] + edge.weight;
                edgeTo[neighbor] = edge;
            }
        }
    }

    public double distTo(int vertex) {
        return distTo[vertex];
    }

    public boolean hasPathTo(int vertex) {
        return distTo[vertex] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }

        Stack<Edge> path = new Stack<>();
        for (Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
            path.push(edge);
        }
        return path;
    }

    public double getLongestDistance() {
        double longestDistance = Double.NEGATIVE_INFINITY;

        for (int vertex = 0; vertex < distTo.length; vertex++) {
            if (-distTo(vertex) > longestDistance) {
                longestDistance = -distTo(vertex);
            }
        }
        return longestDistance;
    }

    public Iterable<Edge> getLongestPath() {
        int furthestVertex = -1;
        double longestDistance = Double.NEGATIVE_INFINITY;

        for (int vertex = 0; vertex < distTo.length; vertex++) {
            if (-distTo(vertex) > longestDistance) {
                longestDistance = -distTo(vertex);
                furthestVertex = vertex;
            }
        }
        return pathTo(furthestVertex);
    }

    private int[] topologicalSort(List<Edge>[] adjacencyList) {
        int[] inDegrees = new int[adjacencyList.length];

        for (int i = 0; i < adjacencyList.length; i++) {
            for (Edge edge : adjacencyList[i]) {
                inDegrees[edge.vertex2]++;
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (inDegrees[vertexID] == 0) {
                queue.add(vertexID);
            }
        }

        int count = 0;

        int[] topologicalOrder = new int[adjacencyList.length];
        int topologicalOrderIndex = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();
            topologicalOrder[topologicalOrderIndex++] = currentVertex;

            for (Edge edge : adjacencyList[currentVertex]) {
                int neighbor = edge.vertex2;
                inDegrees[neighbor]--;

                if (inDegrees[neighbor] == 0) {
                    queue.add(neighbor);
                }
            }
            count++;
        }

        // Check if there was a cycle
        if (count != adjacencyList.length) {
            System.out.println("There exists a cycle in the graph");
            return null;
        }
        return topologicalOrder;
    }
}
