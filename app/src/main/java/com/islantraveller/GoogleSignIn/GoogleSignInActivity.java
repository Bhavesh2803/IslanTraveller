package com.islantraveller.GoogleSignIn;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.islantraveller.Dashboard.Activity.MainActivity;
import com.islantraveller.FaceBookLoginActivity.FaceBookLoginActivity;
import com.islantraveller.FaceBookLoginActivity.SocialLoginParameter;
import com.islantraveller.FaceBookLoginActivity.manager.SocialLoginManager;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.R;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;

public class GoogleSignInActivity extends AppCompatActivity implements ApiCallback.SocialLoginManagerCallback  {

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 87;
    //CallbackManager callbackManager = null;
    Context ct = this;
    String email = "", phone = "", first_name = "", last_name = "", full_name = "", account_id = "";
    String password = "gmaxgps";
    String google_id = "";
    String user_name = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_sign_in);

       try
       {
           GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                   .requestEmail()
                   .requestIdToken(getResources().getString(R.string.google_client_id))
                   .build();

           mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

           // callbackManager = CallbackManager.Factory.create();

           if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
               setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
           }

           signIn();

       }
       catch (Exception e)
       {
           e.printStackTrace();

       }    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {

            //  callbackManager.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("LoginOption", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
Uri image_uri;
    private void updateUI(GoogleSignInAccount account) {
        try {
            if (account != null) {
                full_name = account.getDisplayName();

                email = account.getEmail();
                account_id = account.getId();

                image_uri = account.getPhotoUrl();
                if (full_name != null && full_name.contains(" ")) {
                    String[] full_name_ar = full_name.split(" ");

                    first_name = full_name_ar[0];
                    last_name = full_name_ar[1];
                    AppSession.save(ct, Constants.FIRST_NAME, full_name_ar[0]);
                    AppSession.save(ct, Constants.LAST_NAME, full_name_ar[1]);
                   // Toast.makeText(ct, "First Name: "+first_name+"\n"+"Last Name: "+last_name+"\nEmailId: "+email, Toast.LENGTH_LONG).show();
                }

                makeRequestForSignup();

               /* AppSession.save(ct, Constants.USER_NAME, full_name);
                AppSession.save(ct, Constants.EMAIL, email);
                AppSession.save(ct, Constants.GOOGLE_ID, account_id);
                AppSession.save(ct, Constants.USER_TOKEN, account_id);*/

            //    new GoogleAsync().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                mGoogleSignInClient.signOut();


              /* assessment =  AppSession.getValue(ct,Constants.ASSESSMENT);
                if(assessment.equalsIgnoreCase("false")){
                    startActivity(new Intent(GoogleSignInActivity.this, QueAnsMainActivity.class));
                    finish();
                }
                else

                    startActivity(new Intent(GoogleSignInActivity.this, HomeScreenActivity.class));
                finish();

*/
            }
        } catch (Exception e) {
            e.printStackTrace();


        }
    }



    private void makeRequestForSignup() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        SocialLoginParameter signUpParameter = new SocialLoginParameter();
        signUpParameter.setName(full_name);
        signUpParameter.setEmail(email);
        signUpParameter.setFcm_token(refreshedToken);
        signUpParameter.setSocial_profile("" + image_uri);
        signUpParameter.setLogin_type("FACEBOOK");
        signUpParameter.setSocial_id(""+account_id);
        signUpParameter.setDevice_type("1");
        new SocialLoginManager(GoogleSignInActivity.this).SocialLogin(signUpParameter);
    }

    @Override
    public void onSuccessSocialSignUp(LoginDTO loginDTO) {
        try {
            AppSession.save(ct, Constants.ACCESS_TOKEN, loginDTO.getData().getAccessToken());
            AppSession.save(ct, Constants.USER_NAME, full_name);
            AppSession.save(ct, Constants.EMAIL, email);
            AppSession.save(ct, Constants.IMAGE_URL, ""+image_uri);
            startActivity(new Intent(ct, MainActivity.class));
            finishAffinity();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(String errorMessage) {

    }

    @Override
    public void onShowLoader() {

    }

    @Override
    public void onHideLoader() {

    }
}
