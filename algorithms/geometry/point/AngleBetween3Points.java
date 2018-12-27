package algorithms.geometry.point;

import algorithms.geometry.vector.DotProduct;
import algorithms.geometry.vector.NormalizedSquare;
import datastructures.geometry.Point;
import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class AngleBetween3Points {

    // Returns the angle composed by point1 - point2 - point3 in radians
    public static double angle(Point point1, Point point2, Point point3) {
        Vector point2ToPoint1Vector = new Vector(point2, point1);
        Vector point2ToPoint3Vector = new Vector(point2, point3);

        double dotProduct = DotProduct.dotProduct(point2ToPoint1Vector, point2ToPoint3Vector);
        double normalizedSquareVector1 = NormalizedSquare.normalizedSquare(point2ToPoint1Vector);
        double normalizedSquareVector2 = NormalizedSquare.normalizedSquare(point2ToPoint3Vector);

        return Math.acos(dotProduct / Math.sqrt(normalizedSquareVector1 * normalizedSquareVector2));
    }

}
