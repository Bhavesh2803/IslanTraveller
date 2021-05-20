package com.islantraveller.Payment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.islantraveller.R;

public class ProcessPayment extends AppCompatActivity {

    ImageView back_button;
    RelativeLayout payment_popup;
    TextView make_payment;
    TextView done_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_payment);

        payment_popup = (RelativeLayout) findViewById(R.id.payment_popup);
        make_payment = (TextView) findViewById(R.id.make_payment);
        done_button = (TextView) findViewById(R.id.done_button);
        back_button = (ImageView) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        make_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_popup.setVisibility(View.VISIBLE);
            }
        });
        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment_popup.setVisibility(View.GONE);
            }
        });
    }
}