package com.islantraveller.Dashboard.Activity.Model.SavedEvents;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

@SerializedName("deal_id")
@Expose
private String dealId;
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
@SerializedName("deal_address_lat")
@Expose
private String dealAddressLat;
@SerializedName("deal_address_long")
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

}