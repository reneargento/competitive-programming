package algorithms.graph.shortest.path;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.*;

/**
 * Created by Rene Argento on 17/12/17.
 */
// Bitonic shortest-path: a shortest-path from s to t in which there is an intermediate vertex v
// such that the weights of the edges on the path s to v are strictly increasing and
// the weights of the edges on the path from v to t are strictly decreasing.

// Relax all edges in ascending order from s to all other vertices and then relax all edges again,
// in descending order from s to all other vertices
public class BitonicShortestPaths {

    public static class Path implements Comparable<Path> {

        private Path previousPath;
        private final DirectedEdge directedEdge;
        private double weight;
        private boolean isDescending;
        private int numberOfEdges;

        Path(DirectedEdge directedEdge) {
            this.directedEdge = directedEdge;
            weight = directedEdge.weight();
            numberOfEdges = 1;
        }

        Path(Path previousPath, DirectedEdge directedEdge) {
            this(directedEdge);
            this.previousPath = previousPath;

            weight += previousPath.weight();
            numberOfEdges += previousPath.numberOfEdges;

            if (previousPath.directedEdge.weight() > directedEdge.weight()) {
                isDescending = true;
            }
        }

        public double weight() {
            return weight;
        }

        public boolean isDescending() {
            return isDescending;
        }

        public int numberOfEdges() {
            return numberOfEdges;
        }

        public Iterable<DirectedEdge> getPath() {
            LinkedList<DirectedEdge> path = new LinkedList<>();
            Path iterator = previousPath;

            while (iterator != null && iterator.directedEdge != null) {
                path.addFirst(iterator.directedEdge);
                iterator = iterator.previousPath;
            }
            path.add(directedEdge);
            return path;
        }

        @Override
        public int compareTo(Path other) {
            return Double.compare(this.weight, other.weight);
        }
    }

    public static class VertexInformation {
        private final DirectedEdge[] edges;
        private int edgeIteratorPosition;

        VertexInformation(DirectedEdge[] edges) {
            this.edges = edges;
            edgeIteratorPosition = 0;
        }

        public void incrementEdgeIteratorPosition() {
            edgeIteratorPosition++;
        }

        public DirectedEdge[] getEdges() {
            return edges;
        }

        public int getEdgeIteratorPosition() {
            return edgeIteratorPosition;
        }
    }

    public static class BitonicSP {
        private final Path[] bitonicPathTo;  // bitonic path to vertex

        // O(P lg P), where P is the number of paths in the digraph
        // Includes optimization to prune paths that are not bitonic, ie. ascending + descending + ascending
        // or descending + ascending
        public BitonicSP(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            bitonicPathTo = new Path[edgeWeightedDigraph.vertices()];

            // 1- Relax edges in ascending order to get a monotonic increasing shortest path
            Comparator<DirectedEdge> edgesComparator = new Comparator<DirectedEdge>() {
                @Override
                public int compare(DirectedEdge edge1, DirectedEdge edge2) {
                    return Double.compare(edge2.weight(), edge1.weight());
                }
            };

            List<Path> allCurrentPaths = new ArrayList<>();

            relaxAllEdgesInSpecificOrder(edgeWeightedDigraph, source, edgesComparator, allCurrentPaths, true);

            // 2- Relax edges in descending order to get a monotonic decreasing shortest path
            edgesComparator = new Comparator<DirectedEdge>() {
                @Override
                public int compare(DirectedEdge edge1, DirectedEdge edge2) {
                    return Double.compare(edge1.weight(), edge2.weight());
                }
            };

            relaxAllEdgesInSpecificOrder(edgeWeightedDigraph, source, edgesComparator, allCurrentPaths, false);
        }

