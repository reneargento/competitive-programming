package com.br.facebook.hacker.cup.year2017.round1;

/**
 * Created by rene.argento on 16/01/17.
 */

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by rene on 14/01/17.
 */

//Based on https://www.facebook.com/hackercup/problem/300438463685411/
// And http://puru.hatenablog.com/entry/2017/01/17/000613 (Google translate)

/**
 * Manic Moving
 *
 * Thanks to his tireless hard work, Wilson has been promoted and now gets to drive his moving company's trucks!
 * No, he can't believe it either.

 The moving company services a region that has N towns, with M roads running amongst them.
 The ith road connects two different towns Ai and Bi, requires Gi litres of gas to drive along,
 and can be traversed in either direction.
 There may be multiple roads running directly between any given pair of towns.

 Today, Wilson has been scheduled to transport K families' belongings.
 The ith family is moving from town Si to a different town Di.
 Wilson and his truck will be starting off the day at the company headquarters in town 1.
 For each family, he'll need to drive to their starting town by following a sequence of roads,
 load his truck there, and at some point later, arrive at their destination town to unload their belongings.
 His truck is large enough to fit at most 2 families' sets of belongings at a time,
 meaning that he doesn't necessarily need to deliver each load immediately after picking it up.

 However, Wilson has been instructed that the K families must be helped strictly in order.
 In particular, if i < j, then the ith family's belongings must be loaded before the jth family's belongings are loaded,
 and the ith family's belongings must be delivered before the jth family's belongings are delivered.

 Although Wilson's wages are higher than ever, he does have to pay for the truck's gas out of his own pocket,
 so it's in his best interest to get the job done while burning through as little of it as possible.
 Of course, he'll still need to be careful to follow his company's strict rules regarding the relative order
 of the families' loads and unloads, to avoid getting fired.
 That being said, it's a possibility for it to be impossible to even complete all of the requested moves,
 in which case Wilson will simply call it a day and stay home instead.

 Input
 Input begins with an integer T, the number of sets of families Wilson needs to move.

 For each case, there is first a line containing three space-separated integers, N, M, and K.

 Then, M lines follow, the ith of which contains 3 space-separated integers, Ai, Bi, and Gi.

 Then, K lines follow, the ith of which contains 2 space-separated integers, Si and Di.

 Output
 For the ith case, print a line containing "Case #i: " followed by the minimum amount of gas required for Wilson
 to validly complete his delivery schedule, or -1 if it can't be done.

 Constraints
 1 ≤ T ≤ 100
 2 ≤ N ≤ 100
 1 ≤ M ≤ 5,000
 1 ≤ K ≤ 5,000
 1 ≤ Ai, Bi ≤ N, Ai ≠ Bi
 1 ≤ Si, Di ≤ N, Si ≠ Di
 1 ≤ Gi ≤ 1,000

 Example input
 5
 3 2 3
 1 2 4
 2 3 7
 2 1
 3 2
 3 2
 3 2 3
 1 2 4
 2 3 7
 3 2
 2 1
 3 2
 4 4 4
 1 2 3
 1 3 1
 2 4 10
 3 4 1
 1 2
 2 4
 4 1
 3 1
 4 2 4
 1 2 3
 1 3 1
 1 2
 2 4
 4 1
 3 1
 7 8 10
 7 5 9
 1 2 14
 3 7 7
 2 5 8
 4 3 10
 1 4 3
 7 3 5
 4 2 16
 3 2
 5 1
 7 3
 5 3
 1 7
 3 2
 1 5
 4 7
 7 2
 5 1

 Example output
 Case #1: 26
 Case #2: 40
 Case #3: 10
 Case #4: -1
 Case #5: 219

 Explanation of Sample
 In the first case, Wilson drives to town 2, and then drives the first family's belongings back to town 1.
 That's 8 litres gas so far.
 Then Wilson drives to city 3 (11 more litres of gas), picks up the remaining belongings, and drives them all
 to town 2 (7 litres of gas). A grand total of 8 + 11 + 7 = 26 litres of gas.

 In the fourth case, Wilson can't reach town 4 in order to complete the 2nd and 3rd families' moves.
 */

