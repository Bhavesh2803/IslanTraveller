
package com.islantraveller.FaceBookLoginActivity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SocialLoginParameter implements Serializable {

    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;

    @SerializedName("fcm_token")
    private String fcm_token;

    @SerializedName("device_type")
    private String device_type;

    @SerializedName("login_type")
    private String login_type;

    @SerializedName("social_profile")
    private String social_profile;

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    public String getSocial_profile() {
        return social_profile;
    }

    public void setSocial_profile(String social_profile) {
        this.social_profile = social_profile;
    }

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    @SerializedName("social_id")
    private String social_id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }





}
