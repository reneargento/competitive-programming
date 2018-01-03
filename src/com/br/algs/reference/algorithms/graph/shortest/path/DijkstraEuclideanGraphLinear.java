package com.br.algs.reference.algorithms.graph.shortest.path;

import com.br.algs.reference.datastructures.priority.queue.IndexMinPriorityQueue;

import java.util.*;

/**
 * Created by rene on 09/12/17.
 */
// Computes the shortest path between two vertices in O(V + E) due to the use of the Euclidean heuristic
public class DijkstraEuclideanGraphLinear {

    private static class Edge {
        int vertex1;
        int vertex2;
        double weight;

        Edge(int vertex1, int vertex2, double weight) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    @SuppressWarnings("unchecked")
    public class EuclideanEdgeWeightedDigraph {

        public class Vertex {
            private int id;
            private String name;
            private double xCoordinate;
            private double yCoordinate;

            Vertex(int id, double xCoordinate, double yCoordinate) {
                this(id, String.valueOf(id), xCoordinate, yCoordinate);
            }

            Vertex(int id, String name, double xCoordinate, double yCoordinate) {
                this.id = id;
                this.name = name;
                this.xCoordinate = xCoordinate;
                this.yCoordinate = yCoordinate;
            }
        }

        private final int vertices;
        private int edges;
        private Vertex[] allVertices;
        private List<Edge>[] adjacent;

        public EuclideanEdgeWeightedDigraph(int vertices) {
            this.vertices = vertices;
            edges = 0;
            allVertices = new Vertex[vertices];
            adjacent = (List<Edge>[]) new ArrayList[vertices];

            for(int vertex = 0; vertex < vertices; vertex++) {
                adjacent[vertex] = new ArrayList<>();
            }
        }

        public int vertices() {
            return vertices;
        }

        public int edgesCount() {
            return edges;
        }

        public Vertex getVertex(int vertex) {
            return allVertices[vertex];
        }

        public void addVertex(Vertex vertex) {
            allVertices[vertex.id] = vertex;
        }

        public void addEdge(Edge edge) {
            if(allVertices[edge.vertex1] == null || allVertices[edge.vertex2] == null) {
                throw new IllegalArgumentException("Vertex id not found");
            }

            adjacent[edge.vertex1].add(edge);
            edges++;
        }

        public Iterable<Edge> adjacent(int vertex) {
            return adjacent[vertex];
        }

        public Iterable<Edge> edges() {
            List<Edge> bag = new ArrayList<>();

            for(int vertex = 0; vertex < vertices; vertex++) {
                bag.addAll(adjacent[vertex]);
            }

            return bag;
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();

            for(int vertex = 0; vertex < vertices(); vertex++) {
                stringBuilder.append(vertex).append(": ");

                for(Edge neighbor : adjacent(vertex)) {
                    stringBuilder.append(neighbor).append(" ");
                }
                stringBuilder.append("\n");
            }

            return stringBuilder.toString();
        }
    }

    public class DijkstraEuclideanGraph {
        private Edge[] edgeTo;     // last edge on path to vertex
        private double[] distTo;   // length of path to vertex
        private IndexMinPriorityQueue<Double> priorityQueue;

        private int source;
        private boolean shortestDistanceComputed[]; // used to avoid recomputing shortest distances to the same vertex
        private double finalDistanceTo[];           // length of path to a vertex that has already been computed
        private EuclideanEdgeWeightedDigraph euclideanEdgeWeightedDigraph;

        public DijkstraEuclideanGraph(EuclideanEdgeWeightedDigraph euclideanEdgeWeightedDigraph, int source) {
            priorityQueue = new IndexMinPriorityQueue<>(euclideanEdgeWeightedDigraph.vertices());

            shortestDistanceComputed = new boolean[euclideanEdgeWeightedDigraph.vertices()];
            finalDistanceTo = new double[euclideanEdgeWeightedDigraph.vertices()];
            this.euclideanEdgeWeightedDigraph = euclideanEdgeWeightedDigraph;
            this.source = source;
        }

        // O(V) due to the use of the Euclidean Heuristic
        private void computeSourceSinkShortestPath(int target) {

            edgeTo = new Edge[euclideanEdgeWeightedDigraph.vertices()];

            distTo = new double[euclideanEdgeWeightedDigraph.vertices()];
            for(int vertex = 0; vertex < euclideanEdgeWeightedDigraph.vertices(); vertex++) {
                distTo[vertex] = Double.POSITIVE_INFINITY;
            }

            double distanceFromSourceToTarget = getDistanceBetweenVertices(source, target);

            distTo[source] = distanceFromSourceToTarget;
            priorityQueue.insert(source, distanceFromSourceToTarget);

            while (!priorityQueue.isEmpty()) {
                int vertexToRelax = priorityQueue.deleteMin();

                if(vertexToRelax == target) {
                    break;
                }

                relax(euclideanEdgeWeightedDigraph, vertexToRelax, target);
            }

            finalDistanceTo[target] = distTo[target];
        }

