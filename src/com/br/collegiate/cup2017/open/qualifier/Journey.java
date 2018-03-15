package com.br.collegiate.cup2017.open.qualifier;

import java.util.Scanner;

/**
 * Created by rene on 26/05/17.
 */
//https://www.hackerearth.com/challenge/competitive/brasil-collegiate-cup-2017-open-qualifier/algorithm/beautiful-journey-1/
public class Journey {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int checkpoints = scanner.nextInt();
        int[] array = new int[checkpoints];

        long maxProduct = 0;

        long sum1 = 0;
        long sum2 = 0;

        for(int i = 0; i < array.length; i++) {
            array[i] = scanner.nextInt();
            sum2 += array[i];
        }

        long currentProduct;

        for(int i = 0; i < array.length; i++) {
            sum2 -= array[i];
            sum1 += array[i];

            currentProduct = sum1 * sum2;

            if (currentProduct > maxProduct) {
                maxProduct = currentProduct;
            }
        }

        System.out.println(maxProduct);
    }

}
