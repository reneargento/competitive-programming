package algorithms.tree.lca;

import java.io.*;
import java.util.*;

/**
 * Created by Rene Argento on 08/07/24.
 */
// Computes the LCA and distance of two vertices in a tree using a sparse table.
// Based on https://noiref.codecla.ws/ds/#sparse
// This is the most efficient LCA algorithm on this repository and should be the preferred choice when optimizing runtime.
// O(n) pre-computation and O(lg n) runtime per query, where n is the number of nodes
// O(n lg n) space
public class LowestCommonAncestorLgN {

    private static class Edge {
        int vertexId;
        int weight;

        public Edge(int vertexId, int weight) {
            this.vertexId = vertexId;
            this.weight = weight;
        }
    }

    private static class LCA {
        private final int LOG_N = 21;
        int[][] ancestor;
        int[] heights;           // number of edges from root
        int[] shortestDistances; // sum of edge weights from root
        boolean[] visited;

        LCA(List<Edge>[] adjacencyList, int startVertexId) {
            ancestor = new int[LOG_N + 1][adjacencyList.length];
            heights = new int[adjacencyList.length];
            shortestDistances = new int[adjacencyList.length];
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

        public int computeDistance(int vertexId1, int vertexId2) {
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

        adjacencyList[5].add(new Edge(3, 1));
        adjacencyList[5].add(new Edge(7, 1));
        adjacencyList[3].add(new Edge(5, 1));
        adjacencyList[3].add(new Edge(2, 1));
        adjacencyList[3].add(new Edge(4, 1));
        adjacencyList[2].add(new Edge(3, 1));
        adjacencyList[2].add(new Edge(1, 1));
        adjacencyList[2].add(new Edge(8, 1));
        adjacencyList[4].add(new Edge(3, 1));
        adjacencyList[1].add(new Edge(2, 1));
        adjacencyList[8].add(new Edge(2, 1));
        adjacencyList[7].add(new Edge(5, 1));
        adjacencyList[7].add(new Edge(6, 1));
        adjacencyList[7].add(new Edge(9, 1));
        adjacencyList[6].add(new Edge(7, 1));
        adjacencyList[9].add(new Edge(7, 1));

        LCA lca = new LCA(adjacencyList, 5);
        int firstCommonAncestor1 = lca.computeLCA(1, 8);
        System.out.println("Lowest common ancestor 1 and 8: " + firstCommonAncestor1 + " Expected: 2");
        System.out.println("Distance 1 and 8: " + lca.computeDistance(1, 8) + " Expected: 2\n");

        int firstCommonAncestor2 = lca.computeLCA(1, 4);
        System.out.println("Lowest common ancestor 1 and 4: " + firstCommonAncestor2 + " Expected: 3");
        System.out.println("Distance 1 and 4: " + lca.computeDistance(1, 4) + " Expected: 3\n");

        int firstCommonAncestor3 = lca.computeLCA(8, 9);
        System.out.println("Lowest common ancestor 8 and 9: " + firstCommonAncestor3 + " Expected: 5");
        System.out.println("Distance 8 and 9: " + lca.computeDistance(8, 9) + " Expected: 5\n");

        int firstCommonAncestor4 = lca.computeLCA(6, 9);
        System.out.println("Lowest common ancestor 6 and 9: " + firstCommonAncestor4 + " Expected: 7");
        System.out.println("Distance 6 and 9: " + lca.computeDistance(6, 9) + " Expected: 2\n");

        int firstCommonAncestor5 = lca.computeLCA(3, 4);
        System.out.println("Lowest common ancestor 3 and 4: " + firstCommonAncestor5 + " Expected: 3");
        System.out.println("Distance 3 and 4: " + lca.computeDistance(3, 4) + " Expected: 1\n");

        int firstCommonAncestor6 = lca.computeLCA(6, 6);
        System.out.println("Lowest common ancestor 6 and 6: " + firstCommonAncestor6 + " Expected: 6");
        System.out.println("Distance 6 and 6: " + lca.computeDistance(6, 6) + " Expected: 0");
    }
}
