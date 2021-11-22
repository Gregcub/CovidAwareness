package com.gac5206.covidawareness;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CovidData {
    public static final String URL = "https://covidtracking.com/api/states?state=";

    Context context;
    String city, pos, neg, death;


    public CovidData(Context context){
        this.context = context;
    }


    public String getCity(String cityID){

        String url = URL+cityID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject cities = null;

                city="";
                try{
                    city = response.getString("positive");
                } catch (JSONException e){
                    e.printStackTrace();
                }

                Toast.makeText(context, "City =" + city, Toast.LENGTH_LONG).show();

            }

        }, error -> {
            Toast.makeText(context, "Ooops!", Toast.LENGTH_LONG).show();

        });
        MySingleton.getInstance(context).addToRequestQueue(request);

        return city;

    }

    public String getPositiveCases(String cityID){

        String url = URL+cityID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject cities = null;

                pos="";
                try{
                    pos = response.getString("positive");
                } catch (JSONException e){
                    e.printStackTrace();
                }

                Toast.makeText(context, "Positive =" + pos, Toast.LENGTH_LONG).show();

            }

        }, error -> {
            Toast.makeText(context, "Ooops!", Toast.LENGTH_LONG).show();

        });
        MySingleton.getInstance(context).addToRequestQueue(request);

        return pos;
    }

    public String getNegativeCases(String cityID){

        String url = URL+cityID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject cities = null;

                neg="";
                try{
                    neg = response.getString("negative");
                } catch (JSONException e){
                    e.printStackTrace();
                }

                Toast.makeText(context, "Negative =" + neg, Toast.LENGTH_LONG).show();

            }

        }, error -> {
            Toast.makeText(context, "Ooops!", Toast.LENGTH_LONG).show();

        });
        MySingleton.getInstance(context).addToRequestQueue(request);

        return neg;
    }

    public String getDeathCases(String cityID){

        String url = URL+cityID;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject cities = null;

                death="";
                try{
                    death = response.getString("death");
                } catch (JSONException e){
                    e.printStackTrace();
                }

                Toast.makeText(context, "Deaths =" + death, Toast.LENGTH_LONG).show();

            }

        }, error -> {
            Toast.makeText(context, "Ooops!", Toast.LENGTH_LONG).show();

        });
        MySingleton.getInstance(context).addToRequestQueue(request);

        return death;
    }



}
