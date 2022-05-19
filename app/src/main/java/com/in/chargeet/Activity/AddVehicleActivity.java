package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.in.chargeet.Adapter.CountryAdapter;
import com.in.chargeet.Adapter.VehicleAdapter;
import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.Model.CountryModel;
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

public class AddVehicleActivity extends AppCompatActivity {

    ImageView backButton;
    TextView toolbarHading, model, manufacturer;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;


    List<VehicleModel.Vehicle> vehicleList = new ArrayList<>();
    RecyclerView vehicleRecycler;
    VehicleAdapter vehicleAdapter;

    List<CountryModel.Country> manufacturerList = new ArrayList<>();
    List<CountryModel.Country> modelList = new ArrayList<>();
    RecyclerView countryRecycler;
    CountryAdapter countryAdapter;

    String manufacturerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle);
        init();
        getVehicle(Glob.token, "", "");

    }

    public void init() {

        Glob.progressDialog(this);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        model = findViewById(R.id.model);
        vehicleRecycler = findViewById(R.id.vehicleRecycler);
        manufacturer = findViewById(R.id.manufacturer);
        toolbarHading.setText("My vehicle");


        alertDialog = new AlertDialog.Builder(AddVehicleActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.country_popup, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();
        countryRecycler = dialogLayout.findViewById(R.id.countryRecycler);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        model.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getModelData(Glob.token, "1");


            }
        });
        manufacturer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getManufacturerData(Glob.token);

            }
        });
    }

    public void getVehicle(String token, String manufacturerId, String model) {


        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
//        Glob.dialog.show();


        call.getVehicle(token, manufacturerId, model).enqueue(new Callback<VehicleModel>() {
            @Override
            public void onResponse(Call<VehicleModel> call, Response<VehicleModel> response) {

                VehicleModel vehicleModel = response.body();

                List<VehicleModel.Vehicle> dataList = vehicleModel.getData();

                for (int i = 0; i < dataList.size(); i++) {

                    VehicleModel.Vehicle model = dataList.get(i);
                    VehicleModel.Vehicle data = new VehicleModel.Vehicle(
                            model.getId(), model.getName(), model.getManufacturer(),
                            model.getDescription(), model.getRate(), model.getImage()

                    );

                    vehicleList.add(data);
                }
                setVehicleData();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<VehicleModel> call, Throwable t) {
                Glob.dialog.dismiss();
                Log.e("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }


    public void setVehicleData() {

        vehicleAdapter = new VehicleAdapter(vehicleList, getApplicationContext(), new VehicleAdapter.Click() {
            @Override
            public void onSelectClick(int position) {

                String vehicleId = vehicleList.get(position).getId();

                Log.e("vehicleId", "onSelectClick: "+vehicleId );

                selectVehicle(Glob.token,"4",vehicleId);
            }
        });

        vehicleRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        vehicleAdapter.notifyDataSetChanged();
        vehicleRecycler.setAdapter(vehicleAdapter);

    }

    public void getManufacturerData(String token) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);

        call.getManufacturer(token).enqueue(new Callback<CountryModel>() {
            @Override
            public void onResponse(Call<CountryModel> call, Response<CountryModel> response) {

                manufacturerList.clear();
                CountryModel countryModel = response.body();
                List<CountryModel.Country> dataList = countryModel.getDataList();
                for (int i = 0; i < dataList.size(); i++) {

                    CountryModel.Country model = dataList.get(i);
                    CountryModel.Country data = new CountryModel.Country(model.getId(), model.getName());

                    Log.e("onResponse", "onResponse: " + data.getName());
                    manufacturerList.add(data);
                }
                setCountryData();
                alert.show();

            }

            @Override
            public void onFailure(Call<CountryModel> call, Throwable t) {

            }
        });

    }

    public void getModelData(String token, String manufacturerId) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);

        call.getModel(token, manufacturerId).enqueue(new Callback<CountryModel>() {
            @Override
            public void onResponse(Call<CountryModel> call, Response<CountryModel> response) {

                manufacturerList.clear();
                CountryModel countryModel = response.body();

                List<CountryModel.Country> dataList = countryModel.getDataList();
                for (int i = 0; i < dataList.size(); i++) {

                    CountryModel.Country model = dataList.get(i);
                    CountryModel.Country data = new CountryModel.Country(model.getId(), model.getName());

                    Log.e("onResponse", "onResponse: " + data.getName());
                    manufacturerList.add(data);
                }
                setModelData();
                alert.show();

            }

            @Override
            public void onFailure(Call<CountryModel> call, Throwable t) {

                Log.e("onResponse", "onResponse: " + t.getMessage());

            }
        });

    }

    public void setCountryData() {

        countryAdapter = new CountryAdapter(manufacturerList, getApplicationContext(), new CountryAdapter.Click() {
            @Override
            public void itemClick(int position) {

                manufacturerId = manufacturerList.get(position).getId();
                String manufacturerName = manufacturerList.get(position).getName();
                alert.dismiss();
                manufacturer.setText(manufacturerName);

            }
        });

        countryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        countryAdapter.notifyDataSetChanged();
        countryRecycler.setAdapter(countryAdapter);

    }

    public void setModelData() {

        countryAdapter = new CountryAdapter(manufacturerList, getApplicationContext(), new CountryAdapter.Click() {
            @Override
            public void itemClick(int position) {

                manufacturerId = manufacturerList.get(position).getId();
                String manufacturerName = manufacturerList.get(position).getName();
                alert.dismiss();
                model.setText(manufacturerName);

            }
        });

        countryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        countryAdapter.notifyDataSetChanged();
        countryRecycler.setAdapter(countryAdapter);

    }

    public void selectVehicle(String token, String userId, String vehicleId) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();

        call.selectVehicle(token, userId, vehicleId).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();
                Toast.makeText(AddVehicleActivity.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Glob.dialog.dismiss();

            }
        });

    }

}