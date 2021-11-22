package com.gac5206.covidawareness;

public class User {
    public String mUserName, mEmail, mCountry, mState, mCity;

    public User(){

    }

    public User(String name, String email, String country, String state, String city){
        this.mUserName = name;
        this.mEmail = email;
        this.mCountry = country;
        this.mState = state;
        this.mCity = city;

    }

}
