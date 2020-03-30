package algorithms.geometry.line;

import datastructures.geometry.Line;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class AreLinesEquivalent {

    private static final double EPSILON = 1E-9;

    // Lines are equivalent if their a, b and c coefficients are the same
    public static boolean areLinesEquivalent(Line line1, Line line2) {
        return Math.abs(line1.a - line2.a) < EPSILON
                && Math.abs(line1.b - line2.b) < EPSILON
                && Math.abs(line1.c - line2.c) < EPSILON;
    }

}
