package algorithms.geometry.line;

import datastructures.geometry.Line;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class AreLinesParallel {

    private static final double EPS = 1E-9;

    // Lines are parallel if their a and b coefficients are the same
    public static boolean areLinesParallel(Line line1, Line line2) {
        return Math.abs(line1.a - line2.a) < EPS
                && Math.abs(line1.b - line2.b) < EPS;
    }

}
