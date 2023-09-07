package com.example.sieveoferatosthenes.executors;

import android.os.AsyncTask;

import com.example.sieveoferatosthenes.utils.SieveParser;

import java.util.List;

public class SieveAsyncTask extends AsyncTask<Integer, Void, List<Integer>> {
    private OnPrimesCalculated callback;

    public SieveAsyncTask(OnPrimesCalculated callback) {
        this.callback = callback;
    }

    @Override
    protected List<Integer> doInBackground(Integer... integers) {
        return SieveParser.sieveOfEratosthenes(integers[0]);
    }

    @Override
    protected void onPostExecute(List<Integer> primes) {
        callback.onResult(primes);
    }

    public interface OnPrimesCalculated {
        void onResult(List<Integer> primes);
    }
}
