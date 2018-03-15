package com.br.uri.made.by.women2018;

import java.util.*;

/**
 * Created by Rene Argento on 10/03/18.
 */
// https://www.urionlinejudge.com.br/judge/en/challenges/view/346/7
public class TheReadersLocker {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()) {
            List<Integer> shelf = new ArrayList<>();

            int books = scanner.nextInt();
            int count = 0;

            for (int b = 0; b < books; b++) {
                int bookId = scanner.nextInt();

                boolean isInShelf = false;
                int bookIndexFound = -1;

                for (int index = 0; index < shelf.size(); index++) {
                    int bookIdInShelf = shelf.get(index);

                    if (bookIdInShelf == bookId) {
                        isInShelf = true;
                        bookIndexFound = index;
                        break;
                    }
                }

                if (!isInShelf) {
                    count++;

                    if (shelf.size() == 4) {
                        shelf.remove(0);
                    }
                } else {
                    shelf.remove(bookIndexFound);
                }
                shelf.add(bookId);
            }

            System.out.println(count);
        }

    }

}
