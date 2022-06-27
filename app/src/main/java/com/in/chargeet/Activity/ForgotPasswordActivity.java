package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.in.chargeet.Model.CommonModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {

    AlertDialog alert;
    AlertDialog.Builder alertDialog;
    Button btnContinue, btnContinueToOtp;
    EditText edtEmail;
    ImageView closePopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        init();
    }


    public void init() {

        Glob.progressDialog(this);
        btnContinue = findViewById(R.id.btnContinue);
        edtEmail = findViewById(R.id.edtEmail);


        alertDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.popup_forgot_password, null);
        alertDialog.setView(dialogLayout);
        alert = alertDialog.create();
        btnContinueToOtp = dialogLayout.findViewById(R.id.btnContinueToOtp);
        closePopup = dialogLayout.findViewById(R.id.closePopup);


        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailValidator(edtEmail);


            }
        });

        btnContinueToOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                forgotPassword(Glob.token, edtEmail.getText().toString());
                Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                startActivity(intent);
            }
        });

        closePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });
    }


    public void emailValidator(EditText etMail) {

        String emailToText = etMail.getText().toString();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {

            alert.show();


        } else {
            etMail.setError("Please Enter valid Email address");
        }

    }

    public void forgotPassword(String token, String email) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();


        call.forgotPassword(token, email).enqueue(new Callback<CommonModel>() {
            @Override
            public void onResponse(Call<CommonModel> call, Response<CommonModel> response) {

                CommonModel commonModel = response.body();

                Toast.makeText(ForgotPasswordActivity.this, "" + commonModel.getMessage(), Toast.LENGTH_SHORT).show();
                Glob.dialog.dismiss();
                Intent intent = new Intent(getApplicationContext(), OtpActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<CommonModel> call, Throwable t) {
                Glob.dialog.dismiss();
            }
        });

    }
}