package algorithms.geometry.polygon;

import java.awt.*;
import java.awt.geom.Area;

/**
 * Created by Rene Argento on 17/11/17.
 */
public class DoPolygonsIntersect {

    private static boolean doPolygonsIntersect(Polygon polygon1, Polygon polygon2) {
        Area polygon1Area = new Area(polygon1);
        Area polygon2Area = new Area(polygon2);

        // Sets the shape of polygon1Area equal to its intersection with polygon2Area
        polygon1Area.intersect(polygon2Area);

        return !polygon1Area.isEmpty();
    }

}
