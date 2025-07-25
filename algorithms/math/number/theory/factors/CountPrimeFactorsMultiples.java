package algorithms.math.number.theory.factors;

/**
 * Created by Rene Argento on 24/07/25.
 */
// Algorithm to count the number of prime factors of a range of numbers.
// O(n * log(log(n)))
public class CountPrimeFactorsMultiples {

    public static void main(String[] args) {
        int[] countPFs = eratosthenesSieveCountPFs(10000000);

        int number1 = 1342324;
        System.out.println("Prime factors count: " + countPFs[number1]);
        System.out.println("Expected: 4\n");

        int number2 = 2112483;
        System.out.println("Prime factors count: " + countPFs[number2]);
        System.out.println("Expected: 2");
    }

    private static int[] eratosthenesSieveCountPFs(int maxNumber) {
        int[] primeFactorsCount = new int[maxNumber];
        int[] currentNumber = new int[maxNumber];
        for (int i = 0; i < currentNumber.length; i++) {
            currentNumber[i] = i;
        }

        for (int i = 2; i < primeFactorsCount.length; i++) {
            if (primeFactorsCount[i] == 0) {
                for (int j = i; j < primeFactorsCount.length; j += i) {
                    while (currentNumber[j] % i == 0) {
                        primeFactorsCount[j]++;
                        currentNumber[j] /= i;
                    }
                }
            }
        }
        return primeFactorsCount;
    }
}
