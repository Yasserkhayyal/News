package com.example.news.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.news.R;
import com.example.news.utils.NewsItem;

import java.util.List;

/**
 * Created by Mohamed Yasser on 11/26/2016.
 */
public class NewsItemsAdapter extends BaseAdapter {
    Context mContext;
    List<NewsItem> newsItems;
    ViewHolder viewHolder;


    public NewsItemsAdapter(Context context,List<NewsItem> newsItems){
        mContext = context;
        this.newsItems = newsItems;
    }
    @Override
    public int getCount() {
        return newsItems.size();
    }

    @Override
    public NewsItem getItem(int position) {
        return newsItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.news_list_row, viewGroup,
                    false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }
        Glide.with(mContext).load(newsItems.get(position).getImageUrl())
                .asBitmap()
                .priority(Priority.IMMEDIATE)
                .placeholder(R.drawable.news_image_placeholder).centerCrop()
                .into(new BitmapImageViewTarget(viewHolder.news_image) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(mContext.getResources(),
                                Bitmap.createScaledBitmap(resource, viewHolder.news_image.getWidth()
                                        , viewHolder.news_image.getHeight(), false));
                        drawable.setCircular(true);
                        viewHolder.news_image.setImageDrawable(drawable);
                    }});

        viewHolder.news_title.setText(newsItems.get(position).getNewsTitle());
        if(newsItems.get(position).getNewsType().equals("84")){ //article news
            viewHolder.news_label.setImageResource(R.drawable.article_label);
        }else{ //video news
            viewHolder.news_label.setImageResource(R.drawable.video_label);
        }

        viewHolder.news_date.setText(newsItems.get(position).getPostDate());
        String likes_string = "Likes("+newsItems.get(position).getLikes()+")";
        viewHolder.news_likes.setText(likes_string);
        String views_string = newsItems.get(position).getNumOfViews() + " views";
        viewHolder.news_views.setText(views_string);
        return view;
    }

    private static class ViewHolder{
        ImageView news_image,news_label;
        TextView news_title,news_date,news_likes,news_views;

        public ViewHolder(View view){
            news_image = (ImageView) view.findViewById(R.id.news_image);
            news_label = (ImageView) view.findViewById(R.id.news_label);
            news_title = (TextView) view.findViewById(R.id.news_title);
            news_date = (TextView) view.findViewById(R.id.news_date);
            news_likes = (TextView) view.findViewById(R.id.news_likes);
            news_views = (TextView) view.findViewById(R.id.news_views);
        }
    }
}
