package com.br.algs.reference.algorithms.search;

import java.util.TreeMap;

/**
 * Created by rene on 26/05/17.
 */
public class SlidingWindowMedian {

    //Get highest median in the array
    private static int highestMedianSlidingWindow(int[] array, int k) {
        int median = 0;
        int bestMedian = 0;

        boolean useBothValues = k % 2 == 0;

        //Max-orientation
        TreeMap<Integer, Integer> small = new TreeMap<>((a, b)-> (int)((double)b-a));
        int smallSize = 0;

        //Min-orientation
        TreeMap<Integer, Integer> big = new TreeMap<>();
        int bigSize = 0;

        for(int i = 0; i < array.length; i++){
            //Remove old element
            if(smallSize + bigSize == k){
                if(array[i-k] <= small.firstKey()){
                    remove(small, array[i-k]);
                    smallSize--;
                } else {
                    remove(big, array[i-k]);
                    bigSize--;
                }
            }

            //Add new element
            if(smallSize <= bigSize){
                add(small, array[i]);
                smallSize++;
            } else {
                add(big, array[i]);
                bigSize++;
            }

            //Make sure elements are in the correct position
            if(bigSize > 0) {
                while(small.firstKey() > big.firstKey()){
                    add(big, remove(small, small.firstKey()));
                    add(small, remove(big, big.firstKey()));
                }
            }

            //Get median
            if(smallSize + bigSize == k){
                if(useBothValues) {
                    median = (small.firstKey() + big.firstKey()) / 2;
                }
                else {
                    median = small.firstKey();
                }
            }

            if(median > bestMedian) {
                bestMedian = median;
            }
        }

        return bestMedian;
    }

    //Methods used to handle duplicates in the trees
    private static int remove(TreeMap<Integer, Integer> map, int valueToRemove){
        //Decrease its quantity in the map
        map.put(valueToRemove, map.get(valueToRemove) - 1);

        if(map.get(valueToRemove) == 0) {
            map.remove(valueToRemove);
        }

        return valueToRemove;
    }

    private static void add(TreeMap<Integer, Integer> map, int valueToAdd){
        if(!map.containsKey(valueToAdd)) {
            map.put(valueToAdd, 1);
        } else {
            //Increase its quantity in the map
            map.put(valueToAdd, map.get(valueToAdd) + 1);
        }
    }

}
