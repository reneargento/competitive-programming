package com.br.algs.reference.algorithms.number.theory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by rene on 08/09/17.
 */
public class EulerPhi {

    public static void main(String[] args) {
        System.out.println(eulerPhi(36) + " Expected: 12");
    }

    //Returns the number of coprimes to the number that are less than the number
    //Example:
    // 36 -> 12
    // {1, 5, 7, 11, 13, 17, 19, 23, 25, 29, 31, 35}
    private static long eulerPhi(long number) {
        List<Integer> primes = eratosthenesSieve(number);
        Iterator<Integer> primesIterator = primes.iterator();

        long currentPrime = primesIterator.next();
        long eulerPhi = number;

        while(number != 1 && currentPrime * currentPrime <= number) {
            if (number % currentPrime == 0) {
                eulerPhi -= eulerPhi / currentPrime;
            }

            while (number % currentPrime == 0) {
                number /= currentPrime;
            }

            currentPrime = primesIterator.next();
        }

        if (number != 1) {
            eulerPhi -= eulerPhi / number; //last factor, the number is a prime
        }

        return eulerPhi;
    }

    private static List<Integer> eratosthenesSieve(long number) {

        List<Integer> primeNumbers = new ArrayList<>();
        boolean[] isPrime = new boolean[(int) number + 1];

        //1- Mark all numbers as prime
        for(int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        //2- Remove numbers multiple of the current element
        //3- Repeat until we finish verifying the maxNumberToCheck

        for(long i = 2; i <= number; i++) {

            if (isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }

                primeNumbers.add((int) i);
            }
        }

        return primeNumbers;
    }

}
