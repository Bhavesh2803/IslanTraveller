package com.islantraveller.Notification;


import android.util.Log;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;

/**
 * Created by Belal on 5/27/2016.
 */


//Class extending FirebaseInstanceIdService
public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    @Override
    public void onTokenRefresh() {

        try {
            //Getting registration token
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            AppSession.save(getApplicationContext(), Constants.DEVICE_TOKEN, refreshedToken);
            //Displaying token on logcat
            Log.d(TAG, "Refreshed token: " + refreshedToken);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void sendRegistrationToServer(String token) {
        //You can implement this method to store the token on your server
        //Not required for current project
    }
}