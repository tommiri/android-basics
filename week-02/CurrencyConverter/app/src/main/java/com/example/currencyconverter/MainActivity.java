package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private float conversionRate;
    private TextView inputCurrencyName;
    private TextView outputCurrencyName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        conversionRate = intent.getFloatExtra("CONVERSION_RATE", 0.9433f);
        inputCurrencyName = findViewById(R.id.inputCurrencyName);
        outputCurrencyName = findViewById(R.id.outputCurrencyName);

        if (intent.hasExtra("CURRENCY_1") && intent.hasExtra("CURRENCY_2")) {
            String inputCurrencyNameValue = intent.getStringExtra("CURRENCY_1");
            String outputCurrencyNameValue = intent.getStringExtra("CURRENCY_2");
            inputCurrencyName.setText(inputCurrencyNameValue);
            outputCurrencyName.setText(outputCurrencyNameValue);
        }
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, ConverterSettingsActivity.class);
        intent.putExtra("CONVERSION_RATE", conversionRate);
        intent.putExtra("CURRENCY_1", inputCurrencyName.getText().toString());
        intent.putExtra("CURRENCY_2", outputCurrencyName.getText().toString());
        startActivity(intent);
    }

    public void convertCurrency(View view) {
        // Luetaan source-kentästä luku, tehdään muunnos ja kirjoitetaan destination
        // -kenttään valuuttamuunnos
        EditText inputCurrencyValue = findViewById(R.id.inputCurrencyValue);
        EditText outputCurrencyValue = findViewById(R.id.outputCurrencyValue);

        if (TextUtils.isEmpty(inputCurrencyValue.getText())) {
            inputCurrencyValue.setError(getString(R.string.enter_value_error));
            return;
        }

        // Luetaan lähdevaluutta tekstikentästä ja muunnetaan floatiksi
        String inputCurrencyString = inputCurrencyValue.getText().toString();
        float inputCurrencyAmount = Float.parseFloat(inputCurrencyString);

        // Tehdään valuuttamuunnos
        float outputCurrency = inputCurrencyAmount * conversionRate;

        // Kirjoitetaan tulos kohdevaluutta-kenttään
        outputCurrencyValue.setText(String.format("%.2f", outputCurrency));
    }

    public void swapCurrencies(View view) {
        // Vaihdetaan lähde- ja kohdevaluutta
        String inputCurrencyNameString = inputCurrencyName.getText().toString();
        String outputCurrencyNameString = outputCurrencyName.getText().toString();

        inputCurrencyName.setText(outputCurrencyNameString);
        outputCurrencyName.setText(inputCurrencyNameString);

        // Vaihdetaan myös muuntokerroin
        conversionRate = 1 / conversionRate;
    }
}