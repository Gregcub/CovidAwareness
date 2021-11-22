package com.gac5206.covidawareness.news.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NewsDao {

    @Query("Select * FROM articles")
    LiveData<List<News>> getNews();

    @Insert
    void insertNews(News articles);

    @Update
    void updateNews(News articles);

    @Delete
    void deleteNews(News articles);



}
