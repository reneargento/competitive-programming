package algorithms.graph.bipartite.matching;

import datastructures.graph.Graph;

import java.util.*;

/**
 * Created by Rene Argento on 20/12/20.
 */
// Computes the minimum cost for matching two bipartite set of elements.
// As an example of application, we match a set of bikers with a set of bikes located in a grid.
public class MinCostBipartiteMatching {

    public static void main(String[] args) {
        Random random = new Random();

        int bikers = 10;
        int bikes = 5;
        int minimumMatches = 3;

        GridPosition[] bikersArray = new GridPosition[bikers];
        GridPosition[] bikesArray = new GridPosition[bikes];

        for(int biker = 0; biker < bikers; biker++) {
            int row = random.nextInt(50);
            int column = random.nextInt(50);

            bikersArray[biker] = new GridPosition(biker, row, column);
        }

        for(int bike = 0; bike < bikes; bike++) {
            int row = random.nextInt(50);
            int column = random.nextInt(50);

            bikesArray[bike] = new GridPosition(bike, row, column);
        }

        long[][] distances = new long[bikers][bikes];

        // Use euclidean distance
        for(int biker = 0; biker < bikers; biker++) {
            for(int bike = 0; bike < bikes; bike++) {
                distances[biker][bike] = (bikersArray[biker].row - bikesArray[bike].row) *
                        (bikersArray[biker].row - bikesArray[bike].row) +
                        (bikersArray[biker].column - bikesArray[bike].column) *
                                (bikersArray[biker].column - bikesArray[bike].column);
            }
        }

        long maxDistance = getMinCostBipartiteMatch(distances, minimumMatches);
        System.out.println(maxDistance);
    }

    private static long getMinCostBipartiteMatch(long[][] distances, int minimumMatches) {
        long low = 0;
        long high = 1000000000;

        long maxDistance = 0;

        while (low <= high) {
            long middleTime = low + (high - low) / 2;

            if (checkIfKBikersCanGetBikes(distances, middleTime, minimumMatches)) {
                maxDistance = middleTime;
                high = middleTime - 1;
            } else {
                low = middleTime + 1;
            }
        }
        return maxDistance;
    }

    private static boolean checkIfKBikersCanGetBikes(long[][] distances, long time, int minimumMatches) {
        int bikers = distances.length;
        int bikes = distances[0].length;

        int totalVertices = bikers + bikes;
        Graph graph = new Graph(totalVertices);

        for(int biker = 0; biker < bikers; biker++) {
            for(int bike = 0; bike < bikes; bike++) {
                if (distances[biker][bike] <= time) {
                    graph.addEdge(biker, bike + bikers);
                }
            }
        }

        HopcroftKarp hopcroftKarp = new HopcroftKarp(graph);

        long matches = hopcroftKarp.size();
        return matches >= minimumMatches;
    }

    private static class GridPosition {
        int id;
        long row;
        long column;

        GridPosition(int id, long row, long column) {
            this.id = id;
            this.row = row;
            this.column = column;
        }
    }
}
