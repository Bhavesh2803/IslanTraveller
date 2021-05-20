package com.islantraveller.SignUp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.islantraveller.FaceBookLoginActivity.FaceBookLoginActivity;
import com.islantraveller.GoogleSignIn.GoogleSignInActivity;
import com.islantraveller.Login.LoginActivity;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.basic.BaseActivity;
import com.islantraveller.R;
import com.islantraveller.SignUp.Model.SignUpDTO;
import com.islantraveller.SignUp.manager.SignUpManager;

public class SignUpActivity extends BaseActivity implements ApiCallback.SignUpManagerCallback {

    private int passwordNotVisible = 1;
    LinearLayout ll_sign_in, ll_google, ll_facebook;
    EditText edt_name, edt_email, edt_password;
    TextView tv_signup;
    ImageView iv_showhide;

    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        ll_sign_in = (LinearLayout) findViewById(R.id.ll_sign_in);
        ll_google = (LinearLayout) findViewById(R.id.ll_google);
        ll_facebook = (LinearLayout) findViewById(R.id.ll_facebook);
        tv_signup = (TextView) findViewById(R.id.tv_signup);
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
        ll_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, LoginActivity.class));
                finish();
            }
        });

        ll_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, GoogleSignInActivity.class));

            }
        });

        ll_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ct, FaceBookLoginActivity.class));
                finish();
            }
        });

        tv_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edt_name.getText().toString();
                String email = edt_email.getText().toString();
                String password = edt_password.getText().toString();

                if (!name.equalsIgnoreCase("")) {
                    if (!email.equalsIgnoreCase("")) {
                        if (!password.equalsIgnoreCase("")) {
                            if (password.length() >= 8) {
                                SignUpParameter signUpParameter = new SignUpParameter();
                                signUpParameter.setName(name);
                                signUpParameter.setEmail(email);
                                signUpParameter.setPassword(password);
                                new SignUpManager(SignUpActivity.this).callSignUpApi(signUpParameter);

                            } else {
                                Toast.makeText(ct, "Password must be grater or equal than 8 character", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ct, "Please enter your password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ct, "Please enter your email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ct, "Please enter your name", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onSuccessSignUp(SignUpDTO signUpDTO) {
        startActivity(new Intent(ct,LoginActivity.class));
        Toast.makeText(ct, ""+signUpDTO.getMessage(), Toast.LENGTH_SHORT).show();
       finishAffinity();
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
