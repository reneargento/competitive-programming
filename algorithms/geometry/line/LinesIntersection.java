package algorithms.geometry.line;

import datastructures.geometry.Line;
import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 26/12/18.
 */
// If two lines are not parallel they will intercept at a point.
// This point can be computed by solving the following system of two linear algebraic equations with two unknowns:
// a1x + b1y + c1 = 0
// a2x + b2y + c2 = 0
public class LinesIntersection {

    private static final double EPS = 1E-9;

    // Returns the point of intersection between two lines or null if there is no intersection.
    public static Point intersection(Line line1, Line line2) {
        if (areLinesParallel(line1, line2)) {
            return null;
        }

        // Solve system of 2 linear algebraic equations with 2 unknowns
        double pointX = (line2.b * line1.c - line1.b * line2.c) / (line2.a * line1.b - line1.a * line2.b);
        double pointY;

        // Special case: test for vertical line to avoid division by zero
        if (Math.abs(line1.b) > EPS) {
            pointY = -(line1.a * pointX + line1.c);
        } else {
            pointY = -(line2.a * pointX + line2.c);
        }

        return new Point(pointX, pointY);
    }

    public static boolean areLinesParallel(Line line1, Line line2) {
        return Math.abs(line1.a - line2.a) < EPS
                && Math.abs(line1.b - line2.b) < EPS;
    }

}
