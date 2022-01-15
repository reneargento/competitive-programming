package algorithms.recursion.permutations;

/**
 * Created by Rene Argento on 30/11/21.
 */
public class PreviousPermutation {

    private static int[] previousPermutation(int[] permutation) {
        // 1. Find the largest k, such that permutation[k] > permutation[k + 1]
        int first = getFirstIndexToSwap(permutation);
        if (first == -1) {
            return null; // no smaller permutation
        }

        // 2. Find the last index toSwap, that permutation[k] > permutation[toSwap]
        int toSwap = permutation.length - 1;
        while (permutation[first] <= permutation[toSwap]) {
            toSwap--;
        }

        // 3. Swap elements with indexes first and toSwap
        swap(permutation, first++, toSwap);

        // 4. Reverse sequence from k + 1 to n (inclusive)
        toSwap = permutation.length - 1;
        while (first < toSwap) {
            swap(permutation, first++, toSwap--);
        }
        return permutation;
    }

    // Finds the largest k, that permutation[k] > permutation[k + 1]
    // If no such k exists (there is not smaller permutation), return -1
    private static int getFirstIndexToSwap(int[] permutation ) {
        for (int i = permutation.length - 2; i >= 0; --i) {
            if (permutation[i] > permutation[i + 1]) {
                return i;
            }
        }
        return -1;
    }

    // Swaps two elements (with indexes index1 and index2) in array
    private static void swap(int[] permutation, int index1, int index2) {
        int temp = permutation[index1];
        permutation[index1] = permutation[index2];
        permutation[index2] = temp;
    }
}
