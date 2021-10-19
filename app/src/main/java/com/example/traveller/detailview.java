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
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    Toolbar toolbar;
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
        navigationDrawer();
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
                        Toast.makeText(detailview.this, "Opening Home", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_dev:
                        Toast.makeText(detailview.this, "Opening dev", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_logout:
                        Toast.makeText(detailview.this, "Opening logout", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_settings:
                        Toast.makeText(detailview.this, "Opening settings", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.menu_feedback:
                        Toast.makeText(detailview.this, "Opening feedback", Toast.LENGTH_SHORT).show();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }

                return true;
            }
        });
    }

}