
package com.islantraveller.Dashboard.Activity.manager;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAllDealParameter {

    @SerializedName("category_id")
    @Expose
    private String categoryId;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

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

    @SerializedName("distance")
    @Expose
    private int distance;
    @SerializedName("lat")
    @Expose
    private String lat;
    @SerializedName("lng")
    @Expose
    private String lng;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("start_time")
    @Expose
    private String start_time;

    public boolean isTop_rating() {
        return top_rating;
    }

    public void setTop_rating(boolean top_rating) {
        this.top_rating = top_rating;
    }

    @SerializedName("top_rating")
    @Expose
    private boolean top_rating;

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    @SerializedName("end_time")
    @Expose
    private String end_time;

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

}