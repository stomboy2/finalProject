package com.example.finalproject.retrofit.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentVideoListModel {
    @SerializedName("videoList")
    private List<VideoListModel.videoModel> videoModelList = null;

    public List<VideoListModel.videoModel> getCurrentVideoList(){
        return  videoModelList;
    }

    public class videoModel{
        @SerializedName("title")
        public String title;

        @SerializedName("time")
        public String time;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
