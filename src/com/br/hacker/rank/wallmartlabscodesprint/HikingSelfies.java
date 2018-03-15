package com.br.hacker.rank.wallmartlabscodesprint;

/**
 * Created by rene on 29/10/16.
 */

import java.util.Scanner;

/**
 * Emma has an old-fashioned camera with  frames of film in it. Emma wants to go hiking with  friends and,
 * during the hike, take exactly one selfie with every possible combination of her friends.

 Given  and , determine whether or not Emma has enough frames of film in her camera to take exactly
 one photo with every possible combination of her friends. If yes, print the number of extra frames of
 film she has after taking all the photos; if no, print the number of additional frames of film she needs to accomplish this task.

 Input Format

 The first line contains an integer, , denoting the number of Emma's friends going on the hike.
 The second line contains an integer, , denoting the number of frames of film in her camera.

 Constraints

 Output Format

 Print a single integer denoting the following:

 If Emma has enough film to get a photo of herself with all possible combinations of her  friends,
 print the number of extra (left over) frames of film she has in her camera after taking all the photos.
 If Emma does not have enough film, print the number of additional frames of film she will need to get a photo
 of herself with all possible combinations of her  friends.
 Sample Input 0

 3
 10
 Sample Output 0

 3

 Explanation 0

 Let's say Emma's friends are Alice, Bob, and Dan. Emma can take photos with the following combinations of friends:

 Because there are  combinations of people and Emma has  frames of film in her camera, she will have  frames of film left over.
 Thus, we print  as our answer.

 Sample Input 1

 2
 1
 Sample Output 1

 2

 Explanation 1

 Let's say Emma's friends are Alice and Bob. Emma can take photos with the following combinations of friends:

 Because there are  combinations of people but Emma only has  frame of film in her camera, she will need additional 2 frames
 of film to take a photo with all combinations of her friends. Thus, we print  as our answer.

 https://www.hackerrank.com/contests/walmart-codesprint-algo/challenges/emma-and-her-camera
 */
public class HikingSelfies {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int numberOfFriends = scanner.nextInt();
        int framesOfFile = scanner.nextInt();

        int result = computeNumberOfFramesRequiredOrLeftover(numberOfFriends, framesOfFile);

        System.out.println(result);
    }

    //Total combinations = N! / R! (N - R)!
    //We calculate Total combinations (N) + Total combinations (N - 1) + ... + Total combinations (2)
    private static int computeNumberOfFramesRequiredOrLeftover(int numberOfFrieds, int framesOfFile) {

        int totalCombinations = 0;

        int count = numberOfFrieds;
        int nFactorial = 1;
        int rFactorial;
        int nMinusRFactorial;

        int[] factorials = new int[numberOfFrieds + 1];

        while(count > 0) {
            nFactorial *= count;
            count--;
        }

        factorials[numberOfFrieds] = nFactorial;

        int totalFactorials = numberOfFrieds;

        while(totalFactorials >= 1) {
            count = totalFactorials;
            rFactorial = 1;

            if (factorials[count] != 0) {
                rFactorial = factorials[count];
            } else {
                while(count > 0) {
                    rFactorial *= count;
                    count--;
                }

                factorials[count] = rFactorial;
            }

            nMinusRFactorial = 1;
            int countNMinusR = numberOfFrieds - totalFactorials;
            if (factorials[countNMinusR] != 0) {
                nMinusRFactorial = factorials[countNMinusR];
            } else {
                while(countNMinusR > 0) {
                    nMinusRFactorial *= countNMinusR;
                    countNMinusR--;
                }

                factorials[countNMinusR] = nMinusRFactorial;
            }

            totalCombinations += nFactorial / (rFactorial * nMinusRFactorial);
            totalFactorials--;
        }

        int framesLeft = framesOfFile - totalCombinations;

        return Math.abs(framesLeft);
    }

}
