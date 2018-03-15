package com.br.google.code.jam.kickstart2017.roundf;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 24/09/17.
 */
public class CatchThemAll {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Google Code Jam/Kickstart 2017/Round F/Input - Output/";

    //    private static final String FILE_INPUT_PATH = PATH + "catchthemall_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "catchthemall_output.txt";
    private static final String FILE_INPUT_PATH = PATH + "catchthemall_small_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "catchthemall_small_output.txt";
//    private static final String FILE_INPUT_PATH = PATH + "catchthemall_large_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "catchthemall_large_output.txt";

    public static void main(String[] args) {
        test();
        //compete();
    }

    private static void compete() {
        List<String> input = readFileInput(FILE_INPUT_PATH);

        List<String> output = new ArrayList<>();

        int caseIndex = 1;
        for(int l = 0; l < input.size(); l++) {

            String[] values = input.get(l).split(" ");
            int vertices = Integer.parseInt(values[0]);
            int edgesCount = Integer.parseInt(values[1]);
            int codejamonToCatch = Integer.parseInt(values[2]);

            Edge[] edges = new Edge[edgesCount];
            for(int e = 0; e < edgesCount; e++) {
                l++;
                String[] edgeValues = input.get(l).split(" ");
                int vertexId1 = Integer.parseInt(values[0]);
                int vertexId2 = Integer.parseInt(values[1]);
                int length = Integer.parseInt(values[2]);

                edges[e] = new Edge(vertexId1, vertexId2, length);
            }

            output.add("Case #" + caseIndex + ": " + getTime(edges, vertices, codejamonToCatch));
            caseIndex++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void test() {
        int vertices1 = 5;
        int codejamonToCatch1 = 1;
        Edge[] edges1 = new Edge[4];
        edges1[0] = new Edge(1, 2, 1);
        edges1[1] = new Edge(2, 3, 2);
        edges1[2] = new Edge(1, 4, 2);
        edges1[3] = new Edge(4, 5, 1);

        int vertices2 = 2;
        int codejamonToCatch2 = 200;
        Edge[] edges2 = new Edge[1];
        edges2[0] = new Edge(1, 2, 5);

        int vertices3 = 5;
        int codejamonToCatch3 = 2;
        Edge[] edges3 = new Edge[4];
        edges3[0] = new Edge(1, 2, 1);
        edges3[1] = new Edge(2, 3, 2);
        edges3[2] = new Edge(1, 4, 2);
        edges3[3] = new Edge(4, 5, 1);

        int vertices4 = 3;
        int codejamonToCatch4 = 1;
        Edge[] edges4 = new Edge[3];
        edges4[0] = new Edge(1, 2, 3);
        edges4[1] = new Edge(1, 3, 1);
        edges4[2] = new Edge(2, 3, 1);

        System.out.println(getTime(edges1, vertices1, codejamonToCatch1) + " Expected: 2.250000");
        System.out.println(getTime(edges2, vertices2, codejamonToCatch2) + " Expected: 1000.000000");
        System.out.println(getTime(edges3, vertices3, codejamonToCatch3) + " Expected: 5.437500");
        System.out.println(getTime(edges4, vertices4, codejamonToCatch4) + " Expected: 1.500000");
    }

    private static class FloydWarshall {

        // Graph represented by an adjacency matrix
        private static double[][] graph;

        private static boolean negativeCycle;

        public FloydWarshall(int size) {
            graph = new double[size][size];
            initGraph();
        }

        private static void initGraph() {
            for (int i = 0; i < graph.length; i++) {
                for (int j = 0; j < graph.length; j++) {
                    if (i == j) {
                        graph[i][j] = 0;
                    } else {
                        graph[i][j] = Double.POSITIVE_INFINITY;
                    }
                }
            }
        }

        public static boolean hasNegativeCycle() {
            return negativeCycle;
        }

        public static void addEdge(int from, int to, double weight) {
            graph[from][to] = weight;
        }

        // All-pairs shortest path
        // Considering vertices 1..n -> if they are 0..n change k, i and j starting index to 0
        public static double[][] floydWarshall() {
            double[][] distances = graph;

            for (int k = 1; k < graph.length; k++) {
                for (int i = 1; i < graph.length; i++) {
                    for (int j = 1; j < graph.length; j++) {
                        distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
                    }
                }

                if (distances[k][k] < 0.0) {
                    negativeCycle = true;
                }
            }

            return distances;
        }
    }

    private static class Edge {
        int vertexId1;
        int vertexId2;
        int length;

        Edge(int vertexId1, int vertexId2, int length) {
            this.vertexId1 = vertexId1;
            this.vertexId2 = vertexId2;
            this.length = length;
        }
    }

    private static String getTime(Edge[] edges, int verticesCount, int codejamonToCatch) {
        double time = 0;

        FloydWarshall floydWarshall = new FloydWarshall(verticesCount + 1);

        for(int e = 0; e < edges.length; e++) {
            FloydWarshall.addEdge(edges[e].vertexId1, edges[e].vertexId2, edges[e].length);
            FloydWarshall.addEdge(edges[e].vertexId2, edges[e].vertexId1, edges[e].length);
        }
        FloydWarshall.floydWarshall();

        double[][] distances = FloydWarshall.graph;

        double[] distanceFromVertexToAllOthers = new double[verticesCount + 1];

        for(int i = 1; i <= verticesCount; i++) {
            for(int otherVertex = 1; otherVertex <= verticesCount; otherVertex++) {
                distanceFromVertexToAllOthers[i] += distances[i][otherVertex];
            }
        }

        //We always start from vertex 1
        time += distanceFromVertexToAllOthers[1] / (verticesCount - 1);

        double distanceFromAllToAll = 0;
        for(int i = 1; i <= verticesCount; i++) {
            distanceFromAllToAll += distanceFromVertexToAllOthers[i];
        }

        // Divide by 2 because we counted distance from u to v and from v to u
        //distanceFromAllToAll /= verticesCount;

        if (codejamonToCatch > 1) {
            double distanceToAllMinus1AsSource = distanceFromAllToAll - distanceFromVertexToAllOthers[1];
            time += distanceToAllMinus1AsSource / (verticesCount - 1);
        }

        double otherTime = 0;
        if (codejamonToCatch > 2) {
            otherTime += distanceFromAllToAll * (codejamonToCatch - 1);
        }

        double possibilities = Math.pow(verticesCount - 1, codejamonToCatch);
        otherTime /= possibilities;

        time += otherTime;

        return String.format("%.6f", time);
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> valuesList = new ArrayList<>();

        try {
            List<String> lines = Files.readAllLines(path);

            for (int i = 1; i < lines.size(); i++) {
                valuesList.add(lines.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return valuesList;
    }

    private static void writeDataOnFile(String file, List<String> data){
        for(String line : data) {
            writeFileOutput(file, line + "\n");
        }
    }

    private static void writeFileOutput(String file, String data){
        byte[] dataBytes = data.getBytes();

        try {
            Files.write(Paths.get(file), dataBytes, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
