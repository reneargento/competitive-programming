package algorithms.general;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

/**
 * Created by Rene Argento on 17/05/20.
 */
// Gets the smallest k numbers from an array in O(n)
public class SmallestK {

    private static class PartitionResult {
        int leftEndIndex;
        int middleEndIndex;

        public PartitionResult(int leftEndIndex, int middleEndIndex) {
            this.leftEndIndex = leftEndIndex;
            this.middleEndIndex = middleEndIndex;
        }
    }

    public static List<Integer> smallestK(int[] array, int k) {
        if (array == null || k < 0 || k > array.length) {
            throw new IllegalArgumentException();
        }
        if (k == 0) {
            return new ArrayList<>();
        }
        int targetRank = k - 1;

        shuffle(array);
        int startIndex = 0;
        int endIndex = array.length - 1;

        while (true) {
            int pivot = array[startIndex];
            PartitionResult partitionResult = partition(array, startIndex, endIndex, pivot);

            if (targetRank <= partitionResult.leftEndIndex) {
                endIndex = partitionResult.leftEndIndex;
            } else if (targetRank <= partitionResult.middleEndIndex) {
                break;
            } else {
                startIndex = partitionResult.middleEndIndex + 1;
            }
        }
        return getSmallestK(array, k);
    }

    private static PartitionResult partition(int[] array, int startIndex, int endIndex, int pivot) {
        int leftIndex = startIndex;
        int middleIndex = startIndex;

        while (middleIndex <= endIndex){
            if (array[middleIndex] < pivot) {
                swap(array, middleIndex, leftIndex);
                leftIndex++;
                middleIndex++;
            } else if (array[middleIndex] > pivot) {
                swap(array, middleIndex, endIndex);
                endIndex--;
            } else {
                // array[middleIndex] == pivot
                middleIndex++;
            }
        }
        return new PartitionResult(leftIndex - 1, middleIndex - 1);
    }

    private static void swap(int[] array, int index1, int index2) {
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private static List<Integer> getSmallestK(int[] array, int k) {
        List<Integer> numbers = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            numbers.add(array[i]);
        }
        return numbers;
    }

    private static void shuffle(int[] array) {
        Random random = new Random();

        for (int i = 0; i < array.length; i++) {
            int randomIndex = i + random.nextInt(array.length - i);
            swap(array, randomIndex, i);
        }
    }

    public static void main(String[] args) {
        int[] array1 = { 9, 12, 1, 3, 4, 5, 7, 100, 2, -19, 0 };

        List<Integer> smallestK1 = smallestK(array1, 0);
        String smallestK1Description = getSmallestKDescription(smallestK1);
        System.out.println("Smallest 0: " + smallestK1Description);
        System.out.println("Expected: ");

        List<Integer> smallestK2 = smallestK(array1, 1);
        String smallestK2Description = getSmallestKDescription(smallestK2);
        System.out.println("\nSmallest 1: " + smallestK2Description);
        System.out.println("Expected: -19");

        List<Integer> smallestK3 = smallestK(array1, 3);
        String smallestK3Description = getSmallestKDescription(smallestK3);
        System.out.println("\nSmallest 3: " + smallestK3Description);
        System.out.println("Expected: -19 0 1 (no specific order)");

        List<Integer> smallestK4 = smallestK(array1, 11);
        String smallestK4Description = getSmallestKDescription(smallestK4);
        System.out.println("\nSmallest 11: " + smallestK4Description);
        System.out.println("Expected: -19 0 1 2 3 4 5 7 9 12 100 (no specific order)");

        int[] array2 = { 4, 4, 4, 4, 4, 4, 4, 4 };

        List<Integer> smallestK5 = smallestK(array2, 3);
        String smallestK5Description = getSmallestKDescription(smallestK5);
        System.out.println("\nSmallest 3: " + smallestK5Description);
        System.out.println("Expected: 4 4 4");
    }

    private static String getSmallestKDescription(List<Integer> numbers) {
        StringJoiner numbersDescription = new StringJoiner(" ");
        for (int number : numbers) {
            numbersDescription.add(String.valueOf(number));
        }
        return numbersDescription.toString();
    }

}
