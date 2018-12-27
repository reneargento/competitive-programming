package algorithms.geometry.circle;

/**
 * Created by Rene Argento on 27/12/18.
 */
public class ChordLength {

    // Chord length = sqrt(2 * r^2 * (1 - cos(a)))
    public static double chordLength(double angle, double radius) {
        return Math.sqrt(2 * radius * radius * (1 - Math.cos(angle)));
    }

}
