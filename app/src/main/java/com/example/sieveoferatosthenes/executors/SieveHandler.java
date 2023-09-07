package com.example.sieveoferatosthenes.executors;

import android.os.Handler;
import android.os.Looper;
import com.example.sieveoferatosthenes.utils.SieveParser;
import java.util.List;

/**
 * SieveHandler is a class designed to execute the Sieve of Eratosthenes
 * algorithm in a separate thread, ensuring that the main UI thread remains
 * responsive. After computation, the result is posted back to the main thread.
 */
public class SieveHandler {

    // Handler tied to the main thread, to post results back after computation.
    private Handler sieveHandler = new Handler(Looper.getMainLooper());

    /**
     * Executes the Sieve of Eratosthenes algorithm for a given number
     * and provides the results via a callback.
     *
     * @param number The upper limit up to which prime numbers need to be generated.
     * @param callback The callback to be invoked with the list of prime numbers.
     */
    public void executeSieve(int number, OnPrimesCalculated callback) {
        // Create a new thread to execute the Sieve algorithm, ensuring
        // that the main UI thread remains free for user interactions.
        Thread sieveThread = new Thread(() -> {
            List<Integer> primes = SieveParser.sieveOfEratosthenes(number);

            // After computing the prime numbers, post the result back to the main thread.
            sieveHandler.post(() -> callback.onResult(primes));
        });

        // Start the computation thread.
        sieveThread.start();
    }

    /**
     * OnPrimesCalculated is an interface that defines a callback to be used
     * when the prime numbers have been computed. This helps in ensuring that
     * activities or fragments that use SieveHandler can get the results.
     */
    public interface OnPrimesCalculated {
        void onResult(List<Integer> primes);
    }
}