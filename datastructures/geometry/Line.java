package datastructures.geometry;

/**
 * Created by Rene Argento on 26/12/18.
 */
// A line is a set of points that satisfy the linear equation ax + by + c = 0
// Vertical lines have b = 0; otherwise b = 1
public class Line {
    double a;
    double b;
    double c;

    public Line (double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

}
