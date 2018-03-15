package com.br.algs.reference.algorithms.graph.shortest.path;

import java.util.*;

/**
 * Created by rene on 01/12/17.
 */
public class KShortestWalks {

    private class Edge {
        int vertexFrom;
        int vertexTo;
        double weight;
    }

    public class Path implements Comparable<Path> {

        private Path previousPath;
        private Edge edge;
        private int lastVertexInPath;
        private double weight;

        Path(int vertex) {
            lastVertexInPath = vertex;
        }

        Path(Path previousPath, Edge edge) {
            this(edge.vertexTo);
            this.previousPath = previousPath;

            this.edge = edge;
            weight += previousPath.weight() + edge.weight;
        }

        public double weight() {
            return weight;
        }

        public Iterable<Edge> getPath() {
            LinkedList<Edge> path = new LinkedList<>();

            Path currentPreviousPath = previousPath;

            while (currentPreviousPath != null && currentPreviousPath.edge != null) {
                path.addFirst(currentPreviousPath.edge);

                currentPreviousPath = currentPreviousPath.previousPath;
            }
            path.add(edge);

            return path;
        }

        @Override
        public int compareTo(Path other) {
            if (this.weight < other.weight) {
                return -1;
            } else if (this.weight > other.weight) {
                return 1;
            } else {
                return 0;
            }
        }
    }

    //O(K * V * lg P), where P is the number of paths in the graph
    public List<Path> getKShortestPaths(List<Edge>[] adjacent, int source, int target, int kPaths) {

        List<Path> paths = new ArrayList<>();
        Map<Integer, Integer> countMap = new HashMap<>();
        countMap.put(target, 0);

        PriorityQueue<Path> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Path(source));

        while (!priorityQueue.isEmpty() && countMap.get(target) < kPaths) {
            Path currentPath = priorityQueue.poll();
            int lastVertexInPath = currentPath.lastVertexInPath;

            int pathsToCurrentVertex = 0;

            if (countMap.get(lastVertexInPath) != null) {
                pathsToCurrentVertex = countMap.get(lastVertexInPath);
            }
            pathsToCurrentVertex++;

            countMap.put(lastVertexInPath, pathsToCurrentVertex);

            if (lastVertexInPath == target) {
                paths.add(currentPath);
            }

            if (pathsToCurrentVertex <= kPaths) {
                for(Edge edge : adjacent[lastVertexInPath]) {
                    Path newPath = new Path(currentPath, edge);
                    priorityQueue.offer(newPath);
                }
            }
        }

        return paths;
    }

}
