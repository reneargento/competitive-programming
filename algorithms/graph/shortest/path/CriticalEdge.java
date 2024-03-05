package algorithms.graph.shortest.path;

import algorithms.graph.shortest.path.dijkstra.DijkstraOptimized;
import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.*;

/**
 * Created by Rene Argento on 03/03/24.
 */
// This algorithm finds an edge whose removal causes maximal increase in the shortest-paths length
// from one given vertex to another given vertex in a given edge-weighted digraph.
// Runtime Complexity: O(E lg V)
// Based on http://bababadalgharaghtakamminarronnkonnbro.blogspot.com.br/2012/06/interviewstreet-going-office.html
public class CriticalEdge {

    public DirectedEdge getCriticalEdge(EdgeWeightedDigraph edgeWeightedDigraph, int source, int target) {
        // 1- Compute reverse digraph
        // It will be used to compute the shortest paths using the target vertex as a source.
        EdgeWeightedDigraph reverseDigraph = new EdgeWeightedDigraph(edgeWeightedDigraph.vertices());

        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                reverseDigraph.addEdge(new DirectedEdge(edge.to(), edge.from(), edge.weight()));
            }
        }

        // 2- Get shortest paths from source
        DijkstraOptimized dijkstra = new DijkstraOptimized(edgeWeightedDigraph, source);
        if (!dijkstra.hasPathTo(target)) {
            return null;
        }

        // 3- Get shortest paths from the target
        DijkstraOptimized dijkstraFromTarget = new DijkstraOptimized(reverseDigraph, target);

        // 4- Get the islands in the graph.
        // The i-th island is the set of all vertices v, such that there exists a shortest path
        // from source to v using no more than i shortest-path vertices.
        int[] islands = getIslands(reverseDigraph, dijkstra, target);

        // 5- Compute bypass path lengths
        SegmentTree bypassPathLengths = computeBypassPathLengths(edgeWeightedDigraph, islands, dijkstra,
                dijkstraFromTarget, target);

        // 6- Return a critical edge, which is an edge that has the highest bypass length
        return getCriticalEdge(bypassPathLengths, dijkstra, target);
    }

    private int[] getIslands(EdgeWeightedDigraph reverseDigraph, DijkstraOptimized dijkstra, int target) {
        int[] islands = new int[reverseDigraph.vertices()];
        Arrays.fill(islands, -1);

        boolean[] isInShortestPath = new boolean[reverseDigraph.vertices()];
        int islandId = 0;
        for (DirectedEdge edge : dijkstra.pathTo(target)) {
            if (islands[edge.from()] == -1) {
                islands[edge.from()] = islandId++;
            }
            islands[edge.to()] = islandId++;
            isInShortestPath[edge.from()] = true;
            isInShortestPath[edge.to()] = true;
        }

        // Do a breadth-first walk to find the island number of vertices that are not on the shortest path
        // from source to target.
        // These vertices are on a path from source to target that is not a shortest path.
        boolean[] visited = new boolean[reverseDigraph.vertices()];
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(target);
        visited[target] = true;

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for (DirectedEdge edge : reverseDigraph.adjacent(currentVertex)) {
                int neighbor = edge.to();

                if (!visited[neighbor]) {
                    visited[neighbor] = true;

                    if (!isInShortestPath[neighbor] && islands[currentVertex] > islands[neighbor]) {
                        islands[neighbor] = islands[currentVertex];
                    }
                    queue.offer(edge.to());
                }
            }
        }
        return islands;
    }

    private SegmentTree computeBypassPathLengths(EdgeWeightedDigraph edgeWeightedDigraph, int[] islands,
                                                 DijkstraOptimized dijkstra, DijkstraOptimized dijkstraFromTarget,
                                                 int target) {
        // bypassPathLengths[i] denotes the length of the shortest path that bypasses ei, where ei is the ith edge in
        // the shortest path from source to target.
        double[] bypassPathLengths = new double[edgeWeightedDigraph.vertices()];
        Arrays.fill(bypassPathLengths, Double.POSITIVE_INFINITY);

        Set<DirectedEdge> edgesInShortestPath = new HashSet<>();
        for (DirectedEdge edge : dijkstra.pathTo(target)) {
            edgesInShortestPath.add(edge);
        }

        SegmentTree segmentTree = new SegmentTree(bypassPathLengths);

        for (DirectedEdge edge : edgeWeightedDigraph.edges()) {
            if (!edgesInShortestPath.contains(edge)) {
                int island1 = islands[edge.from()];
                int island2 = islands[edge.to()];

                if (island1 < island2
                        && island1 != -1 && island2 != -1) {
                    double shortestPathLength = dijkstra.distTo(edge.from()) + edge.weight()
                            + dijkstraFromTarget.distTo(edge.to());

                    double currentShortestPathLength = segmentTree.rangeMinQuery(island1, island2 - 1);

                    if (shortestPathLength < currentShortestPathLength) {
                        segmentTree.update(island1, island2 - 1, shortestPathLength);
                    }
                }
            }
        }
        return segmentTree;
    }

    private DirectedEdge getCriticalEdge(SegmentTree bypassPathLengths, DijkstraOptimized dijkstraSP, int target) {
        // key = index of edge in the shortest path from source to target
        // value = edge in the shortest path from source to target
        Map<Integer, DirectedEdge> edgesInShortestPath = new HashMap<>();
        int edgeIndex = 0;

        for (DirectedEdge edge : dijkstraSP.pathTo(target)) {
            edgesInShortestPath.put(edgeIndex++, edge);
        }

        int criticalEdgeId = 0;
        double highestBypassPathLength = Double.NEGATIVE_INFINITY;

        for (int edgeId = 0; edgeId < edgeIndex; edgeId++) {
            double lengthBypassingEdge = bypassPathLengths.rangeMinQuery(edgeId, edgeId);
            if (lengthBypassingEdge > highestBypassPathLength) {
                highestBypassPathLength = lengthBypassingEdge;
                criticalEdgeId = edgeId;
            }
        }
        return edgesInShortestPath.get(criticalEdgeId);
    }

    private static class SegmentTree {
        // The Node class represents a partition range of the array.
        private static class Node {
            double sum;
            double min;

            // Value that will be propagated lazily
            Double pendingValue = null;
            int left;
            int right;

            int size() {
                return right - left + 1;
            }
        }

        private final Node[] heap;
        private final double[] array;

        public SegmentTree(double[] array) {
            this.array = Arrays.copyOf(array, array.length);
            // The max size of this array is about 2 * 2 ^ (log2(n) + 1)
            int size = (int) (2 * Math.pow(2.0, Math.floor((Math.log(array.length) / Math.log(2.0)) + 1)));
            heap = new Node[size];
            build(1, 0, array.length);
        }

        // Initialize the Nodes of the Segment tree
        private void build(int index, int left, int size) {
            heap[index] = new Node();
            heap[index].left = left;
            heap[index].right = left + size - 1;

            if (size == 1) {
                heap[index].sum = array[left];
                heap[index].min = array[left];
            } else {
                // Build children
                build(2 * index, left, size / 2);
                build(2 * index + 1, left + size / 2, size - size / 2);

                heap[index].sum = heap[2 * index].sum + heap[2 * index + 1].sum;
                heap[index].min = Math.min(heap[2 * index].min, heap[2 * index + 1].min);
            }
        }

        public int size() {
            return array.length;
        }

        public double rangeMinQuery(int left, int right) {
            return rangeMinQuery(1, left, right);
        }

        private double rangeMinQuery(int index, int left, int right) {
            Node node = heap[index];

            // If you did a range update that contained this node, you can infer the Min value without going down the tree
            if (node.pendingValue != null && contains(node.left, node.right, left, right)) {
                return node.pendingValue;
            }

            if (contains(left, right, node.left, node.right)) {
                return heap[index].min;
            }

            if (intersects(left, right, node.left, node.right)) {
                propagate(index);
                double leftMin = rangeMinQuery(2 * index, left, right);
                double rightMin = rangeMinQuery(2 * index + 1, left, right);

                return Math.min(leftMin, rightMin);
            }
            return Double.POSITIVE_INFINITY;
        }

        public void update(int left, int right, double value) {
            update(1, left, right, value);
        }

        private void update(int index, int left, int right, double value) {
            // The Node of the heap tree represents a range of the array with bounds: [node.left, node.right]
            Node node = heap[index];

            if (contains(left, right, node.left, node.right)) {
                change(node, value);
            }

            if (node.size() == 1) {
                return;
            }

            if (intersects(left, right, node.left, node.right)) {
                propagate(index);

                update(2 * index, left, right, value);
                update(2 * index + 1, left, right, value);

                node.sum = heap[2 * index].sum + heap[2 * index + 1].sum;
                node.min = Math.min(heap[2 * index].min, heap[2 * index + 1].min);
            }
        }

        // Propagate temporal values to children
        private void propagate(int index) {
            Node node = heap[index];

            if (node.pendingValue != null) {
                change(heap[2 * index], node.pendingValue);
                change(heap[2 * index + 1], node.pendingValue);
                node.pendingValue = null; // Unset the pending propagation value
            }
        }

        // Save the temporal values that will be propagated lazily
        private void change(Node node, double value) {
            node.pendingValue = value;
            node.sum = node.size() * value;
            node.min = value;
            array[node.left] = value;
        }

        // Check if range1 contains range2
        private boolean contains(int left1, int right1, int left2, int right2) {
            return left2 >= left1 && right2 <= right1;
        }

        // Check inclusive intersection, test if range1[left1, right1] intersects range2[left2, right2]
        private boolean intersects(int left1, int right1, int left2, int right2) {
            return left1 <= left2 && right1 >= left2   //  (.[..)..] or (.[...]..)
                    || left1 >= left2 && left1 <= right2; // [.(..]..) or [..(..)..]
        }
    }

    public static void main(String[] args) {
        CriticalEdge criticalEdges = new CriticalEdge();

        EdgeWeightedDigraph edgeWeightedDigraph1 = new EdgeWeightedDigraph(8);
        // Shortest path
        edgeWeightedDigraph1.addEdge(new DirectedEdge(0, 1, 1));
        edgeWeightedDigraph1.addEdge(new DirectedEdge(1, 2, 2));
        edgeWeightedDigraph1.addEdge(new DirectedEdge(2, 3, 1));
        edgeWeightedDigraph1.addEdge(new DirectedEdge(3, 4, 1));
        // Path to take when edge 0->1 is removed
        edgeWeightedDigraph1.addEdge(new DirectedEdge(0, 1, 1));
        // Path to take when edge 3->4 is removed
        edgeWeightedDigraph1.addEdge(new DirectedEdge(3, 5, 2));
        edgeWeightedDigraph1.addEdge(new DirectedEdge(5, 4, 1));
        // Possible path to take when edge 2->3 is removed. Won't be taken because there is a shorter path from 0 to 4
        edgeWeightedDigraph1.addEdge(new DirectedEdge(2, 6, 3));
        edgeWeightedDigraph1.addEdge(new DirectedEdge(6, 4, 3));
        // Path to take when edge 1->2 is removed
        edgeWeightedDigraph1.addEdge(new DirectedEdge(1, 7, 3));
        edgeWeightedDigraph1.addEdge(new DirectedEdge(7, 4, 4));

        int source1 = 0;
        int target1 = 4;

        System.out.print("Critical edge 1: ");
        DirectedEdge criticalEdge1 = criticalEdges.getCriticalEdge(edgeWeightedDigraph1, source1, target1);
        printResult(criticalEdge1, source1, target1);
        System.out.println("Expected: 1->2 2.00");

        int source2 = 7;
        int target2 = 3;

        System.out.print("\nCritical edge 2: ");
        DirectedEdge criticalEdge2 = criticalEdges.getCriticalEdge(edgeWeightedDigraph1, source2, target2);
        printResult(criticalEdge2, source2, target2);
        System.out.println("Expected: There is no path from 7 to 3");

        EdgeWeightedDigraph edgeWeightedDigraph2 = new EdgeWeightedDigraph(9);
        // Shortest path
        edgeWeightedDigraph2.addEdge(new DirectedEdge(0, 1, 1));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(1, 2, 1));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(2, 3, 1));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(3, 4, 1));
        // Path to take when edge 2->3 is removed
        edgeWeightedDigraph2.addEdge(new DirectedEdge(2, 5, 2));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(5, 4, 2));
        // Path to take when edge 0->1 is removed
        edgeWeightedDigraph2.addEdge(new DirectedEdge(0, 6, 4));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(6, 4, 3));
        // Another path from 0 to 4 when edge 0->1 is removed - but this path is longer and won't be taken
        edgeWeightedDigraph2.addEdge(new DirectedEdge(0, 7, 4));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(7, 4, 5));
        // Adding a cycle just for fun
        edgeWeightedDigraph2.addEdge(new DirectedEdge(2, 1, 2));
        // Random edge
        edgeWeightedDigraph2.addEdge(new DirectedEdge(8, 2, 3));

        int source3 = 0;
        int target3 = 4;

        System.out.print("\nCritical edge 3: ");
        DirectedEdge criticalEdge3 = criticalEdges.getCriticalEdge(edgeWeightedDigraph2, source3, target3);
        printResult(criticalEdge3, source3, target3);
        System.out.println("Expected: 0->1 1.00");

        // Digraph with a bridge: 2->3 is a bridge which disconnects source and target vertices when removed
        EdgeWeightedDigraph edgeWeightedDigraph3 = new EdgeWeightedDigraph(9);
        // Shortest path
        edgeWeightedDigraph3.addEdge(new DirectedEdge(0, 1, 1));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(1, 2, 1));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(2, 3, 3));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(3, 4, 1));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(4, 5, 1));
        // Other paths
        edgeWeightedDigraph3.addEdge(new DirectedEdge(0, 1, 2));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(1, 2, 2));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(3, 4, 2));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(4, 5, 2));

        int source4 = 0;
        int target4 = 5;

        System.out.print("\nCritical edge 4: ");
        DirectedEdge criticalEdge4 = criticalEdges.getCriticalEdge(edgeWeightedDigraph3, source4, target4);
        printResult(criticalEdge4, source4, target4);
        System.out.println("Expected: 2->3 3.00");

        EdgeWeightedDigraph edgeWeightedDigraph4 = new EdgeWeightedDigraph(9);
        edgeWeightedDigraph4.addEdge(new DirectedEdge(0, 1, 10));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(1, 2, 10));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(2, 3, 10));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(3, 4, 10));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(4, 5, 10));
        // Other path
        edgeWeightedDigraph4.addEdge(new DirectedEdge(3, 7, 1));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(7, 2, 1));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(2, 6, 10));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(6, 8, 1));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(8, 7, 1));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(0, 2, 30));
        edgeWeightedDigraph4.addEdge(new DirectedEdge(3, 5, 30));
        int source5 = 0;
        int target5 = 5;

        System.out.print("\nCritical edge 5: ");
        DirectedEdge criticalEdge5 = criticalEdges.getCriticalEdge(edgeWeightedDigraph4, source5, target5);
        printResult(criticalEdge5, source5, target5);
        System.out.println("Expected: 2->3 10.00");
    }

    private static void printResult(DirectedEdge criticalEdge, int source, int target) {
        if (criticalEdge == null) {
            System.out.println("There is no path from " + source + " to " + target);
        } else {
            System.out.println(criticalEdge);
        }
    }
}
