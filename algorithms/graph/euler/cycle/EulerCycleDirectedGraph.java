package algorithms.graph.euler.cycle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by rene on 23/10/17.
 */
@SuppressWarnings("unchecked")
public class EulerCycleDirectedGraph {

    public Stack<Integer> getDirectedEulerianCycle(List<Integer>[] adjacent) {
        // A graph with no edges is considered to have an Eulerian cycle
        int edges = 0;
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null && adjacent[vertex].size() > 0) {
                edges += adjacent[vertex].size();
            }
        }

        if (edges == 0) {
            return new Stack<>();
        }

        // Check if all vertices have indegree equal to their outdegree
        // If any vertex does not, the algorithm may return an Eulerian path instead
        int[] indegrees = new int[adjacent.length];
        int[] outdegrees = new int[adjacent.length];

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null) {
                for(int neighbor : adjacent[vertex]) {
                    outdegrees[vertex]++;
                    indegrees[neighbor]++;
                }
            }
        }

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (indegrees[vertex] != outdegrees[vertex]) {
                return null;
            }
        }

        // Create local view of adjacency lists, to iterate one vertex at a time
        Iterator<Integer>[] adjacentCopy = (Iterator<Integer>[]) new Iterator[adjacent.length];
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null) {
                adjacentCopy[vertex] = adjacent[vertex].iterator();
            }
        }

        // Start the cycle with a non-isolated vertex
        int nonIsolatedVertex = nonIsolatedVertex(adjacent);
        Stack<Integer> dfsStack = new Stack<>();
        dfsStack.push(nonIsolatedVertex);

        Stack<Integer> eulerCycle = new Stack<>();

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

    private int nonIsolatedVertex(List<Integer>[] adjacent) {
        int nonIsolatedVertex = -1;

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null && adjacent[vertex].size() > 0) {
                nonIsolatedVertex = vertex;
                break;
            }
        }

        return nonIsolatedVertex;
    }


    //Tests
    public static void main(String[] args) {
        EulerCycleDirectedGraph directedEulerianCycle = new EulerCycleDirectedGraph();

        List<Integer>[] adjacent1 = (List<Integer>[]) new ArrayList[4];
        for(int vertex = 0; vertex < adjacent1.length; vertex++) {
            adjacent1[vertex] = new ArrayList<>();
        }

        adjacent1[0].add(1);
        adjacent1[1].add(2);
        adjacent1[2].add(3);
        adjacent1[3].add(0);
        adjacent1[3].add(2);

        Stack<Integer> eulerCycle1 = directedEulerianCycle.getDirectedEulerianCycle(adjacent1);

        if (eulerCycle1 != null) {
            directedEulerianCycle.printCycle(eulerCycle1);
        } else {
            System.out.println("There is no directed Eulerian cycle");
        }
        System.out.println("Expected: There is no directed Eulerian cycle\n");

        List<Integer>[] adjacent2 = (List<Integer>[]) new ArrayList[4];

        for(int vertex = 0; vertex < adjacent2.length; vertex++) {
            adjacent2[vertex] = new ArrayList<>();
        }

        adjacent2[0].add(1);
        adjacent2[1].add(2);
        adjacent2[2].add(3);
        adjacent2[3].add(0);

        Stack<Integer> eulerCycle2 = directedEulerianCycle.getDirectedEulerianCycle(adjacent2);

        if (eulerCycle2 != null) {
            directedEulerianCycle.printCycle(eulerCycle2);
        } else {
            System.out.println("There is no directed Eulerian cycle");
        }
        System.out.println("Expected: 0->1 1->2 2->3 3->0\n");

        //Note that vertex 5 is an isolated vertex
        List<Integer>[] adjacent3 = (List<Integer>[]) new ArrayList[6];

        for(int vertex = 0; vertex < adjacent3.length; vertex++) {
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

        Stack<Integer> eulerCycle3 = directedEulerianCycle.getDirectedEulerianCycle(adjacent3);

        if (eulerCycle3 != null) {
            directedEulerianCycle.printCycle(eulerCycle3);
        } else {
            System.out.println("There is no directed Eulerian cycle");
        }
        System.out.println("Expected: 0->1 1->2 2->4 4->3 3->1 1->3 3->2 2->0\n");

        List<Integer>[] adjacent4 = (List<Integer>[]) new ArrayList[4];

        for(int vertex = 0; vertex < adjacent4.length; vertex++) {
            adjacent4[vertex] = new ArrayList<>();
        }

        adjacent4[0].add(1);
        adjacent4[1].add(2);
        adjacent4[2].add(3);
        adjacent4[3].add(0);
        adjacent4[3].add(1);

        Stack<Integer> eulerCycle4 = directedEulerianCycle.getDirectedEulerianCycle(adjacent4);

        if (eulerCycle4 != null) {
            directedEulerianCycle.printCycle(eulerCycle4);
        } else {
            System.out.println("There is no directed Eulerian cycle");
        }
        System.out.println("Expected: There is no directed Eulerian cycle");
    }

    private void printCycle(Stack<Integer> eulerCycle) {
        System.out.println("Euler cycle:");

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
