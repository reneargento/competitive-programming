package algorithms.math.fractions;

import java.util.ArrayList;
import java.util.List;
import algorithms.math.fractions.FractionOperations.Fraction;

/**
 * Created by Rene Argento on 25/04/25.
 */
// Converts from fractions to continued fractions, and from continued fractions to fractions.
// Continued fractions (using partial quotients) are a way to represent fractions as a sum of an integer part and a
// reciprocal of another number recursively.
// For more details, check its definition here: https://open.kattis.com/problems/fraction
public class PartialQuotientsToFraction {

    private static class PartialQuotients {
        List<Long> values;

        public PartialQuotients(List<Long> values) {
            this.values = values;
        }
    }

    private static Fraction getFractionFromPartialQuotients(int[] partialQuotients) {
        long nominator = 1;
        long denominator = partialQuotients[partialQuotients.length - 1];
        Fraction result = new Fraction(denominator,1);

        for (int i = partialQuotients.length - 2; i >= 0; i--) {
            result = FractionOperations.sumFraction(partialQuotients[i], 1, nominator, denominator);
            nominator = result.denominator;
            denominator = result.nominator;
        }
        return result;
    }

    private static PartialQuotients getPartialQuotientsFromFraction(Fraction fraction) {
        List<Long> values = new ArrayList<>();
        long number1 = fraction.nominator;
        long number2 = fraction.denominator;

        while (number2 > 0) {
            long partialQuotient = number1 / number2;
            values.add(partialQuotient);

            long temp = number2;
            number2 = number1 % number2;
            number1 = temp;
        }
        return new PartialQuotients(values);
    }
}
