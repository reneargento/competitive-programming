package algorithms.dynamic.programming;

import java.util.Arrays;

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
        return computeTSPDistance(distances, dp, 0, initialBitmask);
    }

    // Computes the minimum cost of the tour if we are at vertex and have visited the vertices
    // that are marked as 1 in the bitmask
    private static int computeTSPDistance(int[][] distances, int[][] dp, int vertex, int bitmask) {
        if (bitmask == (1 << (distances.length)) - 1) {
            // Close the tour
            return distances[vertex][0];
        }

        int minimumDistance = dp[vertex][bitmask];
        if (minimumDistance != -1) {
            return minimumDistance;
        }

        minimumDistance = Integer.MAX_VALUE;
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
}
