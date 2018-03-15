package com.br.training.usp.winterschool2017.campday4;

import java.util.Scanner;

/**
 * Created by rene.argento on 18/07/17.
 */
public class BreakIt {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for(int i = 0; i < tests; i++) {
            int width = scanner.nextInt();
            int height = scanner.nextInt();
            int targetChocolate = scanner.nextInt();

            System.out.println(divideChocolate(width, height, targetChocolate));
        }
    }

    private static int divideChocolate(int width, int height, int targetChocolate) {
        if (targetChocolate == width * height) {
            return 0;
        }

        if (targetChocolate % width == 0 || targetChocolate % height == 0) {
            return 1;
        }

        //If we can get it with 2 breaks
        //   ____
        //  |   _|
        //  |  | |   h
        //  |  | |h'
        //  |__|_|
        //      w'
        //     w
        //
        //targetChocolate = widthPrime * heightPrime
        //OR
        //targetChocolate = (width * height) - (widthPrime * heightPrime)

        //Isolating heightPrime
        //heightPrime = targetChocolate / widthPrime
        //OR
        //(widthPrime * heightPrime) = (width * height) - targetChocolate
        //heightPrime = ((width * height) - targetChocolate) / widthPrime;

        //If width * height is at most 10^12, min(width,height) must be at most 10^6
        //Try all values of min(width, height) from 1 to 10^6

        if (width <= height) {
            for(double widthPrime = 1; widthPrime <= 1000000; widthPrime++) {
                if (widthPrime > width) {
                    break;
                }

                double heightPrime1 = targetChocolate / widthPrime;
                double heightPrime2 = ((width * height) - targetChocolate) / widthPrime;

                if ((heightPrime1 <= height && heightPrime1 == (long) heightPrime1)
                        || (heightPrime2 <= height && heightPrime2 == (long) heightPrime2)) {
                    return 2;
                }
            }
        } else {
            for(double heightPrime = 1; heightPrime <= 1000000; heightPrime++) {
                if (heightPrime > height) {
                    break;
                }

                double widthPrime1 = targetChocolate / heightPrime;
                double widthPrime2 = ((width * height) - targetChocolate) / heightPrime;

                if ((widthPrime1 <= width && widthPrime1 == (long) widthPrime1)
                        || (widthPrime2 <= width && widthPrime2 == (long) widthPrime2)) {
                    return 2;
                }
            }
        }

        return 3;
    }

}