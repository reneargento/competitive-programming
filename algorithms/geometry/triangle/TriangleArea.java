package algorithms.geometry.triangle;

/**
 * Created by Rene Argento on 27/12/18.
 */
public class TriangleArea {

    // Area = base * height / 2
    public static double triangleArea(double base, double height) {
        return base * height / 2.0;
    }

    // Heron formula
    // Area = sqrt(s * (s - a) * (s - b) * (s - c))
    // where s is the semiperimeter = (a + b + c) / 2
    public static double triangleArea(double edgeLength1, double edgeLength2, double edgeLength3) {
        double semiPerimeter = (edgeLength1 + edgeLength2 + edgeLength3) / 2;
        return Math.sqrt(semiPerimeter * (semiPerimeter - edgeLength1)
                * (semiPerimeter - edgeLength2)
                * (semiPerimeter - edgeLength3));
    }

    // Shoelace formula
    public static double triangleArea(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.abs((x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0);
    }
}
