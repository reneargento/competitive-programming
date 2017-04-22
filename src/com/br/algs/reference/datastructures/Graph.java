package com.br.algs.reference.datastructures;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by rene on 21/04/17.
 */
public class Graph {

    private class Vertex {
        int id;
        boolean processed;
        LinkedList<Edge> edgesAssociated;
    }

    private class Edge {
        //Undirected graph
//        Vertex vertex1;
//        Vertex vertex2;

        //Directed graph
        Vertex tailVertex;
        Vertex headVertex;

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

        if(vertices.get(vertexId1) == null) {
            addVertex(vertexId1);
        }
        if(vertices.get(vertexId2) == null) {
            addVertex(vertexId2);
        }

        Vertex vertex1 = vertices.get(vertexId1);
        Vertex vertex2 = vertices.get(vertexId2);

        Edge edge = new Edge();
        //Undirected graph
//        edge.vertex1 = vertex1;
//        edge.vertex2 = vertex2;

        //Directed graph
        edge.headVertex = vertex1;
        edge.tailVertex = vertex2;

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
