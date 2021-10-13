package com.example.traveller;

import android.content.Context;
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

public class fetchWeather  {
    void fetchData(String stateName) {
        String[] weather = {"", "", ""};
        String city = "london";
        String api = "e86c4d57cb044840b0f75101212309";
        String url = "https://api.weatherapi.com/v1/current.json?key=e86c4d57cb044840b0f75101212309&q=" + city + "&aqi=yes";
        RequestQueue queue = Volley.newRequestQueue(null);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject current = response.getJSONObject("current");
                    String degree = current.getString("temp_c");
                    weather[0] = degree;
                    JSONObject location = response.getJSONObject("location");
                    String name = location.getString("name");
                    weather[1] = name;
                    JSONObject condition = current.getJSONObject("condition");
                    String status = condition.getString("text");
                    String imageUrl = condition.getString("icon");
                    imageUrl = "https:" + imageUrl;
                    weather[2] = imageUrl;
//                    URL url = new URL(imageUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(null, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }

}

