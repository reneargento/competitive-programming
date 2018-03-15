package com.br.training.usp.winterschool2017.campday3;

import java.util.Scanner;
import java.util.Stack;

public class Posters {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int buildings = scanner.nextInt();
        long[] heights = new long[buildings];

        for(int i = 0; i < buildings; i++) {
            int width = scanner.nextInt();
            heights[i] = scanner.nextInt();
        }

        int count = 1;
        long lowest = -1;

        Stack<Long> stack = new Stack<>();

        for(int i = 0; i < heights.length - 1; i++) {

            if (heights[i] > heights[i + 1]) {
                while (!stack.isEmpty() && stack.peek() > heights[i + 1]) {
                    stack.pop();
                }

                if (stack.isEmpty() || (stack.peek() != heights[i + 1])) {
                    count++;
                }
            } else if (heights[i] < heights[i+1]) {
                count++;
                stack.push(heights[i]);
            }
        }

        System.out.println(count);
    }
}
