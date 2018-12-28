package algorithms.geometry.triangle;

import algorithms.geometry.line.LineGivenTwoPoints;
import algorithms.geometry.line.LinesIntersection;
import algorithms.geometry.point.DistanceBetweenPoints;
import algorithms.geometry.point.TranslatePoint;
import algorithms.geometry.vector.ScaleVector;
import datastructures.geometry.Line;
import datastructures.geometry.Point;
import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 27/12/18.
 */
//
public class InscribedCircle {

    private static final double EPS = 1E-9;

    // Returns the radius of the inscribed circle given the 3 triangle edge lengths.
    // Inscribed circle radius = triangle area / triangle semiperimeter
    public static double radius(double edgeLength1, double edgeLength2, double edgeLength3) {
        return TriangleArea.triangleArea(edgeLength1, edgeLength2, edgeLength3)
                / (0.5 * TrianglePerimeter.trianglePerimeter(edgeLength1, edgeLength2, edgeLength3));
    }

    public static double radius(Point point1, Point point2, Point point3) {
        return radius(DistanceBetweenPoints.distanceBetweenPoints(point1, point2),
                DistanceBetweenPoints.distanceBetweenPoints(point2, point3),
                DistanceBetweenPoints.distanceBetweenPoints(point3, point1));
    }

    // The inscribed circle center is the point of intersection of the triangle's 3 bisectors.
    public static Point center(Point point1, Point point2, Point point3) {
        double radius = radius(point1, point2, point3);
        if (Math.abs(radius) < EPS) {
            // There is no circle center
            return null;
        }

        double ratio1 = DistanceBetweenPoints.distanceBetweenPoints(point1, point2)
                / DistanceBetweenPoints.distanceBetweenPoints(point1, point3);
        Vector point2ToPoint3Vector = new Vector(point2, point3);
        Vector scaledVector1 = ScaleVector.scale(point2ToPoint3Vector, ratio1 / (1 + ratio1));
        Point translatedPoint1 = TranslatePoint.translatePoint(point2, scaledVector1);
        Line line1 = LineGivenTwoPoints.pointsToLine(point1, translatedPoint1);

        double ratio2 = DistanceBetweenPoints.distanceBetweenPoints(point2, point1)
                / DistanceBetweenPoints.distanceBetweenPoints(point2, point3);
        Vector point1ToPoint3Vector = new Vector(point1, point3);
        Vector scaledVector2 = ScaleVector.scale(point1ToPoint3Vector, ratio2 / (1 + ratio2));
        Point translatedPoint2 = TranslatePoint.translatePoint(point1, scaledVector2);
        Line line2 = LineGivenTwoPoints.pointsToLine(point2, translatedPoint2);

        // Return the intersection of 2 bisectors
        return LinesIntersection.intersection(line1, line2);
    }

}
