package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

public class ActivityLogin extends AppCompatActivity {

    TextInputLayout act_login_email_txt;
    TextInputLayout act_login_pwd_txt;
    Button act_login_login_btn;
    TextView act_login_signup_txt;
    TextView act_login_pwdFind_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        act_login_email_txt = findViewById(R.id.act_login_email_txt);
        act_login_pwd_txt = findViewById(R.id.act_login_pwd_txt);
        act_login_login_btn = findViewById(R.id.act_login_login_btn);
        act_login_signup_txt = findViewById(R.id.act_login_signup_txt);
        act_login_pwdFind_txt = findViewById(R.id.act_login_pwdFind_txt);
    }


    @Override
    protected void onStart() {
        super.onStart();

        act_login_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goMainIntent);
            }
        });

        act_login_signup_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSignUpIntent = new Intent(getApplicationContext(), ActivitySignup.class);
                startActivity(goSignUpIntent);
            }
        });
    }
}
