package com.br.training.usp.winterschool2017.campday2;

import java.util.Scanner;

/**
 * Created by rene on 11/07/17.
 */
// Subsequence sum = (2^n-1 -1) x SUM(all elements)
//Based on http://www.geeksforgeeks.org/find-sum-sum-sub-sequences/
public class Sumsub {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        long modValue = 1000000007;
        int elements = scanner.nextInt();

        long sum = 0;

        for(int i = 0; i < elements; i++) {
            sum += scanner.nextInt();
            sum = sum % modValue;
        }

        long power2 = 1;

        for(int i = 0; i < elements - 1; i++) {
            power2 = power2 * 2;
            power2 = power2 % modValue;
        }

        //We don't count the sequence with all elements
        power2 = power2 - 1;

        long sumsub = (power2 * sum) % modValue;

        System.out.println(sumsub);
    }
}
