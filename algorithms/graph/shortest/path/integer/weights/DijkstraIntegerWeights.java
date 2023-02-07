package algorithms.graph.shortest.path.integer.weights;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 25/12/17.
 */
@SuppressWarnings("unchecked")
// O(E + K * V) = O(E + V), since K is a constant
// where K = maxWeight
// Based on https://cs.stackexchange.com/questions/6797/modifying-dijkstras-algorithm-for-edge-weights-drawn-from-range-1-k
// and https://courses.csail.mit.edu/6.006/spring11/rec/rec16.pdf
public class DijkstraIntegerWeights {

    private final DirectedEdge[] edgeTo;  // last edge on path to vertex
    private final long[] distTo;          // length of path to vertex

    // Circular array
    // The possible distances from the last computed shortest distance D to any vertex v at any time are
    // [D + 0, ..., D + maxWeight]
    private final ArrayList<Integer>[] distances;

    public DijkstraIntegerWeights(EdgeWeightedDigraph edgeWeightedDigraph, int source, int maxWeight) {
        edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];
        distTo = new long[edgeWeightedDigraph.vertices()];
        distances = (ArrayList<Integer>[]) new ArrayList[maxWeight + 1];

        for (int distance = 0; distance < distances.length; distance++) {
            distances[distance] = new ArrayList<>();
        }
        Arrays.fill(distTo, Long.MAX_VALUE);
        distTo[source] = 0;
        int nextVertexToRelax = source;

        while (nextVertexToRelax != -1) {
            relax(edgeWeightedDigraph, nextVertexToRelax);

            int lastComputedShortestDistance = (int) (distTo[nextVertexToRelax] % distances.length);
            nextVertexToRelax = getShortestDistanceVertex(lastComputedShortestDistance);
        }
    }

    // Total runtime in the entire algorithm of O(E)
    private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
        for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            Integer neighbor = edge.to();

            if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                if (distTo[neighbor] != Long.MAX_VALUE) {
                    int distancesIndex = (int) (distTo[neighbor] % distances.length);
                    distances[distancesIndex].remove(neighbor);
                }

                distTo[neighbor] = distTo[vertex] + (int) edge.weight();
                edgeTo[neighbor] = edge;

                int distancesIndex = (int) (distTo[neighbor] % distances.length);
                distances[distancesIndex].add(neighbor);
            }
        }
    }

    // Total runtime in the entire algorithm of O(V * K)
    private int getShortestDistanceVertex(int lastComputedShortestDistance) {
        int initialIndex = lastComputedShortestDistance;

        while (distances[lastComputedShortestDistance].isEmpty()) {
            lastComputedShortestDistance = (lastComputedShortestDistance + 1) % distances.length;

            if (distances[lastComputedShortestDistance].isEmpty()
                    && lastComputedShortestDistance == initialIndex) {
                return -1;
            }
        }

        Integer vertexToRemove = distances[lastComputedShortestDistance].get(0);
        distances[lastComputedShortestDistance].remove(vertexToRemove);
        return vertexToRemove;
    }

    public long distTo(int vertex) {
        return distTo[vertex];
    }

    public DirectedEdge edgeTo(int vertex) {
        return edgeTo[vertex];
    }

    public boolean hasPathTo(int vertex) {
        return distTo[vertex] != Long.MAX_VALUE;
    }

    public Iterable<DirectedEdge> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }

        Deque<DirectedEdge> path = new ArrayDeque<>();
        for (DirectedEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
            path.push(edge);
        }
        return path;
    }

    // Can be tested in https://www.hackerrank.com/challenges/dijkstrashortreach/problem
    public static void main(String[] args) throws IOException {
        FastReader.init();
        int tests = FastReader.nextInt();

        for (int test = 0; test < tests; test++) {
            int vertices = FastReader.nextInt();
            int totalEdges = FastReader.nextInt();

            EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(vertices + 1);

            for (int i = 0; i < totalEdges; i++) {
                int vertex1 = FastReader.nextInt();
                int vertex2 = FastReader.nextInt();
                int length = FastReader.nextInt();

                DirectedEdge edge1 = new DirectedEdge(vertex1, vertex2, length);
                DirectedEdge edge2 = new DirectedEdge(vertex2, vertex1, length);

                edgeWeightedDigraph.addEdge(edge1);
                edgeWeightedDigraph.addEdge(edge2);
            }

            int source = FastReader.nextInt();
            int maxWeight = 100000;

            DijkstraIntegerWeights dijkstraIntegerWeights =
                    new DijkstraIntegerWeights(edgeWeightedDigraph, source, maxWeight);

            for (int vertex = 1; vertex <= vertices; vertex++) {
                if (vertex != source) {
                    long distance = dijkstraIntegerWeights.distTo(vertex);
                    if (distance == Long.MAX_VALUE) {
                        distance = -1;
                    }
                    System.out.print(distance);

                    if (vertex != vertices) {
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
