package com.example.exercise03;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void handleClick(View v) {
        if (v instanceof Button) {
            Button clickedButton = (Button) v;
            clickedButton.setBackgroundTintList(getResources().getColorStateList(R.color.btnYellow));
        }
    }
}