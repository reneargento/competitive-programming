package com.br.hacker.rank.hour.rank24;

import java.util.*;

/**
 * Created by rene on 02/11/17.
 */
@SuppressWarnings("unchecked")
// https://www.hackerrank.com/contests/hourrank-24/challenges/wire-removal
//TODO
public class WireRemoval {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(
                System.in);
        int treeSize = scanner.nextInt();

        List<Integer>[] adjacent = (List<Integer>[]) new ArrayList[treeSize + 1];

        for(int i = 0; i < treeSize - 1; i++) {
            int node1 = scanner.nextInt();
            int node2 = scanner.nextInt();

            if (adjacent[node1] == null) {
                adjacent[node1] = new ArrayList<>();
            }
            if (adjacent[node2] == null) {
                adjacent[node2] = new ArrayList<>();
            }

            adjacent[node1].add(node2);
        }



    }

}
