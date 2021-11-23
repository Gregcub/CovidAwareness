package com.gac5206.covidawareness.covid;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gac5206.covidawareness.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class CovidFragment extends Fragment {

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mRecyclerBundle;

    private RecyclerView mRecyclerView;
    private CovidAdapter mCovidAdapter;
    private ArrayList<CovidItems> mCovidItemList;
    private RequestQueue mRequestQueue;
    public static final String statesURL = "https://covidtracking.com/api/states";
    public static final String countriesURL = "https://covidtracking.com/api/us";
    TextView usPos,usNeg,usDeaths;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Parcelable listState = mRecyclerBundle.getParcelable(KEY_RECYCLER_STATE);
            Objects.requireNonNull(mRecyclerView.getLayoutManager()).onRestoreInstanceState(listState);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle saveInstanceState){

        return inflater.inflate(R.layout.fragment_covid, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle saveInstanceState){
        super.onViewCreated(view, saveInstanceState);

        usPos = view.findViewById(R.id.us_positive_cases);
        usNeg = view.findViewById(R.id.us_negative_cases);
        usDeaths = view.findViewById(R.id.us_deaths);


        mCovidItemList = new ArrayList<>();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.covid_recycler);
        mRequestQueue = Volley.newRequestQueue(requireContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        mRecyclerView.setHasFixedSize(true);
        mCovidAdapter = new CovidAdapter(requireContext(),mCovidItemList);
        mRecyclerView.setAdapter(mCovidAdapter);



        parseStatesJSON();
        parseCountriesJSON();


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        mRecyclerBundle = new Bundle();
        Parcelable listState = Objects.requireNonNull(mRecyclerView.getLayoutManager()).onSaveInstanceState();
        mRecyclerBundle.putParcelable(KEY_RECYCLER_STATE, listState);
    }








    private void parseStatesJSON() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, statesURL, null, response -> {
            try {

                for(int i=0; i < response.length(); i++){
                    JSONObject item = response.getJSONObject(i);

                    String state = item.getString("state");
                    String positive = item.getString("positive");
                    String negative = item.getString("negative");
                    String deaths = item.getString("death");


                    mCovidItemList.add(new CovidItems(state,positive,negative,deaths));


                }
                mCovidAdapter = new CovidAdapter(requireActivity(),mCovidItemList);
                DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
                mRecyclerView.addItemDecoration(divider);
                mRecyclerView.setAdapter(mCovidAdapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        mRequestQueue.add(jsonArrayRequest);
    }


    private void parseCountriesJSON() {


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, countriesURL, null, response -> {
            try {

                JSONObject item = response.getJSONObject(0);

                String positive = item.getString("positive");
                String negative = item.getString("negative");
                String deaths = item.getString("death");



                usPos.setText(positive);
                usNeg.setText(negative);
                usDeaths.setText(deaths);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, Throwable::printStackTrace);

        mRequestQueue.add(jsonArrayRequest);
    }



}