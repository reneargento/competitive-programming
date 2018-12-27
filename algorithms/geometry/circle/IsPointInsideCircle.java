package algorithms.geometry.circle;

import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class IsPointInsideCircle {

    // Returns 0 if point is inside the circle, 1 if it is on the border or 2 if is outside
    public static int isPointInsideCircle(Point point, Point center, int radius) {
        double xDistance = point.x - center.x;
        double yDistance = point.y - center.y;

        double distance = xDistance * xDistance + yDistance * yDistance;
        double radiusSquared = radius * radius;

        if (distance < radiusSquared) {
            return 0; // Inside
        }
        if (distance == radiusSquared) {
            return 1; // On the border / circumference
        }
        return 2; // Outside
    }

}
