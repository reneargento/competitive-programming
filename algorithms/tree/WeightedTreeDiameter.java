package algorithms.tree;

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
        int vertexID;
        double distance;

        public VertexData(int vertexID, double distance) {
            this.vertexID = vertexID;
            this.distance = distance;
        }
    }

    public static double computeTreeDiameter(List<DirectedEdge>[] adjacencyList) {
        VertexData furthestVertex = getFurthestVertex(adjacencyList, 0);
        VertexData furthestVertexFromFurthest = getFurthestVertex(adjacencyList, furthestVertex.vertexID);
        return furthestVertexFromFurthest.distance;
    }

    private static VertexData getFurthestVertex(List<DirectedEdge>[] adjacencyList, int sourceVertexID) {
        Queue<VertexData> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        VertexData source = new VertexData(sourceVertexID, 0);
        queue.offer(source);
        visited[sourceVertexID] = true;

        while (!queue.isEmpty()) {
            VertexData vertexData = queue.poll();

            for (DirectedEdge edge : adjacencyList[vertexData.vertexID]) {
                if (!visited[edge.to()]) {
                    visited[edge.to()] = true;
                    queue.offer(new VertexData(edge.to(), vertexData.distance + edge.weight()));
                }
            }

            if (queue.isEmpty()) {
                return vertexData;
            }
        }
        return source;
    }

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<DirectedEdge>[] adjacencyList = new List[9];
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
        System.out.println("Expected: 16.0");
    }
}
