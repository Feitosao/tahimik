package com.example.lucasfeitosa.loadimagetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends Activity
{
    private EditText etName,etEmail,etPassword;
    private Button btRegister;
    private TextView loginLink;
    private Firebase ref;
    //private final String refURL = "https://sizzling-heat-8551.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Firebase config
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://sizzling-heat-8551.firebaseio.com/");

        //Instancing widgets
        btRegister = (Button)findViewById(R.id.btRegister);
        loginLink = (TextView)findViewById(R.id.tvLinkToRLogin);

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                //Instancing widgets
                etName = (EditText)findViewById(R.id.etFullNameRegister);
                etEmail = (EditText)findViewById(R.id.etEmailRegister);
                etPassword = (EditText)findViewById(R.id.etPasswordRegister);
                String name = etName.getText().toString();
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(name != null && email != null && password != null)
                {
                    ref.createUser(etEmail.getText().toString(),etPassword.getText().toString(),new Firebase.ValueResultHandler<Map<String, Object>>(){
                        @Override
                        public void onSuccess(Map<String,Object> result)
                        {
                            Map<String,String> map = new HashMap<String, String>();
                            map.put("Name",etName.getText().toString());
                            //map.put("Email")

                            ref.child("users").child(result.get("uid").toString()).setValue(map);
                            changeActivity(LoginActivity.class);
                        }
                        @Override
                        public void onError(FirebaseError error)
                        {
                            switch (error.getCode()) {
                                case FirebaseError.USER_DOES_NOT_EXIST:
                                    // handle a non existing user
                                    Log.d(this.toString(),"user do not exist");
                                    break;
                                case FirebaseError.INVALID_PASSWORD:
                                    // handle an invalid password
                                    Log.d(this.toString(),"invalid password");
                                    break;
                                default:
                                    // handle other errors
                                    Log.d(this.toString(),"login error");


                            }
                        }
                    });
                }

            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               changeActivity(LoginActivity.class);
            }
        });
    }
    public void changeActivity(Class activity)
    {
        Intent i = new Intent(this,activity);
        startActivity(i);
        finish();
    }
}
