package algorithms.geometry.line;

import datastructures.geometry.Line;
import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 26/12/18.
 */
// Get the line that intercepts two given points.
public class LineGivenTwoPoints {

    private static final double EPS = Math.pow(10, -9);

    public static Line pointsToLine(Point point1, Point point2) {
        double a;
        double b;
        double c;

        if (Math.abs(point1.x - point2.x) < EPS) {
            // Vertical line
            a = 1;
            b = 0;
            c = -point1.x;
        } else {
            a = -(point1.y - point2.y) / (point1.x - point2.x);
            b = 1;
            c = -(a * point1.x) - point1.y;
        }

        return new Line(a, b, c);
    }

}
