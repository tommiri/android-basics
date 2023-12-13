package com.example.weatherapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private boolean isImperial;
    private boolean isPrecise;
    private String cityName;
    private Location userLocation;
    private TextInputEditText locationInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Handle the intent data and saved instance state
        handleIntent(getIntent(), savedInstanceState);

        // Initialize UI components
        locationInput = findViewById(R.id.locationInputEdit);
        locationInput.setText(cityName);

        // Initialize and set up the bottom navigation bar
        NavigationBarView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setSelectedItemId(R.id.weatherNav);
        navigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.weatherNav) {
                return true;
            } else if (item.getItemId() == R.id.settingsNav) {
                openSettings();
                overridePendingTransition(0, 0);
                return true;
            }
            return false;
        });

        // Set up the weather button click listener
        Button weatherBtn = findViewById(R.id.getWeatherBtn);
        weatherBtn.setOnClickListener(v -> {
            // Update city name and fetch weather data
            cityName = String.valueOf(locationInput.getText());
            getWeatherData();
        });

        // If precise location is enabled, start GPS updates
        if (isPrecise) {
            startGPS();
        }

        // Fetch initial weather data
        getWeatherData();
    }

    /**
     * Handle intent data and saved instance
     */
    private void handleIntent(Intent intent, Bundle savedInstanceState) {
        isImperial = intent.getBooleanExtra("IS_IMPERIAL", false);
        isPrecise = intent.getBooleanExtra("IS_PRECISE_LOCATION", false);
        // Get city name from savedInstanceState or Intent, or use default
        cityName = (savedInstanceState != null)
                ? savedInstanceState.getString("CITY_NAME", getString(R.string.default_city))
                : (intent.getStringExtra("CITY_NAME") != null)
                ? intent.getStringExtra("CITY_NAME")
                : getString(R.string.default_city);
    }

    /**
     * Fetch weather data from the OpenWeatherMap API
     */
    public void getWeatherData() {
        String units = isImperial ? "imperial" : "metric";
        String apiKey = BuildConfig.API_KEY;
        String queryParam = isPrecise && userLocation != null
                ? String.format(Locale.getDefault(), "lat=%f&lon=%f", userLocation.getLatitude(), userLocation.getLongitude())
                : String.format("q=%s", cityName);

        String WEATHER_URL = String.format("https://api.openweathermap.org/data/2.5/weather?%s&appid=%s&units=%s", queryParam, apiKey, units);

        // Create a network request to fetch weather data
        StringRequest request = new StringRequest(Request.Method.GET, WEATHER_URL, this::parseWeatherJsonAndUpdateUi, error -> Toast.makeText(this, getString(R.string.data_fetch_error), Toast.LENGTH_LONG).show());

        // Add the request to the Volley request queue
        Volley.newRequestQueue(this).add(request);
    }

    /**
     * Parse the JSON response and update the UI with weather information
     */
    private void parseWeatherJsonAndUpdateUi(String response) {
        try {
            JSONObject weatherJSON = new JSONObject(response);
            String city = weatherJSON.getString("name");
            int temperature = (int) weatherJSON.getJSONObject("main").getDouble("temp");
            int wind = (int) weatherJSON.getJSONObject("wind").getDouble("speed");
            String iconID = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("icon");

            String imageUrl = String.format("http://openweathermap.org/img/wn/%s@4x.png", iconID);

            TextView locationTextView = findViewById(R.id.locationTextView);
            TextView temperatureTextView = findViewById(R.id.temperatureTextView);
            TextView windTextView = findViewById(R.id.windTextView);
            ImageView weatherIconView = findViewById(R.id.weatherIconView);

            String tempString = String.format(Locale.getDefault(), "%d%s", temperature, isImperial ? "°F" : "℃");
            String windString = String.format(Locale.getDefault(), "%d %s", wind, isImperial ? "mph" : "m/s");

            locationTextView.setText(city);
            temperatureTextView.setText(tempString);
            windTextView.setText(windString);

            cityName = city;
            if (isPrecise && userLocation != null) {
                locationInput.setText(cityName);
            }

            Glide.with(this).load(imageUrl).into(weatherIconView);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Start GPS updates to get the user's location
     */
    public void startGPS() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            return;
        }

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 5000, location -> userLocation = location);
    }

    /**
     * Navigate to the settings activity
     */
    public void openSettings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        intent.putExtra("CITY_NAME", cityName);
        intent.putExtra("IS_IMPERIAL", isImperial);
        intent.putExtra("IS_PRECISE_LOCATION", isPrecise);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save instance state variables
        outState.putBoolean("IS_IMPERIAL", isImperial);
        outState.putBoolean("IS_PRECISE_LOCATION", isPrecise);
        outState.putString("CITY_NAME", cityName);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore instance state variables
        isImperial = savedInstanceState.getBoolean("IS_IMPERIAL", false);
        isPrecise = savedInstanceState.getBoolean("IS_PRECISE_LOCATION", false);
        cityName = savedInstanceState.getString("CITY_NAME", getString(R.string.default_city));
    }
}