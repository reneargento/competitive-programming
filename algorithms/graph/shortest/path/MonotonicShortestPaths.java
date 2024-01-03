package algorithms.graph.shortest.path;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.*;

/**
 * Created by Rene Argento on 10/12/17.
 */
// O(E lg E)
// If negative edge weights are present, still works but becomes O(2^V)
// Based on https://stackoverflow.com/questions/22876105/find-a-monotonic-shortest-path-in-a-graph-in-oe-logv
public class MonotonicShortestPaths {

    public static class Path {
        private final double weight;
        private final DirectedEdge lastEdge;
        private Path previousPath;

        Path(double weight, DirectedEdge lastEdge) {
            this.weight = weight;
            this.lastEdge = lastEdge;
        }

        Path(double weight, DirectedEdge directedEdge, Path previousPath) {
            this(weight, directedEdge);
            this.previousPath = previousPath;
        }

        public double weight() {
            return weight;
        }

        public DirectedEdge lastEdge() {
            return lastEdge;
        }

        public Iterable<DirectedEdge> getPath() {
            LinkedList<DirectedEdge> path = new LinkedList<>();
            Path iterator = previousPath;

            while (iterator != null && iterator.lastEdge != null) {
                path.addFirst(iterator.lastEdge);
                iterator = iterator.previousPath;
            }
            path.add(lastEdge);
            return path;
        }
    }

    public static class VertexInformation {
        private final DirectedEdge[] edges;
        private int currentEdgeIteratorPosition;

        VertexInformation(DirectedEdge[] edges) {
            this.edges = edges;
            this.currentEdgeIteratorPosition = 0;
        }

        public void incrementEdgeIteratorPosition() {
            currentEdgeIteratorPosition++;
        }

        public DirectedEdge[] getEdges() {
            return edges;
        }

        public int getCurrentEdgeIteratorPosition() {
            return currentEdgeIteratorPosition;
        }
    }

    public static class DijkstraMonotonicSP {
        private final double[] distTo;                      // length of path to vertex
        private final Path[] pathTo;                        // paths to each vertex

        private final double[] distToMonotonicAscending;    // length of monotonic ascending path to vertex
        private final Path[] pathMonotonicAscending;        // monotonic ascending path to vertex

        private final double[] distToMonotonicDescending;   // length of monotonic descending path to vertex
        private final Path[] pathMonotonicDescending;       // monotonic descending path to vertex

        // O(E lg E)
        // If negative edge weights are present, still works but becomes O(2^V)
        public DijkstraMonotonicSP(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            distToMonotonicAscending = new double[edgeWeightedDigraph.vertices()];
            distToMonotonicDescending = new double[edgeWeightedDigraph.vertices()];
            distTo = new double[edgeWeightedDigraph.vertices()];

            pathMonotonicAscending = new Path[edgeWeightedDigraph.vertices()];
            pathMonotonicDescending = new Path[edgeWeightedDigraph.vertices()];
            pathTo = new Path[edgeWeightedDigraph.vertices()];

            for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                distTo[vertex] = Double.POSITIVE_INFINITY;
                distToMonotonicAscending[vertex] = Double.POSITIVE_INFINITY;
                distToMonotonicDescending[vertex] = Double.POSITIVE_INFINITY;
            }

            // 1- Relax edges in ascending order to get a monotonic increasing shortest path
            Comparator<DirectedEdge> edgesComparator = new Comparator<DirectedEdge>() {
                @Override
                public int compare(DirectedEdge edge1, DirectedEdge edge2) {
                    return Double.compare(edge2.weight(), edge1.weight());
                }
            };

            relaxAllEdgesInSpecificOrder(edgeWeightedDigraph, source, edgesComparator, distToMonotonicAscending,
                    pathMonotonicAscending, true);

            // 2- Relax edges in descending order to get a monotonic decreasing shortest path
            edgesComparator = new Comparator<DirectedEdge>() {
                @Override
                public int compare(DirectedEdge edge1, DirectedEdge edge2) {
                    return Double.compare(edge1.weight(), edge2.weight());
                }
            };

            relaxAllEdgesInSpecificOrder(edgeWeightedDigraph, source, edgesComparator, distToMonotonicDescending,
                    pathMonotonicDescending, false);

            // 3- Compare distances to get the shortest monotonic path
            compareMonotonicPathsAndComputeShortest();
        }

