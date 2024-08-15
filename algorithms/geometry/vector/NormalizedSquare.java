package algorithms.geometry.vector;

import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class NormalizedSquare {

    public static double normalizedSquare(Vector vector) {
        return vector.x * vector.x + vector.y * vector.y;
    }
}
