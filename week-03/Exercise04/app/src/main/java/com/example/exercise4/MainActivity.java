package com.example.exercise4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private String message = "Counter: ";
    private int clickCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Talletetaan counterin nykyinen arvo
        outState.putInt("COUNTER_VALUE", clickCounter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        clickCounter = savedInstanceState.getInt("COUNTER_VALUE", 0);
        TextView counter = findViewById(R.id.counter);
        counter.setText(message + clickCounter);
    }

    public void handleClick(View view) {
        clickCounter++;
        TextView counter = findViewById(R.id.counter);
        counter.setText(message + clickCounter);
    }

}