package com.startupsourcing.jobhuk;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class JobHuk_Listview_Example extends Activity {
	
	private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mPlanetTitles;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobhuk__main3);
		
		mPlanetTitles = new String[] {"Finders Fee","Type","Salary","Location","Skills","Industries"};
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        ArrayList<String> list = new ArrayList<String>();
	    for (int i = 0; i < mPlanetTitles.length; ++i) {
	      list.add(mPlanetTitles[i]);
	    }
	    
	    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //NavigationDrawer
        mDrawerList = (ListView) findViewById(R.id.left_drawer); //ListView
        
       listAdapter adapter = new listAdapter(JobHuk_Listview_Example.this,android.R.layout.simple_list_item_1,list);
        mDrawerList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.drawable.ic_drawer,R.string.app_name,R.string.app_name)
        {
            public void onDrawerClosed(View drawerView) {
            	super.onDrawerClosed(drawerView);
                getActionBar().setTitle(mTitle);
                // calling onPrepareOptionsMenu() to show action bar icons
                invalidateOptionsMenu();
            }
 
            public void onDrawerOpened(View drawerView) {
            	super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
                // calling onPrepareOptionsMenu() to hide action bar icons
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
	}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    // toggle nav drawer on selecting action bar app icon/title
    if (mDrawerToggle.onOptionsItemSelected(item)) {
        return true;
    }
    // Handle action bar actions click
    switch (item.getItemId()) {
    case R.id.action_settings:
        return true;
    default:
        return super.onOptionsItemSelected(item);
    }
}

@Override
public boolean onPrepareOptionsMenu(Menu menu) {
    // if nav drawer is opened, hide the action items
    boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
    //menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
    return super.onPrepareOptionsMenu(menu);
}

@Override
protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    mDrawerToggle.syncState();
}

@Override
public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    mDrawerToggle.onConfigurationChanged(newConfig);
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.job_huk__main3, menu);
		return true;
	}
}
