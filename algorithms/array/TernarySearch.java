package algorithms.array;

/**
 * Created by Rene Argento on 11/04/22.
 */
// Search for the lowest point in a unimodal function [HIGH, LOW, HIGH]
// Does not work when all values are non-decreasing. E.g. [LOW HIGH]
public class TernarySearch {

    private static int ternarySearch(int[] values) {
        int low = 0;
        int high = values.length - 1;
        int result = Integer.MAX_VALUE;

        for (int i = 0; i < 50; i++) {
            double delta = (high - low) / 3.0;
            int middle1 = (int) Math.round(low + delta);
            int middle2 = (int) Math.round(high - delta);

            // To support [LOW HIGH] arrays, add an equals sign to the comparison.
            // E.g. values[middle1] <= values[middle2]
            // This will make the algorithm not work for [HIGH LOW] arrays.
            if (values[middle1] < values[middle2]) {
                high = middle2;
                result = middle1;
            } else {
                low = middle1;
                result = middle2;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] values1 = { 50, 40, 20, 30, 80, 100 };
        int index1 = ternarySearch(values1);
        System.out.println("Index: " + index1 + " Expected: 2");

        int[] values2 = { 50, 40, 20, 10, 0, -10 };
        int index2 = ternarySearch(values2);
        System.out.println("Index: " + index2 + " Expected: 5");

        int[] values3 = { 100 };
        int index3 = ternarySearch(values3);
        System.out.println("Index: " + index3 + " Expected: 0");

        int[] values4 = { 1000, 1, 1000 };
        int index4 = ternarySearch(values4);
        System.out.println("Index: " + index4 + " Expected: 1");
    }

}
