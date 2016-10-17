package com.tejasvi7.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    private LoginButton floginButton;
    private CallbackManager callbackManager;

    LoginDataBaseAdapter loginDataBaseAdapter;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_main);

        // create a instance of SQLite Database
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
// shared preferences retrieval
        sharedpreferences = getSharedPreferences(regActivity.MyPREFERENCES, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedpreferences.edit();
        String usrname= sharedpreferences.getString(regActivity.Username, "null");
        EditText username = (EditText) findViewById(R.id.uname);
        username.setText(usrname, TextView.BufferType.EDITABLE);
        editor.apply();
/* facebook login code */
        floginButton = (LoginButton) findViewById(R.id.fblogin_button);

        floginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));


        // Facebook Callback registration
        floginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v("LoginActivity", response.toString());

                                // Application code
                                try {
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    String gender = object.getString("gender");
                                    String birthday = object.getString("birthday");
                                    //do something with the data here
                                    Log.v("Facebook Logged In: ", "id: " + id + "\nname: " + name + "\nemail: " + email + "\nGender: " + gender + "\nBirthhday: " + birthday);
                                } catch (JSONException e) {
                                    e.printStackTrace(); //something's seriously wrong here
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();


            }

            @Override
            public void onCancel() {
                // App code
                Log.v("LoginActivity", "cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.v("LoginActivity", exception.getCause().toString());
            }

        });

    }
        public void login(View view){

            EditText username = (EditText) findViewById(R.id.uname);
            EditText password = (EditText) findViewById(R.id.password);
            // get The User name and Password
            String userName=username.getText().toString();
            String spassword=password.getText().toString();
            String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);

            // fetch the Password form database for respective user name
           // String storedPassword=loginDataBaseAdapter.getSinlgeEntry(userName);

            // check if the Stored password matches with  Password entered by user
            if(username.length()==0 ||password.length()==0 ) {
                Toast.makeText(getApplicationContext(), "Enter login credentials", Toast.LENGTH_SHORT).show();
            }
            else if(storedPassword.equals("NOT EXIST"))
            {
                Toast.makeText(MainActivity.this, "Account doesn't exist", Toast.LENGTH_LONG).show();

            }
            else if(spassword.equals(storedPassword))
            {
                LoginDataBaseAdapter.setUser(userName);

                Toast.makeText(MainActivity.this, "Congrats: Login Successfull", Toast.LENGTH_LONG).show();
                Intent activity = new Intent(MainActivity.this, Logpage.class);
                startActivity(activity);

            }
            else
            {
                Toast.makeText(MainActivity.this, "User Name or Password does not match", Toast.LENGTH_LONG).show();
            }


        }

        public void Register(View view) {


            Intent activity1 = new Intent(MainActivity.this, regActivity.class);
            startActivity(activity1);


        }



    }




