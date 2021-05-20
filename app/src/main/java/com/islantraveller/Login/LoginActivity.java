package com.islantraveller.Login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.firebase.iid.FirebaseInstanceId;
import com.islantraveller.Dashboard.Activity.MainActivity;
import com.islantraveller.FaceBookLoginActivity.FaceBookLoginActivity;
import com.islantraveller.ForgotPassword.ForgotPasswordActivity;
import com.islantraveller.GoogleSignIn.GoogleSignInActivity;
import com.islantraveller.Login.Model.LoginDTO;
import com.islantraveller.Login.manager.LoginManager;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.basic.BaseActivity;
import com.islantraveller.R;
import com.islantraveller.SignUp.SignUpActivity;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends BaseActivity implements ApiCallback.LoginManagerCallback {

    private int passwordNotVisible = 1;
    TextView tv_sign_in, tv_forgot_password;
    EditText edt_email, edt_password;
    ImageView iv_showhide;
    LinearLayout ll_sign_up, ll_google, ll_facebook;
    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        tv_sign_in = (TextView) findViewById(R.id.tv_sign_in);
        tv_forgot_password = (TextView) findViewById(R.id.tv_forgot_password);
        ll_sign_up = (LinearLayout) findViewById(R.id.ll_sign_up);
        ll_google = (LinearLayout) findViewById(R.id.ll_google);
        ll_facebook = (LinearLayout) findViewById(R.id.ll_facebook);
        iv_showhide = (ImageView) findViewById(R.id.iv_showhide);

        iv_showhide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordNotVisible == 1) {
                    edt_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    iv_showhide.setColorFilter(ContextCompat.getColor(ct, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);
                    passwordNotVisible = 0;
                } else {
                    iv_showhide.setColorFilter(ContextCompat.getColor(ct, R.color.grey_text), android.graphics.PorterDuff.Mode.SRC_IN);
                    edt_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordNotVisible = 1;

                }
            }
        });
        ll_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, SignUpActivity.class));
                finish();
            }
        });

        tv_forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, ForgotPasswordActivity.class));
            }
        });
        tv_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();

                if (!email.equalsIgnoreCase("")) {
                    if (!password.equalsIgnoreCase("")) {
                        if (password.length() >= 8) {
                            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                            LoginParameter signUpParameter = new LoginParameter();
                            signUpParameter.setEmail(email);
                            signUpParameter.setPassword(password);
                            signUpParameter.setFcm_token(refreshedToken);
                            signUpParameter.setDevice_type("1");
                            signUpParameter.setLogin_type("NORMAL");
                            signUpParameter.setName("");
                            signUpParameter.setFb_id("");
                            signUpParameter.setFb_profile("");
                            new LoginManager(LoginActivity.this).callLoginApi(signUpParameter);
                        } else {
                            Toast.makeText(ct, "Password must be grater or equal than 8 character", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ct, "Please enter your password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ct, "Please enter your email", Toast.LENGTH_SHORT).show();
                }


            }
        });

        ll_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, GoogleSignInActivity.class));
                //finish();
            }
        });

        ll_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, FaceBookLoginActivity.class));

            }
        });

        printHashKey();
    }

    private void printHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.islantraveller",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("keyhash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.d("keyhash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                Log.v("keyhash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

    }

    @Override
    public void onSuccessLogin(LoginDTO loginDTO) {
        if (loginDTO.getData() != null) {
            AppSession.save(ct, Constants.FIRST_NAME, loginDTO.getData().getName());
            AppSession.save(ct, Constants.EMAIL, loginDTO.getData().getEmail());
            AppSession.save(ct, Constants.ACCESS_TOKEN, loginDTO.getData().getAccessToken());
            startActivity(new Intent(ct, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onError(String errorMessage) {
        Toast.makeText(ct, "" + errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onShowLoader() {
        showLoader();
    }

    @Override
    public void onHideLoader() {
        hideLoader();
    }
}
