package com.br.codefights.marathon.may2017;

/**
 * Created by rene on 27/05/17.
 */

/**
 * You have a new favorite board game. Its board is a loop of length n.
 * At the start of a game, the game piece stands at the first cell.
 * You roll a standard 6-sided dice, and the result of that roll tells you how many spaces forward to move the game piece.
 * Some cells have additional values +k (or -k), and if the game piece stands on this cell after a dice throw,
 * the piece moves k cells forward (or backward).

 You are given an array board that describes the loop of the game board and an array diceValues that contains the results of
 the dice throws. board[i] corresponds to the cell at index i+1 (so the indexes of the cells are 1-based).
 If board[i] = k, then the game piece moves k cells forward; if board[i] = -k, then it moves k cells backward; and if
 board[i] = 0, then there are no additional moves available to the game piece at this cell.
 Remember that the game piece can use the additional value on the board's cell only once after each dice throw.

 Return the cell where the game piece will be positioned after all of the dice throws.

 Example

 For board = [0, -5, 4, 0, 0, 2, 0, 3, 0] and diceValues = [1, 2, 3, 1, 2], the output should be
 cyclicGame(board, diceValues) = 1.

 After the first dice throw, the game piece stands on cell 2, where we have an additional value -5, so the piece moves to cell 6.
 Note that the game piece doesn't use the additional value +2 on this cell, because additional values can be used only once
 after a dice throw.
 After the next throw, the game piece stands on cell 8, where we have an additional value +3, so the piece moves to cell 2.
 Again, note that the game piece doesn't use the additional value -5 on it.
 Then, after two more dice throws, the game piece stands on cell 6, where we have an additional value +2, so the piece moves to cell 8.
 Since we've already used an additional value after the dice throw, we cannot use the +3 on cell 8.
 After the last dice throw, the game piece stands on cell 1.

 Input/Output

 [time limit] 3000ms (java)
 [input] array.integer board

 The game board. If board[i] = k, then the game piece moves k cells forward; if board[i] = -k, then the piece moves k cells backward;
 and if board[i] = 0, then there are no additional moves available to the game piece at this cell.

 Guaranteed constraints:
 1 ≤ board.length ≤ 1000,
 -board.length < board[i] < board.length.

 [input] array.integer diceValues

 The results of the dice throws.

 Guaranteed constraints:
 1 ≤ diceValues.length ≤ 1000,
 1 ≤ diceValues[i] ≤ 6.

 [output] integer
 The cell on the board where the game piece will stand after all the dice throws have been completed.
 */
public class CyclicGame {

    public static void main(String[] args) {
        int[] board1 = {0, -5, 4, 0, 0, 2, 0, 3, 0};
        int[] diceValues1 = {1, 2, 3, 1, 2};

        System.out.println(cyclicGame(board1, diceValues1) + " Expected: 1");

        int[] board2 = {-2, 0, 2, 2, -5, 5};
        int[] diceValues2 = {2, 3, 1, 2, 2};

        System.out.println(cyclicGame(board2, diceValues2) + " Expected: 5");

        int[] board3 = {-4, -6, 5, 6, -6, -3, -1, 5, -7};
        int[] diceValues3 = {6, 3, 3, 5, 4, 2, 6};

        System.out.println(cyclicGame(board3, diceValues3) + " Expected: 8");

        int[] board4 = {-6, -5, 9, 4, -4, -5, 7, -4, -5, 5};
        int[] diceValues4 = {6, 2, 5, 6, 4, 6, 2, 1, 4, 1};

        System.out.println(cyclicGame(board4, diceValues4) + " Expected: 7");

        int[] board5 = {-1, -1, -6, -6, 5, -1, -2, -4};
        int[] diceValues5 = {5, 5, 2, 2, 1, 5, 3, 5, 4, 6, 6, 4, 6, 3, 6};

        System.out.println(cyclicGame(board5, diceValues5) + " Expected: 1");

        int[] board6 = {2, 4, -3, 0, -2};
        int[] diceValues6 = {3};

        System.out.println(cyclicGame(board6, diceValues6) + " Expected: 4");
    }

    private static int cyclicGame(int[] board, int[] diceValues) {

        int currentPosition = 0;

        for(int i = 0; i < diceValues.length; i++) {

            int nextBoardPosition = (currentPosition + diceValues[i]) % board.length;

            int nextBoardPositionAfterExtraValue =
                    (nextBoardPosition + board[nextBoardPosition]);

            if (nextBoardPositionAfterExtraValue > 0) {
                currentPosition = nextBoardPositionAfterExtraValue % board.length;
            } else {
                currentPosition = board.length + nextBoardPositionAfterExtraValue;
            }
        }

        return (currentPosition % board.length) + 1;
    }

}
