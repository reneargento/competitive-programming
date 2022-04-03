package algorithms.general;

/**
 * Created by Rene Argento on 11/03/22.
 */
// Computes a lower bound or upper bound of a value in a sorted array.
public class BinarySearch {

    private static int binarySearch(int[] values, int target, boolean isLowerBound, int low, int high) {
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

        int index1 = binarySearch(values, 5, true, 0, values.length - 1);
        System.out.println("Index: " + index1 + " Expected: 3");

        int index2 = binarySearch(values, 5, false, 0, values.length - 1);
        System.out.println("Index: " + index2 + " Expected: 2");

        int index3 = binarySearch(values, 20, true, 0, values.length - 1);
        System.out.println("Index: " + index3 + " Expected: 4");

        int index4 = binarySearch(values, 20, false, 0, values.length - 1);
        System.out.println("Index: " + index4 + " Expected: 4");
    }
}
