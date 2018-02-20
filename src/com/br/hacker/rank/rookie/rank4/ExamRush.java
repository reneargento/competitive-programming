package com.br.hacker.rank.rookie.rank4;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Rene Argento on 16/02/18.
 */
// https://www.hackerrank.com/contests/rookierank-4/challenges/exam-rush/problem
public class ExamRush {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int t = in.nextInt();
        int[] tm = new int[n];
        for(int tm_i = 0; tm_i < n; tm_i++){
            tm[tm_i] = in.nextInt();
        }
        int result = examRush(tm, t);
        System.out.println(result);
        in.close();
    }

    private static int examRush(int[] chapters, int time) {
        int chaptersRead = 0;

        Arrays.sort(chapters);

        for (int i = 0; i < chapters.length; i++) {
            if (time >= chapters[i]) {
                chaptersRead++;
            } else {
                break;
            }

            time -= chapters[i];
        }

        return chaptersRead;
    }

}
