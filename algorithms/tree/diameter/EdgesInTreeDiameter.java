package algorithms.tree.diameter;

import datastructures.graph.DirectedEdge;

import java.util.*;

/**
 * Created by Rene Argento on 08/04/24.
 */
// Computes all edges in the diameter (the highest shortest distance between any pair of vertices) of a graph in
// a tree format.
// Time complexity: O(N)
public class EdgesInTreeDiameter {

    private static List<DirectedEdge> getEdgesInDiameter(List<DirectedEdge>[] adjacencyList) {
        DirectedEdge[] edgeTo = new DirectedEdge[adjacencyList.length];

        int furthestVertex = getFurthestVertex(adjacencyList, edgeTo, 0);
        int furthestVertexFromFurthest = getFurthestVertex(adjacencyList, edgeTo, furthestVertex);
        return getEdgesInDiameter(edgeTo, furthestVertexFromFurthest);
    }

    private static int getFurthestVertex(List<DirectedEdge>[] adjacencyList, DirectedEdge[] edgeTo, int sourceVertexId) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        queue.offer(sourceVertexId);
        visited[sourceVertexId] = true;
        edgeTo[sourceVertexId] = null;

        double[] distances = new double[adjacencyList.length];
        Arrays.fill(distances, -1);
        distances[sourceVertexId] = 0;
        int furthestVertex = sourceVertexId;

        while (!queue.isEmpty()) {
            int vertexId = queue.poll();

            for (DirectedEdge edge : adjacencyList[vertexId]) {
                int neighbor = edge.to();

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    edgeTo[neighbor] = edge;
                    queue.offer(neighbor);

                    distances[neighbor] = distances[vertexId] + edge.weight();
                    if (distances[neighbor] > distances[furthestVertex]) {
                        furthestVertex = neighbor;
                    }
                }
            }
        }
        return furthestVertex;
    }

    private static List<DirectedEdge> getEdgesInDiameter(DirectedEdge[] edgeTo, int vertexIdOrigin) {
        List<DirectedEdge> edgesInDiameter = new ArrayList<>();

        int vertexId = vertexIdOrigin;
        while (edgeTo[vertexId] != null) {
            DirectedEdge edgeToVertex = edgeTo[vertexId];
            edgesInDiameter.add(edgeToVertex);
            vertexId = edgeToVertex.from();
        }
        return edgesInDiameter;
    }

    // Tree:
    //                2
    //               /
    //  4 - 0 - 1 - 3 - 5
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<DirectedEdge>[] adjacencyList = new List[6];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        adjacencyList[0].add(new DirectedEdge(0, 4, 4));
        adjacencyList[0].add(new DirectedEdge(0, 1, 2));
        adjacencyList[1].add(new DirectedEdge(1, 0, 2));
        adjacencyList[1].add(new DirectedEdge(1, 3, 9));
        adjacencyList[2].add(new DirectedEdge(2, 3, 5));
        adjacencyList[3].add(new DirectedEdge(3, 1, 9));
        adjacencyList[3].add(new DirectedEdge(3, 2, 5));
        adjacencyList[3].add(new DirectedEdge(3, 5, 1));
        adjacencyList[4].add(new DirectedEdge(4, 0, 4));
        adjacencyList[5].add(new DirectedEdge(5, 3, 1));

        List<DirectedEdge> edgesInDiameter = getEdgesInDiameter(adjacencyList);
        System.out.println("Edges in diameter:");
        for (DirectedEdge edge : edgesInDiameter) {
            System.out.printf("%d-%d %.2f\n", edge.to(), edge.from(), edge.weight());
        }
    }
}
