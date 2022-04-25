package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.in.chargeet.R;

public class MyWalletActivity extends AppCompatActivity {

    ImageView backButton;
    TextView toolbarHading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        init();
    }

    public void init() {

        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        toolbarHading.setText("wallet");


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });
    }
}