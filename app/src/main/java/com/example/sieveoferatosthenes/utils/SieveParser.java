package com.example.sieveoferatosthenes.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * SieveParser is a utility class that contains methods related to
 * the Sieve of Eratosthenes algorithm, which is used to generate
 * prime numbers up to a given limit.
 */
public class SieveParser {

    /**
     * Computes and returns a list of prime numbers up to the given limit n
     * using the Sieve of Eratosthenes algorithm.
     *
     * @param n The upper limit up to which prime numbers need to be generated.
     * @return A list containing prime numbers up to the given limit n.
     */
    public static List<Integer> sieveOfEratosthenes(int n) {
        // Create an array and set all entries to true.
        // A value in prime[i] will be false if i is NOT a prime, else true bool val.
        boolean[] prime = new boolean[n + 1];
        Arrays.fill(prime, true);

        int p = 2;  // Start with the first prime number, which is 2.

        // The main loop that iterates until p squared is greater than n.
        while (p * p <= n) {
            // If prime[p] is still true, then it is a prime number.
            if (prime[p]) {
                // Update all multiples of p to false, which means they are not primes.
                for (int i = p * p; i <= n; i += p) {
                    prime[i] = false;
                }
            }
            p++;  // Move to the next number.
        }

        // Collect all the prime numbers into a list.
        List<Integer> primeNumbers = new ArrayList<>();
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                primeNumbers.add(i);
            }
        }
        return primeNumbers;
    }
}
