package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.finalproject.adapter.HealthProductAdapter;
import com.example.finalproject.retrofit.RetrofitConnect;
import com.example.finalproject.retrofit.RetrofitInterface;
import com.example.finalproject.retrofit.pojo.HealthProductListModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityHealthProduct extends AppCompatActivity {

    RetrofitInterface retrofitInterface;

    RecyclerView recyclerView;
    HealthProductAdapter healthProductAdapter;
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_product);

        recyclerView = findViewById(R.id.act_health_product_recyclerview);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);


        retrofitInterface = RetrofitConnect.getClient().create(RetrofitInterface.class);

        Call<HealthProductListModel> healthProductListModelCall = retrofitInterface.getHealthProductListModelCall();
        healthProductListModelCall.enqueue(new Callback<HealthProductListModel>() {
            @Override
            public void onResponse(Call<HealthProductListModel> call, Response<HealthProductListModel> response) {
                healthProductAdapter = new HealthProductAdapter(response.body());
                recyclerView.setAdapter(healthProductAdapter);
            }

            @Override
            public void onFailure(Call<HealthProductListModel> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(healthProductAdapter);
    }
}
