package com.gac5206.covidawareness.user;

public class User {
    public String mUserName, mEmail, mCountry, mState, mCity;

    public User() {
    }

    public User(String name, String email, String country, String state, String city){
        this.mUserName = name;
        this.mEmail = email;
        this.mCountry = country;
        this.mState = state;
        this.mCity = city;

    }

    public String getmUserName() {
        return mUserName;
    }

    public void setmUserName(String mUserName) {
        this.mUserName = mUserName;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmState() {
        return mState;
    }

    public void setmState(String mState) {
        this.mState = mState;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }
}
