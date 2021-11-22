package com.gac5206.covidawareness.news.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gac5206.covidawareness.R;
import com.gac5206.covidawareness.news.recycler.NewsAdapter;
import com.gac5206.covidawareness.news.room.NewsViewModel;
import com.gac5206.covidawareness.news.room.News;

import java.util.ArrayList;
import java.util.List;


public class SavedNewsFragment extends Fragment{


    private NewsViewModel newsViewModel;
    RecyclerView recyclerView;
    NewsAdapter newsAdapter;
    ArrayList<News> news;

    TextView mTitle, mPublished, mDescription;
    ImageView mImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_saved_news, container, false);

        recyclerView = view.findViewById(R.id.saved_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        newsAdapter = new NewsAdapter();
        recyclerView.setAdapter(newsAdapter);


        mTitle = view.findViewById(R.id.title);
        mDescription = view.findViewById(R.id.description);
        mPublished = view.findViewById(R.id.published);
        mImageView = view.findViewById(R.id.article_img);


//        if (getIntent().hasExtra("url")){
//
//            String urls = intent.getStringExtra("url");
////            Toast.makeText(getActivity(), urls, Toast.LENGTH_SHORT).show();
//            mTitle.setText(intent.getStringExtra("title"));
//            mDescription.setText(intent.getStringExtra("description"));
//            mPublished.setText(intent.getStringExtra("published"));
//            Glide.with(this)
//                    .load(intent.getStringExtra("image"))
//                    .into(mImageView);






        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.getNews().observe(requireActivity(), new Observer<List<News>>(){

            @Override
            public void onChanged(List<News> news) {


                //update recyclerview
                newsAdapter.setNews(news);
//                Toast.makeText(requireContext(), "OnChanged", Toast.LENGTH_LONG).show();
            }
        });



        return view;
    }


}