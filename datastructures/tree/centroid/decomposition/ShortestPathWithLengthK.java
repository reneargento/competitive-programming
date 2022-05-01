package datastructures.tree.centroid.decomposition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Rene Argento on 26/04/22.
 */
// Consider an unrooted tree T with weighted edges.
// This algorithm uses centroid decomposition to find the shortest path (in number of edges) of length K in T.
// Returns -1 if such path does not exist.
public class ShortestPathWithLengthK {

    private static class Edge {
        int neighbor;
        int length;

        public Edge(int neighbor, int length) {
            this.neighbor = neighbor;
            this.length = length;
        }
    }

    private static class LengthAndNumberOfEdges {
        int length;
        int numberOfEdges;

        public LengthAndNumberOfEdges(int length, int numberOfEdges) {
            this.length = length;
            this.numberOfEdges = numberOfEdges;
        }
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        int nodes = 4;
        List<Edge>[] adjacencyList = new List[nodes];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        // Edge 0-1 with length 1
        adjacencyList[0].add(new Edge(1, 1));
        adjacencyList[1].add(new Edge(0, 1));
        // Edge 1-2 with length 2
        adjacencyList[1].add(new Edge(2, 2));
        adjacencyList[2].add(new Edge(1, 2));
        // Edge 1-3 with length 4
        adjacencyList[1].add(new Edge(3, 4));
        adjacencyList[3].add(new Edge(1, 4));

        int lengthK = 3;
        int minimumNumberOfEdges = centroidDecomposition(adjacencyList, lengthK);

        System.out.print("Result: ");
        if (minimumNumberOfEdges == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(minimumNumberOfEdges);
        }
        System.out.println("Expected: 2");
    }

    private static int centroidDecomposition(List<Edge>[] adjacencyList, int lengthRequired) {
        boolean[] deleted = new boolean[adjacencyList.length];
        int[] subtreeSizes = new int[adjacencyList.length];
        return process(adjacencyList, deleted, 0, lengthRequired, subtreeSizes);
    }

    private static int process(List<Edge>[] adjacencyList, boolean[] deleted, int vertex, int lengthRequired,
                               int[] subtreeSizes) {
        int bestPathNumberOfEdges = Integer.MAX_VALUE;
        int centroid = getCentroid(adjacencyList, deleted, vertex, subtreeSizes);
        deleted[centroid] = true;

        for (Edge edge : adjacencyList[centroid]) {
            int neighbor = edge.neighbor;
            if (!deleted[neighbor]) {
                int childPathNumberOfEdges = process(adjacencyList, deleted, neighbor, lengthRequired,
                        subtreeSizes);
                bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, childPathNumberOfEdges);
            }
        }

        Map<Integer, Integer> lengthToEdgesMap = new HashMap<>();
        List<LengthAndNumberOfEdges> entriesToAdd = new ArrayList<>();
        int pathNumberOfEdges = dfsToGetPath(adjacencyList, deleted, lengthRequired, lengthToEdgesMap,
                entriesToAdd, centroid, -1, 0, 0);

        deleted[centroid] = false;
        return Math.min(bestPathNumberOfEdges, pathNumberOfEdges);
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

    private static int dfsToGetPath(List<Edge>[] adjacencyList, boolean[] deleted, int lengthRequired,
                                    Map<Integer, Integer> lengthToEdgesMap, List<LengthAndNumberOfEdges> entriesToAdd,
                                    int vertex, int parent, int currentLength, int currentNumberOfEdges) {
        int bestPathNumberOfEdges = Integer.MAX_VALUE;

        for (Edge edge : adjacencyList[vertex]) {
            int neighbor = edge.neighbor;
            if (neighbor != parent && !deleted[neighbor]) {
                int newLength = currentLength + edge.length;
                int newNumberOfEdges = currentNumberOfEdges + 1;
                entriesToAdd.add(new LengthAndNumberOfEdges(newLength, newNumberOfEdges));

                int lengthRemaining = lengthRequired - newLength;
                if (lengthRemaining == 0) {
                    bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, newNumberOfEdges);
                } else if (lengthRemaining > 0) {
                    if (lengthToEdgesMap.containsKey(lengthRemaining)) {
                        int totalNumberOfEdges = newNumberOfEdges + lengthToEdgesMap.get(lengthRemaining);
                        bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, totalNumberOfEdges);
                    }
                    int pathNumberOfEdges = dfsToGetPath(adjacencyList, deleted, lengthRequired,
                            lengthToEdgesMap, entriesToAdd, neighbor, vertex, newLength, newNumberOfEdges);
                    bestPathNumberOfEdges = Math.min(bestPathNumberOfEdges, pathNumberOfEdges);
                }
            }

            if (parent == -1) {
                for (LengthAndNumberOfEdges lengthAndNumberOfEdges : entriesToAdd) {
                    int length = lengthAndNumberOfEdges.length;
                    int numberOfEdges = lengthAndNumberOfEdges.numberOfEdges;
                    if (!lengthToEdgesMap.containsKey(length)
                            || lengthToEdgesMap.get(length) > numberOfEdges) {
                        lengthToEdgesMap.put(length, numberOfEdges);
                    }
                }
                entriesToAdd.clear();
            }
        }
        return bestPathNumberOfEdges;
    }
}
