package com.br.maratona.mineira.ano2017;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

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

    private static class DominantPlayer {
        int playerIndex;
        int time;

        DominantPlayer(int playerIndex, int time) {
            this.playerIndex = playerIndex;
            this.time = time;
        }
    }

    public static void main(String[] args) throws IOException{
        FastReader.init(System.in);
        int rows = FastReader.nextInt();
        int columns = FastReader.nextInt();

        int rounds = FastReader.nextInt();
        byte playerIndex = 1;

        DominantPlayer[] dominantRows = new DominantPlayer[rows];
        DominantPlayer[] dominantColumns = new DominantPlayer[columns];

        int time = 0;

        int player1TotalScore = 0;
        int player2TotalScore = 0;
        int player3TotalScore = 0;
        int player4TotalScore = 0;

        for(int r = 0; r < rounds; r++) {
            String move = FastReader.next();
            int value = FastReader.nextInt();

            if (move.equals("L")) {
                DominantPlayer dominantPlayer = new DominantPlayer(playerIndex, time);
                dominantRows[value - 1] = dominantPlayer;
            } else {
                DominantPlayer dominantPlayer = new DominantPlayer(playerIndex, time);
                dominantColumns[value - 1] = dominantPlayer;
            }

            playerIndex++;
            if (playerIndex == 5) {
                playerIndex = 1;
            }

            time++;
        }

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                DominantPlayer dominantPlayerRow = dominantRows[i];
                DominantPlayer dominantPlayerColumn = dominantColumns[j];

                int dominantPlayerId = 0;

                if (dominantPlayerRow != null) {
                    if (dominantPlayerColumn != null) {
                        if (dominantPlayerRow.time > dominantPlayerColumn.time) {
                            dominantPlayerId = dominantPlayerRow.playerIndex;
                        } else {
                            dominantPlayerId = dominantPlayerColumn.playerIndex;
                        }
                    } else {
                        dominantPlayerId = dominantPlayerRow.playerIndex;
                    }
                } else {
                    if (dominantPlayerColumn != null) {
                        dominantPlayerId = dominantPlayerColumn.playerIndex;
                    }
                }

                if (dominantPlayerId == 1) {
                    player1TotalScore++;
                } else if (dominantPlayerId == 2) {
                    player2TotalScore++;
                } else if (dominantPlayerId == 3) {
                    player3TotalScore++;
                } else if (dominantPlayerId == 4) {
                    player4TotalScore++;
                }
            }
        }

        System.out.println("R" + player1TotalScore + " H" + player2TotalScore + " C" + player3TotalScore + " P" + player4TotalScore);
    }

}
