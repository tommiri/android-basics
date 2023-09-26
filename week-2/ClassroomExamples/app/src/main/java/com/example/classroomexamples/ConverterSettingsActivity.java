package com.example.classroomexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ConverterSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter_settings);
        Intent intent = getIntent();
        String message = intent.getStringExtra("HELLO_MESSAGE");

    }

    public void backToConverter(View view) {
        // Takaisin päänäkymään
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}