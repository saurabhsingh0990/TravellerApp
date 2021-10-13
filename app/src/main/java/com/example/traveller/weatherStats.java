package com.example.traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class weatherStats extends AppCompatActivity {
    LinearLayout fiveDayForecast;
    TextView placeName;
    TextView temperature;
    TextView windSpeed;
    TextView humidity;
    TextView feelsLike;
    ImageView weatherImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_stats);
        fetchElements();
        String place = getIntent().getStringExtra(detailview.Nameofplace);
        placeName.setText(place);
        currentWeather(place,temperature,windSpeed,humidity,feelsLike,weatherImage);
        for(int i = 0 ; i < 5 ; i++){
            View weatherCard = getLayoutInflater().inflate(R.layout.weathercard, null);
            fiveDayForecast.addView(weatherCard);
        }

    }
    void fetchElements(){
        placeName = findViewById(R.id.placeName);
        temperature = findViewById(R.id.temperature);
        windSpeed = findViewById(R.id.windSpeed);
        humidity = findViewById(R.id.humidity);
        feelsLike = findViewById(R.id.feelsLike);
        weatherImage = findViewById(R.id.weatherImage);
        fiveDayForecast = findViewById(R.id.fiveDayForecast);
    }
    void currentWeather(String place,TextView temperature, TextView windSpeed, TextView humidity, TextView feelsLike, ImageView weatherImage){
        String api = "e86c4d57cb044840b0f75101212309";
        String url = "https://api.weatherapi.com/v1/current.json?key=e86c4d57cb044840b0f75101212309&q="+place+"&aqi=yes";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject current =  response.getJSONObject("current");
                    String degree = current.getString("temp_c");
                    temperature.setText(degree);
                    JSONObject condition = current.getJSONObject("condition");
                    String status = condition.getString("text");
                    feelsLike.setText(status);
                    String windspeed = current.getString("wind_kph");;
                    windSpeed.setText(windspeed);
                    String humid = current.getString("humidity");
                    humidity.setText(humid);
                    String imageUrl = condition.getString("icon");
                    imageUrl = "https:" + imageUrl;
//                    URL url = new URL(imageUrl);
                    Glide.with(weatherStats.this).load(imageUrl).into(weatherImage);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(weatherStats.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);

    }
}