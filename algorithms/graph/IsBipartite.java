package algorithms.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 15/09/17.
 */
@SuppressWarnings("unchecked")
public class IsBipartite {

    private static boolean isBipartite(List<Integer>[] graph) {
        Boolean[] colors = new Boolean[graph.length];

        for (int vertexID = 0; vertexID < graph.length; vertexID++) {
            if (colors[vertexID] == null) {
                colors[vertexID] = true;
                boolean isBipartite = isBipartite(graph, colors, vertexID, true);
                if (!isBipartite) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isBipartite(List<Integer>[] graph, Boolean[] colors, int vertexID, boolean color) {
        for (int neighborVertexID : graph[vertexID]) {
            if (colors[neighborVertexID] == null) {
                colors[neighborVertexID] = !color;
                boolean isBipartite = isBipartite(graph, colors, neighborVertexID, !color);
                if (!isBipartite) {
                    return false;
                }
            } else if (colors[neighborVertexID] == color) {
                return false;
            }
        }
        return true;
    }

    //  Graph 1          Graph 2
    //  0 - 1 - 2        0 - 1 - 3
    //  |   |             \ /    |
    //  3 - 4              2     4
    public static void main(String[] args) {
        List<Integer>[] graph1 = createGraph(5);
        graph1[0].add(1);
        graph1[0].add(3);
        graph1[1].add(0);
        graph1[1].add(2);
        graph1[2].add(1);
        graph1[3].add(0);
        graph1[3].add(4);
        graph1[4].add(1);
        graph1[4].add(3);
        boolean isBipartite1 = isBipartite(graph1);
        System.out.println("Is Bipartite: " + isBipartite1 + " Expected: true");

        List<Integer>[] graph2 = createGraph(5);
        graph2[0].add(1);
        graph2[0].add(2);
        graph2[1].add(0);
        graph2[1].add(2);
        graph2[1].add(3);
        graph2[2].add(0);
        graph2[2].add(1);
        graph2[3].add(1);
        graph2[3].add(4);
        graph2[4].add(3);
        boolean isBipartite2 = isBipartite(graph2);
        System.out.println("Is Bipartite: " + isBipartite2 + " Expected: false");
    }

    private static List<Integer>[] createGraph(int size) {
        List<Integer>[] graph = new List[size];
        for (int vertexID = 0; vertexID < graph.length; vertexID++) {
            graph[vertexID] = new ArrayList<>();
        }
        return graph;
    }
}
