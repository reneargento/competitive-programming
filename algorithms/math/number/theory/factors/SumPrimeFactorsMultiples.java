package algorithms.math.number.theory.factors;

/**
 * Created by Rene Argento on 25/07/25.
 */
// Algorithm to sum the prime factors of a range of numbers.
// O(n * log(log(n)))
public class SumPrimeFactorsMultiples {

    public static void main(String[] args) {
        int[] sumPFs = eratosthenesSieveSumPFs(10000000);

        int number1 = 1342324;
        System.out.println("Prime factors sum: " + sumPFs[number1]);
        System.out.println("Expected: 4674\n");

        int number2 = 2112483;
        System.out.println("Prime factors sum: " + sumPFs[number2]);
        System.out.println("Expected: 704164");
    }

    private static int[] eratosthenesSieveSumPFs(int maxNumber) {
        int[] primeFactorsSum = new int[maxNumber];
        int[] currentNumber = new int[maxNumber];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i < currentNumber.length; i++) {
            if (primeFactorsSum[i] == 0) {
                for (int j = i; j < currentNumber.length; j += i) {
                    while (currentNumber[j] % i == 0) {
                        primeFactorsSum[j] += i;
                        currentNumber[j] /= i;
                    }
                }
            }
        }
        return primeFactorsSum;
    }
}
