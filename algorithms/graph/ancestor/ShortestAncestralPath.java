package algorithms.graph.ancestor;

import java.util.*;

/**
 * Created by rene on 24/10/17.
 */
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

    //O(V + E)
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

        int[] distancesFromVertex1 = new int[reverseDigraph.length];
        int[] distancesFromVertex2 = new int[reverseDigraph.length];

        for(int vertex = 0; vertex < reverseDigraph.length; vertex++) {
            distancesFromVertex1[vertex] = Integer.MAX_VALUE;
            distancesFromVertex2[vertex] = Integer.MAX_VALUE;
        }

        // 2- Do a BFS from vertex1 to find all its ancestors and compute the distance from vertex1 to them
        bfs(reverseDigraph, vertex1, distancesFromVertex1);

        // 3- Do a BFS from vertex2 to find all its ancestors and compute the distance from vertex2 to them
        bfs(reverseDigraph, vertex2, distancesFromVertex2);

        // 4- Find the common ancestor with a shortest ancestral path
        int commonAncestorWithShortestPath = -1;
        int shortestDistance = Integer.MAX_VALUE;

        for(int vertex = 0; vertex < reverseDigraph.length; vertex++) {
            if (distancesFromVertex1[vertex] != Integer.MAX_VALUE
                    && distancesFromVertex2[vertex] != Integer.MAX_VALUE
                    && distancesFromVertex1[vertex] + distancesFromVertex2[vertex] < shortestDistance) {
                shortestDistance = distancesFromVertex1[vertex] + distancesFromVertex2[vertex];
                commonAncestorWithShortestPath = vertex;
            }
        }

        if (commonAncestorWithShortestPath == -1) {
            return new ShortestAncestralPathResult(-1, null, null);
        }

        // 5- Do a BFS from vertex1 to the common ancestor to get the shortest path
        String shortestPathFromVertex1ToAncestor = bfsToGetPath(reverseDigraph, vertex1, commonAncestorWithShortestPath);

        // 6- Do a BFS from vertex2 to the common ancestor to get the shortest path
        String shortestPathFromVertex2ToAncestor = bfsToGetPath(reverseDigraph, vertex2, commonAncestorWithShortestPath);

        return new ShortestAncestralPathResult(commonAncestorWithShortestPath, shortestPathFromVertex1ToAncestor,
                shortestPathFromVertex2ToAncestor);
    }

    private void bfs(List<Integer>[] adjacent, int source, int[] distances) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);
        distances[source] = 0;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for(int neighbor : adjacent[currentVertex]) {
                distances[neighbor] = distances[currentVertex] + 1;
                queue.offer(neighbor);
            }
        }
    }

    private String bfsToGetPath(List<Integer>[] adjacent, int source, int target) {
        int[] edgeTo = new int[adjacent.length];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(source);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for(int neighbor : adjacent[currentVertex]) {
                edgeTo[neighbor] = currentVertex;
                queue.offer(neighbor);

                if (neighbor == target) {
                    Deque<Integer> inversePath = new ArrayDeque<>();

                    for(int vertex = target; vertex != source; vertex = edgeTo[vertex]) {
                        inversePath.push(vertex);
                    }
                    inversePath.push(source);

                    StringBuilder path = new StringBuilder();

                    while (!inversePath.isEmpty()) {
                        int vertexInPath = inversePath.pop();

                        if (!inversePath.isEmpty()) {
                            int nextVertexInPath = inversePath.peek();
                            path.append(vertexInPath).append("->").append(nextVertexInPath);
                        }

                        if (inversePath.size() > 1) {
                            path.append(" ");
                        }
                    }

                    return path.toString();
                }
            }
        }

        //If we got here, there is not path from source vertex to target vertex
        return null;
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
