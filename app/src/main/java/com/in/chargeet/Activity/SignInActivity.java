package com.in.chargeet.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.in.chargeet.R;

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


//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                startActivity(intent);

                emailValidator(edtEmail);

            }
        });

    }

    public void emailValidator(EditText etMail) {

        String emailToText = etMail.getText().toString();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            Toast.makeText(this, "Email Verified !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
        }
    }
}