package com.br.movile.code.challenge;

import java.util.Scanner;

public class PlayKidsBooks {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numberOfTests = Integer.parseInt(sc.nextLine());

        for(int t = 0; t < numberOfTests; t++) {
            int rows = sc.nextInt();
            int seats = sc.nextInt();

            int[][] booksOrder = getBooksOrder(rows, seats);

            for(int i = 0; i < booksOrder.length; i++) {
                for(int j = 0; j < booksOrder[0].length; j++) {
                    System.out.print(booksOrder[i][j]);

                    if(j < booksOrder[0].length - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }

    private static int[][] getBooksOrder(int rows, int columns) {
        int[][] booksOrder = new int[rows][columns];

        int number = rows * columns;

        for(int i = 0; i < rows * columns; i++) {
            int row = i;
            int column = 0;

            do {
                if(row >= 0 && row < rows && column >= 0 && column < columns) {
                    booksOrder[row][column] = number;
                    number--;
                }

                row--;
                column++;
            } while (row >= 0);

        }

        return booksOrder;
    }

}
