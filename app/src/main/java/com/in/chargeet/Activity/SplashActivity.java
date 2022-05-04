package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.in.chargeet.R;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        moveNext(MainActivity.class);
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