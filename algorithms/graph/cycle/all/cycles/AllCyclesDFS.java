package algorithms.graph.cycle.all.cycles;

import java.util.*;

/**
 * Created by Rene Argento on 04/04/23.
 */
// Runtime: O((E + V) * (C + 1), where C is the number of cycles in the graph.
// The number of cycles in a graph can be exponential.
// Space complexity: O(E + V + S) where S is the sum of the number of vertices in all cycles.
// Since runtime may be exponential, this algorithm is best used in small graphs (V <= 100)
@SuppressWarnings("unchecked")
public class AllCyclesDFS {

    private static class Cycle implements Comparable<Cycle> {
        List<Integer> cycle;
        String representation;

        public Cycle(List<Integer> cycle) {
            this.cycle = cycle;
            computeRepresentation();
        }

        void computeRepresentation() {
            int lowestValue = Integer.MAX_VALUE;
            int lowestValueIndex = 0;
            for (int i = 0; i < cycle.size(); i++) {
                if (cycle.get(i) < lowestValue) {
                    lowestValue = cycle.get(i);
                    lowestValueIndex = i;
                }
            }

            StringBuilder cycleValues = new StringBuilder();
            cycleValues.append(cycle.get(lowestValueIndex));
            for (int i = lowestValueIndex + 1; i < cycle.size(); i++) {
                cycleValues.append("->").append(cycle.get(i));
            }
            for (int i = 0; i <= lowestValueIndex; i++) {
                cycleValues.append("->").append(cycle.get(i));
            }
            representation = cycleValues.toString();
        }

        @Override
        public int compareTo(Cycle other) {
            return representation.compareTo(other.representation);
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }
            Cycle cycle = (Cycle) other;
            return Objects.equals(representation, cycle.representation);
        }

        @Override
        public int hashCode() {
            return Objects.hash(representation);
        }
    }

    private final boolean[] visited;
    private final int[] edgeTo;
    private final boolean[] onStack;
    public List<Cycle> allCyclesByVertices;

    public AllCyclesDFS(List<Integer>[] adjacent) {
        onStack = new boolean[adjacent.length];
        edgeTo = new int[adjacent.length];
        visited = new boolean[adjacent.length];
        Set<Cycle> cyclesSet = new HashSet<>();

        for (int vertexID = 0; vertexID < adjacent.length; vertexID++) {
            if (!visited[vertexID]) {
                dfs(adjacent, cyclesSet, vertexID);
            }
        }
        allCyclesByVertices = new ArrayList<>(cyclesSet);
        Collections.sort(allCyclesByVertices);
    }

    private void dfs(List<Integer>[] adjacent, Set<Cycle> cyclesSet, int vertex) {
        onStack[vertex] = true;
        visited[vertex] = true;

        for (int neighbor : adjacent[vertex]) {
            if (!onStack[neighbor]) {
                edgeTo[neighbor] = vertex;
                dfs(adjacent, cyclesSet, neighbor);
            } else {
                // Cycle found
                List<Integer> cycle = new ArrayList<>();
                for (int currentVertex = vertex; currentVertex != neighbor; currentVertex = edgeTo[currentVertex]) {
                    cycle.add(currentVertex);
                }
                cycle.add(neighbor);
                Collections.reverse(cycle);
                cyclesSet.add(new Cycle(cycle));
            }
        }
        onStack[vertex] = false;
    }

    public static void main(String[] args) {
        List<Integer>[] adjacencyLIst = new ArrayList[9];
        for (int i = 0; i < adjacencyLIst.length; i++) {
            adjacencyLIst[i] = new ArrayList<>();
        }
        adjacencyLIst[0].add(1);
        adjacencyLIst[0].add(7);
        adjacencyLIst[0].add(4);
        adjacencyLIst[1].add(8);
        adjacencyLIst[1].add(6);
        adjacencyLIst[1].add(2);
        adjacencyLIst[2].add(0);
        adjacencyLIst[2].add(1);
        adjacencyLIst[2].add(5);
        adjacencyLIst[2].add(3);
        adjacencyLIst[5].add(3);
        adjacencyLIst[3].add(4);
        adjacencyLIst[4].add(1);
        adjacencyLIst[7].add(8);
        adjacencyLIst[8].add(7);

        AllCyclesDFS cycles = new AllCyclesDFS(adjacencyLIst);
        List<Cycle> allCycles = cycles.allCyclesByVertices;

        System.out.println("All cycles");
        for (Cycle cycle : allCycles) {
            System.out.println(cycle.representation);
        }

        System.out.println("\nExpected:");
        System.out.println("0->1->2->0\n" +
                "0->4->1->2->0\n" +
                "1->2->1\n" +
                "1->2->3->4->1\n" +
                "1->2->5->3->4->1\n" +
                "7->8->7");
    }
}
