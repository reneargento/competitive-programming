package algorithms.geometry;

import algorithms.geometry.point.DistanceBetweenPoints;
import algorithms.geometry.vector.DotProduct;
import algorithms.geometry.vector.NormalizedSquare;
import datastructures.geometry.Point;
import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
// Computing the distance from a point to a line segment is almost the same as computing the distance from a point
// to a line. Except for 2 special cases - that require checking the two vertices in the line segment.
public class DistanceFromPointToLineSegment {

    // Returns the distance from point to the line segment defined by lineSegmentPoint1 and lineSegmentPoint2.
    // lineSegmentPoint1 and lineSegmentPoint2 can be equal.
    public static double distanceFromPointToLineSegment(Point point, Point lineSegmentPoint1, Point lineSegmentPoint2) {
        Vector point1ToPointVector = new Vector(lineSegmentPoint1, point);
        Vector point1ToPoint2Vector = new Vector(lineSegmentPoint1, lineSegmentPoint2);

        double valueToScale = DotProduct.dotProduct(point1ToPointVector, point1ToPoint2Vector)
                / NormalizedSquare.normalizedSquare(point1ToPoint2Vector);

        if (valueToScale < 0) {
            // Closer to lineSegmentPoint1
            return DistanceBetweenPoints.distanceBetweenPoints(point, lineSegmentPoint1);
        }
        if (valueToScale > 1) {
            // Closer to lineSegmentPoint2
            return DistanceBetweenPoints.distanceBetweenPoints(point, lineSegmentPoint2);
        }

        return DistanceFromPointToLine.distanceFromPointToLine(point, lineSegmentPoint1, lineSegmentPoint2);
    }

}
