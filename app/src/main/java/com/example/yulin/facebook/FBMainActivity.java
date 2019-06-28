package com.example.yulin.facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yulin.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class FBMainActivity extends AppCompatActivity {

    private static final String TAG = FBMainActivity.class.getSimpleName();

    public CallbackManager mCallbackManager;
    private TextView mFbID, mFbName, mFbLoginInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        mFbID = (TextView) findViewById(R.id.textId);
        mFbName = (TextView) findViewById(R.id.textName);
        mFbLoginInfo = (TextView) findViewById(R.id.textLoginInfo);

        Button loginButton = (Button) findViewById(R.id.facebook_custom_login_btn);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Call private method
                onFbLogin();
            }
        });

        Button logoutButton = (Button) findViewById(R.id.facebook_custom_logout_btn);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFbLogout();
            }
        });

        mCallbackManager = CallbackManager.Factory.create();

        LoginButton fbLoginBtn = (LoginButton) findViewById(R.id.facebook_login_btn);
        fbLoginBtn.setReadPermissions(Arrays.asList("email", "user_photos", "public_profile"));
        fbLoginBtn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "onSuccess:" + loginResult.getAccessToken().toString());
                Log.i(TAG, "Token:" + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "OnCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: error=" + error);
            }
        });

        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.i(TAG, "onSuccess:" + loginResult.getAccessToken().toString());
                Log.i(TAG, "Token:" + loginResult.getAccessToken().getToken());
                Log.i(TAG, "Expires:" + loginResult.getAccessToken().getExpires());

                GraphRequest.newMeRequest(
                        loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject json, GraphResponse response) {
                                if (response.getError() != null) {
                                    // handle error
                                    Log.d(TAG, "ERROR");
                                } else {
                                    Log.d(TAG, "Success");
                                    try {
                                        String jsonresult = String.valueOf(json);
                                        Log.d(TAG, "JSON Result" + jsonresult);
                                        String str_id = json.getString("id");
                                        String str_name = json.getString("name");
                                        mFbID.setText("str_id = " + str_id);
                                        mFbName.setText("str_name = " + str_name);
                                        Log.d(TAG, "str_id = " + str_id);
                                        Log.d(TAG, "str_name = " + str_name);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "On cancel");
                mFbLoginInfo.setText("OnCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, error.toString());
                mFbLoginInfo.setText("onError->errorMessage : " + error.toString());
            }
        });
    }

    private void onFbLogout() {
        LoginManager.getInstance().logOut();
        mFbID.setText("str_id = ");
        mFbName.setText("str_name = ");
    }

    private void onFbLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
