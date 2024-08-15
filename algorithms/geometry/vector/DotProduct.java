package algorithms.geometry.vector;

import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class DotProduct {

    public static double dotProduct(Vector vector1, Vector vector2) {
        return vector1.x * vector2.x + vector1.y * vector2.y;
    }
}
