package com.islantraveller.DealInfo.manager;

import com.islantraveller.DealInfo.Model.AddFavouriteDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;
import java.io.IOException;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFavouriteManager {
    private ApiCallback.AddFavManagerCallback addFavManagerCallback;

    public AddFavouriteManager(ApiCallback.AddFavManagerCallback addFavManagerCallback) {
        this.addFavManagerCallback = addFavManagerCallback;
    }

    public void callAddFavouriteApi(String token,String deal_id){
        addFavManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
       //
        // String deal_id = AppSession.getValue(ct)
        Call<AddFavouriteDTO> userResponseCall = api.callAddFavApi(token,deal_id);
        userResponseCall.enqueue(new Callback<AddFavouriteDTO>() {
            @Override
            public void onResponse(Call<AddFavouriteDTO> call, Response<AddFavouriteDTO> response) {
                addFavManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        addFavManagerCallback.onSuccessAddFav(response.body());
                    }else {
                        addFavManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    addFavManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<AddFavouriteDTO> call, Throwable t) {
                addFavManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    addFavManagerCallback.onError("Network down or no internet connection");
                }else {
                    addFavManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }


    public void callDeleteFavouriteApi(String token,String deal_id){
        addFavManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        //
        // String deal_id = AppSession.getValue(ct)
        Call<AddFavouriteDTO> userResponseCall = api.callDeleteFavouriteApi(token,deal_id);
        userResponseCall.enqueue(new Callback<AddFavouriteDTO>() {
            @Override
            public void onResponse(Call<AddFavouriteDTO> call, Response<AddFavouriteDTO> response) {
                addFavManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        addFavManagerCallback.onSuccessDeleteFav(response.body());
                    }else {
                        addFavManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    addFavManagerCallback.onError("Opps something went wrong!");
                    // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<AddFavouriteDTO> call, Throwable t) {
                addFavManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    addFavManagerCallback.onError("Network down or no internet connection");
                }else {
                    addFavManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
