package algorithms.geometry;

import algorithms.geometry.point.DistanceBetweenPoints;
import algorithms.geometry.point.TranslatePoint;
import algorithms.geometry.vector.DotProduct;
import algorithms.geometry.vector.NormalizedSquare;
import algorithms.geometry.vector.ScaleVector;
import datastructures.geometry.Point;
import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 18/08/17.
 */
public class DistanceFromPointToLine {

    // linePoint1 and linePoint2 must be different
    public static double distanceFromPointToLine(Point point, Point linePoint1, Point linePoint2) {
        // Formula: c = a + u * ab
        Vector point1ToPointVector = new Vector(linePoint1, point);
        Vector point1ToPoint2Vector = new Vector(linePoint1, linePoint2);

        double valueToScale = DotProduct.dotProduct(point1ToPointVector, point1ToPoint2Vector)
                / NormalizedSquare.normalizedSquare(point1ToPoint2Vector);
        Vector scaledVector = ScaleVector.scale(point1ToPoint2Vector, valueToScale);

        Point closestPointInLine = TranslatePoint.translatePoint(linePoint1, scaledVector);
        return DistanceBetweenPoints.distanceBetweenPoints(point, closestPointInLine);
    }

    // Method 2
    // Based on https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
    // Line given as 2 points
    public static double distanceFromPointToLine(int px, int py, int x1, int y1, int x2, int y2) {
        double numerator = Math.abs( (y2 - y1) * px
                - (x2 - x1) * py
                + x2 * y1
                - y2 * x1 );
        double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        return numerator / denominator;
    }
}
