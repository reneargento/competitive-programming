package algorithms.graph.ancestor;

import java.util.*;

/**
 * Created by rene on 24/10/17.
 */
// Computes the shortest ancestral path between 2 vertices in O(V + E)
@SuppressWarnings("unchecked")
public class ShortestAncestralPath {

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
                if (!visited[vertex]) {
                    dfs(adjacent, vertex);
                }
            }
        }

        private void dfs(List<Integer>[] adjacent, int vertex) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for(int neighbor : adjacent[vertex]) {
                if (hasCycle()) {
                    return;
                } else if (!visited[neighbor]) {
                    edgeTo[neighbor] = vertex;
                    dfs(adjacent, neighbor);
                } else if (onStack[neighbor]) {
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

    private class ShortestAncestralPathResult {
        private int commonAncestor;
        private String shortestPathFromVertex1ToAncestor;
        private String shortestPathFromVertex2ToAncestor;

        ShortestAncestralPathResult(int commonAncestor, String shortestPathFromVertex1ToAncestor,
                              String shortestPathFromVertex2ToAncestor) {
            this.commonAncestor = commonAncestor;
            this.shortestPathFromVertex1ToAncestor = shortestPathFromVertex1ToAncestor;
            this.shortestPathFromVertex2ToAncestor = shortestPathFromVertex2ToAncestor;
        }
    }

    private class BreadthFirstSearchToGetIntersection {
        private List<Integer>[] adjacent;
        private int[] edgeTo;
        private Queue<Integer> pendingVertices;

        BreadthFirstSearchToGetIntersection(List<Integer>[] adjacent, int source) {
            this.adjacent = adjacent;
            edgeTo = new int[adjacent.length];

            for (int i = 0; i < edgeTo.length; i++) {
                edgeTo[i] = -1;
            }
            edgeTo[source] = source;
            pendingVertices = new LinkedList<>();
            pendingVertices.offer(source);
        }

        int runStep(BreadthFirstSearchToGetIntersection otherBFS) {
            int verticesToProcess = pendingVertices.size();

            while (verticesToProcess > 0) {
                int vertex = pendingVertices.poll();
                for (int neighbor : adjacent[vertex]) {
                    if (edgeTo[neighbor] != -1) {
                        continue;
                    }
                    edgeTo[neighbor] = vertex;

                    if (otherBFS.edgeTo[neighbor] != -1) {
                        return neighbor;
                    }
                    pendingVertices.offer(neighbor);
                }
                verticesToProcess--;
            }
            return -1;
        }

        boolean isCompleted() {
            return pendingVertices.isEmpty();
        }

        Stack<Integer> pathTo(int vertex) {
            Stack<Integer> path = new Stack<>();

            while (edgeTo[vertex] != vertex) {
                path.push(vertex);
                vertex = edgeTo[vertex];
            }
            path.push(vertex);
            return path;
        }
    }

    private String pathToString(Stack<Integer> path) {
        StringBuilder pathString = new StringBuilder();

        while (path.size() > 1) {
            pathString.append(path.pop()).append("->").append(path.peek());

            if (path.size() != 1) {
                pathString.append(" ");
            }
        }
        return pathString.toString();
    }

    public ShortestAncestralPathResult getShortestAncestralPath(List<Integer>[] adjacent, int vertex1, int vertex2) {
        // 0- Precondition: check if the graph is a DAG
        HasDirectedCycle hasDirectedCycle = new HasDirectedCycle(adjacent);
        if (hasDirectedCycle.hasCycle()) {
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

        // 2- Run a bidirectional breadth-first search to find the ancestor
        BreadthFirstSearchToGetIntersection bfs1 = new BreadthFirstSearchToGetIntersection(reverseDigraph, vertex1);
        BreadthFirstSearchToGetIntersection bfs2 = new BreadthFirstSearchToGetIntersection(reverseDigraph, vertex2);

        int ancestor = -1;
        while (ancestor == - 1 && (!bfs1.isCompleted() || !bfs2.isCompleted())) {
            ancestor = bfs1.runStep(bfs2);
            if (ancestor == -1) {
                ancestor = bfs2.runStep(bfs1);
            }
        }

        if (ancestor == -1) {
            return new ShortestAncestralPathResult(-1, null, null);
        }

        // 3- Get the paths from each vertex to the ancestor
        Stack<Integer> shortestPathFromVertex1ToAncestor = bfs1.pathTo(ancestor);
        Stack<Integer> shortestPathFromVertex2ToAncestor = bfs2.pathTo(ancestor);

        return new ShortestAncestralPathResult(ancestor, pathToString(shortestPathFromVertex1ToAncestor),
                pathToString(shortestPathFromVertex2ToAncestor));
    }

    public static void main(String[] args) {
        ShortestAncestralPath shortestAncestralPath = new ShortestAncestralPath();

        List<Integer>[] digraph1 = (List<Integer>[]) new ArrayList[5];
        for(int vertex = 0; vertex < digraph1.length; vertex++) {
            digraph1[vertex] = new ArrayList<>();
        }

        digraph1[0].add(1);
        digraph1[1].add(2);
        digraph1[0].add(3);
        digraph1[3].add(4);

        ShortestAncestralPathResult shortestAncestralPath1 = shortestAncestralPath.getShortestAncestralPath(digraph1, 2, 4);
        if (shortestAncestralPath1.commonAncestor == -1) {
            System.out.println("Common ancestor in digraph 1: No common ancestor found");
        } else {
            System.out.println("Common ancestor in digraph 1: " + shortestAncestralPath1.commonAncestor);
            System.out.println("Path from vertex 2 to ancestor: " + shortestAncestralPath1.shortestPathFromVertex1ToAncestor);
            System.out.println("Path from vertex 4 to ancestor: " + shortestAncestralPath1.shortestPathFromVertex2ToAncestor);
        }
        System.out.println("Expected: 0");
        System.out.println("2->1 1->0");
        System.out.println("4->3 3->0\n");


        List<Integer>[] digraph2 = (List<Integer>[]) new ArrayList[5];
        for(int vertex = 0; vertex < digraph2.length; vertex++) {
            digraph2[vertex] = new ArrayList<>();
        }

        digraph2[0].add(1);
        digraph2[0].add(2);
        digraph2[2].add(3);
        digraph2[2].add(4);

        ShortestAncestralPathResult shortestAncestralPath2 = shortestAncestralPath.getShortestAncestralPath(digraph2, 3, 4);
        if (shortestAncestralPath2.commonAncestor == -1) {
            System.out.println("Common ancestor in digraph 2: Common ancestor not found");
        } else {
            System.out.println("Common ancestor in digraph 2: " + shortestAncestralPath2.commonAncestor);
            System.out.println("Path from vertex 3 to ancestor: " + shortestAncestralPath2.shortestPathFromVertex1ToAncestor);
            System.out.println("Path from vertex 4 to ancestor: " + shortestAncestralPath2.shortestPathFromVertex2ToAncestor);
        }
        System.out.println("Expected: 2");
        System.out.println("3->2");
        System.out.println("4->2\n");


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

        ShortestAncestralPathResult shortestAncestralPath3 = shortestAncestralPath.getShortestAncestralPath(digraph3, 2, 3);
        if (shortestAncestralPath3.commonAncestor == -1) {
            System.out.println("Common ancestor in digraph 3: Common ancestor not found");
        } else {
            System.out.println("Common ancestor in digraph 3: " + shortestAncestralPath3.commonAncestor);
            System.out.println("Path from vertex 2 to ancestor: " + shortestAncestralPath3.shortestPathFromVertex1ToAncestor);
            System.out.println("Path from vertex 3 to ancestor: " + shortestAncestralPath3.shortestPathFromVertex2ToAncestor);
        }
        System.out.println("Expected: 1");
        System.out.println("2->1");
        System.out.println("3->1\n");


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

        ShortestAncestralPathResult shortestAncestralPath4 = shortestAncestralPath.getShortestAncestralPath(digraph4, 2, 3);
        if (shortestAncestralPath4.commonAncestor == -1) {
            System.out.println("Common ancestor in digraph 4: Common ancestor not found");
        } else {
            System.out.println("Common ancestor in digraph 4: " + shortestAncestralPath4.commonAncestor);
            System.out.println("Path from vertex 2 to ancestor: " + shortestAncestralPath4.shortestPathFromVertex1ToAncestor);
            System.out.println("Path from vertex 3 to ancestor: " + shortestAncestralPath4.shortestPathFromVertex2ToAncestor);
        }
        System.out.println("Expected: 7");
        System.out.println("2->7");
        System.out.println("3->8 8->7\n");


        List<Integer>[] digraph5 = (List<Integer>[]) new ArrayList[4];
        for(int vertex = 0; vertex < digraph5.length; vertex++) {
            digraph5[vertex] = new ArrayList<>();
        }

        digraph5[0].add(1);
        digraph5[1].add(2);

        ShortestAncestralPathResult shortestAncestralPath5 = shortestAncestralPath.getShortestAncestralPath(digraph5, 2, 3);
        if (shortestAncestralPath5.commonAncestor == -1) {
            System.out.println("Common ancestor in digraph 5: Common ancestor not found");
        } else {
            System.out.println("Common ancestor in digraph 5: " + shortestAncestralPath5.commonAncestor);
            System.out.println("Path from vertex 2 to ancestor: " + shortestAncestralPath5.shortestPathFromVertex1ToAncestor);
            System.out.println("Path from vertex 3 to ancestor: " + shortestAncestralPath5.shortestPathFromVertex2ToAncestor);
        }
        System.out.println("Expected: Common ancestor not found");
    }

}
