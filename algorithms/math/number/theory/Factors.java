package algorithms.math.number.theory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 03/06/17.
 */
// O(sqrt(n))
// Algorithm to get the factors of a number.
// This algorithm can also be used to count the factors of a number, but a faster version exists:
// check the class CountFactors
public class Factors {

    private static List<Long> getFactors(long number) {
        List<Long> factors = new ArrayList<>();
        int upperLimit = (int) Math.sqrt(number);

        for (int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factors.add((long) i);

                if (i != number / i) {
                    factors.add(number / i);
                }
            }
        }
        return factors;
    }
}
