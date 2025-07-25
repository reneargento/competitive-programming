package algorithms.math.number.theory.factors;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 29/04/17.
 */
// O(sqrt(n) / ln(sqrt(n)))
public class PrimeFactors {

    public static void main(String[] args) {
        // Test
        List<Long> primeFactors = primeFactors(10);
        for(long primeFactor : primeFactors) {
            System.out.print(primeFactor + " ");
        }
    }

    private static List<Long> primeFactors(long number) {
        List<Long> primeFactors = new ArrayList<>();

        for (long i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactors.add(i);
                number /= i;
            }
        }

        // Special case where number is a prime number
        if (number > 1) {
            primeFactors.add(number);
        }
        return primeFactors;
    }
}
