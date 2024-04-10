package algorithms.graph.shortest.path.floyd.warshall;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Created by Rene Argento on 24/03/24.
 */
// Given a graph, find the shortest (in terms of number of edges) non-negative cycle for arbitrage.
// Time complexity: O(V^4), should be used only for small graphs
// Based on UVa-104 Arbitrage problem
public class ShortestCycleArbitrage {

    public static void main(String[] args) {
        // Set the distance values on [vertexID1][vertexID2][step 0]
        // Main diagonal has distance 1
        double[][][] distances = {
                { { 1, 0, 0, 0 }, { 3.1, 0, 0, 0 }, { 0.0023, 0, 0, 0 }, { 0.35, 0, 0, 0 } },
                { { 0.21, 0, 0, 0 }, { 1, 0, 0, 0 }, { 0.00353, 0, 0, 0 }, { 8.13, 0, 0, 0 } },
                { { 200, 0, 0, 0 }, { 180.559, 0, 0, 0 }, { 1, 0, 0, 0 }, { 10.339, 0, 0, 0 } },
                { { 2.11, 0, 0, 0 }, { 0.089, 0, 0, 0 }, { 0.06111, 0, 0, 0 }, { 1, 0, 0, 0 } }
        };
        FloydWarshall floydWarshall = new FloydWarshall(distances);
        List<Integer> shortestCycle = floydWarshall.getShortestCycle();

        if (shortestCycle == null) {
            System.out.println("No cycle exists");
        } else {
            System.out.print("Shortest cycle: " + (shortestCycle.get(0)));
            for (int i = 1; i < shortestCycle.size(); i++) {
                System.out.printf(" %d", shortestCycle.get(i));
            }
            System.out.println();
        }
        System.out.println("Expected: 0 1 3 0");
    }

    private static class FloydWarshall {
        private final double[][][] distances;     // length of shortest v->w path
        private final int[][][] path;             // last vertex on shortest v->w path with k steps

        public FloydWarshall(double[][][] distances) {
            this.distances = distances;
            int vertices = distances.length;
            path = new int[vertices][vertices][vertices];

            for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                    path[vertex1][vertex2][0] = vertex1;
                }
            }

            for (int step = 1; step < vertices; step++) {
                for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                    for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                        for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                            if (distances[vertex2][vertex3][step] <
                                    distances[vertex2][vertex1][step - 1] * distances[vertex1][vertex3][0]) {
                                distances[vertex2][vertex3][step] =
                                        distances[vertex2][vertex1][step - 1] * distances[vertex1][vertex3][0];
                                path[vertex2][vertex3][step] = vertex1; // depth
                            }
                        }
                    }
                }
            }
        }

        public List<Integer> getShortestCycle() {
            int vertices = distances.length;
            List<Integer> shortestCycle = new ArrayList<>();
            Deque<Integer> pathStack = new ArrayDeque<>();

            for (int step = 1; step < vertices; step++) {
                for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
                    if (distances[vertex1][vertex1][step] > 1) {
                        int previousVertex = path[vertex1][vertex1][step];
                        pathStack.push(previousVertex);

                        for (int previousStep = step - 1; previousStep >= 0; previousStep--) {
                            previousVertex = pathStack.peek();
                            pathStack.push(path[vertex1][previousVertex][previousStep]);
                        }
                        int lastVertex = pathStack.peek();

                        while (!pathStack.isEmpty()) {
                            shortestCycle.add(pathStack.pop());
                        }
                        shortestCycle.add(lastVertex);
                        return shortestCycle;
                    }
                }
            }
            return null;
        }
    }
}
