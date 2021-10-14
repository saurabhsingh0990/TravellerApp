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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class weatherStats extends AppCompatActivity {
    LinearLayout fiveDayForecast;
    TextView placeName;
    TextView temperature;
    TextView windSpeed;
    TextView humidity;
    TextView feelsLike;
    ImageView weatherImage;
    TextView temper;
    TextView date;
    TextView wind;
    ImageView weatherIcon, weatherback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_stats);
        fetchElements();
        String place = getIntent().getStringExtra(detailview.Nameofplace);
        placeName.setText(place);
        currentWeather(place,temperature,windSpeed,humidity,feelsLike,weatherImage);
        for(int i = 0 ; i < 2 ; i++){
            View weatherCard = getLayoutInflater().inflate(R.layout.weathercard, null);
            temper = weatherCard.findViewById(R.id.temper);
            date = weatherCard.findViewById(R.id.date);
            wind = weatherCard.findViewById(R.id.wind);
            weatherIcon = weatherCard.findViewById(R.id.weatherIcon);
            futureForecast(place, temper, wind, weatherIcon, date,i+1);
            fiveDayForecast.addView(weatherCard);
        }

    }
    void fetchElements(){
        weatherback = findViewById(R.id.weatherback);
        placeName = findViewById(R.id.placeName);
        temperature = findViewById(R.id.temperature);
        windSpeed = findViewById(R.id.windSpeed);
        humidity = findViewById(R.id.humidity);
        feelsLike = findViewById(R.id.feelsLike);
        weatherImage = findViewById(R.id.weatherImage);
        fiveDayForecast = findViewById(R.id.fiveDayForecast);
    }

    void futureForecast(String place,TextView temperature, TextView wind, ImageView weatherIcon, TextView Dateview, int dayCount){
        String api = "e86c4d57cb044840b0f75101212309";
        String url = "http://api.weatherapi.com/v1/forecast.json?key=e86c4d57cb044840b0f75101212309&q="+place+"&days=5&aqi=no&alerts=no";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONObject forecastObj = response.getJSONObject("forecast");
                    JSONObject dayForecast = forecastObj.getJSONArray("forecastday").getJSONObject(dayCount);
                    JSONObject day = dayForecast.getJSONObject("day");
                    String temp = day.getString("avgtemp_c").toString();
                    temperature.setText(temp);
                    String windSpeed = day.getString("avgvis_km").toString();
                    wind.setText(windSpeed);
                    String fetchDate = dayForecast.getString("date");
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date convertingDate = sdf.parse(fetchDate);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM");
                    String newDate = sdf1.format(convertingDate);
                    Dateview.setText(newDate);
                    JSONObject condition = day.getJSONObject("condition");
                    String imageUrl = condition.getString("icon");
                    imageUrl = "https:" + imageUrl;
//                    URL url = new URL(imageUrl);
                    Glide.with(weatherStats.this).load(imageUrl).into(weatherIcon);



                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(weatherStats.this, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        queue.add(request);

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
                    int isDay = current.getInt("is_day");
                    String dayNight;
                    if(isDay == 1){
                        dayNight = "day";
                    }
                    else{
                        dayNight = "night";
                    }

                    weatherback.setImageResource(getResources().getIdentifier(dayNight, "drawable", getPackageName()));
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