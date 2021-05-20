package com.islantraveller.Dashboard.Activity.manager;

import com.islantraveller.Dashboard.Activity.Model.CategoryAll.CategoryAllDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryAllManager {
    private ApiCallback.CategoryAllManagerCallback categoryAllManagerCallback;

    public CategoryAllManager(ApiCallback.CategoryAllManagerCallback categoryAllManagerCallback) {
        this.categoryAllManagerCallback = categoryAllManagerCallback;
    }

    public void callCategoryAllApi(String token){
        categoryAllManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
        Call<CategoryAllDTO> userResponseCall = api.callCategoryAllApi(token);
        userResponseCall.enqueue(new Callback<CategoryAllDTO>() {
            @Override
            public void onResponse(Call<CategoryAllDTO> call, Response<CategoryAllDTO> response) {
                categoryAllManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        categoryAllManagerCallback.onSuccessCategoryAll(response.body());
                    }else {
                        categoryAllManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    categoryAllManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<CategoryAllDTO> call, Throwable t) {
                categoryAllManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    categoryAllManagerCallback.onError("Network down or no internet connection");
                }else {
                    categoryAllManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
