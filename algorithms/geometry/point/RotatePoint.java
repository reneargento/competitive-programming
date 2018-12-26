package algorithms.geometry.point;

import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class RotatePoint {

    // Rotate point by a specific amount of degrees counter-clockwise with relation to the origin (0, 0)
    public static Point rotatePoint(Point point, double degrees) {
        double radians = degreesToRadians(degrees);
        double rotatedX = point.x * Math.cos(radians) - point.y * Math.sin(radians);
        double rotatedY = point.x * Math.sin(radians) + point.y * Math.cos(radians);
        return new Point(rotatedX, rotatedY);
    }

    private static double degreesToRadians(double degrees) {
        return degrees * Math.PI / 180.0;
    }

}
