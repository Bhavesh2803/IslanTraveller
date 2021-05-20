package com.islantraveller.FaceBookLoginActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.iid.FirebaseInstanceId;
import com.islantraveller.Dashboard.Activity.MainActivity;
import com.islantraveller.FaceBookLoginActivity.manager.SocialLoginManager;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.R;
import com.islantraveller.SignUp.SignUpActivity;
import com.islantraveller.SignUp.SignUpParameter;
import com.islantraveller.SignUp.manager.SignUpManager;
import com.islantraveller.Utils.CommonFunction;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;

import org.json.JSONObject;

import java.util.Arrays;

public class FaceBookLoginActivity<loginButton> extends AppCompatActivity implements ApiCallback.SocialLoginManagerCallback {

    CallbackManager callbackManager = null;
    //LoginButton loginButton = null;


    private RelativeLayout email_layout = null;
    private TextView tv_login = null;
    private EditText edt_email = null;
    private String facebook_id = "";
    private String user_name = "";
    private String login_type = "";
    private String email = "";
    private String first_name = "";
    private String full_name = "";
    private String last_name = "";
    private String account_id = "";


    private Uri image_uri;
    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_face_book_login);


        tv_login = (TextView) findViewById(R.id.tv_login);
        email_layout = (RelativeLayout) findViewById(R.id.email_layout);

        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email.getText().toString();
                if (email.equalsIgnoreCase("") && !CommonFunction.isValidEmail(email)) {
                    Toast.makeText(ct, "Please enter valid email", Toast.LENGTH_SHORT).show();
                } else {
                    email_layout.setVisibility(View.GONE);
                    finish();
                }
            }
        });
        try {
            FacebookSdk.sdkInitialize(getApplicationContext());
            AppEventsLogger.activateApp(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //  loginButton.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration

        LoginButton loginButton = new LoginButton(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                if (loginResult != null) {

                }

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        try {
                            if (loginResult != null) {
                                GraphRequest request = GraphRequest.newMeRequest(
                                        loginResult.getAccessToken(),
                                        new GraphRequest.GraphJSONObjectCallback() {
                                            @Override
                                            public void onCompleted(JSONObject object, GraphResponse response) {

                                                Log.v("SignUpPassword", response.toString());

                                                try {

                                                    if (object.has("email"))
                                                        email = object.getString("email");

                                                    if (object.has("first_name"))
                                                        first_name = object.getString("first_name");

                                                    if (object.has("name"))
                                                        full_name = object.getString("name");


                                                    if (object.has("last_name"))
                                                        last_name = object.getString("last_name");

                                                    if (full_name != null && full_name.contains(" ")) {
                                                        String[] full_name_ar = full_name.split(" ");

                                                        first_name = full_name_ar[0];
                                                        last_name = full_name_ar[1];

                                                    }

                                                    full_name = first_name + " " + last_name;

                                                    if (object.has("id"))
                                                        account_id = object.getString("id");

                                                    Profile profile = Profile.getCurrentProfile();
                                                    String id = profile.getId();
                                                    String link = profile.getLinkUri().toString();
                                                    Log.i("Link", link);
                                                    if (Profile.getCurrentProfile() != null) {
                                                        //     Log.i("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(400, 200));
                                                        image_uri = Profile.getCurrentProfile().getProfilePictureUri(400, 520);
                                                    }

                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                                //LoginManager.getInstance().logOut();
                                                if (image_uri != null)
                                                    //AppSession.save(ct, Constants.PIC1, "" + image_uri);
                                                    if (full_name != null && !full_name.equalsIgnoreCase(""))
                                                        //AppSession.save(ct, Constants.USER_NAME, full_name);

                                                        //AppSession.save(ct, Constants.PIC1, image_uri.toString());


                                                        makeRequestForSignup();

                                                LoginManager.getInstance().logOut();
                                                if (email == null || email.equalsIgnoreCase("")) {
                                                    email_layout.setVisibility(View.VISIBLE);
                                                } else {
                                                    finish();
                                                }

                                            }
                                        });
                                Bundle parameters = new Bundle();
                                parameters.putString("fields", "id,name,email");
                                request.setParameters(parameters);
                                request.executeAsync();


                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                        if (exception != null) {
                            finish();
                        }
                    }
                });

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "public_profile"));

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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



        new SocialLoginManager(FaceBookLoginActivity.this).SocialLogin(signUpParameter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

        super.onActivityResult(requestCode, resultCode, data);
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
