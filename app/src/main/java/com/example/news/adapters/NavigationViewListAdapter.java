package com.example.news.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.news.R;
import com.example.news.utils.NavigationItem;

/**
 * Created by Mohamed Yasser on 11/24/2016.
 */
public class NavigationViewListAdapter extends BaseAdapter {
    private Context mContext;
    private NavigationItem[] mNavigationItems;

    public NavigationViewListAdapter(Context context, NavigationItem[] items){
        mContext = context;
        mNavigationItems = items;
    }
    @Override
    public int getCount() {
        return mNavigationItems.length;
    }

    @Override
    public NavigationItem getItem(int position) {
        return mNavigationItems[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.nav_view_item_layout, viewGroup,
                    false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.item_image_view.setImageResource(mNavigationItems[i].item_drawable_resId);
        viewHolder.item_text_view.setText(mNavigationItems[i].item_title);
        return view;
    }

    private static class ViewHolder{
        ImageView item_image_view;
        TextView item_text_view;

        public ViewHolder(View view){
            item_image_view = (ImageView) view.findViewById(R.id.nav_view_item_image);
            item_text_view = (TextView) view.findViewById(R.id.nav_view_item_text);
        }
    }
}
