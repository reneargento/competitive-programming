package algorithms.graph.cycle.detection;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Stack;

/**
 * Created by rene on 24/10/17.
 */
public class HasDirectedCycle {

    private boolean visited[];
    private int[] edgeTo;
    private Deque<Integer> cycle; // vertices on  a cycle (if one exists)
    private boolean[] onStack; // vertices on recursive call stack

    public HasDirectedCycle(List<Integer>[] adjacent) {
        onStack = new boolean[adjacent.length];
        edgeTo = new int[adjacent.length];
        visited = new boolean[adjacent.length];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (!visited[vertex]) {
                dfs(adjacent, vertex);
            }
        }
    }

    private void dfs(List<Integer>[] adjacent, int vertex) {
        onStack[vertex] = true;
        visited[vertex] = true;

        for(int neighbor : adjacent[vertex]) {
            if (hasCycle()) {
                return;
            } else if (!visited[neighbor]) {
                edgeTo[neighbor] = vertex;
                dfs(adjacent, neighbor);
            } else if (onStack[neighbor]) {
                cycle = new ArrayDeque<>();

                for(int currentVertex = vertex; currentVertex != neighbor; currentVertex = edgeTo[currentVertex]) {
                    cycle.push(currentVertex);
                }

                cycle.push(neighbor);
                cycle.push(vertex);
            }
        }

        onStack[vertex] = false;
    }

    public boolean hasCycle() {
        return cycle != null;
    }

    public Iterable<Integer> cycle() {
        return cycle;
    }

}
