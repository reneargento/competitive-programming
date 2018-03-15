package com.br.maratona.mineira.ano2017;

import java.util.Scanner;

/**
 * Created by rene on 27/05/17.
 */

/**
 * Ada is an investor in a very unstable and high-risk business: NlogNintendo shares.
 * However, because she has great sympathy for the beautiful-horizontina company, Ada continues to invest anyway.

 However, instability sometimes makes it difficult for her to do her long-term portfolio planning.
 In order to help her, she has hired you to make a program that predicts the value of NlogNintendo.
 Ada has registered that on D day, a stock NlogNintendo was worth I real.
 Besides, at the beginning of even days, the stock price rises X reais compared to the price at the end of the previous day.
 On odd days, the stock price already starts with a real X value below the value at the end of the day. And now?
 Can you help her know what the stock price will be in F days?

 Input

 The input is composed of a line containing 4 integers separated by space: D (1 ≤ D ≤ 365)
 (the day Ada registered the price of the NlogNintendo stock), I (1 ≤ I ≤ 1000) (the initial price recorded Of the stock),
 X (1 ≤ X ≤ 1000) (the daily stock price change) and F (1 ≤ F ≤ 365) (the number of days in the future that your program should predict the share price).

 Output
 The output should contain a line with a single integer: the expected price of the F share days after the day the initial price was recorded.
 */

//https://www.urionlinejudge.com.br/judge/en/challenges/view/266/1
public class Actions {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int currentDay = scanner.nextInt();
        int initialPrice = scanner.nextInt();
        int priceMovement = scanner.nextInt();
        int daysInTheFuture = scanner.nextInt();

        boolean isDayEven = currentDay % 2 == 0;
        boolean rise = !isDayEven;
        int stockPrice = initialPrice;

        for(int i = 0; i < daysInTheFuture; i++) {
            if (rise) {
                stockPrice += priceMovement;
            } else {
                stockPrice -= priceMovement;
            }

            rise = !rise;
        }

        System.out.println(stockPrice);
    }

}
