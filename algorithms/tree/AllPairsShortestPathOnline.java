package algorithms.tree;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 08/07/24.
 */
// Computes the length of all pairs shortest paths in a weighted tree.
// Based on https://noiref.codecla.ws/ds/#sparse
// O(n^2 lg n) runtime, where n is the number of nodes, and there are N^2 node combinations
// O(n lg n) space
public class AllPairsShortestPathOnline {

    private static class Edge {
        int vertexId;
        double weight;

        public Edge(int vertexId, double weight) {
            this.vertexId = vertexId;
            this.weight = weight;
        }
    }

    private static class LCA {
        private final int LOG_N = 21;
        int[][] ancestor;
        int[] heights;              // number of edges from root
        double[] shortestDistances; // sum of edge weights from root
        boolean[] visited;

        LCA(List<Edge>[] adjacencyList, int startVertexId) {
            ancestor = new int[LOG_N + 1][adjacencyList.length];
            heights = new int[adjacencyList.length];
            shortestDistances = new double[adjacencyList.length];
            visited = new boolean[adjacencyList.length];

            computeShortestDistancesFromRoot(adjacencyList, startVertexId);
        }

        public int computeLCA(int vertexId1, int vertexId2) {
            if (heights[vertexId1] > heights[vertexId2]) {
                int aux = vertexId1;
                vertexId1 = vertexId2;
                vertexId2 = aux;
            }

            // Advance vertexId2 by height[vertexId2] - height[vertexId1] levels
            int levels = heights[vertexId2] - heights[vertexId1];
            for (int height = 0; height < LOG_N; height++) {
                if ((levels & (1 << height)) > 0) {
                    vertexId2 = ancestor[height][vertexId2];
                }
            }

            if (vertexId1 == vertexId2) {
                return vertexId1;
            }

            for (int height = LOG_N - 1; height >= 0; height--) {
                if (ancestor[height][vertexId1] != ancestor[height][vertexId2]) {
                    vertexId1 = ancestor[height][vertexId1];
                    vertexId2 = ancestor[height][vertexId2];
                }
            }
            return ancestor[0][vertexId1];
        }

        public double computeDistance(int vertexId1, int vertexId2) {
            int lca = computeLCA(vertexId1, vertexId2);
            return shortestDistances[vertexId1] + shortestDistances[vertexId2]
                    - 2 * shortestDistances[lca];
        }

        private void computeShortestDistancesFromRoot(List<Edge>[] adjacencyList, int startVertexId) {
            Queue<Integer> queue = new LinkedList<>();
            queue.offer(startVertexId);
            visited[startVertexId] = true;

            while (!queue.isEmpty()) {
                int currentVertexId = queue.poll();

                for (int height = 0; height < ancestor.length - 1; height++) {
                    int parentId = ancestor[height][currentVertexId];
                    ancestor[height + 1][currentVertexId] = ancestor[height][parentId];
                }

                for (Edge edge : adjacencyList[currentVertexId]) {
                    int neighborId = edge.vertexId;
                    if (visited[neighborId]) {
                        continue;
                    }

                    visited[neighborId] = true;
                    ancestor[0][neighborId] = currentVertexId;
                    shortestDistances[neighborId] = shortestDistances[currentVertexId] + edge.weight;
                    heights[neighborId] = heights[currentVertexId] + 1;
                    queue.offer(neighborId);
                }
            }
        }
    }

    //         5
    //     3      7
    //   2  4    6  9
    // 1  8
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws IOException {
        List<Edge>[] adjacencyList = new List[10];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        adjacencyList[5].add(new Edge(3, 3));
        adjacencyList[5].add(new Edge(7, 2));
        adjacencyList[3].add(new Edge(5, 3));
        adjacencyList[3].add(new Edge(2, 1));
        adjacencyList[3].add(new Edge(4, 7));
        adjacencyList[2].add(new Edge(3, 1));
        adjacencyList[2].add(new Edge(1, 3.5));
        adjacencyList[2].add(new Edge(8, 7));
        adjacencyList[4].add(new Edge(3, 7));
        adjacencyList[1].add(new Edge(2, 3.5));
        adjacencyList[8].add(new Edge(2, 7));
        adjacencyList[7].add(new Edge(5, 2));
        adjacencyList[7].add(new Edge(6, 10.2));
        adjacencyList[7].add(new Edge(9, 3));
        adjacencyList[6].add(new Edge(7, 10.2));
        adjacencyList[9].add(new Edge(7, 3));

        LCA lca = new LCA(adjacencyList, 5);

        for (int nodeID1 = 1; nodeID1 < adjacencyList.length; nodeID1++) {
            for (int nodeID2 = 1; nodeID2 < adjacencyList.length; nodeID2++) {
                System.out.printf("Shortest Path length between %d and %d: %.2f%n", nodeID1, nodeID2,
                        lca.computeDistance(nodeID1, nodeID2));
            }
        }
    }
}
