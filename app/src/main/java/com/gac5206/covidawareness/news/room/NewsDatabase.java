package com.gac5206.covidawareness.news.room;


import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {News.class}, version = 1, exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {

    private static NewsDatabase INSTANCE;

    public abstract NewsDao newsDao();

    public static synchronized NewsDatabase getInstance(Context context){

        if(INSTANCE == null){


            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NewsDatabase.class, "News_Database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .addCallback(roomCallback)
                    .build();

        }
        return INSTANCE;

    }



    private static RoomDatabase.Callback roomCallback = new Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(INSTANCE).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private NewsDao newsDao;
        private PopulateDbAsyncTask(NewsDatabase newsDb){
            newsDao = newsDb.newsDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            newsDao.insertNews(new News("","","","","","","",""));
            return null;
        }
    }

}