        // O(degree(V))
        private void relax(EuclideanEdgeWeightedDigraph euclideanEdgeWeightedDigraph, int vertex, int target) {

            double distanceFromVertexToTarget = getDistanceBetweenVertices(vertex, target);

            for(Edge edge : euclideanEdgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.vertex2;

                //Euclidean heuristic
                double distanceFromNeighborToTarget = getDistanceBetweenVertices(neighbor, target);

                double distanceToTargetPassingThroughNeighbor = distTo[vertex] + edge.weight
                        + distanceFromNeighborToTarget - distanceFromVertexToTarget;

                if(distTo[neighbor] > distanceToTargetPassingThroughNeighbor) {
                    distTo[neighbor] = distanceToTargetPassingThroughNeighbor;
                    edgeTo[neighbor] = edge;

                    if(priorityQueue.contains(neighbor)) {
                        priorityQueue.decreaseKey(neighbor, distTo[neighbor]);
                    } else {
                        priorityQueue.insert(neighbor, distTo[neighbor]);
                    }
                }
            }
        }

        private double getDistanceBetweenVertices(int vertex1, int vertex2) {
            EuclideanEdgeWeightedDigraph.Vertex point1 = euclideanEdgeWeightedDigraph.getVertex(vertex1);
            EuclideanEdgeWeightedDigraph.Vertex point2 = euclideanEdgeWeightedDigraph.getVertex(vertex2);

            return Math.sqrt(Math.pow(point1.xCoordinate - point2.xCoordinate, 2) +
                    Math.pow(point1.yCoordinate - point2.yCoordinate, 2));
        }

        // O(V)
        public double distTo(int vertex) {
            if(!shortestDistanceComputed[vertex]) {
                computeSourceSinkShortestPath(vertex);
                shortestDistanceComputed[vertex] = true;
            }

            return finalDistanceTo[vertex];
        }

        // O(V)
        public boolean hasPathTo(int vertex) {
            if(!shortestDistanceComputed[vertex]) {
                computeSourceSinkShortestPath(vertex);
                shortestDistanceComputed[vertex] = true;
            }

            return finalDistanceTo[vertex] < Double.POSITIVE_INFINITY;
        }

        // O(V)
        public Deque<Edge> pathTo(int vertex) {
            if(!shortestDistanceComputed[vertex]) {
                computeSourceSinkShortestPath(vertex);
                shortestDistanceComputed[vertex] = true;
            }

            if(!hasPathTo(vertex)) {
                return null;
            }

            Deque<Edge> path = new ArrayDeque<>();
            for(Edge edge = edgeTo[vertex]; edge != null; edge = edgeTo[edge.vertex1]) {
                path.push(edge);
            }

            return path;
        }
    }

    private double getDistanceBetweenVertices(EuclideanEdgeWeightedDigraph.Vertex vertex1,
                                              EuclideanEdgeWeightedDigraph.Vertex vertex2) {
        return Math.sqrt(Math.pow(vertex1.xCoordinate - vertex2.xCoordinate, 2) +
                Math.pow(vertex1.yCoordinate - vertex2.yCoordinate, 2));
    }

