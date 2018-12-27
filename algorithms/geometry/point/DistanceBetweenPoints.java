package algorithms.geometry.point;

import datastructures.geometry.Point;

/**
 * Created by rene on 17/11/17.
 */
public class DistanceBetweenPoints {

    //Based on https://www.mathsisfun.com/algebra/distance-2-points.html
    public static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double distanceBetweenPoints(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }
}
