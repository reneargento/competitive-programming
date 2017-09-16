package com.br.algs.reference.algorithms.graph;

import java.util.List;

/**
 * Created by rene on 15/09/17.
 */
public class IsBipartite {

    private boolean[] visited;
    private boolean[] color;
    private boolean isTwoColorable = true;

    public IsBipartite(List<Integer>[] adjacent, int verticesNumber) {
        visited = new boolean[verticesNumber];
        color = new boolean[verticesNumber];

        for(int source = 0; source < verticesNumber; source++) {
            if(!visited[source]) {
                dfs(adjacent, source);
            }
        }
    }

    private void dfs(List<Integer>[] adjacent, int vertex) {
        visited[vertex] = true;

        if(adjacent[vertex] != null) {
            for(int neighbor : adjacent[vertex]) {
                if(!visited[neighbor]) {
                    color[neighbor] = !color[vertex];
                    dfs(adjacent, neighbor);
                } else if(color[neighbor] == color[vertex]) {
                    isTwoColorable = false;
                }
            }
        }
    }

    public boolean isBipartite() {
        return isTwoColorable;
    }

}
