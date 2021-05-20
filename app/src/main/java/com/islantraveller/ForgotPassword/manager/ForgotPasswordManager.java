package com.islantraveller.ForgotPassword.manager;

import com.islantraveller.Dashboard.Activity.Model.LogoutDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordManager {
    private ApiCallback.ForgotPasswordManagerCallback forgotPasswordManagerCallback;

    public ForgotPasswordManager(ApiCallback.ForgotPasswordManagerCallback forgotPasswordManagerCallback) {
        this.forgotPasswordManagerCallback = forgotPasswordManagerCallback;
    }

    public void callForgotPasswordApi(String email){
        forgotPasswordManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<LogoutDTO> userResponseCall = api.callForgotPasswordApi(email);
        userResponseCall.enqueue(new Callback<LogoutDTO>() {
            @Override
            public void onResponse(Call<LogoutDTO> call, Response<LogoutDTO> response) {
                forgotPasswordManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        forgotPasswordManagerCallback.onSuccessForgotPassword(response.body());
                    }else {
                        forgotPasswordManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    forgotPasswordManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<LogoutDTO> call, Throwable t) {
                forgotPasswordManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    forgotPasswordManagerCallback.onError("Network down or no internet connection");
                }else {
                    forgotPasswordManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
