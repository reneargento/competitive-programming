package algorithms.tree.diameter;

import java.util.*;

/**
 * Created by Rene Argento on 22/07/24.
 */
// Computes the best roots for a tree - its center (or centers) - and the worst roots for a tree - the vertices on the
// diameter extremities.
// Time complexity: O(N)
// Based on problem UVa-10459 The Tree Root
public class BestAndWorstRoots {

    private static class Result {
        List<Integer> bestRoots;
        List<Integer> worstRoots;

        public Result(List<Integer> bestRoots, List<Integer> worstRoots) {
            this.bestRoots = bestRoots;
            this.worstRoots = worstRoots;
        }
    }

    private static Result computeRoots(List<Integer>[] adjacencyList) {
        List<Integer> bestRoots = getDiameterCenters(adjacencyList);
        List<Integer> worstRoots = new ArrayList<>();

        for (int bestRoot : bestRoots) {
            int[] parent = new int[adjacencyList.length];
            int[] distances = new int[adjacencyList.length];

            List<Integer> furthestVerticesFromBestRoot = getFurthestVertices(adjacencyList, parent, distances, bestRoot);
            worstRoots.addAll(furthestVerticesFromBestRoot);
        }

        Collections.sort(bestRoots);
        Collections.sort(worstRoots);
        return new Result(bestRoots, worstRoots);
    }

    public static List<Integer> getDiameterCenters(List<Integer>[] adjacencyList) {
        List<Integer> centers = new ArrayList<>();
        int[] parent = new int[adjacencyList.length];
        int[] distances = new int[adjacencyList.length];

        int furthestVertex = getFurthestVertices(adjacencyList, parent, distances, 0).get(0);
        int furthestVertexFromFurthest = getFurthestVertices(adjacencyList, parent, distances, furthestVertex).get(0);

        int diameter = distances[furthestVertexFromFurthest];
        double radius = Math.ceil(diameter / 2.0);
        int center = furthestVertexFromFurthest;

        while (distances[center] > radius) {
            center = parent[center];
        }

        centers.add(center);
        if (diameter % 2 == 1) {
            centers.add(parent[center]);
        }
        return centers;
    }

    private static List<Integer> getFurthestVertices(List<Integer>[] adjacencyList, int[] parent, int[] distances,
                                                     int sourceVertexId) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[adjacencyList.length];
        queue.offer(sourceVertexId);
        visited[sourceVertexId] = true;

        Arrays.fill(distances, -1);
        distances[sourceVertexId] = 0;
        parent[sourceVertexId] = -1;

        List<Integer> furthestVertices = new ArrayList<>();
        int furthestDistance = 0;

        while (!queue.isEmpty()) {
            int vertexId = queue.poll();

            for (int neighbor : adjacencyList[vertexId]) {

                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    distances[neighbor] = distances[vertexId] + 1;
                    parent[neighbor] = vertexId;
                    queue.offer(neighbor);

                    if (distances[neighbor] > furthestDistance) {
                        furthestVertices = new ArrayList<>();
                        furthestVertices.add(neighbor);
                        furthestDistance = distances[neighbor];
                    } else if (distances[neighbor] == furthestDistance) {
                        furthestVertices.add(neighbor);
                    }
                }
            }
        }
        return furthestVertices;
    }


    // Tree:
    //     3
    //    /
    //   2
    //  / \
    // 4   0
    //     |
    //     1
    //    / \
    //   5   6
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        List<Integer>[] adjacencyList = new List[7];
        for (int i = 0; i < adjacencyList.length; i++) {
            adjacencyList[i] = new ArrayList<>();
        }
        adjacencyList[0].add(1);
        adjacencyList[0].add(2);
        adjacencyList[1].add(0);
        adjacencyList[1].add(5);
        adjacencyList[1].add(6);
        adjacencyList[2].add(0);
        adjacencyList[2].add(3);
        adjacencyList[2].add(4);
        adjacencyList[3].add(2);
        adjacencyList[4].add(2);
        adjacencyList[5].add(1);
        adjacencyList[6].add(1);

        Result result = computeRoots(adjacencyList);
        System.out.print("Best roots: ");
        printResults(result.bestRoots);
        System.out.println("Expected: 0");

        System.out.print("\nWorst roots: ");
        printResults(result.worstRoots);
        System.out.println("Expected: 3 4 5 6");
    }

    private static void printResults(List<Integer> roots) {
        System.out.print(roots.get(0));
        for (int i = 1; i < roots.size(); i++) {
            System.out.print(" " + roots.get(i));
        }
        System.out.println();
    }
}
