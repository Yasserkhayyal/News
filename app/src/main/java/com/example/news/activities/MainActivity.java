package com.example.news.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.PersistableBundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.example.news.R;
import com.example.news.adapters.NavigationViewListAdapter;
import com.example.news.fragments.HomeFragment;
import com.example.news.utils.NavigationItem;

import java.lang.reflect.Array;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private NavigationViewListAdapter navigationViewAdapter;
    private NavigationView mNavigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        //first get the drawables typed array from strings.xml
        TypedArray nav_view_drawables = getResources().obtainTypedArray(R.array.
                nav_view_item_drawables);

        //then get the strings array from strings.xml
        String[] nav_view_strings = getResources().getStringArray(R.array.nav_view_item_strings);

        //first we initialize the navigationItems array
        NavigationItem[] navigationItems = new NavigationItem[4];

        for(int index=0;index <nav_view_drawables.length();index++){
            navigationItems[index] = new NavigationItem(nav_view_drawables
                    .getResourceId(index,0),
                    nav_view_strings[index]);
        }

        nav_view_drawables.recycle();
        navigationViewAdapter =  new NavigationViewListAdapter(MainActivity.this,navigationItems);
        ListView nav_list_view = (ListView) findViewById(R.id.nav_view_list_view);
        View footerView = ((LayoutInflater) this.getSystemService(Context
                .LAYOUT_INFLATER_SERVICE)).inflate(R.layout.nav_view_list_footer, null, false);
        nav_list_view.addFooterView(footerView);
        nav_list_view.setAdapter(navigationViewAdapter);


        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.app_name, R.string.app_name
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

        };

        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerToggle.syncState();

        if(savedInstanceState==null){
            replaceFragment(new HomeFragment(),"Home");
        }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }


    // called to replace fragment
    private void replaceFragment(Fragment fragment, String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment, tag).commit();
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return false;
    }
}
