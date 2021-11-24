package com.gac5206.covidawareness.news.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.gac5206.covidawareness.R;
import com.gac5206.covidawareness.news.api.NewsAPIModel;
import com.gac5206.covidawareness.news.api.NewsApiAdapter;
import com.gac5206.covidawareness.news.recycler.NewsAdapter;
import com.gac5206.covidawareness.news.room.News;
import com.gac5206.covidawareness.news.room.NewsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class WebActivity extends AppCompatActivity {
    FloatingActionButton saveNews, closeNews;

    ArrayList<NewsAPIModel> mNewsArrayList;
    NewsApiAdapter adapter;
    NewsAdapter newsAdapter;
    NewsViewModel newsViewModel;
    WebView webView;
    String url, author, title, description, published, imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView = findViewById(R.id.activity_web);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);
        mNewsArrayList = new ArrayList<>();


        saveNews = findViewById(R.id.save_news);
        closeNews = findViewById(R.id.close_news);

        saveNews.setOnClickListener(this::OnClick);
        closeNews.setOnClickListener(this::OnClick);

        newsAdapter = new NewsAdapter(this);
        adapter = new NewsApiAdapter(this, mNewsArrayList);


        newsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        newsViewModel.getNews().observe(this, new Observer<List<News>>(){

            @Override
            public void onChanged(List<News> news) {
                newsAdapter.setNews(news);
            }
        });
        



    }

    private void OnClick(View view) {

        switch(view.getId()){

            case R.id.save_news:
                saveArticle();
                break;
            case R.id.close_news:
                startActivity(new Intent(this, NewsActivity.class));

                Toast.makeText(this, "Closed news!", Toast.LENGTH_LONG).show();
                break;


        }
    }

    private void saveArticle() {


        if (getIntent().hasExtra("url")) {
            url = getIntent().getStringExtra("url");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            description = getIntent().getStringExtra("description");
            published = getIntent().getStringExtra("published");
            imageUrl = getIntent().getStringExtra("image");
        }

        News news = new News(author, "Contents2", description, published, "Source23",title, url, imageUrl);
        newsViewModel.insert(news);
        Toast.makeText(this, "Article Saved!", Toast.LENGTH_LONG).show();

    }


}
