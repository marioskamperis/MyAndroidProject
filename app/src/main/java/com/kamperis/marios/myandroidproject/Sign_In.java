package com.kamperis.marios.myandroidproject;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

public class Sign_In extends AppCompatActivity {

    private String TAG = "Login Class";
    private LoginButton loginButton;
    CallbackManager callbackManager;
    TextView txtArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_sign__in);


        loginButton = (LoginButton) findViewById(R.id.login_button);
        List<String> permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile");
        loginButton.setReadPermissions(permissionNeeds);
        txtArea = (TextView) findViewById(R.id.tvarea);
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,"SuCCESS");
                txtArea.setText(
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken()
                );

                final GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                             if(object==null){
                                 Log.d(TAG,"is null");

                             }else{
                                 Log.d(TAG,String.valueOf(object));
                             }

                                // Application code
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,age_range,about,birthday,address");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {
                Log.d(TAG,"Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG,"Error");
            }
        });

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.kamperis.marios.myandroidproject", PackageManager.GET_SIGNATURES);
//            for (android.content.pm.Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
//                Log.d(TAG, sign);
//                //  Toast.makeText(getApplicationContext(),sign,     Toast.LENGTH_LONG).show();
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//        } catch (NoSuchAlgorithmException e) {
//        }
//        setContentView(R.layout.activity_sign__in);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        loginButton = (LoginButton) findViewById(R.id.login_button);
//        txtArea = (TextView) findViewById(R.id.tvarea);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//
//        callbackManager = CallbackManager.Factory.create();
//
//        //defining login button
//        loginButton.getLoginBehavior();
//        //Request additional permission
//        List<String> permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile");
//        loginButton.setReadPermissions(permissionNeeds);
//        // Callback registration
//        Log.d(TAG, "Success Login Before TOKEN");
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "Success Login ACCESS TOKEN");
//                // accessToken = loginResult.getAccessToken();
//
//
//                final GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(
//                                    JSONObject object,
//                                    GraphResponse response) {
//                                try {
//
//                                    txtArea.append("\nFACEBOOK CONNECTION:\n\n" + "Name: " + String.valueOf(object.getString("name")) + "" +
//                                            "\nAgeRange :" + String.valueOf(object.getString("age_range")) +
//                                            "\nBirthday :" + String.valueOf(object.getString("birthday")) + "\n");
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                                // Application code
//                            }
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,age_range,about,birthday,address");
//                request.setParameters(parameters);
//                request.executeAsync();
//
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "Cancel Login ACCESS TOKEN");
//                // App code
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                Log.d(TAG, "Error Login ACCESS TOKEN");
//                // App code
//            }
//        });
//        Log.d(TAG, "Success Login ACCESS TOKEN after");
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign__in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }
}
