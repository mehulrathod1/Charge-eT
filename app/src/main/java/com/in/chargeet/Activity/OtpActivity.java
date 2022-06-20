package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.in.chargeet.R;

public class OtpActivity extends AppCompatActivity {

    Button btnContinue;
    TextView timer, txtResend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        init();
        timer();
    }

    public void init() {

        btnContinue = findViewById(R.id.btnContinue);
        timer = findViewById(R.id.timer);
        txtResend = findViewById(R.id.txtResend);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        txtResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(OtpActivity.this, "" + "true", Toast.LENGTH_SHORT).show();
                timer();
            }
        });
    }

    public void timer() {
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("00 : " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
                txtResend.setClickable(false);
                txtResend.setTextColor(Color.parseColor("#DC4F50"));
            }

            public void onFinish() {
                txtResend.setClickable(true);
                txtResend.setTextColor(Color.parseColor("#4CAF50"));
                timer.setText("done!");
            }

        }.start();
    }
}