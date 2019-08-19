package com.example.finalproject.retrofit.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HealthClubPositionListModel {
    @SerializedName("healthClubPositionList")
    private List<HealthClubPositionModel> healthClubPositionList = null;

    public List<HealthClubPositionModel> getHealthClubPositionList() {
        return healthClubPositionList;
    }

    public class HealthClubPositionModel{
        @SerializedName("name")
        public String name;

        @SerializedName("latitude")
        public String latitude;

        @SerializedName("longitude")
        public String longitude;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
