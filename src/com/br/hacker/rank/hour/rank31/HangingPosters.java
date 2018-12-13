package com.br.hacker.rank.hour.rank31;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rene Argento on 02/12/18.
 */
// https://www.hackerrank.com/contests/hourrank-31/challenges/hanging-posters/problem
public class HangingPosters {

    private static int getMaxHeight(int arthurHeight, List<Integer> wallPoints, List<Integer> lengths) {
        double maxHeight = 0;

        for (int i = 0; i < wallPoints.size(); i++) {
            double wallPoint = wallPoints.get(i);
            double length = lengths.get(i);
            double posterBoltedPoint = Math.ceil(wallPoint - (length * 0.25));

            if (posterBoltedPoint > maxHeight) {
                maxHeight = posterBoltedPoint;
            }
        }

        if (maxHeight <= arthurHeight) {
            return 0;
        }

        return (int) maxHeight - arthurHeight;
    }

    public static void main(String[] args) {
        List<Integer> wallPoints1 = new ArrayList<>();
        wallPoints1.add(15);
        wallPoints1.add(11);
        wallPoints1.add(17);

        List<Integer> lengths1 = new ArrayList<>();
        lengths1.add(5);
        lengths1.add(1);
        lengths1.add(2);

        System.out.println(getMaxHeight(5, wallPoints1, lengths1) + " Expected: 12");

        List<Integer> wallPoints2 = new ArrayList<>();
        wallPoints2.add(5);
        wallPoints2.add(5);

        List<Integer> lengths2 = new ArrayList<>();
        lengths2.add(4);
        lengths2.add(4);

        System.out.println(getMaxHeight(5, wallPoints2, lengths2) + " Expected: 0");
    }

}
