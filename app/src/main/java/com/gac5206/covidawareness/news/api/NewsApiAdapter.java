package com.gac5206.covidawareness.news.api;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gac5206.covidawareness.R;
import com.gac5206.covidawareness.news.activities.WebActivity;

import java.util.ArrayList;

public class NewsApiAdapter extends RecyclerView.Adapter<NewsApiAdapter.ViewHolder> {

    Context context;
    ArrayList<NewsAPIModel> newsArrayList;

    public NewsApiAdapter(Context context, ArrayList<NewsAPIModel> newsArrayList) {
        this.context = context;
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public NewsApiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item,null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsApiAdapter.ViewHolder holder, int position) {

        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, WebActivity.class);
            intent.putExtra("url", newsArrayList.get(position).getUrl());
            intent.putExtra("author", newsArrayList.get(position).getAuthor());
            intent.putExtra("title", newsArrayList.get(position).getTitle());
            intent.putExtra("description", newsArrayList.get(position).getDescription());
            intent.putExtra("published", newsArrayList.get(position).getPublishedAt());
            intent.putExtra("image", newsArrayList.get(position).getUrlToImage());

            context.startActivity(intent);
        });


        holder.mPublished.setText("Published: "+newsArrayList.get(position).getPublishedAt());
        holder.mAuthor.setText(newsArrayList.get(position).getAuthor());
        holder.mTitle.setText(newsArrayList.get(position).getTitle());
        holder.mDescription.setText(newsArrayList.get(position).getDescription());
        Glide.with(context).load(newsArrayList.get(position).getUrlToImage()).into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return newsArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mSource, mAuthor, mPublished, mTitle, mDescription;
        CardView cardView;
        ImageView mImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

//            mSource = itemView.findViewById(R.id.source);
            mAuthor = itemView.findViewById(R.id.author);
            mPublished = itemView.findViewById(R.id.published);
            mTitle = itemView.findViewById(R.id.title);
            mDescription = itemView.findViewById(R.id.description);
            cardView = itemView.findViewById(R.id.news_card);
            mImageView = itemView.findViewById(R.id.article_img);



        }
    }
}
