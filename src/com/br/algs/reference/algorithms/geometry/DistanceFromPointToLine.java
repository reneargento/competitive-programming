package com.br.algs.reference.algorithms.geometry;

import java.awt.*;

/**
 * Created by rene on 18/08/17.
 */
public class DistanceFromPointToLine {

    //Based on https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
    //Line given as 2 points
    private static double distanceFromPointToLine(int px, int py, int x1, int y1, int x2, int y2) {
        double numerator = Math.abs( (y2 - y1) * px
                - (x2 - x1) * py
                + x2 * y1
                - y2 * x1 );
        double denominator = Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        return numerator / denominator;
    }

}
