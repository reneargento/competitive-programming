package com.br.maratona.mineira.ano2017;

import java.util.Scanner;

/**
 * Created by rene on 27/05/17.
 */

/**
 * Red, Hot, Chilli and Peppers are four students who always meet under the bridge after school ends to play a game called Californication.
 * They draw a NxM grid on the ground, initially empty, and the ultimate goal is to master as much of this grid as possible.

 The students play alternately, always following the same order: Red, Hot, Chili and Peppers.
 After Peppers, the turn returns to Red and so they continue playing until they complete K rounds.
 In each of the rounds, the player can choose between two possible moves:

 L X -> {} It means to dominate the line X of the grid, writing the initial of its name in all the elements contained in this line.

 C Y -> {} It means to dominate the column Y of the grid, writing the initial of its name in all elements contained in that column.

 Input

 The first line of the input contains three integers N, M (1 ≤ N, M ≤ 103) and K (1 ≤ K ≤ 5 × 105),
 the grid dimensions (number of rows and columns respectively) and how many rows were played.

 After that, there are K lines, each containing oneL X (1 ≤ X ≤ N) or C Y (1 ≤ Y ≤ M) type, both described above.

 Output
 Show how dominant each player was at the end of the match in the following format:
 Ra Hb Cc Pd, where a, b, c and d are integers, representing the final score of Red, Hot, Chili and Peppers, respectively.
 */

//https://www.urionlinejudge.com.br/judge/en/challenges/view/266/3
public class Californication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int rows = scanner.nextInt();
        int columns = scanner.nextInt();

        int rounds = scanner.nextInt();
        byte playerIndex = 1;

        int[][] matrix = new int[rows][columns];

        int player1TotalScore = 0;
        int player2TotalScore = 0;
        int player3TotalScore = 0;
        int player4TotalScore = 0;

        for(int r=0; r < rounds; r++) {
            String move = scanner.next();
            int value = scanner.nextByte();

            if(move.equals("L")) {
                for(int i=0; i < columns; i++) {
                    fillMatrixRowEfficiently(matrix, value - 1, playerIndex);
                }
            } else {
                for(int j=0; j < rows; j++) {
                    matrix[j][value - 1] = playerIndex;
                }
            }

            playerIndex++;
            if(playerIndex == 5) {
                playerIndex = 1;
            }
        }

        for(int i=0; i < rows; i++) {
            for(int j=0; j < columns; j++) {
                if(matrix[i][j] == 1) {
                    player1TotalScore++;
                } else if(matrix[i][j] == 2) {
                    player2TotalScore++;
                } else if(matrix[i][j] == 3) {
                    player3TotalScore++;
                } else if(matrix[i][j] == 4) {
                    player4TotalScore++;
                }
            }
        }

        System.out.println("R" + player1TotalScore + " H" + player2TotalScore + " C" + player3TotalScore + " P" + player4TotalScore);
    }

    private static void fillMatrixRowEfficiently(int[][] matrix, int row, int value) {
        matrix[row][0] = value;

        for (int i = 1; i < matrix.length; i += i) {
            System.arraycopy(matrix[row], 0, matrix[row], i, ((matrix.length - i) < i) ? (matrix.length - i) : i);
        }
    }

}
