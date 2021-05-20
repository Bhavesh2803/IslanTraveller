package com.islantraveller.ContactUs.manager;

import com.islantraveller.ContactUs.ContactParameter;
import com.islantraveller.ContactUs.ContactUs;
import com.islantraveller.ContactUs.Model.ContactDTO;
import com.islantraveller.Login.LoginParameter;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsManager {
    private ApiCallback.ContactManagerCallback loginManagerCallback;

    public ContactUsManager(ApiCallback.ContactManagerCallback loginManagerCallback) {
        this.loginManagerCallback = loginManagerCallback;
    }

    public void callContactUsApi(ContactParameter loginParameter){
        loginManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<ContactDTO> userResponseCall = api.callContactApi(loginParameter);
        userResponseCall.enqueue(new Callback<ContactDTO>() {
            @Override
            public void onResponse(Call<ContactDTO> call, Response<ContactDTO> response) {
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
            public void onFailure(Call<ContactDTO> call, Throwable t) {
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
