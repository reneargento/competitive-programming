package algorithms.graph.cycle.euler;

import java.util.*;

/**
 * Created by Rene Argento on 23/10/17.
 */
// An Eulerian cycle is a a cycle that starts in a vertex v, visits every edge of the graph, and returns to v,
// with not repeated edges (repeated vertices are allowed).
@SuppressWarnings("unchecked")
public class EulerCycleDirectedGraph {

    private static Deque<Integer> getDirectedEulerCycle(List<Integer>[] adjacent) {
        int edges = 0;

        // Check if all vertices have in-degree equal to their out-degree
        // If any vertex does not, there may exist an Eulerian path, but not an Eulerian cycle
        int[] inDegrees = new int[adjacent.length];
        int[] outDegrees = new int[adjacent.length];

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            edges += adjacent[vertex].size();

            for (int neighbor : adjacent[vertex]) {
                outDegrees[vertex]++;
                inDegrees[neighbor]++;
            }
        }

        // A graph with no edges is considered to have an Eulerian cycle
        if (edges == 0) {
            return new ArrayDeque<>();
        }

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            if (inDegrees[vertex] != outDegrees[vertex]) {
                return null;
            }
        }

        // Create local view of adjacency lists, to iterate one vertex at a time
        Iterator<Integer>[] adjacentCopy = (Iterator<Integer>[]) new Iterator[adjacent.length];
        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            adjacentCopy[vertex] = adjacent[vertex].iterator();
        }

        // Start the cycle with a non-isolated vertex
        int nonIsolatedVertex = nonIsolatedVertex(adjacent);
        Deque<Integer> dfsStack = new ArrayDeque<>();
        dfsStack.push(nonIsolatedVertex);

        Deque<Integer> eulerCycle = new ArrayDeque<>();

        while (!dfsStack.isEmpty()) {
            int vertex = dfsStack.pop();

            while (adjacentCopy[vertex].hasNext()) {
                dfsStack.push(vertex);
                vertex = adjacentCopy[vertex].next();
            }

            // Push vertex with no more leaving edges to the Euler cycle
            eulerCycle.push(vertex);
        }

        // For each edge visited, we visited a vertex. Add 1 because the first and last vertices are the same.
        if (eulerCycle.size() == edges + 1) {
            return eulerCycle;
        } else {
            return null;
        }
    }

    private static int nonIsolatedVertex(List<Integer>[] adjacent) {
        int nonIsolatedVertex = -1;

        for (int vertex = 0; vertex < adjacent.length; vertex++) {
            if (!adjacent[vertex].isEmpty()) {
                nonIsolatedVertex = vertex;
                break;
            }
        }
        return nonIsolatedVertex;
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

        Deque<Integer> eulerCycle1 = EulerCycleDirectedGraph.getDirectedEulerCycle(adjacent1);

        if (eulerCycle1 != null) {
            EulerCycleDirectedGraph.printCycle(eulerCycle1);
        } else {
            System.out.println("There is no directed Eulerian cycle");
        }
        System.out.println("Expected: There is no directed Eulerian cycle\n");

        List<Integer>[] adjacent2 = (List<Integer>[]) new ArrayList[4];

        for (int vertex = 0; vertex < adjacent2.length; vertex++) {
            adjacent2[vertex] = new ArrayList<>();
        }
        adjacent2[0].add(1);
        adjacent2[1].add(2);
        adjacent2[2].add(3);
        adjacent2[3].add(0);

        Deque<Integer> eulerCycle2 = EulerCycleDirectedGraph.getDirectedEulerCycle(adjacent2);

        if (eulerCycle2 != null) {
            EulerCycleDirectedGraph.printCycle(eulerCycle2);
        } else {
            System.out.println("There is no directed Eulerian cycle");
        }
        System.out.println("Expected:    0->1 1->2 2->3 3->0\n");

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

        Deque<Integer> eulerCycle3 = EulerCycleDirectedGraph.getDirectedEulerCycle(adjacent3);

        if (eulerCycle3 != null) {
            EulerCycleDirectedGraph.printCycle(eulerCycle3);
        } else {
            System.out.println("There is no directed Eulerian cycle");
        }
        System.out.println("Expected:    0->1 1->2 2->4 4->3 3->1 1->3 3->2 2->0\n");

        List<Integer>[] adjacent4 = (List<Integer>[]) new ArrayList[4];

        for (int vertex = 0; vertex < adjacent4.length; vertex++) {
            adjacent4[vertex] = new ArrayList<>();
        }
        adjacent4[0].add(1);
        adjacent4[1].add(2);
        adjacent4[2].add(3);
        adjacent4[3].add(0);
        adjacent4[3].add(1);

        Deque<Integer> eulerCycle4 = EulerCycleDirectedGraph.getDirectedEulerCycle(adjacent4);

        if (eulerCycle4 != null) {
            EulerCycleDirectedGraph.printCycle(eulerCycle4);
        } else {
            System.out.println("There is no directed Eulerian cycle");
        }
        System.out.println("Expected: There is no directed Eulerian cycle");
    }

    private static void printCycle(Deque<Integer> eulerCycle) {
        System.out.print("Euler cycle: ");

        while (!eulerCycle.isEmpty()) {
            int vertex = eulerCycle.pop();

            if (!eulerCycle.isEmpty()) {
                System.out.print(vertex + "->" + eulerCycle.peek());

                if (eulerCycle.size() > 1) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }
}
