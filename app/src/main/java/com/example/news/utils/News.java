package com.example.news.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mohamed Yasser on 11/26/2016.
 */
public class News {

    @SerializedName("News")
    List<NewsItem> newsList;
    NewsItem newsItem;

    public News(){
        newsList = new ArrayList<>();
    }

    public News parseJSON(String response) {
        String date_format = "dd-MM, yyyy";
        Gson gson = new GsonBuilder().setDateFormat(date_format).create();
        return gson.fromJson(response, News.class);
    }

    public NewsItem getNewsItem() {
        return newsItem;
    }

    public List<NewsItem> getNewsList() {
        return newsList;
    }
}
