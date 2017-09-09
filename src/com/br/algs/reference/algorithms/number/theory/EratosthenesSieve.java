package com.br.algs.reference.algorithms.number.theory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rene on 08/09/17.
 */
public class EratosthenesSieve {

    public static void main(String[] args) {
        boolean[] primes1 = eratosthenesSieve(100);
        List<Integer> primesList = eratosthenesSieveGetOnlyPrimes(100);

        System.out.println(isPrime(2147483647) + " Expected: true");
        System.out.println(isPrime(136117223861L) + " Expected: false");
    }

    // Faster to verify, it is O(n) but only check primes up to sqrt(n)
    private static boolean[] eratosthenesSieve(long number) {
        int maxNumberToCheck = (int) Math.floor(Math.sqrt(number));

        boolean[] isPrime = new boolean[(int) number + 1];

        //1- Mark all numbers as prime
        for(int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        //2- Remove numbers multiple of the first element
        //3- Repeat until we finish verifying the maxNumberToCheck

        for(long i = 2; i <= maxNumberToCheck; i++) { //maxNumberToCheck is also equal to: i * i <= n

            if(isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }
            }
        }

        return isPrime;
    }

    private static List<Integer> primeNumbers;
    private static boolean[] isPrime;
    private static int MAX_VALUE = 10000000;

    private static boolean isPrime(long number) {
        if(number <= MAX_VALUE) {
            eratosthenesSieveGetOnlyPrimes(number);
            return isPrime[(int) number];
        } else {
            //Only works if number <= (last prime in primeNumbers)^2
            eratosthenesSieveGetOnlyPrimes(MAX_VALUE);

            for(int i = 0; i < primeNumbers.size(); i++) {
                if(number % primeNumbers.get(i) == 0) {
                    return false;
                }
            }

            return true;
        }
    }

    // Returns a list containing only the primes up to number
    // Slightly slower to verify, but still O(n), and returns a list with only the necessary numbers
    // Can be used to verify the primality of numbers > 10^7
    private static List<Integer> eratosthenesSieveGetOnlyPrimes(long number) {

        primeNumbers = new ArrayList<>();
        isPrime = new boolean[(int) number + 1];

        //1- Mark all numbers as prime
        for(int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
        }

        //2- Remove numbers multiple of the current element
        //3- Repeat until we finish verifying the maxNumberToCheck

        for(long i = 2; i <= number; i++) {

            if(isPrime[(int) i]) {
                for (long j = i * i; j < isPrime.length; j += i) {
                    isPrime[(int) j] = false;
                }

                primeNumbers.add((int) i);
            }
        }

        return primeNumbers;
    }

}
