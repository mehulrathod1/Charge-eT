package com.in.chargeet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.chargeet.Adapter.FilterConnectorAdapter;
import com.in.chargeet.Adapter.WattAdapter;
import com.in.chargeet.Model.FilterConnectorModel;
import com.in.chargeet.Model.WattModel;
import com.in.chargeet.R;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    ImageView backButton;
    TextView toolbarHading;
    Button applyFilter;
    CheckBox freeStationCheck, workingStationCheck;

    String freeStation, workingStation, powerLevels, connectorId;

    RecyclerView filterConnectorRecycler, wattRecycler;
    FilterConnectorAdapter filterConnectorAdapter;
    List<FilterConnectorModel> filterConnectorModelList = new ArrayList<>();

    WattAdapter wattAdapter;
    List<WattModel> wattList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        init();
        setConnectorData();
        setWattData();
    }

    public void init() {

        bottom_navigation = findViewById(R.id.bottom_navigation);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        toolbarHading.setText("Filter");
        applyFilter = findViewById(R.id.applyFilter);
        filterConnectorRecycler = findViewById(R.id.filterConnectorRecycler);
        wattRecycler = findViewById(R.id.wattRecycler);
        freeStationCheck = findViewById(R.id.freeStationCheck);
        workingStationCheck = findViewById(R.id.workingStationCheck);
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
                        intent = new Intent(getApplicationContext(), DirectionActivity.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0, 0);
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

                Intent intent;
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });


        applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (freeStationCheck.isChecked()) {
                    freeStation = "1";
                } else {
                    freeStation = "0";
                }
                if (workingStationCheck.isChecked()) {
                    workingStation = "1";
                } else {
                    workingStation = "0";
                }
                Log.e("freeStation", "onClick: " + freeStation + workingStation + powerLevels + connectorId);

                if (powerLevels == null) {
                    Toast.makeText(FilterActivity.this, "Please Select Power Level", Toast.LENGTH_SHORT).show();
                } else if (connectorId == null) {
                    Toast.makeText(FilterActivity.this, "Please Select Connector", Toast.LENGTH_SHORT).show();
                } else {

                    Intent intent;
                    intent = new Intent(getApplicationContext(), FilterResultActivity.class);
                    intent.putExtra("powerLevels", powerLevels);
                    intent.putExtra("connectorId", connectorId);
                    intent.putExtra("freeStation", freeStation);
                    intent.putExtra("workingStation", workingStation);
                    startActivity(intent);
//                finish();
                    overridePendingTransition(0, 0);
                }
            }
        });

        workingStationCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {

                    workingStation = "1";
                } else {

                    workingStation = "0";
                }
            }
        });

        freeStationCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {

                    freeStation = "1";
                } else {

                    freeStation = "0";
                }

            }
        });

    }

    public void setConnectorData() {

        FilterConnectorModel model = new FilterConnectorModel("3-pin", R.drawable.three_pin);
        FilterConnectorModel model1 = new FilterConnectorModel("3-pin", R.drawable.three_pinn);
        FilterConnectorModel model2 = new FilterConnectorModel("Type 2", R.drawable.thye_two);
        FilterConnectorModel model3 = new FilterConnectorModel("Type 2 ( cable attached )", R.drawable.type_twoo);
        FilterConnectorModel model4 = new FilterConnectorModel("combo ccs EU", R.drawable.combo_ccs);
        FilterConnectorModel model5 = new FilterConnectorModel("CHAdeMO", R.drawable.chademo);
        FilterConnectorModel model6 = new FilterConnectorModel("GB/T", R.drawable.gbt);
        FilterConnectorModel model7 = new FilterConnectorModel("Tesla", R.drawable.tesla);
        FilterConnectorModel model8 = new FilterConnectorModel("Tesla Supercharger", R.drawable.tesla_super);

        filterConnectorModelList.add(model);
        filterConnectorModelList.add(model1);
        filterConnectorModelList.add(model2);
        filterConnectorModelList.add(model3);
        filterConnectorModelList.add(model4);
        filterConnectorModelList.add(model5);
        filterConnectorModelList.add(model6);
        filterConnectorModelList.add(model7);
        filterConnectorModelList.add(model8);


        filterConnectorAdapter = new FilterConnectorAdapter(filterConnectorModelList, getApplicationContext(), new FilterConnectorAdapter.Click() {
            @Override
            public void onItemCLick(int position) {

                changeSelected();
                filterConnectorModelList.get(position).setSelected(true);

                connectorId = "1";

            }
        });

        filterConnectorRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        filterConnectorRecycler.setAdapter(filterConnectorAdapter);
    }

    public void setWattData() {

        WattModel model = new WattModel("5kw");
        WattModel model1 = new WattModel("10kw");
        WattModel model2 = new WattModel("20kw");
        WattModel model3 = new WattModel("50kw");
        WattModel model4 = new WattModel("60kw");
        WattModel model5 = new WattModel("80kw");
        WattModel model6 = new WattModel("100kw");
        WattModel model7 = new WattModel("120kw");
        WattModel model8 = new WattModel("140kw");

        wattList.add(model);
        wattList.add(model1);
        wattList.add(model2);
        wattList.add(model3);
        wattList.add(model4);
        wattList.add(model5);
        wattList.add(model6);
        wattList.add(model7);
        wattList.add(model8);


        wattAdapter = new WattAdapter(wattList, getApplicationContext(), new WattAdapter.Click() {
            @Override
            public void OnItemClick(int position) {

                changeWattSelected();
                wattList.get(position).setSelected(true);
                wattAdapter.notifyDataSetChanged();
                powerLevels = wattList.get(position).getKiloWatt();
                Log.e("FilterActivity", "OnItemClick: " + powerLevels);

            }

        });
        wattRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        wattRecycler.setAdapter(wattAdapter);
    }

    public void changeSelected() {

        for (int i = 0; i < filterConnectorModelList.size(); i++) {
            filterConnectorModelList.get(i).setSelected(false);


        }
        filterConnectorAdapter.notifyDataSetChanged();

    }

    public void changeWattSelected() {


        for (int i = 0; i < wattList.size(); i++) {
            wattList.get(i).setSelected(false);
        }
        wattAdapter.notifyDataSetChanged();

    }


    @Override
    public void onBackPressed() {

        Intent intent;
        intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
        super.onBackPressed();

    }

}