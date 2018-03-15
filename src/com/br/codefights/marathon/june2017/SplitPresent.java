package com.br.codefights.marathon.june2017;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rene on 24/06/17.
 */
public class SplitPresent {

    public static void main(String[] args) {
        System.out.println(splitPresentCost(2, 3, 5));
        System.out.println(splitPresentCost(1, 1, 1));
    }

    private static long splitPresentCost(long l, long r, long s) {
        long[] numbers = {l, r};

        ArrayList<ArrayList<Long>> result = combinationSum(numbers, s);
        return result.size();
    }

    public static ArrayList<ArrayList<Long>> combinationSum(long[] candidates, long target) {
        ArrayList<ArrayList<Long>> result = new ArrayList<>();

        if (candidates == null || candidates.length == 0) {
            return result;
        }

        ArrayList<Long> current = new ArrayList<>();
        Arrays.sort(candidates);

        combinationSum(candidates, target, 0, current, result);

        return result;
    }

    public static void combinationSum(long[] candidates, long target, long j, ArrayList<Long> curr, ArrayList<ArrayList<Long>> result){
        if (target == 0){
            ArrayList<Long> temp = new ArrayList<>(curr);
            result.add(temp);
            return;
        }

        for(int i = (int)j; i < candidates.length; i++){
            if (target < candidates[i]) {
                return;
            }

            curr.add(candidates[i]);
            combinationSum(candidates, target - candidates[i], i, curr, result);
            curr.remove(curr.size()-1);
        }
    }

}
