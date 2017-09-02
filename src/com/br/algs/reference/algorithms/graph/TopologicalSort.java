package com.br.algs.reference.algorithms.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by rene on 01/09/17.
 */
@SuppressWarnings("unchecked")
public class TopologicalSort {

    private static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        /** Call this method to initialize reader for InputStream */
        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        /** Get next word */
        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int vertices = FastReader.nextInt();
        int edges = FastReader.nextInt();

        List<Integer>[] adjacent = (List<Integer>[]) new ArrayList[vertices + 1];

        for(int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for(int i = 0; i < edges; i++) {
            int vertex1 = FastReader.nextInt();
            int vertex2 = FastReader.nextInt();

            adjacent[vertex1].add(vertex2);
        }

        List<Integer> topologicalSort = topologicalSort(adjacent);
        for(int vertex : topologicalSort) {
            System.out.println(vertex);
        }
    }

    private static int time = 1;

    private static List<Integer> topologicalSort(List<Integer>[] adjacent) {
        int[] finishTimes = getFinishTimes(adjacent);

        List<Integer> topologicalSort = new ArrayList<>();

        for(int i = finishTimes.length - 1; i >= 1; i--) {
            topologicalSort.add(finishTimes[i]);
        }

        return topologicalSort;
    }

    private static int[] getFinishTimes(List<Integer>[] adjacent) {
        boolean[] visited = new boolean[adjacent.length];
        int[] finishTimes = new int[adjacent.length];

        for(int i = 1; i < adjacent.length; i++) {
            if(!visited[i]) {
                depthFirstSearch(i, adjacent, finishTimes, visited);
            }
        }

        return finishTimes;
    }

    private static void depthFirstSearch(int sourceVertex, List<Integer>[] adj, int[] finishTimes, boolean[] visited) {
        Stack<Integer> stack = new Stack<>();
        stack.push(sourceVertex);
        visited[sourceVertex] = true;

        while (!stack.isEmpty()) {
            int currentVertex = stack.peek();
            boolean isConnectedToUnvisitedVertex = false;

            for(int neighbor : adj[currentVertex]) {
                if(!visited[neighbor]) {
                    stack.push(neighbor);
                    visited[neighbor] = true;

                    isConnectedToUnvisitedVertex = true;
                }
            }

            if(!isConnectedToUnvisitedVertex) {
                stack.pop();

                finishTimes[time] = currentVertex;
                time++;
            }
        }
    }

}
