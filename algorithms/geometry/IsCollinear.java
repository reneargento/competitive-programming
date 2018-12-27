package algorithms.geometry;

import algorithms.geometry.vector.CrossProduct;
import datastructures.geometry.Point;
import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class IsCollinear {

    private static final double EPS = 1E-9;

    // Returns true if point is on the same line as the line composed by points linePoint1 and linePoint2
    public static boolean isCollinear(Point point, Point linePoint1, Point linePoint2) {
        Vector pointToLinePoint1Vector = new Vector(point, linePoint1);
        Vector pointToLinePoint2Vector = new Vector(point, linePoint2);
        double crossProduct = CrossProduct.crossProduct(pointToLinePoint1Vector, pointToLinePoint2Vector);
        return Math.abs(crossProduct) < EPS;
    }

}
