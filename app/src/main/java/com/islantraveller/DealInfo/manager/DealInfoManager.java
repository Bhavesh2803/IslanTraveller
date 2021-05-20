package com.islantraveller.DealInfo.manager;

import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.DealInfo.Model.DealInfoModel.DealInfoDTO;
import com.islantraveller.Network.basic.Api;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.rest.ServiceGenerator;
import com.islantraveller.database.AppSession;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealInfoManager {
    private ApiCallback.DealInfoManagerCallback dealInfoManagerCallback;

    public DealInfoManager(ApiCallback.DealInfoManagerCallback dealInfoManagerCallback) {
        this.dealInfoManagerCallback = dealInfoManagerCallback;
    }

    public void callDealInfoApi(String token,String deal_id){
        dealInfoManagerCallback.onShowLoader();
        final Api api = ServiceGenerator.createService(Api.class);
       //
        // String deal_id = AppSession.getValue(ct)
        Call<DealInfoDTO> userResponseCall = api.callDealInfoApi(token,deal_id);
        userResponseCall.enqueue(new Callback<DealInfoDTO>() {
            @Override
            public void onResponse(Call<DealInfoDTO> call, Response<DealInfoDTO> response) {
                dealInfoManagerCallback.onHideLoader();
                if(null != response.body()){
                    if(response.body().getStatus().equalsIgnoreCase("success")) {
                        dealInfoManagerCallback.onSuccessDealInfo(response.body());
                    }else {
                        dealInfoManagerCallback.onError(response.body().getMessage());
                    }
                }else {
                    dealInfoManagerCallback.onError("Opps something went wrong!");
                   // registrationManagerCallback.onError(response.errorBody().string());
                }
            }

            @Override
            public void onFailure(Call<DealInfoDTO> call, Throwable t) {
                dealInfoManagerCallback.onHideLoader();
                if(t instanceof IOException){
                    dealInfoManagerCallback.onError("Network down or no internet connection");
                }else {
                    dealInfoManagerCallback.onError("Opps something went wrong!");
                }
            }
        });
    }

}
