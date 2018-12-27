package algorithms.geometry.circle;

import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class IsPointInCircumference {

    public static boolean isPointInCircumference(Point point, Point center, int radius) {
        return Math.pow(point.x - center.x, 2) + Math.pow(point.y - center.y, 2) == Math.pow(radius, 2);
    }

}
