package algorithms.geometry.vector;

import datastructures.geometry.Vector;

/**
 * Created by Rene Argento on 26/12/18.
 */
public class ScaleVector {

    public static Vector scale(Vector vector, double size) {
        return new Vector(vector.x * size, vector.y * size);
    }

}
