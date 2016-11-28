package com.example.news.activities;

import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.news.R;
import com.example.news.utils.Constants;
import com.example.news.utils.NetworkCheck;
import com.example.news.utils.News;
import com.example.news.utils.NewsItem;
import com.example.news.utils.SingletonRequestQueue;

import org.json.JSONObject;

/**
 * Created by Mohamed Yasser on 11/26/2016.
 */
public class DetailsActivity extends AppCompatActivity {
    ImageView item_image;
    TextView item_title,date_tv,likes_tv,shows_text_view,news_description;
    NewsItem newsItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        item_image = (ImageView) findViewById(R.id.item_image);
        item_title = (TextView) findViewById(R.id.item_title);
        date_tv = (TextView) findViewById(R.id.date_tv);
        likes_tv = (TextView) findViewById(R.id.likes_tv);
        shows_text_view = (TextView) findViewById(R.id.shows_text_view);
        news_description = (TextView) findViewById(R.id.news_description);
    }

    @Override
    protected void onStart() {
        super.onStart();
        String newsId = getIntent().getStringExtra(Constants.NEWS_ID_EXTRA);
        if(NetworkCheck.isNetworkAvailable(this)) {
            new GetData().execute("http://egyptinnovate.com/en/api/v01/safe/GetNewsDetails?nid=" + newsId);
        }else{
            Toast.makeText(this,"Check your internet connection and try again!",
                    Toast.LENGTH_LONG).show();
        }
    }


    private class GetData extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {
            // Get a RequestQueue
            RequestQueue queue = SingletonRequestQueue.getInstance(DetailsActivity.this).
                    getRequestQueue();

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String stringResponse = response.toString();
                    News news = new News();
                    news = news.parseJSON(stringResponse);
                    newsItem = news.getNewsItem();
                    setViews(newsItem);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setViews(null);
                }
            });

            request.setTag("NewsDetails");
            queue.add(request);

            return null;
        }
    }


    private void setViews(final NewsItem item){
        if(item!=null){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    supportInvalidateOptionsMenu();
                    Glide.with(DetailsActivity.this).load(item.getImageUrl()).asBitmap()
                            .placeholder(R.drawable.news_image_placeholder).centerCrop()
                            .into(new BitmapImageViewTarget(item_image) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(getResources(),
                                            Bitmap.createScaledBitmap(resource, item_image.getWidth()
                                                    , item_image.getHeight(), false));
                                    drawable.setCornerRadius(80);
                                    item_image.setImageDrawable(drawable);
                                }});

                    item_image.setColorFilter(0X33000000, PorterDuff.Mode.SRC_ATOP);
                    item_title.setText(item.getNewsTitle());
                    date_tv.setText(item.getPostDate());
                    String likes_string = "Likes("+newsItem.getLikes()+")";
                    likes_tv.setText(likes_string);
                    String views_string = newsItem.getNumOfViews() + " views";
                    shows_text_view.setText(views_string);
                    news_description.setText(item.getItemDescription());
                }
            });

        }

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if(newsItem!=null){
            menu.findItem(R.id.share).setVisible(true);
        }else{
            menu.findItem(R.id.share).setVisible(false);
        }
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
