package com.br.hacker.rank.rookie.rank4;

import java.util.Scanner;

/**
 * Created by Rene Argento on 16/02/18.
 */
// https://www.hackerrank.com/contests/rookierank-4/challenges/server-room-safety
public class ServerRoomSafety {

    static String checkAll(int racks, int[] height, int[] position) {
        boolean leftUnsafe = true;
        boolean rightUnsafe = true;

        int range = position[0] + height[0];
        int j = 0;

        // Check left
        for (int i = 0; i < position.length - 1; i++) {

            range = Math.max(range, position[i] + height[i]);

            if (range < position[i + 1]) {
                leftUnsafe = false;
                break;
            } else {

                while (j < position.length - 1
                        && position[i] + height[i] >= position[j + 1]) {
                    range = Math.max(range, position[j] + height[j]);

                    if (range < position[j + 1]) {
                        leftUnsafe = false;
                        break;
                    }

                    j++;
                }

                if (!leftUnsafe) {
                    break;
                }
            }
        }

        range = position[position.length - 1] - height[position.length - 1];
        j = position.length - 1;

        // Check right
        for (int i = position.length - 1; i > 0; i--) {

            range = Math.min(range, position[i] - height[i]);

            if (range > position[i - 1]) {
                rightUnsafe = false;
                break;
            } else {

                while (j > 0
                        && position[i] - height[i] <= position[j - 1]) {
                    range = Math.min(range, position[j] - height[j]);

                    if (range > position[j - 1]) {
                        rightUnsafe = false;
                        break;
                    }

                    j--;
                }

                if (!rightUnsafe) {
                    break;
                }
            }
        }

        if (leftUnsafe && rightUnsafe) {
            return "BOTH";
        } else if (leftUnsafe) {
            return "LEFT";
        } else if (rightUnsafe) {
            return "RIGHT";
        } else {
            return "NONE";
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] position = new int[n];
        for(int position_i = 0; position_i < n; position_i++){
            position[position_i] = in.nextInt();
        }
        int[] height = new int[n];
        for(int height_i = 0; height_i < n; height_i++){
            height[height_i] = in.nextInt();
        }
        String ret = checkAll(n, height, position);
        System.out.println(ret);
        in.close();
    }

}
