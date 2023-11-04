package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getWeatherData(View view) {
        // Haetaan säätiedot Volley-kirjastolla
        String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?q=Tampere&appid=8b08501d9a89bb0cd70d61a62642c0cf&units=metric";

        StringRequest request = new StringRequest(Request.Method.GET, WEATHER_URL, response -> {
            // Säätiedot haettu onnistuneesti.
            parseWeatherJsonAndUpdateUi(response);

        }, error -> {
            // Verkkovirhe yms.
            Toast.makeText(this, "NETWORK ERROR", Toast.LENGTH_LONG).show();
        });

        // Lähetetään request Volleylla == lisätään request RequestQueueen
        Volley.newRequestQueue(this).add(request);

        // Tämän jälkeen tullaan jompaan kumpaan callbackin haaraan, kun request on valmis
    }

    private void parseWeatherJsonAndUpdateUi(String response) {
        // Parsitaan JSON ja päivitetään näytölle lämpötila, säätila ja tuulen nopeus
        try {
            JSONObject weatherJSON = new JSONObject(response);
            String weather = weatherJSON.getJSONArray("weather").getJSONObject(0).getString("main");
            double temperature = weatherJSON.getJSONObject("main").getDouble("temp");
            double wind = weatherJSON.getJSONObject("wind").getDouble("speed");

            TextView weatherTextView = findViewById(R.id.weatherTextView);
            weatherTextView.setText(weather);
            TextView temperatureTextView = findViewById(R.id.temperatureTextView);
            temperatureTextView.setText("" + temperature + "C");
            TextView windTextView = findViewById(R.id.windTextView);
            windTextView.setText("" + wind + " m/s");
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public void openForecast(View view) {
        String forecastUrl = "https://openweathermap.org/city/634963";
        Uri forecastUri = Uri.parse(forecastUrl);
        Intent intent = new Intent(Intent.ACTION_VIEW, forecastUri);
        // Tarkastetaan/varmistetaan, onko laitteella tämän intentin toteuttava palvelu
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Webbiselain löytyy
            startActivity(intent);
        } else {
            // Ei webbiselainta, ei voida näyttää.
            Toast.makeText(this, "WEB BROWSER NOT FOUND", Toast.LENGTH_LONG).show();
        }
    }
}