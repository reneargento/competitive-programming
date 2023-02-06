package algorithms.graph.shortest.path.integer.weights;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.*;

/**
 * Created by Rene Argento on 25/12/17.
 */
// O(E + K * V) = O(E + V), since K is a constant, where K = max possible path length = maxWeight * (V - 1)
// Since there may be negative edge weights, a constant number of linear scans in the distances[] array may happen
// and some vertices may be relaxed more than once.
// This leaves the algorithm runtime below linearithmic, but not necessarily linear.
@SuppressWarnings("unchecked")
public class BellmanFordIntegerWeights {

    private final int[] distTo;                  // length of path to vertex
    private final DirectedEdge[] edgeTo;         // last edge on path to vertex
    private int callsToRelax;              // number of calls to relax()
    private Iterable<DirectedEdge> cycle;  // if there is a negative cycle in edgeTo[], return it

    // The possible path distances are in the range [-maxWeight * (V - 1), ..., maxWeight * (V - 1)]
    // because the maximum number of edges in any path is V - 1.
    // We cannot use negative indexes in an array, so we add maxWeight * (V - 1) to each index,
    // to be able to index all the distances.
    private final ArrayList<Integer>[] distances; // Theoretically, an array of HashSets would be faster, but in practice
    // an array of ArrayLists has proven to be faster in this case
    private final int maxPathDistance;

    @SuppressWarnings("PointlessArithmeticExpression")
    public BellmanFordIntegerWeights(EdgeWeightedDigraph edgeWeightedDigraph, int source, int maxWeight) {
        distTo = new int[edgeWeightedDigraph.vertices()];
        edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];

        maxPathDistance = maxWeight * (edgeWeightedDigraph.vertices() - 1);
        distances = (ArrayList<Integer>[]) new ArrayList[maxPathDistance * 2 + 1];

