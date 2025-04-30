package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 17/11/24.
 */
// Solves the weighted geometric sum
// S(a, n) = (1 * a + 2 * a^2 + 3 * a^3 + ... + n * a^n) modulo 10^9 + 7 where 1 < a < 10 in O(lg n) time
public class WeightedGeometricSum {
    private static final int MOD = 1000000007;

    private static long computeWeightedSum(int a, int n) {
        if (a == 1) {
            // Special case: S(1, n) = n * (n + 1) / 2
            return ((long) n * (n + 1) / 2) % MOD;
        }

        // Compute a^n % MOD and (1 - a^n) % MOD
        long aPowN = fastExponentiation(a, n);
        long oneMinusAPowN = (1 - aPowN + MOD) % MOD;

        // Compute modular inverse of (1 - a) and (1 - a)^2
        long oneMinusA = (1 - a + MOD) % MOD;
        long oneMinusAInverse = modInverse(oneMinusA);
        long oneMinusAInverseSquared = (oneMinusAInverse * oneMinusAInverse) % MOD;

        // Compute the weighted sum formula
        long term1 = (a * oneMinusAPowN) % MOD;
        long term2 = (a * ((n * aPowN) % MOD * oneMinusA % MOD)) % MOD;

        long result = (term1 - term2 + MOD) % MOD;
        result = (result * oneMinusAInverseSquared) % MOD;
        return result;
    }

    private static long fastExponentiation(long base, int exponent) {
        if (exponent == 0) {
            return 1;
        }

        long baseSquared = base * base;
        long result = fastExponentiation(baseSquared % MOD, exponent / 2);
        if (exponent % 2 == 0) {
            return result;
        } else {
            return (base * result) % MOD;
        }
    }

    private static long modInverse(long x) {
        // Compute x^(mod - 2) % mod using Fermat's Little Theorem: x^-1 % MOD = x^(MOD - 2) % MOD when MOD is prime
        return fastExponentiation(x, MOD - 2);
    }

    public static void main(String[] args) {
        long result1 = computeWeightedSum(5, 3);
        System.out.println("S(5, 3) mod 10^9+7: " + result1 + " Expected: 430");

        long result2 = computeWeightedSum(6, 4);
        System.out.println("S(6, 4) mod 10^9+7: " + result2 + " Expected: 5910");
    }
}
