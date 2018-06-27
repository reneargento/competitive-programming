package com.br.movile.code.challenge;

import java.util.*;

@SuppressWarnings("unchecked")
public class iFoodDelivery {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int bikers = scanner.nextInt();
        int bikes = scanner.nextInt();
        int racers = scanner.nextInt();

        GridPosition[] bikersArray = new GridPosition[bikers];
        GridPosition[] bikesArray = new GridPosition[bikes];

        for(int biker = 0; biker < bikers; biker++) {
            int row = scanner.nextInt();
            int column = scanner.nextInt();

            bikersArray[biker] = new GridPosition(biker, row, column);
        }

        for(int bike = 0; bike < bikes; bike++) {
            int row = scanner.nextInt();
            int column = scanner.nextInt();

            bikesArray[bike] = new GridPosition(bike, row, column);
        }

        long[][] distances = new long[bikers][bikes];

        for(int biker = 0; biker < bikers; biker++) {
            for(int bike = 0; bike < bikes; bike++) {
                distances[biker][bike] = (bikersArray[biker].row - bikesArray[bike].row) *
                        (bikersArray[biker].row - bikesArray[bike].row) +
                        (bikersArray[biker].column - bikesArray[bike].column) *
                                (bikersArray[biker].column - bikesArray[bike].column);
            }
        }

        long low = 0;
        long high = 1000000000;

        long maxDistance = 0;

        while (low <= high) {
            long middleTime = low + (high - low) / 2;

            if (checkIfKBikersCanGetBikes(distances, middleTime, racers)) {
                maxDistance = middleTime;
                high = middleTime - 1;
            } else {
                low = middleTime + 1;
            }
        }

