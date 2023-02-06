package algorithms.graph.cycle.johnson.all.cycles;

import datastructures.graph.DirectedEdge;
import datastructures.graph.EdgeWeightedDigraph;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 30/12/17.
 */
public class KosarajuSharirSCCWeighted {

    private boolean[] visited; // reached vertices
    private int[] id;          // component identifiers
    private int count;         // number of strong components

    private EdgeWeightedDigraph edgeWeightedDigraph;

    public KosarajuSharirSCCWeighted(EdgeWeightedDigraph edgeWeightedDigraph) {
        visited = new boolean[edgeWeightedDigraph.vertices()];
        id = new int[edgeWeightedDigraph.vertices()];
        this.edgeWeightedDigraph = edgeWeightedDigraph;

        DepthFirstOrderDirectedWeighted depthFirstOrder =
                new DepthFirstOrderDirectedWeighted(edgeWeightedDigraph.reverse());

        for (int vertex : depthFirstOrder.reversePostOrder()) {
            if (!visited[vertex]) {
                dfs(edgeWeightedDigraph, vertex);
                count++;
            }
        }
    }

    private void dfs(EdgeWeightedDigraph edgeWeightedDigraph, int vertex) {
        visited[vertex] = true;
        id[vertex] = count;

        for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
            int neighbor = edge.to();

            if (!visited[neighbor]) {
                dfs(edgeWeightedDigraph, neighbor);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public List<Integer>[] getSCCs() {
        List<Integer>[] stronglyConnectedComponents = (List<Integer>[]) new ArrayList[count];

        for (int scc = 0; scc < count; scc++) {
            stronglyConnectedComponents[scc] = new ArrayList<>();
        }

        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            stronglyConnectedComponents[id(vertex)].add(vertex);
        }
        return stronglyConnectedComponents;
    }

    public EdgeWeightedDigraph getKernelDAG() {
        EdgeWeightedDigraph kernelDAG = new EdgeWeightedDigraph(count());

        for (int vertex = 0; vertex < edgeWeightedDigraph.vertices(); vertex++) {
            for (DirectedEdge edge : edgeWeightedDigraph.adjacent(vertex)) {
                int neighbor = edge.to();

                if (id(vertex) != id(neighbor)) {
                    kernelDAG.addEdge(new DirectedEdge(id(vertex), id(neighbor), edge.weight()));
                }
            }
        }
        return kernelDAG;
    }

    public boolean stronglyConnected(int vertex1, int vertex2) {
        return id[vertex1] == id[vertex2];
    }

    public int id(int vertex) {
        return id[vertex];
    }

    public int count() {
        return count;
    }

}
