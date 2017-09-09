package com.br.algs.reference.algorithms.number.theory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by rene on 03/06/17.
 */
public class Factors {

    private static List<Integer> getFactors(int number) {
        List<Integer> factors = new ArrayList<>();

        int upperLimit = (int) Math.sqrt(number);

        for(int i = 1; i <= upperLimit; i++) {
            if (number % i == 0) {
                factors.add(i);

                if(i != number / i) {
                    factors.add(number / i);
                }
            }
        }

        Collections.sort(factors);

        return factors;
    }

}
