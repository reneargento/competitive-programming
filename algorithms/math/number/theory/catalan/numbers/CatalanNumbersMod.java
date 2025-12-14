package algorithms.math.number.theory.catalan.numbers;

/**
 * Created by Rene Argento on 27/10/25.
 */
// Computes the Catalan numbers mod a prime number P.
// P must be higher than MAX_N.
// Complexity: O(MAX_N * log(P))
public class CatalanNumbersMod {

    private static final int MAX_N = 100010;

    public static void main(String[] args) {
        int prime = 1000000007;
        long[] catalanNumbers = catalanNumbers(prime);
        System.out.println("C(100000): " + catalanNumbers[100000]);
        System.out.println("Expected:  945729344");
    }

    private static long[] catalanNumbers(int prime) {
        if (prime <= MAX_N) {
            throw new IllegalArgumentException("Prime number must be higher than " + MAX_N + " for this algorithm.");
        }
        long[] catalanNumbers = new long[MAX_N];
        catalanNumbers[0] = 1;

        for (int i = 0; i < catalanNumbers.length - 1; i++) {
            catalanNumbers[i + 1] = ((4 * i + 2) % prime
                    * catalanNumbers[i] % prime
                    * modPowFermat(i + 2, prime)) % prime;
        }
        return catalanNumbers;
    }

    private static long modPowFermat(long value, int prime) {
        return modPow(value, prime - 2, prime);
    }

    private static long modPow(long base, long power, long mod) {
        if (power == 0) {
            return 1;
        }
        long result = modPow(base, power / 2, mod);
        result = mod(result * result, mod);
        if (power % 2 == 1) {
            result = mod(result * base, mod);
        }
        return result;
    }

    private static long mod(long number, long mod) {
        return ((number % mod) + mod) % mod;
    }
}