        private void relaxAllEdgesInSpecificOrder(EdgeWeightedDigraph edgeWeightedDigraph, int source,
                                                  Comparator<DirectedEdge> edgesComparator, List<Path> allCurrentPaths,
                                                  boolean isAscendingOrder) {

            // Create a map with vertices as keys and sorted outgoing edges as values
            Map<Integer, VertexInformation> verticesInformation = new HashMap<>();
            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                DirectedEdge[] edges = new DirectedEdge[edgeWeightedDigraph.outdegree(vertex)];
                int edgeIndex = 0;
                for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                    edges[edgeIndex++] = edge;
                }

                Arrays.sort(edges, edgesComparator);
                verticesInformation.put(vertex, new VertexInformation(edges));
            }

            PriorityQueue<Path> priorityQueue = new PriorityQueue<>();

            // If we are relaxing edges for the first time, add the initial paths to the priority queue
            if (isAscendingOrder) {
                VertexInformation sourceVertexInformation = verticesInformation.get(source);
                while (sourceVertexInformation.getEdgeIteratorPosition() < sourceVertexInformation.getEdges().length) {
                    DirectedEdge edge = sourceVertexInformation.getEdges()[sourceVertexInformation.getEdgeIteratorPosition()];
                    sourceVertexInformation.incrementEdgeIteratorPosition();

                    Path path = new Path(edge);
                    priorityQueue.offer(path);

                    allCurrentPaths.add(path);
                }
            }

            // If we are relaxing edges for the second time, add all existing ascending paths to the priority queue
            if (!allCurrentPaths.isEmpty()) {
                for (Path currentPath : allCurrentPaths) {
                    priorityQueue.offer(currentPath);
                }
            }

