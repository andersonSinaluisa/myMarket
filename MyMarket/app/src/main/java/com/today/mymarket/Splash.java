package com.today.mymarket;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Handler;

import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.today.mymarket.Principal.Principal;

import static com.today.mymarket.DB.Preference.getID;

public class Splash extends AppCompatActivity {
    private final int DURACION_SPLASH = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        FirebaseApp.initializeApp(this);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.setPersistenceEnabled(true);


        // [START storage_field_initialization]
        FirebaseStorage storage = FirebaseStorage.getInstance();
        // [END storage_field_initialization]


        getSupportActionBar().hide();

        new Handler().postDelayed(new Runnable(){
            public void run(){
                if(getID(Splash.this)==null){
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);

                }else{
                    Intent intent = new Intent(Splash.this, Principal.class);
                    startActivity(intent);
                }



                finish();
            };
        }, DURACION_SPLASH);
    }




}
