package com.example.finalproject.retrofit.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HealthProductListModel {
    @SerializedName("healthProductList")
    private List<HealthProductModel> healthProductModelList = null;

    public List<HealthProductModel> getHealthProductModelList(){
        return healthProductModelList;
    }

    public class HealthProductModel{
        @SerializedName("name")
        public String name;

        @SerializedName("price")
        public String price;

        @SerializedName("imgPath")
        public String imgPath;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getImgPath() {
            return imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }
    }
}
