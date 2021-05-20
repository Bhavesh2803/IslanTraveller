package com.islantraveller.SignUp.manager;

import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;
import com.islantraveller.SignUp.Model.SignUpDTO;
import com.islantraveller.SignUp.SignUpParameter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpManager {
    private ApiCallback.SignUpManagerCallback signUpManagerCallback;

    public SignUpManager(ApiCallback.SignUpManagerCallback signUpManagerCallback) {
        this.signUpManagerCallback = signUpManagerCallback;
    }

    public void callSignUpApi(SignUpParameter signUpParameter){
        signUpManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<SignUpDTO> userResponseCall = api.callRegistrationApi(signUpParameter);
        userResponseCall.enqueue(new Callback<SignUpDTO>() {
            @Override
            public void onResponse(Call<SignUpDTO> call, Response<SignUpDTO> response) {
                signUpManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        signUpManagerCallback.onSuccessSignUp(response.body());
                    }else {
                        signUpManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    signUpManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<SignUpDTO> call, Throwable t) {
                signUpManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    signUpManagerCallback.onError("Network down or no internet connection");
                }else {
                    signUpManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
