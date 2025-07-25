package algorithms.math.number.theory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 08/09/17.
 */
// Returns the number of co-primes to the number that are less than the number
// Example:
// 36 -> 12
// { 1, 5, 7, 11, 13, 17, 19, 23, 25, 29, 31, 35 }
// O(sqrt(n) / ln(sqrt(n)))
public class EulerPhi {

    public static void main(String[] args) {
        System.out.println("Result: " + eulerPhi(36));
        System.out.println("Expected: 12");
    }

    private static long eulerPhi(long number) {
        Integer[] primes = eratosthenesSieve(number);
        long eulerPhi = number;

        for (long prime : primes) {
            if (prime * prime > number) {
                break;
            }

            if (number % prime == 0) {
                eulerPhi -= eulerPhi / prime;
            }

            while (number % prime == 0) {
                number /= prime;
            }
        }

        if (number != 1) {
            eulerPhi -= eulerPhi / number; // last factor, the number is a prime
        }
        return eulerPhi;
    }

    private static Integer[] eratosthenesSieve(long number) {
        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[(int) number + 1];
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        for (long i = 2; i < isPrime.length; i++) {
            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
                primeNumbers.add((int) i);
            }
        }
        return primeNumbers.toArray(new Integer[0]);
    }
}
