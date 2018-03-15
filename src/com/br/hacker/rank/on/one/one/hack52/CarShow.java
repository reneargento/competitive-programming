package com.br.hacker.rank.on.one.one.hack52;

import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by rene on 03/12/17.
 */
public class CarShow {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int q = in.nextInt();
        int[] cars = new int[n];
        for(int i = 0; i < n; i++){
            cars[i] = in.nextInt();
        }

        for(int a0 = 0; a0 < q; a0++){
            int l = in.nextInt() - 1;
            int r = in.nextInt() - 1;
            long sum = 0;

            for(int car = l; car <= r; car++) {
                for(int car2 = car; car2 <= r; car2++) {
                    Set<Integer> combinations = new HashSet<>();
                    boolean validRange = true;

                    for (int car3 = car; car3 <= car2; car3++) {
                        if (combinations.contains(cars[car3])) {
                            validRange = false;
                            break;
                        }
                        combinations.add(cars[car3]);
                    }

                    if (validRange) {
                        sum++;
                    }
                }
            }

            System.out.println(sum);
        }
        in.close();
    }

}
