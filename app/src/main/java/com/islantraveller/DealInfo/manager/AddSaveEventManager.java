package com.islantraveller.DealInfo.manager;

import com.islantraveller.DealInfo.Model.AddFavouriteDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddSaveEventManager {
    private ApiCallback.SaveEventManagerCallback saveEventManagerCallback;

    public AddSaveEventManager(ApiCallback.SaveEventManagerCallback saveEventManagerCallback) {
        this.saveEventManagerCallback = saveEventManagerCallback;
    }

    public void callAddSaveApi(String token,String deal_id){
        saveEventManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
       //
        // String deal_id = AppSession.getValue(ct)
        Call<AddFavouriteDTO> userResponseCall = api.callAddSaveEventApi(token,deal_id);
        userResponseCall.enqueue(new Callback<AddFavouriteDTO>() {
            @Override
            public void onResponse(Call<AddFavouriteDTO> call, Response<AddFavouriteDTO> response) {
                saveEventManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        saveEventManagerCallback.onSuccessSave(response.body());
                    }else {
                        saveEventManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    saveEventManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<AddFavouriteDTO> call, Throwable t) {
                saveEventManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    saveEventManagerCallback.onError("Network down or no internet connection");
                }else {
                    saveEventManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }
    public void callDeleteSaveApi(String token,String deal_id){
        saveEventManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        //
        // String deal_id = AppSession.getValue(ct)
        Call<AddFavouriteDTO> userResponseCall = api.callDeleteUserSaveEventDealApi(token,deal_id);
        userResponseCall.enqueue(new Callback<AddFavouriteDTO>() {
            @Override
            public void onResponse(Call<AddFavouriteDTO> call, Response<AddFavouriteDTO> response) {
                saveEventManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        saveEventManagerCallback.onSuccessDelete(response.body());
                    }else {
                        saveEventManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    saveEventManagerCallback.onError("Opps something went wrong!");
                    // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<AddFavouriteDTO> call, Throwable t) {
                saveEventManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    saveEventManagerCallback.onError("Network down or no internet connection");
                }else {
                    saveEventManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }
}
