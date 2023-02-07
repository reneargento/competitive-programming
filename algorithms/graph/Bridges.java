package algorithms.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Rene Argento on 07/10/17.
 */
public class Bridges {

    private static class Edge {
        int vertex1;
        int vertex2;

        Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }
    }

    private static int count;
    private static int[] time; // time[v] = order in which dfs examines v
    private static int[] low;  // low[v] = lowest preorder of any vertex connected to v

    private static List<Edge> findBridges(List<Integer>[] adjacencyList) {
        low = new int[adjacencyList.length];
        time = new int[adjacencyList.length];
        List<Edge> bridges = new ArrayList<>();

        Arrays.fill(low, -1);
        Arrays.fill(time, -1);

        for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
            if (time[vertex] == -1) {
                dfs(adjacencyList, vertex, vertex, bridges);
            }
        }
        return bridges;
    }

    private static void dfs(List<Integer>[] adjacencyList, int currentVertex, int sourceVertex, List<Edge> bridges) {
        time[currentVertex] = count;
        low[currentVertex] = count;
        count++;

        for (int neighbor : adjacencyList[currentVertex]) {
            if (time[neighbor] == -1) {
                dfs(adjacencyList, neighbor, currentVertex, bridges);

                low[currentVertex] = Math.min(low[currentVertex], low[neighbor]);

                if (low[neighbor] > time[currentVertex]) {
                    bridges.add(new Edge(currentVertex, neighbor));
                }
            } else if (neighbor != sourceVertex) {
                low[currentVertex] = Math.min(low[currentVertex], time[neighbor]);
            }
        }
    }
}
