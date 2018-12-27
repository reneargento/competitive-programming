package algorithms.geometry.circle;

/**
 * Created by Rene Argento on 27/12/18.
 */
public class ArcLength {

    // Arc length = 2 * PI * r * angle / 360
    public static double arcLength(double angle, double radius) {
        return 2 * Math.PI * radius * angle / 360.0;
    }

}
