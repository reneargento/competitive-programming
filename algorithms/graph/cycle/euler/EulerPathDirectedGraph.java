package algorithms.graph.cycle.euler;

import java.util.*;

/**
 * Created by Rene Argento on 24/10/17.
 */
@SuppressWarnings("unchecked")
public class EulerPathDirectedGraph {

    private static Deque<Integer> getDirectedEulerPath(List<Integer>[] adjacent) {
        int edges = 0;

        // Check if all vertices have in-degree equal to their out-degree (Eulerian path AND cycle)
        // OR
        // if all vertices have in-degree equal to their out-degree, except 2,
        // where one of these vertices has one more out-degree than in-degree (this is the start vertex)
        // and the other vertex has one more in-degree than out-degree (this is the end vertex) (Eulerian path)
        int[] inDegrees = new int[adjacent.length];
        int[] outDegrees = new int[adjacent.length];

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            edges += adjacent[vertex].size();

            for (int neighbor : adjacent[vertex]) {
                outDegrees[vertex]++;
                inDegrees[neighbor]++;
            }
        }

        // A graph with no edges is considered to have an Eulerian path
        if (edges == 0) {
            return new ArrayDeque<>();
        }

        int startVertexCandidates = 0;
        int endVertexCandidates = 0;
        int startVertex = -1;

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            if (inDegrees[vertex] == outDegrees[vertex]) {
                if (inDegrees[vertex] > 0 && startVertex == -1) {
                    startVertex = vertex;
                }
            } else {
                if (inDegrees[vertex] - 1 == outDegrees[vertex]) {
                    endVertexCandidates++;
                } else if (inDegrees[vertex] + 1 == outDegrees[vertex]) {
                    startVertexCandidates++;
                    startVertex = vertex;
                } else {
                    return null;
                }
            }
        }

        boolean hasEulerCycle = startVertexCandidates == 0 && endVertexCandidates == 0;
        boolean hasEulerPath = startVertexCandidates == 1 && endVertexCandidates == 1;
        if (!hasEulerCycle && !hasEulerPath) {
            return null;
        }

        Deque<Integer> dfsStack = new ArrayDeque<>();
        dfsStack.push(startVertex);

        Deque<Integer> eulerPath = new ArrayDeque<>();
        int[] index = new int[adjacent.length];

        while (!dfsStack.isEmpty()) {
            int vertex = dfsStack.peek();
            int indexValue = index[vertex];

            if (indexValue < adjacent[vertex].size()) {
                int neighborVertex = adjacent[vertex].get(indexValue);
                dfsStack.push(neighborVertex);
                index[vertex]++;
            } else {
                // Push vertex with no more leaving edges to the Euler path
                eulerPath.push(vertex);
                dfsStack.pop();
            }
        }

        // For each edge visited, we visited a vertex.
        // Add 1 because the first and last vertices are the same (in the case of an Euler cycle)
        // or because the vertex with one more in-degree than out-degree is visited twice (in the case of an Euler path)
        if (eulerPath.size() == edges + 1) {
            return eulerPath;
        } else {
            return null;
        }
    }

    // Tests
    public static void main(String[] args) {
        List<Integer>[] adjacent1 = (List<Integer>[]) new ArrayList[4];
        for (int vertex = 0; vertex < adjacent1.length; vertex++) {
            adjacent1[vertex] = new ArrayList<>();
        }

        adjacent1[0].add(1);
        adjacent1[1].add(2);
        adjacent1[2].add(3);
        adjacent1[3].add(0);
        adjacent1[3].add(2);

        Deque<Integer> eulerCycle1 = EulerPathDirectedGraph.getDirectedEulerPath(adjacent1);

        if (eulerCycle1 != null) {
            EulerPathDirectedGraph.printCycle(eulerCycle1);
        } else {
            System.out.println("There is no directed Eulerian path");
        }
        System.out.println("Expected:   3->0 0->1 1->2 2->3 3->2\n");

        List<Integer>[] adjacent2 = (List<Integer>[]) new ArrayList[4];

        for (int vertex = 0; vertex < adjacent2.length; vertex++) {
            adjacent2[vertex] = new ArrayList<>();
        }

        adjacent2[0].add(1);
        adjacent2[1].add(2);
        adjacent2[2].add(3);
        adjacent2[3].add(0);

        Deque<Integer> eulerCycle2 = EulerPathDirectedGraph.getDirectedEulerPath(adjacent2);

        if (eulerCycle2 != null) {
            EulerPathDirectedGraph.printCycle(eulerCycle2);
        } else {
            System.out.println("There is no directed Eulerian path");
        }
        System.out.println("Expected:   0->1 1->2 2->3 3->0\n");

        //Note that vertex 5 is an isolated vertex
        List<Integer>[] adjacent3 = (List<Integer>[]) new ArrayList[6];

        for (int vertex = 0; vertex < adjacent3.length; vertex++) {
            adjacent3[vertex] = new ArrayList<>();
        }

        adjacent3[0].add(1);
        adjacent3[1].add(2);
        adjacent3[2].add(0);
        adjacent3[1].add(3);
        adjacent3[3].add(1);
        adjacent3[3].add(2);
        adjacent3[2].add(4);
        adjacent3[4].add(3);

        Deque<Integer> eulerCycle3 = EulerPathDirectedGraph.getDirectedEulerPath(adjacent3);

        if (eulerCycle3 != null) {
            EulerPathDirectedGraph.printCycle(eulerCycle3);
        } else {
            System.out.println("There is no directed Eulerian path");
        }
        System.out.println("Expected:   0->1 1->2 2->4 4->3 3->1 1->3 3->2 2->0\n");

        List<Integer>[] adjacent4 = (List<Integer>[]) new ArrayList[4];

        for (int vertex = 0; vertex < adjacent4.length; vertex++) {
            adjacent4[vertex] = new ArrayList<>();
        }

        adjacent4[0].add(1);
        adjacent4[1].add(2);
        adjacent4[2].add(3);
        adjacent4[3].add(0);
        adjacent4[3].add(1);

        Deque<Integer> eulerCycle4 = EulerPathDirectedGraph.getDirectedEulerPath(adjacent4);

        if (eulerCycle4 != null) {
            EulerPathDirectedGraph.printCycle(eulerCycle4);
        } else {
            System.out.println("There is no directed Eulerian path");
        }
        System.out.println("Expected:   3->0 0->1 1->2 2->3 3->1");
    }

    private static void printCycle(Deque<Integer> eulerPath) {
        System.out.print("Euler path: ");

        while (!eulerPath.isEmpty()) {
            int vertex = eulerPath.pop();

            if (!eulerPath.isEmpty()) {
                System.out.print(vertex + "->" + eulerPath.peek());

                if (eulerPath.size() > 1) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }
}
