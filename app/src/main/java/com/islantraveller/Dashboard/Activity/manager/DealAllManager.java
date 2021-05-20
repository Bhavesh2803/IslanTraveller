package com.islantraveller.Dashboard.Activity.manager;

import com.islantraveller.Dashboard.Activity.Model.CategoryAll.CategoryAllDTO;
import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealAllManager {
    private ApiCallback.DealAllManagerCallback dealAllManagerCallback;

    public DealAllManager(ApiCallback.DealAllManagerCallback dealAllManagerCallback) {
        this.dealAllManagerCallback = dealAllManagerCallback;
    }

    public void callDealAllApi(String token,GetAllDealParameter getAllDealParameter){
        dealAllManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<DealAllDTO> userResponseCall = api.callDealAllApi(token,getAllDealParameter);
        userResponseCall.enqueue(new Callback<DealAllDTO>() {
            @Override
            public void onResponse(Call<DealAllDTO> call, Response<DealAllDTO> response) {
                dealAllManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        dealAllManagerCallback.onSuccessDealAll(response.body());
                    }else {
                        dealAllManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    dealAllManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<DealAllDTO> call, Throwable t) {
                dealAllManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    dealAllManagerCallback.onError("Network down or no internet connection");
                }else {
                    dealAllManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }



    public void callUpdateLatLongApi(String token,UpdateLatLongParameter updateLatLongParameter){
        dealAllManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<UpdateLatLongDTO> userResponseCall = api.callUpdateLatLongApi(token,updateLatLongParameter);
        userResponseCall.enqueue(new Callback<UpdateLatLongDTO>() {
            @Override
            public void onResponse(Call<UpdateLatLongDTO> call, Response<UpdateLatLongDTO> response) {
                dealAllManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        dealAllManagerCallback.onSuccessUpdateLatLong(response.body());
                    }else {
                        dealAllManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    dealAllManagerCallback.onError("Opps something went wrong!");
                    // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<UpdateLatLongDTO> call, Throwable t) {
                dealAllManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    dealAllManagerCallback.onError("Network down or no internet connection");
                }else {
                    dealAllManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