        System.out.println(maxDistance);
        scanner.close();
    }

    private static boolean checkIfKBikersCanGetBikes(long[][] distances, long time, int racers) {

        int bikers = distances.length;
        int bikes = distances[0].length;

        int totalVertices = bikers + bikes;
        Graph graph = new Graph(totalVertices);

        for(int biker = 0; biker < bikers; biker++) {
            for(int bike = 0; bike < bikes; bike++) {
                if (distances[biker][bike] <= time) {
                    graph.addEdge(biker, bike + bikers);
                }
            }
        }

        HopcroftKarp hopcroftKarp = new HopcroftKarp(graph);

        long matches = hopcroftKarp.size();
        return matches >= racers;
    }

    public static class Graph {

        private final int vertices;
        private List<Integer>[] adjacent;

        public Graph(int vertices) {
            this.vertices = vertices;
            adjacent = (List<Integer>[]) new ArrayList[vertices];

            for(int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public void addEdge(int vertex1, int vertex2) {
            adjacent[vertex1].add(vertex2);
            adjacent[vertex2].add(vertex1);
        }

        public Iterable<Integer> adjacent(int vertex) {
            return adjacent[vertex];
        }
    }

    private static class GridPosition {
        int id;
        long row;
        long column;

        GridPosition(int id, long row, long column) {
            this.id = id;
            this.row = row;
            this.column = column;
        }
    }

    // Based on https://algs4.cs.princeton.edu/41graph/BipartiteX.java.html
    public static class BipartiteX {
        private static final boolean WHITE = false;

        private boolean isBipartite;   // is the graph bipartite?
        private boolean[] color;       // color[v] gives vertices on one side of bipartition
        private boolean[] marked;      // marked[v] = true if v has been visited in DFS
        private int[] edgeTo;          // edgeTo[v] = last edge on path to v
        private Queue<Integer> cycle;  // odd-length cycle

        public BipartiteX(Graph graph) {
            isBipartite = true;
            color  = new boolean[graph.vertices()];
            marked = new boolean[graph.vertices()];
            edgeTo = new int[graph.vertices()];

            for (int vertex = 0; vertex < graph.vertices() && isBipartite; vertex++) {
                if (!marked[vertex]) {
                    bfs(graph, vertex);
                }
            }
        }

        private void bfs(Graph graph, int source) {
            Queue<Integer> queue = new LinkedList<>();
            color[source] = WHITE;
            marked[source] = true;
            queue.offer(source);

            while (!queue.isEmpty()) {
                int vertex = queue.poll();
                for (int neighbor : graph.adjacent(vertex)) {
                    if (!marked[neighbor]) {
                        marked[neighbor] = true;
                        edgeTo[neighbor] = vertex;
                        color[neighbor] = !color[vertex];
                        queue.offer(neighbor);
                    } else if (color[neighbor] == color[vertex]) {
                        isBipartite = false;

                        cycle = new LinkedList<>();
                        Deque<Integer> stack = new ArrayDeque<>();
                        int x = vertex;
                        int y = neighbor;

                        while (x != y) {
                            stack.push(x);
                            cycle.offer(y);
                            x = edgeTo[x];
                            y = edgeTo[y];
                        }

                        stack.push(x);
                        while (!stack.isEmpty()) {
                            cycle.offer(stack.pop());
                        }

                        cycle.offer(neighbor);
                        return;
                    }
                }
            }
        }

        public boolean isBipartite() {
            return isBipartite;
        }

        public boolean color(int vertex) {
            if (!isBipartite) {
                throw new UnsupportedOperationException("Graph is not bipartite");
            }
            return color[vertex];
        }
    }

    // O(E * sqrt(vertices))
    // Based on https://algs4.cs.princeton.edu/65reductions/HopcroftKarp.java.html
    public static class HopcroftKarp {
        private static final int UNMATCHED = -1;

        private final int vertices;          // number of vertices in the graph
        private BipartiteX bipartition;      // the bipartition
        private int cardinality;             // cardinality of current matching
        private int[] mate;                  // mate[v] =  w if v-w is an edge in current matching
        private boolean[] inMinVertexCover;  // inMinVertexCover[v] = true iff v is in min vertex cover
        private boolean[] marked;            // marked[v] = true iff v is reachable via alternating path
        private int[] distTo;                // distTo[v] = number of edges on shortest path to v

        public HopcroftKarp(Graph graph) {
            bipartition = new BipartiteX(graph);
            if (!bipartition.isBipartite()) {
                throw new IllegalArgumentException("graph is not bipartite");
            }

            // initialize empty matching
            this.vertices = graph.vertices();
            mate = new int[vertices];
            for (int vertex = 0; vertex < vertices; vertex++) {
                mate[vertex] = UNMATCHED;
            }

            // the call to hasAugmentingPath() provides enough info to reconstruct level graph
            while (hasAugmentingPath(graph)) {

                // to be able to iterate over each adjacency list, keeping track of which
                // vertex in each adjacency list needs to be explored next
                Iterator<Integer>[] adjacent = (Iterator<Integer>[]) new Iterator[graph.vertices()];
                for (int vertex = 0; vertex < graph.vertices(); vertex++) {
                    adjacent[vertex] = graph.adjacent(vertex).iterator();
                }

                // for each unmatched vertex source on one side of bipartition
                for (int source = 0; source < vertices; source++) {
                    if (isMatched(source) || !bipartition.color(source)) {
                        continue;
                    }

                    // find augmenting path from source using nonrecursive DFS
                    Stack<Integer> path = new Stack<Integer>();
                    path.push(source);
                    while (!path.isEmpty()) {
                        int vertex = path.peek();

                        // retreat, no more edges in level graph leaving vertex
                        if (!adjacent[vertex].hasNext()) {
                            path.pop();
                        } else {
                            // process edge vertex-w only if it is an edge in level graph
                            int w = adjacent[vertex].next();
                            if (!isLevelGraphEdge(vertex, w)) {
                                continue;
                            }

                            // add w to augmenting path
                            path.push(w);

                            // augmenting path found: update the matching
                            if (!isMatched(w)) {
                                while (!path.isEmpty()) {
                                    int x = path.pop();
                                    int y = path.pop();
                                    mate[x] = y;
                                    mate[y] = x;
                                }
                                cardinality++;
                            }
                        }
                    }
                }
            }

            // also find a min vertex cover
            inMinVertexCover = new boolean[vertices];

            for (int vertex = 0; vertex < vertices; vertex++) {
                if (bipartition.color(vertex) && !marked[vertex]) {
                    inMinVertexCover[vertex] = true;
                }
                if (!bipartition.color(vertex) && marked[vertex]) {
                    inMinVertexCover[vertex] = true;
                }
            }
        }

        // is the edge vertex1-vertex2 in the level graph?
        private boolean isLevelGraphEdge(int vertex1, int vertex2) {
            return (distTo[vertex2] == distTo[vertex1] + 1) && isResidualGraphEdge(vertex1, vertex2);
        }

        // is the edge vertex1-vertex2 a forward edge not in the matching or a reverse edge in the matching?
        private boolean isResidualGraphEdge(int vertex1, int vertex2) {
            if ((mate[vertex1] != vertex2) && bipartition.color(vertex1)) {
                return true;
            }
            if ((mate[vertex1] == vertex2) && !bipartition.color(vertex1)) {
                return true;
            }
            return false;
        }

        private boolean hasAugmentingPath(Graph graph) {

            // shortest path distances
            marked = new boolean[vertices];
            distTo = new int[vertices];
            for (int vertex = 0; vertex < vertices; vertex++) {
                distTo[vertex] = Integer.MAX_VALUE;
            }

            // breadth-first search (starting from all unmatched vertices on one side of bipartition)
            Queue<Integer> queue = new LinkedList<>();
            for (int vertex = 0; vertex < vertices; vertex++) {
                if (bipartition.color(vertex) && !isMatched(vertex)) {
                    queue.offer(vertex);
                    marked[vertex] = true;
                    distTo[vertex] = 0;
                }
            }

            // run BFS until an augmenting path is found
            // (and keep going until all vertices at that distance are explored)
            boolean hasAugmentingPath = false;
            while (!queue.isEmpty()) {
                int vertex = queue.poll();

                for (int neighbor : graph.adjacent(vertex)) {

                    // forward edge not in matching or backwards edge in matching
                    if (isResidualGraphEdge(vertex, neighbor)) {
                        if (!marked[neighbor]) {
                            distTo[neighbor] = distTo[vertex] + 1;
                            marked[neighbor] = true;
                            if (!isMatched(neighbor)) {
                                hasAugmentingPath = true;
                            }

                            // stop enqueuing vertices once an alternating path has been discovered
                            // (no vertex on same side will be marked if its shortest path distance longer)
                            if (!hasAugmentingPath) {
                                queue.offer(neighbor);
                            }
                        }
                    }
                }
            }

            return hasAugmentingPath;
        }

        public boolean isMatched(int v) {
            return mate[v] != UNMATCHED;
        }

        public int size() {
            return cardinality;
        }
    }
}