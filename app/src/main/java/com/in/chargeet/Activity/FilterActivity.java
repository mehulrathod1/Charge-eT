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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.in.chargeet.Adapter.FilterConnectorAdapter;
import com.in.chargeet.Model.FilterConnectorModel;
import com.in.chargeet.R;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {
    BottomNavigationView bottom_navigation;
    ImageView backButton;
    TextView toolbarHading;
    Button applyFilter;

    RecyclerView filterConnectorRecycler;
    FilterConnectorAdapter filterConnectorAdapter;
    List<FilterConnectorModel> filterConnectorModelList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        init();
        setConnectorData();
    }

    public void init() {

        bottom_navigation = findViewById(R.id.bottom_navigation);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        toolbarHading.setText("Filter");
        applyFilter = findViewById(R.id.applyFilter);
        filterConnectorRecycler = findViewById(R.id.filterConnectorRecycler);
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
                Intent intent;
                intent = new Intent(getApplicationContext(), FilterResultActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
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
            }
        });

        filterConnectorRecycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        filterConnectorRecycler.setAdapter(filterConnectorAdapter);
    }


    public void changeSelected() {

        for (int i = 0; i < filterConnectorModelList.size(); i++) {
            filterConnectorModelList.get(i).setSelected(false);


        }
        filterConnectorAdapter.notifyDataSetChanged();

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