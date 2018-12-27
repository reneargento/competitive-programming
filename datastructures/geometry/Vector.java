package datastructures.geometry;

/**
 * Created by Rene Argento on 26/12/18.
 */
// A line segment is an interval of a line delimited by 2 points.
// A vector is a line segment from the origin until a point (x, y).
// It can also be defined by a module (length) and direction.
public class Vector {
    public double x;
    public double y;

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Point point1, Point point2) {
        x = point2.x - point1.x;
        y = point2.y - point1.y;
    }
}
