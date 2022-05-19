package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.Model.ProfileDetail;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    ImageView backButton;
    Button update;
    TextView toolbarHading, edtEmail, edtPhoneNo, edtUserName, edtSurname, gender, edtTown, edtCountry, edtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();
        getProfile(Glob.token, "1");
    }

    public void init() {

        Glob.progressDialog(this);
        backButton = findViewById(R.id.backButton);
        toolbarHading = findViewById(R.id.toolbarHading);
        toolbarHading.setText("Edit Profile");
        edtEmail = findViewById(R.id.edtEmail);
        edtPhoneNo = findViewById(R.id.edtPhoneNo);
        edtUserName = findViewById(R.id.edtUserName);
        edtSurname = findViewById(R.id.edtSurname);
        gender = findViewById(R.id.gender);
        edtTown = findViewById(R.id.edtTown);
        edtCountry = findViewById(R.id.edtCountry);
        edtDescription = findViewById(R.id.edtDescription);
        update = findViewById(R.id.update);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(Glob.token, "1", edtEmail.getText().toString().trim(),
                        edtPhoneNo.getText().toString().trim(),
                        edtUserName.getText().toString().trim(),
                        edtSurname.getText().toString().trim(),
                        gender.getText().toString().trim(),
                        "4030", "101",
                        edtDescription.getText().toString().trim());
            }
        });
    }

    public void getProfile(String token, String userId) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.getProfile(token, userId).enqueue(new Callback<ProfileDetail>() {
            @Override
            public void onResponse(Call<ProfileDetail> call, Response<ProfileDetail> response) {

                ProfileDetail profileDetail = response.body();
                ProfileDetail.Detail model = profileDetail.getData();


                edtEmail.setText(model.getEmail());
                edtPhoneNo.setText(model.getPhone_number());
                edtUserName.setText(model.getUsername());
                edtSurname.setText(model.getSurname());
                gender.setText(model.getGender());
                edtTown.setText(model.getTown());
                edtCountry.setText(model.getCountry());

                edtDescription.setText(model.getDescription());


                Log.e("TAG", "onResponse: " + model.getProfile_image());

                Glob.dialog.dismiss();


            }

            @Override
            public void onFailure(Call<ProfileDetail> call, Throwable t) {
                Glob.dialog.dismiss();
            }
        });
    }

    public void updateProfile(String token, String user_id, String email, String phone_number,
                              String name, String surname, String gender,
                              String town_id, String country_id, String description) {


        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.updateProfile(token, user_id, email, phone_number, name, surname,
                gender, town_id, country_id, description).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();

                Toast.makeText(EditProfileActivity.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();

                Glob.dialog.dismiss();
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {

                Glob.dialog.dismiss();
            }
        });

    }
}