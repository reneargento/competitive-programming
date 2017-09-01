package com.br.algs.reference.algorithms.graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rene on 01/09/17.
 */
@SuppressWarnings("unchecked")
public class StronglyConnectedComponents {

    private static int time;

    private static int countStronglyConnectedComponents(List<Integer>[] adj) {
        boolean[] visited = new boolean[adj.length];
        int[] finishTimes = new int[adj.length];

        for(int i = 1; i < adj.length; i++) {
            if(!visited[i]) {
                depthFirstSearch(i, adj, finishTimes, visited, true);
            }
        }

        List<Integer>[] inverseEdges = invertGraphEdges(adj);

        visited = new boolean[inverseEdges.length];

        int stronglyConnectedComponents = 0;

        for(int i = finishTimes.length - 1; i >= 1; i--) {
            if(!visited[finishTimes[i]]) {
                stronglyConnectedComponents++;
                depthFirstSearch(finishTimes[i], inverseEdges, finishTimes, visited, false);
            }
        }

        return stronglyConnectedComponents;
    }

    private static void depthFirstSearch(int sourceVertex, List<Integer>[] adj, int[] finishTimes, boolean[] visited,
                                         boolean getVisitOrder) {
        visited[sourceVertex] = true;

        for(int neighbor : adj[sourceVertex]) {
            if(!visited[neighbor]) {
                depthFirstSearch(neighbor, adj, finishTimes, visited, getVisitOrder);
            }
        }

        if(getVisitOrder) {
            finishTimes[time] = sourceVertex;
            time++;
        }
    }

    private static List<Integer>[] invertGraphEdges(List<Integer>[] adj) {
        List<Integer>[] inverseEdges = new ArrayList[adj.length];

        for(int i = 0; i < inverseEdges.length; i++) {
            inverseEdges[i] = new ArrayList<>();
        }

        for(int i = 1; i < adj.length; i++) {
            List<Integer> neighbors = adj[i];

            if(neighbors != null) {
                for(int neighbor : adj[i]) {
                    inverseEdges[neighbor].add(i);
                }
            }
        }

        return inverseEdges;
    }

}
