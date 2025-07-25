package algorithms.math.number.theory.factors;

import java.util.Arrays;

/**
 * Created by Rene Argento on 25/07/25.
 */
// Algorithm to count the number of factors of a range of numbers.
// O(n * log(log(n)))
public class CountFactorsMultiples {

    public static void main(String[] args) {
        int[] factorsCount = eratosthenesSieveCountFactors(10000000);

        int number1 = 1342324;
        System.out.println("Factors count: " + factorsCount[number1]);
        System.out.println("Expected: 12\n");

        int number2 = 2112483;
        System.out.println("Factors count: " + factorsCount[number2]);
        System.out.println("Expected: 4");
    }

    private static int[] eratosthenesSieveCountFactors(int maxNumber) {
        int[] factorsCount = new int[maxNumber];
        Arrays.fill(factorsCount, 1);
        int[] currentNumber = new int[maxNumber];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i < factorsCount.length; i++) {
            if (factorsCount[i] == 1) {
                for (int j = i; j < factorsCount.length; j += i) {
                    int power = 0;
                    while (currentNumber[j] % i == 0) {
                        currentNumber[j] /= i;
                        power++;
                    }
                    factorsCount[j] *= power + 1;
                }
            }
        }
        return factorsCount;
    }
}
