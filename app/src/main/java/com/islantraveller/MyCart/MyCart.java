package com.islantraveller.MyCart;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.islantraveller.Payment.ProcessPayment;
import com.islantraveller.R;

public class MyCart extends AppCompatActivity {

    ImageView back_button;
    TextView buy_now;
    Context ct = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);

        buy_now = (TextView) findViewById(R.id.buy_now);
        back_button = (ImageView) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        buy_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(ct, ProcessPayment.class));
            }
        });
    }
}