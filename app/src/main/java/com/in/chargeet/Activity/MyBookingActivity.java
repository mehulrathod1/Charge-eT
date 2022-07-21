package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.in.chargeet.Adapter.MyBookingAdapter;
import com.in.chargeet.Model.MyBookingModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBookingActivity extends AppCompatActivity {

    ImageView backButton;
    TextView toolbarHading;


    View thumbView;
    RecyclerView bookingRecycle;
    MyBookingAdapter myBookingAdapter;
    List<MyBookingModel.Booking> bookingModelList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_booking);
        init();
        getMyBooking(Glob.token, Glob.userId);
    }

    public void init() {

        Glob.progressDialog(this);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        toolbarHading.setText("My Booking");
        bookingRecycle = findViewById(R.id.bookingRecycle);

        thumbView = LayoutInflater.from(MyBookingActivity.this).inflate(R.layout.layout_seekbar_thumb, null, false);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);

            }
        });


    }


    public void getMyBooking(String token, String userId) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();

        call.getMyBooking(token, userId).enqueue(new Callback<MyBookingModel>() {
            @Override
            public void onResponse(Call<MyBookingModel> call, Response<MyBookingModel> response) {

                MyBookingModel myBookingModel = response.body();

                List<MyBookingModel.Booking> dataList = myBookingModel.getBookingList();

                for (int i = 0; i < dataList.size(); i++) {

                    MyBookingModel.Booking model = dataList.get(i);
                    MyBookingModel.Booking data = new MyBookingModel.Booking(
                            model.getId(),model.getPower_station_id(), model.getBooking_date(), model.getPower_station_name(),
                            model.getConnectors_id(), model.getDescription(),
                            model.getPercentage(),
                            model.getUnit(),
                            model.getTime(),
                            model.getCreated_at()
                    );

                    bookingModelList.add(data);

                }
                setBookingData();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<MyBookingModel> call, Throwable t) {

            }
        });

    }

    public void setBookingData() {

        myBookingAdapter = new MyBookingAdapter(bookingModelList, getApplicationContext(), new MyBookingAdapter.Click() {
            @Override
            public void onRebookClick(int position) {

                String connectorId = bookingModelList.get(position).getConnectors_id();
                String paymentMethod = "Wallet";
                String SelectedPercentage = bookingModelList.get(position).getPercentage();
                String SelectedUnit = bookingModelList.get(position).getUnit();
                String SelectedTime = bookingModelList.get(position).getTime();
                String powerStationId = bookingModelList.get(position).getPower_station_id();


                Intent intent = new Intent(getApplicationContext(), ChargingActivity.class);
                intent.putExtra("connectorId", connectorId);
                intent.putExtra("powerStationId", powerStationId);
                intent.putExtra("paymentMethod", paymentMethod);
                intent.putExtra("SelectedPercentage", SelectedPercentage);
                intent.putExtra("SelectedUnit", SelectedUnit);
                intent.putExtra("SelectedTime", SelectedTime);
                startActivity(intent);
            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        bookingRecycle.setLayoutManager(mLayoutManager);
        myBookingAdapter.notifyDataSetChanged();
        bookingRecycle.setAdapter(myBookingAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);

    }
}