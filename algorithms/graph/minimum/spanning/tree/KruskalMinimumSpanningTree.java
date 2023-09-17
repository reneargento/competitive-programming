package algorithms.graph.minimum.spanning.tree;

import java.util.*;

/**
 * Created by Rene Argento on 09/09/17.
 */
@SuppressWarnings("unchecked")
public class KruskalMinimumSpanningTree {

    private static class Edge implements Comparable<Edge> {
        int vertex1;
        int vertex2;
        int cost;

        Edge(int vertex1, int vertex2, int cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }

        @Override
        public int compareTo(Edge other) {
            return Integer.compare(cost, other.cost);
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

    private static List<Edge> getMinimumSpanningTreeEdges(List<Edge> edges, int totalVertices) {
        List<Edge> edgesInSpanningTree = new ArrayList<>();

        Collections.sort(edges);
        UnionFind unionFind = new UnionFind(totalVertices);

        for (Edge edge : edges) {
            if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                unionFind.union(edge.vertex1, edge.vertex2);
                edgesInSpanningTree.add(edge);
            }

            if (unionFind.components == 1) {
                break;
            }
        }
        return edgesInSpanningTree;
    }

    private static List<Edge>[] getMinimumSpanningTree(List<Edge> edges, int totalVertices) {
        List<Edge>[] minimumSpanningTree = (List<Edge>[]) new ArrayList[totalVertices + 1];

        Collections.sort(edges);
        UnionFind unionFind = new UnionFind(totalVertices);

        for (Edge edge : edges) {
            if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                unionFind.union(edge.vertex1, edge.vertex2);

                if (minimumSpanningTree[edge.vertex1] == null) {
                    minimumSpanningTree[edge.vertex1] = new ArrayList<>();
                }
                if (minimumSpanningTree[edge.vertex2] == null) {
                    minimumSpanningTree[edge.vertex2] = new ArrayList<>();
                }
                minimumSpanningTree[edge.vertex1].add(edge);
                minimumSpanningTree[edge.vertex2].add(edge);
            }

            if (unionFind.components == 1) {
                break;
            }
        }
        return minimumSpanningTree;
    }

    private static long getCostOfMinimumSpanningTree(List<Edge> edgesInSpanningTree) {
        long costOfMinimumSpanningTree = 0;
        for (Edge edge : edgesInSpanningTree) {
            costOfMinimumSpanningTree += edge.cost;
        }
        return costOfMinimumSpanningTree;
    }
}
