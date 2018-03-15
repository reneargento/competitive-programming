package com.br.algs.reference.algorithms.graph.shortest.path;

import com.br.algs.reference.datastructures.DirectedEdge;
import com.br.algs.reference.datastructures.EdgeWeightedDigraph;
import com.br.algs.reference.datastructures.priority.queue.IndexMinPriorityQueue;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Created by rene on 24/12/17.
 */
public class DijkstraBidirectional {

    private DirectedEdge[] edgeToSource;  // last edge on path from source to vertex
    private double[] distToSource;        // length of path from source to vertex

    private DirectedEdge[] edgeToTarget;  // last edge on inverse path from target to vertex
    private double[] distToTarget;        // length of inverse path from target to vertex

    public DijkstraBidirectional(EdgeWeightedDigraph edgeWeightedDigraph, int source, int target) {
        edgeToSource = new DirectedEdge[edgeWeightedDigraph.vertices()];
        distToSource = new double[edgeWeightedDigraph.vertices()];

        edgeToTarget = new DirectedEdge[edgeWeightedDigraph.vertices()];
        distToTarget = new double[edgeWeightedDigraph.vertices()];

        boolean[] relaxedFromSource = new boolean[edgeWeightedDigraph.vertices()];
        boolean[] relaxedFromTarget = new boolean[edgeWeightedDigraph.vertices()];

        IndexMinPriorityQueue<Double> priorityQueueSource = new IndexMinPriorityQueue<>(edgeWeightedDigraph.vertices());
        IndexMinPriorityQueue<Double> priorityQueueTarget = new IndexMinPriorityQueue<>(edgeWeightedDigraph.vertices());

        // Reverse digraph will be used to create the shortest-path-tree from the target vertex
        EdgeWeightedDigraph reverseDigraph = new EdgeWeightedDigraph(edgeWeightedDigraph.vertices());

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                reverseDigraph.addEdge(new DirectedEdge(edge.to(), edge.from(), edge.weight()));
            }
        }

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            distToSource[vertex] = Double.POSITIVE_INFINITY;
            distToTarget[vertex] = Double.POSITIVE_INFINITY;
        }
        distToSource[source] = 0;
        priorityQueueSource.insert(source, 0.0);

        distToTarget[target] = 0;
        priorityQueueTarget.insert(target, 0.0);

        while (!priorityQueueSource.isEmpty() || !priorityQueueTarget.isEmpty()) {
            if (!priorityQueueSource.isEmpty()) {
                int nextVertexToRelax = priorityQueueSource.deleteMin();
                relax(edgeWeightedDigraph, nextVertexToRelax, priorityQueueSource, distToSource, edgeToSource,
                        relaxedFromSource);

                // Combine shortest paths if there are shortest paths from source to vertex and from vertex to target
                if (relaxedFromSource[nextVertexToRelax] && relaxedFromTarget[nextVertexToRelax]) {
                    computeShortestPath(edgeWeightedDigraph);
                    break;
                }
            }

            if (!priorityQueueTarget.isEmpty()) {
                int nextVertexToRelax = priorityQueueTarget.deleteMin();
                relax(reverseDigraph, nextVertexToRelax, priorityQueueTarget, distToTarget, edgeToTarget,
                        relaxedFromTarget);

                // Combine shortest paths if there are shortest paths from source to vertex and from vertex to target
                if (relaxedFromSource[nextVertexToRelax] && relaxedFromTarget[nextVertexToRelax]) {
                    computeShortestPath(edgeWeightedDigraph);
                    break;
                }
            }
        }
    }

    // Shortest-path distance calculation based on https://courses.csail.mit.edu/6.006/spring11/rec/rec16.pdf
    private void computeShortestPath(EdgeWeightedDigraph edgeWeightedDigraph) {
        double shortestDistance = Double.POSITIVE_INFINITY;
        int intermediateVertex = -1;

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            if (distToSource[vertex] + distToTarget[vertex] < shortestDistance) {
                shortestDistance = distToSource[vertex] + distToTarget[vertex];
                intermediateVertex = vertex;
            }
        }

        for(DirectedEdge edge = edgeToTarget[intermediateVertex]; edge != null; edge = edgeToTarget[edge.from()]) {
            distToSource[edge.from()] = distToSource[edge.to()] + edge.weight();
            edgeToSource[edge.from()] = new DirectedEdge(edge.to(), edge.from(), edge.weight());
        }
    }

    private void relax(EdgeWeightedDigraph edgeWeightedDigraph, int vertex, IndexMinPriorityQueue<Double> priorityQueue,
                       double[] distTo, DirectedEdge[] edgeTo, boolean[] relaxed) {

        for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            int neighbor = edge.to();

            if (distTo[neighbor] > distTo[vertex] + edge.weight()) {
                distTo[neighbor] = distTo[vertex] + edge.weight();
                edgeTo[neighbor] = edge;

                if (priorityQueue.contains(neighbor)) {
                    priorityQueue.decreaseKey(neighbor, distTo[neighbor]);
                } else {
                    priorityQueue.insert(neighbor, distTo[neighbor]);
                }
            }
        }

        relaxed[vertex] = true;
    }

    public double distTo(int vertex) {
        return distToSource[vertex];
    }

    public DirectedEdge edgeTo(int vertex) {
        return edgeToSource[vertex];
    }

    public boolean hasPathTo(int vertex) {
        return distToSource[vertex] != Double.POSITIVE_INFINITY;
    }

    public Iterable<DirectedEdge> pathTo(int vertex) {
        if (!hasPathTo(vertex)) {
            return null;
        }

        Deque<DirectedEdge> path = new ArrayDeque<>();
        for(DirectedEdge edge = edgeToSource[vertex]; edge != null; edge = edgeToSource[edge.from()]) {
            path.push(edge);
        }

        return path;
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph edgeWeightedDigraph = new EdgeWeightedDigraph(8);
        edgeWeightedDigraph.addEdge(new DirectedEdge(4, 5, 0.35));
        edgeWeightedDigraph.addEdge(new DirectedEdge(5, 4, 0.35));
        edgeWeightedDigraph.addEdge(new DirectedEdge(4, 7, 0.37));
        edgeWeightedDigraph.addEdge(new DirectedEdge(5, 7, 0.28));
        edgeWeightedDigraph.addEdge(new DirectedEdge(7, 5, 0.28));
        edgeWeightedDigraph.addEdge(new DirectedEdge(5, 1, 0.32));
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 4, 0.38));
        edgeWeightedDigraph.addEdge(new DirectedEdge(0, 2, 0.26));
        edgeWeightedDigraph.addEdge(new DirectedEdge(7, 3, 0.39));
        edgeWeightedDigraph.addEdge(new DirectedEdge(1, 3, 0.29));
        edgeWeightedDigraph.addEdge(new DirectedEdge(2, 7, 0.34));
        edgeWeightedDigraph.addEdge(new DirectedEdge(6, 2, 0.40));
        edgeWeightedDigraph.addEdge(new DirectedEdge(3, 6, 0.52));
        edgeWeightedDigraph.addEdge(new DirectedEdge(6, 0, 0.58));
        edgeWeightedDigraph.addEdge(new DirectedEdge(6, 4, 0.93));

        int source = 0;
        int target = 6;

        DijkstraBidirectional dijkstraBidirectional1 = new DijkstraBidirectional(edgeWeightedDigraph, source, target);

        System.out.println("Shortest path from 0 to 6:");

        if (dijkstraBidirectional1.hasPathTo(target)) {
            System.out.printf("%d to %d (%.2f)  ", source, target, dijkstraBidirectional1.distTo(target));

            for (DirectedEdge edge : dijkstraBidirectional1.pathTo(target)) {
                System.out.print(edge + "   ");
            }
            System.out.println();
        } else {
            System.out.printf("%d to %d         no path\n", source, target);
        }

        System.out.println("Expected:");
        System.out.println("0 to 6 (1.51)  0->2 0.26   2->7 0.34   7->3 0.39   3->6 0.52");

        // This digraph would cause a collision between vertices of different SPTs in the priority queue and would
        // lead to incorrect shortest-paths if extra bookkeeping were not used
        EdgeWeightedDigraph edgeWeightedDigraph2 = new EdgeWeightedDigraph(8);
        edgeWeightedDigraph2.addEdge(new DirectedEdge(0, 1, 1));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(0, 2, 4));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(1, 3, 5));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(2, 7, 1));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(3, 4, 3));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(4, 6, 1));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(5, 6, 5));
        edgeWeightedDigraph2.addEdge(new DirectedEdge(7, 4, 2));

        int source2 = 0;
        int target2 = 6;

        DijkstraBidirectional dijkstraBidirectional2 = new DijkstraBidirectional(edgeWeightedDigraph2, source2, target2);

        System.out.println("\nShortest path from 0 to 6:");

        if (dijkstraBidirectional2.hasPathTo(target2)) {
            System.out.printf("%d to %d (%.2f)  ", source2, target2, dijkstraBidirectional2.distTo(target2));

            for (DirectedEdge edge : dijkstraBidirectional2.pathTo(target2)) {
                System.out.print(edge + "   ");
            }
            System.out.println();
        } else {
            System.out.printf("%d to %d         no path\n", source2, target2);
        }

        System.out.println("Expected:");
        System.out.println("0 to 6 (8.00)  0->2 4.00   2->7 1.00   7->4 2.00   4->6 1.00");

        EdgeWeightedDigraph edgeWeightedDigraph3 = new EdgeWeightedDigraph(6);
        edgeWeightedDigraph3.addEdge(new DirectedEdge(0, 1, 3));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(0, 2, 1));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(1, 3, 1));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(2, 4, 4));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(3, 5, 3));
        edgeWeightedDigraph3.addEdge(new DirectedEdge(4, 5, 1));

        int source3 = 0;
        int target3 = 5;

        DijkstraBidirectional dijkstraBidirectional3 = new DijkstraBidirectional(edgeWeightedDigraph3, source3, target3);

        System.out.println("\nShortest path from 0 to 5:");

        if (dijkstraBidirectional3.hasPathTo(target3)) {
            System.out.printf("%d to %d (%.2f)  ", source3, target3, dijkstraBidirectional3.distTo(target3));

            for (DirectedEdge edge : dijkstraBidirectional3.pathTo(target3)) {
                System.out.print(edge + "   ");
            }
            System.out.println();
        } else {
            System.out.printf("%d to %d         no path\n", source3, target3);
        }

        System.out.println("Expected:");
        System.out.println("0 to 5 (6.00)  0->2 1.00   2->4 4.00   4->5 1.00");
    }

}
