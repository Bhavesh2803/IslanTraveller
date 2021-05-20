package com.islantraveller.Network.basic;

import com.islantraveller.ContactUs.ContactParameter;
import com.islantraveller.ContactUs.Model.ContactDTO;
import com.islantraveller.Dashboard.Activity.Model.CategoryAll.CategoryAllDTO;
import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.Dashboard.Activity.Model.Locations.LocationsList;
import com.islantraveller.Dashboard.Activity.Model.LogoutDTO;
import com.islantraveller.Dashboard.Activity.Model.SavedEvents.SavedDealsDTO;
import com.islantraveller.Dashboard.Activity.manager.GetAllDealParameter;
import com.islantraveller.Dashboard.Activity.manager.UpdateLatLongDTO;
import com.islantraveller.Dashboard.Activity.manager.UpdateLatLongParameter;
import com.islantraveller.DealInfo.Model.AddFavouriteDTO;
import com.islantraveller.DealInfo.Model.DealInfoModel.DealInfoDTO;
import com.islantraveller.FaceBookLoginActivity.SocialLoginParameter;
import com.islantraveller.Login.LoginParameter;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.SignUp.Model.SignUpDTO;
import com.islantraveller.SignUp.SignUpParameter;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @POST("Signup")
    Call<SignUpDTO> callRegistrationApi(@Body SignUpParameter signUpParameter);

    @POST("Login")
    Call<LoginDTO> callLoginApi(@Body LoginParameter loginParameter);

    @POST("Contact")
    Call<ContactDTO> callContactApi(@Body ContactParameter loginParameter);

    @POST("ForgotPassword")
    Call<LogoutDTO> callForgotPasswordApi(@Query("email") String email_id);

    @POST("Logout")
    Call<LogoutDTO> callLoginApi(@Header("ACCESS-TOKEN") String token);

    @POST("SocialSignup")
    Call<LoginDTO> callSocialLoginApi(@Body SocialLoginParameter socialLoginParameter);

    @GET("GetCategoryAll")
    Call<CategoryAllDTO> callCategoryAllApi(@Header("ACCESS-TOKEN") String token);

    @POST("GetDealAll")
    Call<DealAllDTO> callDealAllApi(@Header("ACCESS-TOKEN") String token,@Body GetAllDealParameter getAllDealParameter);


    @POST("UpdateUserLatLong")
    Call<UpdateLatLongDTO> callUpdateLatLongApi(@Header("ACCESS-TOKEN") String token, @Body UpdateLatLongParameter updateLatLongParameter);


    @GET("GetUserFavDealList")
    Call<DealAllDTO> callUserFavouriteListApi(@Header("ACCESS-TOKEN") String token);

    @GET("GetUserSaveEventDealList")
    Call<DealAllDTO> callUserSaveEventListApi(@Header("ACCESS-TOKEN") String token);

    @GET("GetLocationAll")
    Call<LocationsList> GetLocationAllApi(@Header("ACCESS-TOKEN") String token);

    @POST("GetDealById")
    Call<DealInfoDTO> callDealInfoApi(@Header("ACCESS-TOKEN") String token, @Query("deal_id") String deal_id);

    @POST("AddUserFavDeal")
    Call<AddFavouriteDTO> callAddFavApi(@Header("ACCESS-TOKEN") String token, @Query("deal_id") String deal_id);

    @POST("DeleteUserFavDeal")
    Call<AddFavouriteDTO> callDeleteFavouriteApi(@Header("ACCESS-TOKEN") String token,@Query("deal_id") String deal_id);

    @POST("DeleteUserSaveEventDeal")
    Call<AddFavouriteDTO> callDeleteUserSaveEventDealApi(@Header("ACCESS-TOKEN") String token,@Query("deal_id") String deal_id);

    @POST("AddUserSaveEventDeal")
    Call<AddFavouriteDTO> callAddSaveEventApi(@Header("ACCESS-TOKEN") String token, @Query("deal_id") String deal_id);
}



