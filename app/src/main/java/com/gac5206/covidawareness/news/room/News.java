package com.gac5206.covidawareness.news.room;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName="articles", indices = {@Index(value = {"title","published"}, unique = true)})
public class News {

    @PrimaryKey(autoGenerate = true)
    private  int newsID;

    @ColumnInfo(name = "author")
    private  String author;

    @ColumnInfo(name = "contents")
    private  String contents;

    @ColumnInfo(name = "description")
    private  String description;

    @ColumnInfo(name = "published")
    private  String published;

    @ColumnInfo(name = "source")
    private  String source;

    @ColumnInfo(name = "title")
    private  String title;

    @ColumnInfo(name = "url")
    private  String url;

    @ColumnInfo(name = "urlImage")
    private  String urlToImage;


    public News(String author, String contents, String description, String published, String source, String title, String url, String urlToImage) {
        this.author = author;
        this.contents = contents;
        this.description = description;
        this.published = published;
        this.source = source;
        this.title = title;
        this.url = url;
        this.urlToImage = urlToImage;
    }



    public int getNewsID() {
        return newsID;
    }

    public void setNewsID(int newsID) {
        this.newsID = newsID;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }
}
