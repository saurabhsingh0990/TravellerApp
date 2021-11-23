package com.example.traveller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    Button login, signup;
    LinearLayout page;
    EditText name, eid, pnumber, password, username, userpassword;
    userDBhelper DB;
    public void getElements(){
        page = findViewById(R.id.page);
        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        name = findViewById(R.id.name);
        eid = findViewById(R.id.eid);
        pnumber = findViewById(R.id.pnumber);
        password = findViewById(R.id.password);
        username = findViewById(R.id.username);
        userpassword = findViewById(R.id.userpassword);
        DB = new userDBhelper(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getElements();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean userexist  = checkUser();
                if (userexist) {
                    Intent secondPage = new Intent(MainActivity.this, loadingScreen.class);
                    startActivity(secondPage);
                }
                else
                    Toast.makeText(MainActivity.this, "user Not Found", Toast.LENGTH_SHORT).show();

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameTXT = name.getText().toString();
                String eidTXT = eid.getText().toString();
                String pnumberTXT = pnumber.getText().toString();
                String passwordTXT = password.getText().toString();
                Boolean checkInsert = DB.insertUser(nameTXT, eidTXT, passwordTXT, pnumberTXT);
                if (checkInsert) {
                    Toast.makeText(MainActivity.this, "User Created !", Toast.LENGTH_SHORT).show();
                    name.setText(""); eid.setText(""); pnumber.setText(""); password.setText("");
                } else
                    Toast.makeText(MainActivity.this, "User not created", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean checkUser() {
        String loginName;
        String loginPassword;
        loginName = username.getText().toString();
        loginPassword = userpassword.getText().toString();
        Cursor res = DB.getdata();
        if(res.getCount() <= 0)
            return false;
        while(res.moveToNext()){
//            Toast.makeText(this, res.getString(2), Toast.LENGTH_SHORT).show();
            if (res.getString(1).equals(loginName) && res.getString(2).equals(loginPassword)) {
                Toast.makeText(this, "Welcome "+ res.getString(0)+" !", Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;

    }
}