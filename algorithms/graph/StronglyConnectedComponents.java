package algorithms.graph;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 01/09/17.
 */
@SuppressWarnings("unchecked")
public class StronglyConnectedComponents {
    private final int[] sccId;
    private int sccCount;
    private final int[] componentSizes;

    public boolean stronglyConnected(int vertex1, int vertex2) {
        return sccId[vertex1] == sccId[vertex2];
    }

    public int sccId(int vertex) {
        return sccId[vertex];
    }

    public int count() {
        return sccCount;
    }

    public StronglyConnectedComponents(List<Integer>[] adjacent) {
        boolean[] visited = new boolean[adjacent.length];
        sccId = new int[adjacent.length];
        componentSizes = new int[adjacent.length];

        List<Integer>[] inverseEdges = invertGraphEdges(adjacent);
        int[] decreasingFinishingTimes = computeDecreasingFinishingTimes(inverseEdges);

        for (int vertex : decreasingFinishingTimes) {
            if (!visited[vertex]) {
                depthFirstSearch(vertex, adjacent, null, visited, false);
                sccCount++;
            }
        }
    }

    private int[] computeDecreasingFinishingTimes(List<Integer>[] adjacent) {
        boolean[] visited = new boolean[adjacent.length];
        Stack<Integer> finishTimes = new Stack<>();

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                depthFirstSearch(i, adjacent, finishTimes, visited, true);
            }
        }

        int[] decreasingFinishingTimes = new int[finishTimes.size()];
        int arrayIndex = 0;
        while (!finishTimes.isEmpty()) {
            decreasingFinishingTimes[arrayIndex++] = finishTimes.pop();
        }
        return decreasingFinishingTimes;
    }

    // Fast, but recursive
    private void depthFirstSearch(int sourceVertex, List<Integer>[] adjacent, Stack<Integer> finishTimes,
                                  boolean[] visited, boolean getFinishTimes) {
        visited[sourceVertex] = true;

        if (!getFinishTimes) {
            sccId[sourceVertex] = sccCount;
            componentSizes[sccCount]++;
        }

        if (adjacent[sourceVertex] != null) {
            for (int neighbor : adjacent[sourceVertex]) {
                if (!visited[neighbor]) {
                    depthFirstSearch(neighbor, adjacent, finishTimes, visited, getFinishTimes);
                }
            }
        }

        if (getFinishTimes) {
            finishTimes.push(sourceVertex);
        }
    }

    // Trade-off between time and memory
    // Takes longer because it has to create the iterators, but avoid stack overflows
    private void depthFirstSearchIterative(int sourceVertex, List<Integer>[] adjacent, Stack<Integer> finishTimes,
                                           boolean[] visited, boolean getFinishTimes) {
        Stack<Integer> stack = new Stack<>();
        stack.push(sourceVertex);
        visited[sourceVertex] = true;

        if (!getFinishTimes) {
            sccId[sourceVertex] = sccCount;
        }

        // Used to be able to iterate over each adjacency list, keeping track of which
        // vertex in each adjacency list needs to be explored next
        Iterator<Integer>[] adjacentIterators = (Iterator<Integer>[]) new Iterator[adjacent.length];
        for (int vertexId = 0; vertexId < adjacentIterators.length; vertexId++) {
            if (adjacent[vertexId] != null) {
                adjacentIterators[vertexId] = adjacent[vertexId].iterator();
            }
        }

        while (!stack.isEmpty()) {
            int currentVertex = stack.peek();

            if (adjacentIterators[currentVertex].hasNext()) {
                int neighbor = adjacentIterators[currentVertex].next();

                if (!visited[neighbor]) {
                    stack.push(neighbor);
                    visited[neighbor] = true;
                }
            } else {
                stack.pop();

                if (getFinishTimes) {
                    finishTimes.push(currentVertex);
                }
            }
        }
    }

    private List<Integer>[] invertGraphEdges(List<Integer>[] adjacent) {
        List<Integer>[] inverseEdges = new ArrayList[adjacent.length];
        for (int i = 0; i < inverseEdges.length; i++) {
            inverseEdges[i] = new ArrayList<>();
        }

        for (int i = 0; i < adjacent.length; i++) {
            List<Integer> neighbors = adjacent[i];

            if (neighbors != null) {
                for (int neighbor : adjacent[i]) {
                    inverseEdges[neighbor].add(i);
                }
            }
        }
        return inverseEdges;
    }

    private List<Integer>[] getSCCsInDecreasingFinishingOrder() {
        List<Integer>[] stronglyConnectedComponents = (List<Integer>[]) new ArrayList[sccCount];
        for (int scc = 0; scc < stronglyConnectedComponents.length; scc++) {
            stronglyConnectedComponents[scc] = new ArrayList<>();
        }

        for (int vertex = 0; vertex < sccId.length; vertex++) {
            int stronglyConnectedComponentId = sccId(vertex);
            stronglyConnectedComponents[stronglyConnectedComponentId].add(vertex);
        }
        return stronglyConnectedComponents;
    }

    public List<Integer>[] getSCCsInFinishingOrder() {
        List<Integer>[] sccsInDecreasingFinishingOrder = getSCCsInDecreasingFinishingOrder();
        List<Integer>[] sccsInFinishingOrder = (List<Integer>[]) new ArrayList[sccCount];
        int currentSCCInFinishingOrderIndex = 0;

        for (int scc = sccsInDecreasingFinishingOrder.length - 1; scc >= 0; scc--) {
            sccsInFinishingOrder[currentSCCInFinishingOrderIndex++] = sccsInDecreasingFinishingOrder[scc];
        }
        return sccsInFinishingOrder;
    }

    // Generate the kernel DAG, the condensation graph where each SCC is a vertex
    private Set<Integer>[] getKernelDAG(List<Integer>[] adjacent) {
        Set<Integer>[] adjacentComponents = (HashSet<Integer>[]) new HashSet[sccCount];
        for (int i = 0; i < adjacentComponents.length; i++) {
            adjacentComponents[i] = new HashSet<>();
        }

        for (int vertexId = 0; vertexId < adjacent.length; vertexId++) {
            int currentComponent = sccId[vertexId];

            for (int neighbor : adjacent[vertexId]) {
                if (currentComponent != sccId[neighbor]) {
                    adjacentComponents[currentComponent].add(sccId[neighbor]);
                }
            }
        }
        return adjacentComponents;
    }

    // Test code
    public static void main(String[] args) throws IOException {
        FastReader.init();
        int vertices = FastReader.nextInt();
        int edges = FastReader.nextInt();

        List<Integer>[] adjacent = (List<Integer>[]) new ArrayList[vertices];
        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        for (int i = 0; i < edges; i++) {
            int vertex1 = FastReader.nextInt();
            int vertex2 = FastReader.nextInt();
            adjacent[vertex1].add(vertex2);
        }
        StronglyConnectedComponents stronglyConnectedComponents = new StronglyConnectedComponents(adjacent);
        System.out.println(stronglyConnectedComponents.sccCount + " components\n");
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init() {
            reader = new BufferedReader(new InputStreamReader(System.in));
            tokenizer = new StringTokenizer("");
        }

        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens()) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }
    }
}
