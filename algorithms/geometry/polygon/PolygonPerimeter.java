package algorithms.geometry.polygon;

import algorithms.geometry.point.DistanceBetweenPoints;
import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 28/12/18.
 */
public class PolygonPerimeter {

    // Assumption: first and last points are the same.
    public static double perimeter(Point[] points) {
        double perimeter = 0;

        for (int i = 0; i < points.length - 1; i++) {
            perimeter += DistanceBetweenPoints.distanceBetweenPoints(points[i], points[i + 1]);
        }

        return perimeter;
    }

}
