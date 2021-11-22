package com.gac5206.covidawareness.news.recycler;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.gac5206.covidawareness.R;
import com.gac5206.covidawareness.news.room.News;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private Context mContext;
    private List<News> news;


//    public NewsAdapter(Context context, ArrayList<News> news){
//        this.mContext = context;
//        this.news = news;
//    }

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
//        holder.newsSource.setText(currentNews.getSource());
            holder.newsPublished.setText(currentNews.getSource());
            holder.newsTitle.setText(currentNews.getSource());
            holder.newsDescription.setText(currentNews.getSource());
            Glide.with(mContext).load(news.get(position).getUrlToImage()).into(holder.newsImage);




    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public void setNews(List<News> news){
        this.news = news;
        notifyDataSetChanged();
    }


    static class NewsHolder extends RecyclerView.ViewHolder {
//        private TextView newsSource;
        private TextView newsPublished;
        private TextView newsTitle;
        private TextView newsDescription;
        private ImageView newsImage;


        public NewsHolder(@NonNull View itemView) {
            super(itemView);

//            newsSource = itemView.findViewById(R.id.source);
            newsPublished = itemView.findViewById(R.id.published);
            newsTitle = itemView.findViewById(R.id.title);
            newsDescription = itemView.findViewById(R.id.description);
            newsImage = itemView.findViewById(R.id.article_img);

        }
    }
}
