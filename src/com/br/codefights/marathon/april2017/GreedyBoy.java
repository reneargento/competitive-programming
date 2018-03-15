package com.br.codefights.marathon.april2017;

/**
 * Created by rene on 29/04/17.
 */

/**
 * John wants to invite some friends to his birthday party.
 * He knows that each friend needs to eat exactly k portions of food in order to be happy.
 * They will still be hungry if they eat fewer than k portions, and they will get too full if they eat more than k.
 * John is going to buy some snacks (as many as necessary) and possibly one of the cakes from the local supermarket.
 * Each snack has 10 portions, and the ith cake from the store has cakes[i] portions.
 * It's guaranteed that all of the cakes have fewer than 10 portions.

 What is the smallest amount of friends that John can invite to his party, such that everyone is happy and no food is left over?
 Remember that John needs k portions for himself as well, and he wants at least one friend at his party.

 Given the value k and an array cakes that contains the amount of portions for each cake option,
 return the smallest number of friends John can invite.

 Example

 For k = 7 and cakes = [6, 8, 9], the output should be
 greedyBoy(k, cakes) = 3.

 The smallest number of people John can invite is 3.
 Including John, there are 4 people at the party who eat 28 portions in total.
 The 28 portions are divided among two snacks (10 portions in each) and one cake (8 portions).

 For k = 13 and cakes = [3], the output should be
 greedyBoy(k, cakes) = 9.

 The smallest number of people John can invite is 9.
 Including John, there are 10 people at the party who eat 130 portions (13 snacks and 0 cake).
 Note that since John wants to invite at least one friend, the output can't be 0.

 Input/Output

 [time limit] 3000ms (java)
 [input] integer k

 The number of food portions each guest needs in order to be happy.

 Guaranteed constraints:
 1 ≤ k ≤ 100.

 [input] array.integer cakes

 An array that contains the number of portions for each cake option.

 Guaranteed constraints:
 1 ≤ cakes.length ≤ 9,
 1 ≤ cakes[i] ≤ 9 .

 [output] integer

 The smallest number of people that John can invite to his party.
 */

public class GreedyBoy {

    public static void main(String[] args) {

        int k1 = 7;
        int[] cakes1 = {6, 8, 9};
        System.out.println(greedyBoy(k1, cakes1) + " Expected: 3");

        int k2 = 13;
        int[] cakes2 = {3};
        System.out.println(greedyBoy(k2, cakes2) + " Expected: 9");

        int k3 = 5;
        int[] cakes3 = {1, 2, 3, 4, 5};
        System.out.println(greedyBoy(k3, cakes3) + " Expected: 1");

        int k4 = 99;
        int[] cakes4 = {7, 2, 9, 3};
        System.out.println(greedyBoy(k4, cakes4) + " Expected: 2");

        int k5 = 62;
        int[] cakes5 = {1, 3, 5, 7, 8};
        System.out.println(greedyBoy(k5, cakes5) + " Expected: 3");

        int k6 = 1;
        int[] cakes6 = {9, 8};
        System.out.println(greedyBoy(k6, cakes6) + " Expected: 7");
    }

    private static int greedyBoy(int k, int[] cakes) {

        int initialPortions = k * 10;
        int portions = initialPortions;

        //Just one cake is enough
        for(int i = 0; i < cakes.length; i++) {
            if (cakes[i] % k == 0
                    && cakes[i] < portions
                    && cakes[i] >= k * 2) {
                portions = cakes[i];
            }
        }

        if (portions != initialPortions) {
            return (portions / k) - 1;
        }

        //Some snacks are required
        for(int i = 10; i < initialPortions; i += 10) {
            if (i % k == 0
                    && i < portions
                    && i >= k * 2) {
                portions = i;
            }
        }

        //Some snacks and a cake are required
        for(int i = 10; i < initialPortions; i += 10) {
            for(int j = 0; j < cakes.length; j++) {
                int possiblePortions = (i + cakes[j]) % k;

                if (possiblePortions == 0
                        && i + cakes[j] < portions
                        && i + cakes[j] >= k * 2) {
                    portions = i + cakes[j];
                }
            }

        }

        return (portions / k) - 1;
    }

    private static int greedyBoyOptimized(int k, int[] cakes) {
        int res = 2;

        while (true) {
            int total = res * k;
            if (total % 10 == 0)
                return res - 1;
            for (int cake : cakes) {
                if ((total - cake) % 10 == 0)
                    return res - 1;
            }
            ++res;
        }
    }

}
