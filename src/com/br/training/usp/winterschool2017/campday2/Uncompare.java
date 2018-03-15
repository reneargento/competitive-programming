package com.br.training.usp.winterschool2017.campday2;

import java.util.*;

/**
 * Created by rene on 11/07/17.
 */
public class Uncompare {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.next();

        int maxValue = line.length() + 1;

        Stack<Integer> stack = new Stack<>();
        Stack<Integer> aux = new Stack<>();

        for(int i = maxValue; i >= 1; i--) {
            stack.push(i);
        }

        for(int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '<' ) {
                System.out.print(stack.pop());

                if (!stack.isEmpty()) {
                    System.out.print(" ");
                }
            } else {
                int count = 0;
                int currentLineIndex = i;
                while (stack.size() > 0 && currentLineIndex < line.length() && line.charAt(currentLineIndex) == '>') {
                    count++;
                    aux.push(stack.pop());
                    currentLineIndex++;
                }

                System.out.print(stack.pop());

                if (!stack.isEmpty() || !aux.isEmpty()) {
                    System.out.print(" ");
                }

                while (count > 0) {
                    System.out.print(aux.pop());

                    if (!aux.isEmpty()) {
                        System.out.print(" ");
                    }

                    count--;
                    i++;
                }

                if (!stack.isEmpty()) {
                    System.out.print(" ");
                }
            }
        }

        if (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }

        System.out.println();
    }

}
