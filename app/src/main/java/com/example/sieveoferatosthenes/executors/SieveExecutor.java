package com.example.sieveoferatosthenes.executors;

import android.os.Looper;

import com.example.sieveoferatosthenes.utils.SieveParser;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SieveExecutor {

    // Using a single thread executor for simplicity. You can choose other types based on your requirements.
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    /**
     * Executes the Sieve of Eratosthenes algorithm using the provided upper limit.
     *
     * @param number   The upper limit for the algorithm.
     * @param callback The callback to receive computed prime numbers.
     */
    public void executeSieve(int number, OnPrimesCalculated callback) {
        executorService.execute(() -> {
            List<Integer> primes = SieveParser.sieveOfEratosthenes(number);
            // Assuming the callback methods are intended to be called on the main thread.
            new android.os.Handler(Looper.getMainLooper()).post(() -> callback.onResult(primes));
        });
    }

    /**
     * OnPrimesCalculated is an interface that defines a callback method to be used
     * when the prime numbers are computed. This ensures that components
     * using SieveExecutor can easily receive and handle the results.
     */
    public interface OnPrimesCalculated {
        void onResult(List<Integer> primes);
    }
}
