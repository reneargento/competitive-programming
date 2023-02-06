package algorithms.math.number.theory;

import java.util.TreeSet;

/**
 * Created by Rene Argento on 03/06/17.
 */
public class Factors {

    private static TreeSet<Long> getFactors(long number) {
        TreeSet<Long> factors = new TreeSet<>();
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
