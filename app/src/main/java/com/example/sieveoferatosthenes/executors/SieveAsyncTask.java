package com.example.sieveoferatosthenes.executors;

import android.os.AsyncTask;
import com.example.sieveoferatosthenes.utils.SieveParser;
import java.util.List;

/**
 * SieveAsyncTask is an AsyncTask designed to compute the Sieve of Eratosthenes
 * algorithm asynchronously without blocking the main UI thread.
 * Once the computation is complete, the results are delivered through a callback.
 */
public class SieveAsyncTask extends AsyncTask<Integer, Void, List<Integer>> {

    // Callback to be invoked when prime numbers have been computed.
    private OnPrimesCalculated callback;

    /**
     * Constructor for SieveAsyncTask.
     *
     * @param callback The callback to receive computed prime numbers.
     */
    public SieveAsyncTask(OnPrimesCalculated callback) {
        this.callback = callback;
    }

    /**
     * Computes the prime numbers using the Sieve of Eratosthenes algorithm.
     *
     * @param integers An array where the first element represents the
     *                 upper limit for computing prime numbers.
     * @return A list of prime numbers up to the provided upper limit.
     */
    @Override
    protected List<Integer> doInBackground(Integer... integers) {
        return SieveParser.sieveOfEratosthenes(integers[0]);
    }

    /**
     * Delivers the computed prime numbers through the callback.
     * This method runs on the main UI thread.
     *
     * @param primes The computed list of prime numbers.
     */
    @Override
    protected void onPostExecute(List<Integer> primes) {
        callback.onResult(primes);
    }

    /**
     * OnPrimesCalculated is an interface that defines a callback method to be used
     * when the prime numbers are computed. This ensures that components
     * using SieveAsyncTask can easily receive and handle the results.
     */
    public interface OnPrimesCalculated {
        void onResult(List<Integer> primes);
    }
}
