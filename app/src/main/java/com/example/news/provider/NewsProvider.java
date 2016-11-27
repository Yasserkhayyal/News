package com.example.news.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Mohamed Yasser on 11/25/2016.
 */
public class NewsProvider extends ContentProvider {
    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DatabaseHandler mOpenHelper;

    private final static String LOG_TAG = NewsProvider.class.getSimpleName();
    final static int NEWS_DIR = 100;
    final static int NEWS_ITEM = 101;
    final static int DETAILS_ITEM = 201;

    @Override
    public boolean onCreate() {
        mOpenHelper = new DatabaseHandler(getContext());
        return true;
    }

    static UriMatcher buildUriMatcher() {
        // 1) The code passed into the constructor represents the code to return for the root
        // URI.  It's common to use NO_MATCH as the code for this case. Add the constructor below.
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        String authority = ProviderContract.CONTENT_AUTHORITY;


        // 2) Use the addURI function to match each of the types.  Use the constants from
        // ImagesContract to help define the types to the UriMatcher.
        uriMatcher.addURI(authority, ProviderContract.PATH_NEWS_TABLE, NEWS_DIR);
        uriMatcher.addURI(authority, ProviderContract.PATH_NEWS_TABLE + "/*", NEWS_ITEM);
        uriMatcher.addURI(authority, ProviderContract.PATH_DETAILS_TABLE + "/*", DETAILS_ITEM);
        return uriMatcher;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case NEWS_DIR:
                retCursor = mOpenHelper.getReadableDatabase()
                        .query(ProviderContract.NEWS_TABLE_ENTRY.TABLE_NAME, projection, selection
                                , selectionArgs, null, null, sortOrder);
                break;

            case NEWS_ITEM:
                retCursor = getSelectedItem(uri, projection, sortOrder);
                break;

            case DETAILS_ITEM:
                retCursor = getSelectedItemDetails(uri, projection, sortOrder);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);

        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case NEWS_DIR:
                //All or multiple records are required
                return ProviderContract.NEWS_TABLE_ENTRY.CONTENT_TYPE;

            case NEWS_ITEM:
                //only one record is required
                return ProviderContract.NEWS_TABLE_ENTRY.CONTENT_ITEM_TYPE;

            case DETAILS_ITEM:
                //only one record is required
                return ProviderContract.DETAILS_TABLE_ENTRY.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case DETAILS_ITEM:
                long _id = db.insert(ProviderContract.DETAILS_TABLE_ENTRY.TABLE_NAME, null, values);
                if (_id > 0) {
                    returnUri = ProviderContract.DETAILS_TABLE_ENTRY.addNewRecord(_id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;

    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int returnCount = 0;

        switch (match) {
            case NEWS_DIR:
                db.beginTransaction();
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(ProviderContract.NEWS_TABLE_ENTRY.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }


    //get primary data of the news item
    private Cursor getSelectedItem(Uri uri, String[] projection, String sortOrder) {
        String news_id = uri.getLastPathSegment();
        String selection = ProviderContract.NEWS_TABLE_ENTRY.NEWS_ID + " =?";
        String[] selectionArgs = new String[]{news_id};
        return getContext().getContentResolver().query(ProviderContract.NEWS_TABLE_ENTRY.CONTENT_URI
                , projection, selection, selectionArgs, sortOrder);
    }

    //get details of the news item
    private Cursor getSelectedItemDetails(Uri uri, String[] projection, String sortOrder){
        String news_id = uri.getLastPathSegment();
        String selection = ProviderContract.DETAILS_TABLE_ENTRY.NEWS_ID + " =?";
        String[] selectionArgs = new String[]{news_id};
        return getContext().getContentResolver().query(ProviderContract.DETAILS_TABLE_ENTRY.CONTENT_URI
                , projection, selection, selectionArgs, sortOrder);
    }
}
