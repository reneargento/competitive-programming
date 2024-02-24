package algorithms.graph.shortest.path.bellman.ford;

import java.io.IOException;
import java.util.*;

/**
 * Created by Rene Argento on 08/02/24.
 */
// This algorithm uses Bellman Ford to compute for a query vertex if:
// 1- It is reachable from the source and from a negative cycle (output -Infinity)
// 2- It is reachable from the source vertex and not from any negative cycle (output its distance)
// 3- It is not reachable from the source (output Impossible)
// Time-Complexity: O(V * E)
// Practice problem: Kattis shortestpath3
public class VerticesReachableFromNegativeCycles {

    private static class Edge {
        int nextVertex;
        int weight;

        public Edge(int nextVertex, int weight) {
            this.nextVertex = nextVertex;
            this.weight = weight;
        }
    }

    private static class Result {
        long[] distances;
        boolean[] nodesReachableFromCycles;

        public Result(long[] distances, boolean[] nodesReachableFromCycles) {
            this.distances = distances;
            this.nodesReachableFromCycles = nodesReachableFromCycles;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        int vertices = 5;
        int sourceVertex = 0;
        List<Edge>[] adjacencyList = new List[vertices];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        adjacencyList[0].add(new Edge(1, 999));
        adjacencyList[1].add(new Edge(2, -2));
        adjacencyList[2].add(new Edge(1, 1));
        adjacencyList[0].add(new Edge(3, 2));

        Result result = computeDistances(adjacencyList, sourceVertex);

        // Tests
        String distance1 = getDistance(result, 1);
        System.out.println("Distance: " + distance1);
        System.out.println("Expected: -Infinity\n");

        String distance2 = getDistance(result, 3);
        System.out.println("Distance: " + distance2);
        System.out.println("Expected: 2\n");

        String distance3 = getDistance(result, 4);
        System.out.println("Distance: " + distance3);
        System.out.println("Expected: Impossible");
    }

    public static Result computeDistances(List<Edge>[] adjacencyList, int sourceVertex) {
        long[] distances = new long[adjacencyList.length];
        Arrays.fill(distances, Long.MAX_VALUE);
        distances[sourceVertex] = 0;
        new BellmanFord(adjacencyList, distances);

        long[] originalDistances = new long[distances.length];
        System.arraycopy(distances, 0, originalDistances, 0, distances.length);
        new BellmanFord(adjacencyList, distances);

        boolean[] nodesInCycles = computeNodesInCycles(originalDistances, distances);
        boolean[] nodesReachableFromCycles = computeNodesReachableFromCycles(adjacencyList, nodesInCycles);
        return new Result(distances, nodesReachableFromCycles);
    }

    private static boolean[] computeNodesInCycles(long[] originalDistances, long[] updatedDistances) {
        boolean[] nodesInCycles = new boolean[originalDistances.length];
        for (int vertexID = 0; vertexID < originalDistances.length; vertexID++) {
            if (originalDistances[vertexID] != updatedDistances[vertexID]) {
                nodesInCycles[vertexID] = true;
            }
        }
        return nodesInCycles;
    }

    private static boolean[] computeNodesReachableFromCycles(List<Edge>[] adjacencyList, boolean[] nodesInCycles) {
        boolean[] nodesReachableFromCycles = new boolean[nodesInCycles.length];
        boolean[] visited = new boolean[nodesInCycles.length];
        Queue<Integer> queue = new LinkedList<>();

        for (int vertexID = 0; vertexID < nodesInCycles.length; vertexID++) {
            if (nodesInCycles[vertexID]) {
                nodesReachableFromCycles[vertexID] = true;
                visited[vertexID] = true;
                queue.offer(vertexID);
            }
        }

        while (!queue.isEmpty()) {
            int vertexID = queue.poll();
            for (Edge edge : adjacencyList[vertexID]) {
                if (!visited[edge.nextVertex]) {
                    visited[edge.nextVertex] = true;
                    nodesReachableFromCycles[edge.nextVertex] = true;
                    queue.offer(edge.nextVertex);
                }
            }
        }
        return nodesReachableFromCycles;
    }

    private static class BellmanFord {
        public BellmanFord(List<Edge>[] adjacencyList, long[] distTo) {
            for (int iteration = 0; iteration < adjacencyList.length; iteration++) {
                boolean updated = false;
                for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
                    if (distTo[vertexID] != Long.MAX_VALUE) {
                        updated |= relax(adjacencyList, distTo, vertexID);
                    }
                }
                if (!updated) {
                    break;
                }
            }
        }

        private boolean relax(List<Edge>[] adjacencyList, long[] distTo, int vertex) {
            boolean updated = false;
            for (Edge edge : adjacencyList[vertex]) {
                int neighbor = edge.nextVertex;

                if (distTo[neighbor] > distTo[vertex] + edge.weight) {
                    distTo[neighbor] = distTo[vertex] + edge.weight;
                    updated = true;
                }
            }
            return updated;
        }
    }

    private static String getDistance(Result result, int targetVertexID) {
        if (result.nodesReachableFromCycles[targetVertexID]) {
            return "-Infinity";
        } else if (result.distances[targetVertexID] != Long.MAX_VALUE) {
            return String.valueOf(result.distances[targetVertexID]);
        } else {
            return "Impossible";
        }
    }
}
