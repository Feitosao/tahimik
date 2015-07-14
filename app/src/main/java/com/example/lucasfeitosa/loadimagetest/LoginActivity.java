package com.example.lucasfeitosa.loadimagetest;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.facebook.FacebookSdk;
import com.firebase.client.Query;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends Activity {
    private EditText etEmail,etPassword;
    private Button btLogin;
    private TextView registerLink;
    private Firebase ref;
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private String fbAuthToken;
    private String fbUserID;
    private static final String TAG = "FacebookLogin";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://sizzling-heat-8551.firebaseio.com/");
        //facebookLoginButton = (LoginButton)findViewById(R.id.login_button);
        //facebookLoginButton.setReadPermissions("email");
        btLogin = (Button)findViewById(R.id.btLogin);
        callbackManager = CallbackManager.Factory.create();
        /*
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.lucasfeitosa.loadimagetest",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/
        accessTokenTracker = new AccessTokenTracker()
        {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken,AccessToken currentAccessToken){
                fbAuthToken = currentAccessToken.getToken();
                fbUserID = currentAccessToken.getUserId();

                if(fbUserID != null)
                {
                    Log.d(TAG, "User id: " + fbUserID);
                }
                if(fbAuthToken != null)
                {
                    Log.d(TAG, "Access token is: " + fbAuthToken);
                }

            }
        };
        btLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                loginWithFacebbok();
            }
        });

        // Callback registration
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult)
            {
                ref.authWithOAuthToken("facebook", loginResult.getAccessToken().getToken(), new Firebase.AuthResultHandler()
                {
                    @Override
                    public void onAuthenticated(AuthData authData)
                    {
                        // The Facebook user is now authenticated with Firebase
                        final String uid = authData.getUid();
                        final String name = authData.getProviderData().get("displayName").toString();
                        final String email = authData.getProviderData().get("email").toString();
                        /*Query query = ref.child("users").orderByChild("Uid").equalTo(uid);
                        query.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String s)
                            {
                                if(dataSnapshot.getValue() == null)
                                {
                                    Map<String,String> map = new HashMap<String, String>();
                                    map.put("Name",name);
                                    map.put("Email",email);
                                    ref.child("users").child(uid).setValue(map);
                                }
                            }
                            @Override
                            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onChildRemoved(DataSnapshot dataSnapshot) {

                            }

                            @Override
                            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                            }

                            @Override
                            public void onCancelled(FirebaseError firebaseError)
                            {

                            }
                        });*/
                        Map<String,String> map = new HashMap<String, String>();
                        map.put("Name",name);
                        map.put("Email",email);
                        ref.child("users").child(uid).setValue(map);
                        changeActivity(MainActivity.class);
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError)
                    {
                        // there was an error
                    }
                });
            }


            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.d("errouuu", exception.toString());
            }
        });



        //instancing widgets
        /*btLogin = (Button)findViewById(R.id.btLogin);
        registerLink = (TextView)findViewById(R.id.tvLinkToRegister);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                etEmail = (EditText)findViewById(R.id.etEmailLogin);
                etPassword = (EditText)findViewById(R.id.etPasswordLogin);
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if(email != null && password != null)
                {
                    ref.authWithPassword(email,password,new Firebase.AuthResultHandler() {
                        @Override
                        public void onAuthenticated(AuthData authData)
                        {
                            changeActivity(MyActivity.class);
                        }

                        @Override
                        public void onAuthenticationError(FirebaseError firebaseError)
                        {
                            Log.d(this.toString(),"erro de login");
                        }
                    });
                }
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                changeActivity(RegisterActivity.class);
            }
        });
        */
    }
    private void loginWithFacebbok()
    {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "user_friends"));

    }

    private void updateWithToken(AccessToken currentAccessToken)
    {
        if (currentAccessToken != null)
        {

        }
        else
        {

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    public void changeActivity(Class activity)
    {
        Intent i = new Intent(this,activity);
        startActivity(i);
        finish();
    }
}
