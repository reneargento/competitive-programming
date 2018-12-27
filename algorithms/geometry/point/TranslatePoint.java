package algorithms.geometry.point;

import datastructures.geometry.Point;
import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class TranslatePoint {

    public static Point translatePoint(Point point, Vector vector) {
        return new Point(point.x + vector.x, point.y + vector.y);
    }

}
