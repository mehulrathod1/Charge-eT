package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.in.chargeet.Adapter.CountryAdapter;
import com.in.chargeet.Model.CountryModel;
import com.in.chargeet.Model.LoginModel;
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

    EditText edtEmail, edtPhoneNo, edtUserName, edtPassword, edtCountry, searchCountry;
    Button btnSignUp;
    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    RecyclerView countryRecycler;
    CountryAdapter countryAdapter;
    List<CountryModel.Country> countryList = new ArrayList<>();
    String countryId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        getCountryData(Glob.token, "");
    }

    public void init() {

        Glob.progressDialog(this);
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
        searchCountry = dialogLayout.findViewById(R.id.searchCountry);


        edtCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.show();
//                getCountryData(Glob.token);

            }
        });


        searchCountry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length() != 0) {

                    Log.e("onTextChanged", "onTextChanged: " + searchCountry.getText().toString());
                    getCountryData(Glob.token, searchCountry.getText().toString());

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailToText = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();


                if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {


                    if (edtPhoneNo.getText().toString().trim().equals("")) {
                        edtPhoneNo.setError("Please Enter PhoneNo");

                    } else if (edtUserName.getText().toString().trim().equals("")) {
                        edtUserName.setError("Please Enter UserName");

                    } else if (password.equals("") || (password.length() < 8)) {
                        edtPassword.setError("Please Enter valid Password");

                    } else if (edtCountry.getText().toString().trim().equals("")) {
                        edtCountry.setError("Please Select Country");

                    } else {

                        signUp(Glob.token, emailToText, edtPhoneNo.getText().toString().trim(),
                                edtUserName.getText().toString().trim(),
                                password, countryId);

                    }
                } else {
                    edtEmail.setError("Please Enter valid Email address");

                }


            }
        });
    }

    public void getCountryData(String token, String search) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);

        call.getCountry(token, search).enqueue(new Callback<CountryModel>() {
            @Override
            public void onResponse(Call<CountryModel> call, Response<CountryModel> response) {

                CountryModel countryModel = response.body();

                countryList.clear();
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

                countryId = countryList.get(position).getId();
                String countryName = countryList.get(position).getName();
                alert.dismiss();
                edtCountry.setText(countryName);

                Log.e("TAGasff", "itemClick: " + countryId);

            }
        });

        countryRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        countryAdapter.notifyDataSetChanged();
        countryRecycler.setAdapter(countryAdapter);

    }

    public void signUp(String token, String email, String phoneNumber, String username, String password, String country_id) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();

        call.signUp(token, email, phoneNumber, username, password, country_id).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                LoginModel loginModel = response.body();
                if (loginModel.getStatus().equals("true")) {
                    LoginModel.Login model = loginModel.getData();

                    Toast.makeText(SignUpActivity.this, "" + loginModel.getMessage(), Toast.LENGTH_SHORT).show();

                    Glob.userId = model.getUser_id();
                    Glob.dialog.dismiss();


                    SharedPreferences sharedPref = getSharedPreferences("UserId", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("UserId", Glob.userId);
                    editor.apply();


                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                } else {
                    Glob.dialog.dismiss();

                    Toast.makeText(SignUpActivity.this, "" + loginModel.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {

            }
        });

    }

    public void emailValidator(EditText etMail) {

        String emailToText = etMail.getText().toString();
        String password = edtPassword.getText().toString().trim();
        String phoneNumber = edtPhoneNo.getText().toString().trim();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {


            if (phoneNumber.equals("") || password.length() < 10) {
                edtPassword.setError("Please Enter Valid Mobile Number");

            } else if (password.equals("") || (password.length() < 8)) {

                edtPassword.setError("Please Enter valid password");

            } else if (edtUserName.getText().toString().trim().equals("")) {

                edtPassword.setError("Please Enter Username");

            } else if (password.equals("") || (password.length() < 8)) {

                edtPassword.setError("Please Enter valid password");


            } else if (edtCountry.getText().toString().trim().equals("")) {

                edtCountry.setError("Please Select Country");

            } else {

            }
        } else {
            edtEmail.setError("Please Enter valid Email address");
        }

    }

}