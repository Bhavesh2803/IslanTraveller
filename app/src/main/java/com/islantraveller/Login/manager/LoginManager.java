package com.islantraveller.Login.manager;

import com.islantraveller.Login.LoginParameter;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginManager {
    private ApiCallback.LoginManagerCallback loginManagerCallback;

    public LoginManager(ApiCallback.LoginManagerCallback loginManagerCallback) {
        this.loginManagerCallback = loginManagerCallback;
    }

    public void callLoginApi(LoginParameter loginParameter){
        loginManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<LoginDTO> userResponseCall = api.callLoginApi(loginParameter);
        userResponseCall.enqueue(new Callback<LoginDTO>() {
            @Override
            public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                loginManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        loginManagerCallback.onSuccessLogin(response.body());
                    }else {
                        loginManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    loginManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<LoginDTO> call, Throwable t) {
                loginManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    loginManagerCallback.onError("Network down or no internet connection");
                }else {
                    loginManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