public class ManicMoving {

    private class Graph {

        class Vertex {
            int id;
            List<Edge> edgesAssociated;

            boolean processed;
            Vertex previous;
            long distance;
        }

        class Edge {
            Vertex head;
            Vertex tail;
            long length;
        }

        Map<Integer, Vertex> vertices;
        List<Edge> edges;

        Graph() {
            vertices = new HashMap<>();
            edges = new ArrayList<>();
        }

        //O(1)
        void addVertex(int id) {
            Vertex vertex = new Vertex();
            vertex.id = id;
            vertex.edgesAssociated = new ArrayList<>();

            vertex.processed = false;

            vertices.put(id, vertex);
        }

        //O(m)
        void addEdge(int tailVertexId, int headVertexId, long length) {

            if (vertices.get(headVertexId) == null) {
                addVertex(headVertexId);
            }
            if (vertices.get(tailVertexId) == null) {
                addVertex(tailVertexId);
            }

            Vertex headVertex = vertices.get(headVertexId);
            Vertex tailVertex = vertices.get(tailVertexId);

            //Only add the path if it is shortest than the current path
            if (headVertex.edgesAssociated.size() < tailVertex.edgesAssociated.size()) {
                for(Edge edge : headVertex.edgesAssociated) {
                    if ((edge.tail.id == tailVertexId && edge.head.id == headVertexId)
                            || (edge.head.id == tailVertexId && edge.tail.id == headVertexId)) {
                        if (edge.length < length) {
                            return;
                        }
                    }
                }
            } else {
                for(Edge edge : tailVertex.edgesAssociated) {
                    if ((edge.tail.id == tailVertexId && edge.head.id == headVertexId)
                            || (edge.head.id == tailVertexId && edge.tail.id == headVertexId)) {
                        if (edge.length < length) {
                            return;
                        }
                    }
                }
            }

            Edge edge = new Edge();
            edge.head = headVertex;
            edge.tail = tailVertex;
            edge.length = length;

            headVertex.edgesAssociated.add(edge);
            tailVertex.edgesAssociated.add(edge);

            edges.add(edge);
        }

        //O(n)
        void resetAllProcessedVertices(){
            for(Integer vertexId : vertices.keySet()) {
                vertices.get(vertexId).processed = false;
            }
        }

        //O(m)
        void resetDistance() {
            for(Integer vertexId : vertices.keySet()) {
                vertices.get(vertexId).distance = Integer.MAX_VALUE;
            }
        }

        //O(n)
        void resetPreviousVertex() {
            for(int vertexId : vertices.keySet()) {
                vertices.get(vertexId).previous = null;
            }
        }

        //O(1)
        int getVerticesCount() {
            return vertices.size();
        }
    }

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2017/Round 1/Input - Output/";

    private static final String FILE_INPUT_PATH = PATH + "manic_moving_example_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "manic_moving_output.txt";

//    private static final String FILE_INPUT_PATH = PATH + "manicmoving.in";
//    private static final String FILE_OUTPUT_PATH = PATH + "manic_moving_output_submission.txt";

