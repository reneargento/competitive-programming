package com.br.algs.reference.algorithms.graph.cycle.detection;

import java.util.*;

/**
 * Created by rene on 28/10/17.
 */
@SuppressWarnings("unchecked")
public class HasOddLengthDirectedCycle {

    // A digraph G has an odd-length directed cycle if and only if one (or more) of its strong components
    // (when treated as an undirected graph) is nonbipartite

    // Proof
    // If the digraph G has an odd-length directed cycle, then this cycle will be entirely contained in one of the
    // strong components. When the strong component is treated as an undirected graph, the odd-length directed cycle
    // becomes an odd-length cycle.
    // An undirected graph is bipartite if and only if it has no odd-length cycle.

    // Suppose a strong component of G is nonbipartite (when treated as an undirected graph). This means that there is
    // an odd-length cycle C in the strong component, ignoring direction.
    // If C is a directed cycle, then we are done.
    // Otherwise, if and edge v->w is pointing in the "wrong" direction, we can replace it with an odd-length path that
    // is pointing in the opposite direction (which preserves the parity of the number of edges in the cycle).
    // To see how, note that there exists a directed path P from w to v because v and w are in the same strong component.
    // If P has odd length, then we replace edge v->w by P; if P has even length, then this path P combined with
    // v->w is an odd-length cycle.
    // O(V + E)
    public boolean hasOddLengthDirectedCycle(List<Integer>[] adjacent) {
        StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacent);

        Set<Integer>[] strongComponents = (Set<Integer>[]) new HashSet[stronglyConnectedComponents.count()];

