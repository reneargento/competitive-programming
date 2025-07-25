package algorithms.math.number.theory.factors;

import algorithms.math.number.theory.EratosthenesSieve;

/**
 * Created by Rene Argento on 18/07/25.
 */
// Algorithm to sum the factors (divisors) of a number.
// O(sqrt(n) / log(n))
public class SumFactors {

    public static void main(String[] args) {
        long maxPrime = (long) Math.sqrt(1000000000000L);
        Integer[] primeNumbers = EratosthenesSieve.eratosthenesSievePrimeList(maxPrime);

        long number1 = 982413;
        long factorsSum = sumFactors(primeNumbers, number1);
        System.out.println("Factors sum: " + factorsSum);
        System.out.println("Expected: 1502748");

        long number2 = 12312392191L;
        long factorsSum2 = sumFactors(primeNumbers, number2);
        System.out.println("\nFactors sum: " + factorsSum2);
        System.out.println("Expected: 13431700584");
    }

    private static long sumFactors(Integer[] primeNumbers, long number) {
        long factorsSum = 1;
        long numberCopy = number;

        for (long primeNumber : primeNumbers) {
            if (primeNumber * primeNumber > numberCopy) {
                break;
            }

            if (numberCopy % primeNumber != 0) {
                continue;
            }
            long power = primeNumber;
            while (numberCopy % primeNumber == 0) {
                numberCopy /= primeNumber;
                power *= primeNumber;
            }
            factorsSum *= (power - 1) / (primeNumber - 1);
        }
        if (numberCopy != 1) {
            factorsSum *= (numberCopy + 1);
        }
        return factorsSum;
    }
}
