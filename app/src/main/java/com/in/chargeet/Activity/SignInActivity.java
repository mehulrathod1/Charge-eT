package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.in.chargeet.Model.LoginModel;
import com.in.chargeet.R;
import com.in.chargeet.Retrofit.Api;
import com.in.chargeet.Retrofit.RetrofitClient;
import com.in.chargeet.Utils.Glob;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    TextView forgotPassword, signUp;
    Button btnLogin;
    EditText edtEmail, edtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        init();
    }

    public void init() {

        Glob.progressDialog(this);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUp = findViewById(R.id.signUp);
        btnLogin = findViewById(R.id.btnLogin);
        edtPassword = findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);


        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);

            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                emailValidator(edtEmail);


            }
        });

    }

    public void emailValidator(EditText etMail) {

        String emailToText = etMail.getText().toString();
        String password = edtPassword.getText().toString().trim();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {


            if (password.equals("") || (password.length() < 8)) {

                edtPassword.setError("Please Enter valid password");
            } else {

                login(Glob.token, edtEmail.getText().toString().toString(), edtPassword.getText().toString().trim());
            }
        } else {
            edtEmail.setError("Please Enter valid Email address");
        }

    }

    public void login(String token, String email, String password) {

        Api call = RetrofitClient.getClient(Glob.baseUrl).create(Api.class);
        Glob.dialog.show();

        call.login(token, email, password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {

                LoginModel loginModel = response.body();

                if (loginModel.getStatus().equals("true")) {
                    LoginModel.Login model = loginModel.getData();
                    Toast.makeText(SignInActivity.this, "" + loginModel.getMessage(), Toast.LENGTH_SHORT).show();
                    Glob.userId = model.getUser_id();
                    Glob.dialog.dismiss();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);


                }
                else {

                    Glob.dialog.dismiss();
                    Toast.makeText(SignInActivity.this, "" + loginModel.getMessage(), Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                Glob.dialog.dismiss();

                Log.e("onFailure:", "onFailure: " + t.getMessage());
            }
        });

    }
}