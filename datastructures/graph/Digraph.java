package datastructures.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 30/03/18.
 */
@SuppressWarnings("unchecked")
public class Digraph {

    private final int vertices;
    private int edges;
    private List<Integer>[] adjacent;
    private int[] indegrees;
    private int[] outdegrees;

    public Digraph(int vertices) {
        this.vertices = vertices;
        this.edges = 0;

        indegrees = new int[vertices];
        outdegrees = new int[vertices];

        adjacent = (ArrayList<Integer>[]) new ArrayList[vertices];

        for(int vertex = 0; vertex < vertices; vertex++) {
            adjacent[vertex] = new ArrayList<>();
        }
    }

    public int vertices() {
        return vertices;
    }

    public int edges() {
        return edges;
    }

    public void addEdge(int vertex1, int vertex2) {
        adjacent[vertex1].add(vertex2);
        edges++;

        outdegrees[vertex1]++;
        indegrees[vertex2]++;
    }

    public List<Integer>[] getAdjacencyList() {
        return adjacent;
    }

    public Iterable<Integer> adjacent(int vertex) {
        return adjacent[vertex];
    }

    public int indegree(int vertex) {
        return indegrees[vertex];
    }

    public int outdegree(int vertex) {
        return outdegrees[vertex];
    }

    public Digraph reverse() {
        Digraph reverse = new Digraph(vertices);

        for(int vertex = 0; vertex < vertices; vertex++) {
            for(int neighbor : adjacent(vertex)) {
                reverse.addEdge(neighbor, vertex);
            }
        }

        return reverse;
    }
}
