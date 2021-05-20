
package com.islantraveller.Dashboard.Activity.manager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateLatLongParameter {

    @SerializedName("lat")
    @Expose
    private String lat;

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    @SerializedName("device_type")
    @Expose
    private String device_type;

    @SerializedName("fcm_token")
    @Expose
    private String fcm_token;

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    @SerializedName("lng")
    @Expose
    private String lng;


}