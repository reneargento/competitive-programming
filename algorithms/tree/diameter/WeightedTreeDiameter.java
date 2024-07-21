package algorithms.tree.diameter;

import datastructures.graph.DirectedEdge;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 08/04/24.
 */
// Computes the diameter (the highest shortest distance between any pair of vertices) of a graph in a tree format.
// Time complexity: O(N)
public class WeightedTreeDiameter {

    private static class VertexData {
        int vertexId;
        double distance;

        public VertexData(int vertexId, double distance) {
            this.vertexId = vertexId;
            this.distance = distance;
        }
    }

    public static double computeTreeDiameter(List<DirectedEdge>[] adjacencyList) {
        VertexData furthestVertex = getFurthestVertex(adjacencyList, 0);
        VertexData furthestVertexFromFurthest = getFurthestVertex(adjacencyList, furthestVertex.vertexId);
        return furthestVertexFromFurthest.distance;
    }

    private static VertexData getFurthestVertex(List<DirectedEdge>[] adjacencyList, int sourceVertexId) {
        Queue<VertexData> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        VertexData source = new VertexData(sourceVertexId, 0);
        queue.offer(source);
        visited[sourceVertexId] = true;

        VertexData furthestVertex = new VertexData(sourceVertexId, 0);

        while (!queue.isEmpty()) {
            VertexData vertexData = queue.poll();

            for (DirectedEdge edge : adjacencyList[vertexData.vertexId]) {
                if (!visited[edge.to()]) {
                    visited[edge.to()] = true;
                    double newDistance = vertexData.distance + edge.weight();
                    queue.offer(new VertexData(edge.to(), newDistance));

                    if (newDistance > furthestVertex.distance) {
                        furthestVertex = new VertexData(edge.to(), newDistance);
                    }
                }
            }
        }
        return furthestVertex;
    }

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

        double treeDiameter = computeTreeDiameter(adjacencyList);
        System.out.println("Tree diameter: " + treeDiameter);
        System.out.println("Expected: 20.0");
    }
}
