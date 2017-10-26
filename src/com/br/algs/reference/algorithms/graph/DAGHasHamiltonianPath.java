package com.br.algs.reference.algorithms.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by rene on 25/10/17.
 */
@SuppressWarnings("unchecked")
public class DAGHasHamiltonianPath {

    // A DAG has a Hamiltonian path if and only if there is a directed edge between each pair of consecutive vertices
    // in a topological order

    // Also, a DAG has a unique topological ordering if and only if there is a directed edge between each pair of consecutive
    // vertices in its topological order (i.e, the digraph has a Hamiltonian path)
    // If the DAG has multiple topological orderings, then a second topological order can be obtained by swapping
    // any pair of consecutive and nonadjacent vertices
    public boolean hasHamiltonianPath(List<Integer>[] adjacent) {

        // 0- Precondition: check if the graph is a DAG
        HasDirectedCycle hasDirectedCycle = new HasDirectedCycle(adjacent);
        if(hasDirectedCycle.hasCycle()) {
            throw new IllegalArgumentException("Digraph is not a DAG");
        }

        // 1- Compute topological order
        int[] topologicalOrder = topologicalSort(adjacent);

        for(int i = 0; i < topologicalOrder.length - 1; i++) {
            boolean hasEdgeToNextVertex = false;

            for(int neighbor : adjacent[topologicalOrder[i]]) {
                if(neighbor == topologicalOrder[i + 1]) {
                    hasEdgeToNextVertex = true;
                    break;
                }
            }

            if(!hasEdgeToNextVertex) {
                return false;
            }
        }

        return true;
    }

    private static int[] topologicalSort(List<Integer>[] adjacent) {
        Stack<Integer> finishTimes = getFinishTimes(adjacent);

        int[] topologicalSort = new int[finishTimes.size()];
        int arrayIndex = 0;

        while (!finishTimes.isEmpty()) {
            topologicalSort[arrayIndex++] = finishTimes.pop();
        }

        return topologicalSort;
    }

    private static Stack<Integer> getFinishTimes(List<Integer>[] adjacent) {
        boolean[] visited = new boolean[adjacent.length];
        Stack<Integer> finishTimes = new Stack<>();

        for(int i = 0; i < adjacent.length; i++) {
            if(!visited[i]) {
                depthFirstSearch(i, adjacent, finishTimes, visited);
            }
        }

        return finishTimes;
    }

    private static void depthFirstSearch(int sourceVertex, List<Integer>[] adj, Stack<Integer> finishTimes, boolean[] visited) {
        visited[sourceVertex] = true;

        for(int neighbor : adj[sourceVertex]) {
            if(!visited[neighbor]) {
                depthFirstSearch(neighbor, adj, finishTimes, visited);
            }
        }

        finishTimes.push(sourceVertex);
    }

    private static class HasDirectedCycle {

        private boolean visited[];
        private int[] edgeTo;
        private Stack<Integer> cycle; // vertices on  a cycle (if one exists)
        private boolean[] onStack; // vertices on recursive call stack

        public HasDirectedCycle(List<Integer>[] adjacent) {
            onStack = new boolean[adjacent.length];
            edgeTo = new int[adjacent.length];
            visited = new boolean[adjacent.length];

            for(int vertex = 0; vertex < adjacent.length; vertex++) {
                if(!visited[vertex]) {
                    dfs(adjacent, vertex);
                }
            }
        }

        private void dfs(List<Integer>[] adjacent, int vertex) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for(int neighbor : adjacent[vertex]) {
                if(hasCycle()) {
                    return;
                } else if(!visited[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    dfs(adjacent, neighbor);
                } else if(onStack[neighbor]) {
                    cycle = new Stack<>();

                    for(int currentVertex = vertex; currentVertex != neighbor; currentVertex = edgeTo[currentVertex]) {
                        cycle.push(currentVertex);
                    }

                    cycle.push(neighbor);
                    cycle.push(vertex);
                }
            }

            onStack[vertex] = false;
        }

        public boolean hasCycle() {
            return cycle != null;
        }

        public Iterable<Integer> cycle() {
            return cycle;
        }
    }

    public static void main(String[] args) {
        DAGHasHamiltonianPath hamiltonianPathInDAGs = new DAGHasHamiltonianPath();

        List<Integer>[] digraph1 = (List<Integer>[]) new ArrayList[5];
        for(int vertex = 0; vertex < digraph1.length; vertex++) {
            digraph1[vertex] = new ArrayList<>();
        }

        digraph1[0].add(1);
        digraph1[0].add(2);
        digraph1[1].add(2);
        digraph1[2].add(3);
        digraph1[3].add(4);
        System.out.println("Has Hamiltonian path: " + hamiltonianPathInDAGs.hasHamiltonianPath(digraph1) + " Expected: true");

        List<Integer>[] digraph2 = (List<Integer>[]) new ArrayList[6];
        for(int vertex = 0; vertex < digraph2.length; vertex++) {
            digraph2[vertex] = new ArrayList<>();
        }

        digraph2[0].add(1);
        digraph2[1].add(2);
        digraph2[3].add(4);
        digraph2[4].add(5);
        System.out.println("Has Hamiltonian path: " + hamiltonianPathInDAGs.hasHamiltonianPath(digraph2) + " Expected: false");

        List<Integer>[] digraph3 = (List<Integer>[]) new ArrayList[9];
        for(int vertex = 0; vertex < digraph3.length; vertex++) {
            digraph3[vertex] = new ArrayList<>();
        }

        digraph3[0].add(1);
        digraph3[1].add(2);
        digraph3[1].add(3);

        digraph3[4].add(5);
        digraph3[5].add(6);
        digraph3[6].add(7);
        digraph3[6].add(8);
        digraph3[7].add(2);
        digraph3[8].add(3);
        System.out.println("Has Hamiltonian path: " + hamiltonianPathInDAGs.hasHamiltonianPath(digraph3) + " Expected: false");

        List<Integer>[] digraph4 = (List<Integer>[]) new ArrayList[5];
        for(int vertex = 0; vertex < digraph4.length; vertex++) {
            digraph4[vertex] = new ArrayList<>();
        }

        digraph4[0].add(2);
        digraph4[1].add(2);
        digraph4[1].add(3);
        digraph4[2].add(4);
        digraph4[3].add(4);
        System.out.println("Has Hamiltonian path: " + hamiltonianPathInDAGs.hasHamiltonianPath(digraph4) + " Expected: false");
    }

}
