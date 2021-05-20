package com.islantraveller.Dashboard.Activity.manager;

import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.Dashboard.Activity.Model.Locations.LocationsList;
import com.islantraveller.Dashboard.Activity.Model.SavedEvents.SavedDealsDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaveEventDealAllManager {
    private ApiCallback.UserSaveEventListManagerCallback userSaveEventListManagerCallback;

    public SaveEventDealAllManager(ApiCallback.UserSaveEventListManagerCallback userSaveEventListManagerCallback) {
        this.userSaveEventListManagerCallback = userSaveEventListManagerCallback;
    }

    public void callSaveDealAllApi(String token){
        userSaveEventListManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<DealAllDTO> userResponseCall = api.callUserSaveEventListApi(token);
        userResponseCall.enqueue(new Callback<DealAllDTO>() {
            @Override
            public void onResponse(Call<DealAllDTO> call, Response<DealAllDTO> response) {
                userSaveEventListManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        userSaveEventListManagerCallback.onSuccessUserSaveEvent(response.body());
                    }else {
                        userSaveEventListManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    userSaveEventListManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<DealAllDTO> call, Throwable t) {
                userSaveEventListManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    userSaveEventListManagerCallback.onError("Network down or no internet connection");
                }else {
                    userSaveEventListManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

    public void callLocationListApi(String token){
        userSaveEventListManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<LocationsList> userResponseCall = api.GetLocationAllApi(token);
        userResponseCall.enqueue(new Callback<LocationsList>() {
            @Override
            public void onResponse(Call<LocationsList> call, Response<LocationsList> response) {
                userSaveEventListManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        userSaveEventListManagerCallback.onSuccessLocationsList(response.body());
                    }else {
                        userSaveEventListManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    userSaveEventListManagerCallback.onError("Opps something went wrong!");
                    // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<LocationsList> call, Throwable t) {
                userSaveEventListManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    userSaveEventListManagerCallback.onError("Network down or no internet connection");
                }else {
                    userSaveEventListManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }


}
