package com.br.hacker.rank.rookie.rank4;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/02/18.
 */
// https://www.hackerrank.com/contests/rookierank-4/challenges/winning-hand-of-cards
//TODO
public class WinningHandOfCards {

    private static int winningHands = 0;
    private static int targetValue;
    private static int modValue;

    private static int winningHands(int mod, int target, int[] cards) {
        targetValue = target;
        modValue = mod;

        for(int i = 1; i <= cards.length; i++) {
            printCombination(cards, cards.length, i);
        }

        return winningHands;
    }

    private static void printCombination(int arr[], int n, int r) {
        // A temporary array to store all combination one by one
        int data[] = new int[r];

        // Print all combination using temprary array 'data[]'
        combinationUtil(arr, data, 0, n - 1, 0, r);
    }

    private static void combinationUtil(int arr[], int data[], int start, int end, int index, int r) {
        // Current combination is ready to be printed, print it
        if (index == r) {
            long sum = 1;

            for (int j = 0; j < r; j++) {
                sum *= data[j];
                sum %= modValue;
            }

            if (sum % modValue == targetValue) {
                winningHands++;
            }

            return;
        }

        // replace index with all possible elements. The condition
        // "end-i+1 >= r-index" makes sure that including one element
        // at index will make a combination with remaining elements
        // at remaining positions
        for (int i = start; i <= end && end - i + 1 >= r - index; i++) {
            data[index] = arr[i];
            combinationUtil(arr, data, i + 1, end, index + 1, r);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int mod = in.nextInt();
        int target = in.nextInt();
        int[] cards = new int[n];

        for(int i = 0; i < n; i++){
            cards[i] = in.nextInt();
        }

        int result = winningHands(mod, target, cards);
        System.out.println(result);
        in.close();
    }

}
