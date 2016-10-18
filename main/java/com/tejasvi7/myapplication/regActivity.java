package com.tejasvi7.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class regActivity extends AppCompatActivity {
    LoginDataBaseAdapter loginDataBaseAdapter;
    public static final String Username = "unameKey";
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //sqlite strts here
        // get Instance  of Database Adapter
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();


    }

    public void submit(View view) {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
       

        EditText username = (EditText) findViewById(R.id.uname1);
        EditText password = (EditText) findViewById(R.id.password1);
        EditText cPassword = (EditText) findViewById(R.id.cPassword);
        EditText email = (EditText) findViewById(R.id.email);
        EditText mNumber = (EditText) findViewById(R.id.mNumber);
        RadioButton male = (RadioButton) findViewById(R.id.radioButton);
        RadioButton female = (RadioButton) findViewById(R.id.radioButton2);

        String userName=username.getText().toString();
        String sPassword=password.getText().toString();
        String confirmPassword=cPassword.getText().toString();
        String semail=email.getText().toString();
        String smNumber=mNumber.getText().toString();
        int gender=0;
        if(female.isChecked())
        gender=2;
        else if(male.isChecked())
        gender=1;
String Gender= ""+gender;
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        pattern = Pattern.compile(EMAIL_PATTERN);

        matcher=pattern.matcher(semail);

        boolean vemail= matcher.matches();

        if (!(confirmPassword.equals(sPassword))) {
            Toast.makeText(getApplicationContext(), "Passwords not matching", Toast.LENGTH_SHORT).show();

        } else if(!(male.isChecked()||female.isChecked())) {
            Toast.makeText(getApplicationContext(), "Enter gender", Toast.LENGTH_SHORT).show();
        }
         else if(mNumber.length()!=10 ){
            Toast.makeText(getApplicationContext(), "Enter a valid mobile number", Toast.LENGTH_SHORT).show();
        }
        else if(!vemail){
            Toast.makeText(getApplicationContext(), "Email is invalid", Toast.LENGTH_SHORT).show();
        }
        else if(username.length()==0){
            Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
        }
        else {
            EditText username1= (EditText) findViewById(R.id.uname1);
            String value  = username1.getText().toString();
            String storedPassword=loginDataBaseAdapter.getSinlgeEntry(value);
              if(storedPassword.equals("NOT EXIST")) {
                  // Save the Data in Database
                  User user=User.getInstance();
                  user.setEmailID(semail);
                  user.setGender(Gender);
                  user.setmNumber(smNumber);
                  user.setPassword(sPassword);
                  user.setUsername(userName);
                  loginDataBaseAdapter.insertEntry(user/*, semail,smNumber, Gender*/);
                  Toast.makeText(getApplicationContext(), "Account Successfully Created ", Toast.LENGTH_LONG).show();
                  SharedPreferences.Editor editor = sharedpreferences.edit();

                  editor.putString(Username, value);
                  editor.commit();
                  Intent activity2 = new Intent(regActivity.this, MainActivity.class);
                  startActivity(activity2);
              }
            else{
                  Toast.makeText(getApplicationContext(), "Username already used", Toast.LENGTH_LONG).show();
              }
        }

    }
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();

        loginDataBaseAdapter.close();
    }
}
