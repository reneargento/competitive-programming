package algorithms.dynamic.programming.longest.increasing.subsequence;

import java.util.*;

/**
 * Created by Rene Argento on 03/05/20.
 */
// Computes the longest increasing subsequence with 2 dimensions in O(N lg N).
// Basically sorts by dimension 1 and computes the LIS of dimension 2.
// Based on https://www.geeksforgeeks.org/longest-monotonically-increasing-subsequence-size-n-log-n/
// and https://www.geeksforgeeks.org/construction-of-longest-monotonically-increasing-subsequence-n-log-n/
public class LongestIncreasingSubsequence2D {

    public static class Object2D implements Comparable<Object2D> {
        int dimension1;
        int dimension2;

        public Object2D(int dimension1, int dimension2) {
            this.dimension1 = dimension1;
            this.dimension2 = dimension2;
        }

        @Override
        public int compareTo(Object2D otherObject) {
            if (this.dimension1 != otherObject.dimension1) {
                return this.dimension1 - otherObject.dimension1;
            } else {
                return this.dimension2 - otherObject.dimension2;
            }
        }

        public boolean isBefore(Object2D otherObject) {
            return dimension1 < otherObject.dimension1 && dimension2 < otherObject.dimension2;
        }

        public int compareDimension2(Object2D otherObject) {
            return this.dimension2 - otherObject.dimension2;
        }

        @Override
        public String toString() {
            return "(" + dimension1 + ", " + dimension2 + ")";
        }
    }

    public static List<Object2D> longestIncreasingSubsequence(List<Object2D> objects) {
        if (objects == null || objects.isEmpty()) {
            return new ArrayList<>();
        }
        Collections.sort(objects);

        int objectsLength = objects.size();
        int[] endIndexes = new int[objectsLength];
        int[] previousObject = new int[objectsLength];

        Arrays.fill(previousObject, -1);
        int length = 1;

        for (int i = 1; i < objectsLength; i++) {
            // Case 1 - smallest end element
            if (objects.get(i).compareDimension2(objects.get(endIndexes[0])) < 0) {
                endIndexes[0] = i;
            } else if (objects.get(endIndexes[length - 1]).isBefore(objects.get(i))) {
                // Case 2 - highest end element - extends longest increasing subsequence
                previousObject[i] = endIndexes[length - 1];
                endIndexes[length++] = i;
            } else {
                // Case 3 - middle end element
                int indexToReplace = ceilIndex(objects, endIndexes, 0, length - 1, objects.get(i));

                Object2D objectToBeReplaced = objects.get(endIndexes[indexToReplace]);
                if (objectToBeReplaced.compareDimension2(objects.get(i)) > 0) {
                    previousObject[i] = endIndexes[indexToReplace - 1];
                    endIndexes[indexToReplace] = i;
                }
            }
        }
        return getSequence(objects, endIndexes, previousObject, length);
    }

    private static int ceilIndex(List<Object2D> objects, int[] endIndexes, int low, int high, Object2D key) {
        while (high > low) {
            int middle = low + (high - low) / 2;

            if (objects.get(endIndexes[middle]).compareDimension2(key) >= 0) {
                high = middle;
            } else {
                low = middle + 1;
            }
        }
        return high;
    }

    private static List<Object2D> getSequence(List<Object2D> objects, int[] endIndexes, int[] previousObject, int length) {
        LinkedList<Object2D> sequence = new LinkedList<>();

        for (int i = endIndexes[length - 1]; i >= 0; i = previousObject[i]) {
            Object2D object = objects.get(i);
            sequence.addFirst(object);
        }
        return sequence;
    }

    public static void main(String[] args) {
        List<Object2D> objects1 = new ArrayList<>();
        objects1.add(new Object2D(65, 100));
        objects1.add(new Object2D(70, 150));
        objects1.add(new Object2D(56, 90));
        objects1.add(new Object2D(75, 190));
        objects1.add(new Object2D(60, 95));
        objects1.add(new Object2D(68, 110));
        List<Object2D> sequence1 = longestIncreasingSubsequence(objects1);
        printSequence(sequence1);
        System.out.println("Expected: (56, 90) (60, 95) (65, 100) (68, 110) (70, 150) (75, 190)\n");

        List<Object2D> objects2 = new ArrayList<>();
        List<Object2D> sequence2 = longestIncreasingSubsequence(objects2);
        printSequence(sequence2);
        System.out.println("Expected: \n");

        List<Object2D> objects3 = new ArrayList<>();
        objects3.add(new Object2D(50, 100));
        objects3.add(new Object2D(75, 120));
        objects3.add(new Object2D(60, 130));
        objects3.add(new Object2D(63, 115));
        objects3.add(new Object2D(63, 118));
        objects3.add(new Object2D(100, 99));
        objects3.add(new Object2D(100, 110));
        List<Object2D> sequence3 = longestIncreasingSubsequence(objects3);
        printSequence(sequence3);
        System.out.println("Expected: (50, 100) (63, 115) (75, 120)");
    }

    private static void printSequence(List<Object2D> sequence) {
        StringJoiner sequenceDescription = new StringJoiner(" ");
        for (Object2D object : sequence) {
            sequenceDescription.add(object.toString());
        }
        System.out.println("Sequence: " + sequenceDescription.toString());
    }

}
