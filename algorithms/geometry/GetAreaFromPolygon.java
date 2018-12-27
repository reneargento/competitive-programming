package algorithms.geometry;

import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 17/11/17.
 */
public class GetAreaFromPolygon {

    // Make sure the first and last points are the same
    private static double area(Point[] points) {
        double result = 0.0, x1, y1, x2, y2;

        for (int i = 0; i < points.length - 1; i++) {
            x1 = points[i].x;
            x2 = points[i + 1].x;
            y1 = points[i].y;
            y2 = points[i + 1].y;

            result += (x1 * y2 - x2 * y1);
        }

        return Math.abs(result) / 2.0;
    }

}
