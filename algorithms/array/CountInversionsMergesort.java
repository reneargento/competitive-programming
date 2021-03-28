package algorithms.array;

/**
 * Created by Rene Argento on 28/02/21.
 */
public class CountInversionsMergesort {

    private static long countInversions(long[] array) {
        long[] helper = new long[array.length];
        return countInversions(array, helper, 0, array.length - 1);
    }

    private static long countInversions(long[] array, long[] helper, int low, int high) {
        if (low >= high) {
            return 0;
        }
        int middle = low + (high - low) / 2;

        long inversions = 0;

        inversions += countInversions(array, helper, low, middle);
        inversions += countInversions(array, helper, middle + 1, high);
        inversions += countSplitInversions(array, helper, low, middle, high);
        return inversions;
    }

    private static long countSplitInversions(long[] array, long[] helper, int low, int middle, int high) {
        long inversions = 0;

        for (int i = low; i <= high; i++) {
            helper[i] = array[i];
        }

        int i = low;
        int j = middle + 1;
        int k = low;

        while (i <= middle && j <= high) {
            if (helper[i] <= helper[j]) {
                array[k] = helper[i];
                i++;
            } else {
                array[k] = helper[j];
                j++;
                inversions += middle - i + 1;
            }
            k++;
        }

        while (i <= middle) {
            array[k] = helper[i];
            k++;
            i++;
        }
        return inversions;
    }

    public static void main(String[] args) {
        long[] numberTest1 = { 1, 3, 5, 2, 4, 6 };
        long[] numberTest2 = { 1, 5, 3, 2, 4 };
        long[] numberTest3 = { 5, 4, 3, 2, 1 };
        long[] numberTest4 = { 1, 6, 3, 2, 4, 5 };
        long[] numberTest5 = { 9, 12, 3, 1, 6, 8, 2, 5, 14, 13, 11, 7, 10, 4, 0 };
        long[] numberTest6 = { 37, 7, 2, 14, 35, 47, 10, 24, 44, 17, 34, 11,
                16, 48, 1, 39, 6, 33, 43, 26, 40, 4, 28, 5, 38, 41, 42, 12, 13,
                21, 29, 18, 3, 19, 0, 32, 46, 27, 31, 25, 15, 36, 20, 8, 9, 49,
                22, 23, 30, 45 };
        long[] numberTest7 = { 4, 80, 70, 23, 9, 60, 68, 27, 66, 78, 12, 40,
                52, 53, 44, 8, 49, 28, 18, 46, 21, 39, 51, 7, 87, 99, 69, 62,
                84, 6, 79, 67, 14, 98, 83, 0, 96, 5, 82, 10, 26, 48, 3, 2, 15,
                92, 11, 55, 63, 97, 43, 45, 81, 42, 95, 20, 25, 74, 24, 72, 91,
                35, 86, 19, 75, 58, 71, 47, 76, 59, 64, 93, 17, 50, 56, 94, 90,
                89, 32, 37, 34, 65, 1, 73, 41, 36, 57, 77, 30, 22, 13, 29, 38,
                16, 88, 61, 31, 85, 33, 54 };

        long inversions1 = countInversions(numberTest1);
        System.out.println("Test case 1: " + inversions1 + " - Should be 3");

        long inversions2 = countInversions(numberTest2);
        System.out.println("Test case 2: " + inversions2 + " - Should be 4");

        long inversions3 = countInversions(numberTest3);
        System.out.println("Test case 3: " + inversions3 + " - Should be 10");

        long inversions4 = countInversions(numberTest4);
        System.out.println("Test case 4: " + inversions4 + " - Should be 5");

        long inversions5 = countInversions(numberTest5);
        System.out.println("Test case 5: " + inversions5 + " - Should be 56");

        long inversions6 = countInversions(numberTest6);
        System.out.println("Test case 6: " + inversions6 + " - Should be 590");

        long inversions7 = countInversions(numberTest7);
        System.out.println("Test case 7: " + inversions7 + " - Should be 2372");
    }
}
