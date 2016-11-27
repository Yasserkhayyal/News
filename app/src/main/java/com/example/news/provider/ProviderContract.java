package com.example.news.provider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohamed Yasser on 11/25/2016.
 */
public class ProviderContract {
    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.news";
    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    //possible path (uniquely is the path of the "news" table)
    public static final String PATH_NEWS_TABLE = "news";

    //possible path (uniquely is the path of the "details" table)
    public static final String PATH_DETAILS_TABLE = "details";


    public static final class NEWS_TABLE_ENTRY implements BaseColumns {

        //news table name
        public static final String TABLE_NAME = "news";

        //"news" table columns' names
        public static final String NEWS_TITLE = "NewsTitle";//1
        public static final String NEWS_ID = "Nid";//2
        public static final String POST_DATE = "PostDate";//3
        public static final String IMAGE_URL = "ImageUrl";//4
        public static final String NEWS_TYPE = "NewsType";//5
        public static final String NUM_OF_VIEWS = "NumofViews";//6
        public static final String LIKES = "Likes";//7

        // "news" table uri
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_NEWS_TABLE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS_TABLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS_TABLE;

        public static Uri addNewRecord(long rowId) {
            return ContentUris.withAppendedId(CONTENT_URI, rowId);
        }

    }

    public static final class DETAILS_TABLE_ENTRY implements BaseColumns{
        //news table name
        public static final String TABLE_NAME = "details";

        public static final String NEWS_ID = "Nid";//1
        public static final String ITEM_DESCRIPTION = "ItemDescription";//2
        public static final String SHARE_URL = "ShareURL";//3
        public static final String VIDEO_URL = "VideoURL";//4

        // "news" table uri
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DETAILS_TABLE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAILS_TABLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DETAILS_TABLE;

        public static Uri addNewRecord(long rowId) {
            return ContentUris.withAppendedId(CONTENT_URI, rowId);
        }
    }

}