        for(int scc = 0; scc < strongComponents.length; scc++) {
            strongComponents[scc] = new HashSet<>();
        }

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            int strongComponentId = stronglyConnectedComponents.sccId(vertex);
            strongComponents[strongComponentId].add(vertex);
        }

        for(int scc = 0; scc < strongComponents.length; scc++) {
            List<Integer>[] subGraph = (List<Integer>[]) new ArrayList[adjacent.length];

            for(int vertex : strongComponents[scc]) {
                for(int neighbor : adjacent[vertex]) {
                    if (strongComponents[scc].contains(neighbor)) {

                        //Lazily initialize adjacency lists to keep algorithm O(V + E) instead of O(V^2)
                        if (subGraph[vertex] == null) {
                            subGraph[vertex] = new ArrayList<>();
                        }
                        if (subGraph[neighbor] == null) {
                            subGraph[neighbor] = new ArrayList<>();
                        }

                        subGraph[vertex].add(neighbor);
                        subGraph[neighbor].add(vertex);
                    }
                }
            }

            IsBipartite isBipartite = new IsBipartite(subGraph);
            if (!isBipartite.isBipartite()) {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        HasOddLengthDirectedCycle oddLengthDirectedCycle = new HasOddLengthDirectedCycle();

        List<Integer>[] digraph1 = (List<Integer>[]) new ArrayList[4];
        for(int i = 0; i < digraph1.length; i++) {
            digraph1[i] = new ArrayList<>();
        }

        digraph1[0].add(1);
        digraph1[1].add(2);
        digraph1[2].add(3);
        digraph1[3].add(0);

        System.out.println("Has odd length directed cycle: " + oddLengthDirectedCycle.hasOddLengthDirectedCycle(digraph1)
                + " Expected: false");

        List<Integer>[] digraph2 = (List<Integer>[]) new ArrayList[5];
        for(int i = 0; i < digraph2.length; i++) {
            digraph2[i] = new ArrayList<>();
        }

        digraph2[0].add(1);
        digraph2[1].add(2);
        digraph2[2].add(0);
        digraph2[3].add(4);

        System.out.println("Has odd length directed cycle: " + oddLengthDirectedCycle.hasOddLengthDirectedCycle(digraph2)
                + " Expected: true");

        List<Integer>[] digraph3 = (List<Integer>[]) new ArrayList[10];
        for(int i = 0; i < digraph3.length; i++) {
            digraph3[i] = new ArrayList<>();
        }

        digraph3[0].add(1);
        digraph3[1].add(2);
        digraph3[3].add(4);
        digraph3[4].add(6);
        digraph3[6].add(8);
        digraph3[8].add(5);
        digraph3[5].add(9);
        digraph3[9].add(4);
        digraph3[7].add(0);

        System.out.println("Has odd length directed cycle: " + oddLengthDirectedCycle.hasOddLengthDirectedCycle(digraph3)
                + " Expected: true");

        List<Integer>[] digraph4 = (List<Integer>[]) new ArrayList[5];
        for(int i = 0; i < digraph4.length; i++) {
            digraph4[i] = new ArrayList<>();
        }

        digraph4[0].add(1);
        digraph4[1].add(0);
        digraph4[2].add(3);
        digraph4[3].add(4);

        System.out.println("Has odd length directed cycle: " + oddLengthDirectedCycle.hasOddLengthDirectedCycle(digraph4)
                + " Expected: false");
    }

    private static class StronglyConnectedComponents {

        private int[] sccId;
        private int sccCount;
        private int[] componentSizes;

        public StronglyConnectedComponents(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            sccId = new int[adjacent.length];
            componentSizes = new int[adjacent.length];

            List<Integer>[] inverseEdges = invertGraphEdges(adjacent);
            int[] topologicalOrder = topologicalSort(inverseEdges);

            for(int vertex : topologicalOrder) {
                if (!visited[vertex]) {
                    depthFirstSearch(vertex, adjacent, null, visited, false);
                    sccCount++;
                }
            }
        }

        public boolean stronglyConnected(int vertex1, int vertex2) {
            return sccId[vertex1] == sccId[vertex2];
        }

        public int sccId(int vertex) {
            return sccId[vertex];
        }

        public int count() {
            return sccCount;
        }

        private int[] topologicalSort(List<Integer>[] adjacent) {
            boolean[] visited = new boolean[adjacent.length];
            Stack<Integer> finishTimes = new Stack<>();

            for(int i = 0; i < visited.length; i++) {
                if (!visited[i]) {
                    depthFirstSearch(i, adjacent, finishTimes, visited, true);
                }
            }

            int[] topologicalSort = new int[finishTimes.size()];
            int arrayIndex = 0;

            while (!finishTimes.isEmpty()) {
                topologicalSort[arrayIndex++] = finishTimes.pop();
            }

            return topologicalSort;
        }

        // Fast, but recursive
        private void depthFirstSearch(int sourceVertex, List<Integer>[] adjacent, Stack<Integer> finishTimes,
                                      boolean[] visited, boolean getFinishTimes) {
            visited[sourceVertex] = true;

            if (!getFinishTimes) {
                sccId[sourceVertex] = sccCount;
                componentSizes[sccCount]++;
            }

            if (adjacent[sourceVertex] != null) {
                for(int neighbor : adjacent[sourceVertex]) {
                    if (!visited[neighbor]) {
                        depthFirstSearch(neighbor, adjacent, finishTimes, visited, getFinishTimes);
                    }
                }
            }

            if (getFinishTimes) {
                finishTimes.push(sourceVertex);
            }
        }

        private List<Integer>[] invertGraphEdges(List<Integer>[] adjacent) {
            List<Integer>[] inverseEdges = new ArrayList[adjacent.length];

            for(int i = 0; i < inverseEdges.length; i++) {
                inverseEdges[i] = new ArrayList<>();
            }

            for(int i = 0; i < adjacent.length; i++) {
                List<Integer> neighbors = adjacent[i];

                if (neighbors != null) {
                    for(int neighbor : adjacent[i]) {
                        inverseEdges[neighbor].add(i);
                    }
                }
            }

            return inverseEdges;
        }
    }

    private static class IsBipartite {

        private boolean[] visited;
        private boolean[] color;
        private boolean isTwoColorable = true;

        public IsBipartite(List<Integer>[] adjacent) {
            visited = new boolean[adjacent.length];
            color = new boolean[adjacent.length];

            for(int source = 0; source < adjacent.length; source++) {
                if (!visited[source]) {
                    dfs(adjacent, source);
                }
            }
        }

        private void dfs(List<Integer>[] adjacent, int vertex) {
            visited[vertex] = true;

            if (adjacent[vertex] != null) {
                for(int neighbor : adjacent[vertex]) {
                    if (!visited[neighbor]) {
                        color[neighbor] = !color[vertex];
                        dfs(adjacent, neighbor);
                    } else if (color[neighbor] == color[vertex]) {
                        isTwoColorable = false;
                    }
                }
            }
        }

        public boolean isBipartite() {
            return isTwoColorable;
        }

    }

}
