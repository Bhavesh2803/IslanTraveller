package com.islantraveller.FaceBookLoginActivity.manager;

import com.islantraveller.Dashboard.Activity.Model.LogoutDTO;
import com.islantraveller.FaceBookLoginActivity.SocialLoginParameter;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocialLoginManager {
    private ApiCallback.SocialLoginManagerCallback logOutManagerCallback;

    public SocialLoginManager(ApiCallback.SocialLoginManagerCallback socialLoginManagerCallback) {
        this.logOutManagerCallback = socialLoginManagerCallback;
    }

    public void SocialLogin(SocialLoginParameter socialLoginParameter){
        logOutManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<LoginDTO> userResponseCall = api.callSocialLoginApi(socialLoginParameter);
        userResponseCall.enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                logOutManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        logOutManagerCallback.onSuccessSocialSignUp(response.body());
                    }else {
                        logOutManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    logOutManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<LoginDTO> call, Throwable t) {
                logOutManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    logOutManagerCallback.onError("Network down or no internet connection");
                }else {
                    logOutManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
