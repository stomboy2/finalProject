package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.finalproject.retrofit.RetrofitConnect;
import com.example.finalproject.retrofit.RetrofitInterface;
import com.example.finalproject.retrofit.pojo.CurrentVideoListModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityCurrentViewer extends AppCompatActivity {

    RecyclerView recyclerview;
    RecyclerView.LayoutManager mLayoutManager;

    RetrofitInterface retrofitInterface;
    CurrentViewerAdapter currentViewerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_viewer);

        recyclerview = findViewById(R.id.act_current_viewer_recyclerview);
        recyclerview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(mLayoutManager);

        retrofitInterface = RetrofitConnect.getClient().create(RetrofitInterface.class);

        Call<CurrentVideoListModel> currentVideoListModelCall = retrofitInterface.getCurrentVideoList();
        currentVideoListModelCall.enqueue(new Callback<CurrentVideoListModel>() {
            @Override
            public void onResponse(Call<CurrentVideoListModel> call, Response<CurrentVideoListModel> response) {
                currentViewerAdapter = new CurrentViewerAdapter(response.body());
                Log.e("반환값", String.valueOf(response.body().getCurrentVideoList().size()));
                recyclerview.setAdapter(currentViewerAdapter);
            }

            @Override
            public void onFailure(Call<CurrentVideoListModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerview.setAdapter(currentViewerAdapter);
    }
}
