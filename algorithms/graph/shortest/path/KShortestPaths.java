package algorithms.graph.shortest.path;

import java.util.*;

/**
 * Created by Rene Argento on 01/12/17.
 */
// Computes the K shortest paths in a graph in O(K * V^2 * lg P)
// P is the total number of paths in the graph.
// The V^2 is because of HashSet.addAll() operation in Path constructor to avoid repeated vertices.
public class KShortestPaths {

    private static class Edge {
        int vertexFrom;
        int vertexTo;
        double weight;
    }

    public class Path implements Comparable<Path> {
        private Path previousPath;
        private Edge edge;
        private final int lastVertexInPath;
        private double weight;
        private final HashSet<Integer> verticesInPath;

        Path(int vertex) {
            lastVertexInPath = vertex;

            verticesInPath = new HashSet<>();
            verticesInPath.add(vertex);
        }

        Path(Path previousPath, Edge edge) {
            this(edge.vertexTo);
            this.previousPath = previousPath;

            verticesInPath.addAll(previousPath.verticesInPath);

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
            return Double.compare(this.weight, other.weight);
        }
    }

    public List<Path> getKShortestPaths(List<Edge>[] adjacent, int source, int target, int kPaths) {
        List<Path> paths = new ArrayList<>();
        Map<Integer, Integer> countMap = new HashMap<>();
        countMap.put(target, 0);

        PriorityQueue<Path> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(new Path(source));

        while (!priorityQueue.isEmpty() && countMap.get(target) < kPaths) {
            Path currentPath = priorityQueue.poll();
            int lastVertexInPath = currentPath.lastVertexInPath;

            int pathsToCurrentVertex = countMap.getOrDefault(lastVertexInPath, 0) + 1;
            countMap.put(lastVertexInPath, pathsToCurrentVertex);

            if (lastVertexInPath == target) {
                paths.add(currentPath);
            }

            if (pathsToCurrentVertex <= kPaths) {
                for (Edge edge : adjacent[lastVertexInPath]) {
                    // Do not repeat vertices - we are interested in paths and not walks
                    if (!currentPath.verticesInPath.contains(edge.vertexTo)) {
                        Path newPath = new Path(currentPath, edge);
                        priorityQueue.offer(newPath);
                    }
                }
            }
        }
        return paths;
    }
}