        private void relaxAllEdgesInSpecificOrder(EdgeWeightedDigraph edgeWeightedDigraph, int source,
                                                  Comparator<DirectedEdge> edgesComparator, double[] distToVertex,
                                                  Path[] pathToVertex, boolean isAscendingOrder) {
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

            PriorityQueue<Path> priorityQueue = new PriorityQueue<>(new Comparator<Path>() {
                @Override
                public int compare(Path path1, Path path2) {
                    return Double.compare(path1.weight(), path2.weight());
                }
            });
            distToVertex[source] = 0;

            VertexInformation sourceVertexInformation = verticesInformation.get(source);
            while (sourceVertexInformation.currentEdgeIteratorPosition < sourceVertexInformation.getEdges().length) {
                DirectedEdge edge = sourceVertexInformation.getEdges()[sourceVertexInformation.getCurrentEdgeIteratorPosition()];
                sourceVertexInformation.incrementEdgeIteratorPosition();

                Path path = new Path(edge.weight(), edge);
                priorityQueue.offer(path);
            }

            while (!priorityQueue.isEmpty()) {
                Path currentShortestPath = priorityQueue.poll();
                DirectedEdge currentEdge = currentShortestPath.lastEdge();

                int nextVertexInPath = currentEdge.to();
                VertexInformation nextVertexInformation = verticesInformation.get(nextVertexInPath);

                if (pathToVertex[nextVertexInPath] == null
                        || currentShortestPath.weight() < distToVertex[nextVertexInPath]) {
                    distToVertex[nextVertexInPath] = currentShortestPath.weight();
                    pathToVertex[nextVertexInPath] = currentShortestPath;
                }

                double weightInPreviousEdge = currentEdge.weight();

                while (nextVertexInformation.getCurrentEdgeIteratorPosition() < nextVertexInformation.getEdges().length) {
                    DirectedEdge edge =
                            verticesInformation.get(nextVertexInPath).getEdges()[nextVertexInformation.getCurrentEdgeIteratorPosition()];

                    if ((isAscendingOrder && edge.weight() <= weightInPreviousEdge)
                            || (!isAscendingOrder && edge.weight() >= weightInPreviousEdge)) {
                        break;
                    }

                    nextVertexInformation.incrementEdgeIteratorPosition();
                    Path path = new Path(currentShortestPath.weight() + edge.weight(), edge, currentShortestPath);
                    priorityQueue.offer(path);
                }
            }
        }

        private void compareMonotonicPathsAndComputeShortest() {
            for (int vertex = 0; vertex < distTo.length; vertex++) {
                if (distToMonotonicAscending[vertex] <= distToMonotonicDescending[vertex]) {
                    distTo[vertex] = distToMonotonicAscending[vertex];
                    pathTo[vertex] = pathMonotonicAscending[vertex];
                } else {
                    distTo[vertex] = distToMonotonicDescending[vertex];
                    pathTo[vertex] = pathMonotonicDescending[vertex];
                }
            }
        }

        // Shortest monotonic path
        public double distTo(int vertex) {
            return distTo[vertex];
        }

        public boolean hasPathTo(int vertex) {
            return distTo[vertex] != Double.POSITIVE_INFINITY;
        }

        public Iterable<DirectedEdge> pathTo(int vertex) {
            if (!hasPathTo(vertex)) {
                return null;
            }
            return pathTo[vertex].getPath();
        }

        // Shortest monotonic ascending path
        public double distToMonotonicAscending(int vertex) {
            return distToMonotonicAscending[vertex];
        }

        public boolean hasPathToMonotonicAscending(int vertex) {
            return distToMonotonicAscending[vertex] != Double.POSITIVE_INFINITY;
        }

        public Iterable<DirectedEdge> pathToMonotonicAscending(int vertex) {
            if (!hasPathTo(vertex)) {
                return null;
            }
            return pathMonotonicAscending[vertex].getPath();
        }

        // Shortest monotonic descending path
        public double distToMonotonicDescending(int vertex) {
            return distToMonotonicDescending[vertex];
        }

        public boolean hasPathToMonotonicDescending(int vertex) {
            return distToMonotonicDescending[vertex] != Double.POSITIVE_INFINITY;
        }

        public Iterable<DirectedEdge> pathToMonotonicDescending(int vertex) {
            if (!hasPathTo(vertex)) {
                return null;
            }
            return pathMonotonicDescending[vertex].getPath();
        }
    }

    // Tests
    public static void main(String[] args) {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(8);
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 1, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 4, 3));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 2, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 6, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 5, 0));
        edgeWeightedDigraph.addEdge(new DirectedEdge(5, 0, 3));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 4, 1));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 3, 2));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 3, 0));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 7, 1));

        int source = 0;
        MonotonicShortestPaths.DijkstraMonotonicSP dijkstraMonotonicSP =
                new DijkstraMonotonicSP(edgeWeightedDigraph, source);

        System.out.print("Monotonic shortest paths: ");

        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            System.out.print("\nPath from vertex 0 to vertex " + vertex + ": ");
            if (vertex == source) {
                continue;
            }

            if (dijkstraMonotonicSP.hasPathTo(vertex)) {
                for (DirectedEdge edge : dijkstraMonotonicSP.pathTo(vertex)) {
                    System.out.print(edge.from() + "->" + edge.to() + " (" + edge.weight() + ") ");
                }
            } else {
                System.out.print("There is no monotonic path to vertex " + vertex);
            }
        }

        System.out.println("\n\nExpected monotonic paths");
        System.out.println("Vertex 0: ");
        System.out.println("Vertex 1: 0->1 (1.0)");
        System.out.println("Vertex 2: 0->1 (1.0) 1->2 (2.0)");
        System.out.println("Vertex 3: 0->1 (1.0) 1->3 (0.0)");
        System.out.println("Vertex 4: 0->4 (3.0)");
        System.out.println("Vertex 5: 0->1 (1.0) 1->5 (0.0)");
        System.out.println("Vertex 6: There is no monotonic path to vertex 6"); // There is a path but it is not monotonic
        System.out.println("Vertex 7: There is no monotonic path to vertex 7"); // There is a path but it is not monotonic
    }
}
