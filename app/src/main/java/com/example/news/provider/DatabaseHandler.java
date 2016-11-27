package com.example.news.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mohamed Yasser on 11/25/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper{
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "NewsDataBase";

    Context context;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_IMAGES_TABLE = "CREATE TABLE "
                + ProviderContract.NEWS_TABLE_ENTRY.TABLE_NAME + " (" +
                ProviderContract.NEWS_TABLE_ENTRY._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProviderContract.NEWS_TABLE_ENTRY.NEWS_TITLE + " TEXT NOT NULL, " +
                ProviderContract.NEWS_TABLE_ENTRY.NEWS_ID + " TEXT NOT NULL, " +
                ProviderContract.NEWS_TABLE_ENTRY.POST_DATE + " TEXT NOT NULL, " +
                ProviderContract.NEWS_TABLE_ENTRY.IMAGE_URL + " TEXT NOT NULL, " +
                ProviderContract.NEWS_TABLE_ENTRY.NEWS_TYPE + " TEXT NOT NULL, " +
                ProviderContract.NEWS_TABLE_ENTRY.NUM_OF_VIEWS + " TEXT NOT NULL, " +
                ProviderContract.NEWS_TABLE_ENTRY.LIKES + " TEXT NOT NULL, " +
                " UNIQUE (" + ProviderContract.NEWS_TABLE_ENTRY.NEWS_ID + ") ON CONFLICT IGNORE );";

        final String SQL_CREATE_DETAILS_TABLE = "CREATE TABLE " +
                ProviderContract.DETAILS_TABLE_ENTRY.TABLE_NAME + " (" +
                ProviderContract.DETAILS_TABLE_ENTRY._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ProviderContract.DETAILS_TABLE_ENTRY.NEWS_ID + " TEXT NOT NULL, " +
                ProviderContract.DETAILS_TABLE_ENTRY.ITEM_DESCRIPTION + " TEXT NOT NULL, "+
                ProviderContract.DETAILS_TABLE_ENTRY.SHARE_URL + " TEXT NOT NULL, " +
                ProviderContract.DETAILS_TABLE_ENTRY.VIDEO_URL + " TEXT NOT NULL, " +
                // Set up the movie id column as a foreign key to movie_details table.
                " FOREIGN KEY (" + ProviderContract.DETAILS_TABLE_ENTRY.NEWS_ID + ") REFERENCES "
                + ProviderContract.NEWS_TABLE_ENTRY.TABLE_NAME +
                " (" + ProviderContract.NEWS_TABLE_ENTRY.NEWS_ID + "));";

        
        sqLiteDatabase.execSQL(SQL_CREATE_IMAGES_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_DETAILS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
                    ProviderContract.NEWS_TABLE_ENTRY.TABLE_NAME);
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " +
                    ProviderContract.DETAILS_TABLE_ENTRY.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }
}
