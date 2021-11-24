package com.gac5206.covidawareness.covid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gac5206.covidawareness.R;

import java.util.ArrayList;

public class CovidAdapter extends RecyclerView.Adapter<CovidHolder> {
    private Context mContext;
    private ArrayList<CovidItems> mCovidItemList;

    public CovidAdapter(Context context, ArrayList<CovidItems> covidList){
        mContext = context;
        mCovidItemList = covidList;
    }


    @NonNull
    @Override
    public CovidHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.covid_item, parent, false);
        return new CovidHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CovidHolder holder, int position) {
        CovidItems currentItem = mCovidItemList.get(position);

        String state = currentItem.getMstate();
        String positive = currentItem.getMpositive();
        String negative = currentItem.getMnegative();
        String deaths = currentItem.getMdeaths();

        holder.mState.setText(state);
        holder.mPositive.setText(positive);
        holder.mNegative.setText(negative);
        holder.mDeaths.setText(deaths);


    }


    @Override
    public int getItemCount() {
        return mCovidItemList.size();
    }
}
