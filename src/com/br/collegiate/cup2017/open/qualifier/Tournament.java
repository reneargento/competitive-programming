package com.br.collegiate.cup2017.open.qualifier;

import java.util.Scanner;

/**
 * Created by rene on 26/05/17.
 */
//https://www.hackerearth.com/challenge/competitive/brasil-collegiate-cup-2017-open-qualifier/algorithm/torneio-dos-torcedores/
public class Tournament {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int team1Ability = scanner.nextInt();
        int team1Fans = scanner.nextInt();
        int team2Ability = scanner.nextInt();
        int team2Fans = scanner.nextInt();
        int team3Ability = scanner.nextInt();
        int team3Fans = scanner.nextInt();
        int team4Ability = scanner.nextInt();
        int team4Fans = scanner.nextInt();

        int team1Performance = team1Ability + team1Fans;
        int team2Performance = team2Ability + team2Fans;
        int team3Performance = team3Ability + team3Fans;
        int team4Performance = team4Ability + team4Fans;

        int match1Winner = 0;
        int match2Winner = 0;
        int match1WinnerPerformance = 0;
        int match2WinnerPerformance = 0;

        int fansOfTeamAgainstMatch1Winner = 0;
        int fansOfTeamAgainstMatch2Winner = 0;

        //Match 1
        if (team1Performance >= team2Performance) {
            match1Winner = 1;
            match1WinnerPerformance = team1Performance;

            fansOfTeamAgainstMatch1Winner = team2Fans;
        } else {
            match1Winner = 2;
            match1WinnerPerformance = team2Performance;

            fansOfTeamAgainstMatch1Winner = team1Fans;
        }

        //Match 2
        if (team3Performance >= team4Performance) {
            match2Winner = 3;
            match2WinnerPerformance = team3Performance;

            fansOfTeamAgainstMatch2Winner = team4Fans;
        } else {
            match2Winner = 4;
            match2WinnerPerformance = team4Performance;

            fansOfTeamAgainstMatch2Winner = team3Fans;
        }

        int champion;
        match1WinnerPerformance += fansOfTeamAgainstMatch2Winner;
        match2WinnerPerformance += fansOfTeamAgainstMatch1Winner;

        if (match1WinnerPerformance >= match2WinnerPerformance) {
            champion = match1Winner;
        } else {
            champion = match2Winner;
        }

        System.out.println(champion);
    }

}
