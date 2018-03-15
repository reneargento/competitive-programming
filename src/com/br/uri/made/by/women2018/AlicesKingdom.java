package com.br.uri.made.by.women2018;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Created by Rene Argento on 10/03/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/346/6
public class AlicesKingdom {

    private static class Cell {
        int row;
        int column;

        Cell(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        scanner.nextLine();

        String[][] kingdom = new String[rows][columns];

        for (int i = 0; i < rows; i++) {
            String[] lineInformation = scanner.nextLine().split(" ");

            for (int j = 0; j < lineInformation.length; j++) {
                kingdom[i][j] = lineInformation[j];
            }
        }

        int maxCastlesVisited = 0;
        boolean[][] visited = new boolean[kingdom.length][kingdom[0].length];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (isValidIndex(kingdom, i, j) && kingdom[i][j].equals("C") && !visited[i][j]) {
                    int castlesVisited = bfs(kingdom, i, j, visited);
                    maxCastlesVisited = Math.max(maxCastlesVisited, castlesVisited);
                }
            }
        }

        System.out.println(maxCastlesVisited);
    }

    private static int bfs(String[][] kingdom, int row, int column, boolean[][] visited) {
        int castles = 0;

        visited[row][column] = true;

        Queue<Cell> queue = new LinkedList<>();
        queue.offer(new Cell(row, column));

        while (!queue.isEmpty()) {
            Cell currentCell = queue.poll();
            if (kingdom[currentCell.row][currentCell.column].equals("C")) {
                castles++;
            }

            int[] neighborRows = {-1, 0, 0, 1};
            int[] neighborColumns = {0, -1, 1, 0};

            for(int i = 0; i < neighborRows.length; i++) {
                int neighborRow = currentCell.row + neighborRows[i];
                int neighborColumn = currentCell.column + neighborColumns[i];

                if (isValidIndex(kingdom, neighborRow, neighborColumn)
                        && !visited[neighborRow][neighborColumn]
                        && !kingdom[neighborRow][neighborColumn].equals("R")
                        && !kingdom[neighborRow][neighborColumn].equals("P")) {
                    queue.offer(new Cell(neighborRow, neighborColumn));
                    visited[neighborRow][neighborColumn] = true;
                }
            }
        }

        return castles;
    }

    private static boolean isValidIndex(String[][] kingdom, int row, int column) {
        return row >= 0 && row < kingdom.length && column >= 0 && column < kingdom[0].length;
    }

}