    public static void main(String[] args) {
        tests();

      //  List<String> output = readFileInputAndReturnOutput(FILE_INPUT_PATH);
       // writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private static void tests() {

        Graph graph1 = new ManicMoving().new Graph();
        graph1.addEdge(1, 2, 4);
        graph1.addEdge(2, 3, 7);

        int[][] instructions1 = new int[3][2];
        instructions1[0] = new int[] {2, 1};
        instructions1[1] = new int[] {3, 2};
        instructions1[2] = new int[] {3, 2};

        System.out.println(computeMinimumGas(graph1, instructions1) + " Expected: 26");

        Graph graph2 = new ManicMoving().new Graph();
        graph2.addEdge(1, 2, 4);
        graph2.addEdge(2, 3, 7);

        int[][] instructions2 = new int[3][2];
        instructions2[0] = new int[] {3, 2};
        instructions2[1] = new int[] {2, 1};
        instructions2[2] = new int[] {3, 2};

        System.out.println(computeMinimumGas(graph2, instructions2) + " Expected: 40");

        Graph graph3 = new ManicMoving().new Graph();
        graph3.addEdge(1, 2, 3);
        graph3.addEdge(1, 3, 1);
        graph3.addEdge(2, 4, 10);
        graph3.addEdge(3, 4, 1);

        int[][] instructions3 = new int[4][2];
        instructions3[0] = new int[] {1, 2};
        instructions3[1] = new int[] {2, 4};
        instructions3[2] = new int[] {4, 1};
        instructions3[3] = new int[] {3, 1};

        System.out.println(computeMinimumGas(graph3, instructions3) + " Expected: 10");

        Graph graph4 = new ManicMoving().new Graph();
        graph4.addEdge(1, 2, 3);
        graph4.addEdge(1, 3, 1);
        graph4.addEdge(4, 4, 0);

        int[][] instructions4 = new int[4][2];
        instructions4[0] = new int[] {1, 2};
        instructions4[1] = new int[] {2, 4};
        instructions4[2] = new int[] {4, 1};
        instructions4[3] = new int[] {3, 1};

        System.out.println(computeMinimumGas(graph4, instructions4) + " Expected: -1");

        Graph graph5 = new ManicMoving().new Graph();
        graph5.addEdge(7, 5, 9);
        graph5.addEdge(1, 2, 14);
        graph5.addEdge(3, 7, 7);
        graph5.addEdge(2, 5, 8);
        graph5.addEdge(4, 3, 10);
        graph5.addEdge(1, 4, 3);
        graph5.addEdge(7, 3, 5);
        graph5.addEdge(4, 2, 16);
        graph5.addEdge(6, 6, 0);

        int[][] instructions5 = new int[10][2];
        instructions5[0] = new int[] {3, 2};
        instructions5[1] = new int[] {5, 1};
        instructions5[2] = new int[] {7, 3};
        instructions5[3] = new int[] {5, 3};
        instructions5[4] = new int[] {1, 7};
        instructions5[5] = new int[] {3, 2};
        instructions5[6] = new int[] {1, 5};
        instructions5[7] = new int[] {4, 7};
        instructions5[8] = new int[] {7, 2};
        instructions5[9] = new int[] {5, 1};

        System.out.println(computeMinimumGas(graph5, instructions5) + " Expected: 219");
    }

