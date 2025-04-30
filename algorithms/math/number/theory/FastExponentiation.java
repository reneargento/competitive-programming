package algorithms.math.number.theory;

/**
 * Created by Rene Argento on 08/09/17.
 */
public class FastExponentiation {

    // Fast exponentiation in mod 10^9 + 7
    private static final long MOD = 1000000007;

    private static long fastExponentiation(long base, long exponent) {
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
}
