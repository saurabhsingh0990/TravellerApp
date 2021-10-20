package com.example.traveller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class detailview extends AppCompatActivity {
    public  static  final String Nameofplace = "getting place";
    TextView placeNameHolder;
    FloatingActionButton fabMap ;
    FloatingActionButton fabWeather;
    ImageView placeimg;
    float xDownM, yDownM;
    float xDownW, yDownW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);
        Intent intent = getIntent();
        String place = intent.getStringExtra(secondpage.placeName);
        placeNameHolder = findViewById(R.id.placeNameHolder);
        placeNameHolder.setText(place);
        fabMap = findViewById(R.id.map);
        fabWeather = findViewById(R.id.weather);
        placeimg = findViewById(R.id.placeimg);
        placeimg.setImageResource(getResources().getIdentifier(place, "drawable", getPackageName()));
        fabMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mapActivity = new Intent(detailview.this, mapActivity.class);
                startActivity(mapActivity);
            }
        });
        fabMap.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getActionMasked()){
                    case MotionEvent.ACTION_DOWN:
                        xDownM = event.getX();
                        yDownM = event.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float MmovedX, MmovedY;
                        MmovedX = event.getX();
                        MmovedY = event.getY();

                        float MdistanceX = MmovedX - xDownM;
                        float MdistanceY = MmovedY - yDownM;

                        fabMap.setX(fabMap.getX() + MdistanceX);
                        fabMap.setY(fabMap.getY() + MdistanceY);
                        break;
                }
                return false;
            }
        });
        fabWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent weatherActivity = new Intent(detailview.this, weatherStats.class);
                weatherActivity.putExtra(Nameofplace,place);
                startActivity(weatherActivity);

            }
        });
        fabWeather.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                    switch (event.getActionMasked()) {
                        case MotionEvent.ACTION_DOWN:
                            xDownW = event.getX();
                            yDownW = event.getY();
                            break;

                        case MotionEvent.ACTION_MOVE:
                            float WmovedX, WmovedY;
                            WmovedX = event.getX();
                            WmovedY = event.getY();

                            float WdistanceX = WmovedX - xDownW;
                            float WdistanceY = WmovedY - yDownW;

                            fabWeather.setX(fabWeather.getX() + WdistanceX);
                            fabWeather.setY(fabWeather.getY() + WdistanceY);
                            break;
                    }
                    return false;
                }
        });
    }



}