package com.br.algs.reference.algorithms.graph.ancestor;

import java.util.*;

/**
 * Created by rene on 24/10/17.
 */
@SuppressWarnings("unchecked")
public class LCAInDAG {

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

    private List<Integer>[] adjacent;
    private int[] maxDistances;

    //Preprocess to
    // 1- Find all sources in the digraph
    // 2- Compute the height of all vertices (max distance from any source)
    // O(S * (V + E)) where S is the number of sources = O(VE)
    public LCAInDAG(List<Integer>[] adjacent) {
        this.adjacent = adjacent;
        maxDistances = new int[adjacent.length];
        Set<Integer> sources = new HashSet<>();

        // 1- Find the sources in the graph
        int[] indegrees = new int[adjacent.length];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            for(int neighbor : adjacent[vertex]) {
                indegrees[neighbor]++;
            }
        }

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if(indegrees[vertex] == 0) {
                sources.add(vertex);
            }
        }

        // 2- Find the height of all vertices (the length of the longest distance from a source)
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            maxDistances[vertex] = -1;
        }

        for(int source : sources) {
            int[] distanceFromCurrentSource = new int[adjacent.length];

            for(int vertex = 0; vertex < distanceFromCurrentSource.length; vertex++) {
                distanceFromCurrentSource[vertex] = Integer.MAX_VALUE;
            }

            Queue<Integer> sourceDistanceQueue = new LinkedList<>();
            sourceDistanceQueue.offer(source);
            distanceFromCurrentSource[source] = 0;

            if(distanceFromCurrentSource[source] > maxDistances[source]) {
                maxDistances[source] = distanceFromCurrentSource[source];
            }

            while (!sourceDistanceQueue.isEmpty()) {
                int currentVertex = sourceDistanceQueue.poll();

                for(int neighbor : adjacent[currentVertex]) {
                    distanceFromCurrentSource[neighbor] = distanceFromCurrentSource[currentVertex] + 1;
                    sourceDistanceQueue.offer(neighbor);

                    if(distanceFromCurrentSource[neighbor] > maxDistances[neighbor]) {
                        maxDistances[neighbor] = distanceFromCurrentSource[neighbor];
                    }
                }
            }
        }
    }

    //O(V + E)
    public int getLCA(int vertex1, int vertex2) {

        // 0- Precondition: check if the graph is a DAG
        HasDirectedCycle hasDirectedCycle = new HasDirectedCycle(adjacent);
        if(hasDirectedCycle.hasCycle()) {
            throw new IllegalArgumentException("Digraph is not a DAG");
        }

        // 1- Reverse graph
        List<Integer>[] reverseDigraph = (List<Integer>[]) new ArrayList[adjacent.length];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            reverseDigraph[vertex] = new ArrayList<>();
        }

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            for(int neighbor : adjacent[vertex]) {
                reverseDigraph[neighbor].add(vertex);
            }
        }

        // 2- Do a BFS from vertex1 to find all its ancestors
        Set<Integer> vertex1Ancestors = new HashSet<>();

        Queue<Integer> queue = new LinkedList<>();
        queue.offer(vertex1);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            vertex1Ancestors.add(currentVertex);

            for(int neighbor : reverseDigraph[currentVertex]) {
                queue.offer(neighbor);
            }
        }

        // 3- Do a BFS from vertex2 to find all its ancestors and see which ones are common ancestors to vertex1
        Set<Integer> commonAncestors = new HashSet<>();

        queue.offer(vertex2);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            if(vertex1Ancestors.contains(currentVertex)) {
                commonAncestors.add(currentVertex);
            }

            for(int neighbor : reverseDigraph[currentVertex]) {
                queue.offer(neighbor);
            }
        }

        // 4- Find the height of all common ancestors (the length of the longest distance from a source)
        // The common ancestor with greatest height is an LCA of vertex1 and vertex2
        int maxDistance = -1;
        int lowestCommonAncestor = -1;

        for(int commonAncestor : commonAncestors) {
            if(maxDistances[commonAncestor] > maxDistance) {
                maxDistance = maxDistances[commonAncestor];
                lowestCommonAncestor = commonAncestor;
            }
        }

        // If there is no LCA, -1 will be returned
        return lowestCommonAncestor;
    }

    public static void main(String[] args) {
        List<Integer>[] digraph1 = (List<Integer>[]) new ArrayList[5];
        for(int vertex = 0; vertex < digraph1.length; vertex++) {
            digraph1[vertex] = new ArrayList<>();
        }

        digraph1[0].add(1);
        digraph1[1].add(2);
        digraph1[0].add(3);
        digraph1[3].add(4);

        LCAInDAG lcaInDAG1 = new LCAInDAG(digraph1);
        int lca1 = lcaInDAG1.getLCA(2, 4);
        if(lca1 == -1) {
            System.out.print("LCA in digraph 1: There is no LCA in this DAG");
        } else {
            System.out.print("LCA in digraph 1: " + lca1);
        }
        System.out.println(" Expected: 0");


        List<Integer>[] digraph2 = (List<Integer>[]) new ArrayList[5];
        for(int vertex = 0; vertex < digraph2.length; vertex++) {
            digraph2[vertex] = new ArrayList<>();
        }

        digraph2[0].add(1);
        digraph2[0].add(2);
        digraph2[2].add(3);
        digraph2[2].add(4);

        LCAInDAG lcaInDAG2 = new LCAInDAG(digraph2);
        int lca2 = lcaInDAG2.getLCA(3, 4);
        if(lca2 == -1) {
            System.out.print("LCA in digraph 2: There is no LCA in this DAG");
        } else {
            System.out.print("LCA in digraph 2: " + lca2);
        }
        System.out.println(" Expected: 2");


        List<Integer>[] digraph3 = (List<Integer>[]) new ArrayList[9];
        for(int vertex = 0; vertex < digraph3.length; vertex++) {
            digraph3[vertex] = new ArrayList<>();
        }

        digraph3[0].add(1);
        digraph3[1].add(2);
        digraph3[1].add(3);

        digraph3[4].add(5);
        digraph3[5].add(6);
        digraph3[6].add(8);
        digraph3[6].add(7);
        digraph3[7].add(2);
        digraph3[8].add(3);

        LCAInDAG lcaInDAG3 = new LCAInDAG(digraph3);
        int lca3 = lcaInDAG3.getLCA(2, 3);
        if(lca3 == -1) {
            System.out.print("LCA in digraph 3: There is no LCA in this DAG");
        } else {
            System.out.print("LCA in digraph 3: " + lca3);
        }
        System.out.println(" Expected: 6");


        List<Integer>[] digraph4 = (List<Integer>[]) new ArrayList[9];
        for(int vertex = 0; vertex < digraph4.length; vertex++) {
            digraph4[vertex] = new ArrayList<>();
        }

        digraph4[0].add(1);
        digraph4[1].add(3);
        digraph4[1].add(4);
        digraph4[4].add(5);
        digraph4[5].add(6);
        digraph4[6].add(2);

        digraph4[7].add(8);
        digraph4[8].add(3);
        digraph4[7].add(2);

        LCAInDAG lcaInDAG4 = new LCAInDAG(digraph4);
        int lca4 = lcaInDAG4.getLCA(2, 3);
        if(lca4 == -1) {
            System.out.print("LCA in digraph 4: There is no LCA in this DAG");
        } else {
            System.out.print("LCA in digraph 4: " + lca4);
        }
        System.out.println(" Expected: 1");


        List<Integer>[] digraph5 = (List<Integer>[]) new ArrayList[4];
        for(int vertex = 0; vertex < digraph5.length; vertex++) {
            digraph5[vertex] = new ArrayList<>();
        }

        digraph5[0].add(1);
        digraph5[1].add(2);

        LCAInDAG lcaInDAG5 = new LCAInDAG(digraph5);
        int lca5 = lcaInDAG5.getLCA(2, 3);
        if(lca5 == -1) {
            System.out.print("LCA in digraph 5: There is no LCA in this DAG");
        } else {
            System.out.print("LCA in digraph 5: " + lca5);
        }
        System.out.println(" Expected: There is no LCA in this DAG");
    }

}
