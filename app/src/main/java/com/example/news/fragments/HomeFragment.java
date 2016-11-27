package com.example.news.fragments;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.news.R;
import com.example.news.activities.DetailsActivity;
import com.example.news.adapters.NewsItemsAdapter;
import com.example.news.utils.Constants;
import com.example.news.utils.NetworkCheck;
import com.example.news.utils.News;
import com.example.news.utils.NewsItem;
import com.example.news.utils.SingletonRequestQueue;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Mohamed Yasser on 11/25/2016.
 */
public class HomeFragment extends Fragment {
    ListView listView;
    NewsItemsAdapter adapter;
    List<NewsItem> newsItems;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fagment,container,false);
        listView = (ListView) view.findViewById(R.id.news_list_view);
        //fire data request
        if(NetworkCheck.isNetworkAvailable(getActivity())) {
            new GetData().execute("http://egyptinnovate.com/en/api/v01/safe/GetNews");
        }else{
            Toast.makeText(getActivity(),"Check your internet connection and try again!",
                    Toast.LENGTH_LONG).show();
        }
        return view;
    }



    private class GetData extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {
            // Get a RequestQueue
            RequestQueue queue = SingletonRequestQueue.getInstance(getActivity()).
                    getRequestQueue();

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                    strings[0], null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    String stringResponse = response.toString();
                    News news = new News();//to initialize news.newsList
                    news = news.parseJSON(stringResponse);//to map jsonArray News to news.newsList
                    newsItems = news.getNewsList();
                    setAdapter(newsItems);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    setAdapter(null);
                }
            });

            request.setTag("NewsItems");
            queue.add(request);


            return null;
        }

    }


    private void setAdapter(final List<NewsItem> list){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(list!=null ){
                    if(!list.isEmpty()) {
                        getActivity().supportInvalidateOptionsMenu();
                        adapter = new NewsItemsAdapter(getActivity(), list);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                                    long rowId) {
                                Intent startDetailsActivity = new Intent(getActivity(),
                                        DetailsActivity.class);
                                startDetailsActivity.putExtra(Constants.NEWS_ID_EXTRA,
                                        newsItems.get(position).getNid());
                                startActivity(startDetailsActivity);
                            }
                        });
                    }
                }else{
                    Toast.makeText(getActivity(),"Oops!..something went wrong",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    //called directly before showing the menu
    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        if(newsItems!=null) {
            if (!newsItems.isEmpty()) {
                menu.findItem(R.id.filter).setVisible(true);
            } else {
                menu.findItem(R.id.filter).setVisible(false);
            }
        }
        super.onPrepareOptionsMenu(menu);
    }


    /*called once the user clicks on the overflow menu button on
    this is followed on devices running versions after Honeycomb*/
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
