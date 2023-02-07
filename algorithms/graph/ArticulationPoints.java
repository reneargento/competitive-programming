package algorithms.graph;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Rene Argento on 23/09/17.
 */
//Based on http://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
public class ArticulationPoints {

    private static int[] time; // time[v] = order in which dfs examines v
    private static int[] low;  // low[v] = lowest preorder of any vertex connected to v
    private static int[] parent;
    private static int count;
    private static Set<Integer> articulationPoints;

    private static void computeArticulationPoints(List<Integer>[] adjacencyList) {
        time = new int[adjacencyList.length];
        low = new int[adjacencyList.length];
        parent = new int[adjacencyList.length];
        count = 0;
        articulationPoints = new HashSet<>();

        Arrays.fill(time, -1);
        Arrays.fill(low, -1);
        Arrays.fill(parent, -1);

        for (int vertex = 0; vertex < adjacencyList.length; vertex++) {
            if (time[vertex] == -1) {
                dfs(adjacencyList, vertex);
            }
        }
    }

    private static void dfs(List<Integer>[] adjacencyList, int vertex) {
        time[vertex] = count;
        low[vertex] = count;
        count++;

        int children = 0;

        for (int neighbor : adjacencyList[vertex]) {
            if (time[neighbor] == -1) {
                parent[neighbor] = vertex;
                children++;

                dfs(adjacencyList, neighbor);

                low[vertex] = Math.min(low[vertex], low[neighbor]);

                if (parent[vertex] == -1 && children > 1) {
                    articulationPoints.add(vertex);
                } else if (parent[vertex] != -1 && low[neighbor] >= time[vertex]) {
                    articulationPoints.add(vertex);
                }
            } else if (parent[vertex] != neighbor) {
                low[vertex] = Math.min(low[vertex], time[neighbor]);
            }
        }
    }
}