            while (!priorityQueue.isEmpty()) {
                Path currentShortestPath = priorityQueue.poll();
                DirectedEdge currentEdge = currentShortestPath.directedEdge;

                int nextVertexInPath = currentEdge.to();
                VertexInformation nextVertexInformation = verticesInformation.get(nextVertexInPath);

                // Edge case: a bitonic path consisting of 2 edges of the same weight.
                // s to v with only one edge is strictly increasing, v to t with only one edge is strictly decreasing
                boolean isEdgeCase = false;

                if (currentShortestPath.numberOfEdges() == 2
                        && currentEdge.weight() == currentShortestPath.previousPath.directedEdge.weight()) {
                    isEdgeCase = true;
                }

                if ((currentShortestPath.isDescending() || isEdgeCase)
                        && (currentShortestPath.weight() < bitonicPathDistTo(nextVertexInPath)
                            || bitonicPathTo[nextVertexInPath] == null)) {
                    bitonicPathTo[nextVertexInPath] = currentShortestPath;
                }

                double weightInPreviousEdge = currentEdge.weight();

                while (nextVertexInformation.getEdgeIteratorPosition() < nextVertexInformation.getEdges().length) {
                    DirectedEdge edge =
                            verticesInformation.get(nextVertexInPath).getEdges()[nextVertexInformation.getEdgeIteratorPosition()];

                    boolean isEdgeInEdgeCase = currentShortestPath.numberOfEdges() == 1
                            && edge.weight() == weightInPreviousEdge;

                    if (!isEdgeInEdgeCase && ((isAscendingOrder && edge.weight() <= weightInPreviousEdge)
                            || (!isAscendingOrder && edge.weight() >= weightInPreviousEdge))) {
                        break;
                    }

                    nextVertexInformation.incrementEdgeIteratorPosition();

                    Path path = new Path(currentShortestPath, edge);
                    priorityQueue.offer(path);

                    // If we are relaxing edges for the first time, store the ascending paths so they can be further
                    // relaxed when computing the descending paths on the second relaxation
                    if (isAscendingOrder) {
                        allCurrentPaths.add(path);
                    }
                }
            }
        }

        public double bitonicPathDistTo(int vertex) {
            if (hasBitonicPathTo(vertex)) {
                return bitonicPathTo[vertex].weight();
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }

        public boolean hasBitonicPathTo(int vertex) {
            return bitonicPathTo[vertex] != null;
        }

        public Iterable<DirectedEdge> bitonicPathTo(int vertex) {
            if (!hasBitonicPathTo(vertex)) {
                return null;
            }
            return bitonicPathTo[vertex].getPath();
        }
    }

    //Tests
    public static void main(String[] args) {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(13);
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 1, 4));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 2, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 3, 4));
        edgeWeightedDigraph.addEdge(new DirectedEdge(3, 4, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 5, 5));
        edgeWeightedDigraph.addEdge(new DirectedEdge(5, 6, 3));
        edgeWeightedDigraph.addEdge(new DirectedEdge(6, 7, 8));
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 8, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(8, 9, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 2, 4));

        // With the following 3 edges, there is now a shortest monotonic ascending path from 0 to 3:
        // 0->1 (1.0) 1->2 (2.0) 2->3 (3.0)
        // but it is not bitonic, so it should not be selected
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 1, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 2, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 3, 3));

        // Edge case: a bitonic path consisting of 2 edges of the same weight
        // 0->10 (3.0) 10->11 (3.0)
        // Should be in the final solution
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 10, 3));
        edgeWeightedDigraph.addEdge(new DirectedEdge(10, 11, 3));

        // Not an edge case: 3 edges of the same weight in the path
        // 0->10 (3.0) 10->11 (3.0) 11->12 (3.0)
        // Should not be in the final solution
        edgeWeightedDigraph.addEdge(new DirectedEdge(11, 12, 3));

        BitonicSP bitonicSP = new BitonicSP(edgeWeightedDigraph, 0);

        System.out.println("Bitonic shortest paths: ");

        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            System.out.print("\nPath from vertex 0 to vertex " + vertex + ": ");

            if (bitonicSP.hasBitonicPathTo(vertex)) {
                for (DirectedEdge edge : bitonicSP.bitonicPathTo(vertex)) {
                    System.out.print(edge.from() + "->" + edge.to() + " (" + edge.weight() + ") ");
                }
            } else {
                System.out.print("There is no bitonic path to vertex " + vertex);
            }
        }

        System.out.println("\n\nExpected bitonic paths");
        System.out.println("Vertex 0: There is no bitonic path to vertex 0"); // There is a path but it is not bitonic
        System.out.println("Vertex 1: There is no bitonic path to vertex 1"); // There is a path but it is not bitonic
        System.out.println("Vertex 2: 0->1 (4.0) 1->2 (2.0)");
        System.out.println("Vertex 3: 0->1 (1.0) 1->2 (4.0) 2->3 (3.0)");
        System.out.println("Vertex 4: 0->1 (1.0) 1->2 (2.0) 2->3 (3.0) 3->4 (1.0)");
        System.out.println("Vertex 5: There is no bitonic path to vertex 5"); // There is a path but it is not bitonic
        System.out.println("Vertex 6: 0->1 (1.0) 1->5 (5.0) 5->6 (3.0)");
        System.out.println("Vertex 7: There is no bitonic path to vertex 7"); // There is a path but it is not bitonic
        System.out.println("Vertex 8: There is no bitonic path to vertex 8"); // There is a path but it is not bitonic
        System.out.println("Vertex 9: 0->8 (2.0) 8->9 (1.0)");
        System.out.println("Vertex 10: There is no bitonic path to vertex 10"); // There is a path but it is not bitonic
        System.out.println("Vertex 11: 0->10 (3.0) 10->11 (3.0)"); // An edge case
        System.out.println("Vertex 12: There is no bitonic path to vertex 12"); // There is a path but it is not bitonic

        double[] expectedDistances = {
                Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 6, 8, 7, Double.POSITIVE_INFINITY,
                9, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 3, Double.POSITIVE_INFINITY,
                6, Double.POSITIVE_INFINITY
        };

        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            System.out.print("\nDistance to vertex " + vertex + ": " + bitonicSP.bitonicPathDistTo(vertex)
                    + " Expected: " + expectedDistances[vertex]);
        }
    }
}
