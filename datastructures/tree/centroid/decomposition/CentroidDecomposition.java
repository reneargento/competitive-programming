package datastructures.tree.centroid.decomposition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 25/04/22.
 */
// A template for centroid decomposition problems.
public class CentroidDecomposition {

    private static class Edge {
        int neighbor;
        int length;

        public Edge(int neighbor, int length) {
            this.neighbor = neighbor;
            this.length = length;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<Edge>[] adjacencyList = new List[2];
        adjacencyList[0] = new ArrayList<>();
        adjacencyList[0].add(new Edge(1, 5));
        adjacencyList[1] = new ArrayList<>();
        adjacencyList[1].add(new Edge(0, 5));
        centroidDecomposition(adjacencyList);
    }

    private static int centroidDecomposition(List<Edge>[] adjacencyList) {
        boolean[] deleted = new boolean[adjacencyList.length];
        int[] subtreeSizes = new int[adjacencyList.length];
        return process(adjacencyList, deleted, 0, subtreeSizes);
    }

    private static int process(List<Edge>[] adjacencyList, boolean[] deleted, int vertex, int[] subtreeSizes) {
        int bestValue = Integer.MAX_VALUE;
        int centroid = getCentroid(adjacencyList, deleted, vertex, subtreeSizes);
        deleted[centroid] = true;

        for (Edge edge : adjacencyList[centroid]) {
            int neighbor = edge.neighbor;
            if (!deleted[neighbor]) {
                int childResult = process(adjacencyList, deleted, neighbor, subtreeSizes);
                bestValue = Math.min(bestValue, childResult);
            }
        }

        // Solve problem using centroid here

        //

        deleted[centroid] = false;
        return bestValue;
    }

    private static int getCentroid(List<Edge>[] adjacencyList, boolean[] deleted, int root, int[] subtreeSizes) {
        computeSubtreeSizes(subtreeSizes, adjacencyList, deleted, root, -1);
        int totalSize = subtreeSizes[root];
        return dfsToGetCentroid(subtreeSizes, adjacencyList, deleted, totalSize, root, -1);
    }

    private static void computeSubtreeSizes(int[] subtreeSizes, List<Edge>[] adjacencyList, boolean[] deleted,
                                            int vertex, int parent) {
        subtreeSizes[vertex] = 1;
        for (Edge edge : adjacencyList[vertex]) {
            int neighbor = edge.neighbor;
            if (neighbor != parent && !deleted[neighbor]) {
                computeSubtreeSizes(subtreeSizes, adjacencyList, deleted, neighbor, vertex);
                subtreeSizes[vertex] += subtreeSizes[neighbor];
            }
        }
    }

    private static int dfsToGetCentroid(int[] subtreeSizes, List<Edge>[] adjacencyList, boolean[] deleted,
                                        int totalSize, int vertex, int parent) {
        for (Edge edge : adjacencyList[vertex]) {
            int neighbor = edge.neighbor;
            if (neighbor != parent && !deleted[neighbor] && subtreeSizes[neighbor] > totalSize / 2) {
                return dfsToGetCentroid(subtreeSizes, adjacencyList, deleted, totalSize, neighbor, vertex);
            }
        }
        return vertex;
    }
}
