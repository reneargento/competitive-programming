package algorithms.geometry.circle;

import algorithms.geometry.triangle.TriangleArea;

/**
 * Created by Rene Argento on 27/12/18.
 */
public class SegmentArea {

    // Segment area = sector area - T area
    // where T is the triangle composed by 2 radius line segments and the chord length
    public static double segmentArea(double angle, double radius) {
        double sectorArea = SectorArea.sectorArea(angle, radius);
        double chordLength = ChordLength.chordLength(angle, radius);
        double triangleArea = TriangleArea.triangleArea(chordLength, radius, radius);

        return sectorArea - triangleArea;
    }

}
