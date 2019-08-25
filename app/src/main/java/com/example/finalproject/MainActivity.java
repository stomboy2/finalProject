package com.example.finalproject;

/**
 * 로그인 후 처음 들어오게되는 화면
 * DrawerLayout을 적용
 * DrawerLayout에 추가해야되는 모든 것들은 여기에서 작성하면 됨.
 * */

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.example.finalproject.googlemap.ActivityGoogleMap;
import com.example.finalproject.kakaopay.ActivityKakaopayToToken;
import com.example.finalproject.retrofit.RetrofitConnect;
import com.example.finalproject.retrofit.RetrofitInterface;
import com.example.finalproject.streaming.broadcaster.BroadCasterActivity_;
import com.example.finalproject.unitygame.UnityPlayerActivity;
import com.google.android.material.navigation.NavigationView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Activity currentActivity;

    public static String vodTitle;
    public static String vodTime;


    RetrofitInterface retrofitInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentActivity = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        retrofitInterface = RetrofitConnect.getClient().create(RetrofitInterface.class);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {

                    //주위 헬스장 찾기
                    case R.id.navigation_item_health_place:
                        Intent goGoogleMap = new Intent(getApplicationContext(), ActivityGoogleMap.class);
                        startActivity(goGoogleMap);
                        break;

                        //카카오페이로 토큰 충전하기
                    case R.id.navigation_item_kakao_token:
                        Intent gokakaoPay = new Intent(getApplicationContext(), ActivityKakaopayToToken.class);
                        startActivity(gokakaoPay);
                        break;

                        //토큰으로 물품 구입하기
                    case R.id.navigation_item_health_product:
                        Intent goHealthProduct = new Intent(getApplicationContext(), ActivityHealthProduct.class);
                        startActivity(goHealthProduct);
                        break;

                        //방송을 하기 위한 case
                    case R.id.nav_sub_presenter:


                        AlertDialog.Builder alert = new AlertDialog.Builder(currentActivity);
                        alert.setTitle("방제목 설정");

                        //alert 창에서 입력한 text 값을 받을 edit
                        final EditText name = new EditText(currentActivity);
                        alert.setView(name);
                        alert.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            //alert창에서 확인 버튼을 클릭했을때
                            public void onClick(DialogInterface dialog, int whichButton) {
                                long time = System.currentTimeMillis();
                                String vodTitleTmp = name.getText().toString();
                                vodTitle = vodTitleTmp;
                                vodTime = String.valueOf(time);

                                //방송을 시작하려고 하는 방의 제목과 시작시간을 db에 저장
                                //저장을 하는 이유는 현재 방송중인 list를 사용자들에게 보여주기 위해서
                                Call<Void> videoSet = retrofitInterface.videoSet(vodTitle,vodTime);
                                videoSet.enqueue(new Callback<Void>() {
                                    @Override
                                    public void onResponse(Call<Void> call, Response<Void> response) {
                                        Intent goPresenterIntent = new Intent(getApplicationContext(), BroadCasterActivity_.class);
                                        startActivity(goPresenterIntent);
                                    }

                                    @Override
                                    public void onFailure(Call<Void> call, Throwable t) {

                                    }
                                });
                            }
                        });
                        alert.setNegativeButton("취소",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) { //취소 버튼을 클ㅣ
                            }
                        });
                        alert.show();
                        break;

                        //방송을 시청하기 위한 case
                    case R.id.nav_sub_viewer:
                        Intent goViewer = new Intent(getApplicationContext(),ActivityCurrentViewer.class);
                        startActivity(goViewer);
                        break;

                        //vod를 보기 위한 case
                    case R.id.nav_sub_Vod_viewer:
                        Intent goVodViewer = new Intent(getApplicationContext(),ActivityVodViewer.class);
                        startActivity(goVodViewer);
                        break;

                    //vod를 보기 위한 case
                    case R.id.nav_sub_game:
                        Intent goPlayGame = new Intent(getApplicationContext(), UnityPlayerActivity.class);
                        startActivity(goPlayGame);
                        break;
                }
                return true;
            }
        });

    }//oncreate END

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
