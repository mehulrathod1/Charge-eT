package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.in.chargeet.R;
import com.in.chargeet.Utils.Glob;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        SharedPreferences sharedPref = getSharedPreferences("UserId", Context.MODE_PRIVATE);
        String id = sharedPref.getString("UserId", "null");


        if (id.equals("null")) {
            moveNext(GetStartOneActivity.class);

        } else {

            moveNext(MainActivity.class);
            Glob.userId = id;
        }

    }

    public void moveNext(Class c) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent i = new Intent(getApplicationContext(), c);
                startActivity(i);
                finish();

            }
        }, SPLASH_SCREEN_TIME_OUT);
    }
}