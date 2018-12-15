package algorithms.permutations;

import java.util.Arrays;

/**
 * Created by http://codeforces.com/profile/Betlista
 * Updated by Rene Argento on 02/01/18.
 */
@SuppressWarnings("unchecked")
// Based on http://codeforces.com/blog/entry/3980
public class NextPermutation {

    // Simply prints all the next permutations - to see how it works
    private static void printAllNextPermutations(Comparable[] permutation) {
        System.out.println(Arrays.toString(permutation));

        while ((permutation = nextPermutation(permutation)) != null ) {
            System.out.println(Arrays.toString(permutation));
        }
    }

    // Modifies a permutation to the next permutation (lexicographically greater) or returns null if such permutation
    // does not exist
    public static Comparable[] nextPermutation(final Comparable[] permutation) {
        // 1. Find the largest k, such that permutation[k] < permutation[k+1]
        int first = getFirstIndexToSwap(permutation);

        if (first == -1) {
            return null; // no greater permutation
        }

        // 2. Find the last index toSwap, that permutation[k] < permutation[toSwap]
        int toSwap = permutation.length - 1;

        while (permutation[first].compareTo(permutation[toSwap]) >= 0) {
            toSwap--;
        }

        // 3. Swap elements with indexes first and last
        swap(permutation, first++, toSwap);

        // 4. Reverse sequence from k+1 to n (inclusive)
        toSwap = permutation.length - 1;

        while (first < toSwap) {
            swap(permutation, first++, toSwap--);
        }

        return permutation;
    }

    // Finds the largest k, that permutation[k] < permutation[k+1]
    // If no such k exists (there is not greater permutation), return -1
    private static int getFirstIndexToSwap(final Comparable[] permutation ) {

        for (int i = permutation.length - 2; i >= 0; --i) {
            if (permutation[i].compareTo(permutation[i + 1] ) < 0) {
                return i;
            }
        }

        return -1;
    }

    // Swaps two elements (with indexes index1 and index2) in array
    private static void swap( final Comparable[] permutation, final int index1, final int index2) {
        final Comparable temp = permutation[index1];
        permutation[index1] = permutation[index2];
        permutation[index2] = temp;
    }

    public static void main(String[] args) {
        Integer[] array = {1, 2, 7, 3, 6, 8};

//        System.out.println("Next permutation: ");
//        System.out.print(Arrays.toString(nextPermutation(array)));
//        System.out.println("\nExpected: [1, 2, 7, 3, 8, 6]");

        printAllNextPermutations(array);
    }

}