    public static void main(String[] args) {
        DijkstraEuclideanGraphLinear shortestPathsInEuclideanGraphs = new DijkstraEuclideanGraphLinear();

        EuclideanEdgeWeightedDigraph euclideanEdgeWeightedDigraph =
                shortestPathsInEuclideanGraphs.new EuclideanEdgeWeightedDigraph(7);

        EuclideanEdgeWeightedDigraph.Vertex vertex0 = euclideanEdgeWeightedDigraph.new Vertex(0, 6.1, 1.3);
        EuclideanEdgeWeightedDigraph.Vertex vertex1 = euclideanEdgeWeightedDigraph.new Vertex(1, 7.2, 2.5);
        EuclideanEdgeWeightedDigraph.Vertex vertex2 = euclideanEdgeWeightedDigraph.new Vertex(2, 8.4, 1.3);
        EuclideanEdgeWeightedDigraph.Vertex vertex3 = euclideanEdgeWeightedDigraph.new Vertex(3, 8.4, 15.3);
        EuclideanEdgeWeightedDigraph.Vertex vertex4 = euclideanEdgeWeightedDigraph.new Vertex(4, 6.1, 15.3);
        EuclideanEdgeWeightedDigraph.Vertex vertex5 = euclideanEdgeWeightedDigraph.new Vertex(5, 7.2, 5.2);
        EuclideanEdgeWeightedDigraph.Vertex vertex6 = euclideanEdgeWeightedDigraph.new Vertex(6, 7.2, 8.4);

        euclideanEdgeWeightedDigraph.addVertex(vertex0);
        euclideanEdgeWeightedDigraph.addVertex(vertex1);
        euclideanEdgeWeightedDigraph.addVertex(vertex2);
        euclideanEdgeWeightedDigraph.addVertex(vertex3);
        euclideanEdgeWeightedDigraph.addVertex(vertex4);
        euclideanEdgeWeightedDigraph.addVertex(vertex5);
        euclideanEdgeWeightedDigraph.addVertex(vertex6);

        double distanceFromVertex0ToVertex1 = shortestPathsInEuclideanGraphs.getDistanceBetweenVertices(vertex0, vertex1);
        double distanceFromVertex2ToVertex1 = shortestPathsInEuclideanGraphs.getDistanceBetweenVertices(vertex2, vertex1);
        double distanceFromVertex0ToVertex2 = shortestPathsInEuclideanGraphs.getDistanceBetweenVertices(vertex0, vertex2);
        double distanceFromVertex3ToVertex6 = shortestPathsInEuclideanGraphs.getDistanceBetweenVertices(vertex3, vertex6);
        double distanceFromVertex4ToVertex6 = shortestPathsInEuclideanGraphs.getDistanceBetweenVertices(vertex4, vertex6);
        double distanceFromVertex3ToVertex4 = shortestPathsInEuclideanGraphs.getDistanceBetweenVertices(vertex3, vertex4);
        double distanceFromVertex1ToVertex5 = shortestPathsInEuclideanGraphs.getDistanceBetweenVertices(vertex1, vertex5);
        double distanceFromVertex5ToVertex6 = shortestPathsInEuclideanGraphs.getDistanceBetweenVertices(vertex5, vertex6);

        euclideanEdgeWeightedDigraph.addEdge(new Edge(0, 1, distanceFromVertex0ToVertex1));
        euclideanEdgeWeightedDigraph.addEdge(new Edge(2, 1, distanceFromVertex2ToVertex1));
        euclideanEdgeWeightedDigraph.addEdge(new Edge(0, 2, distanceFromVertex0ToVertex2));
        euclideanEdgeWeightedDigraph.addEdge(new Edge(3, 6, distanceFromVertex3ToVertex6));
        euclideanEdgeWeightedDigraph.addEdge(new Edge(4, 6, distanceFromVertex4ToVertex6));
        euclideanEdgeWeightedDigraph.addEdge(new Edge(3, 4, distanceFromVertex3ToVertex4));
        euclideanEdgeWeightedDigraph.addEdge(new Edge(1, 5, distanceFromVertex1ToVertex5));
        euclideanEdgeWeightedDigraph.addEdge(new Edge(5, 6, distanceFromVertex5ToVertex6));

        DijkstraEuclideanGraph dijkstraSPEuclideanGraph =
                shortestPathsInEuclideanGraphs.new DijkstraEuclideanGraph(euclideanEdgeWeightedDigraph, 0);

        for(int vertex = 0; vertex < euclideanEdgeWeightedDigraph.vertices(); vertex++) {
            System.out.printf("Distance to vertex %d: %.2f\n", vertex, dijkstraSPEuclideanGraph.distTo(vertex));
        }

        System.out.println("\nExpected distances");
        System.out.println("Vertex 0: 0.0");
        System.out.println("Vertex 1: 1.63");
        System.out.println("Vertex 2: 2.30");
        System.out.println("Vertex 3: Infinity");
        System.out.println("Vertex 4: Infinity");
        System.out.println("Vertex 5: 4.33");
        System.out.println("Vertex 6: 7.53");

        for(int vertex = 0; vertex < euclideanEdgeWeightedDigraph.vertices(); vertex++) {
            System.out.print("\nPath from vertex 0 to vertex " + vertex + ": ");

            if(dijkstraSPEuclideanGraph.hasPathTo(vertex)) {
                for(Edge edge : dijkstraSPEuclideanGraph.pathTo(vertex)) {
                    System.out.print(edge.vertex1 + "->" + edge.vertex2 + " ");
                }
            } else {
                System.out.print("There is no path to vertex " + vertex);
            }
        }

        System.out.println("\n\nExpected paths");
        System.out.println("Vertex 0: ");
        System.out.println("Vertex 1: 0->1");
        System.out.println("Vertex 2: 0->2");
        System.out.println("Vertex 3: There is no path to vertex 3");
        System.out.println("Vertex 4 There is no path to vertex 4");
        System.out.println("Vertex 5: 0->1 1->5");
        System.out.println("Vertex 6: 0->1 1->5 5->6");
    }

}
