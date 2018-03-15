package com.br.codefights.marathon.april2017;

/**
 * Created by rene on 29/04/17.
 */
/**
 * Alice is putting all her books on a shelf.
 * Instead of putting the books on the shelf with their spines out, though, she's placing them cover to cover,
 * from the back of the bookshelf to the front.
 * When she stands in front of the bookshelf, she can see the entire front cover of the last book she put on the shelf.
 * The books have different heights, meaning that some of the books can completely hide the other books.
 * So if a book has another book of at least the same height standing in front of it, Alice can't see the first book.

 Alice puts one book per second on the shelf.
 You are given an array books that contains the heights of the books.
 books[i] is placed on the shelf at the ith second.
 Return an array that represents how many seconds Alice can see each book.

 Example

 For books = [9, 6, 6, 2, 5], the output should be
 visibleBooks(books) = [5, 1, 3, 1, 1].

 The whole process of putting these books on a shelf takes 5 seconds.

 1st second: There's only one book on the shelf, with a height of books[0] = 9.
 2nd second: Now there are two books on the shelf, with heights of books[0] = 9 and books[1] = 6.
 Alice can see both of them since books[1] is shorter than books[0].
 3rd second: The third book on the shelf has the same height as the second one, so Alice can't see the second book now.
 4th second: The shelf now has books with heights 9, 6, 6, 2. Alice can see the first, third, and fourth books.
 5th second: The last book has a height of books[4] = 5 and covers the previous one, which has a height of books[3] = 2.
 So now Alice can only see the first, third, and fifth books.
 To sum it up, Alice can see the first book during all 5 seconds, the second book only at second 2,
 the third book at seconds 3, 4, and 5, the fourth book at second 4, and the fifth book at second 5.

 For books = [7, 9, 4, 3, 7], the output should be
 visibleBooks(books) = [1, 4, 2, 1, 1].

 As we can see, the first book was covered by the second one so it can only be seen once.
 The second book is the tallest one, so Alice can always see it after she puts it on a shelf. The third and fourth books were covered by the fifth book, so they can be seen for 2 and 1 seconds. And of course the last book can only be seen once.

 Input/Output

 [time limit] 3000ms (java)
 [input] array.integer books

 The heights of the books. books[i] is placed on the shelf at second i + 1.

 Guaranteed constraints:
 1 ≤ books.length ≤ 100,
 1 ≤ books[i] ≤ 109.

 [output] array.integer

 An array that indicates how many seconds Alice can see each book.
 */
public class VisibleBooks {

    public static void main(String[] args) {
        int[] books1 = {9, 6, 6, 2, 5};
        int[] visibleBooks1 = visibleBooks(books1);

        for(int i = 0; i < visibleBooks1.length; i++) {
            System.out.print(visibleBooks1[i]);

            if (i < visibleBooks1.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 5, 1, 3, 1, 1");

        int[] books2 = {7, 9, 4, 3, 7};
        int[] visibleBooks2 = visibleBooks(books2);

        for(int i = 0; i < visibleBooks2.length; i++) {
            System.out.print(visibleBooks2[i]);

            if (i < visibleBooks2.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 1, 4, 2, 1, 1");

        int[] books3 = {2, 2, 2, 1, 1, 1};
        int[] visibleBooks3 = visibleBooks(books3);

        for(int i = 0; i < visibleBooks3.length; i++) {
            System.out.print(visibleBooks3[i]);

            if (i < visibleBooks3.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 1, 1, 4, 1, 1, 1");

        int[] books4 = {5, 13, 46, 34, 26, 44, 5, 35, 37, 7};
        int[] visibleBooks4 = visibleBooks(books4);

        for(int i = 0; i < visibleBooks4.length; i++) {
            System.out.print(visibleBooks4[i]);

            if (i < visibleBooks4.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 1, 1, 8, 2, 1, 5, 1, 1, 2, 1");

        int[] books5 = {78, 6, 21, 54, 19, 36, 30, 22, 36, 25, 70, 87, 39, 75, 59};
        int[] visibleBooks5 = visibleBooks(books5);

        for(int i = 0; i < visibleBooks5.length; i++) {
            System.out.print(visibleBooks5[i]);

            if (i < visibleBooks5.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 11, 1, 1, 7, 1, 3, 2, 1, 2, 1, 1, 4, 1, 2, 1");

        int[] books6 = {10};
        int[] visibleBooks6 = visibleBooks(books6);

        for(int i = 0; i < visibleBooks6.length; i++) {
            System.out.print(visibleBooks6[i]);

            if (i < visibleBooks6.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("\nExpected: 1");
    }

    private static int[] visibleBooks(int[] books) {

        int[] visible = new int[books.length];


        for(int i = 0; i < books.length; i++) {
            int secondsVisible = 1;

            for(int j = i + 1; j < books.length; j++) {
                if (books[i] > books[j]) {
                    secondsVisible++;
                } else {
                    break;
                }
            }

            visible[i] = secondsVisible;
        }

        return visible;
    }

}
