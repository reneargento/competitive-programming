package datastructures.priority.queue;

/**
 * Created by Rene Argento on 31/12/17.
 */
@SuppressWarnings("unchecked")
public class DWayPriorityQueue<Key extends Comparable<Key>> {

    public enum Orientation {
        MAX, MIN;
    }

    private Key[] priorityQueue;
    private int size = 0; // in priorityQueue[1..n] with pq[0] unused
    private final Orientation orientation;
    private final int numberOfChildrenPerNode;

    public DWayPriorityQueue(Orientation orientation, int numberOfChildrenPerNode) {
        priorityQueue = (Key[]) new Comparable[2];
        this.orientation = orientation;
        this.numberOfChildrenPerNode = numberOfChildrenPerNode;
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

    // parent of n: (n + (d - 2)) / d
    private void swim(int index) {
        int parentIndex = (index + (numberOfChildrenPerNode - 2)) / numberOfChildrenPerNode;

        while (parentIndex >= 1) {
            if ((orientation == Orientation.MAX && ArrayUtil.less(priorityQueue[parentIndex], priorityQueue[index]))
                    || (orientation == Orientation.MIN && ArrayUtil.more(priorityQueue[parentIndex], priorityQueue[index]))) {
                ArrayUtil.exchange(priorityQueue, parentIndex, index);
            } else {
                break;
            }

            index = parentIndex;
            parentIndex = (index + (numberOfChildrenPerNode - 2)) / numberOfChildrenPerNode;
        }
    }

    // children of n: ((n * d - (d - 2)), ..., (n * d + 1))
    private void sink(int index) {
        int childSmallestIndex = (index * numberOfChildrenPerNode - (numberOfChildrenPerNode - 2));
        int childHighestIndex = (index * numberOfChildrenPerNode + 1);

        while (childSmallestIndex <= size) {
            int selectedChildIndex = childSmallestIndex;

            for (int childIndex = childSmallestIndex + 1; childIndex <= childHighestIndex; childIndex++) {
                if (childIndex <= size &&
                        (
                                (orientation == Orientation.MAX && ArrayUtil.less(priorityQueue[selectedChildIndex], priorityQueue[childIndex]))
                                        || (orientation == Orientation.MIN && ArrayUtil.more(priorityQueue[selectedChildIndex], priorityQueue[childIndex]))
                        )
                ) {
                    selectedChildIndex = childIndex;
                }
            }

            if ((orientation == Orientation.MAX && ArrayUtil.more(priorityQueue[selectedChildIndex], priorityQueue[index]))
                    || (orientation == Orientation.MIN && ArrayUtil.less(priorityQueue[selectedChildIndex], priorityQueue[index]))) {
                ArrayUtil.exchange(priorityQueue, index, selectedChildIndex);
            } else {
                break;
            }

            index = selectedChildIndex;
            childSmallestIndex = (index * numberOfChildrenPerNode - (numberOfChildrenPerNode - 2));
            childHighestIndex = (index * numberOfChildrenPerNode + 1);
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
