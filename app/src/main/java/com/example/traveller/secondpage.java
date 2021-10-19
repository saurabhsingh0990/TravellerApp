package com.example.traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.security.identity.NoAuthenticationKeyAvailableException;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;


public class secondpage extends AppCompatActivity {
    public  static  final String placeName = "getting place";
    Button addbtn;
    Button nextpage;
    LinearLayout play;
    Double lat;
    Double lon;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
    String []states = {"shimla","kolkata","jodhpur","spiti","kerala"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondpage);
        play = findViewById(R.id.play);
        navigationDrawer();
        View.OnClickListener btnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String place = v.getTag().toString();
                thirdPage(place);
            }
        };
        for (int i = 0; i < states.length; i++) {
            View card = getLayoutInflater().inflate(R.layout.card, null);
            nextpage = card.findViewById(R.id.thirdPage);
            nextpage.setTag(states[i]);
            nextpage.setId(i);
            nextpage.setOnClickListener(btnClick);
            TextView name = card.findViewById(R.id.stateName);
            ImageView img = card.findViewById(R.id.statePic);
            ImageView weatherimg = card.findViewById(R.id.weatherimg);
            String stateName = states[i];
            fetchWeather(stateName,weatherimg);
            name.setText(stateName);
            String variableValue = states[i];
            img.setImageResource(getResources().getIdentifier(variableValue, "drawable", getPackageName()));
            play.addView(card);
        }

//        nextpage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) { thirdPage(); }
//        });
    }

    private void navigationDrawer() {
        navigationView = findViewById(R.id.navmenu);
        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        Toast.makeText(secondpage.this, "Opening Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_dev:
                        Toast.makeText(secondpage.this, "Opening dev", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_logout:
                        Toast.makeText(secondpage.this, "Opening logout", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_settings:
                        Toast.makeText(secondpage.this, "Opening settings", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_feedback:
                        Toast.makeText(secondpage.this, "Opening feedback", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
    }

    private void thirdPage(String place) {
        Intent third = new Intent(secondpage.this, detailview.class);
        third.putExtra(placeName,place);
        startActivity(third);
    }
    void fetchWeather(String nameOfState, ImageView weatherimg){
        String city = nameOfState;
        String api = "e86c4d57cb044840b0f75101212309";
        String url = "https://api.weatherapi.com/v1/current.json?key=e86c4d57cb044840b0f75101212309&q="+city+"&aqi=yes";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject current =  response.getJSONObject("current");
                    String degree = current.getString("temp_c");
                    JSONObject location = response.getJSONObject("location");
                    String name = location.getString("name");
                    JSONObject condition = current.getJSONObject("condition");
                    String status = condition.getString("text");
                    String imageUrl = condition.getString("icon");
                    imageUrl = "https:" + imageUrl;
//                    URL url = new URL(imageUrl);
                    Glide.with(secondpage.this).load(imageUrl).into(weatherimg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(secondpage.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(request);
    }
}