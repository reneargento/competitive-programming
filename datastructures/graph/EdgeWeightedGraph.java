package datastructures.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 29/06/19.
 */
@SuppressWarnings("unchecked")
public class EdgeWeightedGraph {
    private final int vertices;
    private int edges;
    private List<Edge>[] adjacent;

    public EdgeWeightedGraph(int vertices) {
        this.vertices = vertices;
        adjacent = (List<Edge>[]) new List[vertices];

        for(int vertex = 0; vertex < vertices; vertex++) {
            adjacent[vertex] = new ArrayList<>();
        }
    }

    public int vertices() {
        return vertices;
    }

    public int edgesCount() {
        return edges;
    }

    public void addEdge(Edge edge) {
        int vertex1 = edge.either();
        int vertex2 = edge.other(vertex1);

        adjacent[vertex1].add(edge);
        adjacent[vertex2].add(edge);
        edges++;
    }

    public Iterable<Edge> adjacent(int vertex) {
        return adjacent[vertex];
    }

    public Iterable<Edge> edges() {
        List<Edge> edges = new ArrayList<>();

        for(int vertex = 0; vertex < vertices; vertex++) {
            for(Edge edge : adjacent[vertex]) {
                if (edge.other(vertex) > vertex) {
                    edges.add(edge);
                }
            }
        }

        return edges;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for(int vertex = 0; vertex < vertices(); vertex++) {
            stringBuilder.append(vertex).append(": ");

            for(Edge neighbor : adjacent(vertex)) {
                stringBuilder.append(neighbor).append(" ");
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }
}
