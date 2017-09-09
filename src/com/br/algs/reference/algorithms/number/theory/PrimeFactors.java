package com.br.algs.reference.algorithms.number.theory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 29/04/17.
 */
public class PrimeFactors {

    public static void main(String[] args) {
        //Test
        List<Integer> primeFactors = primeFactors(10);
        for(int primeFactor : primeFactors) {
            System.out.print(primeFactor + " ");
        }
    }

    private static List<Integer> primeFactors(int number) {
        List<Integer> primeFactors = new ArrayList<>();

        for (int i = 2; i * i <= number; i++) {
            while (number % i == 0) {
                primeFactors.add(i);
                number /= i;
            }
        }

        //Special case where number is a prime number
        if(number > 1) {
            primeFactors.add(number);
        }

        return primeFactors;
    }

}
