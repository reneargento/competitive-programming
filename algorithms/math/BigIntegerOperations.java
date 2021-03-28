package algorithms.math;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Rene Argento on 16/03/21.
 */
public class BigIntegerOperations {

    // Returns the log base 2 rounded up
    private static int logBase2(BigDecimal number) {
        int log = 0;

        while (number.compareTo(BigDecimal.ONE) > 0) {
            log++;
            number = number.divide(BigDecimal.valueOf(2), RoundingMode.CEILING);
        }
        return log;
    }
}
