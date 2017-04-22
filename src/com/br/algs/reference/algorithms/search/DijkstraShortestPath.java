package com.br.algs.reference.algorithms.search;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by rene on 21/04/17.
 */
public class DijkstraShortestPath {

    private class Graph {

        private class Vertex {
            int id;
            boolean processed;
            LinkedList<Edge> edgesAssociated;
        }

        private class Edge {
            Vertex vertex1;
            Vertex vertex2;
            int length;
        }

        HashMap<Integer, Vertex> vertices;
        LinkedList<Edge> edges;

        public Graph() {
            vertices = new HashMap<>();
            edges = new LinkedList<>();
        }

        //O(1)
        public void addVertex(int vertexId) {
            Vertex vertex = new Vertex();
            vertex.id = vertexId;
            vertex.processed = false;
            vertex.edgesAssociated = new LinkedList<>();

            vertices.put(vertexId, vertex);
        }

        //O(1)
        public void addEdge(int vertexId1, int vertexId2, int length) {
            if(length < 0) {
                throw new UnsupportedOperationException("Edge length cannot be negative");
            }

            if(vertices.get(vertexId1) == null) {
                addVertex(vertexId1);
            }
            if(vertices.get(vertexId2) == null) {
                addVertex(vertexId2);
            }

            Vertex vertex1 = vertices.get(vertexId1);
            Vertex vertex2 = vertices.get(vertexId2);

            Edge edge = new Edge();
            edge.vertex1 = vertex1;
            edge.vertex2 = vertex2;
            edge.length = length;

            edges.add(edge);

            vertices.get(vertexId1).edgesAssociated.add(edge);
            vertices.get(vertexId2).edgesAssociated.add(edge);
        }

        //O(1)
        public int getVerticesCount() {
            return vertices.size();
        }

        //O(1)
        public void setVertexProcessed(int vertexId) {
            vertices.get(vertexId).processed = true;
        }
    }

    private static final String GRAPH_FILE_PATH = "";
    //By definition
    private static final int UNCOMPUTED_DISTANCE = 1000000;

    private int[] computedShortestPathDistances;

    //O(n * log(m))
    private void computeShortestPath(String filePath, String characterSeparator) {
        Graph graph = new DijkstraShortestPath().new Graph();
        generateGraph(graph, filePath, characterSeparator);

        int verticesCount = graph.getVerticesCount();

        computedShortestPathDistances = new int[verticesCount + 1];

        setDefaultDistances();

        //Distance from S to itself is 0
        computedShortestPathDistances[1] = 0;
        graph.vertices.get(1).processed = true;

        //Using Java's priority queue
        PriorityQueue<Graph.Edge> heap = new PriorityQueue<>(new Comparator<Graph.Edge>() {
            @Override
            public int compare(Graph.Edge edge1, Graph.Edge edge2) {
                return edge1.length - edge2.length;
            }
        });

        //Add edges associated to the first vertex to the heap
        for(Graph.Edge edge : graph.vertices.get(1).edgesAssociated) {
            heap.add(edge);
        }

        while (heap.size() > 0) {
            Graph.Edge edge = heap.poll();

            if(!edge.vertex1.processed) {
                graph.setVertexProcessed(edge.vertex1.id);
                computeAndAddEdgeToHeap(graph, heap, edge, edge.vertex1.id);
            } else if(!edge.vertex2.processed) {
                graph.setVertexProcessed(edge.vertex2.id);
                computeAndAddEdgeToHeap(graph, heap, edge, edge.vertex2.id);
            }
        }
    }

    @SuppressWarnings("unchecked")
    private void computeAndAddEdgeToHeap(Graph graph, PriorityQueue heap, Graph.Edge edge, int vertexId) {
        computedShortestPathDistances[vertexId] = edge.length;

        for(Graph.Edge newEdge : graph.vertices.get(vertexId).edgesAssociated) {
            if (!newEdge.vertex1.processed || !newEdge.vertex2.processed) {
                newEdge.length = edge.length + newEdge.length;
                heap.add(newEdge);
            }
        }
    }

    private void generateGraph(Graph graph, String filePath, String characterSeparator) {
        Path path = Paths.get(filePath);
        String[] values;

        try {
            List<String> lines = Files.readAllLines(path);

            for (String line : lines) {
                values = line.split(characterSeparator);

                int vertexId = Integer.parseInt(values[0]);

                for (int adjacentVertex = 1; adjacentVertex < values.length; adjacentVertex++) {
                    String adjacentVertexIdString = values[adjacentVertex].substring(0, values[adjacentVertex].indexOf(","));
                    int adjacentVertexId = Integer.parseInt(adjacentVertexIdString);

                    String lengthString = values[adjacentVertex].
                            substring(values[adjacentVertex].indexOf(",") + 1, values[adjacentVertex].length());
                    int length = Integer.parseInt(lengthString);

                    if(vertexId <= adjacentVertexId) {
                        graph.addEdge(vertexId, adjacentVertexId, length);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setDefaultDistances() {
        for (int i=0; i < computedShortestPathDistances.length; i++) {
            computedShortestPathDistances[i] = UNCOMPUTED_DISTANCE;
        }
    }

    private void printAllDistances() {
        for(int i=1; i < computedShortestPathDistances.length; i++) {
            System.out.println(i + " " + computedShortestPathDistances[i]);
        }
    }

}
