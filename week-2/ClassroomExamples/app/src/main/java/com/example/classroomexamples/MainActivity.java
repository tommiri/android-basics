package com.example.classroomexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openSettings(View view) {
        // Nappulan tapahtumankäsittelijä. Muunna EUR -> HELLO
//        TextView inputCurrencyName = findViewById(R.id.inputCurrencyName);
//        inputCurrencyName.setText("HELLO");
        // Todo: avaa asetukset-aktiviteetti ja välitä sinne viesti tästä aktiviteetista
        Intent intent = new Intent(this, ConverterSettingsActivity.class);
        intent.putExtra("HELLO_MESSAGE", "Hello from MainActivity")
        startActivity(intent);
    }
}