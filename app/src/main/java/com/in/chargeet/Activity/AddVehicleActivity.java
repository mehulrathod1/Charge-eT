package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.chargeet.R;

public class AddVehicleActivity extends AppCompatActivity {

    ImageView backButton;
    TextView toolbarHading,model;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        init();
    }

    public void init() {

        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        model = findViewById(R.id.model);

        toolbarHading.setText("My vehicle");


        alertDialog = new AlertDialog.Builder(AddVehicleActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.select_model_popup, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.show();
            }
        });

    }

}