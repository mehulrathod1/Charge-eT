package com.in.chargeet.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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
import com.in.chargeet.Model.NotAvailableFilterModel;
import com.in.chargeet.R;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);
        init();
        availableData();
    }

    public void init() {

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

                Intent intent;
                intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });

        layoutAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_bg));
                layoutNotAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_white_bg));
                notAvailableText.setTextColor(Color.parseColor("#1C1C1C"));
                availableText.setTextColor(Color.parseColor("#ffffff"));
                availableData();
            }
        });

        layoutNotAvailable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_white_bg));
                layoutNotAvailable.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.filter_heading_bg));
                availableText.setTextColor(Color.parseColor("#1C1C1C"));
                notAvailableText.setTextColor(Color.parseColor("#ffffff"));
                notAvailable();
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

    public void availableData() {

        availableFilterList.clear();
        AvailableFilterModel model = new AvailableFilterModel("percentage", "units", "times");
        availableFilterList.add(model);
        availableFilterList.add(model);
        availableFilterList.add(model);
        availableFilterList.add(model);
        availableFilterList.add(model);

        availableFilterAdapter = new AvailableFilterAdapter(availableFilterList, getApplicationContext(), new AvailableFilterAdapter.Click() {
            @Override
            public void onBookClick(int position) {

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
        });


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        filterRecycler.setLayoutManager(mLayoutManager);
        availableFilterAdapter.notifyDataSetChanged();
        filterRecycler.setAdapter(availableFilterAdapter);


    }

    public void notAvailable(){

        NotAvailableFilterModel model = new NotAvailableFilterModel("schedule");

        notAvailableFilterList.add(model);
        notAvailableFilterList.add(model);
        notAvailableFilterList.add(model);
        notAvailableFilterList.add(model);
        notAvailableFilterList.add(model);
        notAvailableFilterList.add(model);

        notAvailableFilterAdapter  = new NotAvailableFilterAdapter(notAvailableFilterList, getApplicationContext(), new NotAvailableFilterAdapter.Click() {
            @Override
            public void onScheduleClick(int position) {

            }
        });

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        filterRecycler.setLayoutManager(mLayoutManager);
        notAvailableFilterAdapter.notifyDataSetChanged();
        filterRecycler.setAdapter(notAvailableFilterAdapter);
    }
}