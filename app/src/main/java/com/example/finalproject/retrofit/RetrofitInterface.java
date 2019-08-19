package com.example.finalproject.retrofit;

import com.example.finalproject.retrofit.pojo.CurrentVideoListModel;
import com.example.finalproject.retrofit.pojo.HealthProductListModel;
import com.example.finalproject.retrofit.pojo.KakaoPayResult;
import com.example.finalproject.retrofit.pojo.VideoListModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RetrofitInterface {

    ///회원가입시 DB에 user를 insert 하기 위한 Post
    @FormUrlEncoded
    @POST("userInsert.php")
    Call<String> userSignUp(@Field("userId") String userId, @Field("userEmail") String userEmail, @Field("userPwd") String userPwd);

    //스트리밍 방송을 시작할때 회원들에게 스트리머가 방송을 하고 있다는걸 보여주기위해 DB에 방송의 제목과 시작시간을 저장하기 위한 요청
    @FormUrlEncoded
    @POST("setVideo.php")
    Call<Void> videoSet(@Field("title") String title, @Field("currentTime") String currentTime);

    //스티리밍 방송이 끝났을때 VOD로 저장을 시켜는데 그때 저장된 VOD 목록을 불러오는 요청
    @GET("getVideoList.php")
    Call<VideoListModel> getVideoList();

    //스트리머가 방송을 하고 있을때 그 방송 목록을 갖고오기 위한 요청
    @GET("getCurrentVideoList.php")
    Call<CurrentVideoListModel> getCurrentVideoList();

    //스트리머가 방송을 종료했을때 썸네일 생성을 하기 위한 요청
    @FormUrlEncoded
    @POST("thumnail.php")
    Call<Void> makeThumbnail(@Field("title") String title, @Field("currentTime") String currentTime);

    //헬스용품 목록을 갖고 오기 위해서 요청
    @GET("getHealthProduct.php")
    Call<HealthProductListModel> getHealthProductListModelCall();

    //카카오톡페이를 이용할때 결제 준비를 위한 요청
    @FormUrlEncoded
    @POST("/v1/payment/ready")
    Call<KakaoPayResult> approvalPrepare(
      @Header("Authorization") String auto,
      @Field("cid") String cid,
      @Field("partner_order_id") String partner_order_id,
      @Field("partner_user_id") String partner_user_id,
      @Field("item_name") String item_name,
      @Field("quantity") int quantity,
      @Field("total_amount") int total_amount,
      @Field("tax_free_amount") int tax_free_amount,
      @Field("approval_url") String approval_url,
      @Field("cancel_url") String cancel_url,
      @Field("fail_url") String fail_url
    );

    //카카오톡페이를 이용할때 결제 완료를 위한 요청
    @FormUrlEncoded
    @POST("/v1/payment/approve ")
    Call<KakaoPayResult> approvalFinish(
            @Header("Authorization") String auto,
            @Field("cid") String cid,
            @Field("tid") String tid,
            @Field("partner_order_id") String partner_order_id,
            @Field("partner_user_id") String partner_user_id,
            @Field("pg_token") String pg_token
    );
}
