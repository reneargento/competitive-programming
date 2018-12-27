package algorithms.geometry.vector;

import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class CrossProduct {

    public static double crossProduct(Vector vector1, Vector vector2) {
        return vector1.x * vector2.y - vector1.y * vector2.x;
    }

}
