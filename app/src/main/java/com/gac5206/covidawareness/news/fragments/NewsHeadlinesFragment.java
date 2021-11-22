package com.gac5206.covidawareness.news.fragments;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gac5206.covidawareness.news.api.APIUtil;
import com.gac5206.covidawareness.MainNews;
import com.gac5206.covidawareness.news.api.NewsAPIModel;
import com.gac5206.covidawareness.news.api.NewsApiAdapter;
import com.gac5206.covidawareness.R;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NewsHeadlinesFragment extends Fragment {

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private static Bundle mRecyclerBundle;

    ProgressBar progressBar;
    ArrayList<NewsAPIModel> mNewsArrayList;
    NewsApiAdapter adapter;
    String country ="us";
    private RecyclerView recyclerView;
    String newsAPIKey = "a39850e22d1d41d3afe4ee2f40b5aa4b";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            Parcelable listState = mRecyclerBundle.getParcelable(KEY_RECYCLER_STATE);
            Objects.requireNonNull(recyclerView.getLayoutManager()).onRestoreInstanceState(listState);
        }


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_headlines, container, false);

        
        recyclerView = view.findViewById(R.id.news_recycler);
        mNewsArrayList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration divider = new DividerItemDecoration(requireActivity(), DividerItemDecoration.VERTICAL);
        adapter = new NewsApiAdapter(getContext(), mNewsArrayList);
        recyclerView.addItemDecoration(divider);
        recyclerView.setAdapter(adapter);
        progressBar = view.findViewById(R.id.news_loading);
        progressBar.setVisibility(View.VISIBLE);

        findNews();





        

        return view;
    }

    private void findNews() {
        APIUtil.getApiInterface().getNews(country, 100, newsAPIKey).enqueue(new Callback<MainNews>() {

            @Override
            public void onResponse(Call<MainNews> call, Response<MainNews> response) {

                if(response.isSuccessful()){
                    mNewsArrayList.addAll(response.body().getArticles());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }

            }
            @Override
            public void onFailure(Call<MainNews> call, Throwable t) {
                Toast.makeText(requireActivity(), "Error!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }



    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        mRecyclerBundle = new Bundle();
        Parcelable listState = Objects.requireNonNull(recyclerView.getLayoutManager()).onSaveInstanceState();
        mRecyclerBundle.putParcelable(KEY_RECYCLER_STATE, listState);
    }
    


}