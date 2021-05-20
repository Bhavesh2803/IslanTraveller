
package com.islantraveller.Login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginParameter implements Serializable {

    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("fcm_token")
    private String fcm_token;
    @SerializedName("device_type")
    private String device_type;
    @SerializedName("login_type")
    private String login_type;
    @SerializedName("name")
    private String name;
    @SerializedName("fb_profile")
    private String fb_profile;
    @SerializedName("fb_id")
    private String fb_id;



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 public String getFb_profile() {
        return fb_profile;
    }

    public void setFb_profile(String fb_profile) {
        this.fb_profile = fb_profile;
    }

    public String getFb_id() {
        return fb_id;
    }

    public void setFb_id(String fb_id) {
        this.fb_id = fb_id;
    }

}
