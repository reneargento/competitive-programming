package com.br.training.usp.winterschool2017.campday4;

import java.util.*;

/**
 * Created by rene on 13/07/17.
 */
public class Wallpapers {

    private static class Store {
        int id;
        long minWidth, maxWidth, minHeight, maxHeight;

        Store(int id, long minWidth, long maxWidth, long minHeight, long maxHeight) {
            this.id = id;
            this.minWidth = minWidth;
            this.maxWidth = maxWidth;
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        }

        @Override
        public int hashCode() {
            return id;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();

        for(int i = 0; i < tests; i++) {
            int stores = scanner.nextInt();

            long minWidth = Integer.MAX_VALUE;
            long maxWidth = 0;
            long minHeight = Integer.MAX_VALUE;
            long maxHeight = 0;

            Set<Store> storeSet = new HashSet<>();

            for(int s = 0; s < stores; s++) {
                long width1 = scanner.nextInt();
                long width2 = scanner.nextInt();
                long height1 = scanner.nextInt();
                long height2 = scanner.nextInt();

                Store store = new Store(s, width1, width2, height1, height2);

                if (width1 <= minWidth) {
                    minWidth = width1;
                    storeSet.add(store);
                }
                if (width2 >= maxWidth) {
                    maxWidth = width2;
                    storeSet.add(store);
                }
                if (height1 <= minHeight) {
                    minHeight = height1;
                    storeSet.add(store);
                }
                if (height2 >= maxHeight) {
                    maxHeight = height2;
                    storeSet.add(store);
                }
            }

            boolean hasStore = false;

            for(Store store : storeSet) {
                if (store.minWidth == minWidth && store.maxWidth == maxWidth
                        && store.minHeight == minHeight && store.maxHeight == maxHeight) {
                    hasStore = true;
                    break;
                }
            }

            if (hasStore) {
                System.out.println("ANO");
            } else {
                System.out.println("NIE");
            }

        }
    }
}
