package com.example.sieveoferatosthenes.executors;

import android.os.Handler;
import android.os.Looper;

import com.example.sieveoferatosthenes.utils.SieveParser;

import java.util.List;

public class SieveHandler {
    private Handler sieveHandler = new Handler(Looper.getMainLooper());

    public void executeSieve(int number, OnPrimesCalculated callback) {
        Thread sieveThread = new Thread(() -> {
            List<Integer> primes = SieveParser.sieveOfEratosthenes(number);
            sieveHandler.post(() -> callback.onResult(primes));
        });
        sieveThread.start();
    }

    public interface OnPrimesCalculated {
        void onResult(List<Integer> primes);
    }
}
