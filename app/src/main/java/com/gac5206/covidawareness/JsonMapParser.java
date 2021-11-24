package com.gac5206.covidawareness;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonMapParser {

    private HashMap<String,String> parseJSONObject(JSONObject obj){

        HashMap<String,String> googlePlaceMap = new HashMap<String, String>();

        String name = "-NA-";
        String vicinity = "-NA-";
        String lat = "";
        String lng = "";

        Log.d("getPlace", "Entered");

        try {
            if(!obj.isNull("name")){
                name = obj.getString("name");
            }
            if(!obj.isNull("vicinity")){
                name = obj.getString("vicinity");
            }


//            name = obj.getString("name");
            lat = obj.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");
            lng = obj.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");
            googlePlaceMap.put("name", name);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", lat);
            googlePlaceMap.put("lng",lng);

            Log.d("getPlace", "Putting Places");

        } catch (JSONException e) {
            Log.d("getPlace", "Error");
            e.printStackTrace();
        }

        return googlePlaceMap;
    }

    private List<HashMap<String,String>> parseJsonArray(JSONArray jsonArray){

        List<HashMap<String,String>> placesList = new ArrayList<>();
        HashMap<String,String> data = null;
        Log.d("Places", "getPlaces");

        for (int i = 0; i < jsonArray.length(); i++){

            try {

                data =  parseJSONObject((JSONObject) jsonArray.get(i));
                placesList.add(data);
                Log.d("Places", "Adding places");

            } catch (JSONException e) {
                Log.d("Places", "Error in Adding places");
                e.printStackTrace();
            }
        }

        return placesList;
    }

    public List<HashMap<String,String>> parseResult(String obj){
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            Log.d("Places", "parse");
            jsonObject = new JSONObject(obj);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            Log.d("Places", "parse error");
            e.printStackTrace();
        }

        assert jsonArray != null;
        return parseJsonArray(jsonArray);
    }

}
