package com.gac5206.covidawareness.news.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsViewModel extends AndroidViewModel {
    private NewsRepository newsRepository;

    private LiveData<List<News>> allNews;


    public NewsViewModel(@NonNull Application application) {
        super(application);
        newsRepository = new NewsRepository(application);
        allNews = newsRepository.getNews();
    }

    public void insert(News news){
        newsRepository.insert(news);
    }

    public void update(News news){
        newsRepository.update(news);
    }

    public void delete(News news){
        newsRepository.delete(news);
    }

    public LiveData<List<News>> getNews(){
        return allNews;
    }

}
