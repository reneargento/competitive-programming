package algorithms.math;

/**
 * Created by Rene Argento on 01/11/23.
 */
public class CompoundInterest {

    private static double computeCompoundInterest(double principal, double interestRate,
                                                  int timesInterestCompoundPerYear, int years) {
        int iterations = timesInterestCompoundPerYear * years;
        return principal * Math.pow(1 + interestRate / timesInterestCompoundPerYear, iterations);
    }
}
