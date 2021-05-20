package com.islantraveller.Network.basic;

import com.islantraveller.ContactUs.Model.ContactDTO;
import com.islantraveller.Dashboard.Activity.Model.CategoryAll.CategoryAllDTO;
import com.islantraveller.Dashboard.Activity.Model.DealAll.DealAllDTO;
import com.islantraveller.Dashboard.Activity.Model.Locations.LocationsList;
import com.islantraveller.Dashboard.Activity.Model.LogoutDTO;
import com.islantraveller.Dashboard.Activity.Model.SavedEvents.SavedDealsDTO;
import com.islantraveller.Dashboard.Activity.manager.UpdateLatLongDTO;
import com.islantraveller.DealInfo.Model.AddFavouriteDTO;
import com.islantraveller.DealInfo.Model.DealInfoModel.DealInfoDTO;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.SignUp.Model.SignUpDTO;

public class ApiCallback {
    public interface SignUpManagerCallback extends BaseInterface{
        void onSuccessSignUp(SignUpDTO signUpDTO);

    }public interface SocialLoginManagerCallback extends BaseInterface{
        void onSuccessSocialSignUp(LoginDTO loginDTO);

    }

    public interface LoginManagerCallback extends BaseInterface{
        void onSuccessLogin(LoginDTO loginDTO);

    }
    public interface ContactManagerCallback extends BaseInterface{
        void onSuccessLogin(ContactDTO contactDTO);

    }
    public interface ForgotPasswordManagerCallback extends BaseInterface{
        void onSuccessForgotPassword(LogoutDTO logoutDTO);

    }
    public interface LogOutManagerCallback extends BaseInterface{
        void onSuccessLogOut(LogoutDTO logoutDTO);
    }
    public interface CategoryAllManagerCallback extends BaseInterface{
        void onSuccessCategoryAll(CategoryAllDTO categoryAllDTO);
    }

    public interface DealAllManagerCallback extends BaseInterface{
        void onSuccessDealAll(DealAllDTO dealAllDTO);
        void onSuccessUpdateLatLong(UpdateLatLongDTO updateLatLongDTO);
    }


    public interface DealInfoManagerCallback extends BaseInterface{
        void onSuccessDealInfo(DealInfoDTO dealAllDTO);
    }
    public interface AddFavManagerCallback extends BaseInterface{
        void onSuccessAddFav(AddFavouriteDTO addFavouriteDTO);
        void onSuccessDeleteFav(AddFavouriteDTO addFavouriteDTO);
    }
    public interface UserFavouriteListManagerCallback extends BaseInterface{
        void onSuccessUserfavourite(DealAllDTO dealAllDTO);
    }

    public interface UserSaveEventListManagerCallback extends BaseInterface{
        void onSuccessUserSaveEvent(DealAllDTO dealAllDTO);
        void onSuccessLocationsList(LocationsList dealAllDTO);

    }
    public interface SaveEventManagerCallback extends BaseInterface{
        void onSuccessSave(AddFavouriteDTO addFavouriteDTO);
        void onSuccessDelete(AddFavouriteDTO addFavouriteDTO);
    }
}
