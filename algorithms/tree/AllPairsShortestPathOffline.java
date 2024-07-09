package algorithms.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 11/04/24.
 */
// Computes the length of all pairs shortest paths in a weighted tree.
// Uses Tarjan's LCA offline algorithm.
// O(α(n) + Q) time complexity, where α is the inverse Ackermann function and Q is the number of queries.
// The runtime to compute all pairs shortest paths is O(N^2), since there are N^2 node combinations and
// we compute N^2 LCAs, so Q = N^2.
// The algorithm can be modified to not query all N^2 node pair combinations if not all distances are needed,
// which would reduce its time complexity.
// O(n) space
public class AllPairsShortestPathOffline {

    private static class Edge {
        Node nextNode;
        double length;

        public Edge(Node nextNode, double length) {
            this.nextNode = nextNode;
            this.length = length;
        }
    }

    private static class Node {
        int id;
        int value;
        Edge left;
        Edge right;

        Node(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    private static class UnionFind {
        private final int[] leader;
        private final int[] rank;
        private int components;

        public UnionFind(int size) {
            leader = new int[size];
            rank = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leader[i] = i;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        // O(α(n)), where α is the inverse Ackermann function.
        public int find(int site) {
            if (site == leader[site]) {
                return site;
            }
            return leader[site] = find(leader[site]);
        }

        // O(α(n)), where α is the inverse Ackermann function.
        public void union(int site1, int site2) {
            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (rank[leader1] < rank[leader2]) {
                leader[leader1] = leader2;
            } else if (rank[leader2] < rank[leader1]) {
                leader[leader2] = leader1;
            } else {
                leader[leader1] = leader2;
                rank[leader2]++;
            }
            components--;
        }
    }

    public static double[][] computeAllPairsShortestPathLengths(Node rootNode, int numberOfNodes) {
        int[][] LCAs = new int[numberOfNodes][numberOfNodes];
        int[] ancestor = new int[numberOfNodes];
        boolean[] visited = new boolean[numberOfNodes];
        UnionFind unionFind = new UnionFind(numberOfNodes);

        computeLCAs(ancestor, visited, unionFind, LCAs, rootNode);

        double[] shortestDistancesFromRoot = new double[numberOfNodes];
        computeShortestDistancesFromRoot(rootNode, shortestDistancesFromRoot);

        return computeAllDistances(LCAs, shortestDistancesFromRoot);
    }

    private static void computeLCAs(int[] ancestor, boolean[] visited, UnionFind unionFind, int[][] LCAs, Node node) {
        visited[node.id] = true;
        ancestor[node.id] = node.id;

        for (Node child : getChildrenNodes(node)) {
            computeLCAs(ancestor, visited, unionFind, LCAs, child);
            unionFind.union(node.id, child.id);

            int newLeader = unionFind.find(node.id);
            ancestor[newLeader] = node.id;
        }

        for (int queryNodeID = 0; queryNodeID < LCAs.length; queryNodeID++) {
            if (visited[queryNodeID]) {
                int queryNodeLeader = unionFind.find(queryNodeID);
                LCAs[node.id][queryNodeID] = ancestor[queryNodeLeader];
                LCAs[queryNodeID][node.id] = ancestor[queryNodeLeader];
            }
        }
    }

    private static List<Node> getChildrenNodes(Node node) {
        List<Node> childrenNodes = new ArrayList<>();
        if (node.left != null) {
            childrenNodes.add(node.left.nextNode);
        }
        if (node.right != null) {
            childrenNodes.add(node.right.nextNode);
        }
        return childrenNodes;
    }

    private static void computeShortestDistancesFromRoot(Node node, double[] shortestDistances) {
        double currentDistance = shortestDistances[node.id];

        if (node.left != null) {
            shortestDistances[node.left.nextNode.id] = currentDistance + node.left.length;
            computeShortestDistancesFromRoot(node.left.nextNode, shortestDistances);
        }
        if (node.right != null) {
            shortestDistances[node.right.nextNode.id] = currentDistance + node.right.length;
            computeShortestDistancesFromRoot(node.right.nextNode, shortestDistances);
        }
    }

    private static double[][] computeAllDistances(int[][] LCAs, double[] shortestDistancesFromRoot) {
        int numberOfNodes = shortestDistancesFromRoot.length;
        double[][] shortestPathLengths = new double[numberOfNodes][numberOfNodes];

        for (int nodeID1 = 0; nodeID1 < numberOfNodes; nodeID1++) {
            for (int nodeID2 = 0; nodeID2 < numberOfNodes; nodeID2++) {
                int lcaNode = LCAs[nodeID1][nodeID2];
                shortestPathLengths[nodeID1][nodeID2] = shortestDistancesFromRoot[nodeID1]
                        + shortestDistancesFromRoot[nodeID2] - 2 * shortestDistancesFromRoot[lcaNode];
            }
        }
        return shortestPathLengths;
    }

    //         5
    //     3      7
    //   2  4   6  -4
    // 1  8
    public static void main(String[] args) {
        Node[] nodes = new Node[9];
        Node node5 = new Node(0, 5);
        Node node3 = new Node(1, 3);
        Node node7 = new Node(2, 7);
        Node node2 = new Node(3, 2);
        Node node4 = new Node(4, 4);
        Node node1 = new Node(5, 1);
        Node node6 = new Node(6, 6);
        Node node8 = new Node(7, 8);
        Node nodeMinus4 = new Node(8, -4);

        nodes[0] = node5;
        nodes[1] = node3;
        nodes[2] = node7;
        nodes[3] = node2;
        nodes[4] = node4;
        nodes[5] = node1;
        nodes[6] = node6;
        nodes[7] = node8;
        nodes[8] = nodeMinus4;

        node5.left = new Edge(node3, 3);
        node5.right = new Edge(node7, 2);
        node3.left = new Edge(node2, 1);
        node3.right = new Edge(node4, 7);
        node2.left = new Edge(node1, 3.5);
        node2.right = new Edge(node8, 7);
        node7.left = new Edge(node6, 10.2);
        node7.right = new Edge(nodeMinus4, 3);

        double[][] shortestDistances = computeAllPairsShortestPathLengths(node5, 9);

        for (int nodeID1 = 0; nodeID1 < shortestDistances.length; nodeID1++) {
            for (int nodeID2 = 0; nodeID2 < shortestDistances[0].length; nodeID2++) {
                int nodeValue1 = nodes[nodeID1].value;
                int nodeValue2 = nodes[nodeID2].value;

                System.out.printf("Shortest Path length between %d and %d: %.2f%n", nodeValue1, nodeValue2,
                        shortestDistances[nodeID1][nodeID2]);
            }
        }
    }
}
