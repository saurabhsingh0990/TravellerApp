package com.example.traveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import java.util.Timer;
import java.util.TimerTask;

public class loadingScreen extends AppCompatActivity {
    ProgressBar pb;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_screen);
        progress();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //redirecting to second screen
                startActivity(new Intent(loadingScreen.this, secondpage.class));
                finish();

            }
        }, 6000);
    }

    private void progress() {
        pb = findViewById(R.id.progressBar);
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                counter++;
                pb.setProgress(counter);

                if (counter == 100)
                    t.cancel();

            }
        };
        t.schedule(tt, 0,50);
    }
}