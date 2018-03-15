package com.br.codefights.marathon.april2017;

/**
 * Created by rene on 29/04/17.
 */

/**
 * The university library needs to buy some new textbooks.
 * There are n textbooks numbered from 1 to n.
 * The library gives you a number of textbooks n and an array textbooks that contains pairs [k, kAmount].
 * The library needs to purchase at least kAmount copies of each textbook with numbers k, 2 * k, 3 * k, etc.

 Help the library calculate how many copies of each book it should buy!

 Example

 For n = 6 and textbooks = [[3, 1], [2, 2]], the output should be
 buyingTextbooks(n, textbooks) = [0, 2, 1, 2, 0, 2].

 The library needs the following textbooks:

 Each 3rd textbook:
 book 3 × 1
 book 6 × 1
 Each 2nd textbook:
 book 2 × 2
 book 4 × 2
 book 6 × 2
 Note that the library has two requirements about book 6 it needs to meet.
 To meet the first requirement, they need 1 copy of the book, but to meet the second, they need 2 copies.
 To meet the requirements, they should buy 2 copies of textbook 6.

 Altogether, the library should buy two copies of book 2, one copy of book 3, two copies of book 4, and two copies of book 6.
 The library doesn't need books 1 and 5.

 For n = 7 and textbooks = [[1, 2]], the output should be
 buyingTextbooks(n, textbooks) = [2, 2, 2, 2, 2, 2, 2].

 Every 1st textbook means all the textbooks, so the library needs 2 copies of each textbook.

 Input/Output

 [time limit] 3000ms (java)
 [input] integer n

 The number of textbooks.

 Guaranteed constraints:
 1 ≤ n ≤ 100 .

 [input] array.array.integer textbooks

 Pairs [k, kAmount], such that the library wants at least kAmount copies of each kth textbook.

 Guaranteed constraints:
 1 ≤ textbooks.length ≤ n ,
 textbooks[i].length = 2,
 1 ≤ textbooks[i][0] ≤ n , unique for all pairs,
 1 ≤ textbooks[i][1] ≤ 109 .

 [output] array.integer

 For every book i, how many copies of it the library should purchase.
 */

public class BuyingTextBooks {

    public static void main(String[] args) {
        int n1 = 6;
        int[][] textbooks1 = {{3,1}, {2,2}};

        int[] copiesToPurchase1 = buyingTextbooks(n1, textbooks1);

        for(int i = 0; i < copiesToPurchase1.length; i++) {
            System.out.print(copiesToPurchase1[i]);

            if (i < copiesToPurchase1.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 0, 2, 1, 2, 0, 2");

        int n2 = 7;
        int[][] textbooks2 = {{1,2}};

        int[] copiesToPurchase2 = buyingTextbooks(n2, textbooks2);

        for(int i = 0; i < copiesToPurchase2.length; i++) {
            System.out.print(copiesToPurchase2[i]);

            if (i < copiesToPurchase2.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 2, 2, 2, 2, 2, 2, 2");

        int n3 = 15;
        int[][] textbooks3 = {{5,1}, {3,2}};

        int[] copiesToPurchase3 = buyingTextbooks(n3, textbooks3);

        for(int i = 0; i < copiesToPurchase3.length; i++) {
            System.out.print(copiesToPurchase3[i]);

            if (i < copiesToPurchase3.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 0, 0, 2, 0, 1, 2, 0, 0, 2, 1, 0, 2, 0, 0, 2");

        int n4 = 20;
        int[][] textbooks4 = {{2,2}, {4,1}, {10, 3}};

        int[] copiesToPurchase4 = buyingTextbooks(n4, textbooks4);

        for(int i = 0; i < copiesToPurchase4.length; i++) {
            System.out.print(copiesToPurchase4[i]);

            if (i < copiesToPurchase4.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 0, 2, 0, 2, 0, 2, 0, 2, 0, 3, 0, 2, 0, 2, 0, 2, 0, 2, 0, 3");

        int n5 = 12;
        int[][] textbooks5 = {{2,1}, {3,2}, {6,3}};

        int[] copiesToPurchase5 = buyingTextbooks(n5, textbooks5);

        for(int i = 0; i < copiesToPurchase5.length; i++) {
            System.out.print(copiesToPurchase5[i]);

            if (i < copiesToPurchase5.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 0, 1, 2, 1, 0, 3, 0, 1, 2, 1, 0, 3");

        int n6 = 1;
        int[][] textbooks6 = {{1,2}};

        int[] copiesToPurchase6 = buyingTextbooks(n6, textbooks6);

        for(int i = 0; i < copiesToPurchase6.length; i++) {
            System.out.print(copiesToPurchase6[i]);

            if (i < copiesToPurchase6.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 2");
    }

    private static int[] buyingTextbooks(int n, int[][] textbooks) {
        int[] copiesToPurchase = new int[n];

        for(int i = 0; i < textbooks.length; i++) {

            for(int j = 1; j <= n; j++) {
                if (textbooks[i][0] > 0 && j % textbooks[i][0] == 0) {
                    if (textbooks[i][1] > copiesToPurchase[j - 1]) {
                        copiesToPurchase[j - 1] = textbooks[i][1];
                    }
                }
            }
        }

        return copiesToPurchase;
    }

    private static int[] buyingTextbooksOptimized(int n, int[][] textbooks) {
        int[] ret = new int[n];
        for(int[] out: textbooks) {
            int mod = out[0];
            int need = out[1];
            for(int a = mod; a <= n; a+=mod) {
                ret[a - 1] = Math.max(ret[a - 1], need);
            }
        }
        return ret;
    }

}
