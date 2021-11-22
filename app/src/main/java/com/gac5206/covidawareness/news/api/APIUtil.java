package com.gac5206.covidawareness.news.api;

import com.gac5206.covidawareness.news.api.ApiInterface;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIUtil {

    private static Retrofit retrofit=null;

    public static ApiInterface getApiInterface(){

        if(retrofit ==null){
            retrofit = new Retrofit.Builder().baseUrl(ApiInterface.BASE_URL).addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(ApiInterface.class);

    }

}
