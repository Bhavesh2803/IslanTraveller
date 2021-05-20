package com.islantraveller.MyFavourite.Manager;

import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserFavouriteListManager {
    private ApiCallback.UserFavouriteListManagerCallback userFavouriteListManagerCallback;

    public GetUserFavouriteListManager(ApiCallback.UserFavouriteListManagerCallback userFavouriteListManagerCallback) {
        this.userFavouriteListManagerCallback = userFavouriteListManagerCallback;
    }

    public void callFavouriteAllApi(String token){
        userFavouriteListManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<DealAllDTO> userResponseCall = api.callUserFavouriteListApi(token);
        userResponseCall.enqueue(new Callback<DealAllDTO>() {
            @Override
            public void onResponse(Call<DealAllDTO> call, Response<DealAllDTO> response) {
                userFavouriteListManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        userFavouriteListManagerCallback.onSuccessUserfavourite(response.body());
                    }else {
                        userFavouriteListManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    userFavouriteListManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<DealAllDTO> call, Throwable t) {
                userFavouriteListManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    userFavouriteListManagerCallback.onError("Network down or no internet connection");
                }else {
                    userFavouriteListManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
