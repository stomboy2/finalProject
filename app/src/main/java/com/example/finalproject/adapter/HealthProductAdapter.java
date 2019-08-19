package com.example.finalproject.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.ActivityHealthProductBuy;
import com.example.finalproject.R;
import com.example.finalproject.retrofit.pojo.HealthProductListModel;

public class HealthProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView productImg;
        TextView productName;
        TextView productPrice;

        MyViewHolder(View view){
            super(view);
            productImg = view.findViewById(R.id.act_health_product_img);
            productName = view.findViewById(R.id.act_health_product_name);
            productPrice = view.findViewById(R.id.act_health_product_price);
        }
    }

    HealthProductListModel healthProductListModel;

    public HealthProductAdapter(HealthProductListModel healthProductListModel){
        this.healthProductListModel = healthProductListModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_health_product_recyclerview_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.productName.setText(healthProductListModel.getHealthProductModelList().get(position).name);
        myViewHolder.productPrice.setText(healthProductListModel.getHealthProductModelList().get(position).price + " SG");
        Glide.with(myViewHolder.itemView.getContext()).load(healthProductListModel.getHealthProductModelList().get(position).imgPath).into(myViewHolder.productImg);

        myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHealthProductBuy = new Intent(myViewHolder.itemView.getContext(), ActivityHealthProductBuy.class);
                goHealthProductBuy.putExtra("img",healthProductListModel.getHealthProductModelList().get(position).imgPath);
                goHealthProductBuy.putExtra("name", healthProductListModel.getHealthProductModelList().get(position).name);
                goHealthProductBuy.putExtra("price", healthProductListModel.getHealthProductModelList().get(position).price);
                myViewHolder.itemView.getContext().startActivity(goHealthProductBuy);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(healthProductListModel != null){
            return healthProductListModel.getHealthProductModelList().size();
        }else{
            return 0;
        }
    }
}
