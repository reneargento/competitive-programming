package algorithms.graph.shortest.path;

import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Rene Argento on 08/01/24.
 */
// Count the number of shortest paths from source vertex to target vertex
// O((E + V) lg E)
public class CountShortestPathsWeighted {

    private static long countShortestPaths(List<Edge>[] adjacencyList, int source, int target) {
        Dijkstra dijkstra = new Dijkstra(adjacencyList, source);
        return dijkstra.shortestPathsCount[target];
    }

    private static class Edge {
        private final int vertex2;
        private final long distance;

        public Edge(int vertex2, long distance) {
            this.vertex2 = vertex2;
            this.distance = distance;
        }
    }

    private static class Dijkstra {

        private static class Vertex implements Comparable<Vertex> {
            int id;
            long distance;

            public Vertex(int id, long distance) {
                this.id = id;
                this.distance = distance;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final long[] shortestPathsCount;
        private final long[] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            shortestPathsCount = new long[adjacencyList.length];
            distTo = new long[adjacencyList.length];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            Arrays.fill(distTo, MAX_VALUE);
            distTo[source] = 0;
            shortestPathsCount[source] = 1;
            priorityQueue.offer(new Vertex(source, 0));

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            if (distTo[vertex.id] < vertex.distance) {
                return;
            }

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor] > distTo[vertex.id] + edge.distance) {
                    distTo[neighbor] = distTo[vertex.id] + edge.distance;
                    shortestPathsCount[neighbor] = shortestPathsCount[vertex.id];
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor]));
                } else if (distTo[neighbor] == distTo[vertex.id] + edge.distance) {
                    shortestPathsCount[neighbor] += shortestPathsCount[vertex.id];
                }
            }
        }
    }
}
