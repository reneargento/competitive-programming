package algorithms.dynamic.programming;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 01/10/22.
 */
public class TravellingSalesmanProblem {

    public static void main(String[] args) {
        int[][] distances = {
                { 0, 20, 42, 35 },
                { 20, 0, 30, 34 },
                { 42, 30, 0, 12 },
                { 35, 34, 12, 0 }
        };
        int tspDistance = computeTSPDistance(distances);
        System.out.println("Expected: 0 1 2 3 0");
        System.out.println("Distance: " + tspDistance + " Expected: 97");
    }

    // O(2^n * n^2)
    private static int computeTSPDistance(int[][] distances) {
        int maxBitmaskSize = (int) Math.pow(2, distances.length);
        int[][] dp = new int[distances.length][maxBitmaskSize];
        for (int[] values : dp) {
            Arrays.fill(values, -1);
        }

        // Tour starts at vertex 0
        int initialBitmask = 1;
        int distance = computeTSPDistance(distances, dp, 0, initialBitmask);

        // Reconstruction
        List<Integer> tour = computeTour(distances, dp);
        System.out.print("Tour:    ");
        for (int vertex : tour) {
            System.out.print(" " + vertex);
        }
        System.out.println();
        return distance;
    }

    // Computes the minimum cost of the tour if we are at vertex and have visited the vertices
    // that are marked as 1 in the bitmask
    private static int computeTSPDistance(int[][] distances, int[][] dp, int vertex, int bitmask) {
        if (bitmask == (1 << (distances.length)) - 1) {
            // Close the tour
            return distances[vertex][0];
        }
        if (dp[vertex][bitmask] != -1) {
            return dp[vertex][bitmask];
        }

        int minimumDistance = Integer.MAX_VALUE;
        for (int nextVertex = 0; nextVertex < distances.length; nextVertex++) {
            if ((bitmask & (1 << nextVertex)) == 0) {
                int newMask = bitmask | (1 << nextVertex); // set bit
                int newDistance = distances[vertex][nextVertex] + computeTSPDistance(distances, dp, nextVertex, newMask);
                minimumDistance = Math.min(minimumDistance, newDistance);
            }
        }
        dp[vertex][bitmask] = minimumDistance;
        return minimumDistance;
    }

    private static List<Integer> computeTour(int[][] distances, int[][] dp) {
        List<Integer> tour = new ArrayList<>();
        tour.add(0);
        int currentVertex = 0;
        int bitmask = 1;
        int nextBitmask = -1;

        for (int size = 0; size < distances.length - 1; size++) {
            int nextVertex = -1;
            for (int vertexId = 0; vertexId < distances.length; vertexId++) {
                if (vertexId == currentVertex || (bitmask & (1 << vertexId)) != 0) {
                    continue;
                }
                if (nextVertex == -1) {
                    nextVertex = vertexId;
                    nextBitmask = bitmask | (1 << vertexId);
                }
                int distance1;
                if (dp[nextVertex][nextBitmask] != -1) {
                    distance1 = distances[currentVertex][nextVertex] + dp[nextVertex][nextBitmask];
                } else {
                    distance1 = Integer.MAX_VALUE;
                }

                int alternativeBitmask = bitmask | (1 << vertexId);
                int distance2;
                if (dp[vertexId][alternativeBitmask] != -1) {
                    distance2 = distances[currentVertex][vertexId] + dp[vertexId][alternativeBitmask];
                } else {
                    distance2 = Integer.MAX_VALUE;
                }

                if (distance2 < distance1) {
                    nextVertex = vertexId;
                    nextBitmask = alternativeBitmask;
                }
            }
            tour.add(nextVertex);
            currentVertex = nextVertex;
            bitmask = nextBitmask;
        }
        tour.add(0);
        return tour;
    }
}
