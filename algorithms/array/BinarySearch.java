package algorithms.array;

/**
 * Created by Rene Argento on 11/03/22.
 */
// Computes a lower bound or upper bound of a value in a sorted array.
public class BinarySearch {

    private static int binarySearch(int[] values, int target, boolean isLowerBound) {
        int low = 0;
        int high = values.length - 1;
        int result = -1;

        while (low <= high) {
            int middle = low + (high - low) / 2;

            if (isLowerBound) {
                if (values[middle] < target) {
                    low = middle + 1;
                } else {
                    result = middle;
                    high = middle - 1;
                }
            } else {
                if (values[middle] > target) {
                    high = middle - 1;
                } else {
                    result = middle;
                    low = middle + 1;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] values = { 1, 2, 3, 10, 20 };

        int index1 = binarySearch(values, 5, true);
        System.out.println("Index: " + index1 + " Expected: 3");

        int index2 = binarySearch(values, 5, false);
        System.out.println("Index: " + index2 + " Expected: 2");

        int index3 = binarySearch(values, 20, true);
        System.out.println("Index: " + index3 + " Expected: 4");

        int index4 = binarySearch(values, 20, false);
        System.out.println("Index: " + index4 + " Expected: 4");
    }
}
