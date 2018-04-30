package com.br.google.code.jam.code2018.round1a;

import java.util.Scanner;

/**
 * Created by Rene Argento on 13/04/18.
 */
public class WaffleChoppers {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for (int test = 0; test < tests; test++) {
            int rows = scanner.nextInt();
            int columns = scanner.nextInt();
            int horizontalCuts = scanner.nextInt();
            int verticalCuts = scanner.nextInt();

            int[][] waffle = new int[rows][columns];

            for (int i = 0; i < rows; i++) {
                String row = scanner.next();
                String[] chars = row.split("");

                for (int j = 0; j < chars.length; j++) {
                    if (chars[j].equals("@")) {
                        waffle[i][j] = 1;
                    } else {
                        waffle[i][j] = 0;
                    }
                }
            }

            int rowToCut = -1;
            int columnToCut = -1;
            boolean possible = true;

            // Process rows
            for (int i = 0; i < rows; i++) {
                int sum = 0;

                for (int j = 0; j < columns; j++) {
                    if (waffle[i][j] == 1) {
                        sum++;
                    }
                }

                if (sum % 2 != 0) {
                    possible = false;
                    break;
                }

                int halfSum = 0;

                for (int j = 0; j < columns; j++) {
                    if (waffle[i][j] == 1) {
                        halfSum++;
                    }

                    if (halfSum == sum / 2) {
                        if (rowToCut == -1) {
                            rowToCut = j;
                        } else if (rowToCut != j) {
                            possible = false;
                        }

                        break;
                    }
                }

                if (!possible) {
                    break;
                }
            }

            if (possible) {

                // Process columns
                for (int i = 0; i < columns; i++) {
                    int sum = 0;

                    for (int j = 0; j < rows; j++) {
                        if (waffle[j][i] == 1) {
                            sum++;
                        }
                    }

                    if (sum % 2 != 0) {
                        possible = false;
                        break;
                    }

                    int halfSum = 0;

                    for (int j = 0; j < rows; j++) {
                        if (waffle[j][i] == 1) {
                            halfSum++;
                        }

                        if (halfSum == sum / 2) {
                            if (columnToCut == -1) {
                                columnToCut = i;
                            } else if (columnToCut != i) {
                                possible = false;
                            }

                            break;
                        }
                    }

                    if (!possible) {
                        break;
                    }
                }
            }

            if (possible) {
                System.out.println("POSSIBLE");
            } else {
                System.out.println("IMPOSSIBLE");
            }

        }

    }

}