    //O(F x C) - The truck pick ups and loads are constant
    private static long computeMinimumGas(Graph graph, int[][] instructions) {

        long[][] shortestPaths = computeAllPairsShortestPath(graph);

        int numberOfFamilies = instructions.length;

        for(int i = 0; i < instructions.length; i++) {
            if (shortestPaths[instructions[i][0]][instructions[i][1]] == Integer.MAX_VALUE) {
                return -1;
            }
        }

        int numberOfCities = graph.getVerticesCount();

        //dp(F, C, S) -> F: remaining number of families to pickup; C: current city; S: number of sets in the truck 0 <= H <= 2
        long[][][] dp = new long[numberOfFamilies + 1][numberOfCities + 1][3];

        for(int i = 0; i <= numberOfFamilies; i++) {
            for(int j = 1; j <= numberOfCities; j++) {
                for(int k = 0; k <= 2; k++) {
                    dp[i][j][k] = Integer.MAX_VALUE;
                }
            }
        }

        //Base case - No more family sets to pick up
        for(int i = 1; i <= numberOfCities; i++) {
            dp[0][i][0] = 0; //all sets delivered

            int lastCity = instructions[numberOfFamilies - 1][1];
            dp[0][i][1] = shortestPaths[i][lastCity];//last set to deliver

            if (numberOfFamilies > 1) {
                int beforeLastCity = instructions[numberOfFamilies - 2][1];
                dp[0][i][2] = shortestPaths[i][beforeLastCity] + shortestPaths[beforeLastCity][lastCity];//last 2 sets to deliver
            }
        }

        for(int family = 1; family <= numberOfFamilies; family++) {
            for(int load = 0; load <= 2; load++) {
                for(int city = 1; city <= numberOfCities; city++) {

                    int nextFamily = numberOfFamilies - family;
                    int nextFamilySourceCity = instructions[nextFamily][0];

                    if (load == 0) {
                        //H = 0
                        //If we have no belongings in the truck, we must pick up the ith family's belongings next
                        dp[family][city][0] = dp[family-1][nextFamilySourceCity][1] + shortestPaths[city][nextFamilySourceCity];
                    } else if (load == 1) {
                        //H = 1
                        //If we have 1 set of belongings in the truck, we can either pick up the next family's belongings,
                        // or drop off the current family's belongings
                        int nextFamilyToBeDropped = numberOfFamilies - family -  1;

                        if (nextFamilyToBeDropped < 0) {
                            continue;
                        }

                        int nextFamilyDestinationCity = instructions[nextFamilyToBeDropped][1];

                        dp[family][city][1] = Math.min(dp[family - 1][nextFamilySourceCity][2] + shortestPaths[city][nextFamilySourceCity],
                                dp[family][nextFamilyDestinationCity][0] + shortestPaths[city][nextFamilyDestinationCity]);
                    } else {
                        //H = 2
                        //If we have 2 sets of belongings in the truck, we must drop off the (family - 2)th family's belongings next

                        //If we still have F or F-1 family sets to pick up, it is impossible to have H = 2
                        if (family == numberOfFamilies || family == numberOfFamilies-1) {
                            continue;
                        }

                        int nextFamilyToBeDropped = numberOfFamilies - family - 2;
                        int nextFamilyDestinationCity = instructions[nextFamilyToBeDropped][1];

                        dp[family][city][2] = dp[family][nextFamilyDestinationCity][1] + shortestPaths[city][nextFamilyDestinationCity];
                    }
                }
            }
        }

        return dp[numberOfFamilies][1][0];
    }

    //O(n * m log(n))
    private static long[][] computeAllPairsShortestPath(Graph graph) {
        ManicMoving manicMoving = new ManicMoving();

        graph.resetDistance();

        long[][] dijkstraShortestDistances = new long[graph.getVerticesCount() + 1][];

        for(int i = 1; i <= graph.getVerticesCount(); i++) {
            dijkstraShortestDistances[i] = manicMoving.dijkstra(graph, i);

            graph.resetAllProcessedVertices();
            graph.resetDistance();
            graph.resetPreviousVertex();
        }

        return dijkstraShortestDistances;
    }

    //O(m log(n))
    private long[] dijkstra(Graph graph, int sourceVertexId) {

        long[] computedShortestPathDistances = new long[graph.getVerticesCount() + 1];

        //1- Init base case
        for(int i = 0; i < computedShortestPathDistances.length; i++) {
            computedShortestPathDistances[i] = Integer.MAX_VALUE;
        }

        computedShortestPathDistances[sourceVertexId] = 0;
        Graph.Vertex sourceVertex = graph.vertices.get(sourceVertexId);
        sourceVertex.distance = 0;

        //2- Create heap and compute distances

        @SuppressWarnings("unchecked")
        PriorityQueue<Graph.Vertex> heap = new PriorityQueue(20, new Comparator<Graph.Vertex>() {
            @Override
            public int compare(Graph.Vertex vertex1, Graph.Vertex vertex2) {
                return (int) (vertex1.distance - vertex2.distance);
            }
        });

        heap.offer(sourceVertex);

        while(heap.size() > 0) {
            //Get nearest vertex
            Graph.Vertex nearestVertex = heap.poll();

            nearestVertex.processed = true;

            //Recompute distances of the connected vertices
            for(Graph.Edge edge : nearestVertex.edgesAssociated) {

                Graph.Vertex vertexToProcess;

                if (!edge.head.processed) {
                    vertexToProcess = edge.head;
                } else {
                    vertexToProcess = edge.tail;
                }

                if (!vertexToProcess.processed) {

                    if (vertexToProcess.previous == null
                            || vertexToProcess.distance > nearestVertex.distance + edge.length) {
                        heap.remove(vertexToProcess);

                        vertexToProcess.previous = nearestVertex;
                        vertexToProcess.distance = nearestVertex.distance + edge.length;

                        computedShortestPathDistances[vertexToProcess.id] = vertexToProcess.distance;

                        heap.add(vertexToProcess);
                    }
                }
            }
        }

        return computedShortestPathDistances;
    }

