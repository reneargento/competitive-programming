package com.br.training.unicamp.summer.school2018.contest1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rene on 22/01/18.
 */
// TODO http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/E
public class NumberPartitions {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int target = scanner.nextInt();
        List<Integer> fib = new ArrayList<>();

        for(int i = 0; i < target; i++){
            fib.add(0);
        }

        fib.set(0, target);

        System.out.println(target);

        while(fib.get(0) != 1) {

            for(int j = 0; j < target - 1 ; j++){
                if (fib.get(j) > fib.get(j + 1) + 1 ) {
                    fib.set(j, fib.get(j) - 1);
                    fib.set(j + 1, fib.get(j + 1) + 1);
                    break;
                } else if (fib.get(j) == 2 && fib.get(j + 1) == 1){
                    fib.set(j, fib.get(j) - 1);
                    fib.set(j + 1, fib.get(j + 1) + 1);
                }
            }

            for(int j = 0; j < target - 1; j++) {

                if (fib.get(j + 1) != 0 && j + 1 != target - 1)
                    System.out.print(fib.get(j) + "+");
                else {
                    System.out.println(fib.get(j));
                    break;
                }
            }
        }
    }
}
