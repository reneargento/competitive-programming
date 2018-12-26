package algorithms.graph.shortest.path;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.*;

/**
 * Created by rene on 10/12/17.
 */
@SuppressWarnings("unchecked")
// O(E lg E)
// If negative edge weights are present, still works but become O(2^V)
public class MonotonicShortestPaths {

    public class Path {
        private double weight;
        private DirectedEdge lastEdge;

        Path(double weight, DirectedEdge lastEdge) {
            this.weight = weight;
            this.lastEdge = lastEdge;
        }

        public double weight() {
            return weight;
        }

        public DirectedEdge lastEdge() {
            return lastEdge;
        }
    }

    public class VertexInformation {

        private DirectedEdge[] edges;
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

    public class DijkstraMonotonicSP {

        private double[] distTo;                            // length of path to vertex
        private DirectedEdge[] edgeTo;                      // last edge on path to vertex

        private double[] distToMonotonicAscending;          // length of monotonic ascending path to vertex
        private DirectedEdge[] edgeToMonotonicAscending;    // last edge on monotonic ascending path to vertex

        private double[] distToMonotonicDescending;         // length of monotonic descending path to vertex
        private DirectedEdge[] edgeToMonotonicDescending;   // last edge on monotonic descending path to vertex

        // O(E lg E)
        public DijkstraMonotonicSP(EdgeWeightedDigraph edgeWeightedDigraph, int source) {
            distToMonotonicAscending = new double[edgeWeightedDigraph.vertices()];
            distToMonotonicDescending = new double[edgeWeightedDigraph.vertices()];
            distTo = new double[edgeWeightedDigraph.vertices()];

            edgeToMonotonicAscending = new DirectedEdge[edgeWeightedDigraph.vertices()];
            edgeToMonotonicDescending = new DirectedEdge[edgeWeightedDigraph.vertices()];
            edgeTo = new DirectedEdge[edgeWeightedDigraph.vertices()];

            for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                distTo[vertex] = Double.POSITIVE_INFINITY;
                distToMonotonicAscending[vertex] = Double.POSITIVE_INFINITY;
                distToMonotonicDescending[vertex] = Double.POSITIVE_INFINITY;
            }

            // 1- Relax edges in ascending order to get a monotonic increasing shortest path
            Comparator<DirectedEdge> edgesComparator = new Comparator<DirectedEdge>() {
                @Override
                public int compare(DirectedEdge edge1, DirectedEdge edge2) {
                    if (edge1.weight() > edge2.weight()) {
                        return -1;
                    } else if (edge1.weight() < edge2.weight()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            };

            relaxAllEdgesInSpecificOrder(edgeWeightedDigraph, source, edgesComparator, distToMonotonicAscending,
                    edgeToMonotonicAscending, true);

            // 2- Relax edges in descending order to get a monotonic decreasing shortest path
            edgesComparator = new Comparator<DirectedEdge>() {
                @Override
                public int compare(DirectedEdge edge1, DirectedEdge edge2) {
                    if (edge1.weight() < edge2.weight()) {
                        return -1;
                    } else if (edge1.weight() > edge2.weight()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            };

            relaxAllEdgesInSpecificOrder(edgeWeightedDigraph, source, edgesComparator, distToMonotonicDescending,
                    edgeToMonotonicDescending, false);

            // 3- Compare distances to get the shortest monotonic path
            compareMonotonicPathsAndComputeShortest();
        }

        private void relaxAllEdgesInSpecificOrder(EdgeWeightedDigraph edgeWeightedDigraph, int source,
                                                  Comparator<DirectedEdge> edgesComparator, double[] distToVertex,
                                                  DirectedEdge[] edgeToVertex, boolean isAscendingOrder) {

            // Create a map with vertices as keys and sorted outgoing edges as values
            Map<Integer, VertexInformation> verticesInformation = new HashMap<>();
            for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
                DirectedEdge[] edges = new DirectedEdge[edgeWeightedDigraph.outdegree(vertex)];

                int edgeIndex = 0;
                for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                    edges[edgeIndex++] = edge;
                }

                Arrays.sort(edges, edgesComparator);

                verticesInformation.put(vertex, new VertexInformation(edges));
            }

            PriorityQueue<Path> priorityQueue = new PriorityQueue<>(new Comparator<Path>() {
                @Override
                public int compare(Path path1, Path path2) {
                    if (path1.weight() < path2.weight()) {
                        return -1;
                    } else if (path1.weight() > path2.weight()) {
                        return 1;
                    } else {
                        return 0;
                    }
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

                double weightInPreviousEdge = currentEdge.weight();

                while (nextVertexInformation.getCurrentEdgeIteratorPosition() < nextVertexInformation.getEdges().length) {
                    DirectedEdge edge =
                            verticesInformation.get(nextVertexInPath).getEdges()[nextVertexInformation.getCurrentEdgeIteratorPosition()];

                    if ((isAscendingOrder && edge.weight() <= weightInPreviousEdge)
                            || (!isAscendingOrder && edge.weight() >= weightInPreviousEdge)) {
                        break;
                    }

                    nextVertexInformation.incrementEdgeIteratorPosition();

                    edgeToVertex[nextVertexInPath] = currentShortestPath.lastEdge();
                    distToVertex[nextVertexInPath] = currentShortestPath.weight();

                    Path path = new Path(currentShortestPath.weight() + edge.weight(), edge);
                    priorityQueue.offer(path);
                }

                if (edgeToVertex[nextVertexInPath] == null) {
                    edgeToVertex[nextVertexInPath] = currentEdge;
                    distToVertex[nextVertexInPath] = currentShortestPath.weight();
                }
            }
        }

        private void compareMonotonicPathsAndComputeShortest() {
            for(int vertex = 0; vertex < edgeTo.length; vertex++) {
                if (distToMonotonicAscending[vertex] <= distToMonotonicDescending[vertex]) {
                    distTo[vertex] = distToMonotonicAscending[vertex];
                    edgeTo[vertex] = edgeToMonotonicAscending[vertex];
                } else {
                    distTo[vertex] = distToMonotonicDescending[vertex];
                    edgeTo[vertex] = edgeToMonotonicDescending[vertex];
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

            Deque<DirectedEdge> path = new ArrayDeque<>();
            for(DirectedEdge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.from()]) {
                path.push(edge);
            }

            return path;
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

            Deque<DirectedEdge> path = new ArrayDeque<>();
            for(DirectedEdge edge = edgeToMonotonicAscending[vertex]; edge != null; edge = edgeToMonotonicAscending[edge.from()]) {
                path.push(edge);
            }

            return path;
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

            Deque<DirectedEdge> path = new ArrayDeque<>();
            for(DirectedEdge edge = edgeToMonotonicDescending[vertex]; edge != null; edge = edgeToMonotonicDescending[edge.from()]) {
                path.push(edge);
            }

            return path;
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

        MonotonicShortestPaths.DijkstraMonotonicSP dijkstraMonotonicSP =
                new MonotonicShortestPaths().new DijkstraMonotonicSP(edgeWeightedDigraph, 0);

        System.out.print("Monotonic shortest paths: ");

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            System.out.print("\nPath from vertex 0 to vertex " + vertex + ": ");

            if (dijkstraMonotonicSP.hasPathTo(vertex)) {
                for(DirectedEdge edge : dijkstraMonotonicSP.pathTo(vertex)) {
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
