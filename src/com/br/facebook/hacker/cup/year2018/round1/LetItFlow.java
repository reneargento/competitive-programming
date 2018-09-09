package com.br.facebook.hacker.cup.year2018.round1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

/**
 * Created by Rene Argento on 21/07/18.
 */
public class LetItFlow {

    private static final String PATH = "/Users/rene/Desktop/Algorithms/Competitions/Facebook Hacker Cup/2018/Round 1/Input - Output/";

//    private static final String FILE_INPUT_PATH = PATH + "let_it_flow_sample_input.txt";
//    private static final String FILE_OUTPUT_PATH = PATH + "let_it_flow_sample_output.txt";

    private static final String FILE_INPUT_PATH = PATH + "let_it_flow_input.txt";
    private static final String FILE_OUTPUT_PATH = PATH + "let_it_flow_output.txt";

    public static void main(String[] args) {
        //test();
        compete();
    }

    private static void test() {
        String[][] matrix1 = {
                {".", "."},
                {".", "."},
                {".", "."}
        };
        long pathsCount1 = getPathCount(matrix1);
        System.out.println(pathsCount1 + " Expected: 1");

        String[][] matrix2 = {
                {"#", "."},
                {".", "."},
                {".", "#"}
        };
        long pathsCount2 = getPathCount(matrix2);
        System.out.println(pathsCount2 + " Expected: 0");

        String[][] matrix3 = {
                {".", ".", ".", "#"},
                {".", ".", ".", "."},
                {".", "#", ".", "."}
        };
        long pathsCount3 = getPathCount(matrix3);
        System.out.println(pathsCount3 + " Expected: 1");

        String[][] matrix4 = {
                {".", ".", "#", "#"},
                {".", ".", ".", "."},
                {".", "#", ".", "."}
        };
        long pathsCount4 = getPathCount(matrix4);
        System.out.println(pathsCount4 + " Expected: 0");

        String[][] matrix5 = {
                {".", "#", ".", ".", "."},
                {".", ".", ".", ".", "."},
                {".", ".", ".", ".", "."}
        };
        long pathsCount5 = getPathCount(matrix5);
        System.out.println(pathsCount5 + " Expected: 0");

        String[][] matrix6 = {
                {".", ".", ".", ".", "#", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", "."}
        };
        long pathsCount6 = getPathCount(matrix6);
        System.out.println(pathsCount6 + " Expected: 4");

        String[][] matrix7 = {
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", },
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", },
                {".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".",
                        ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", ".", }
        };
        long pathsCount7 = getPathCount(matrix7);
        System.out.println(pathsCount7 + " Expected: 179869065");
    }

    private static void compete() {
        List<String> lines = readFileInput(FILE_INPUT_PATH);
        int caseId = 1;
        List<String> output = new ArrayList<>();

        for(int i = 1; i < lines.size();) {
            int columns = Integer.parseInt(lines.get(i));
            i++;

            String[][] matrix = new String[3][columns];

            for (int row = 0; row < 3; row++, i++) {
                matrix[row] = lines.get(i).split("");
            }

            long pathsCount = getPathCount(matrix);

            output.add("Case #" + caseId + ": " + pathsCount);
            caseId++;
        }

        writeDataOnFile(FILE_OUTPUT_PATH, output);
    }

    private enum Direction {
        HORIZONTAL, VERTICAL;
    }
    private static final long MOD = 1000000007;

    private static class Cell {
        private int id;
        private Direction originDirection;

        Cell(int id, Direction originDirection) {
            this.id = id;
            this.originDirection = originDirection;
        }
    }

    private static long getPathCount(String[][] matrix) {
        List<Cell>[] adjacent = getAdjacencyList(matrix);
        int source = 0;
        int destination = matrix.length * matrix[0].length - 1;

        return countNumberOfPaths(adjacent, adjacent.length, source, destination);
    }

    @SuppressWarnings("unchecked")
    private static List<Cell>[] getAdjacencyList(String[][] matrix) {
        int maxColumn = matrix[0].length;
        List<Cell>[] adjacent = new ArrayList[matrix.length * maxColumn];

        for (int i = 0; i < adjacent.length; i++) {
            adjacent[i] = new ArrayList<>();
        }

        int[] neighborRows = {0, -1, 1, 0};
        int[] neighborColumns = {-1, 0, 0, 1};

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j].equals("#")) {
                    continue;
                }

                int currentCellId = getCellId(i, j, maxColumn);

                for (int k = 0; k < 4; k++) {
                    int neighborRow = i + neighborRows[k];
                    int neighborColumn = j + neighborColumns[k];

                    if (isValidCell(neighborRow, neighborColumn, matrix)) {
                        if (matrix[neighborRow][neighborColumn].equals(".")) {
                            int neighborCellId = getCellId(neighborRow, neighborColumn, maxColumn);

                            Direction direction;
                            if (neighborRow != i) {
                                direction = Direction.VERTICAL;
                            } else {
                                direction = Direction.HORIZONTAL;
                            }

                            adjacent[currentCellId].add(new Cell(neighborCellId, direction));
                        }
                    }
                }
            }
        }

        return adjacent;
    }

    private static int getCellId(int row, int column, int maxColumn) {
        return (row * maxColumn) + column;
    }

    private static boolean isValidCell(int row, int column, String[][] matrix) {
        return row >= 0 && row < matrix.length && column >= 0 && column < matrix[0].length;
    }

    private static long countNumberOfPaths(List<Cell>[] adjacent, int vertexCount, int source, int destination) {
        boolean[] visited = new boolean[vertexCount];
        long[] paths = new long[vertexCount];

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(source, Direction.HORIZONTAL));
        paths[source] = 1;

        visited[source] = true;

        while (!queue.isEmpty()) {
            Cell currentVertex = queue.poll();

            for(Cell neighbor : adjacent[currentVertex.id]) {

                if (currentVertex.originDirection != neighbor.originDirection) {
                    if (neighbor.id == destination && neighbor.originDirection == Direction.HORIZONTAL) {
                        continue;
                    }

                    if (!visited[neighbor.id]) {
                        paths[neighbor.id] = paths[currentVertex.id];
                        queue.offer(neighbor);
                    } else {
                        paths[neighbor.id] += paths[currentVertex.id];
                        paths[neighbor.id] = paths[neighbor.id] % MOD;
                    }

                    visited[neighbor.id] = true;
                }
            }
        }

        return paths[destination];
    }

    private static List<String> readFileInput(String filePath) {
        Path path = Paths.get(filePath);
        List<String> lines = null;

        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
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
