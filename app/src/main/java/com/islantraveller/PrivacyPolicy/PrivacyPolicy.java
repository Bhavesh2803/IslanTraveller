package com.islantraveller.PrivacyPolicy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.islantraveller.R;

public class PrivacyPolicy extends AppCompatActivity {

    TextView header_text;
    ImageView back_button;
public static boolean is_from_terms = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        if (is_from_terms)
        {
            header_text = (TextView) findViewById(R.id.header_text);
            header_text.setText("Terms & Conditions");
        }



        back_button = (ImageView) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}