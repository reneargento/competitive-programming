package com.br.algs.reference.algorithms.geometry;

/**
 * Created by rene on 17/11/17.
 */
public class DistanceBetweenPoints {

    //Based on https://www.mathsisfun.com/algebra/distance-2-points.html
    private static double distanceBetweenPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

}
