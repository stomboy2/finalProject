package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalproject.retrofit.RetrofitConnect;
import com.example.finalproject.retrofit.RetrofitInterface;
import com.example.finalproject.retrofit.pojo.VideoListModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityVodViewer extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager mLayoutManager;

    RetrofitInterface retrofitInterface;
    VodViewerAdapter vodViewerAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vod_viewer);

        recyclerView = findViewById(R.id.act_vod_viewer_recyclerview);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        retrofitInterface = RetrofitConnect.getClient().create(RetrofitInterface.class);


        Call<VideoListModel> videoListModelCall = retrofitInterface.getVideoList();
        videoListModelCall.enqueue(new Callback<VideoListModel>() {
            @Override
            public void onResponse(Call<VideoListModel> call, Response<VideoListModel> response) {
                vodViewerAdapter = new VodViewerAdapter(response.body());
                recyclerView.setAdapter(vodViewerAdapter);
            }

            @Override
            public void onFailure(Call<VideoListModel> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(vodViewerAdapter);
    }
}
