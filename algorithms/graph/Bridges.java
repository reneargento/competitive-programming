package algorithms.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 07/10/17.
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

    private static int[] low; // low[v] = lowest preorder of any vertex connected to v
    private static int[] time; // time[v] = order in which dfs examines v

    private static List<Edge> findBridges(List<Integer>[] adjacent) {
        low = new int[adjacent.length];
        time = new int[adjacent.length];

        List<Edge> bridges = new ArrayList<>();

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            low[vertex] = -1;
            time[vertex] = -1;
        }

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (time[vertex] == -1) {
                dfs(adjacent, vertex, vertex, bridges);
            }
        }

        return bridges;
    }

    private static void dfs(List<Integer>[] adjacent, int currentVertex, int sourceVertex, List<Edge> bridges) {
        time[currentVertex] = count;
        low[currentVertex] = count;
        count++;

        for(int neighbor : adjacent[currentVertex]) {
            if (time[neighbor] == -1) {
                dfs(adjacent, neighbor, currentVertex, bridges);

                low[currentVertex] = Math.min(low[currentVertex], low[neighbor]);

                if (low[neighbor] == time[neighbor]) {
                    bridges.add(new Edge(currentVertex, neighbor));
                }

            } else if (neighbor != sourceVertex) {
                low[currentVertex] = Math.min(low[currentVertex], time[neighbor]);
            }
        }
    }

}
