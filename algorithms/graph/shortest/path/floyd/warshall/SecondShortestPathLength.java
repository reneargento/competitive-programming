package algorithms.graph.shortest.path.floyd.warshall;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 21/03/24.
 */
// Computes the second shortest walk length between all pairs of vertices (or INFINITE if there is none).
// Repeated vertices in the walk are allowed.
// O(V^3)
// Based on problem UVa 10342 - Always Late
@SuppressWarnings("unchecked")
public class SecondShortestPathLength {

    private static class Edge {
        private final int vertex2;
        private final int weight;

        public Edge(int vertex2, int weight) {
            this.vertex2 = vertex2;
            this.weight = weight;
        }
    }

    private static final int INFINITE = 10000000;

    public static void main(String[] args) throws IOException {
        int vertices = 4;
        List<Edge>[] adjacencyList = new List[vertices];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }

        int[][] distances = new int[vertices][vertices];
        for (int junctionID = 0; junctionID < distances.length; junctionID++) {
            Arrays.fill(distances[junctionID], INFINITE);
            distances[junctionID][junctionID] = 0;
        }

        distances[0][1] = 11;
        distances[1][0] = 11;
        distances[0][2] = 20;
        distances[2][0] = 20;
        distances[1][2] = 20;
        distances[2][1] = 15;

        adjacencyList[0].add(new Edge(1, 11));
        adjacencyList[1].add(new Edge(0, 11));
        adjacencyList[0].add(new Edge(2, 20));
        adjacencyList[2].add(new Edge(0, 20));
        adjacencyList[1].add(new Edge(2, 15));
        adjacencyList[2].add(new Edge(1, 15));

        int[][] secondShortestPathLengths = computeSecondShortestPathLengths(distances, adjacencyList);

        int secondSP1 = secondShortestPathLengths[0][1];
        System.out.println("Second shortest length: " + secondSP1 + " Expected: 33");

        int secondSP2 = secondShortestPathLengths[0][2];
        System.out.println("Second shortest length: " + secondSP2 + " Expected: 26");

        int secondSP3 = secondShortestPathLengths[0][3];
        System.out.println("Second shortest length: " + secondSP3 + " Expected: " + INFINITE);
    }

    private static int[][] computeSecondShortestPathLengths(int[][] distances, List<Edge>[] adjacencyList) {
        int[][] secondShortestPathLengths = new int[distances.length][distances.length];
        for (int[] values : secondShortestPathLengths) {
            Arrays.fill(values, INFINITE);
        }
        floydWarshall(distances);

        for (int junctionID1 = 0; junctionID1 < distances.length; junctionID1++) {
            for (int junctionID2 = 0; junctionID2 < distances.length; junctionID2++) {
                if (distances[junctionID1][junctionID2] != INFINITE) {
                    dfs(distances, adjacencyList, secondShortestPathLengths, junctionID1, junctionID1, 0);
                }
            }
        }
        return secondShortestPathLengths;
    }

    private static void dfs(int[][] distances, List<Edge>[] adjacencyList, int[][] secondShortestPathLengths,
                            int junctionID, int neighborJunctionID, int currentLength) {
        if (currentLength > secondShortestPathLengths[junctionID][neighborJunctionID]) {
            return;
        }
        if (currentLength > distances[junctionID][neighborJunctionID]) {
            secondShortestPathLengths[junctionID][neighborJunctionID] = currentLength;
        }

        for (Edge edge : adjacencyList[neighborJunctionID]) {
            dfs(distances, adjacencyList, secondShortestPathLengths, junctionID, edge.vertex2,
                    currentLength + edge.weight);
        }
    }

    private static void floydWarshall(int[][] distances) {
        int vertices = distances.length;

        for (int vertex1 = 0; vertex1 < vertices; vertex1++) {
            for (int vertex2 = 0; vertex2 < vertices; vertex2++) {
                for (int vertex3 = 0; vertex3 < vertices; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
    }
}
