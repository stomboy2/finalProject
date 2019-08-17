package com.example.finalproject;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.retrofit.pojo.CurrentVideoListModel;
import com.example.finalproject.streaming.viewer.ViewerActivity_;

public class CurrentViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    public static String vodViewerTitle;
    public static String vodViewerTime;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.current_viewer_recyclerview_item_txt);
        }
    }

    CurrentVideoListModel currentVideoListModel;

    CurrentViewerAdapter(CurrentVideoListModel currentVideoListModel){
        this.currentVideoListModel =currentVideoListModel;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_current_viewer_recyclerview_item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.textView.setText(currentVideoListModel.getCurrentVideoList().get(position).title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vodViewerTitle = currentVideoListModel.getCurrentVideoList().get(position).title;
                vodViewerTime = currentVideoListModel.getCurrentVideoList().get(position).time;

                Intent goViewer = new Intent(view.getContext(), ViewerActivity_.class);
                view.getContext().startActivity(goViewer);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(currentVideoListModel != null){
            return currentVideoListModel.getCurrentVideoList().size();
        }else {
            return 0;
        }
    }
}
