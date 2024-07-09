package algorithms.graph.dag;

import java.util.*;

/**
 * Created by Rene Argento on 27/11/17.
 */
// Computes shortest paths in O(E + V) when the graph is a DAG
public class DAGShortestPath {

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

    private static Edge[] edgeTo;
    private static double[] distTo;

    public DAGShortestPath(List<Edge>[] adjacent, int source) {
        edgeTo = new Edge[adjacent.length];
        distTo = new double[adjacent.length];
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[source] = 0;

        int[] topological = topologicalSort(adjacent);
        for (int vertex : topological) {
            relax(adjacent, vertex);
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
