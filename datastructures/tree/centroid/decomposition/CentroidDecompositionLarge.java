package datastructures.tree.centroid.decomposition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 25/04/22.
 */
// A template with an alternative implementation (iterative) of the centroid decomposition algorithm.
// It also starts a new thread to avoid stack overflow and is useful for a tree with 10^5 or more nodes.
@SuppressWarnings("unchecked")
public class CentroidDecompositionLarge implements Runnable {

    private static class Edge {
        int neighbor;
        int length;

        public Edge(int neighbor, int length) {
            this.neighbor = neighbor;
            this.length = length;
        }
    }

    public static void main(String[] args) throws Exception {
        new Thread(null, new CentroidDecompositionLarge(), "ThreadName", (1L << 27)).start();
    }

    public void run() {
        try {
            solve();
        }
        catch (Exception e) {}
    }

    public static void solve() {
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
        return process(adjacencyList, deleted, subtreeSizes, 0);
    }

    private static int process(List<Edge>[] adjacencyList, boolean[] deleted, int[] subtreeSizes, int vertex) {
        int bestValue = Integer.MAX_VALUE;
        computeSubtreeSizes(subtreeSizes, adjacencyList, deleted, vertex, -1);
        int centroid = dfsToGetCentroid(adjacencyList, deleted, subtreeSizes, subtreeSizes[vertex], vertex, -1);

        // Solve problem using centroid here

        //

        deleted[centroid] = true;
        for (Edge edge : adjacencyList[centroid]) {
            if (!deleted[edge.neighbor]) {
                int childResult = process(adjacencyList, deleted, subtreeSizes, edge.neighbor);
                bestValue = Math.min(bestValue, childResult);
            }
        }
        return bestValue;
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

    private static int dfsToGetCentroid(List<Edge>[] adjacencyList, boolean[] deleted, int[] subtreeSizes,
                                        int totalSize, int vertex, int parent) {
        for (Edge edge : adjacencyList[vertex]) {
            int neighbor = edge.neighbor;
            if (neighbor != parent && !deleted[neighbor] && subtreeSizes[neighbor] > totalSize / 2) {
                return dfsToGetCentroid(adjacencyList, deleted, subtreeSizes, totalSize, neighbor, vertex);
            }
        }
        return vertex;
    }
}
