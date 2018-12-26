package algorithms.graph.johnson.all.cycles;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by rene on 30/12/17.
 */
public class DepthFirstOrderDirectedWeighted {

    private boolean[] visited;

    private Queue<Integer> preOrder;
    private Queue<Integer> postOrder;
    private Deque<Integer> reversePostOrder;

    public DepthFirstOrderDirectedWeighted(EdgeWeightedDigraph edgeWeightedDigraph) {
        preOrder = new LinkedList<>();
        postOrder = new LinkedList<>();
        reversePostOrder = new ArrayDeque<>();
        visited = new boolean[edgeWeightedDigraph.vertices()];

        for(int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            if (!visited[vertex]) {
                dfs(edgeWeightedDigraph, vertex);
            }
        }
    }

    private void dfs(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
        preOrder.offer(vertex);

        visited[vertex] = true;

        for(DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            int neighbor = edge.to();

            if (!visited[neighbor]) {
                dfs(edgeWeightedDigraph, neighbor);
            }
        }

        postOrder.offer(vertex);
        reversePostOrder.push(vertex);
    }

    public Iterable<Integer> preOrder() {
        return preOrder;
    }

    public Iterable<Integer> postOrder() {
        return postOrder;
    }

    public Iterable<Integer> reversePostOrder() {
        return reversePostOrder;
    }

}
