package algorithms.graph.minimum.spanning.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 28/06/23.
 */
// Computes the second best MST in O(E lg V)
// In this variant, the second best MST may have the same weight as the MST, as long as they have one different edge.
// Adapted from https://cp-algorithms.com/graph/second_best_mst.html#modeling-into-a-lowest-common-ancestor-lca-problem
@SuppressWarnings("unchecked")
public class SecondBestMSTSameWeightOk {

    private static class Edge implements Comparable<Edge> {
        int id;
        int vertex1;
        int vertex2;
        int weight;

        public Edge(int id, int vertex1, int vertex2, int weight) {
            this.id = id;
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(weight, other.weight);
        }
    }

    private static class UnionFind {
        private final int[] leaders;
        private final int[] ranks;
        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            components = size;

            for (int i = 0; i < size; i++) {
                leaders[i] = i;
                ranks[i] = 0;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        // O(inverse Ackermann function)
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

        // O(inverse Ackermann function)
        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);
            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
            } else {
                leaders[leader1] = leader2;
                ranks[leader2]++;
            }
            components--;
        }
    }

    private static final int MAX_PATH = 21;
    private static List<Edge>[] adjacencyList;
    private static List<Edge> edges;
    private static int[][] dpMaxWeights;
    private static int[][] vertexIDsAbove;
    private static int[] distances;
    private static int verticesNumber;

    public SecondBestMSTSameWeightOk(List<Edge>[] originalAdjacencyList) {
        verticesNumber = originalAdjacencyList.length + 1;
        adjacencyList = new List[verticesNumber];

        dpMaxWeights = new int[verticesNumber][MAX_PATH];
        vertexIDsAbove = new int[verticesNumber][MAX_PATH];
        distances = new int[verticesNumber];

        for (int vertexID = 1; vertexID < verticesNumber; vertexID++) {
            adjacencyList[vertexID] = new ArrayList<>();
            Arrays.fill(vertexIDsAbove[vertexID], -1);

            for (int distance = 0; distance < MAX_PATH; distance++) {
                dpMaxWeights[vertexID][distance] = 0;
            }
        }

        for (int distance = 0; distance < MAX_PATH; distance++) {
            dpMaxWeights[0][distance] = 0;
        }
        computeEdges(originalAdjacencyList);
    }

    public static long computeSecondBestMST() {
        long secondMSTWeight = Long.MAX_VALUE;
        long mstWeight = 0;
        Collections.sort(edges);
        UnionFind unionFind = new UnionFind(verticesNumber);
        boolean[] edgeIDIsInMST = new boolean[edges.size()];

        // Kruskal's algorithm
        for (Edge edge : edges) {
            if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                adjacencyList[edge.vertex1].add(edge);
                adjacencyList[edge.vertex2].add(new Edge(edge.id, edge.vertex2, edge.vertex1, edge.weight));
                edgeIDIsInMST[edge.id] = true;
                mstWeight += edge.weight;
                unionFind.union(edge.vertex1, edge.vertex2);
            }
        }
        if (unionFind.components != 2) {
            // No MST exists
            return Long.MAX_VALUE;
        }

        // Pre-computation
        depthFirstSearch(1, 0, 0);

        for (int distance = 1; distance <= MAX_PATH - 1; distance++) {
            for (int vertexID = 1; vertexID < verticesNumber; vertexID++) {
                if (vertexIDsAbove[vertexID][distance - 1] != -1) {
                    int vertexIDAbove = vertexIDsAbove[vertexID][distance - 1];
                    vertexIDsAbove[vertexID][distance] = vertexIDsAbove[vertexIDAbove][distance - 1];
                    dpMaxWeights[vertexID][distance] =
                            Math.max(dpMaxWeights[vertexID][distance - 1], dpMaxWeights[vertexIDAbove][distance - 1]);
                }
            }
        }

        // Try to add each edge not in the MST
        for (Edge edge : edges) {
            if (!edgeIDIsInMST[edge.id]) {
                int highestWeightInAncestorPath = lca(edge.vertex1, edge.vertex2);

                if (secondMSTWeight > mstWeight + edge.weight - highestWeightInAncestorPath) {
                    secondMSTWeight = mstWeight + edge.weight - highestWeightInAncestorPath;
                }
            }
        }
        return secondMSTWeight;
    }

    private static void computeEdges(List<Edge>[] adjacencyList) {
        edges = new ArrayList<>();
        int edgeID = 0;

        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            for (Edge edge : adjacencyList[vertexID]) {
                edges.add(new Edge(edgeID, vertexID + 1, edge.vertex2 + 1, edge.weight));
                edgeID++;
            }
        }
    }

    private static void depthFirstSearch(int vertexID, int parentID, int weight) {
        distances[vertexID] = distances[parentID] + 1;
        vertexIDsAbove[vertexID][0] = parentID;
        dpMaxWeights[vertexID][0] = weight;

        for (Edge edge : adjacencyList[vertexID]) {
            if (edge.vertex2 != parentID) {
                depthFirstSearch(edge.vertex2, vertexID, edge.weight);
            }
        }
    }

    // Computes the highest edge weights on the paths from vertexID1 and vertexID2 to their LCA
    private static int lca(int vertexID1, int vertexID2) {
        int highestWeightInAncestorPath = -2;
        if (distances[vertexID1] < distances[vertexID2]) {
            int aux = vertexID2;
            vertexID2 = vertexID1;
            vertexID1 = aux;
        }

        for (int i = MAX_PATH - 1; i >= 0; i--) {
            if (distances[vertexID1] - distances[vertexID2] >= (1 << i)) {
                highestWeightInAncestorPath =
                        Math.max(highestWeightInAncestorPath, dpMaxWeights[vertexID1][i]);
                vertexID1 = vertexIDsAbove[vertexID1][i];
            }
        }

        if (vertexID1 == vertexID2) {
            return highestWeightInAncestorPath;
        }

        for (int i = MAX_PATH - 1; i >= 0; i--) {
            if (vertexIDsAbove[vertexID1][i] != -1
                    && vertexIDsAbove[vertexID2][i] != -1
                    && vertexIDsAbove[vertexID1][i] != vertexIDsAbove[vertexID2][i]) {
                int highestWeightDP = Math.max(dpMaxWeights[vertexID1][i], dpMaxWeights[vertexID2][i]);
                highestWeightInAncestorPath = Math.max(highestWeightInAncestorPath, highestWeightDP);
                vertexID1 = vertexIDsAbove[vertexID1][i];
                vertexID2 = vertexIDsAbove[vertexID2][i];
            }
        }
        int highestRootWeight = Math.max(dpMaxWeights[vertexID1][0], dpMaxWeights[vertexID2][0]);
        return Math.max(highestWeightInAncestorPath, highestRootWeight);
    }

    public static void main(String[] args) {
        List<SecondBestMSTSameWeightOk.Edge>[] adjacencyList = new List[6];
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            adjacencyList[vertexID] = new ArrayList<>();
        }
        adjacencyList[0].add(new SecondBestMSTSameWeightOk.Edge(0, 0, 1, 13));
        adjacencyList[0].add(new SecondBestMSTSameWeightOk.Edge(1, 0, 2, 28));
        adjacencyList[0].add(new SecondBestMSTSameWeightOk.Edge(2, 0, 3, 7));
        adjacencyList[1].add(new SecondBestMSTSameWeightOk.Edge(3, 1, 2, 27));
        adjacencyList[1].add(new SecondBestMSTSameWeightOk.Edge(4, 1, 4, 39));
        adjacencyList[2].add(new SecondBestMSTSameWeightOk.Edge(5, 2, 3, 2));
        adjacencyList[2].add(new SecondBestMSTSameWeightOk.Edge(6, 2, 4, 34));
        adjacencyList[2].add(new SecondBestMSTSameWeightOk.Edge(7, 2, 5, 14));
        adjacencyList[3].add(new SecondBestMSTSameWeightOk.Edge(8, 3, 5, 7));
        adjacencyList[4].add(new SecondBestMSTSameWeightOk.Edge(9, 4, 5, 36));

        new SecondBestMSTSameWeightOk(adjacencyList);
        long secondBestMSTWeight = SecondBestMSTSameWeightOk.computeSecondBestMST();
        System.out.println("Second best MST weight: " + secondBestMSTWeight);
        System.out.println("Expected: 65");
    }
}
