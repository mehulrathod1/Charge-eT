package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.in.chargeet.Adapter.CountryAdapter;
import com.in.chargeet.Model.CountryModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText edtEmail, edtPhoneNo, edtUserName, edtPassword, edtCountry;
    Button btnSignUp;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    RecyclerView countryRecycler;
    CountryAdapter countryAdapter;
    List<CountryModel.Country> countryList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        getCountryData(Glob.token);
    }

    public void init() {

        btnSignUp = findViewById(R.id.btnSignUp);

        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNo = findViewById(R.id.edtPhoneNo);
        edtUserName = findViewById(R.id.edtUserName);
        edtPassword = findViewById(R.id.edtPassword);
        edtCountry = findViewById(R.id.edtCountry);


        alertDialog = new AlertDialog.Builder(SignUpActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.country_popup, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();
        countryRecycler = dialogLayout.findViewById(R.id.countryRecycler);


        edtCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                if (edtEmail.getText().toString().trim().equals("")) {

                    edtEmail.setError("Please  Email");

                } else if (edtPhoneNo.getText().toString().trim().equals("")) {
                    edtPhoneNo.setError("Please Enter edtPhoneNo");

                } else if (edtUserName.getText().toString().trim().equals("")) {
                    edtUserName.setError("Please Enter edtUserName");

                } else if (edtPassword.getText().toString().trim().equals("")) {
                    edtPassword.setError("Please Enter Password");

                } else if (edtCountry.getText().toString().trim().equals("")) {
                    edtCountry.setError("Please Enter edtCountry");

                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void getCountryData(String token) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);

        call.getCountry(token).enqueue(new Callback<CountryModel>() {
            @Override
            public void onResponse(Call<CountryModel> call, Response<CountryModel> response) {

                CountryModel countryModel = response.body();

                List<CountryModel.Country> dataList = countryModel.getDataList();

                for (int i = 0; i < dataList.size(); i++) {

                    CountryModel.Country model = dataList.get(i);
                    CountryModel.Country data = new CountryModel.Country(model.getId(), model.getName());

                    Log.e("onResponse", "onResponse: " + data.getName());
                    countryList.add(data);
                }
                setCountryData();
            }

            @Override
            public void onFailure(Call<CountryModel> call, Throwable t) {

            }
        });

    }

    public void setCountryData() {

        countryAdapter = new CountryAdapter(countryList, getApplicationContext(), new CountryAdapter.Click() {
            @Override
            public void itemClick(int position) {

            }
        });

        countryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        countryAdapter.notifyDataSetChanged();
        countryRecycler.setAdapter(countryAdapter);

    }
}