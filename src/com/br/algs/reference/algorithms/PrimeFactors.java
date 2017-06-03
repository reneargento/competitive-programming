package com.br.algs.reference.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 29/04/17.
 */
public class PrimeFactors {

    private static List<Integer> primeFactors(int number) {
        List<Integer> primeFactors = new ArrayList<>();

        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactors.add(i);
                number /= i;
            }
        }

        if(number > 1) {
            primeFactors.add(number);
        }

        return primeFactors;
    }

}
