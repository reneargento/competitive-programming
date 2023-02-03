package algorithms.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Rene Argento on 30/11/22.
 */
// Computes a path between two nodes in an unrooted tree.
// O(n) runtime complexity
@SuppressWarnings("unchecked")
public class ComputePathInUnrootedTree {

    private static class Edge {
        int node;
        int length;

        public Edge(int node, int length) {
            this.node = node;
            this.length = length;
        }
    }

    private static class Length {
        int value;
    }

    private static class Result {
        Length length;
        List<Integer> path;

        public Result(Length length, List<Integer> path) {
            this.length = length;
            this.path = path;
        }
    }

    private static Result computePath(List<Edge>[] edges, int source, int destination) {
        int[] parent = new int[edges.length + 1];
        Length length = new Length();
        boolean[] visited = new boolean[edges.length + 1];

        computePath(destination, edges, visited, parent, length, source);

        List<Integer> path = new ArrayList<>();
        int node = destination;
        while (parent[node] != 0) {
            path.add(node);
            node = parent[node];
        }
        path.add(source);
        Collections.reverse(path);

        return new Result(length, path);
    }

    private static boolean computePath(int destination, List<Edge>[] edges, boolean[] visited, int[] parent, Length length,
                                       int vertex) {
        if (destination == vertex) {
            return true;
        }
        visited[vertex] = true;

        for (Edge edge : edges[vertex]) {
            if (!visited[edge.node]
                    && computePath(destination, edges, visited, parent, length, edge.node)) {
                length.value += edge.length;
                parent[edge.node] = vertex;
                return true;
            }
        }
        return false;
    }

    //         5
    //     3       7
    //   2  4   6  8  1
    public static void main(String[] args) {
        List<Edge>[] edges = new List[9];
        for (int i = 0; i < edges.length; i++) {
            edges[i] = new ArrayList<>();
        }
        edges[5].add(new Edge(3, 10));
        edges[3].add(new Edge(5, 10));
        edges[5].add(new Edge(7, 2));
        edges[7].add(new Edge(5, 2));
        edges[3].add(new Edge(2, 5));
        edges[2].add(new Edge(3, 5));
        edges[3].add(new Edge(4, 4));
        edges[4].add(new Edge(3, 4));
        edges[7].add(new Edge(6, 5));
        edges[6].add(new Edge(7, 5));
        edges[7].add(new Edge(8, 12));
        edges[8].add(new Edge(7, 12));
        edges[7].add(new Edge(1, 1));
        edges[1].add(new Edge(7, 1));

        Result path1 = computePath(edges, 2, 7);
        System.out.println("Length: " + path1.length.value + " Expected: 17");
        printPath(path1.path);
        System.out.println("Expected: 2 -> 3 -> 5 -> 7");

        System.out.println();
        Result path2 = computePath(edges, 6, 5);
        System.out.println("Length: " + path2.length.value + " Expected: 7");
        printPath(path2.path);
        System.out.println("Expected: 6 -> 7 -> 5");
    }

    private static void printPath(List<Integer> path) {
        System.out.print("Path: ");
        System.out.print(path.get(0));
        for (int i = 1; i < path.size(); i++) {
            System.out.print(" -> " + path.get(i));
        }
        System.out.println();
    }
}
