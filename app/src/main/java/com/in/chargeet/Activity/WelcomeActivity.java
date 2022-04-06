package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.in.chargeet.R;

public class WelcomeActivity extends AppCompatActivity {

    LinearLayout SignIn, SignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        init();

    }

    public void init() {

        SignIn = findViewById(R.id.SignIn);
        SignUp = findViewById(R.id.SignUp);

        clickEvent();
    }

    public void clickEvent() {

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}