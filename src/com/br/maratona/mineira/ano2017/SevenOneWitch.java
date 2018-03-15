package com.br.maratona.mineira.ano2017;

import java.util.Scanner;

/**
 * Created by rene on 27/05/17.
 */

/**
 * Dona Clotilde is a very nice lady who lives in a village, in house which number is 71.
 * It's not known why, but she had a reputation for being a witch.
 * Clotilde was eager to watch a football game.
 * Clotilde was eager to watch a football game.
 * One day, she bought a liquid to clean silver.
 * With this, she won a coupon that gave her a number to compete for a ticket to the 2014 World Cup semifinal at Mineirão,
 * the game between Germany and Brazil. The raffle happened and she won the ticket.
 * Clotilde went to the game, Brazil lost 7 x 1, and everyone in the village thought that Brazil had lost that way because of her, poor thing!
 * Her hacking nephew, San Tanaz, taking her aunt's pains, decided to create a computer virus that interfered with mathematical calculations, so that anything involving number 7 in the accounts would become 0.

 For example:
 3 + 4 = 0
 33 + 44 = 0
 17 + 11 = 21
 8 x 9 = 2
 12 x 7 = 0
 8 + 9 = 10

 Input
 Composed of a single line with two integers a and b (0 < a, b < 10000), separated by a sum or multiplication operator.

 Output
 An integer corresponding to the result of the account, after the virus.
 */

//https://www.urionlinejudge.com.br/judge/en/challenges/view/266/2
public class SevenOneWitch {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int number1 = scanner.nextInt();
        String operation = scanner.next();
        int number2 = scanner.nextInt();

        String num1String = String.valueOf(number1);
        String num2String = String.valueOf(number2);
        num1String = num1String.replace('7', '0');
        num2String = num2String.replace('7', '0');

        int result;
        if (operation.equals("+")) {
            result = Integer.parseInt(num1String) + Integer.parseInt(num2String);
        } else {
            result = Integer.parseInt(num1String) * Integer.parseInt(num2String);
        }

        String finalResult = String.valueOf(result).replace('7', '0');

        StringBuilder sb = new StringBuilder();
        boolean leadingZero = true;
        for(int i = 0; i < finalResult.length(); i++) {
            if (leadingZero && finalResult.charAt(i) == '0') {
                continue;
            } else {
                leadingZero = false;
                sb.append(finalResult.charAt(i));
            }
        }

        String resultAfterRemovingLeadingZeroes = sb.toString();
        if (resultAfterRemovingLeadingZeroes.equals("")) {
            resultAfterRemovingLeadingZeroes = "0";
        }

        System.out.println(resultAfterRemovingLeadingZeroes);
    }

}
