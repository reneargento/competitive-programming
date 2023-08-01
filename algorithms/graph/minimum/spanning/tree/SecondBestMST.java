package algorithms.graph.minimum.spanning.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 28/06/23.
 */
// Computes the second best MST in O(E lg V)
// Based on https://cp-algorithms.com/graph/second_best_mst.html#modeling-into-a-lowest-common-ancestor-lca-problem
@SuppressWarnings("unchecked")
public class SecondBestMST {

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

    private static class Pair {
        int first;
        int second;

        public Pair(int first, int second) {
            this.first = first;
            this.second = second;
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
    private static Pair[][] dpMaxWeights;
    private static int[][] vertexIDsAbove;
    private static int[] distances;
    private static int verticesNumber;

    public SecondBestMST(List<Edge>[] originalAdjacencyList) {
        verticesNumber = originalAdjacencyList.length + 1;
        adjacencyList = new List[verticesNumber];

        dpMaxWeights = new Pair[verticesNumber][MAX_PATH];
        vertexIDsAbove = new int[verticesNumber][MAX_PATH];
        distances = new int[verticesNumber];

        for (int vertexID = 1; vertexID < verticesNumber; vertexID++) {
            adjacencyList[vertexID] = new ArrayList<>();
            Arrays.fill(vertexIDsAbove[vertexID], -1);

            for (int distance = 0; distance < MAX_PATH; distance++) {
                dpMaxWeights[vertexID][distance] = new Pair(0, 0);
            }
        }

        for (int distance = 0; distance < MAX_PATH; distance++) {
            dpMaxWeights[0][distance] = new Pair(0, 0);
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

        // Pre-computation
        depthFirstSearch(1, 0, 0);

        for (int distance = 1; distance <= MAX_PATH - 1; distance++) {
            for (int vertexID = 1; vertexID < verticesNumber; vertexID++) {
                if (vertexIDsAbove[vertexID][distance - 1] != -1) {
                    int vertexIDAbove = vertexIDsAbove[vertexID][distance - 1];
                    vertexIDsAbove[vertexID][distance] = vertexIDsAbove[vertexIDAbove][distance - 1];
                    dpMaxWeights[vertexID][distance] =
                            getHighestWeightsFromPairs(dpMaxWeights[vertexID][distance - 1],
                                    dpMaxWeights[vertexIDAbove][distance - 1]);
                }
            }
        }

        // Try to add each edge not in the MST
        for (Edge edge : edges) {
            if (!edgeIDIsInMST[edge.id]) {
                Pair highestWeightsInAncestorPath = lca(edge.vertex1, edge.vertex2);
                if (highestWeightsInAncestorPath.first != edge.weight) {
                    if (secondMSTWeight > mstWeight + edge.weight - highestWeightsInAncestorPath.first) {
                        secondMSTWeight = mstWeight + edge.weight - highestWeightsInAncestorPath.first;
                    }
                } else if (highestWeightsInAncestorPath.second != -1) {
                    if (secondMSTWeight > mstWeight + edge.weight - highestWeightsInAncestorPath.second) {
                        secondMSTWeight = mstWeight + edge.weight - highestWeightsInAncestorPath.second;
                    }
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

    private static Pair getHighestWeightsFromPairs(Pair pair1, Pair pair2) {
        int[] values = { pair1.first, pair1.second, pair2.first, pair2.second };
        int highestValue = -2;
        int secondHighestValue = -3;

        for (int value : values) {
            if (value > highestValue) {
                secondHighestValue = highestValue;
                highestValue = value;
            } else if (value > secondHighestValue && value < highestValue) {
                secondHighestValue = value;
            }
        }
        return new Pair(highestValue, secondHighestValue);
    }

    private static void depthFirstSearch(int vertexID, int parentID, int weight) {
        distances[vertexID] = distances[parentID] + 1;
        vertexIDsAbove[vertexID][0] = parentID;
        dpMaxWeights[vertexID][0] = new Pair(weight, -1);

        for (Edge edge : adjacencyList[vertexID]) {
            if (edge.vertex2 != parentID) {
                depthFirstSearch(edge.vertex2, vertexID, edge.weight);
            }
        }
    }

    // Computes the highest edge weights on the paths from vertexID1 and vertexID2 to their LCA
    private static Pair lca(int vertexID1, int vertexID2) {
        Pair highestWeightsInAncestorPath = new Pair(-2, -3);
        if (distances[vertexID1] < distances[vertexID2]) {
            int aux = vertexID2;
            vertexID2 = vertexID1;
            vertexID1 = aux;
        }

        for (int i = MAX_PATH - 1; i >= 0; i--) {
            if (distances[vertexID1] - distances[vertexID2] >= (1 << i)) {
                highestWeightsInAncestorPath =
                        getHighestWeightsFromPairs(highestWeightsInAncestorPath, dpMaxWeights[vertexID1][i]);
                vertexID1 = vertexIDsAbove[vertexID1][i];
            }
        }

        if (vertexID1 == vertexID2) {
            return highestWeightsInAncestorPath;
        }

        for (int i = MAX_PATH - 1; i >= 0; i--) {
            if (vertexIDsAbove[vertexID1][i] != -1
                    && vertexIDsAbove[vertexID2][i] != -1
                    && vertexIDsAbove[vertexID1][i] != vertexIDsAbove[vertexID2][i]) {
                Pair weightsCombination = getHighestWeightsFromPairs(dpMaxWeights[vertexID1][i],
                        dpMaxWeights[vertexID2][i]);
                highestWeightsInAncestorPath =
                        getHighestWeightsFromPairs(highestWeightsInAncestorPath, weightsCombination);
                vertexID1 = vertexIDsAbove[vertexID1][i];
                vertexID2 = vertexIDsAbove[vertexID2][i];
            }
        }
        Pair rootWeights = getHighestWeightsFromPairs(dpMaxWeights[vertexID1][0], dpMaxWeights[vertexID2][0]);
        return getHighestWeightsFromPairs(highestWeightsInAncestorPath, rootWeights);
    }

    public static void main(String[] args) {
        List<SecondBestMST.Edge>[] adjacencyList = new List[6];
        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            adjacencyList[vertexID] = new ArrayList<>();
        }
        adjacencyList[0].add(new SecondBestMST.Edge(0, 0, 1, 13));
        adjacencyList[0].add(new SecondBestMST.Edge(1, 0, 2, 28));
        adjacencyList[0].add(new SecondBestMST.Edge(2, 0, 3, 7));
        adjacencyList[1].add(new SecondBestMST.Edge(3, 1, 0, 13));
        adjacencyList[1].add(new SecondBestMST.Edge(4, 1, 2, 27));
        adjacencyList[1].add(new SecondBestMST.Edge(5, 1, 4, 39));
        adjacencyList[2].add(new SecondBestMST.Edge(6, 2, 0, 28));
        adjacencyList[2].add(new SecondBestMST.Edge(7, 2, 1, 27));
        adjacencyList[2].add(new SecondBestMST.Edge(8, 2, 3, 2));
        adjacencyList[2].add(new SecondBestMST.Edge(9, 2, 4, 34));
        adjacencyList[2].add(new SecondBestMST.Edge(10, 2, 5, 14));
        adjacencyList[3].add(new SecondBestMST.Edge(11, 3, 0, 7));
        adjacencyList[3].add(new SecondBestMST.Edge(12, 3, 2, 2));
        adjacencyList[3].add(new SecondBestMST.Edge(13, 3, 5, 7));
        adjacencyList[4].add(new SecondBestMST.Edge(14, 4, 1, 39));
        adjacencyList[4].add(new SecondBestMST.Edge(15, 4, 2, 34));
        adjacencyList[4].add(new SecondBestMST.Edge(16, 4, 5, 36));
        adjacencyList[5].add(new SecondBestMST.Edge(17, 5, 2, 14));
        adjacencyList[5].add(new SecondBestMST.Edge(17, 5, 3, 7));
        adjacencyList[5].add(new SecondBestMST.Edge(17, 5, 4, 36));

        new SecondBestMST(adjacencyList);
        long secondBestMSTWeight = SecondBestMST.computeSecondBestMST();
        System.out.println("Second best MST weight: " + secondBestMSTWeight);
        System.out.println("Expected: 65");
    }
}
