package com.br.algs.reference.algorithms;

/**
 * Created by rene on 18/08/17.
 */
public class Geometry {

    private static boolean isPointInsideTriangle(int pointInsideX, int pointInsideY, int point1X, int point1Y,
                                                 int point2X, int point2Y, int point3X, int point3Y) {
        double targetArea = getTriangleArea(point1X, point1Y, point2X, point2Y, point3X, point3Y);

        /* Calculate area of triangle PBC */
        double area1 = getTriangleArea(pointInsideX, pointInsideY, point2X, point2Y, point3X, point3Y);

        /* Calculate area of triangle PAC */
        double area2 = getTriangleArea(point1X, point1Y, pointInsideX, pointInsideY, point3X, point3Y);

        /* Calculate area of triangle PAB */
        double area3 = getTriangleArea(point1X, point1Y, point2X, point2Y, pointInsideX, pointInsideY);

        /* Check if sum of area1, area2 and area3 is same as targetArea */
        return (targetArea == area1 + area2 + area3);
    }

    private static double getTriangleArea(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3-  y1) + x3 * (y1 - y2)) / 2.0);
    }

    //Based on https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
    //Line given as 2 points
    private static double distanceFromPointToLine(int px, int py, int x1, int y1, int x2, int y2) {
        double numerator = Math.abs( (y2 - y1) * px
                - (x2 - x1) * py
                + x2 * y1
                - y2 * x1 );
        double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        return numerator / denominator;
    }

    //Based on https://www.mathsisfun.com/algebra/distance-2-points.html
    private static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

}
