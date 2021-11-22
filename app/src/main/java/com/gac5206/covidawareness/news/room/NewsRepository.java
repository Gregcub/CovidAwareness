package com.gac5206.covidawareness.news.room;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NewsRepository {

    private NewsDao newsDao;
    private LiveData<List<News>> allNews;

    public NewsRepository(Application application) {
        NewsDatabase newsDatabase = NewsDatabase.getInstance(application);
        newsDao = newsDatabase.newsDao();
        allNews = newsDao.getNews();
    }

    public void insert(News news){
        new InsertNewsAsyncTask(newsDao).execute(news);
    }

    public void update(News news){
        new UpdateNewsAsyncTask(newsDao).execute(news);
    }

    public void delete(News news){
        new DeleteNewsAsyncTask(newsDao).execute(news);
    }

    public LiveData<List<News>> getNews(){
        return allNews;
    }

    private static class InsertNewsAsyncTask extends AsyncTask<News, Void, Void> {

        private NewsDao newsDao;

        private InsertNewsAsyncTask(NewsDao newsDao){
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            newsDao.insertNews(news[0]);
            return null;
        }

    }

    private static class UpdateNewsAsyncTask extends AsyncTask<News, Void, Void> {

        private NewsDao newsDao;

        private UpdateNewsAsyncTask(NewsDao newsDao){
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            newsDao.updateNews(news[0]);
            return null;
        }

    }

    private static class DeleteNewsAsyncTask extends AsyncTask<News, Void, Void> {

        private NewsDao newsDao;

        private DeleteNewsAsyncTask(NewsDao newsDao){
            this.newsDao = newsDao;
        }

        @Override
        protected Void doInBackground(News... news) {
            newsDao.deleteNews(news[0]);
            return null;
        }

    }

}
