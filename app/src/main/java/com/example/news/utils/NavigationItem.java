package com.example.news.utils;

/**
 * Created by Mohamed Yasser on 11/24/2016.
 * Object to store navigation item data
 */
public class NavigationItem {
    public int item_drawable_resId;
    public String item_title;

    public NavigationItem(int drawable_resId,String title){
        item_drawable_resId = drawable_resId;
        item_title = title;
    }
}
