package com.example.finalproject.retrofit.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VideoListModel {
    @SerializedName("videoList")
    private List<videoModel> videoModelList = null;

    public List<videoModel> getCategoredStockList(){
        return  videoModelList;
    }

    public class videoModel{
        @SerializedName("title")
        public String title;

        @SerializedName("time")
        public String time;

        @SerializedName("img")
        public String img;

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

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }
    }
}
