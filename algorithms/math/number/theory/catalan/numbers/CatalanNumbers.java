package algorithms.math.number.theory.catalan.numbers;

import java.math.BigInteger;

/**
 * Created by Rene Argento on 27/10/25.
 */
// Computes the Catalan numbers.
// Complexity: O(N)
public class CatalanNumbers {

    public static void main(String[] args) {
        BigInteger[] catalanNumbers = catalanNumbers();
        System.out.println("C(32):    " + catalanNumbers[32]);
        System.out.println("Expected: 55534064877048198");
    }

    private static BigInteger[] catalanNumbers() {
        BigInteger[] catalanNumbers = new BigInteger[40];
        catalanNumbers[0] = BigInteger.ONE;

        for (int i = 0; i < catalanNumbers.length - 1; i++) {
            catalanNumbers[i + 1] = catalanNumbers[i]
                    .multiply(BigInteger.valueOf(4 * i + 2))
                    .divide(BigInteger.valueOf(i + 2));
        }
        return catalanNumbers;
    }
}
