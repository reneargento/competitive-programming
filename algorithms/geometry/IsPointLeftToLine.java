package algorithms.geometry;

import algorithms.geometry.vector.CrossProduct;
import datastructures.geometry.Point;
import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class IsPointLeftToLine {

    // Returns true if point is on the left side of line composed by points linePoint1 and linePoint2
    // To also accept collinear points, change the > 0 to == 0
    public static boolean isPointLeftToLine(Point point, Point linePoint1, Point linePoint2) {
        Vector pointToLinePoint1Vector = new Vector(point, linePoint1);
        Vector pointToLinePoint2Vector = new Vector(point, linePoint2);
        return CrossProduct.crossProduct(pointToLinePoint1Vector, pointToLinePoint2Vector) > 0;
    }
}
