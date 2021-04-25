package datastructures;

/**
 * Created by Rene Argento on 02/04/21.
 */
// Data structure that implements insertFirst(), insertLast(), insertMiddle() and get() all in O(1).
// InsertMiddle() inserts an element on index (N + 1) / 2, where N is the size of the teque.
public class Teque {
    private final int[] firstHalf;
    private final int[] secondHalf;

    private int firstHalfStartIndex;
    private int firstHalfEndIndex;
    private int secondHalfStartIndex;
    private int secondHalfEndIndex;

    private int firstHalfSize;
    private int secondHalfSize;

    public Teque(int maxSize) {
        int arraysMaxSize = (maxSize + 1) * 2;
        firstHalf = new int[arraysMaxSize];
        secondHalf = new int[arraysMaxSize];

        firstHalfStartIndex = maxSize;
        firstHalfEndIndex = maxSize;
        secondHalfStartIndex = maxSize;
        secondHalfEndIndex = maxSize;
    }

    public int get(int index) {
        if (index < 0 || index >= firstHalfSize + secondHalfSize) {
            throw new IllegalArgumentException("Invalid index");
        }

        if (index < firstHalfSize) {
            return firstHalf[firstHalfStartIndex + index];
        } else {
            int secondHalfIndex = index - firstHalfSize;
            return secondHalf[secondHalfStartIndex + secondHalfIndex];
        }
    }

    public void insertFirst(int value) {
        if (firstHalfSize != 0) {
            firstHalfStartIndex--;
        }
        firstHalf[firstHalfStartIndex] = value;
        firstHalfSize++;
        balanceArrays();
    }

    public void insertLast(int value) {
        if (secondHalfSize != 0) {
            secondHalfEndIndex++;
        }
        secondHalf[secondHalfEndIndex] = value;
        secondHalfSize++;
        balanceArrays();
    }

    public void insertMiddle(int value) {
        if (secondHalfSize != 0) {
            secondHalfStartIndex--;
        }
        secondHalf[secondHalfStartIndex] = value;
        secondHalfSize++;
        balanceArrays();
    }

    private void balanceArrays() {
        int sizeDifference = firstHalfSize - secondHalfSize;

        if (sizeDifference > 1) {
            // Move element from left half to right half
            int valueToMove = firstHalf[firstHalfEndIndex];
            if (firstHalfSize != 1) {
                firstHalfEndIndex--;
            }
            if (secondHalfSize != 0) {
                secondHalfStartIndex--;
            }

            secondHalf[secondHalfStartIndex] = valueToMove;
            firstHalfSize--;
            secondHalfSize++;
        } else if (sizeDifference < 0) {
            // Move element from right half to left half
            int valueToMove = secondHalf[secondHalfStartIndex];
            if (secondHalfSize != 1) {
                secondHalfStartIndex++;
            }
            if (firstHalfSize != 0) {
                firstHalfEndIndex++;
            }

            firstHalf[firstHalfEndIndex] = valueToMove;
            firstHalfSize++;
            secondHalfSize--;
        }
    }
}
