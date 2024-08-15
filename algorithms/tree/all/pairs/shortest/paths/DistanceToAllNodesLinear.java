package algorithms.tree.all.pairs.shortest.paths;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 26/07/24.
 */
// Computes the sum of the length of all shortest paths in a weighted tree from each node to all other nodes.
// Based on problem E (Full Depth Morning Show) solution in
// https://www.dropbox.com/scl/fi/ome3m546wlwmyzxgvdfax/scusa-2019-outlines.pdf?rlkey=egnvvw8fazodhrjd9stx7ijdm&e=1&dl=0
// O(n) runtime, where n is the number of nodes.
public class DistanceToAllNodesLinear {

    private static class Edge {
        int nextNodeId;
        double length;

        public Edge(int nextNodeId, double length) {
            this.nextNodeId = nextNodeId;
            this.length = length;
        }
    }

    private static double[] computeAllDistances(List<Edge>[] adjacencyList, int rootId) {
        double[] distances = new double[adjacencyList.length];
        long[] subtreeSizes = new long[adjacencyList.length];
        boolean[] visited = new boolean[adjacencyList.length];
        visited[rootId] = true;

        computeDistancesFromRoot(adjacencyList, visited, distances, subtreeSizes, rootId, rootId, 0);

        visited = new boolean[adjacencyList.length];
        visited[rootId] = true;
        computeDistances(adjacencyList, visited, distances, subtreeSizes, rootId);
        return distances;
    }

    private static void computeDistancesFromRoot(List<Edge>[] adjacencyList, boolean[] visited, double[] distances,
                                                 long[] subtreeSizes, int rootId, int nodeId, double currentDistance) {
        distances[rootId] += currentDistance;
        subtreeSizes[nodeId] = 1;

        for (Edge edge : adjacencyList[nodeId]) {
            if (!visited[edge.nextNodeId]) {
                visited[edge.nextNodeId] = true;

                computeDistancesFromRoot(adjacencyList, visited, distances, subtreeSizes, rootId, edge.nextNodeId,
                        currentDistance + edge.length);
                subtreeSizes[nodeId] += subtreeSizes[edge.nextNodeId];
            }
        }
    }

    private static void computeDistances(List<Edge>[] adjacencyList, boolean[] visited, double[] distances,
                                         long[] subtreeSizes, int nodeId) {
        for (Edge edge : adjacencyList[nodeId]) {
            if (!visited[edge.nextNodeId]) {
                visited[edge.nextNodeId] = true;

                distances[edge.nextNodeId] = distances[nodeId]
                        + edge.length * (adjacencyList.length - subtreeSizes[edge.nextNodeId])
                        - edge.length * subtreeSizes[edge.nextNodeId];
                computeDistances(adjacencyList, visited, distances, subtreeSizes, edge.nextNodeId);
            }
        }
    }

    //    0
    //    1
    //  2   3
    // 4
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        List<Edge>[] adjacencyList = new List[5];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        adjacencyList[0].add(new Edge(1, 2));
        adjacencyList[1].add(new Edge(0, 2));
        adjacencyList[1].add(new Edge(2, 5));
        adjacencyList[1].add(new Edge(3, 6));
        adjacencyList[2].add(new Edge(1, 5));
        adjacencyList[2].add(new Edge(4, 3));
        adjacencyList[3].add(new Edge(1, 6));
        adjacencyList[4].add(new Edge(2, 3));

        double[] distances = computeAllDistances(adjacencyList, 3);
        double[] expectedDistances = { 27.00, 21.00, 26.00, 39.00, 35.00 };

        for (int nodeID = 0; nodeID < adjacencyList.length; nodeID++) {
            System.out.printf("Sum of path lengths between %d and all other nodes: %.2f Expected: %.2f%n",
                    nodeID, distances[nodeID], expectedDistances[nodeID]);
        }
    }
}
