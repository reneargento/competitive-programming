package com.br.codefights.marathon.june2017;

/**
 * Created by rene on 24/06/17.
 */
public class BuildRectangle {

    public static void main(String[] args) {
        System.out.println(canBuildRectangle(12) + " Expected: true");
        System.out.println(canBuildRectangle(13) + " Expected: false");
        System.out.println(canBuildRectangle(1) + " Expected: false");
        System.out.println(canBuildRectangle(2) + " Expected: false");
        System.out.println(canBuildRectangle(4) + " Expected: false");
        System.out.println(canBuildRectangle(6) + " Expected: false");
        System.out.println(canBuildRectangle(7) + " Expected: true");
        System.out.println(canBuildRectangle(10) + " Expected: false");
        System.out.println(canBuildRectangle(24) + " Expected: true");
        System.out.println(canBuildRectangle(22) + " Expected: false");
        System.out.println(canBuildRectangle(23) + " Expected: true");
    }

    private static boolean canBuildRectangle(int n) {
        int sum = (n + 1) * n / 2;

        if (sum % 2 == 0 && n != 4 && n != 3) {
            return true;
        } else {
            return false;
        }
    }

}