    //Solution using Floyd-Warshall for all-pairs shortest paths

    //O(F x C) - The truck pick ups and loads are constant
//    private static long computeMinimumGas(Graph graph, int[][] instructions) {
//
//       // long[][] shortestPaths = computeAllPairsShortestPath(graph);
//        long[][][] shortestPaths = new long[graph.getVerticesCount()+1][graph.getVerticesCount()+1][2];
//        floydWarshall(graph, shortestPaths);
//
//        int numberOfFamilies = instructions.length;
//
//        for(int i = 0; i < instructions.length; i++) {
//            if (shortestPaths[instructions[i][0]][instructions[i][1]][0] == Integer.MAX_VALUE) {
//                return -1;
//            }
//        }
//
//        int numberOfCities = graph.getVerticesCount();
//
//        //dp(F, C, S) -> F: remaining number of families to pickup; C: current city; S: number of sets in the truck 0 <= H <= 2
//        long[][][] dp = new long[numberOfFamilies + 1][numberOfCities + 1][3];
//
//        for(int i = 0; i <= numberOfFamilies; i++) {
//            for(int j = 1; j <= numberOfCities; j++) {
//                for(int k = 0; k <= 2; k++) {
//                    dp[i][j][k] = Integer.MAX_VALUE;
//                }
//            }
//        }
//
//        //Base case - No more family sets to pick up
//        for(int i = 1; i <= numberOfCities; i++) {
//            dp[0][i][0] = 0; //all sets delivered
//
//            int lastCity = instructions[numberOfFamilies - 1][1];
//            dp[0][i][1] = shortestPaths[i][lastCity][0];//last set to deliver
//
//            if (numberOfFamilies > 1) {
//                int beforeLastCity = instructions[numberOfFamilies - 2][1];
//                dp[0][i][2] = shortestPaths[i][beforeLastCity][0] + shortestPaths[beforeLastCity][lastCity][0];//last 2 sets to deliver
//            }
//        }
//
//        for(int family=1; family <= numberOfFamilies; family++) {
//            for(int load = 0; load <= 2; load++) {
//                for(int city=1; city <= numberOfCities; city++) {
//
//                    int nextFamily = numberOfFamilies - family;
//                    int nextFamilySourceCity = instructions[nextFamily][0];
//
//                    if (load == 0) {
//                        //H = 0
//                        //If we have no belongings in the truck, we must pick up the ith family's belongings next
//                        dp[family][city][0] = dp[family-1][nextFamilySourceCity][1] + shortestPaths[city][nextFamilySourceCity][0];
//                    } else if (load == 1) {
//                        //H = 1
//                        //If we have 1 set of belongings in the truck, we can either pick up the next family's belongings,
//                        // or drop off the current family's belongings
//                        int nextFamilyToBeDropped = numberOfFamilies - family -  1;
//
//                        if (nextFamilyToBeDropped < 0) {
//                            continue;
//                        }
//
//                        int nextFamilyDestinationCity = instructions[nextFamilyToBeDropped][1];
//
//                        dp[family][city][1] = Math.min(dp[family - 1][nextFamilySourceCity][2] + shortestPaths[city][nextFamilySourceCity][0],
//                                dp[family][nextFamilyDestinationCity][0] + shortestPaths[city][nextFamilyDestinationCity][0]);
//                    } else {
//                        //H = 2
//                        //If we have 2 sets of belongings in the truck, we must drop off the (family - 2)th family's belongings next
//
//                        //If we still have F or F-1 family sets to pick up, it is impossible to have H = 2
//                        if (family == numberOfFamilies || family == numberOfFamilies-1) {
//                            continue;
//                        }
//
//                        int nextFamilyToBeDropped = numberOfFamilies - family - 2;
//                        int nextFamilyDestinationCity = instructions[nextFamilyToBeDropped][1];
//
//                        dp[family][city][2] = dp[family][nextFamilyDestinationCity][1] + shortestPaths[city][nextFamilyDestinationCity][0];
//                    }
//                }
//            }
//        }
//
//        return dp[numberOfFamilies][1][0];
//    }

