package com.example.sieveoferatosthenes;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sieveoferatosthenes.databinding.ActivityMainBinding;
import com.example.sieveoferatosthenes.executors.SieveAsyncTask;
import com.example.sieveoferatosthenes.executors.SieveExecutor;
import com.example.sieveoferatosthenes.executors.SieveHandler;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TextView numberResults;
    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private NumberAdapter numberAdapter;
    private SieveHandler sieveHandler = new SieveHandler();
    private final SieveExecutor sieveExecutor = new SieveExecutor();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        numberResults = findViewById(R.id.numberResults);

        numberAdapter = new NumberAdapter();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(numberAdapter);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);

        // Infinite scrolling logic
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int position = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        int previousSelectedItem = numberAdapter.getSelectedItem();
                        numberAdapter.setSelectedItem(position);
                        numberAdapter.notifyItemChanged(previousSelectedItem);  // Refresh previous selected item
                        numberAdapter.notifyItemChanged(position);  // Refresh newly selected item

                        sieveExecutor.executeSieve(numberAdapter.numbers.get(position), new SieveExecutor.OnPrimesCalculated() {
                            @Override
                            public void onResult(List<Integer> primes) {
                                // This is your callback; you can update your UI here
                                if (primes != null && !primes.isEmpty()) {
                                    String primeNumbersString = android.text.TextUtils.join(", ", primes);
                                    numberResults.setText(primeNumbersString);
                                }
                            }
                        });

//                        sieveHandler.executeSieve(numberAdapter.numbers.get(position), primes -> {
//                            Log.e(TAG, primes.toString());
//                            String primeNumbersString = android.text.TextUtils.join(", ", primes);
//                            numberResults.setText(primeNumbersString);
//                            numberResults.setTextSize(17);
//                        });

//                        new SieveAsyncTask(primes -> {
//                            Log.e(TAG, primes.toString());
//                            String primeNumbersString = android.text.TextUtils.join(", ", primes);
//                            numberResults.setText(primeNumbersString);
//                            numberResults.setTextSize(17);
//                        }).execute(numberAdapter.numbers.get(position));
                    }
                }
            }
        });
    }

    private class NumberAdapter extends RecyclerView.Adapter<NumberAdapter.NumberViewHolder> {

        private List<Integer> numbers;
        private int selectedItem = -1;  // Track the currently selected item

        NumberAdapter() {
            numbers = new ArrayList<>();
            for (int i = 1; i <= 10000; i++) {
                numbers.add(i);
            }
        }

        int getSelectedItem() {
            return selectedItem;
        }

        void setSelectedItem(int position) {
            this.selectedItem = position;
        }

        @Override
        public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new NumberViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(NumberViewHolder holder, int position) {
            holder.bind(numbers.get(position));
        }

        @Override
        public int getItemCount() {
            return numbers.size();
        }

        class NumberViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            NumberViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }

            void bind(int number) {
                textView.setText(String.valueOf(number));
                if (getAdapterPosition() == selectedItem) {
                    textView.setTextColor(Color.RED); // Highlight the selected item with a red color
                    textView.setTextSize(24);
                } else {
                    textView.setTextColor(Color.BLACK); // Default color
                    textView.setTextSize(16);
                }
            }
        }
    }
}
