package algorithms.graph.strongly.connected.components;

import java.util.*;

/**
 * Created by Rene Argento on 22/04/23.
 */
@SuppressWarnings("unchecked")
public class StronglyConnectedComponentsTarjan {
    private final int[] sccIds;
    private final int[] componentSizes;
    private final int[] dfsLow;
    private final int[] dfsNumber;
    private final boolean[] visited;
    private final Deque<Integer> stack;
    private int sccCount;
    private int dfsNumberCounter;
    private static final int UNVISITED = -1;

    public StronglyConnectedComponentsTarjan(List<Integer>[] adjacencyList) {
        sccIds = new int[adjacencyList.length];
        componentSizes = new int[adjacencyList.length];
        visited = new boolean[adjacencyList.length];
        dfsLow = new int[adjacencyList.length];
        dfsNumber = new int[adjacencyList.length];
        stack = new ArrayDeque<>();
        Arrays.fill(dfsNumber, UNVISITED);

        for (int vertexID = 0; vertexID < adjacencyList.length; vertexID++) {
            if (dfsNumber[vertexID] == UNVISITED) {
                depthFirstSearch(adjacencyList, vertexID);
            }
        }
    }

    private void depthFirstSearch(List<Integer>[] adjacencyList, int vertexID) {
        visited[vertexID] = true;
        dfsLow[vertexID] = dfsNumberCounter;
        dfsNumber[vertexID] = dfsNumberCounter;
        dfsNumberCounter++;
        stack.push(vertexID);

        for (int neighborID: adjacencyList[vertexID]) {
            if (dfsNumber[neighborID] == UNVISITED) {
                depthFirstSearch(adjacencyList, neighborID);
            }
            if (visited[neighborID]) {
                dfsLow[vertexID] = Math.min(dfsLow[vertexID], dfsLow[neighborID]);
            }
        }

        if (dfsLow[vertexID] == dfsNumber[vertexID]) {
            while (true) {
                int vertexInStack = stack.pop();
                visited[vertexInStack] = false;
                sccIds[vertexInStack] = sccCount;
                componentSizes[sccCount]++;

                if (vertexInStack == vertexID) {
                    break;
                }
            }
            sccCount++;
        }
    }

    private List<Integer>[] getSCCs() {
        List<Integer>[] stronglyConnectedComponents = (List<Integer>[]) new ArrayList[sccCount];
        for (int scc = 0; scc < stronglyConnectedComponents.length; scc++) {
            stronglyConnectedComponents[scc] = new ArrayList<>();
        }

        for (int vertex = 0; vertex < sccIds.length; vertex++) {
            int stronglyConnectedComponentId = sccIds[vertex];
            stronglyConnectedComponents[stronglyConnectedComponentId].add(vertex);
        }
        return stronglyConnectedComponents;
    }
}
