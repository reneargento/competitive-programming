package algorithms.geometry.line;

/**
 * Created by Rene Argento on 13/03/20.
 */
// Gets the point of intersection between two line segments (if it exists)
public class LineSegmentIntersection {

    public class Point {
        public double x, y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    public class Line {
        public Point start, end;
        public double slope, yIntercept;

        public Line(Point start, Point end) {
            this.start = start;
            this.end = end;

            if (start.x == end.x) { // Vertical line
                slope = Double.POSITIVE_INFINITY;
                yIntercept = Double.POSITIVE_INFINITY;
            } else {
                slope = (end.y - start.y) / (end.x - start.x);
                yIntercept = end.y - slope * end.x;
            }
        }

        public boolean isVertical() {
            return slope == Double.POSITIVE_INFINITY;
        }

        public double getYIntersectionFromXIntersection(double x) {
            if (isVertical()) {
                return Double.POSITIVE_INFINITY;
            }
            return slope * x + yIntercept;
        }
    }

    // Checks if middle is between start and end
    private boolean isBetween(double start, double middle, double end) {
        if (start > end) {
            return end <= middle && middle <= start;
        } else {
            return start <= middle && middle <= end;
        }
    }

    private boolean isBetween(Point start, Point middle, Point end) {
        return isBetween(start.x, middle.x, end.x) && isBetween(start.y, middle.y, end.y);
    }

    // O(1) runtime
    // O(1) space
    public Point intersection(Point start1, Point end1, Point start2, Point end2) {
        Line line1 = new Line(start1, end1);
        Line line2 = new Line(start2, end2);

        // Parallel lines only intercept if they have the same y-intercept (are the same line) and if the start or
        // the end of one line is within the other line.
        if (line1.slope == line2.slope) { // Parallel lines
            if (line1.yIntercept != line2.yIntercept) return null;

            if (isBetween(start1, start2, end1)) return start2;
            if (isBetween(start1, end2, end1)) return end2;
            if (isBetween(start2, start1, end2)) return start1;
            if (isBetween(start2, end1, end2)) return end1;
            return null;
        }

        // Compute intersection point for infinite lines
        double xIntersection;
        if (line1.isVertical() || line2.isVertical()) {
            // A vertical line is not described by the y = m * x + b equation, it is described by the x = x1 equation
            xIntersection = line1.isVertical() ? line1.start.x : line2.start.x;
        } else { // Set y = m * x + b equations equal and solve for x (where m is the slope and b is the y-intercept)
            xIntersection = (line2.yIntercept - line1.yIntercept) / (line1.slope - line2.slope);
        }
        // Compute y intersection point using a non-vertical line
        double yIntersection = line1.isVertical() ? line2.getYIntersectionFromXIntersection(xIntersection) :
                line1.getYIntersectionFromXIntersection(xIntersection);
        Point intersection = new Point(xIntersection, yIntersection);

        // Check if intersection point is within both line segments
        if (isBetween(start1, intersection, end1) && isBetween(start2, intersection, end2)) {
            return intersection;
        }
        return null;
    }

}
