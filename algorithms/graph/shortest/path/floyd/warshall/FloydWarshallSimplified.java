package algorithms.graph.shortest.path.floyd.warshall;

/**
 * Created by Rene Argento on 15/03/24.
 */
// Floyd Warshall algorithm simplified for reusability on contests.
// O(V^3)
public class FloydWarshallSimplified {

    private static void floydWarshall(int[][] distances) {
        for (int vertex1 = 0; vertex1 < distances.length; vertex1++) {
            for (int vertex2 = 0; vertex2 < distances.length; vertex2++) {
                for (int vertex3 = 0; vertex3 < distances.length; vertex3++) {
                    if (distances[vertex2][vertex3] > distances[vertex2][vertex1] + distances[vertex1][vertex3]) {
                        distances[vertex2][vertex3] = distances[vertex2][vertex1] + distances[vertex1][vertex3];
                    }
                }
            }
        }
    }
}
