package algorithms.geometry.triangle;

import datastructures.geometry.Point;

/**
 * Created by Rene Argento on 28/12/18.
 */
public class InscribedTriangle {

    // Returns the radius of the circle around a inscribed triangle, given the 3 triangle edge lengths.
    // Circle radius = (triangle edge 1 * triangle edge 2 * triangle edge 3) / (4 * triangle area)
    public static double radius(double edgeLength1, double edgeLength2, double edgeLength3) {
        double area = TriangleArea.triangleArea(edgeLength1, edgeLength2, edgeLength3);
        return (edgeLength1 * edgeLength2 * edgeLength3) / (4 * area);
    }

    // The center of a circle around a inscribed triangle is the point of intersection of the triangle's 3 medians.
    // That is also the centroid, which can be found with the averages of the 3 point coordinates.
    // Reference: https://www.geeksforgeeks.org/program-to-find-the-centroid-of-the-triangle/
    public static Point center(Point point1, Point point2, Point point3) {
        double centerX = (point1.x + point2.x + point3.x) / 3;
        double centerY = (point1.y + point2.y + point3.y) / 3;
        return new Point(centerX, centerY);
    }

}
