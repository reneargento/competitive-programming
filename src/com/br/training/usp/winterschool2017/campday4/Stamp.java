package com.br.training.usp.winterschool2017.campday4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by rene on 13/07/17.
 */
public class Stamp {

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
    }

    private static class InkPosition {
        int row, column;

        InkPosition(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    private static long numberOfInks = 0;

    public static void main(String[] args) throws IOException {
        FastReader.init(System.in);

        int tests = FastReader.nextInt();

        for(int i = 0; i < tests; i++) {
            int rowPaper = FastReader.nextInt();
            int columnPaper = FastReader.nextInt();
            int rowStamp = FastReader.nextInt();
            int columnStamp = FastReader.nextInt();

            numberOfInks = 0;

            char[][] paper = new char[rowPaper][columnPaper];
            for(int row = 0; row < rowPaper; row++) {
                String currentRow = FastReader.next();

                for(int column = 0; column < columnPaper; column++) {
                    paper[row][column] = currentRow.charAt(column);

                    if (paper[row][column] == 'x') {
                        numberOfInks++;
                    }
                }
            }

            int originRow = -1;
            int originColumn = -1;
            Set<InkPosition> inkPositions = new HashSet<>();

            char[][] stamp = new char[rowStamp][columnStamp];
            for(int row = 0; row < rowStamp; row++) {
                String currentRow = FastReader.next();

                for(int column = 0; column < columnStamp; column++) {
                    stamp[row][column] = currentRow.charAt(column);

                    if (stamp[row][column] == 'x') {
                        if (originRow == -1) {
                            originRow = row;
                            originColumn = column;
                        }

                        inkPositions.add(new InkPosition(row - originRow, column - originColumn));
                    }
                }
            }

            boolean[][] visited = new boolean[rowPaper][columnPaper];
            boolean possibleToCreate = true;

            for(int row = 0; row < rowPaper; row++) {
                for(int column = 0; column < columnPaper; column++) {
                    if (paper[row][column] == 'x' && !visited[row][column]) {
                        boolean possible = possibleToCreate(paper, row, column, visited, inkPositions);
                        if (!possible) {
                            possibleToCreate = false;
                            break;
                        }
                    }
                }

                if (!possibleToCreate) {
                    break;
                }
            }

            if (possibleToCreate && numberOfInks == 0) {
                System.out.println("ANO");
            } else {
                System.out.println("NIE");
            }
        }
    }

    private static boolean possibleToCreate(char[][] paper, int paperRow, int paperColumn,
                                            boolean[][] visited, Set<InkPosition> inkPositions) {
        for(InkPosition inkPosition : inkPositions) {
            int rowToCheck = paperRow + inkPosition.row;
            int columnToCheck = paperColumn + inkPosition.column;

            if (!isValidIndex(paper, rowToCheck, columnToCheck)) {
                return false;
            }

            if (visited[rowToCheck][columnToCheck]) {
                return false;
            }

            if (paper[rowToCheck][columnToCheck] != 'x') {
                return false;
            }

            visited[rowToCheck][columnToCheck] = true;
            numberOfInks--;
        }

        return true;
    }

    private static boolean isValidIndex(char[][] matrix, int row, int column) {
        return !(row < 0 || row >= matrix.length || column < 0 || column >= matrix[0].length);
    }

}
