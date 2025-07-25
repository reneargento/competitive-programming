package algorithms.math.number.theory.factors;

import java.util.Arrays;

/**
 * Created by Rene Argento on 25/07/25.
 */
// Algorithm to sum the factors of a range of numbers.
// O(n * log(log(n)))
public class SumFactorsMultiples {

    public static void main(String[] args) {
        long[] factorsSum = eratosthenesSieveSumFactors(10000000);

        int number1 = 1342324;
        System.out.println("Factors sum: " + factorsSum[number1]);
        System.out.println("Expected: 2381764\n");

        int number2 = 2112483;
        System.out.println("Factors sum: " + factorsSum[number2]);
        System.out.println("Expected: 2816648");
    }

    private static long[] eratosthenesSieveSumFactors(int maxNumber) {
        long[] factorsSum = new long[maxNumber];
        Arrays.fill(factorsSum, 1);
        int[] currentNumber = new int[maxNumber];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i < factorsSum.length; i++) {
            if (factorsSum[i] == 1) {
                for (int j = i; j < factorsSum.length; j += i) {
                    long multiplier = i;
                    long total = 1;
                    while (currentNumber[j] % i == 0) {
                        currentNumber[j] /= i;
                        total += multiplier;
                        multiplier *= i;
                    }
                    factorsSum[j] *= total;
                }
            }
        }
        return factorsSum;
    }
}
