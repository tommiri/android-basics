package com.example.helloandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView helloText = findViewById(R.id.helloTextView);
        helloText.setText(R.string.hello_from_code);
    }

    public void buttonClicked(View view) {
        TextView helloText = findViewById(R.id.helloTextView);
        helloText.setText(R.string.button_clicked);
    }
}