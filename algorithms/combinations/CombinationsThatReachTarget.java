package algorithms.combinations;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rene on 20/10/17.
 */
public class CombinationsThatReachTarget {

    public static void main(String args[]) {
        Integer[] numbers = {3,9,8,4,5,7,10};
        int target = 15;

        sum(new ArrayList<>(Arrays.asList(numbers)),target);
    }

    private static void sum(ArrayList<Integer> numbers, int target) {
        getSums(numbers, target, new ArrayList<>());
    }

    private static void getSums(ArrayList<Integer> numbers, int target, ArrayList<Integer> partial) {
        int sum = 0;

        for (int currentNumber : partial) {
            sum += currentNumber;
        }

        if (sum == target) {
            System.out.println("sum("+ Arrays.toString(partial.toArray())+") = " + target);
        }

        if (sum >= target) {
            return;
        }

        for(int i = 0; i < numbers.size(); i++) {
            ArrayList<Integer> remaining = new ArrayList<>();
            int currentNumber = numbers.get(i);

            for (int j = i + 1; j < numbers.size(); j++) {
                remaining.add(numbers.get(j));
            }

            ArrayList<Integer> partialSum = new ArrayList<>(partial);
            partialSum.add(currentNumber);
            getSums(remaining, target, partialSum);
        }
    }

}
