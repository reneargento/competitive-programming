package com.br.hacker.rank.hour.rank25;

import java.util.Scanner;

/**
 * Created by Rene Argento on 02/01/18.
 */
// https://www.hackerrank.com/contests/hourrank-25/challenges/constructing-a-number
public class ConstructingANumber {

    private static String canConstruct(int[] numbers) {
        long numberSum = 0;

        for(int number : numbers) {
            while (number > 0) {
                numberSum += number % 10;
                number /= 10;
            }

        }

        if (numberSum % 3 == 0) {
            return "Yes";
        } else {
            return "No";
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int queries = in.nextInt();

        for(int query = 0; query < queries; query++){
            int totalNumbers = in.nextInt();

            int[] numbers = new int[totalNumbers];

            for(int number = 0; number < totalNumbers; number++){
                numbers[number] = in.nextInt();
            }

            String result = canConstruct(numbers);
            System.out.println(result);
        }

        in.close();
    }

}
