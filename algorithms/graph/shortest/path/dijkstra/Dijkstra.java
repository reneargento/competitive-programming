package algorithms.graph.shortest.path.dijkstra;

import java.util.*;

/**
 * Created by Rene Argento on 15/04/23.
 */
// Dijkstra algorithm optimized for reusability on contests.
// O((E + V) lg E)
public class Dijkstra {

    private static class Vertex implements Comparable<Vertex> {
        int id;
        long distance;

        public Vertex(int id, long distance) {
            this.id = id;
            this.distance = distance;
        }

        @Override
        public int compareTo(Vertex other) {
            return Long.compare(distance, other.distance);
        }
    }

    private static class Edge {
        private final int vertex1;
        private final int vertex2;
        private final long distance;

        public Edge(int vertex1, int vertex2, long distance) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.distance = distance;
        }
    }

    private final Edge[] edgeTo;  // last edge on path to vertex
    private final long[] distTo;  // length of path to vertex
    private final PriorityQueue<Vertex> priorityQueue;
    private final long MAX_VALUE = 10000000000000000L;

    public Dijkstra(List<Edge>[] adjacencyList, int source) {
        edgeTo = new Edge[adjacencyList.length];
        distTo = new long[adjacencyList.length];
        priorityQueue = new PriorityQueue<>(adjacencyList.length);

        Arrays.fill(distTo, MAX_VALUE);
        distTo[source] = 0;
        priorityQueue.offer(new Vertex(source, 0));

        while (!priorityQueue.isEmpty()) {
            relax(adjacencyList, priorityQueue.poll());
            // In a source-sink problem, break the loop if the sink was relaxed
        }
    }

    private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
        // Optional optimization
        if (distTo[vertex.id] < vertex.distance) {
            return;
        }

        for (Edge edge : adjacencyList[vertex.id]) {
            int neighbor = edge.vertex2;

            if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                distTo[neighbor] = distTo[vertex.id] + edge.distance;
                edgeTo[neighbor] = edge;
                priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
            }
        }
    }

    public boolean hasPathTo(int vertex) {
        return distTo[vertex] != MAX_VALUE;
    }

    public List<Edge> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }

        List<Edge> path = new ArrayList<>();
        for (Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
            path.add(edge);
        }
        Collections.reverse(path);
        return path;
    }
}
