package algorithms.geometry.circle;

import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 27/12/18.
 */
// Based on http://mathforum.org/library/drmath/view/53027.html
// and https://stackoverflow.com/questions/4914098/centre-of-a-circle-that-intersects-two-points/4914148
public class CircleCenterFrom2PointsAndRadius {

    // There are 2 possible circle centers
    public static Point[] circleCenterFrom2PointsAndRadius(Point point1, Point point2, double radius) {
        double distanceBetweenPoints = Math.sqrt(Math.pow(point1.x - point2.x, 2)) + Math.pow(point1.y - point2.y, 2);

        // Point halfway between the two points
        double point3X = (point1.x + point2.x) / 2;
        double point3Y = (point1.y + point2.y) / 2;

        double baseX = Math.sqrt(Math.pow(radius, 2) - Math.pow(distanceBetweenPoints / 2, 2))
                * (point1.y - point2.y) / distanceBetweenPoints;
        double baseY = Math.sqrt(Math.pow(radius, 2) - Math.pow(distanceBetweenPoints / 2, 2))
                * (point2.x - point1.x) / distanceBetweenPoints;

        double center1X = point3X + baseX;
        double center1Y = point3Y + baseY;
        Point center1 = new Point(center1X, center1Y);

        double center2X = point3X - baseX;
        double center2Y = point3Y - baseY;
        Point center2 = new Point(center2X, center2Y);
        return new Point[]{center1, center2};
    }

}
