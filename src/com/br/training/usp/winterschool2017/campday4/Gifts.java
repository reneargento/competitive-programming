package com.br.training.usp.winterschool2017.campday4;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by rene on 13/07/17.
 */
public class Gifts {

    static long modValue = 1000000007;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int gifts = scanner.nextInt();
        long combinations = 1;
        int itemsUsed = 0;
        int[] boxes = new int[gifts];

        for(int i = 0; i < gifts; i++) {
            boxes[i] = scanner.nextInt();
        }

        Arrays.sort(boxes);

        for(int i = 0; i < boxes.length; i++) {
            long currentValue = boxes[i] - itemsUsed;

            if (currentValue <= 0) {
                combinations = 0;
                break;
            }

            itemsUsed++;
            combinations *= currentValue;
            combinations = combinations % modValue;
        }

        System.out.println(combinations);
    }

}
