package algorithms.graph.minimum.spanning.tree;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by Rene Argento on 02/07/17.
 */
@SuppressWarnings("unchecked")
public class PrimMinimumSpanningTree {

    // Graph
    private static class Vertex {
        int id;
        boolean processed;

        int heapKey;
        boolean isInHeap;

        Vertex(int id) {
            this.id = id;
            heapKey = Integer.MAX_VALUE;
            processed = false;
            isInHeap = false;
        }
    }

    private static class Edge {
        int vertex1;
        int vertex2;
        int cost;

        Edge(int vertex1, int vertex2, int cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }
    }

    // O(E log(V)) <-- Overall complexity
    // We are considering that the graph is connected
    private static List<Edge> getMinimumSpanningTree(List<Edge>[] adjacent, Vertex[] vertices, int sourceVertex) {
        List<Edge> edgesInSpanningTree = new ArrayList<>();

        // Add vertex to start (any vertex works)
        Vertex firstVertexInserted = vertices[sourceVertex];
        int verticesSpannedSoFar = 1;
        firstVertexInserted.processed = true;

        PriorityQueue<Vertex> heap = new PriorityQueue<>(new Comparator<Vertex>() {
            @Override
            public int compare(Vertex node1, Vertex node2) {
                if (node1.heapKey < 0 && node2.heapKey > 0) {
                    return -1;
                } else if (node2.heapKey < 0 && node1.heapKey > 0) {
                    return 1;
                } else {
                    return node1.heapKey - node2.heapKey;
                }
            }
        });

        // O(V)
        initHeap(heap, vertices, adjacent, firstVertexInserted);

        // O(V)
        while (verticesSpannedSoFar != adjacent.length - 1) {
            // O(log V)
            Vertex vertexWithCheapestEdge = heap.poll();

            int cheapestEdgeCost = Integer.MAX_VALUE;
            Edge cheapestEdge = null;

            for (Edge edge : adjacent[vertexWithCheapestEdge.id]) {
                if ((vertices[edge.vertex1].processed && !vertices[edge.vertex2].processed)
                        || (vertices[edge.vertex2].processed && !vertices[edge.vertex1].processed)) {

                    if (edge.cost < cheapestEdgeCost) {
                        cheapestEdgeCost = edge.cost;
                        cheapestEdge = edge;
                    }
                }
            }

            edgesInSpanningTree.add(cheapestEdge);
            verticesSpannedSoFar++;
            vertexWithCheapestEdge.processed = true;

            // Total number of executions will be O(E log(V))
            updateHeapKeys(heap, vertices, adjacent, vertexWithCheapestEdge);
        }
        return edgesInSpanningTree;
    }

    // Add vertices to initialize heap - O(V)
    private static void initHeap(PriorityQueue<Vertex> heap, Vertex[] vertices, List<Edge>[] adjacent, Vertex firstVertexInserted) {
        for (Edge edge : adjacent[firstVertexInserted.id]) {
            if (edge.vertex1 == firstVertexInserted.id) {
                vertices[edge.vertex2].heapKey = Math.min(edge.cost, vertices[edge.vertex2].heapKey);
            } else if (edge.vertex2 == firstVertexInserted.id) {
                vertices[edge.vertex1].heapKey = Math.min(edge.cost, vertices[edge.vertex1].heapKey);
            }
        }

        for (Vertex vertex : vertices) {
            if (vertex != null && !vertex.isInHeap && vertex != firstVertexInserted) {
                heap.add(vertex);
                vertex.isInHeap = true;
            }
        }
    }

    // O(degree(vertex) lg V) -> Counting all executions we have O(E lg V)
    private static void updateHeapKeys(PriorityQueue<Vertex> heap, Vertex[] vertices, List<Edge>[] adjacent,
                                       Vertex vertex) {
        for (Edge edge : adjacent[vertex.id]) {
            Vertex vertex1 = vertices[edge.vertex1];
            Vertex vertex2 = vertices[edge.vertex2];

            if (vertex1.processed && !vertex2.processed) {
                if (edge.cost < vertex2.heapKey) {
                    heap.remove(vertex2);

                    vertex2.heapKey = edge.cost;
                    heap.add(vertex2);
                }
            } else if (!vertex1.processed && vertex2.processed) {
                if (edge.cost < vertex1.heapKey) {
                    heap.remove(vertex1);

                    vertex1.heapKey = edge.cost;
                    heap.add(vertex1);
                }
            }
        }
    }

    private static long getCostOfMinimumSpanningTree(List<Edge> edgesInSpanningTree) {
        long costOfMinimumSpanningTree = 0;

        for (Edge edge : edgesInSpanningTree) {
            costOfMinimumSpanningTree += edge.cost;
        }
        return costOfMinimumSpanningTree;
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int totalVertices = FastReader.nextInt();
        int totalEdges = FastReader.nextInt();

        Vertex[] vertices = new Vertex[totalVertices + 1];
        List<Edge>[] adjacent = (List<Edge>[]) new ArrayList[totalVertices + 1];

        for (int i = 0; i < totalEdges; i++) {
            int vertex1Id = FastReader.nextInt();
            int vertex2Id = FastReader.nextInt();
            int cost = FastReader.nextInt();

            // Add vertices
            Vertex vertex1 = new Vertex(vertex1Id);
            vertices[vertex1Id] = vertex1;
            Vertex vertex2 = new Vertex(vertex2Id);
            vertices[vertex2Id] = vertex2;

            //Optimization: If multiple edges may exist, only add a new edge if its cost is smaller than the current edge
//            boolean addEdge = true;
//            if (adjacent[vertex1Id] != null) {
//                for(Edge edge : adjacent[vertex1Id]) {
//                    if (edge.vertex1 == vertex2Id || edge.vertex2 == vertex2Id) {
//                        if (edge.cost <= cost) {
//                            addEdge = false;
//                            break;
//                        }
//                    }
//                }
//                if (!addEdge) {
//                    continue;
//                }
//            }

            // Add edge
            Edge edge = new Edge(vertex1Id, vertex2Id, cost);
            if (adjacent[vertex1Id] == null) {
                adjacent[vertex1Id] = new ArrayList<>();
            }
            if (adjacent[vertex2Id] == null) {
                adjacent[vertex2Id] = new ArrayList<>();
            }

            adjacent[vertex1Id].add(edge);
            adjacent[vertex2Id].add(edge); //undirected graph
        }

        List<Edge> edgesInSpanningTree = getMinimumSpanningTree(adjacent, vertices, 1);
        long minimumSpanningTreeCost = getCostOfMinimumSpanningTree(edgesInSpanningTree);
        System.out.println("Cost of the minimum spanning tree: " + minimumSpanningTreeCost);
    }

    private static class FastReader {
        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
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
