package algorithms.geometry.point;

import algorithms.geometry.IsPointLeftToLine;

import java.awt.*;

/**
 * Created by rene on 17/11/17.
 */
public class IsPointInPolygon {

    private static boolean isPointInPolygon(int[] xCoordinates, int[] yCoordinates, Point point) {
        Polygon polygon = new Polygon(xCoordinates, yCoordinates, xCoordinates.length);
        return polygon.contains(point);
    }

    private static final double EPSILON = 1E-9;

    // Winding algorithm: compute the sum of all angles formed by the extremities of each polygon edge with the
    // queried point. If the sum is equal to 360 then the queried point is inside the polygon.
    // Assumption: first and last points are the same.
    private static boolean isPointInPolygon2(datastructures.geometry.Point point,
                                             datastructures.geometry.Point[] points) {
        if (points.length == 0) {
            return false;
        }

        double sum = 0;

        for (int i = 0; i < points.length - 1; i++) {
            double angle = AngleBetween3Points.angle(points[i], point, points[i + 1]);

            if (IsPointLeftToLine.isPointLeftToLine(point, points[i], points[i + 1])) {
                // Left turn
                sum += angle;
            } else {
                // Right turn
                sum -= angle;
            }
        }

        return Math.abs(Math.abs(sum) - 2 * Math.PI) < EPSILON;
    }

}