        for (int distance = 0; distance < distances.length; distance++) {
            distances[distance] = new ArrayList<>();
        }

        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            distTo[vertex] = Integer.MAX_VALUE;
        }

        distTo[source] = 0;
        // Using 0 + maxPathDistance for readability: we always add maxPathDistance to indexes in distances[] because
        // of possible negative distances
        distances[0 + maxPathDistance].add(source);
        int lastComputedShortestDistance = 0 + maxPathDistance;

        while (!hasNegativeCycle()) {
            int nextVertexToRelax = getShortestDistanceVertex(lastComputedShortestDistance);

            if (nextVertexToRelax == -1) {
                // All shortest distances have been found
                break;
            }

            lastComputedShortestDistance = relax(edgeWeightedDigraph, nextVertexToRelax);
        }
    }

    // Total runtime in the entire algorithm of O(V * K), where K = max possible path length
    // Does a linear scan to find the next closest vertex, but keeps track of the position of the last vertex found
    // to do a constant number of scans in the distances[] array during the entire algorithm
    private int getShortestDistanceVertex(int lastComputedShortestDistance) {
        while (distances[lastComputedShortestDistance].isEmpty()) {
            lastComputedShortestDistance++;

            if (lastComputedShortestDistance == distances.length) {
                return -1;
            }
        }

        Integer vertexToRemove = distances[lastComputedShortestDistance].get(0);
        distances[lastComputedShortestDistance].remove(vertexToRemove);
        return vertexToRemove;
    }

    // Total runtime in the entire algorithm is ~ E
    // Cannot guarantee E because a vertex may be relaxed more than once if a new shortest-path to it is found
    // due to negative edge weights.
    private int relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
        int lastComputedShortestDistance = distTo[vertex] + maxPathDistance;

        for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            Integer neighbor = edge.to();

            if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                if (hasPathTo(neighbor)) {
                    int distancesIndex = distTo[neighbor] + maxPathDistance;
                    distances[distancesIndex].remove(neighbor);
                }

                distTo[neighbor] = distTo[vertex] + (int) edge.weight();
                edgeTo[neighbor] = edge;

                int distancesIndex = distTo[neighbor] + maxPathDistance;
                distances[distancesIndex].add(neighbor);

                if (distTo[neighbor] + maxPathDistance < lastComputedShortestDistance) {
                    lastComputedShortestDistance = distTo[neighbor] + maxPathDistance;
                }
            }

            if (callsToRelax++ % edgeWeightedDigraph.vertices() == 0) {
                findNegativeCycle();
            }
        }
        return lastComputedShortestDistance;
    }

    public int distTo(int vertex) {
        return distTo[vertex];
    }

    public boolean hasPathTo(int vertex) {
        return distTo[vertex] != Integer.MAX_VALUE;
    }

    public Iterable<DirectedEdge> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }

        Deque<DirectedEdge> path = new ArrayDeque<>();
        for (DirectedEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
            path.push(edge);
        }
        return path;
    }

    private void findNegativeCycle() {
        int vertices = edgeTo.length;
        List<DirectedEdge>[] shortestPathsTree = (List<DirectedEdge>[]) new ArrayList[vertices];

        for (int vertex = 0; vertex < vertices; vertex++) {
            shortestPathsTree[vertex] = new ArrayList<>();
        }

        for (int vertex = 0; vertex < vertices; vertex++) {
            if (edgeTo[vertex] != null) {
                DirectedEdge edge = edgeTo[vertex];
                shortestPathsTree[edge.from()].add(edge);
            }
        }
        new HasDirectedWeightedCycle(shortestPathsTree);
        cycle = HasDirectedWeightedCycle.cycle();
    }

    public boolean hasNegativeCycle() {
        return cycle != null;
    }

    public Iterable<DirectedEdge> negativeCycle() {
        return cycle;
    }

    public static class HasDirectedWeightedCycle {
        private static boolean visited[];
        private static DirectedEdge[] edgeTo;
        private static Deque<DirectedEdge> cycle;    // vertices on  a cycle (if one exists)
        private static boolean[] onStack;            // vertices on recursive call stack

        public HasDirectedWeightedCycle(List<DirectedEdge>[] adjacent) {
            onStack = new boolean[adjacent.length];
            edgeTo = new DirectedEdge[adjacent.length];
            visited = new boolean[adjacent.length];

            for (int vertex = 0; vertex < adjacent.length; vertex++) {
                if (!visited[vertex]) {
                    dfs(adjacent, vertex);
                }
            }
        }

        private void dfs(List<DirectedEdge>[] adjacent, int vertex) {
            onStack[vertex] = true;
            visited[vertex] = true;

            for (DirectedEdge edge : adjacent[vertex]) {
                int neighbor = edge.to();

                if (hasCycle()) {
                    return;
                } else if (!visited[neighbor]) {
                    edgeTo[neighbor] = edge;
                    dfs(adjacent, neighbor);
                } else if (onStack[neighbor]) {
                    cycle = new ArrayDeque<>();

                    cycle.push(edge);

                    for (DirectedEdge edgeInCycle = edgeTo[vertex]; edgeInCycle != null && edgeInCycle.from() != vertex;
                         edgeInCycle = edgeTo[edgeInCycle.from()]) {
                        cycle.push(edgeInCycle);
                    }
                    cycle.push(edge);
                }
            }
            onStack[vertex] = false;
        }

        public static boolean hasCycle() {
            return cycle != null;
        }

        public static Iterable<DirectedEdge> cycle() {
            return cycle;
        }
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph edgeWeightedDigraph1 = new EdgeWeightedDigraph(5);
        edgeWeightedDigraph1.addEdge(new DirectedEdge(0, 1, -10));
        edgeWeightedDigraph1.addEdge(new DirectedEdge(1, 2, -40));
        edgeWeightedDigraph1.addEdge(new DirectedEdge(2, 3, 40));

        int source1 = 0;
        int maxWeight1 = 40;
        BellmanFordIntegerWeights bellmanFordIntegerWeights1 =
                new BellmanFordIntegerWeights(edgeWeightedDigraph1, source1, maxWeight1);

        System.out.println("Shortest-paths-tree 1");
        bellmanFordIntegerWeights1.printShortestPathsTree(edgeWeightedDigraph1, source1);

        System.out.println("\nExpected:");
        System.out.println("0 to 0 (0)  \n" +
                "0 to 1 (-10)  0->1 -10.00   \n" +
                "0 to 2 (-50)  0->1 -10.00   1->2 -40.00   \n" +
                "0 to 3 (-10)  0->1 -10.00   1->2 -40.00   2->3 40.00   \n" +
                "0 to 4         no path");

        EdgeWeightedDigraph edgeWeightedDigraph2 = new EdgeWeightedDigraph(4);
        edgeWeightedDigraph2.addEdge(new DirectedEdge(0, 1, -5));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(1, 2, -5));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(2, 3, -5));

        int source2 = 0;
        int maxWeight2 = 5;
        BellmanFordIntegerWeights bellmanFordSPBoundedIntegerWeights2 =
                new BellmanFordIntegerWeights(edgeWeightedDigraph2, source2, maxWeight2);

        System.out.println("\nShortest-paths-tree 2");
        bellmanFordSPBoundedIntegerWeights2.printShortestPathsTree(edgeWeightedDigraph2, source2);

        System.out.println("\nExpected:");
        System.out.println("0 to 0 (0)  \n" +
                "0 to 1 (-5)  0->1 -5.00   \n" +
                "0 to 2 (-10)  0->1 -5.00   1->2 -5.00   \n" +
                "0 to 3 (-15)  0->1 -5.00   1->2 -5.00   2->3 -5.00");

        EdgeWeightedDigraph edgeWeightedDigraph3 = new EdgeWeightedDigraph(8);
        edgeWeightedDigraph3.addEdge(new DirectedEdge(4, 5, 35));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(5, 4, 35));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(4, 7, 37));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(5, 7, 28));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(7, 5, 28));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(5, 1, 32));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(0, 4, 38));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(0, 2, 26));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(7, 3, 39));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(1, 3, 29));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(2, 7, 34));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(6, 2, -120));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(3, 6, 52));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(6, 0, -140));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(6, 4, -125));

        int source3 = 0;
        int maxWeight3 = 125;
        BellmanFordIntegerWeights bellmanFordSPBoundedIntegerWeights3 =
                new BellmanFordIntegerWeights(edgeWeightedDigraph3, source3, maxWeight3);

        System.out.println("\nShortest-paths-tree 3");
        bellmanFordSPBoundedIntegerWeights3.printShortestPathsTree(edgeWeightedDigraph3, source3);

        System.out.println("\nExpected:");
        System.out.println("0 to 0 (0)  \n" +
                "0 to 1 (93)  0->2 26.00   2->7 34.00   7->3 39.00   3->6 52.00   6->4 -125.00   4->5 35.00   5->1 32.00   \n" +
                "0 to 2 (26)  0->2 26.00   \n" +
                "0 to 3 (99)  0->2 26.00   2->7 34.00   7->3 39.00   \n" +
                "0 to 4 (26)  0->2 26.00   2->7 34.00   7->3 39.00   3->6 52.00   6->4 -125.00   \n" +
                "0 to 5 (61)  0->2 26.00   2->7 34.00   7->3 39.00   3->6 52.00   6->4 -125.00   4->5 35.00   \n" +
                "0 to 6 (151)  0->2 26.00   2->7 34.00   7->3 39.00   3->6 52.00   \n" +
                "0 to 7 (60)  0->2 26.00   2->7 34.00 ");
    }

    private void printShortestPathsTree(EdgeWeightedDigraph edgeWeightedDigraph,
                                        int source) {
        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            if (hasPathTo(vertex)) {
                System.out.printf("%d to %d (%d)  ", source, vertex, distTo(vertex));

                for (DirectedEdge edge : pathTo(vertex)) {
                    System.out.print(edge + "   ");
                }
                System.out.println();
            } else {
                System.out.printf("%d to %d         no path\n", source, vertex);
            }
        }
    }
}
