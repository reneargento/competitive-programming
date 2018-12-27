package algorithms.geometry.circle;

/**
 * Created by Rene Argento on 27/12/18.
 */
public class SectorArea {

    // Sector area = PI * r^2 * angle / 360
    public static double sectorArea(double angle, double radius) {
        return Math.PI * radius * radius * angle / 360.0;
    }

}
