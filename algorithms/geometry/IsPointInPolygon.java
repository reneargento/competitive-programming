package algorithms.geometry;

import java.awt.*;

/**
 * Created by rene on 17/11/17.
 */
public class IsPointInPolygon {

    private static boolean isPointInPolygon(int[] xCoordinates, int[] yCoordinates, Point point) {
        Polygon polygon = new Polygon(xCoordinates, yCoordinates, xCoordinates.length);
        return polygon.contains(point);
    }

}
