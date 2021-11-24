package com.gac5206.covidawareness;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gac5206.covidawareness.news.activities.NewsActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.ui.PlaceAutocomplete;
import com.google.android.libraries.places.compat.ui.PlaceAutocompleteFragment;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;
import com.google.android.libraries.places.compat.ui.SupportPlaceAutocompleteFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class MapsFragment extends Fragment implements OnMapReadyCallback {


    private FirebaseUser user;
    private DatabaseReference ref;
    public String userID, country, city, state;
    private GoogleMap mGoogleMap;
    private FusedLocationProviderClient client;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private MapView mapView;
    View view;
    Button testCenters, vacCenters;
    private final int REQUEST_LOCATION_PERMISSIONS = 0;
    private final int PERMISSIONS_REQUEST_ENABLE_GPS = 0;
    private final int PERMISSIONS_SERVICES = 0;
    public static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    private boolean Permissions = false;
    double currLat, currLong;
    private String[] placeList, placeName;
    public static final int RADIUS = 15000;
    private MapFragment mapFragment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_maps, container, false);

        testCenters = view.findViewById(R.id.test_centers);
        vacCenters = view.findViewById(R.id.vac_centers);




        //Getting user information for location
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        placeList = new String[]{"covid testing center", "covid vaccination center", "hospital"};
        placeName = new String[]{"Covid Testing Centers", "Covid Vaccination Centers", "Hospitals"};

        client = LocationServices.getFusedLocationProviderClient(requireContext());

        //Initializing map fragment
        mapFragment = (MapFragment) requireActivity().getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Initializing the mapview
        mapView = (MapView) view.findViewById(R.id.map_view);
        if (mapView != null) {
            mapView.onCreate(null);
            mapView.onResume();
            mapView.getMapAsync(this);
        }



        //Checking requirements
        userInformation();
        checkRequirements();
        locationPermission();
        findLocation();
//        autoComplete();




        locationRequest = LocationRequest.create()
                .setInterval(5000)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

//        locationCallback = new LocationCallback() {
//            @Override
//            public void onLocationResult(@NonNull LocationResult locationResult) {
//                for (Location location : locationResult.getLocations()) {
//                    updateMap(location);
//                }
//            }
//        };


        testCenters.setOnClickListener(this::onClick);
        vacCenters.setOnClickListener(this::onClick);



        return view;
    }

//    private void autoComplete() {
//        PlaceAutocompleteFragment autocomplete = (PlaceAutocompleteFragment) requireActivity()
//                .getFragmentManager().findFragmentById(R.id.map_places);
//        autocomplete.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//
//                String url = getUrl(currLat,currLong,"");
//                Object[] DataTransfer = new Object[2];
//                DataTransfer[0] = mGoogleMap;
//                DataTransfer[1] = url;
//                Log.d("onClick", url);
//                GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();
//                getNearbyPlaces.execute(DataTransfer);
//
//            }
//            @Override
//            public void onError(Status status) {
//                Log.e("Error", status.getStatusMessage());
//            }
//
//
//        });
//
//    }

    private String getUrl(double lat, double lng, String nearby) {
        
        StringBuilder sb = new StringBuilder(getString(R.string.nearby_url_api));
        sb.append("location=" + lat + "," + lng);
        sb.append("&radius=" + RADIUS);
        sb.append("&type="+ nearby);
        sb.append("&sensor=true");
        sb.append("&key=" + getResources().getString(R.string.google_maps_key));
        Log.d("getUrl", sb.toString());

        return (sb.toString());

    }



    private void onClick(@NonNull View view) {
        switch(view.getId()){

            case R.id.test_centers:
                getUrl(currLat,currLong,"covid_testing_center");

                break;
            case R.id.vac_centers:
                getUrl(currLat,currLong,"covid vaccination center");
                break;

        }


    }


    //Checking user information
    public void userInformation(){
        ref.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    country = userProfile.mCountry;
                    city = userProfile.mCity;
                    state = userProfile.mState;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getActivity(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }


    //Checking Requirements
    private boolean checkRequirements() {

        if (checkServices()) {
            if (checkGPS()) {
                return true;
            }
        }

        return false;
    }


    //Checking is good play services is installed
    public boolean checkServices() {

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(requireContext());

        if (available == ConnectionResult.SUCCESS) {
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(requireActivity(), available, PERMISSIONS_SERVICES);
            assert dialog != null;
            dialog.show();
        } else {
            Toast.makeText(requireActivity(), "You can not make requests", Toast.LENGTH_LONG).show();
        }


        return false;
    }


    //Checking is GPS is enabled
    public boolean checkGPS() {
        final LocationManager locationManager = (LocationManager) requireContext().getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
            builder.setMessage("This Google maps requires GPS to be enabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent enableGPSIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(enableGPSIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
            return false;
        }

        return true;
    }


    //Getting location permissions
    public boolean locationPermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSIONS);
            return false;
        }
        return true;
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle bundle) {
        super.onSaveInstanceState(bundle);

        Bundle mapBundle = bundle.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapBundle == null) {
            mapBundle = new Bundle();
            bundle.putBundle(MAPVIEW_BUNDLE_KEY, mapBundle);
        }

        mapView.onSaveInstanceState(mapBundle);
    }


    //Finding users location
    @SuppressLint("MissingPermission")
    private void findLocation() {
//        client.getLastLocation()
//                .addOnSuccessListener(requireActivity(), location -> Log.d(TAG, "location=" + location));

        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(requireActivity(),location -> {
            currLat = location.getLatitude();
            currLong = location.getLongitude();
            mapView.getMapAsync(this);

        });

    }


    private void updateMap(@NonNull Location location) {
        LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());

        MarkerOptions myMarker = new MarkerOptions()
                .position(myLatLng);

        mGoogleMap.clear();
        mGoogleMap.addMarker(myMarker);

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(myLatLng, 20);

        mGoogleMap.animateCamera(update);

    }


    @Override
    public void onMapReady(@NonNull GoogleMap map) {

        if (ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map.setMyLocationEnabled(true);

        mGoogleMap = map;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setCompassEnabled(true);
        LatLng myLatLng = new LatLng(currLat, currLong);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(myLatLng, 10);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(myLatLng);
        markerOptions.title("My position");
        map.addMarker(markerOptions);
        mGoogleMap.animateCamera(update);


    }

    @SuppressLint("MissingPermission")
    @Override
    public void onResume() {
        super.onResume();

        if(mGoogleMap != null){
            mGoogleMap.clear();
        }

//        if(locationPermission()) {
//            client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
//        }

        mapView.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    
}