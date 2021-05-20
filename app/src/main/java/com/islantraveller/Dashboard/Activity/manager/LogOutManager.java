package com.islantraveller.Dashboard.Activity.manager;

import com.islantraveller.Dashboard.Activity.Model.LogoutDTO;
import com.islantraveller.Login.LoginParameter;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogOutManager {
    private ApiCallback.LogOutManagerCallback logOutManagerCallback;

    public LogOutManager(ApiCallback.LogOutManagerCallback logOutManagerCallback) {
        this.logOutManagerCallback = logOutManagerCallback;
    }

    public void callLogOutApi(String token){
        logOutManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<LogoutDTO> userResponseCall = api.callLoginApi(token);
        userResponseCall.enqueue(new Callback<LogoutDTO>() {
            @Override
            public void onResponse(Call<LogoutDTO> call, Response<LogoutDTO> response) {
                logOutManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        logOutManagerCallback.onSuccessLogOut(response.body());
                    }else {
                        logOutManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    logOutManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<LogoutDTO> call, Throwable t) {
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
