package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChargingActivity extends AppCompatActivity {

    ImageView backButton, increaseHour, decreaseHour, navigateLocation;
    TextView toolbarHading, hour;
    Button btnScheduleCharging, btnScanCode;
    String selectedHour;
    LinearLayout calenderLayout;
    LinearLayout timeLayout;
    String manageSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging);
        init();
    }

    public void init() {

        Glob.progressDialog(this);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        toolbarHading.setText("Charging");

        increaseHour = findViewById(R.id.increaseHour);
        decreaseHour = findViewById(R.id.decreaseHour);
        hour = findViewById(R.id.hour);
        navigateLocation = findViewById(R.id.navigateLocation);
        btnScheduleCharging = findViewById(R.id.btnScheduleCharging);
        calenderLayout = findViewById(R.id.calenderLayout);
        timeLayout = findViewById(R.id.timeLayout);
        btnScanCode = findViewById(R.id.btnScanCode);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        navigateLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                startActivity(intent);
            }
        });

        increaseHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedHour = hour.getText().toString().trim();
                int s = Integer.parseInt(selectedHour);
                int pluse = s + 1;
                String as = String.valueOf(pluse);
                hour.setText(as);

            }
        });

        btnScheduleCharging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (calenderLayout.getVisibility() == View.VISIBLE && timeLayout.getVisibility() == View.VISIBLE) {
                    calenderLayout.setVisibility(View.GONE);
                    timeLayout.setVisibility(View.GONE);
                } else {
                    calenderLayout.setVisibility(View.VISIBLE);
                    timeLayout.setVisibility(View.VISIBLE);
                }
            }
        });

        btnScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ChargingActivity.this,ScanCodeActivity.class);
                startActivity(intent);

            }
        });
    }

    public void bookChargingPoint(String token,
                                  String user_id,
                                  String power_station_id,
                                  String connectors_id,
                                  String booking_date,
                                  String booking_time,
                                  String payment_method) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();
        call.bookChargingPoint(token, user_id, power_station_id, connectors_id, booking_date, booking_time, payment_method).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();
                Toast.makeText(ChargingActivity.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Glob.dialog.dismiss();

            }

        });


    }


}