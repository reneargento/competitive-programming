package algorithms.tree.diameter;

import java.util.*;

/**
 * Created by Rene Argento on 08/04/24.
 */
// Computes the center vertex of the diameter (the highest shortest distance between any pair of vertices) of a graph
// in a tree format.
// Time complexity: O(N)
public class TreeDiameterCenter {

    private static class Edge {
        int vertex1;
        int vertex2;

        public Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
        }
    }

    public static int getDiameterCenter(List<Edge>[] adjacencyList) {
        int[] parent = new int[adjacencyList.length];
        int[] distances = new int[adjacencyList.length];

        int furthestVertex = getFurthestVertex(adjacencyList, parent, distances, 0);
        int furthestVertexFromFurthest = getFurthestVertex(adjacencyList, parent, distances, furthestVertex);

        int diameter = distances[furthestVertexFromFurthest];
        int radius = diameter / 2;
        int center = furthestVertexFromFurthest;

        while (distances[center] > radius) {
            center = parent[center];
        }
        return center;
    }

    private static int getFurthestVertex(List<Edge>[] adjacencyList, int[] parent, int[] distances,
                                         int sourceVertexId) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        queue.offer(sourceVertexId);
        visited[sourceVertexId] = true;

        Arrays.fill(distances, -1);
        distances[sourceVertexId] = 0;
        parent[sourceVertexId] = -1;

        int furthestVertex = sourceVertexId;

        while (!queue.isEmpty()) {
            int vertexId = queue.poll();

            for (Edge edge : adjacencyList[vertexId]) {
                int neighbor = edge.vertex2;

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distances[neighbor] = distances[vertexId] + 1;
                    parent[neighbor] = vertexId;
                    queue.offer(neighbor);

                    if (distances[neighbor] > distances[furthestVertex]) {
                        furthestVertex = neighbor;
                    }
                }
            }
        }
        return furthestVertex;
    }

    // Tree:
    //                2
    //               /
    //  4 - 0 - 1 - 3 - 5
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<Edge>[] adjacencyList = new List[6];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        adjacencyList[0].add(new Edge(0, 4));
        adjacencyList[0].add(new Edge(0, 1));
        adjacencyList[1].add(new Edge(1, 0));
        adjacencyList[1].add(new Edge(1, 3));
        adjacencyList[2].add(new Edge(2, 3));
        adjacencyList[3].add(new Edge(3, 1));
        adjacencyList[3].add(new Edge(3, 2));
        adjacencyList[3].add(new Edge(3, 5));
        adjacencyList[4].add(new Edge(4, 0));
        adjacencyList[5].add(new Edge(5, 3));

        int diameterCenter = getDiameterCenter(adjacencyList);
        System.out.println("Tree diameter center: " + diameterCenter);
        System.out.println("Expected: 1");
    }
}
