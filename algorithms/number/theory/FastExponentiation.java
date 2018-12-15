package algorithms.number.theory;

/**
 * Created by rene on 08/09/17.
 */
public class FastExponentiation {

    //Fast exponentiation in mod 10^9 + 7
    private static long mod = 1000000007;

    private static long fastExponentiation(long base, int exponent) {
        if (exponent == 0) {
            return 1;
        }

        if (exponent == 1) {
            return base;
        }

        long baseSquared = base * base;

        if (exponent % 2 == 0) {
            return fastExponentiation(baseSquared % mod, exponent / 2);
        }

        if (exponent % 2 == 1) {
            return (base * fastExponentiation(baseSquared % mod, exponent / 2)) % mod;
        }

        return -1;
    }

}