    private static void floydWarshall(Graph graph, long[][][] distances) {
        initBaseCases(graph, distances);
        computeShortestPaths(distances);
    }

    //O(n^2)
    private static void initBaseCases(Graph graph, long[][][] distances) {

        // 0 if i = j
        // Cij if (i,j) e E
        // +Infinite if i != j && (i,j) !e E

        for(int i = 0; i < distances.length; i++) {
            for(int j = 0; j < distances[0].length; j++) {
                if (i != j) {
                    distances[i][j][0] = Integer.MAX_VALUE;
                }
            }
        }

        for(Graph.Edge edge : graph.edges) {
            distances[edge.tail.id][edge.head.id][0] = edge.length;
            distances[edge.head.id][edge.tail.id][0] = edge.length;
        }
    }

    //O(n^3)
    private static boolean computeShortestPaths(long[][][] distances){
        for(int k = 1; k < distances.length; k++){
            for(int i = 1; i < distances.length; i++){
                for(int j = 1; j < distances.length; j++) {
                    if (distances[i][k][0] == Integer.MAX_VALUE
                            || distances[k][j][0] == Integer.MAX_VALUE
                            || distances[i][j][0] < distances[i][k][0] + distances[k][j][0]) {
                        distances[i][j][1] = distances[i][j][0];
                    } else {
                        distances[i][j][1] = distances[i][k][0] + distances[k][j][0];
                    }

                    //Check for a negative cycle
                    // If found, terminate
                    if (distances[i][i][1] < 0) {
                        return true;
                    }

                    //Memory optimization: Copy new K column to K-1 column
                    for(int l=0; l < distances.length; l++) {
                        distances[i][j][0] = distances[i][j][1];
                    }
                }
            }
        }
        //All shortest paths computed and no negative cycles found
        return false;
    }

    private static List<String> readFileInputAndReturnOutput(String filePath) {
        List<String> output = new ArrayList<>();

        Path path = Paths.get(filePath);

        try {
            List<String> lines = Files.readAllLines(path);

            int setsOfFamilies = Integer.parseInt(lines.get(0));
            int lineIndex = 1;

            for (int familySet = 1; familySet <= setsOfFamilies; familySet++) {
                String line = lines.get(lineIndex);

                String[] valuesInLine = line.split(" ");
                int cities = Integer.parseInt(valuesInLine[0]);
                int roads = Integer.parseInt(valuesInLine[1]);
                int familyBelongings = Integer.parseInt(valuesInLine[2]);

                Graph graph = new ManicMoving().new Graph();

                for(int city = 1; city <= cities; city++) {
                    graph.addVertex(city);
                }

                for(int road = 0; road < roads; road++) {
                    lineIndex++;
                    String[] roadsLine = lines.get(lineIndex).split(" ");

                    int vertexId1 = Integer.parseInt(roadsLine[0]);
                    int vertexId2 = Integer.parseInt(roadsLine[1]);
                    int requiredGas = Integer.parseInt(roadsLine[2]);

                    graph.addEdge(vertexId1, vertexId2, requiredGas);
                }

                int[][] instructions = new int[familyBelongings][2];

                for(int i = 0; i < familyBelongings; i++) {
                    lineIndex++;
                    String[] instructionsLine = lines.get(lineIndex).split(" ");

                    int sourceCity = Integer.parseInt(instructionsLine[0]);
                    int destinationCity = Integer.parseInt(instructionsLine[1]);

                    instructions[i][0] = sourceCity;
                    instructions[i][1] = destinationCity;
                }

                long minimumGas = computeMinimumGas(graph, instructions);
                output.add("Case #" + familySet + ": " + minimumGas);

                lineIndex++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
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
