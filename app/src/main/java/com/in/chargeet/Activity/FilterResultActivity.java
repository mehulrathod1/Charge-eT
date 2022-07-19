package com.in.chargeet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.chargeet.Adapter.AvailableFilterAdapter;
import com.in.chargeet.Adapter.NotAvailableFilterAdapter;
import com.in.chargeet.Model.AvailableFilterModel;
import com.in.chargeet.Model.FilterModel;
import com.in.chargeet.Model.NotAvailableFilterModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FilterResultActivity extends AppCompatActivity {

    BottomNavigationView bottom_navigation;
    ImageView backButton;
    TextView toolbarHading, notAvailableText, availableText;
    Button applyFilter;
    LinearLayout layoutAvailable, layoutNotAvailable;

    RecyclerView filterRecycler;
    AvailableFilterAdapter availableFilterAdapter;
    List<AvailableFilterModel> availableFilterList = new ArrayList<>();

    NotAvailableFilterAdapter notAvailableFilterAdapter;
    List<NotAvailableFilterModel> notAvailableFilterList = new ArrayList<>();

    String TAG = "FilterResultActivity";

    String freeStation, workingStation, powerLevels, connectorId, powerStationId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);

        Intent intent = getIntent();
        freeStation = intent.getStringExtra("freeStation");
        workingStation = intent.getStringExtra("workingStation");
        powerLevels = intent.getStringExtra("powerLevels");
        connectorId = intent.getStringExtra("connectorId");
        init();
        getFillerResult(Glob.token, powerLevels, connectorId, freeStation, workingStation);
        Log.e("freeStation", "onClick: " + freeStation + workingStation + powerLevels + connectorId);

    }

    public void init() {

        Glob.progressDialog(this);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        toolbarHading.setText("Filter Result");
        applyFilter = findViewById(R.id.applyFilter);
        filterRecycler = findViewById(R.id.filterRecycler);
        layoutNotAvailable = findViewById(R.id.layoutNotAvailable);
        layoutAvailable = findViewById(R.id.layoutAvailable);
        availableText = findViewById(R.id.availableText);
        notAvailableText = findViewById(R.id.notAvailableText);
        layoutAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_bg));

        bottom_navigation.getMenu().findItem(R.id.filter).setChecked(true);
        bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;

                switch (item.getItemId()) {

                    case R.id.location:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;


                    case R.id.order:
//                        intent = new Intent(getApplicationContext(), MyAccountActivity.class);
//                        startActivity(intent);
//                        finish();
//                        overridePendingTransition(0, 0);
                        break;

                    case R.id.filter:
                        intent = new Intent(getApplicationContext(), FilterActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;

                    case R.id.account:
                        intent = new Intent(getApplicationContext(), MyAccountActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
                        break;
                }

                return false;
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Intent intent;
//                intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);
                finish();
//                overridePendingTransition(0, 0);
            }
        });

        layoutAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_bg));
                layoutNotAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_white_bg));
                notAvailableText.setTextColor(Color.parseColor("#1C1C1C"));
                availableText.setTextColor(Color.parseColor("#ffffff"));
                getFillerResult(Glob.token, "4", "1", "0", "0");


            }
        });

        layoutNotAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_white_bg));
                layoutNotAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_bg));
                availableText.setTextColor(Color.parseColor("#1C1C1C"));
                notAvailableText.setTextColor(Color.parseColor("#ffffff"));
                getNotAvailableFillerResult(Glob.token, "4", "1", "0", "0");

            }
        });
    }


    @Override
    public void onBackPressed() {

        Intent intent;
        intent = new Intent(getApplicationContext(), FilterActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();

    }


    public void getFillerResult(String token, String power_levels, String connectors_id, String free_station,
                                String working_station) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.getFillerResult(token, power_levels, connectors_id, free_station, working_station).enqueue(new Callback<FilterModel>() {
            @Override
            public void onResponse(Call<FilterModel> call, Response<FilterModel> response) {

                availableFilterList.clear();
                notAvailableFilterList.clear();

                FilterModel filterModel = response.body();
                FilterModel.FilterData filterData = filterModel.getFilterData();

                List<AvailableFilterModel> availableFilterModelList = filterData.getAvailableFilterModelList();

                for (int i = 0; i < availableFilterModelList.size(); i++) {

                    AvailableFilterModel availableFilterModel = availableFilterModelList.get(i);
                    AvailableFilterModel availableData = new AvailableFilterModel(

                            availableFilterModel.getId(),
                            availableFilterModel.getName(),
                            availableFilterModel.getDescription(),
                            availableFilterModel.getPower(),
                            availableFilterModel.getRate(),
                            availableFilterModel.getConnectors(),
                            availableFilterModel.getLatitude(),
                            availableFilterModel.getLongitude(),
                            availableFilterModel.getIcon()
                    );

                    Log.e(TAG, "onResponse: " + availableFilterModel.getConnectors());

                    availableFilterList.add(availableData);
                }

                availableData();
                Glob.dialog.dismiss();


            }

            @Override
            public void onFailure(Call<FilterModel> call, Throwable t) {

            }
        });

    }


    public void getNotAvailableFillerResult(String token, String power_levels, String connectors_id, String free_station,
                                            String working_station) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.getFillerResult(token, power_levels, connectors_id, free_station, working_station).enqueue(new Callback<FilterModel>() {
            @Override
            public void onResponse(Call<FilterModel> call, Response<FilterModel> response) {

                notAvailableFilterList.clear();
                availableFilterList.clear();

                FilterModel filterModel = response.body();
                FilterModel.FilterData filterData = filterModel.getFilterData();

                List<NotAvailableFilterModel> notAvailableFilterModelList = filterData.getNotAvailableFilterModelList();


                for (int j = 0; j < notAvailableFilterModelList.size(); j++) {


                    NotAvailableFilterModel notAvailableFilterModel = notAvailableFilterModelList.get(j);
                    NotAvailableFilterModel notAvailableData = new NotAvailableFilterModel(

                            notAvailableFilterModel.getId(),
                            notAvailableFilterModel.getName(),
                            notAvailableFilterModel.getDescription(),
                            notAvailableFilterModel.getPower(),
                            notAvailableFilterModel.getRate(),
                            notAvailableFilterModel.getConnectors(),
                            notAvailableFilterModel.getLatitude(),
                            notAvailableFilterModel.getLongitude(),
                            notAvailableFilterModel.getIcon()
                    );

                    Log.e(TAG, "onResponse: " + notAvailableFilterModel.getConnectors());

                    notAvailableFilterList.add(notAvailableData);
                }
                notAvailable();
                Glob.dialog.dismiss();


            }

            @Override
            public void onFailure(Call<FilterModel> call, Throwable t) {

                Log.e(TAG, "onResponse: " + t.getMessage());

            }
        });

    }

    public void availableData() {


        availableFilterAdapter = new AvailableFilterAdapter(availableFilterList, getApplicationContext(), new AvailableFilterAdapter.Click() {
            @Override
            public void onBookClick(int position) {


                powerStationId = availableFilterList.get(position).getId();
                Intent intent = new Intent(getApplicationContext(), ChargingActivity.class);
                intent.putExtra("connectorId", connectorId);
                intent.putExtra("powerStationId", powerStationId);
                intent.putExtra("paymentMethod", "wallet");
                startActivity(intent);

            }

            @Override
            public void onPercentageClick(int position) {

            }

            @Override
            public void onUnitClick(int position) {

            }

            @Override
            public void onTimeClick(int position) {

            }

            @Override
            public void onDirectionClick(int position) {

                String Latitude = availableFilterList.get(position).getLatitude();
                String Longitude = availableFilterList.get(position).getLongitude();
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                intent.putExtra("Latitude", Latitude);
                intent.putExtra("Longitude", Longitude);
                startActivity(intent);
            }
        });


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        filterRecycler.setLayoutManager(mLayoutManager);
        availableFilterAdapter.notifyDataSetChanged();
        filterRecycler.setAdapter(availableFilterAdapter);


    }

    public void notAvailable() {

        notAvailableFilterAdapter = new NotAvailableFilterAdapter(notAvailableFilterList, getApplicationContext(), new NotAvailableFilterAdapter.Click() {
            @Override
            public void onScheduleClick(int position) {

                powerStationId = notAvailableFilterList.get(position).getId();
                Intent intent = new Intent(getApplicationContext(), ChargingActivity.class);
                intent.putExtra("connectorId", connectorId);
                intent.putExtra("powerStationId", powerStationId);
                intent.putExtra("paymentMethod", "wallet");
                startActivity(intent);
            }

            @Override
            public void onDirectionClick(int position) {


//                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
//                startActivity(intent);


                String Latitude = notAvailableFilterList.get(position).getLatitude();
                String Longitude = notAvailableFilterList.get(position).getLongitude();
                Intent intent = new Intent(getApplicationContext(), DirectionActivity.class);
                intent.putExtra("Latitude", Latitude);
                intent.putExtra("Longitude", Longitude);
                startActivity(intent);

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        filterRecycler.setLayoutManager(mLayoutManager);
        notAvailableFilterAdapter.notifyDataSetChanged();
        filterRecycler.setAdapter(notAvailableFilterAdapter);
    }
}