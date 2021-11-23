package com.gac5206.covidawareness.news;

import com.gac5206.covidawareness.news.api.NewsAPIModel;

import java.util.ArrayList;

public class MainNews {

    private String status;
    private String totalResults;
    private ArrayList<NewsAPIModel> articles;

    public MainNews(String status, String totalResults, ArrayList<NewsAPIModel> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public ArrayList<NewsAPIModel> getArticles() {
        return articles;
    }

    public void setArticles(ArrayList<NewsAPIModel> articles) {
        this.articles = articles;
    }
}
