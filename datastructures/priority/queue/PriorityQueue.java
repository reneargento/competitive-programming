package datastructures.priority.queue;

/**
 * Created by Rene Argento on 16/04/17.
 */
@SuppressWarnings("unchecked")
public class PriorityQueue<Key extends Comparable<Key>> {

    public enum Orientation {
        MAX, MIN;
    }

    private Key[] priorityQueue;
    private int size = 0; // in priorityQueue[1..n] with pq[0] unused
    private final Orientation orientation;

    public PriorityQueue(Orientation orientation) {
        priorityQueue = (Key[]) new Comparable[2];
        this.orientation = orientation;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public Key peek() {
        return priorityQueue[1];
    }

    public void insert(Key key) {
        if (size == priorityQueue.length - 1) {
            resize(priorityQueue.length * 2);
        }
        size++;
        priorityQueue[size] = key;

        swim(size);
    }

    public Key deleteTop() {
        if (size == 0) {
            throw new RuntimeException("Priority queue underflow");
        }
        size--;
        Key top = priorityQueue[1];

        ArrayUtil.exchange(priorityQueue, 1, size + 1);
        priorityQueue[size + 1] = null;

        sink(1);

        if (size == priorityQueue.length / 4) {
            resize(priorityQueue.length / 2);
        }
        return top;
    }

    private void swim(int index) {
        while (index / 2 >= 1) {
            if ((orientation == Orientation.MAX && ArrayUtil.less(priorityQueue[index / 2], priorityQueue[index]))
                    || (orientation == Orientation.MIN && ArrayUtil.more(priorityQueue[index / 2], priorityQueue[index]))) {
                ArrayUtil.exchange(priorityQueue, index / 2, index);
            } else {
                break;
            }

            index = index / 2;
        }
    }

    private void sink(int index) {
        while (index * 2 <= size) {
            int selectedChildIndex = index * 2;

            if (index * 2 + 1 <= size &&
                    (
                            (orientation == Orientation.MAX && ArrayUtil.less(priorityQueue[index * 2], priorityQueue[index * 2 + 1]))
                                    || (orientation == Orientation.MIN && ArrayUtil.more(priorityQueue[index * 2], priorityQueue[index * 2 + 1]))
                    )
            ) {
                selectedChildIndex = index * 2 + 1;
            }

            if ((orientation == Orientation.MAX && ArrayUtil.more(priorityQueue[selectedChildIndex], priorityQueue[index]))
                    || (orientation == Orientation.MIN && ArrayUtil.less(priorityQueue[selectedChildIndex], priorityQueue[index]))) {
                ArrayUtil.exchange(priorityQueue, index, selectedChildIndex);
            } else {
                break;
            }
            index = selectedChildIndex;
        }
    }

    private void resize(int newSize) {
        Key[] newPriorityQueue = (Key[]) new Comparable[newSize];
        System.arraycopy(priorityQueue, 1, newPriorityQueue, 1, size);
        priorityQueue = newPriorityQueue;
    }

    private static class ArrayUtil {

        public static boolean less(Comparable value1, Comparable value2) {
            if (value1.compareTo(value2) < 0) {
                return true;
            } else {
                return false;
            }
        }

        public static boolean more(Comparable value1, Comparable value2) {
            if (value1.compareTo(value2) > 0) {
                return true;
            } else {
                return false;
            }
        }

        public static void exchange(Comparable[] array, int position1, int position2) {
            Comparable temp = array[position1];
            array[position1] = array[position2];
            array[position2] = temp;
        }
    }
}
