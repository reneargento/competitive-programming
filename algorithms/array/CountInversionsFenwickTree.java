package algorithms.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Rene Argento on 14/02/21.
 */
public class CountInversionsFenwickTree {

    public static void main(String[] args) {
        long[] array = { 1, -9, 5, 4, 3 };
        int inversions = countInversions(array);
        System.out.println("Inversions: " + inversions);
        System.out.println("Expected: 4");
    }

    private static int countInversions(long[] array) {
        int inversions = 0;

        Map<Long, Integer> positionsMap = createPositionsMap(array);
        for (int i = 0; i < array.length; i++) {
            array[i] = positionsMap.get(array[i]);
        }

        FenwickTreeRangeSum fenwickTree = new FenwickTreeRangeSum(array.length);

        for (int index = array.length - 1; index >= 0; index--) {
            inversions += fenwickTree.rangeSumQuery((int) array[index] - 1);
            fenwickTree.update((int) array[index], 1);
        }
        return inversions;
    }

    private static Map<Long, Integer> createPositionsMap(long[] array) {
        long[] arrayCopy = array.clone();
        Arrays.sort(arrayCopy);

        Map<Long, Integer> positionsMap = new HashMap<>();
        for (int i = 0; i < arrayCopy.length; i++) {
            positionsMap.put(arrayCopy[i], i + 1);
        }
        return positionsMap;
    }

    private static class FenwickTreeRangeSum {
        private long[] fenwickTree;

        FenwickTreeRangeSum(int size) {
            fenwickTree = new long[size + 1];
        }

        // Returns sum from 1 to index
        public long rangeSumQuery(int index) {
            long sum = 0;
            while (index > 0) {
                sum += fenwickTree[index];
                index -= lsOne(index);
            }
            return sum;
        }

        public long rangeSumQuery(int startIndex, int endIndex) {
            return rangeSumQuery(endIndex) - rangeSumQuery(startIndex - 1);
        }

        // Updates the value of element on index by value (can be positive/increment or negative/decrement)
        public void update(int index, long value) {
            while (index < fenwickTree.length) {
                fenwickTree[index] += value;
                index += lsOne(index);
            }
        }

        int lsOne(int value) {
            return value & (-value);
        }
    }
}
