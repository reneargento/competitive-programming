package com.br.google.code.jam.code2018.round1a;

import java.util.Scanner;

/**
 * Created by Rene Argento on 13/04/18.
 */
public class WaffleChoppers {

    private static class Interval {
        int leftIndex;
        int rightIndex;

        Interval(int leftIndex, int rightIndex) {
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int test = 1; test <= tests; test++) {

            System.out.print("Case #"+ test + ": ");

            if (isPossible(scanner)) {
                System.out.println("POSSIBLE");
            } else {
                System.out.println("IMPOSSIBLE");
            }
        }
    }

    private static boolean isPossible(Scanner scanner) {
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();
        int horizontalCuts = scanner.nextInt();
        int verticalCuts = scanner.nextInt();

        int numberOfChips = 0;

        int[][] waffle = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            String row = scanner.next();
            String[] chars = row.split("");

            for (int j = 0; j < chars.length; j++) {
                if (chars[j].equals("@")) {
                    waffle[i][j] = 1;
                    numberOfChips++;
                } else {
                    waffle[i][j] = 0;
                }
            }
        }

        // 1- Create row and column sum arrays
        int[] rowSum = new int[rows];
        int[] columnSum = new int[columns];

        for (int i = 0; i < waffle.length; i++) {
            for (int j = 0; j < waffle[0].length; j++) {
                rowSum[i] += waffle[i][j];
                columnSum[j] += waffle[i][j];
            }
        }

        // 2- Create cumulative sum arrays
        int[] prefixSumRows = new int[rows];
        int[] prefixSumColumns = new int[columns];

        prefixSumRows[0] = rowSum[0];
        for (int row = 1; row < rowSum.length; row++) {
            prefixSumRows[row] = prefixSumRows[row - 1] + rowSum[row];
        }

        prefixSumColumns[0] = columnSum[0];
        for (int column = 1; column < columnSum.length; column++) {
            prefixSumColumns[column] = prefixSumColumns[column - 1] + columnSum[column];
        }

        // 3- Find places to cut and create interval arrays
        Interval[] rowIntervals = new Interval[horizontalCuts + 1];
        Interval[] columnIntervals = new Interval[verticalCuts + 1];

        int rowIntervalSize = numberOfChips / (horizontalCuts + 1);
        int columnIntervalSize = numberOfChips / (verticalCuts + 1);

        int currentRowIntervalSize = rowIntervalSize;
        int currentColumnIntervalSize = columnIntervalSize;

        // Row intervals
        int rowMatches = 0;

        for (int row = 0; row < rowSum.length; row++) {
            if (prefixSumRows[row] == currentRowIntervalSize) {
                currentRowIntervalSize += rowIntervalSize;

                if (rowMatches == 0) {
                    rowIntervals[0] = new Interval(0, row);
                } else {
                    rowIntervals[rowMatches] = new Interval(rowIntervals[rowMatches - 1].rightIndex + 1, row);
                }

                if (rowMatches == horizontalCuts - 1) {
                    rowIntervals[rowMatches + 1] = new Interval(row + 1, rows - 1);
                    rowMatches++;
                    break;
                }

                rowMatches++;
            } else if (prefixSumRows[row] > currentRowIntervalSize) {
                return false;
            }
        }

        if (rowMatches < horizontalCuts) {
            return false;
        }

        // Column intervals
        int columnMatches = 0;

        for (int column = 0; column < columnSum.length; column++) {
            if (prefixSumColumns[column] == currentColumnIntervalSize) {
                currentColumnIntervalSize += columnIntervalSize;

                if (columnMatches == 0) {
                    columnIntervals[0] = new Interval(0, column);
                } else {
                    columnIntervals[columnMatches] = new Interval(columnIntervals[columnMatches - 1].rightIndex + 1, column);
                }

                if (columnMatches == verticalCuts - 1) {
                    columnIntervals[columnMatches + 1] = new Interval(column + 1, columns - 1);
                    columnMatches++;
                    break;
                }

                columnMatches++;
            } else if (prefixSumColumns[column] > currentColumnIntervalSize) {
                return false;
            }
        }

        if (columnMatches < verticalCuts) {
            return false;
        }

        // 4- Use the interval arrays to check if every piece has the same number of chips
        int numberOfChipsExpected = numberOfChips / ((horizontalCuts + 1) * (verticalCuts + 1));

        for (int rowInterval = 0; rowInterval < rowIntervals.length; rowInterval++) {
            for (int columnInterval = 0; columnInterval < columnIntervals.length; columnInterval++) {
                int chips = 0;

                for (int row = rowIntervals[rowInterval].leftIndex; row <= rowIntervals[rowInterval].rightIndex; row++) {
                    for (int column = columnIntervals[columnInterval].leftIndex;
                         column <= columnIntervals[columnInterval].rightIndex; column++) {
                        if (waffle[row][column] == 1) {
                            chips++;
                        }
                    }
                }

                if (chips != numberOfChipsExpected) {
                    return false;
                }
            }
        }

        return true;
    }
}