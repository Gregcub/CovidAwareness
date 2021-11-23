package com.gac5206.covidawareness.covid;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gac5206.covidawareness.R;

public class CovidHolder extends RecyclerView.ViewHolder {

    public TextView mState, mPositive, mNegative, mDeaths;

    public CovidHolder(@NonNull View itemView) {
        super(itemView);
        mState = itemView.findViewById(R.id.state);
        mPositive = itemView.findViewById(R.id.positive_cases);
        mNegative = itemView.findViewById(R.id.negative_cases);
        mDeaths = itemView.findViewById(R.id.deaths);

    }
}
