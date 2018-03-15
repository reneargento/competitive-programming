package com.br.training.unicamp.summer.school2018.contest1;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by rene on 22/01/18.
 */
// http://codeforces.com/group/3qadGzUdR4/contest/101704/problem/U
public class PrimeNumber {

    public static void main(String[] args) {
        List<Integer> primesList = eratosthenesSieveGetOnlyPrimes(1000000);

        Scanner scanner = new Scanner(System.in);
        int number = scanner.nextInt();
        System.out.println(primesList.get(number - 1));
    }

    private static List<Integer> primeNumbers;
    private static boolean[] isPrime;

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
