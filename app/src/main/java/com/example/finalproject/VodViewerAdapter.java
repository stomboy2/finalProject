package com.example.finalproject;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.retrofit.pojo.VideoListModel;

public class VodViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

            MyViewHolder(View view){
                super(view);
                imageView = view.findViewById(R.id.viewer_recyclerview_item_image);
                textView = view.findViewById(R.id.viewer_recyclerview_item_txt);
            }

    }

    VideoListModel videoListModel;

    VodViewerAdapter(VideoListModel videoListModel){
        this.videoListModel =videoListModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vod_viewer_recyclerview_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        Glide.with(myViewHolder.itemView.getContext()).load("http://15.164.219.152/"+videoListModel.getCategoredStockList().get(position).img).into(myViewHolder.imageView);
        myViewHolder.textView.setText(videoListModel.getCategoredStockList().get(position).title);
    }

    @Override
    public int getItemCount() {
        if(videoListModel != null){
            return videoListModel.getCategoredStockList().size();
        }else {
            return 0;
        }
    }
}
