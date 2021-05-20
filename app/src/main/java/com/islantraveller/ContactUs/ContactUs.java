package com.islantraveller.ContactUs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.islantraveller.ContactUs.Model.ContactDTO;
import com.islantraveller.ContactUs.manager.ContactUsManager;
import com.islantraveller.Login.LoginActivity;
import com.islantraveller.Login.LoginParameter;
import com.islantraveller.Login.manager.LoginManager;
import com.islantraveller.Network.basic.ApiCallback;
import com.islantraveller.R;
import com.islantraveller.database.Constants;

public class ContactUs extends AppCompatActivity implements ApiCallback.ContactManagerCallback {

    ImageView back_button;
    public static String contact_title = "false";
    TextView title;
    TextView submit;
    EditText edt_name;
    EditText edt_email;
    EditText edt_contact_num;
    EditText edt_message;

    String name, email, contact_num, message;

    Context ct = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        back_button = (ImageView) findViewById(R.id.back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title = (TextView) findViewById(R.id.title);
        submit = (TextView) findViewById(R.id.submit);
        title.setText(contact_title);

        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_message = (EditText) findViewById(R.id.edt_message);
        edt_contact_num = (EditText) findViewById(R.id.edt_contact_num);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }

    private void validate()
    {
        name = edt_name.getText().toString();
        contact_num = edt_contact_num.getText().toString();
        message = edt_message.getText().toString();
        email = edt_email.getText().toString();

        if (name == null || name.equalsIgnoreCase("")) {
            Toast.makeText(ct, "Please enter your name", Toast.LENGTH_SHORT).show();
            return;
        } else if (email == null || email.equalsIgnoreCase("")) {
            Toast.makeText(ct, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        } else if (contact_num == null || contact_num.equalsIgnoreCase("")) {
            Toast.makeText(ct, "Please enter your contact number", Toast.LENGTH_SHORT).show();
            return;
        } else if (message == null || message.equalsIgnoreCase("")) {
            Toast.makeText(ct, "Please type your message", Toast.LENGTH_SHORT).show();
            return;
        } else {
            ContactParameter contactParameter = new ContactParameter();
            contactParameter.setEmail(email);
            contactParameter.setName(name);
            contactParameter.setMessage(message);
            contactParameter.setSubject("Contact Us");
            contactParameter.setContact_num(contact_num);
            new ContactUsManager(ContactUs.this).callContactUsApi(contactParameter);

        }
    }

    @Override
    public void onSuccessLogin(ContactDTO contactDTO)
    {
       try {

           if (contactDTO != null && contactDTO.getStatus().equalsIgnoreCase("success")) {
               Toast.makeText(ct,contactDTO.getMessage(),Toast.LENGTH_SHORT).show();
               finish();
           }else
           {
               Toast.makeText(ct,contactDTO.getMessage(),Toast.LENGTH_SHORT).show();
           }
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