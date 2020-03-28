package algorithms.geometry.square;

/**
 * Created by Rene Argento on 28/03/20.
 */
// Gets the line segment that cuts two squares in half.
// Assumes that the origin (0, 0) is in the upper left-hand corner.
public class SquareBisection {

    public static class Point {
        public double x;
        public double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    public static class Line {
        public Point start;
        public Point end;

        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;
        }

        public String toString() {
            return "Line from " + start + " to " + end;
        }
    }

    public static class Square {
        public double left;
        public double top;
        public double right;
        public double bottom;
        public double size;

        public Square(double left, double top, double size) {
            this.size = size;
            this.left = left;
            this.top = top;
            right = left + size;
            bottom = top + size;
        }

        public Point middle() {
            double middleX = (left + right) / 2.0;
            double middleY = (top + bottom) / 2.0;
            return new Point(middleX, middleY);
        }

        public String toString() {
            return "(" + left + ", " + top + ")|(" + right + ", " + bottom + ")";
        }
    }

    /* Computes the point where the line segment connecting middle1 and middle2 intercepts the edge of square 1.
     * That is, draw a line from middle2 to middle1, and continue it out until the edge of the square. */
    private static Point extend(Point middle1, Point middle2, double square1Size) {
        // Find to which direction the line middle2 -> middle1 goes
        int xDirection = middle2.x < middle1.x ? 1 : -1;
        int yDirection = middle2.y < middle1.y ? 1 : -1;

        /* If middle1 and middle2 have the same x value, then the slope calculation will throw a divide by 0 exception.
         * So, compute this edge case directly. */
        if (middle1.x == middle2.x) {
            return new Point(middle1.x, middle1.y + (square1Size / 2.0) * yDirection);
        }

        double slope = (middle1.y - middle2.y) / (middle1.x - middle2.x);
        double x;
        double y;

        /* If the slope is "steep" (higher than 1) then the end of the line segment will hit (square1Size / 2) units
         * away from the middle on the y axis. If the slope is "shallow" (lower than 1) the end of the line segment will
         * hit (square1Size / 2) units away from the middle on the x axis. */
        if (Math.abs(slope) == 1) {
            x = middle1.x + (square1Size / 2.0) * xDirection;
            y = middle1.y + (square1Size / 2.0) * yDirection;
        } else if (Math.abs(slope) < 1) { // Shallow slope
            x = middle1.x + (square1Size / 2.0) * xDirection;
            y = middle1.y + slope * (x - middle1.x);
        } else { // Steep slope
            y = middle1.y + (square1Size / 2.0) * yDirection;
            x = middle1.x + (y - middle1.y) / slope;
        }
        return new Point(x, y);
    }

    // O(1) runtime
    // O(1) space
    public static Line bisectSquares(Square square1, Square square2) {
        // Compute where a line between each middle would collide with the edges of the squares.
        Point point1 = extend(square1.middle(), square2.middle(), square1.size);
        Point point2 = extend(square1.middle(), square2.middle(), -1 * square1.size);
        Point point3 = extend(square2.middle(), square1.middle(), square2.size);
        Point point4 = extend(square2.middle(), square1.middle(), -1 * square2.size);

        /* Find which of the points above are the start and end of the line.
         * Start is the farthest left (with top most as a tie breaker) and end is the farthest right (with bottom most
         * as a tie breaker). */
        Point start = point1;
        Point end = point1;
        Point[] points = {point2, point3, point4};

        for (Point point : points) {
            if (point.x < start.x || (point.x == start.x && point.y < start.y)) {
                start = point;
            } else if (point.x > end.x || (point.x == end.x && point.y > end.y)) {
                end = point;
            }
        }
        return new Line(start, end);
    }

    public static void main(String[] args) {
        doTests();
    }

    private static boolean isApproximatelyEqual(double double1, double double2) {
        double epsilon = .001;
        return Math.abs(double1 - double2) < epsilon;
    }

    private static boolean isApproximatelyEqual(Point point1, Point point2) {
        return isApproximatelyEqual(point1.x, point2.x) && isApproximatelyEqual(point1.y, point2.y);
    }

    private static boolean doTest(Square square1, Square square2, Point expectedStart, Point expectedEnd) {
        Line line = bisectSquares(square1, square2);
        return (isApproximatelyEqual(line.start, expectedStart) && isApproximatelyEqual(line.end, expectedEnd))
                || (isApproximatelyEqual(line.start, expectedEnd) && isApproximatelyEqual(line.end, expectedStart));
    }

    private static void doTestFull(Square square1, Square square2, Point expectedStart, Point expectedEnd) {
        boolean testResult = doTest(square1, square2, expectedStart, expectedEnd)
                && doTest(square2, square1, expectedStart, expectedEnd);
        System.out.print("Test with squares " + square1 + " and " + square2 + ": ");
        if (testResult) {
            System.out.println("success");
        } else {
            System.out.println("failed");
        }
    }

    private static void doTests() {
        // Equal squares
        doTestFull(new Square(1, 1, 5), new Square(1, 1, 5), new Point(3.5, 1), new Point(3.5, 6));

        // Concentric squares
        doTestFull(new Square(1, 1, 5), new Square(2, 2, 3), new Point(3.5, 1), new Point(3.5, 6));

        // Partially overlapping squares -- side by side
        doTestFull(new Square(10, 10, 10), new Square(8, 10, 10), new Point(8, 15), new Point(20, 15));

        // Partially overlapping squares -- corners
        doTestFull(new Square(10, 10, 10), new Square(8, 7, 7), new Point(8.777777, 7), new Point(18.8888888, 20));

        // Partially overlapping squares -- on top of each other
        doTestFull(new Square(10, 10, 10), new Square(8, 7, 15), new Point(8, 22), new Point(23, 7));

        // Not overlapping squares -- side by side
        doTestFull(new Square(10, 10, 10), new Square(19, 25, 4), new Point(12.5, 10), new Point(22, 29));

        // Not overlapping squares -- on top of each other
        doTestFull(new Square(10, 10, 10), new Square(4, 4, 3), new Point(4, 4), new Point(20, 20));

        // Contained squares
        doTestFull(new Square(10, 10, 10), new Square(12, 14, 3), new Point(10, 16.66666), new Point(20, 13.333));
    }

}
