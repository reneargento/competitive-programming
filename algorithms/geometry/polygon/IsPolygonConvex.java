package algorithms.geometry.polygon;

import algorithms.geometry.IsPointLeftToLine;
import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 28/12/18.
 */
public class IsPolygonConvex {

    // Assumption 1: first and last points are the same.
    // Assumption 2: Points are ordered either clockwise or counter-clockwise.
    private static boolean isConvex(Point[] points) {
        if (points.length <= 3) {
            // A point (length = 2) or a line (length = 3) is not convex
            return false;
        }

        boolean isLeft = IsPointLeftToLine.isPointLeftToLine(points[0], points[1], points[2]);

        for (int i = 1; i < points.length - 1; i++) {
            int thirdPointIndex = i + 2;
            if (thirdPointIndex == points.length) {
                thirdPointIndex = 1;
            }

            if (IsPointLeftToLine.isPointLeftToLine(points[i], points[i + 1], points[thirdPointIndex]) != isLeft) {
                return false; // Concave polygon
            }
        }

        return true;
    }

}
