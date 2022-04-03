package datastructures.graph;

/**
 * Created by rene on 09/12/17.
 */
public class DirectedEdge {
    private final int vertex1;
    private final int vertex2;
    private final double weight;

    public DirectedEdge(int vertex1, int vertex2, double weight) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int from() {
        return vertex1;
    }

    public int to() {
        return vertex2;
    }

    public String toString() {
        return String.format("%d->%d %.2f", vertex1, vertex2, weight);
    }
}
