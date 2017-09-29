package com.br.algs.reference.algorithms.graph;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Created by rene on 25/09/17.
 */
public class Clusterization {

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
            if(site == leaders[site]) {
                return site;
            }

            return leaders[site] = find(leaders[site]);
        }

        //O(inverse Ackermann function)
        public void union(int site1, int site2) {

            int leader1 = find(site1);
            int leader2 = find(site2);

            if(leader1 == leader2) {
                return;
            }

            if(ranks[leader1] < ranks[leader2]) {
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

    private static long getClusterizationCost(int verticesCount, Edge[] edges, int clusters) {
        long cost = 0;
        UnionFind unionFind = new UnionFind(verticesCount);

        Arrays.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                return edge1.cost - edge2.cost;
            }
        });

        int currentEdge = 0;

        while (unionFind.count() > clusters) {
            if(!unionFind.connected(edges[currentEdge].vertex1, edges[currentEdge].vertex2)) {
                cost += edges[currentEdge].cost;
                unionFind.union(edges[currentEdge].vertex1, edges[currentEdge].vertex2);
            }

            currentEdge++;
        }

        return cost;
    }

}
