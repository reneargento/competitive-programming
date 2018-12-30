package algorithms.geometry.polygon;

import algorithms.geometry.line.LineGivenTwoPoints;
import algorithms.geometry.line.LinesIntersection;
import algorithms.geometry.vector.CrossProduct;
import datastructures.geometry.Line;
import datastructures.geometry.Point;
import datastructures.geometry.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 28/12/18.
 */
public class CutPolygon {

    private static final double EPS = 1E-9;

    // Cuts polygon along the line formed by linePoint1 -> linePoint2
    // Assumption: first and last points are the same.
    List<Point> cutPolygon(Point[] polygon, Point linePoint1, Point linePoint2) {
        List<Point> resultingPolygon = new ArrayList<>();
        Vector lineVector = new Vector(linePoint1, linePoint2);

        for (int i = 0; i < polygon.length; i++) {
            double pointLeftOfLine1 = CrossProduct.crossProduct(lineVector, new Vector(linePoint1, polygon[i]));
            double pointLeftOfLine2 = 0;

            if (i != polygon.length - 1) {
                pointLeftOfLine2 = CrossProduct.crossProduct(lineVector, new Vector(linePoint1, polygon[i + 1]));
            }

            if (pointLeftOfLine1 > -EPS) {
                // polygon[i] is on the left of line
                resultingPolygon.add(polygon[i]);
            }
            if (pointLeftOfLine1 * pointLeftOfLine2 < -EPS) {
                // Edge (polygon[i], polygon[i + 1]) crosses line
                Line cuttingLine = LineGivenTwoPoints.pointsToLine(linePoint1, linePoint2);
                Line pointsLine = LineGivenTwoPoints.pointsToLine(polygon[i], polygon[i + 1]);
                Point intersectionPoint = LinesIntersection.intersection(cuttingLine, pointsLine);
                resultingPolygon.add(intersectionPoint);
            }
        }

        if (!resultingPolygon.isEmpty()
                && resultingPolygon.get(0) != resultingPolygon.get(resultingPolygon.size() - 1)) {
            // Make resulting polygon's first point = resulting polygon's last point
            resultingPolygon.add(resultingPolygon.get(0));
        }

        return resultingPolygon;
    }

}
