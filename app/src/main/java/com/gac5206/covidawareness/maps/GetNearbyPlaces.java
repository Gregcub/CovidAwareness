package com.gac5206.covidawareness.maps;

import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    String placesData;
    GoogleMap mGoogleMap;
    String url;

    @Override
    protected String doInBackground(Object... objects) {

        try {
            mGoogleMap = (GoogleMap) objects[0];
            url = (String) objects[1];
            DownloadUrl downloadUrl = new DownloadUrl();
            placesData = downloadUrl.readUrl(url);

        }catch(Exception e){
            Log.d("GooglePlacesReadTask", e.toString());
        }

        return placesData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaces = null;
        JsonMapParser parser = new JsonMapParser();
        nearbyPlaces = parser.parseResult(s);
        ShowNearbyPlaces(nearbyPlaces);

    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlaces) {

        for(int i=0; i < nearbyPlaces.size(); i++){
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlaces.get(i);
            double lat = Double.parseDouble(Objects.requireNonNull(googlePlace.get("lat")));
            double lng = Double.parseDouble(Objects.requireNonNull(googlePlace.get("lng")));
            String name = googlePlace.get("name");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat,lng);
            markerOptions.position(latLng);
            markerOptions.title(name + " : " + vicinity);
            mGoogleMap.addMarker(markerOptions);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(12));

        }

    }
}
