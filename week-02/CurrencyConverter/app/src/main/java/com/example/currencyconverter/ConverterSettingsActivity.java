package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class ConverterSettingsActivity extends AppCompatActivity {
    private float conversionRate;
    private EditText currencyOneName;
    private EditText currencyTwoName;
    private EditText conversionRateText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter_settings);

        Intent intent = getIntent();

        currencyOneName = findViewById(R.id.currencyOneName);
        currencyTwoName = findViewById(R.id.currencyTwoName);
        conversionRateText = findViewById(R.id.editTextConversionRate);

        currencyOneName.setText(intent.getStringExtra("CURRENCY_1"));
        currencyTwoName.setText(intent.getStringExtra("CURRENCY_2"));

        conversionRate = intent.getFloatExtra("CONVERSION_RATE", 0.9433f);
        conversionRateText.setText(Float.toString(conversionRate));
    }

    public void backToConverter(View view) {
        if (!isValidInput(currencyOneName) || !isValidInput(currencyTwoName) || !isValidInput(conversionRateText)) {
            return;
        }

        // Takaisin päänäkymään
        Intent intent = new Intent(this, MainActivity.class);

        // Välitetään asetuksena valuutat ja muuntokerroin
        intent.putExtra("CURRENCY_1", currencyOneName.getText().toString());
        intent.putExtra("CURRENCY_2", currencyTwoName.getText().toString());

        conversionRate = Float.parseFloat(conversionRateText.getText().toString());
        intent.putExtra("CONVERSION_RATE", conversionRate);
        startActivity(intent);
    }

    private boolean isValidInput(EditText view) {
        if (TextUtils.isEmpty(view.getText())) {
            view.setError(getString(R.string.enter_value_error));
            return false;
        }
        return true;
    }
}