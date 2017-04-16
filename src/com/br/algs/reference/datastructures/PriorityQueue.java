package com.br.algs.reference.datastructures;

/**
 * Created by rene on 16/04/17.
 */
public class PriorityQueue {

    private enum Orientation {
        MAX, MIN;
    }

    private long[] priorityQueue;
    private int size = 0; // in priorityQueue[1..n] with pq[0] unused
    private Orientation orientation;
    ArrayUtil arrayUtil = new ArrayUtil();

    PriorityQueue(Orientation orientation) {
        priorityQueue = new long[2];
        this.orientation = orientation;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public long peek() {
        return priorityQueue[1];
    }

    public void insert(long key) {

        if(size == priorityQueue.length - 1) {
            resize(priorityQueue.length * 2);
        }

        size++;
        priorityQueue[size] = key;

        swim(size);
    }

    public long deleteTop() {

        if(size == 0) {
            throw new RuntimeException("Priority queue underflow");
        }

        size--;

        long top = priorityQueue[1];

        arrayUtil.exchange(priorityQueue, 1, size + 1);

        sink(1);

        if(size == priorityQueue.length / 4) {
            resize(priorityQueue.length / 2);
        }

        return top;
    }

    private void swim(int index) {
        while(index / 2 >= 1) {
            if((orientation == Orientation.MAX && arrayUtil.less(priorityQueue[index / 2], priorityQueue[index]))
                    || (orientation == Orientation.MIN && arrayUtil.more(priorityQueue[index / 2], priorityQueue[index]))) {
                arrayUtil.exchange(priorityQueue, index / 2, index);
            } else {
                break;
            }

            index = index / 2;
        }
    }

    private void sink(int index) {
        while (index * 2 <= size) {
            int selectedChildIndex = index * 2;

            if(index * 2 + 1 <= size &&
                    (
                            (orientation == Orientation.MAX && arrayUtil.less(priorityQueue[index * 2], priorityQueue[index * 2 + 1]))
                                    || (orientation == Orientation.MIN && arrayUtil.more(priorityQueue[index * 2], priorityQueue[index * 2 + 1]))
                    )
                    ){
                selectedChildIndex = index * 2 + 1;
            }

            if((orientation == Orientation.MAX && arrayUtil.more(priorityQueue[selectedChildIndex], priorityQueue[index]))
                    || (orientation == Orientation.MIN && arrayUtil.less(priorityQueue[selectedChildIndex], priorityQueue[index]))) {
                arrayUtil.exchange(priorityQueue, index, selectedChildIndex);
            } else {
                break;
            }

            index = selectedChildIndex;
        }
    }

    private void resize(int newSize) {
        long[] newPriorityQueue = new long[newSize];
        System.arraycopy(priorityQueue, 1, newPriorityQueue, 1, size);
        priorityQueue = newPriorityQueue;
    }

    private class ArrayUtil {

        boolean less(long value1, long value2) {
            if(value1 < value2) {
                return true;
            } else {
                return false;
            }
        }

        boolean more(long value1, long value2) {
            if(value1 > value2) {
                return true;
            } else {
                return false;
            }
        }

        void exchange(long[] array, int position1, int position2) {
            long temp = array[position1];
            array[position1] = array[position2];
            array[position2] = temp;
        }
    }

}
