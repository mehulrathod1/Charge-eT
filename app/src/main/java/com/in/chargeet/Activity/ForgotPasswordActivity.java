package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.in.chargeet.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    Button btnContinue, btnContinueToOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
    }


    public void init() {

        btnContinue = findViewById(R.id.btnContinue);


        alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.popup_forgot_password, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();
        btnContinueToOtp = dialogLayout.findViewById(R.id.btnContinueToOtp);


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

        btnContinueToOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                startActivity(intent);
            }
        });
    }
}