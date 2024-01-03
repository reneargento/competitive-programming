package algorithms.graph.minimum.spanning.tree;

import datastructures.UnionFind;
import datastructures.graph.Edge;
import datastructures.graph.EdgeWeightedGraph;

import java.util.*;

/**
 * Created by Rene Argento on 02/01/24.
 */
// Computes the critical edges of a MST (edges whose deletion from the graph would cause the MST weight to increase)
// in O(E lg E)
// Based on https://stackoverflow.com/questions/15720155/find-all-critical-edges-of-an-mst
public class CriticalEdges {

    // O(E lg E)
    public Queue<Edge> findCriticalEdges(EdgeWeightedGraph edgeWeightedGraph) {
        Queue<Edge> criticalEdges = new LinkedList<>();

        // Modified Kruskal's algorithm
        Queue<Edge> minimumSpanningTree = new LinkedList<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>();

        for (Edge edge : edgeWeightedGraph.edges()) {
            priorityQueue.offer(edge);
        }
        UnionFind unionFind = new UnionFind(edgeWeightedGraph.vertices());

        while (!priorityQueue.isEmpty() && minimumSpanningTree.size() < edgeWeightedGraph.vertices() - 1) {
            Edge edge = priorityQueue.poll();

            int vertex1 = edge.either();
            int vertex2 = edge.other(vertex1);

            // Ineligible edges are never critical edges
            if (unionFind.connected(vertex1, vertex2)) {
                continue;
            }

            // Get next equal-weight edge block
            double currentWeight = edge.weight();

            Queue<Edge> equalWeightEdgesBlock = new LinkedList<>();
            equalWeightEdgesBlock.offer(edge);

            while (!priorityQueue.isEmpty() && priorityQueue.peek().weight() == currentWeight) {
                equalWeightEdgesBlock.offer(priorityQueue.poll());
            }

            if (equalWeightEdgesBlock.size() == 1) {
                criticalEdges.offer(edge); // There is no cycle, so this is a critical edge

                unionFind.union(vertex1, vertex2);
                minimumSpanningTree.offer(edge);
            } else {
                // Generate subgraph with the current components
                GraphX subgraph = new GraphX();

                for (Edge edgeInCurrentBlock : equalWeightEdgesBlock) {
                    vertex1 = edgeInCurrentBlock.either();
                    vertex2 = edgeInCurrentBlock.other(vertex1);

                    int component1 = unionFind.find(vertex1);
                    int component2 = unionFind.find(vertex2);
                    if (component1 == component2) {
                        continue;
                    }

                    MarkedEdge subGraphEdge = new MarkedEdge(component1, component2, currentWeight);
                    subgraph.addEdge(subGraphEdge);
                }

                BridgeFinder bridgeFinder = new BridgeFinder(subgraph);
                // All bridges are critical edges
                for (Edge bridgeEdge : bridgeFinder.bridges()) {
                    criticalEdges.offer(bridgeEdge);
                }

                // Add all edges that belong to an MST to the MST
                for (Edge edgeInCurrentBlock : equalWeightEdgesBlock) {
                    vertex1 = edgeInCurrentBlock.either();
                    vertex2 = edgeInCurrentBlock.other(vertex1);

                    if (!unionFind.connected(vertex1, vertex2)) {
                        unionFind.union(vertex1, vertex2);
                        minimumSpanningTree.offer(edge); // Add edge to the minimum spanning tree
                    }
                }
            }
        }
        return criticalEdges;
    }

    private static class MarkedEdge extends Edge {
        boolean marked;

        public MarkedEdge(int vertex1, int vertex2, double weight) {
            super(vertex1, vertex2, weight);
        }
    }

    private static class GraphX {
        private final Map<Integer, List<MarkedEdge>> adjacent; // optimal for very sparse graphs

        public GraphX() {
            adjacent = new HashMap<>();
        }

        public void addEdge(MarkedEdge edge) {
            int vertex1 = edge.either();
            int vertex2 = edge.other(vertex1);
            int[] vertices = new int[] { vertex1, vertex2 };

            for (int vertex : vertices) {
                if (!adjacent.containsKey(vertex)) {
                    adjacent.put(vertex, new ArrayList<>());
                }
                adjacent.get(vertex).add(edge);
            }
        }

        public Iterable<MarkedEdge> adjacent(int vertex) {
            return adjacent.get(vertex);
        }

        // Only include vertices with degree > 0
        public Iterable<Integer> vertices() {
            return adjacent.keySet();
        }
    }

    private static class BridgeFinder {
        private final Queue<Edge> bridges = new LinkedList<>();
        private int dfsCount;          // dfs invocation count
        private final Map<Integer, Integer> order;     // order[v] = order in which dfs examines v
        private final Map<Integer, Integer> low;       // low[v] = lowest preorder of any vertex connected to v

        public BridgeFinder(GraphX graph) {
            order = new HashMap<>();
            low = new HashMap<>();

            for (Integer vertex : graph.vertices())
                if (order.get(vertex) == null) {
                    dfs(graph, vertex, vertex);
                }
        }

        private void dfs(GraphX graph, int from, int vertex) {
            order.put(vertex, ++dfsCount);
            low.put(vertex, dfsCount);

            for (MarkedEdge edge : graph.adjacent(vertex)) {
                if (edge.marked) {
                    continue;
                }
                edge.marked = true;

                int otherVertex = edge.other(vertex);
                if (order.get(otherVertex) == null) { // new edges, otherVertex is a descendant of vertex
                    dfs(graph, vertex, otherVertex);
                    low.put(vertex, Math.min(low.get(vertex), low.get(otherVertex)));

                    if (low.get(otherVertex).equals(order.get(otherVertex))) {
                        bridges.offer(edge);
                    }
                } else { // back edges, otherVertex is an ancestor of vertex
                    low.put(vertex, Math.min(low.get(vertex), order.get(otherVertex)));
                }
            }
        }

        public Iterable<Edge> bridges() {
            return bridges;
        }
    }

    public static void main(String[] args) {
        CriticalEdges criticalEdges = new CriticalEdges();

        EdgeWeightedGraph edgeWeightedGraph1 = new EdgeWeightedGraph(4);
        edgeWeightedGraph1.addEdge(new Edge(0, 1, 1)); // Critical edge
        edgeWeightedGraph1.addEdge(new Edge(1, 2, 3));
        edgeWeightedGraph1.addEdge(new Edge(2, 3, 2)); // Critical edge
        edgeWeightedGraph1.addEdge(new Edge(3, 0, 3));

        System.out.println("Critical edges 1:");

        Queue<Edge> criticalEdges1 = criticalEdges.findCriticalEdges(edgeWeightedGraph1);
        for (Edge edge : criticalEdges1) {
            System.out.println(edge);
        }

        System.out.println("Expected:\n" +
                "0-1 1.00000\n" +
                "2-3 2.00000\n");

        EdgeWeightedGraph edgeWeightedGraph2 = new EdgeWeightedGraph(4);
        edgeWeightedGraph2.addEdge(new Edge(0, 1, 5));
        edgeWeightedGraph2.addEdge(new Edge(1, 2, 5));
        edgeWeightedGraph2.addEdge(new Edge(0, 2, 5));
        edgeWeightedGraph2.addEdge(new Edge(2, 3, 3)); // Critical edge

        System.out.println("Critical edges 2:");

        Queue<Edge> criticalEdges2 = criticalEdges.findCriticalEdges(edgeWeightedGraph2);
        for (Edge edge : criticalEdges2) {
            System.out.println(edge);
        }

        System.out.println("Expected:\n" +
                "2-3 3.00000");
    }
}
