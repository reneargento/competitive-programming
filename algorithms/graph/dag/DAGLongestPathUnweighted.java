package algorithms.graph.dag;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Created by Rene Argento on 12/09/17.
 */
public class DAGLongestPathUnweighted {

    private static int computeLongestPathLength(List<Integer>[] adjacencyList, int[] inDegrees) {
        int longestPathLength = 0;
        int[] pathLengths = new int[adjacencyList.length];

        Queue<Integer> queue = new LinkedList<>();
        for (int nodeID = 0; nodeID < adjacencyList.length; nodeID++) {
            if (inDegrees[nodeID] == 0) {
                queue.add(nodeID);
                pathLengths[nodeID] = 1;
                longestPathLength = 1;
            }
        }

        while (!queue.isEmpty()) {
            int currentVertex = queue.poll();

            for (int neighborID : adjacencyList[currentVertex]) {
                inDegrees[neighborID]--;

                if (inDegrees[neighborID] == 0) {
                    int newPathLength = pathLengths[currentVertex] + 1;
                    pathLengths[neighborID] = newPathLength;
                    longestPathLength = Math.max(longestPathLength, newPathLength);

                    queue.add(neighborID);
                }
            }
        }
        return longestPathLength;
    }
}
