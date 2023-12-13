package com.example.weatherapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.materialswitch.MaterialSwitch;
import com.google.android.material.navigation.NavigationBarView;

/**
 * SettingsActivity allows the user to configure preferences such as units and location precision.
 */
public class SettingsActivity extends AppCompatActivity {

    // Switches for toggling preferences
    MaterialSwitch imperialSwitch;
    MaterialSwitch locationSwitch;

    // Preference state variables
    private boolean isImperial;
    private boolean isPrecise;
    private String cityName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Retrieve intent data from the calling activity
        Intent intent = getIntent();
        isImperial = intent.getBooleanExtra("IS_IMPERIAL", false);
        isPrecise = intent.getBooleanExtra("IS_PRECISE_LOCATION", false);
        cityName = intent.getStringExtra("CITY_NAME");

        // Initialize switches and set their initial states
        imperialSwitch = findViewById(R.id.imperialSwitch);
        locationSwitch = findViewById(R.id.locationSwitch);
        setSwitches();

        // Bottom navigation setup
        NavigationBarView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.settingsNav);
        navigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.settingsNav) {
                return true;
            } else if (item.getItemId() == R.id.weatherNav) {
                // Navigate back to the main weather screen
                backToHome();
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });
    }

    /**
     * Set the initial states of switches based on the retrieved preferences.
     */
    private void setSwitches() {
        imperialSwitch.setChecked(isImperial);
        locationSwitch.setChecked(isPrecise);
    }

    /**
     * Navigate back to the main weather screen with updated preferences.
     */
    public void backToHome() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("CITY_NAME", cityName);
        intent.putExtra("IS_IMPERIAL", imperialSwitch.isChecked());
        intent.putExtra("IS_PRECISE_LOCATION", locationSwitch.isChecked());
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save current preference states during configuration changes
        outState.putBoolean("IS_IMPERIAL", imperialSwitch.isChecked());
        outState.putBoolean("IS_PRECISE_LOCATION", locationSwitch.isChecked());
        outState.putString("CITY_NAME", cityName);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore preference states after configuration changes
        isImperial = savedInstanceState.getBoolean("IS_IMPERIAL", false);
        isPrecise = savedInstanceState.getBoolean("IS_PRECISE_LOCATION", false);
        cityName = savedInstanceState.getString("CITY_NAME", getString(R.string.default_city));
    }
}