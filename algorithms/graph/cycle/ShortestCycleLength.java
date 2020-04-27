package algorithms.graph.cycle;

import datastructures.graph.Graph;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by Rene Argento on 27/04/20.
 */
public class ShortestCycleLength {

    // O(V * (V + E))
    private static int shortestCycleLength(Graph graph) {
        int girth = Integer.MAX_VALUE;

        for(int vertex = 0; vertex < graph.vertices(); vertex++) {
            int shortestCycle = bfsToGetShortestCycle(graph, vertex);
            girth = Math.min(girth, shortestCycle);
        }
        return girth;
    }

    private static int bfsToGetShortestCycle(Graph graph, int sourceVertex) {
        int shortestCycle = Integer.MAX_VALUE;
        int[] distTo = new int[graph.vertices()];
        int[] edgeTo = new int[graph.vertices()];

        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[graph.vertices()];

        visited[sourceVertex] = true;
        edgeTo[sourceVertex] = Integer.MAX_VALUE;
        queue.offer(sourceVertex);

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for(int neighbor : graph.adjacent(currentVertex)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distTo[neighbor] = distTo[currentVertex] + 1;
                    edgeTo[neighbor] = currentVertex;
                    queue.offer(neighbor);
                } else if (neighbor != edgeTo[currentVertex]) {
                    // Cycle found
                    int cycleLength = distTo[currentVertex] + distTo[neighbor] + 1;
                    shortestCycle = Math.min(shortestCycle, cycleLength);
                }
            }
        }
        return shortestCycle;
    }

    public static void main(String[] args) {
        // Graph with girth = 3
        Graph graph1 = new Graph(6);
        graph1.addEdge(2 ,3);
        graph1.addEdge(0 ,1);
        graph1.addEdge(3 ,1);
        graph1.addEdge(5 ,3);
        graph1.addEdge(2 ,0);
        graph1.addEdge(1 ,2);
        graph1.addEdge(4 ,2);
        graph1.addEdge(4 ,5);
        graph1.addEdge(4 ,0);

        // Graph with girth = 2
        Graph graph2 = new Graph(4);
        graph2.addEdge(0, 1);
        graph2.addEdge(1, 0); //Parallel edge
        graph2.addEdge(1, 2);
        graph2.addEdge(2, 3);

        // Graph with girth = 4
        Graph graph3 = new Graph(4);
        graph3.addEdge(0, 1);
        graph3.addEdge(1, 2);
        graph3.addEdge(2, 3);
        graph3.addEdge(3, 0);

        // Graph with girth = Integer.MAX_VALUE
        Graph graph4 = new Graph(4);
        graph4.addEdge(0, 1);
        graph4.addEdge(1, 3);
        graph4.addEdge(2, 3);

        System.out.println("Girth 1: " + shortestCycleLength(graph1) + " Expected: 3");
        System.out.println("Girth 2: " + shortestCycleLength(graph2) + " Expected: 2");
        System.out.println("Girth 3: " + shortestCycleLength(graph3) + " Expected: 4");
        System.out.println("Girth 4: " + shortestCycleLength(graph4) + " Expected: 2147483647");
    }

}
