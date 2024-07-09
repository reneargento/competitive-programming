package algorithms.tree.lca;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 11/04/24.
 */
// Computes the LCA of Q queries of pair of nodes, offline.
// O(α(n) + Q) time complexity, where α is the inverse Ackermann function and Q is the number of queries.
// O(n) space
// Based on https://cp-algorithms.com/graph/lca_tarjan.html
public class LCAOfflineTarjan {

    private static class Node {
        int id;
        int value;
        Node left;
        Node right;

        Node(int id, int value) {
            this.id = id;
            this.value = value;
        }
    }

    private static class Query {
        int nodeID1;
        int nodeID2;
        int result;

        public Query(int nodeID1, int nodeID2) {
            this.nodeID1 = nodeID1;
            this.nodeID2 = nodeID2;
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

    public static void computeLCAs(List<Query>[] queries, Node rootNode, int numberOfNodes) {
        int[] ancestor = new int[numberOfNodes];
        boolean[] visited = new boolean[numberOfNodes];
        UnionFind unionFind = new UnionFind(numberOfNodes);
        computeLCAs(queries, ancestor, visited, unionFind, rootNode);
    }

    private static void computeLCAs(List<Query>[] queries, int[] ancestor, boolean[] visited, UnionFind unionFind,
                                    Node node) {
        visited[node.id] = true;
        ancestor[node.id] = node.id;

        Node[] children = { node.left, node.right };
        for (Node child : children) {
            if (child == null) {
                continue;
            }
            computeLCAs(queries, ancestor, visited, unionFind, child);
            unionFind.union(node.id, child.id);

            int newLeader = unionFind.find(node.id);
            ancestor[newLeader] = node.id;
        }

        for (Query query : queries[node.id]) {
            int queryNodeID;
            if (query.nodeID1 == node.id) {
                queryNodeID = query.nodeID2;
            } else {
                queryNodeID = query.nodeID1;
            }

            if (visited[queryNodeID]) {
                int queryNodeLeader = unionFind.find(queryNodeID);
                query.result = ancestor[queryNodeLeader];
            }
        }
    }

    //         5
    //     3      7
    //   2  4   6  -4
    // 1  8
    @SuppressWarnings("unchecked")
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

        node5.left = node3;
        node5.right = node7;
        node3.left = node2;
        node3.right = node4;
        node2.left = node1;
        node2.right = node8;
        node7.left = node6;
        node7.right = nodeMinus4;

        List<Query>[] queries = new List[9];
        for (int i = 0; i < queries.length; i++) {
            queries[i] = new ArrayList<>();
        }
        Query query1 = new Query(5, 7);
        queries[5].add(query1);
        queries[7].add(query1);
        Query query2 = new Query(5, 4);
        queries[5].add(query2);
        queries[4].add(query2);
        Query query3 = new Query(7, 8);
        queries[7].add(query3);
        queries[8].add(query3);
        Query query4 = new Query(6, 8);
        queries[6].add(query4);
        queries[8].add(query4);
        Query query5 = new Query(1, 4);
        queries[1].add(query5);
        queries[4].add(query5);
        Query query6 = new Query(6, 6);
        queries[6].add(query6);

        computeLCAs(queries, node5, 9);

        for (int nodeID = 0; nodeID < queries.length; nodeID++) {
            for (Query query : queries[nodeID]) {
                if (query.nodeID1 != nodeID) {
                    continue;
                }
                int nodeValue1 = nodes[query.nodeID1].value;
                int nodeValue2 = nodes[query.nodeID2].value;
                int LCA = nodes[query.result].value;

                System.out.printf("Lowest common ancestor %d and %d: %d%n", nodeValue1, nodeValue2, LCA);
            }
        }
    }
}
