package com.in.chargeet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChargingActivity extends AppCompatActivity {

    ImageView backButton, increaseHour, decreaseHour, increaseMinute, decreaseMinute, setAm, setPm, navigateLocation;
    TextView toolbarHading, hour, minute, amPm;
    Button btnScheduleCharging, btnScanCode, payForCharging;
    String selectedHour;
    LinearLayout calenderLayout;
    LinearLayout timeLayout;
    String manageSchedule;


    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    RadioButton googlePay, amazonPay, radioWallet, creditCard;
    TextView wallet;

    String connectorId, powerStationId, paymentMethod, bookingDate, BookingTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charging);

        Intent intent = getIntent();
        connectorId = intent.getStringExtra("connectorId");
        powerStationId = intent.getStringExtra("powerStationId");
        paymentMethod = intent.getStringExtra("paymentMethod");

        init();
    }

    public void init() {

        Glob.progressDialog(this);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        payForCharging = findViewById(R.id.payForCharging);
        toolbarHading.setText("Charging");

        increaseHour = findViewById(R.id.increaseHour);
        decreaseHour = findViewById(R.id.decreaseHour);
        hour = findViewById(R.id.hour);
        navigateLocation = findViewById(R.id.navigateLocation);
        btnScheduleCharging = findViewById(R.id.btnScheduleCharging);
        calenderLayout = findViewById(R.id.calenderLayout);
        timeLayout = findViewById(R.id.timeLayout);
        btnScanCode = findViewById(R.id.btnScanCode);
        wallet = findViewById(R.id.wallet);
        minute = findViewById(R.id.minute);
        increaseMinute = findViewById(R.id.increaseMinute);
        decreaseMinute = findViewById(R.id.decreaseMinute);
        amPm = findViewById(R.id.amPm);
        setAm = findViewById(R.id.setAm);
        setPm = findViewById(R.id.setPm);

        alertDialog = new AlertDialog.Builder(ChargingActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.payment_option_layout, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();

        googlePay = dialogLayout.findViewById(R.id.googlePay);
        amazonPay = dialogLayout.findViewById(R.id.amazonPay);
        radioWallet = dialogLayout.findViewById(R.id.wallet);
        creditCard = dialogLayout.findViewById(R.id.creditCard);


        CalendarView myCalender = (CalendarView) findViewById(R.id.myCalender); // get the reference of CalendarView
        myCalender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

                int a =i1+1;
                bookingDate = i + "-" + a + "-" + i2;
                Log.e("onSelectedDayChange", "onSelectedDayChange: " + bookingDate);

            }
        });


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
                if (!selectedHour.equals("12")) {
                    int s = Integer.parseInt(selectedHour);
                    int pluse = s + 1;
                    String as = String.valueOf(pluse);
                    hour.setText(as);
                }

            }
        });

        decreaseHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedHour = hour.getText().toString().trim();
                if (!selectedHour.equals("0")) {
                    int s = Integer.parseInt(selectedHour);
                    int pluse = s - 1;
                    String as = String.valueOf(pluse);
                    hour.setText(as);
                }
            }
        });

        increaseMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedHour = minute.getText().toString().trim();
                if (!selectedHour.equals("60")) {
                    int s = Integer.parseInt(selectedHour);
                    int pluse = s + 5;
                    String as = String.valueOf(pluse);
                    minute.setText(as);
                }
            }
        });
        decreaseMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectedHour = minute.getText().toString().trim();
                if (!selectedHour.equals("0")) {
                    int s = Integer.parseInt(selectedHour);
                    int pluse = s - 5;
                    String as = String.valueOf(pluse);
                    minute.setText(as);
                }
            }
        });

        setAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amPm.setText("AM");
            }
        });


        setPm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                amPm.setText("PM");
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

                Intent intent = new Intent(ChargingActivity.this, ScanCodeActivity.class);
                startActivity(intent);

            }
        });

        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();


            }
        });


        googlePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.dismiss();
                wallet.setText(googlePay.getText().toString().trim());
            }
        });

        amazonPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alert.dismiss();
                wallet.setText(amazonPay.getText().toString().trim());
            }
        });
        radioWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.dismiss();
                wallet.setText(radioWallet.getText().toString().trim());
                paymentMethod = "wallet";
            }
        });

        creditCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alert.dismiss();
                wallet.setText(creditCard.getText().toString().trim());
                paymentMethod = "card";

            }
        });

        payForCharging.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.e("init", "init: " + Glob.token + "||||" + "||||" + Glob.userId + "||||" + powerStationId + "||||" + connectorId + "||||" + bookingDate + "||||" + BookingTime + "||||" + paymentMethod);
                BookingTime = hour.getText().toString() + ":" + minute.getText().toString() + " " + amPm.getText().toString();

                if (bookingDate == null) {
                    Toast.makeText(ChargingActivity.this, "Please select bookingDate", Toast.LENGTH_SHORT).show();
                } else if (BookingTime == null) {
                    Toast.makeText(ChargingActivity.this, "Please select bookingTime", Toast.LENGTH_SHORT).show();
                } else {
                    bookChargingPoint(Glob.token,
                            Glob.userId,
                            powerStationId,
                            connectorId,
                            bookingDate,
                            BookingTime,
                            paymentMethod);
                }
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