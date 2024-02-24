package algorithms.graph.shortest.path.dijkstra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Created by Rene Argento on 09/01/24.
 */
// Computes the shortest paths from a source vertex to all other vertices in the graph,
// either using an odd or even number of edges.
// O((E + V) lg E)
// Practice problem: UVa 12950
@SuppressWarnings("unchecked")
public class DijkstraOddOrEvenEdges {

    public static void main(String[] args) throws IOException {
        List<Edge>[] adjacencyList = new List[4];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        adjacencyList[0].add(new Edge(1, 2));
        adjacencyList[1].add(new Edge(0, 2));
        adjacencyList[1].add(new Edge(2, 1));
        adjacencyList[2].add(new Edge(1, 1));
        adjacencyList[1].add(new Edge(3, 10));
        adjacencyList[3].add(new Edge(1, 10));
        adjacencyList[2].add(new Edge(3, 6));
        adjacencyList[3].add(new Edge(2, 6));
        int source = 0;
        int target = 3;
        long minimumDistanceEvenEdges = computeMinimumDistance(adjacencyList, source, target, Dijkstra.EVEN);
        System.out.println("Minimum Distance: " + minimumDistanceEvenEdges);
        System.out.println("Expected: 12");
    }

    private static long computeMinimumDistance(List<Edge>[] adjacencyList, int source, int target, int parity) {
        Dijkstra dijkstra = new Dijkstra(adjacencyList, source);
        if (dijkstra.hasPathTo(target, parity)) {
            return dijkstra.distTo[target][parity];
        } else {
            return -1;
        }
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
            int edgesCount;

            public Vertex(int id, long distance, int edgesCount) {
                this.id = id;
                this.distance = distance;
                this.edgesCount = edgesCount;
            }

            @Override
            public int compareTo(Vertex other) {
                return Long.compare(distance, other.distance);
            }
        }

        private final long[][] distTo;
        private final PriorityQueue<Vertex> priorityQueue;
        private final long MAX_VALUE = 10000000000000000L;
        private static final int ODD = 0;
        private static final int EVEN = 1;

        public Dijkstra(List<Edge>[] adjacencyList, int source) {
            distTo = new long[adjacencyList.length][2];
            priorityQueue = new PriorityQueue<>(adjacencyList.length);

            for (int i = 0; i < distTo.length; i++) {
                Arrays.fill(distTo[i], MAX_VALUE);
            }
            distTo[source][EVEN] = 0;
            priorityQueue.offer(new Vertex(source, 0, 0));

            while (!priorityQueue.isEmpty()) {
                relax(adjacencyList, priorityQueue.poll());
            }
        }

        private void relax(List<Edge>[] adjacencyList, Vertex vertex) {
            int currentParityIndex = vertex.edgesCount % 2 == 0 ? EVEN : ODD;
            int nextParityIndex = currentParityIndex == ODD ? EVEN : ODD;

            for (Edge edge : adjacencyList[vertex.id]) {
                int neighbor = edge.vertex2;

                if (distTo[neighbor][nextParityIndex] > distTo[vertex.id][currentParityIndex] + edge.distance) {
                    distTo[neighbor][nextParityIndex] = distTo[vertex.id][currentParityIndex] + edge.distance;
                    int nextEdgesCount = vertex.edgesCount + 1;
                    priorityQueue.offer(new Vertex(neighbor, distTo[neighbor][nextParityIndex], nextEdgesCount));
                }
            }
        }

        public boolean hasPathTo(int vertex, int parity) {
            return distTo[vertex][parity] != MAX_VALUE;
        }
    }
}
