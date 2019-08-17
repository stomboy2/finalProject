package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.finalproject.retrofit.RetrofitConnect;
import com.example.finalproject.retrofit.RetrofitInterface;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivitySignup extends AppCompatActivity {

    ImageView act_signup_back_img;
    TextInputLayout act_signup_name_txt;
    TextInputLayout act_signup_email_txt;
    TextInputLayout act_signup_pwd_txt;
    Button act_signup_signup_btn;
    RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        act_signup_back_img = findViewById(R.id.act_signup_back_img);
        act_signup_name_txt = findViewById(R.id.act_signup_name_txt);
        act_signup_email_txt = findViewById(R.id.act_signup_email_txt);
        act_signup_pwd_txt = findViewById(R.id.act_signup_pwd_txt);
        act_signup_signup_btn = findViewById(R.id.act_signup_signup_btn);

        retrofitInterface = RetrofitConnect.getClient().create(RetrofitInterface.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        act_signup_back_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        act_signup_signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = act_signup_name_txt.getEditText().getText().toString();
                String email = act_signup_email_txt.getEditText().getText().toString();
                String pwd = act_signup_pwd_txt.getEditText().getText().toString();

                if(name !="" && email != "" && pwd != ""){
                    Call<String> userInsert = retrofitInterface.userSignUp(name, email, pwd);
                    userInsert.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Toast.makeText(getApplicationContext(),"회원가입 완료", Toast.LENGTH_LONG).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {

                        }
                    });
                }
            }
        });
    }
}
