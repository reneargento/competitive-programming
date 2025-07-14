package algorithms.math.number.theory;

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

            int power = 1;
            while (number % primeNumber == 0) {
                power++;
                number /= primeNumber;
            }
            factors *= power;
        }

        // Special case where number is a prime number
        if (number > 1) {
            factors *= 2;
        }
        return factors;
    }
}
