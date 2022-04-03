package algorithms.geometry.rectangle;

/**
 * Created by Rene Argento on 13/02/22.
 */
// Checks if rectangles intersect.
// Rectangles are represented by (x, y), where x is the left position and y is the top position,
// and their width and height.
// This assumes that y coordinates increase downwards.
// Based on https://stackoverflow.com/questions/2752349/fast-rectangle-to-rectangle-intersection
public class DoRectanglesIntersect {

    private static class Rectangle {
        int x;
        int y;
        int width;
        int height;

        public Rectangle(int x, int y, int width, int height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }

    public static boolean intersect(Rectangle rectangle1, Rectangle rectangle2) {
        int rectangle1Y2 = rectangle1.y + rectangle1.height;
        int rectangle1X2 = rectangle1.x + rectangle1.width;

        int rectangle2Y2 = rectangle2.y + rectangle2.height;
        int rectangle2X2 = rectangle2.x + rectangle2.width;

        return (rectangle1.x <= rectangle2X2
                && rectangle2.x <= rectangle1X2
                && rectangle1.y <= rectangle2Y2
                && rectangle2.y <= rectangle1Y2);
    }
}
