package com.islantraveller.DealInfo.Model.DealInfoModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.islantraveller.Dashboard.Activity.Model.DealAll.Datum;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("deal_id")
    @Expose
    private String dealId;

    public String getDeal_check_all() {
        return deal_check_all;
    }

    public void setDeal_check_all(String deal_check_all) {
        this.deal_check_all = deal_check_all;
    }

    @SerializedName("deal_check_all")
    @Expose
    private String deal_check_all;

    @SerializedName("deal_start_time")
    @Expose
    private String deal_start_time;

    public String getDeal_start_time() {
        return deal_start_time;
    }

    public void setDeal_start_time(String deal_start_time) {
        this.deal_start_time = deal_start_time;
    }

    public String getDeal_end_time() {
        return deal_end_time;
    }

    public void setDeal_end_time(String deal_end_time) {
        this.deal_end_time = deal_end_time;
    }

    @SerializedName("deal_end_time")
    @Expose
    private String deal_end_time;

    public String getDeal_rating() {
        return deal_rating;
    }

    public void setDeal_rating(String deal_rating) {
        this.deal_rating = deal_rating;
    }

    @SerializedName("deal_rating")
    @Expose
    private String deal_rating;

    @SerializedName("deal_title")
    @Expose
    private String dealTitle;
    @SerializedName("deal_description")
    @Expose
    private String dealDescription;
    @SerializedName("deal_image")
    @Expose
    private String dealImage;
    @SerializedName("deal_validity_from")
    @Expose
    private String dealValidityFrom;
    @SerializedName("deal_validity_to")
    @Expose
    private String dealValidityTo;
    @SerializedName("deal_timing")
    @Expose
    private String dealTiming;
    @SerializedName("deal_person_valid")
    @Expose
    private String dealPersonValid;
    @SerializedName("deal_price")
    @Expose
    private String dealPrice;
    @SerializedName("deal_detail")
    @Expose
    private String dealDetail;
    @SerializedName("deal_free_cancellation")
    @Expose
    private String dealFreeCancellation;
    @SerializedName("deal_address")
    @Expose
    private String dealAddress;
    @SerializedName("location_lat")
    @Expose
    private String dealAddressLat;
    @SerializedName("location_long")
    @Expose
    private String dealAddressLong;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("business_id")
    @Expose
    private String businessId;
    @SerializedName("category_id")
    @Expose
    private String categoryId;
    @SerializedName("sub_category_id")
    @Expose
    private String subCategoryId;
    @SerializedName("promo_code_id")
    @Expose
    private String promoCodeId;
    @SerializedName("is_favourite")
    @Expose
    private String isFavourite;

    @SerializedName("save_event")
    @Expose
    private String saveEvent;

    public List<String> getDeal_valid_days()
    {
        return deal_valid_days;
    }

    public void setDeal_valid_days(List<String> deal_valid_days) {
        this.deal_valid_days = deal_valid_days;
    }

    @SerializedName("deal_valid_days")
    @Expose
    private List<String> deal_valid_days;

    public ArrayList<ImageArray> getImages() {
        return images;
    }

    public void setImages(ArrayList<ImageArray> images) {
        this.images = images;
    }

    @SerializedName("images")
    @Expose
    private ArrayList<ImageArray> images;

    public String getDealId() {
        return dealId;
    }

    public void setDealId(String dealId) {
        this.dealId = dealId;
    }

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String dealTitle) {
        this.dealTitle = dealTitle;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String dealDescription) {
        this.dealDescription = dealDescription;
    }

    public String getDealImage() {
        return dealImage;
    }

    public void setDealImage(String dealImage) {
        this.dealImage = dealImage;
    }

    public String getDealValidityFrom() {
        return dealValidityFrom;
    }

    public void setDealValidityFrom(String dealValidityFrom) {
        this.dealValidityFrom = dealValidityFrom;
    }

    public String getDealValidityTo() {
        return dealValidityTo;
    }

    public void setDealValidityTo(String dealValidityTo) {
        this.dealValidityTo = dealValidityTo;
    }

    public String getDealTiming() {
        return dealTiming;
    }

    public void setDealTiming(String dealTiming) {
        this.dealTiming = dealTiming;
    }

    public String getDealPersonValid() {
        return dealPersonValid;
    }

    public void setDealPersonValid(String dealPersonValid) {
        this.dealPersonValid = dealPersonValid;
    }

    public String getDealPrice() {
        return dealPrice;
    }

    public void setDealPrice(String dealPrice) {
        this.dealPrice = dealPrice;
    }

    public String getDealDetail() {
        return dealDetail;
    }

    public void setDealDetail(String dealDetail) {
        this.dealDetail = dealDetail;
    }

    public String getDealFreeCancellation() {
        return dealFreeCancellation;
    }

    public void setDealFreeCancellation(String dealFreeCancellation) {
        this.dealFreeCancellation = dealFreeCancellation;
    }

    public String getDealAddress() {
        return dealAddress;
    }

    public void setDealAddress(String dealAddress) {
        this.dealAddress = dealAddress;
    }

    public String getDealAddressLat() {
        return dealAddressLat;
    }

    public void setDealAddressLat(String dealAddressLat) {
        this.dealAddressLat = dealAddressLat;
    }

    public String getDealAddressLong() {
        return dealAddressLong;
    }

    public void setDealAddressLong(String dealAddressLong) {
        this.dealAddressLong = dealAddressLong;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

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

    public String getPromoCodeId() {
        return promoCodeId;
    }

    public void setPromoCodeId(String promoCodeId) {
        this.promoCodeId = promoCodeId;
    }

    public String getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        this.isFavourite = isFavourite;
    }

    public String getSaveEvent() {
        return saveEvent;
    }

    public void setSaveEvent(String saveEvent) {
        this.saveEvent = saveEvent;
    }
    public class ImageArray {
        public String getDeal_image() {
            return deal_image;
        }

        public void setDeal_image(String deal_image) {
            this.deal_image = deal_image;
        }

        @SerializedName("deal_image")
        @Expose
        private String deal_image;
    }
}