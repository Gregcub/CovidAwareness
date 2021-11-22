package com.gac5206.covidawareness.news.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.gac5206.covidawareness.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class ArticleFragment extends Fragment {
    FloatingActionButton saveNews, closeNews;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_article, container, false);

        saveNews = (FloatingActionButton) view.findViewById(R.id.save_news);
        closeNews = (FloatingActionButton) view.findViewById(R.id.close_news);

        saveNews.setOnClickListener(this::OnClick);
        closeNews.setOnClickListener(this::OnClick);


        return view;
    }

    public void OnClick(View view){
        Fragment fragment;
        switch(view.getId()){
            case R.id.close_news:
                fragment = new NewsHeadlinesFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.news_nav_host_fragment, fragment);
                transaction.commit();
                Toast.makeText(getActivity(), "Closed news!", Toast.LENGTH_LONG).show();
            case R.id.save_news:
                Toast.makeText(getActivity(), "Saved News!", Toast.LENGTH_LONG).show();




        }
    }

}