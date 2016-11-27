package com.example.news.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohamed Yasser on 11/26/2016.
 */

public class NewsItem {

    String NewsTitle, Nid, PostDate, ImageUrl, NewsType, Likes;
    String ItemDescription,ShareURL,VideoURL;
    @SerializedName("NumofViews")
    String NumOfViews;

    public String getNewsTitle() {
        return NewsTitle;
    }

    public String getNid() {
        return Nid;
    }

    public String getPostDate() {
        return PostDate;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public String getNewsType() {
        return NewsType;
    }

    public String getLikes() {
        return Likes;
    }

    public String getNumOfViews() {
        return NumOfViews;
    }

    public String getItemDescription() {
        return ItemDescription;
    }

    public String getShareURL() {
        return ShareURL;
    }

    public String getVideoURL() {
        return VideoURL;
    }

}
