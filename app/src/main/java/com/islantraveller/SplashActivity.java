package com.islantraveller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.islantraveller.Dashboard.Activity.MainActivity;
import com.islantraveller.Login.LoginActivity;
import com.islantraveller.database.AppSession;
import com.islantraveller.database.Constants;

public class SplashActivity extends AppCompatActivity {
    Handler handler = new Handler();
    Context ct = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                String token = AppSession.getValue(ct, Constants.ACCESS_TOKEN);

                if (token != null && !token.equalsIgnoreCase(""))
                {
                    startActivity(new Intent(ct, MainActivity.class));

                } else {
                    startActivity(new Intent(ct, LoginActivity.class));
                }

                finish();

            }
        }, 2500);
    }
}