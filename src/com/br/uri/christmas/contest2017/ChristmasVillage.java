package com.br.uri.christmas.contest2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by rene on 16/12/17.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/338/9
// https://www.urionlinejudge.com.br/judge/en/problems/view/2725
public class ChristmasVillage {

    private static class FastReader {

        private static BufferedReader reader;
        private static StringTokenizer tokenizer;

        /** Call this method to initialize reader for InputStream */
        static void init(InputStream input) {
            reader = new BufferedReader(new InputStreamReader(input));
            tokenizer = new StringTokenizer("");
        }

        /** Get next word */
        private static String next() throws IOException {
            while (!tokenizer.hasMoreTokens() ) {
                tokenizer = new StringTokenizer(reader.readLine());
            }
            return tokenizer.nextToken();
        }

        private static int nextInt() throws IOException {
            return Integer.parseInt(next());
        }

        private static double nextDouble() throws IOException {
            return Double.parseDouble(next());
        }

        private static long nextLong() throws IOException {
            return Long.parseLong(next());
        }

        //Used to check EOF
        //If getLine() == null, it is a EOF
        //Otherwise, it returns the next line
        private static String getLine() throws IOException {
            return reader.readLine();
        }
    }

    private static class Edge {
        int vertex1;
        int vertex2;
        double cost;

        Edge(int vertex1, int vertex2, double cost) {
            this.vertex1 = vertex1;
            this.vertex2 = vertex2;
            this.cost = cost;
        }
    }

    private static class UnionFind {

        private int[] leaders;
        private int[] ranks;

        private int components;

        public UnionFind(int size) {
            leaders = new int[size];
            ranks = new int[size];
            components = size;

            for(int i = 0; i < size; i++) {
                leaders[i] = i;
                ranks[i] = 0;
            }
        }

        public int count() {
            return components;
        }

        public boolean connected(int site1, int site2) {
            return find(site1) == find(site2);
        }

        //O(inverse Ackermann function)
        public int find(int site) {
            if (site == leaders[site]) {
                return site;
            }

            return leaders[site] = find(leaders[site]);
        }

        //O(inverse Ackermann function)
        public void union(int site1, int site2) {

            int leader1 = find(site1);
            int leader2 = find(site2);

            if (leader1 == leader2) {
                return;
            }

            if (ranks[leader1] < ranks[leader2]) {
                leaders[leader1] = leader2;
            } else if (ranks[leader2] < ranks[leader1]) {
                leaders[leader2] = leader1;
            } else {
                leaders[leader1] = leader2;
                ranks[leader2]++;
            }

            components--;
        }
    }

    private static class Coordinate {
        int x;
        int y;

        Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object object) {
            if (!(object instanceof Coordinate)) {
                return false;
            }

            Coordinate otherCoordinate = (Coordinate) object;
            return this.x == otherCoordinate.x && this.y == otherCoordinate.y;
        }

        @Override
        public int hashCode() {
            return (x * 1000) + y;
        }
    }

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);
        int tests = FastReader.nextInt();

        for(int t = 0; t < tests; t++) {
            int elves = FastReader.nextInt();
            int currencyRate = FastReader.nextInt();

            Coordinate[] elvesHousesCoordinates = new Coordinate[elves];

            for(int elf = 0; elf < elves; elf++) {
                int x = FastReader.nextInt();
                int y = FastReader.nextInt();

                Coordinate house = new Coordinate(x, y);
                elvesHousesCoordinates[elf] = house;
            }

            Edge[] edges = new Edge[elves * elves];
            int edgeIndex = 0;

            for(int i = 0; i < elvesHousesCoordinates.length; i++) {
                for(int j = 0; j < elvesHousesCoordinates.length; j++) {
                    int cost;

                    if (i == j) {
                        cost = 0;
                    } else {
                        cost = countIntegerCoordinatesInLine(elvesHousesCoordinates[i].x, elvesHousesCoordinates[i].y,
                                elvesHousesCoordinates[j].x, elvesHousesCoordinates[j].y);
                        cost -= 2; // Subtract the two elves houses
                    }

                    edges[edgeIndex++] = new Edge(i, j, cost);
                }
            }

            List<Edge> edgesInMST = getMinimumSpanningTreeEdges(edges, elves);

            int numberOfLeprechaunsHousesIntersected = 0;

            for(Edge edge : edgesInMST) {
                numberOfLeprechaunsHousesIntersected += edge.cost;
            }

            long amountToPay = numberOfLeprechaunsHousesIntersected * currencyRate;
            System.out.println(amountToPay);
        }
    }

    private static List<Edge> getMinimumSpanningTreeEdges(Edge[] edges, int totalVertices) {
        List<Edge> edgesInSpanningTree = new ArrayList<>();

        Arrays.sort(edges, new Comparator<Edge>() {
            @Override
            public int compare(Edge edge1, Edge edge2) {
                if (edge1.cost < edge2.cost) {
                    return -1;
                } else if (edge1.cost > edge2.cost) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });

        UnionFind unionFind = new UnionFind(totalVertices);

        for(Edge edge : edges) {

            if (unionFind.find(edge.vertex1) != unionFind.find(edge.vertex2)) {
                unionFind.union(edge.vertex1, edge.vertex2);
                edgesInSpanningTree.add(edge);
            }

            if (unionFind.components == 1) {
                break;
            }
        }

        return edgesInSpanningTree;
    }

    // Based on https://math.stackexchange.com/questions/918362/what-is-the-number-of-integer-coordinates-on-a-line-segment
    private static int countIntegerCoordinatesInLine(int house1X, int house1Y, int house2X, int house2Y) {
        int minX = Math.min(house1X, house2X);
        int maxX = Math.max(house1X, house2X);
        int minY = Math.min(house1Y, house2Y);
        int maxY = Math.max(house1Y, house2Y);

        // Slope = (y2 - y1) / (x2 - x1)
        int slopeY = maxY - minY;
        int slopeX = maxX - minX;

        int gcd = (int) gcd(slopeY, slopeX);

        int reducedY = slopeY / gcd;
        int reducedX = slopeX / gcd;

        int integerCoordinatesInLine = 0;

        int currentX = minX;
        int currentY = minY;

        while (currentX <= maxX && currentY <= maxY) {
            integerCoordinatesInLine++;

            currentX += reducedX; // Increase reducedX for every point to check
            currentY += reducedY; // Increase reducedY for every point to check
        }

        return integerCoordinatesInLine;
    }

    private static long gcd(long number1, long number2) {
        while (number2 > 0) {
            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return number1;
    }

}