package com.br.algs.reference.algorithms.graph;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by rene on 23/09/17.
 */
//Based on http://www.geeksforgeeks.org/articulation-points-or-cut-vertices-in-a-graph/
public class ArticulationPoints {

    private static int[] time;
    private static int[] low;
    private static int[] parent;
    private static int count;
    private static Set<Integer> articulationPoints;

    private static void computeArticulationPoints(List<Integer>[] adjacent, int vertices) {
        time = new int[vertices + 1];
        low = new int[vertices + 1];
        parent = new int[vertices + 1];
        count = 0;

        articulationPoints = new HashSet<>();

        //1-index based
        for(int vertex = 1; vertex <= vertices; vertex++) {
            time[vertex] = -1;
            low[vertex] = -1;
            parent[vertex] = -1;
        }

        for(int vertex = 1; vertex <= vertices; vertex++) {
            if (time[vertex] == -1) {
                dfs(adjacent, vertex);
            }
        }

    }

    private static void dfs(List<Integer>[] adjacent, int vertex) {
        time[vertex] = count;
        low[vertex] = count;
        count++;

        int children = 0;

        for (int neighbor : adjacent[vertex]) {

            if (time[neighbor] == -1) {
                parent[neighbor] = vertex;
                children++;
                dfs(adjacent, neighbor);

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
