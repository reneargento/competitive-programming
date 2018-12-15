package algorithms.graph.euler.cycle;

import java.util.*;

/**
 * Created by rene on 30/09/17.
 */
@SuppressWarnings("unchecked")
public class EulerPathUndirectedGraph {

    private class Edge {
        int vertex1;
        int vertex2;
        boolean isUsed;

        Edge(int vertex1, int vertex2) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            isUsed = false;
        }

        public int otherVertex(int vertex) {
            if (vertex == vertex1) {
                return vertex2;
            } else {
                return vertex1;
            }
        }
    }

    public Stack<Integer> getEulerPath(List<Integer>[] adjacent) {

        // A graph with no edges is considered to have an Eulerian path
        int edges = 0;
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null && adjacent[vertex].size() > 0) {
                edges += adjacent[vertex].size();
            }
        }

        edges /= 2;

        if (edges == 0) {
            return new Stack<>();
        }

        // Necessary condition: all vertices have even degree (Eulerian path AND cycle)
        // or
        // exactly 2 vertices have even degrees (Eulerian path)
        int verticesWithOddDegree = 0;

        int vertexWithOddDegree = -1;

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            if (adjacent[vertex] != null && adjacent[vertex].size() % 2 != 0) {
                verticesWithOddDegree++;

                if (vertexWithOddDegree == -1) {
                    vertexWithOddDegree = vertex;
                }
            }
        }

        if (verticesWithOddDegree != 0 && verticesWithOddDegree != 2) {
            return null;
        }

        // Create local view of adjacency lists, to iterate one vertex at a time
        Queue<Edge>[] adjacentCopy = (Queue<Edge>[]) new Queue[adjacent.length];
        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            adjacentCopy[vertex] = new LinkedList<>();
        }

        for(int vertex = 0; vertex < adjacent.length; vertex++) {
            int selfLoops = 0;

            if (adjacent[vertex] != null) {
                for(int neighbor : adjacent[vertex]) {
                    //Careful with self-loops
                    if (vertex == neighbor) {
                        if (selfLoops % 2 == 0) {
                            Edge edge = new Edge(vertex, neighbor);
                            adjacentCopy[vertex].offer(edge);
                            adjacentCopy[neighbor].offer(edge);
                        }

                        selfLoops++;
                    } else {
                        if (vertex < neighbor) {
                            Edge edge = new Edge(vertex, neighbor);
                            adjacentCopy[vertex].offer(edge);
                            adjacentCopy[neighbor].offer(edge);
                        }
                    }
                }
            }
        }

        // If there are vertices with odd degrees, start with one of them
        // Otherwise, start the cycle with a non-isolated vertex
        int sourceVertex;

        if (vertexWithOddDegree != -1) {
            sourceVertex = vertexWithOddDegree;
        } else {
            sourceVertex = nonIsolatedVertex(adjacent);
        }

        Stack<Integer> dfsStack = new Stack<>();
        dfsStack.push(sourceVertex);

        Stack<Integer> eulerPath = new Stack<>();

        while (!dfsStack.isEmpty()) {
            int vertex = dfsStack.pop();

            while (!adjacentCopy[vertex].isEmpty()) {
                Edge edge = adjacentCopy[vertex].poll();
                if (edge.isUsed) {
                    continue;
                }
                edge.isUsed = true;

                dfsStack.push(vertex);
                vertex = edge.otherVertex(vertex);
            }

            // Push vertex with no more leaving edges to the Euler path
            eulerPath.push(vertex);
        }

        // For each edge visited, we visited a vertex.
        // Add 1 because the first and last vertices are the same (in the case of an Euler circuit)
        // or because the vertex with one more indegree than outdegree is visited twice (in the case of an Euler path)
        if (eulerPath.size() == edges + 1) {
            return eulerPath;
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
        EulerPathUndirectedGraph eulerPath = new EulerPathUndirectedGraph();

        List<Integer>[] adjacent1 = (List<Integer>[]) new ArrayList[4];
        for(int vertex = 0; vertex < adjacent1.length; vertex++) {
            adjacent1[vertex] = new ArrayList<>();
        }

        adjacent1[0].add(1);
        adjacent1[1].add(0);
        adjacent1[1].add(2);
        adjacent1[2].add(1);
        adjacent1[2].add(3);
        adjacent1[3].add(2);
        adjacent1[3].add(0);
        adjacent1[0].add(3);
        adjacent1[3].add(2);
        adjacent1[2].add(3);

        Stack<Integer> eulerPath1 = eulerPath.getEulerPath(adjacent1);

        if (eulerPath1 != null) {
            printPath(eulerPath1);
        } else {
            System.out.println("There is no Eulerian path");
        }
        System.out.println("Expected: 2-1 1-0 0-3 3-2 2-3\n");

        List<Integer>[] adjacent2 = (List<Integer>[]) new ArrayList[4];

        for(int vertex = 0; vertex < adjacent2.length; vertex++) {
            adjacent2[vertex] = new ArrayList<>();
        }

        adjacent2[0].add(1);
        adjacent2[1].add(0);
        adjacent2[1].add(2);
        adjacent2[2].add(1);
        adjacent2[2].add(3);
        adjacent2[3].add(2);
        adjacent2[3].add(0);
        adjacent2[0].add(3);

        Stack<Integer> eulerPath2 = eulerPath.getEulerPath(adjacent2);

        if (eulerPath2 != null) {
            printPath(eulerPath2);
        } else {
            System.out.println("There is no Eulerian path");
        }
        System.out.println("Expected: 0-1 1-2 2-3 3-0\n");

        //Note that vertex 12 is an isolated vertex
        List<Integer>[] adjacent3 = (List<Integer>[]) new ArrayList[13];

        for(int vertex = 0; vertex < adjacent3.length; vertex++) {
            adjacent3[vertex] = new ArrayList<>();
        }

        adjacent3[0].add(9);
        adjacent3[9].add(0);
        adjacent3[0].add(3);
        adjacent3[3].add(0);
        adjacent3[10].add(9);
        adjacent3[9].add(10);
        adjacent3[10].add(3);
        adjacent3[3].add(10);
        adjacent3[3].add(4);
        adjacent3[4].add(3);
        adjacent3[3].add(6);
        adjacent3[6].add(3);
        adjacent3[3].add(2);
        adjacent3[2].add(3);
        adjacent3[3].add(9);
        adjacent3[9].add(3);
        adjacent3[9].add(6);
        adjacent3[6].add(9);
        adjacent3[9].add(8);
        adjacent3[8].add(9);
        adjacent3[9].add(11);
        adjacent3[11].add(9);
        adjacent3[4].add(2);
        adjacent3[2].add(4);
        adjacent3[6].add(2);
        adjacent3[2].add(6);
        adjacent3[6].add(8);
        adjacent3[8].add(6);
        adjacent3[11].add(8);
        adjacent3[8].add(11);
        adjacent3[2].add(5);
        adjacent3[5].add(2);
        adjacent3[2].add(1);
        adjacent3[1].add(2);
        adjacent3[2].add(8);
        adjacent3[8].add(2);
        adjacent3[8].add(5);
        adjacent3[5].add(8);
        adjacent3[8].add(7);
        adjacent3[7].add(8);
        adjacent3[1].add(7);
        adjacent3[7].add(1);

        Stack<Integer> eulerPath3 = eulerPath.getEulerPath(adjacent3);

        if (eulerPath3 != null) {
            printPath(eulerPath3);
        } else {
            System.out.println("There is no Eulerian path");
        }
        System.out.println("Expected: 0-9 9-3 3-2 2-1 1-7 7-8 8-2 2-4 4-3 3-10 10-9 9-6 6-2 2-5 5-8 8-9 9-11 11-8 8-6 6-3 3-0\n");

        List<Integer>[] adjacent4 = (List<Integer>[]) new ArrayList[4];

        for(int vertex = 0; vertex < adjacent4.length; vertex++) {
            adjacent4[vertex] = new ArrayList<>();
        }

        adjacent4[0].add(1);
        adjacent4[1].add(0);
        adjacent4[1].add(2);
        adjacent4[2].add(1);
        adjacent4[2].add(3);
        adjacent4[3].add(2);
        adjacent4[3].add(0);
        adjacent4[0].add(3);
        adjacent4[3].add(1);
        adjacent4[1].add(3);

        Stack<Integer> eulerPath4 = eulerPath.getEulerPath(adjacent4);

        if (eulerPath4 != null) {
            printPath(eulerPath4);
        } else {
            System.out.println("There is no Eulerian path");
        }
        System.out.println("Expected: 1-0 0-3 3-1 1-2 2-3");
    }

    private static void printPath(Stack<Integer> eulerPath) {
        System.out.println("Euler path:");

        while (!eulerPath.isEmpty()) {
            int vertex = eulerPath.pop();

            if (!eulerPath.isEmpty()) {
                System.out.print(vertex + "-" + eulerPath.peek());

                if (eulerPath.size() > 1) {
                    System.out.print(" ");
                }
            }
        }
        System.out.println();
    }

}
