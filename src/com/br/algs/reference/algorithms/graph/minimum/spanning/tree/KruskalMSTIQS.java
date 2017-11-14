package com.br.algs.reference.algorithms.graph.minimum.spanning.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by rene on 14/11/17.
 */
// Kruskal's MST algorithm using incremental quick select (IQS) instead of a priority queue.
// O(E + V lg^2 V) average time

// This is one of the fastest MST algorithms in practice, with performance comparable to a Fibonacci-heap based
// implementation of Prim's MST algorithm.
public class KruskalMSTIQS {

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
        public int compareTo(Edge otherEdge) {
            return this.cost - otherEdge.cost;
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

    private static Queue<Edge> minimumSpanningTree;
    private static double weight;

    public KruskalMSTIQS(Edge[] edges, int totalVertices) {
        minimumSpanningTree = new LinkedList<>();

        UnionFind unionFind = new UnionFind(totalVertices);
        int kthLightestEdgeToFind = 0;

        Stack<Integer> pivots = new Stack<>();
        pivots.push(edges.length);

        while (minimumSpanningTree.size() < totalVertices - 1) {
            Edge edge = (Edge) incrementalQuickSelect(edges, kthLightestEdgeToFind, pivots);
            kthLightestEdgeToFind++;

            // Ignore ineligible edges
            if(unionFind.connected(edge.vertex1, edge.vertex2)) {
                continue;
            }

            unionFind.union(edge.vertex1, edge.vertex2);
            minimumSpanningTree.offer(edge); // Add edge to the minimum spanning tree

            weight += edge.cost;
        }
    }

    private static Comparable incrementalQuickSelect(Comparable[] array, int indexToSearch, Stack<Integer> pivots) {
        int smallestCorrectPivotIndex = pivots.peek();

        // Pre-condition: indexToSearch <= smallestCorrectPivotIndex
        if(indexToSearch > smallestCorrectPivotIndex) {
            throw new IllegalArgumentException("Searching for an element in the wrong range");
        }

        if(indexToSearch == smallestCorrectPivotIndex) {
            pivots.pop();
            return array[indexToSearch];
        }

        int pivotIndex = ThreadLocalRandom.current().nextInt(indexToSearch, smallestCorrectPivotIndex);
        int correctPivotIndexAfterPartition = partition(array, pivotIndex, indexToSearch, smallestCorrectPivotIndex - 1);

        // Invariant: array[0] <= ... <= array[indexToSearch - 1] <= array[indexToSearch, correctPivotIndexAfterPartition - 1]
        // <= array[correctPivotIndexAfterPartition] <= array[correctPivotIndexAfterPartition + 1, pivots.peek() - 1]
        // <= array[pivots.peek(), array.length - 1]
        pivots.push(correctPivotIndexAfterPartition);
        return incrementalQuickSelect(array, indexToSearch, pivots);
    }

    private static int partition(Comparable[] array, int pivotIndex, int low, int high) {
        if(low == high) {
            return low;
        }

        Comparable pivot = array[pivotIndex];

        exchange(array, low, pivotIndex);

        int lowIndex = low;
        int highIndex = high + 1;

        while (true) {
            while (less(array[++lowIndex], pivot)) {
                if(lowIndex == high) {
                    break;
                }
            }

            while (less(pivot, array[--highIndex])) {
                if(highIndex == low) {
                    break;
                }
            }

            if(lowIndex >= highIndex) {
                break;
            }

            exchange(array, lowIndex, highIndex);
        }

        // Place pivot in the correct place
        exchange(array, low, highIndex);
        return highIndex;
    }

    public static Iterable<Edge> edges() {
        return minimumSpanningTree;
    }

    public static double eagerWeight() {
        return weight;
    }

    public static void exchange(Comparable[] array, int position1, int position2) {
        Comparable temp = array[position1];
        array[position1] = array[position2];
        array[position2] = temp;
    }

    public static boolean less(Comparable value1, Comparable value2) {
        if(value1.compareTo(value2) < 0) {
            return true;
        } else {
            return false;
        }
    }

}
