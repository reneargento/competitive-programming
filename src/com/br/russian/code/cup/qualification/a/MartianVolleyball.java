package com.br.russian.code.cup.qualification.a;

/**
 * Created by rene on 02/04/17.
 */

import java.util.Scanner;

/**
 * Volleyball match at Mars is played by two teams until one of the teams gets k points,
 * with score at least 2 points more than the score of the other team.
 * For each ball played exactly one of the teams gets 1 point.

 Now the score of the first team if x, the score of the second team is y.
 What is the minimal number of balls that must be played until one of the teams wins the match?

 Input format
 Input contains several test cases. The first line contains integer t — the number of cases (1 ≤ t ≤ 5000).

 Each test case is described using one line that contains three integers separated by spaces: k, x and y (1 ≤ k ≤ 100; 0 ≤ x, y ≤ 100).

 It is guaranteed that the score can be obtained in a correct unfinished game.

 Output format
 For each test case print one line — the minimal number of balls to be played, until the match is finished.

 Input data
 3
 2 1 0
 3 4 3
 5 0 0

 Output data
 1
 1
 5
 */
public class MartianVolleyball {

    public static void main(String[] args) {
//        System.out.println(getNumberOfBalls(2, 1, 0) + " Expected: 1");
//        System.out.println(getNumberOfBalls(3, 4, 3) + " Expected: 1");
//        System.out.println(getNumberOfBalls(5, 0, 0) + " Expected: 5");

        Scanner sc = new Scanner(System.in);
        int numberOfTests = Integer.parseInt(sc.nextLine());

        for(int i = 0; i < numberOfTests; i++) {
            int minimumScoreToEndGame = sc.nextInt();
            int team1Score = sc.nextInt();
            int team2Score = sc.nextInt();

            System.out.println(getNumberOfBalls(minimumScoreToEndGame, team1Score, team2Score));
        }
    }

    private static int getNumberOfBalls(int minimumScoreToEndGame, int team1Score, int team2Score) {
        int maxScore = Math.max(team1Score, team2Score);

        if (minimumScoreToEndGame - maxScore >= 2) {
            return minimumScoreToEndGame - maxScore;
        } else {
            int minScore = Math.min(team1Score, team2Score);
            int scoreDifferenceBetweenTeams = maxScore - minScore;
            if (scoreDifferenceBetweenTeams == 0) {
                return 2;
            } else {
                return 1;
            }
        }
    }
}
