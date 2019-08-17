package com.example.finalproject.streaming.viewer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;

import java.util.ArrayList;

public class ViewerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        MyViewHolder(View view){
            super(view);
            textView = view.findViewById(R.id.act_viewer_recyc_item_txt);
        }

    }

    ArrayList<String> chatList;

    ViewerAdapter(ArrayList chatList){
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_viewer_recyclerview_item, parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        MyViewHolder myViewHolder = (MyViewHolder) holder;
        myViewHolder.textView.setText(chatList.get(position));

    }

    @Override
    public int getItemCount() {
        if(chatList != null){
            return chatList.size();
        }else {
            return 0;
        }
    }
}
