package algorithms.graph.shortest.path.floyd.warshall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 25/03/24.
 */
// Given a directed graph, find the number of different paths from each vertex to each vertex.
// If there are cycles, output -1 for the vertices reachable from them (since there are infinite number of paths).
// Time complexity: O(V^3), should be used only for small graphs
// Based on UVa-125 Numbering Paths problem
public class CountNumberOfPaths {

    private static final int INFINITE = 10000000;

    private static class Edge {
        int vertex1;
        int vertex2;

        public Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }
    }

    private static long[][] countPaths(int vertices, List<Edge> edges) {
        long[][] pathsCount = new long[vertices][vertices];

        int[][] distances = new int[vertices][vertices];
        int[][] distancesCycles = new int[vertices][vertices];

        initDistances(distances, distancesCycles, pathsCount, edges);

        floydWarshall(distances);
        floydWarshall(distancesCycles);

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                if (distancesCycles[vertex1][vertex1] < INFINITE && distances[vertex1][vertex2] < INFINITE) {
                    pathsCount[vertex1][vertex2] = -1;
                }
            }
        }

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex1] + distances[vertex1][vertex3] < INFINITE) {
                        if (pathsCount[vertex2][vertex2] == -1
                                || pathsCount[vertex2][vertex1] == -1
                                || pathsCount[vertex1][vertex3] == -1) {
                            pathsCount[vertex2][vertex3] = -1;
                            continue;
                        }
                        pathsCount[vertex2][vertex3] += pathsCount[vertex2][vertex1] * pathsCount[vertex1][vertex3];
                    }
                }
            }
        }
        return pathsCount;
    }

    private static void initDistances(int[][] distances, int[][] distancesCycles, long[][] pathsCount,
                                      List<Edge> edges) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            Arrays.fill(distances[vertex1], INFINITE);
            distances[vertex1][vertex1] = 0;

            Arrays.fill(distancesCycles[vertex1], INFINITE);
            distancesCycles[vertex1][vertex1] = INFINITE;
        }

        for (Edge edge : edges) {
            int vertex1 = edge.vertex1;
            int vertex2 = edge.vertex2;
            distances[vertex1][vertex2] = 1;
            distancesCycles[vertex1][vertex2] = 1;
            pathsCount[vertex1][vertex2] = 1;
        }
    }

    private static void floydWarshall(int[][] distances) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < distances.length; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        int vertices1 = 5;
        List<Edge> edges1 = new ArrayList<>();
        edges1.add(new Edge(0, 1));
        edges1.add(new Edge(0, 2));
        edges1.add(new Edge(0, 4));
        edges1.add(new Edge(2, 4));
        edges1.add(new Edge(2, 3));
        edges1.add(new Edge(3, 1));
        edges1.add(new Edge(4, 3));

        long[][] pathsCount1 = countPaths(vertices1, edges1);
        System.out.println("Paths 1");
        printPathCounts(pathsCount1);

        System.out.println("Expected: \n" +
                "0 4 1 3 2\n" +
                "0 0 0 0 0\n" +
                "0 2 0 2 1\n" +
                "0 1 0 0 0\n" +
                "0 1 0 1 0");

        int vertices2 = 5;
        List<Edge> edges2 = new ArrayList<>();
        edges2.add(new Edge(0, 1));
        edges2.add(new Edge(0, 2));
        edges2.add(new Edge(0, 3));
        edges2.add(new Edge(0, 4));
        edges2.add(new Edge(1, 4));
        edges2.add(new Edge(2, 1));
        edges2.add(new Edge(2, 0));
        edges2.add(new Edge(3, 0));
        edges2.add(new Edge(3, 1));

        long[][] pathsCount2 = countPaths(vertices2, edges2);
        System.out.println("\nPaths 2");
        printPathCounts(pathsCount2);

        System.out.println("Expected: \n" +
                "-1 -1 -1 -1 -1\n" +
                "0 0 0 0 1\n" +
                "-1 -1 -1 -1 -1\n" +
                "-1 -1 -1 -1 -1\n" +
                "0 0 0 0 0");
    }

    private static void printPathCounts(long[][] pathsCount) {
        for (int row = 0; row < pathsCount.length; row++) {
            for (int column = 0; column < pathsCount[0].length; column++) {
                System.out.print(pathsCount[row][column]);
                if (column != pathsCount[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
