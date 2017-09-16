package com.br.algs.reference.algorithms.graph;

import java.util.List;

/**
 * Created by rene on 15/09/17.
 */
public class HasCycle {

    private boolean[] visited;
    private boolean hasCycle;

    public HasCycle(List<Integer>[] adjacent, int verticesNumber) {
        visited = new boolean[verticesNumber];

        for(int source = 0; source < verticesNumber; source++) {
            if(!visited[source]) {
                dfs(adjacent, source, source);
            }
        }
    }

    private void dfs(List<Integer>[] adjacent, int vertex, int origin) {
        visited[vertex] = true;

        if(adjacent[vertex] != null) {
            for(int neighbor : adjacent[vertex]) {
                if(!visited[neighbor]) {
                    dfs(adjacent, neighbor, vertex);
                } else if(neighbor != origin) {
                    hasCycle = true;
                }
            }
        }
    }

    public boolean hasCycle() {
        return hasCycle;
    }

}
