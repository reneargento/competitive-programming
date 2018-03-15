package com.br.algs.reference.algorithms.graph.minimum.spanning.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by rene on 09/09/17.
 */
@SuppressWarnings("unchecked")
public class KruskalMinimumSpanningTree {

    private static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        /** Call this method to initialize reader for InputStream */
        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        /** Get next word */
        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        //Used to check EOF
        //If getLine() == null, it is a EOF
        //Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class Edge {
        int vertex1;
        int vertex2;
        int cost;

        Edge(int vertex1, int vertex2, int cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }
    }

    private static class UnionFind {

        private int[] leaders;
        private int[] ranks;

        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i]  = i;
                ranks[i] = 0;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        //O(inverse Ackermann function)
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }

            return leaders[site] = find(leaders[site]);
        }

        //O(inverse Ackermann function)
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

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int totalVertices = FastReader.nextInt();
        int totalEdges = FastReader.nextInt();

        Edge[] edges = new Edge[totalEdges];

        for(int i = 0; i < totalEdges; i++) {
            int vertex1Id = FastReader.nextInt();
            int vertex2Id = FastReader.nextInt();
            int cost = FastReader.nextInt();

            //Add edge
            Edge edge = new Edge(vertex1Id, vertex2Id, cost);
            edges[i] = edge;
        }

        List<Edge> edgesInSpanningTree = getMinimumSpanningTreeEdges(edges, totalVertices);
        long minimumSpanningTreeCost = getCostOfMinimumSpanningTree(edgesInSpanningTree);
        System.out.println("Cost of the minimum spanning tree: " + minimumSpanningTreeCost);
    }

    private static List<Edge>[] getMinimumSpanningTree(Edge[] edges, int totalVertices) {
        List<Edge>[] minimumSpanningTree = (List<Edge>[]) new ArrayList[totalVertices + 1];

        Arrays.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                if (edge1.cost < edge2.cost) {
                    return -1;
                } else if (edge1.cost > edge2.cost) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        UnionFind unionFind = new UnionFind(totalVertices + 1);

        for(Edge edge : edges) {

            if (unionFind.find(edge.vertex1) != unionFind.find(edge.vertex2)) {
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

    private static List<Edge> getMinimumSpanningTreeEdges(Edge[] edges, int totalVertices) {
        List<Edge> edgesInSpanningTree = new ArrayList<>();

        Arrays.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                if (edge1.cost < edge2.cost) {
                    return -1;
                } else if (edge1.cost > edge2.cost) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        UnionFind unionFind = new UnionFind(totalVertices + 1);

        for(Edge edge : edges) {

            if (unionFind.find(edge.vertex1) != unionFind.find(edge.vertex2)) {
                unionFind.union(edge.vertex1, edge.vertex2);
                edgesInSpanningTree.add(edge);
            }

            if (unionFind.components == 1) {
                break;
            }
        }

        return edgesInSpanningTree;
    }

    private static long getCostOfMinimumSpanningTree(List<Edge> edgesInSpanningTree) {
        long costOfMinimumSpanningTree = 0;

        for(Edge edge : edgesInSpanningTree) {
            costOfMinimumSpanningTree += edge.cost;
        }

        return costOfMinimumSpanningTree;
    }

}
