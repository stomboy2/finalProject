package com.example.finalproject.kakaopay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.finalproject.R;
import com.example.finalproject.retrofit.RetrofitConnect;
import com.example.finalproject.retrofit.RetrofitInterface;
import com.example.finalproject.retrofit.pojo.KakaoPayResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ActivityKakaopayToToken extends AppCompatActivity {

    RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakaopay_to_token);

        retrofitInterface = RetrofitConnect.getClient2().create(RetrofitInterface.class);
        Call<KakaoPayResult> payResultCall = retrofitInterface.approvalPrepare(
                "KakaoAK 3d51acf09951cc15968df201611d1eeb", "TC0ONETIME", "test",
                "test", "토큰 10개", 1000, 1000, 0,
                "https://developers.kakao.com","https://developers.kakao.com","https://developers.kakao.com"
        );

        payResultCall.enqueue(new Callback<KakaoPayResult>() {
            @Override
            public void onResponse(Call<KakaoPayResult> call, Response<KakaoPayResult> response) {
                String nextUrl = response.body().next_redirect_mobile_url;
                String tid = response.body().tid;

                Intent goApprovalFinish = new Intent(getApplicationContext(), ActivityKakaopayWebview.class);
                goApprovalFinish.putExtra("url", nextUrl);
                goApprovalFinish.putExtra("tid", tid);
                startActivity(goApprovalFinish);
            }

            @Override
            public void onFailure(Call<KakaoPayResult> call, Throwable t) {

            }
        });

    }
}
