package com.br.uri.made.by.women2018;

import java.util.*;

/**
 * Created by Rene Argento on 10/03/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/346/3
public class ShoppingList {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < tests; i++) {
            String[] items = scanner.nextLine().split(" ");

            Set<String> set = new HashSet<>();
            set.addAll(Arrays.asList(items));

            String[] uniqueItems = new String[set.size()];
            set.toArray(uniqueItems);

            Arrays.sort(uniqueItems);

            StringBuilder list = new StringBuilder();
            for(int index = 0; index < uniqueItems.length; index++) {
                list.append(uniqueItems[index]);

                if (index != uniqueItems.length - 1) {
                    list.append(" ");
                }
            }

            System.out.println(list.toString());
        }

    }

}
