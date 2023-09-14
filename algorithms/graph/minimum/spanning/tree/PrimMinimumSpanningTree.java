package algorithms.graph.minimum.spanning.tree;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Rene Argento on 02/07/17.
 */
// Computes a Minimum Spanning Tree using Prim's algorithm in O(E lg V)
public class PrimMinimumSpanningTree {

    private static class Edge implements Comparable<Edge> {
        private final int vertex1;
        private final int vertex2;
        private final long weight;

        public Edge(int vertex1, int vertex2, long weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        public int other(int vertex) {
            return vertex == vertex1 ? vertex2 : vertex1;
        }

        public int compareTo(Edge that) {
            return Long.compare(weight, that.weight);
        }
    }

    protected Edge[] edgeTo; // shortest edge from tree vertex
    private final boolean[] marked; // true if vertex is on the minimum spanning tree
    private final PriorityQueue<Edge> priorityQueue; // eligible crossing edges
    private final List<Edge> edges;
    public long weight;

    public PrimMinimumSpanningTree(List<Edge>[] adjacencyList) {
        int verticesNumber = adjacencyList.length;
        edgeTo = new Edge[verticesNumber];
        marked = new boolean[verticesNumber];
        priorityQueue = new PriorityQueue<>();
        edges = new ArrayList<>();
        int verticesProcessed = 0;

        // Initialize priority queue with vertex 0.
        // Can be called multiple times in the case of multiple sources.
        process(adjacencyList, 0);
        verticesProcessed++;

        while (!priorityQueue.isEmpty() && verticesNumber != verticesProcessed) {
            Edge edge = priorityQueue.poll(); // Add closest vertex to the minimum spanning tree
            if (marked[edge.vertex2]) {
                continue;
            }

            weight += edge.weight;
            edgeTo[edge.vertex2] = edge;
            edges.add(edge);
            verticesProcessed++;

            process(adjacencyList, edge.vertex2);
        }
    }

    private void process(List<Edge>[] adjacencyList, int vertex) {
        marked[vertex] = true;

        for (Edge edge : adjacencyList[vertex]) {
            int otherVertex = edge.other(vertex);
            if (!marked[otherVertex]) {
                priorityQueue.offer(new Edge(vertex, otherVertex, edge.weight));
            }
        }
    }
}
