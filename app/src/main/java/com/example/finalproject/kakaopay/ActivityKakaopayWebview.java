package com.example.finalproject.kakaopay;
/**
 * 카카오페이 관련 WebView를 처리하는 화면
 * 결제 준비 및 결제 완료를 처리
 * */
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.finalproject.R;
import com.example.finalproject.retrofit.RetrofitConnect;
import com.example.finalproject.retrofit.RetrofitInterface;
import com.example.finalproject.retrofit.pojo.KakaoPayResult;

import java.net.URISyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityKakaopayWebview extends AppCompatActivity {

    WebView act_kakaopay_webview;
    RetrofitInterface retrofitInterface;
    String tid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakaopay_webview);

        Intent getIntent = getIntent();
        String nextUrl = getIntent.getExtras().getString("url");
        tid = getIntent.getExtras().getString("tid");


        act_kakaopay_webview = findViewById(R.id.act_kakaopay_webview);
        act_kakaopay_webview.getSettings().setJavaScriptEnabled(true);

        act_kakaopay_webview.loadUrl(nextUrl);
        act_kakaopay_webview.setWebChromeClient(new WebChromeClient());
        act_kakaopay_webview.setWebViewClient(new WebViewClientClass());

        retrofitInterface = RetrofitConnect.getClient2().create(RetrofitInterface.class);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("javascript:") && url.startsWith("intent://")) {
                Intent intent = null;
                try {
                    intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME); //IntentURI처리
                    Uri uri = Uri.parse(intent.getDataString());
                    startActivity(new Intent(Intent.ACTION_VIEW, uri));
                    return true;
                } catch (ActivityNotFoundException e) {
                    if ( intent == null )	return false;
                    String packageName = intent.getPackage(); //packageName should be com.kakao.talk
                    if (packageName != null) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName)));
                        return true;
                    }

                    return false;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
            //결제 승인을 위한 코드 작성
            String pg_Token = url.substring(url.lastIndexOf("=")+1);

            Call<KakaoPayResult> resultCall = retrofitInterface.approvalFinish(
                    "KakaoAK 3d51acf09951cc15968df201611d1eeb", "TC0ONETIME", tid, "test",
                    "test", pg_Token
            );
            resultCall.enqueue(new Callback<KakaoPayResult>() {
                @Override
                public void onResponse(Call<KakaoPayResult> call, Response<KakaoPayResult> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<KakaoPayResult> call, Throwable t) {

                }
            });
            return false;
        }
    }
}
