package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.in.chargeet.Adapter.OtherVehicleAdapter;
import com.in.chargeet.Adapter.VehicleAdapter;
import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.Model.OtherVehicleModel;
import com.in.chargeet.Model.PrimaryVehicleModel;
import com.in.chargeet.Model.VehicleModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyVehicleActivity extends AppCompatActivity {

    ImageView backButton, vehicleImage, delete;
    TextView toolbarHading, vehicleName, vehicleDescription, vehicleSpeed;
    Button addVehicle;
    LinearLayout primaryLayout;
    RecyclerView otherVehicleRecycler;
    List<OtherVehicleModel.OtherVehicle> otherVehicleList = new ArrayList<>();
    OtherVehicleAdapter otherVehicleAdapter;
    String vehicleId;

    String TAG = "MyVehicleActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_vehiecle);
        init();
        getPrimaryVehicle(Glob.token, Glob.userId);
        getOtherVehicle(Glob.token, Glob.userId);

        Log.e(TAG, "onCreate: " + Glob.userId);
    }

    public void init() {

        Glob.progressDialog(this);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        addVehicle = findViewById(R.id.addVehicle);
        toolbarHading.setText("My vehicle");
        vehicleImage = findViewById(R.id.vehicleImage);
        vehicleName = findViewById(R.id.vehicleName);
        vehicleDescription = findViewById(R.id.vehicleDescription);
        vehicleSpeed = findViewById(R.id.vehicleSpeed);
        otherVehicleRecycler = findViewById(R.id.otherVehicleRecycler);
        delete = findViewById(R.id.delete);
        primaryLayout = findViewById(R.id.primaryLayout);


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                removeVehicle(Glob.token, Glob.userId, vehicleId);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
        addVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), AddVehicleActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getPrimaryVehicle(String token, String userId) {


        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();

        call.getPrimaryVehicle(token, userId).enqueue(new Callback<PrimaryVehicleModel>() {
            @Override
            public void onResponse(Call<PrimaryVehicleModel> call, Response<PrimaryVehicleModel> response) {

                PrimaryVehicleModel primaryVehicleModel = response.body();

                PrimaryVehicleModel.Primary model = primaryVehicleModel.getData();
                Glide.with(getApplicationContext()).load(model.getImage()).into(vehicleImage);
                vehicleName.setText(model.getName());
                vehicleDescription.setText(model.getDescription());
                vehicleSpeed.setText(model.getRate() + " / ");
                vehicleId = model.getUser_vehicle_id();
                Glob.dialog.dismiss();

                Toast.makeText(MyVehicleActivity.this, "" + primaryVehicleModel.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, "onResponse: " + model.getName());

                if (vehicleName.getText().toString().equals("")) {
                    primaryLayout.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<PrimaryVehicleModel> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());

                Glob.dialog.dismiss();
                if (vehicleName.getText().toString().equals("")) {
                    primaryLayout.setVisibility(View.GONE);
                }
            }
        });
    }

    public void getOtherVehicle(String token, String userId) {


        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.getOtherVehicle(token, userId).enqueue(new Callback<OtherVehicleModel>() {
            @Override
            public void onResponse(Call<OtherVehicleModel> call, Response<OtherVehicleModel> response) {

                otherVehicleList.clear();
                OtherVehicleModel otherVehicleModel = response.body();

                List<OtherVehicleModel.OtherVehicle> dataList = otherVehicleModel.getData();

                for (int i = 0; i < dataList.size(); i++) {

                    OtherVehicleModel.OtherVehicle model = dataList.get(i);

                    OtherVehicleModel.OtherVehicle data = new OtherVehicleModel.OtherVehicle(
                            model.getId(), model.getUser_vehicle_id(), model.getName(),
                            model.getManufacturer()
                            , model.getDescription(), model.getRate(), model.getImage()
                    );

                    otherVehicleList.add(data);
                }
                otherVehicleData();
                Glob.dialog.dismiss();

            }

            @Override
            public void onFailure(Call<OtherVehicleModel> call, Throwable t) {

                Log.e("TAG", "onFailure: " + t.getMessage());
                Glob.dialog.dismiss();
            }
        });

    }

    public void otherVehicleData() {

        otherVehicleAdapter = new OtherVehicleAdapter(otherVehicleList, getApplicationContext(), new OtherVehicleAdapter.Click() {
            @Override
            public void onSelectClick(int position) {


                String vehicleId = otherVehicleList.get(position).getUser_vehicle_id();

                makePrimary(Glob.token, Glob.userId, vehicleId);


            }
        });


        otherVehicleRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        otherVehicleAdapter.notifyDataSetChanged();
        otherVehicleRecycler.setAdapter(otherVehicleAdapter);



    }

    public void makePrimary(String token, String userId, String userVehicleId) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();

        call.makePrimary(token, userId, userVehicleId).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();
                Toast.makeText(MyVehicleActivity.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
                getOtherVehicle(Glob.token, Glob.userId);
                getPrimaryVehicle(Glob.token, Glob.userId);

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Glob.dialog.dismiss();
            }
        });

    }

    public void removeVehicle(String token, String userId, String userVehicleId) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();

        call.removeVehicle(token, userId, userVehicleId).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();

                Toast.makeText(MyVehicleActivity.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();


                Log.e(TAG, "onResponse: " + commonModel.getMessage() + Glob.userId);
                Glob.dialog.dismiss();
                getPrimaryVehicle(Glob.token, Glob.userId);

            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Glob.dialog.dismiss();
            }
        });

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