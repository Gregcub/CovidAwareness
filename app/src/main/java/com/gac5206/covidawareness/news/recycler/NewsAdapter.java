package com.gac5206.covidawareness.news.recycler;

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
import com.gac5206.covidawareness.news.activities.WebActivity2;
import com.gac5206.covidawareness.news.room.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private Context mContext;
    private List<News> news = new ArrayList<>();


    public NewsAdapter(Context context){
        this.mContext = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_item,parent,false);

        return new NewsHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        News currentNews = news.get(position);


        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, WebActivity2.class);
            intent.putExtra("url", currentNews.getUrl());
            mContext.startActivity(intent);
        });


            holder.newsPublished.setText("Published: " + currentNews.getPublished());
            holder.newsTitle.setText(currentNews.getTitle());
            holder.newsAuthor.setText(currentNews.getAuthor());
            holder.newsDescription.setText(currentNews.getDescription());
            Glide.with(mContext)
                    .load(news.get(position).getUrlToImage())
                    .into(holder.newsImage);




    }

    @Override
    public int getItemCount() {

        return news.size();
    }

    public void setNews(List<News> news){
        this.news = news;
        notifyDataSetChanged();
    }

    public News getNewsAt(int position){
        return news.get(position);
    }


    static class NewsHolder extends RecyclerView.ViewHolder {
        private TextView newsPublished;
        private TextView newsTitle;
        private TextView newsDescription;
        private TextView newsAuthor;
        private TextView id;
        private ImageView newsImage;
        private CardView cardView;


        public NewsHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.news_card);
            newsPublished = itemView.findViewById(R.id.published);
            newsTitle = itemView.findViewById(R.id.title);
            newsAuthor = itemView.findViewById(R.id.author);
            newsDescription = itemView.findViewById(R.id.description);
            newsImage = itemView.findViewById(R.id.article_img);

        }
    }
}
