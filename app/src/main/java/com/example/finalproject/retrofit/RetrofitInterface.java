package com.example.finalproject.retrofit;

import com.example.finalproject.retrofit.pojo.CurrentVideoListModel;
import com.example.finalproject.retrofit.pojo.VideoListModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @FormUrlEncoded
    @POST("userInsert.php")
    Call<String> userSignUp(@Field("userId") String userId, @Field("userEmail") String userEmail, @Field("userPwd") String userPwd);

    @FormUrlEncoded
    @POST("setVideo.php")
    Call<Void> videoSet(@Field("title") String title, @Field("currentTime") String currentTime);

    @GET("getVideoList.php")
    Call<VideoListModel> getVideoList();

    @GET("getCurrentVideoList.php")
    Call<CurrentVideoListModel> getCurrentVideoList();

    @FormUrlEncoded
    @POST("thumnail.php")
    Call<Void> makeThumbnail(@Field("title") String title, @Field("currentTime") String currentTime);
}
