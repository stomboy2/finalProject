package com.example.finalproject.retrofit.pojo;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

public class KakaoPayResult {
    //결제요청
    @SerializedName("tid")
    public String tid;
    @SerializedName("next_redirect_app_url")
    public String next_redirect_app_url;
    @SerializedName("next_redirect_mobile_url")
    public String next_redirect_mobile_url;
    @SerializedName("next_redirect_pc_url")
    public String next_redirect_pc_url;
    @SerializedName("android_app_scheme")
    public String android_app_scheme;
    @SerializedName("ios_app_scheme")
    public String ios_app_scheme;
    @SerializedName("created_at")
    public String created_at;

    //결제승인
    @SerializedName("aid")
    public String aid;
    @SerializedName("cid")
    public String cid;
    @SerializedName("sid")
    public String sid;
    @SerializedName("partner_order_id")
    public String partner_order_id;
    @SerializedName("partner_user_id")
    public String partner_user_id;
    @SerializedName("payment_method_type")
    public String payment_method_type;
    @SerializedName("amount")
    public JsonObject amount;
    @SerializedName("card_info")
    public JsonObject card_info;
    @SerializedName("item_name")
    public String item_name;
    @SerializedName("item_code")
    public String item_code;
    @SerializedName("quantity")
    public String quantity;
    @SerializedName("approved_at")
    public String approved_at;
    @SerializedName("payload")
    public String payload;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getNext_redirect_app_url() {
        return next_redirect_app_url;
    }

    public void setNext_redirect_app_url(String next_redirect_app_url) {
        this.next_redirect_app_url = next_redirect_app_url;
    }

    public String getNext_redirect_mobile_url() {
        return next_redirect_mobile_url;
    }

    public void setNext_redirect_mobile_url(String next_redirect_mobile_url) {
        this.next_redirect_mobile_url = next_redirect_mobile_url;
    }

    public String getNext_redirect_pc_url() {
        return next_redirect_pc_url;
    }

    public void setNext_redirect_pc_url(String next_redirect_pc_url) {
        this.next_redirect_pc_url = next_redirect_pc_url;
    }

    public String getAndroid_app_scheme() {
        return android_app_scheme;
    }

    public void setAndroid_app_scheme(String android_app_scheme) {
        this.android_app_scheme = android_app_scheme;
    }

    public String getIos_app_scheme() {
        return ios_app_scheme;
    }

    public void setIos_app_scheme(String ios_app_scheme) {
        this.ios_app_scheme = ios_app_scheme;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getPartner_order_id() {
        return partner_order_id;
    }

    public void setPartner_order_id(String partner_order_id) {
        this.partner_order_id = partner_order_id;
    }

    public String getPartner_user_id() {
        return partner_user_id;
    }

    public void setPartner_user_id(String partner_user_id) {
        this.partner_user_id = partner_user_id;
    }

    public String getPayment_method_type() {
        return payment_method_type;
    }

    public void setPayment_method_type(String payment_method_type) {
        this.payment_method_type = payment_method_type;
    }

    public JsonObject getAmount() {
        return amount;
    }

    public void setAmount(JsonObject amount) {
        this.amount = amount;
    }

    public JsonObject getCard_info() {
        return card_info;
    }

    public void setCard_info(JsonObject card_info) {
        this.card_info = card_info;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getApproved_at() {
        return approved_at;
    }

    public void setApproved_at(String approved_at) {
        this.approved_at = approved_at;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}
