package com.tejasvi7.myapplication;

/**
 * Created by tejasvi7 on 10/15/2016.
 */

public class User {
    private static User instance;

    private User() {}

    public static User getInstance() {
        if (instance == null) {
            instance = new User();
        }
        return instance;
    }

    private String username;
    private String password;
    private String emailID;
    private String mNumber;
    private String gender;



    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getmNumber() {
        return mNumber;
    }
    public void setmNumber(String mNumber) {
        this.mNumber = mNumber;
    }
    public String getEmailID() {
        return emailID;
    }
    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

}
