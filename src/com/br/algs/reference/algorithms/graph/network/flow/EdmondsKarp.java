package com.br.algs.reference.algorithms.graph.network.flow;

/**
 * Created by rene on 20/10/17.
 */

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Edmonds–Karp algorithm is an implementation of the Ford–Fulkerson method for
 * computing the maximum flow in a flow network. Complexity O(V * E^2) time.
 */
public class EdmondsKarp {

    private long[][] flow; //max flow between i and j vertices
    private long[][] capacity; // edge capacity
    private int[] parent; //parent
    private boolean[] visited; //just for checking if visited

    private int verticesNumber;

    public EdmondsKarp(int vertices) {
        this.verticesNumber = vertices;

        this.flow = new long[verticesNumber][verticesNumber];
        this.capacity = new long[verticesNumber][verticesNumber];
        this.parent = new int[verticesNumber];
        this.visited = new boolean[verticesNumber];
    }

    public void addEdge(int from, int to, long capacity) {
        if(capacity < 0) {
            throw new IllegalArgumentException("Capacity must be equal or higher than 0");
        }

        this.capacity[from][to] += capacity;
    }

    public long getMaxFlow(int source, int target) {
        while (true) {
            final Queue<Integer> queue = new ArrayDeque<>();
            queue.add(source);

            for (int i = 0; i < this.verticesNumber; ++i) {
                visited[i] = false;
            }

            visited[source] = true;

            boolean check = false;
            int current;

            while (!queue.isEmpty()) {
                current = queue.peek();
                if (current == target) {
                    check = true;
                    break;
                }

                queue.remove();

                for (int i = 0; i < verticesNumber; ++i) {
                    if (!visited[i] && capacity[current][i] > flow[current][i]) {
                        visited[i] = true;
                        queue.add(i);
                        parent[i] = current;
                    }
                }
            }
            if (!check) {
                break;
            }

            long temp = capacity[parent[target]][target] - flow[parent[target]][target];

            for (int i = target; i != source; i = parent[i]) {
                temp = Math.min(temp, (capacity[parent[i]][i] - flow[parent[i]][i]));
            }

            for (int i = target; i != source; i = parent[i]) {
                flow[parent[i]][i] += temp;
                flow[i][parent[i]] -= temp;
            }
        }

        long result = 0;
        for (int i = 0; i < verticesNumber; ++i) {
            result += flow[source][i];
        }

        return result;
    }
}
