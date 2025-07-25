package algorithms.math.number.theory.factors;

import algorithms.math.number.theory.EratosthenesSieve;

/**
 * Created by Rene Argento on 14/07/25.
 */
// Algorithm to count the factors (divisors) of a number.
// O(sqrt(n) / log(n))
public class CountFactors {

    public static void main(String[] args) {
        long number = 18953220000L;
        long maxPrime = (long) Math.sqrt(number);

        Integer[] primeNumbers = EratosthenesSieve.eratosthenesSievePrimeList(maxPrime);
        int factorsCount = countFactors(primeNumbers, number);
        System.out.println("Factors count: " + factorsCount);
        System.out.println("Expected: 720");
    }

    private static int countFactors(Integer[] primeNumbers, long number) {
        int factors = 1;

        for (int primeNumber : primeNumbers) {
            int sqrtNumber = (int) Math.sqrt(number);
            if (primeNumber > sqrtNumber) {
                break;
            }

            int exponent = 1;
            while (number % primeNumber == 0) {
                exponent++;
                number /= primeNumber;
            }
            factors *= exponent;
        }

        // Special case where number is a prime number
        if (number > 1) {
            factors *= 2;
        }
        return factors;
    }
}
