package algorithms.graph.minimum.spanning.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by Rene Argento on 23/08/23.
 */
// Computes incremental Minimum Spanning Trees.
// After adding each edge, output the cost of the MST, or -1, if the MST does not exist.
// Runtime complexity: O(V * E)
@SuppressWarnings("unchecked")
public class MSTIncremental {

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

        private int otherVertex(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            }
            return vertex1;
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

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }
            return leaders[site] = find(leaders[site]);
        }

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

    public static List<Integer> computeIncrementalMST(int verticesNumber, List<Edge> edges) {
        List<Integer> incrementalMSTCosts = new ArrayList<>();
        int minimumMSTCost = 0;
        boolean hasMST = false;

        UnionFind unionFind = new UnionFind(verticesNumber);
        List<Edge>[] adjacencyList = new ArrayList[verticesNumber];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        for (Edge edge : edges) {
            if (!unionFind.connected(edge.vertex1, edge.vertex2)) {
                unionFind.union(edge.vertex1, edge.vertex2);
            }
            if (unionFind.components == 1) {
                hasMST = true;
            }

            Edge highestEdgeCost = getHighestEdgeCostInPath(adjacencyList, edge.vertex1, edge.vertex2);

            if (highestEdgeCost == null || highestEdgeCost.cost > edge.cost) {
                if (highestEdgeCost != null) {
                    int difference = highestEdgeCost.cost - edge.cost;
                    minimumMSTCost -= difference;
                    adjacencyList[highestEdgeCost.vertex1].remove(highestEdgeCost);
                    adjacencyList[highestEdgeCost.vertex2].remove(highestEdgeCost);
                } else {
                    minimumMSTCost += edge.cost;
                }

                adjacencyList[edge.vertex1].add(edge);
                adjacencyList[edge.vertex2].add(edge);
            }

            if (hasMST) {
                incrementalMSTCosts.add(minimumMSTCost);
            } else {
                incrementalMSTCosts.add(-1);
            }
        }
        return incrementalMSTCosts;
    }

    private static Edge getHighestEdgeCostInPath(List<Edge>[] adjacencyList, int vertexID1, int vertexID2) {
        Edge highestEdgeCost = null;
        Deque<Edge> edgesInPathStack = new ArrayDeque<>();
        boolean[] visited = new boolean[adjacencyList.length];

        boolean hasPath = getEdgesInPath(adjacencyList, edgesInPathStack, visited, vertexID2, vertexID1);
        if (hasPath) {
            for (Edge edge : edgesInPathStack) {
                if (highestEdgeCost == null || edge.cost > highestEdgeCost.cost) {
                    highestEdgeCost = edge;
                }
            }
        }
        return highestEdgeCost;
    }

    private static boolean getEdgesInPath(List<Edge>[] adjacencyList, Deque<Edge> edgesInPathStack,
                                          boolean[] visited, int targetVertexID, int currentVertexID) {
        visited[currentVertexID] = true;

        for (Edge edge : adjacencyList[currentVertexID]) {
            int otherVertex = edge.otherVertex(currentVertexID);
            if (visited[otherVertex]) {
                continue;
            }

            edgesInPathStack.push(edge);
            if (otherVertex == targetVertexID) {
                return true;
            }

            boolean hasPath = getEdgesInPath(adjacencyList, edgesInPathStack, visited, targetVertexID, otherVertex);
            if (hasPath) {
                return true;
            } else {
                edgesInPathStack.pop();
            }
        }
        return false;
    }

    public static void main(String[] args) {
        List<Edge> edges = new ArrayList<>();
        // Based on IOI 2003 Trail Maintenance problem sample
        edges.add(new Edge(0, 1, 10));
        edges.add(new Edge(0, 2, 8));
        edges.add(new Edge(2, 1, 3));
        edges.add(new Edge(0, 3, 3));
        edges.add(new Edge(0, 2, 6));
        edges.add(new Edge(1, 0, 2));

        List<Integer> incrementalMST = computeIncrementalMST(4, edges);
        System.out.print("MST costs:");
        for (int cost : incrementalMST) {
            System.out.print(" " + cost);
        }
        System.out.println();
        System.out.println("Expected:  -1 -1 -1 14 12 8");
    }
}
