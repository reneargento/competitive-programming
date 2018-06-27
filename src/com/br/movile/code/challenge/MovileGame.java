package com.br.movile.code.challenge;

import java.util.Scanner;

public class MovileGame {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int arraySize = in.nextInt();
        int instructions = in.nextInt();

        long[] array = new long[arraySize];

        for(int instruction = 0; instruction < instructions; instruction++) {
            int left = in.nextInt() - 1;
            int right = in.nextInt() - 1;
            int value = in.nextInt();

            array[left] += value;

            if(right + 1 < arraySize) {
                array[right + 1] -= value;
            }
        }

        long maxValue = Long.MIN_VALUE;
        long prefixSumValue = 0;

        for(long value : array) {
            prefixSumValue += value;

            if(prefixSumValue > maxValue) {
                maxValue = prefixSumValue;
            }
        }
        System.out.println(maxValue);

        in.close();
    }

}
