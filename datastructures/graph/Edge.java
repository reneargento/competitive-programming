package datastructures.graph;

/**
 * Created by Rene Argento on 29/06/19.
 */
public class Edge implements Comparable<Edge> {
    private final int vertex1;
    private final int vertex2;
    private final double weight;

    public Edge(int vertex1, int vertex2, double weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return vertex1;
    }

    public int other(int vertex) {
        if (vertex == vertex1) {
            return vertex2;
        } else if (vertex == vertex2) {
            return vertex1;
        } else {
            throw new RuntimeException("Inconsistent edge");
        }
    }

    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.5f", vertex1, vertex2, weight);
    }

}