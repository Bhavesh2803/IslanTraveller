package com.islantraveller.ForgotPassword;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.islantraveller.Dashboard.Activity.Model.LogoutDTO;
import com.islantraveller.ForgotPassword.manager.ForgotPasswordManager;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.Network.basic.BaseActivity;
import com.islantraveller.R;
import com.islantraveller.Utils.CommonFunction;
import com.islantraveller.database.Constants;

public class ForgotPasswordActivity extends BaseActivity implements ApiCallback.ForgotPasswordManagerCallback {
    EditText edt_email;
    TextView tv_continue;



    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        edt_email = (EditText) findViewById(R.id.edt_email);
        tv_continue = (TextView) findViewById(R.id.tv_continue);

        tv_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edt_email.getText().toString();

                if (!email.equalsIgnoreCase("")) {
                    if (CommonFunction.isValidEmail(email)) {
                        new ForgotPasswordManager(ForgotPasswordActivity.this).callForgotPasswordApi(email);
                    } else {
                        Toast.makeText(ct, "Please enter Valid email", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(ct, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });



      //  randomlySetGotoBtnIndex();

    }

    @Override
    public void onSuccessForgotPassword(LogoutDTO logoutDTO) {

        finish();
        Toast.makeText(ct, "" + logoutDTO.getMessage(), Toast.LENGTH_SHORT).show();
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