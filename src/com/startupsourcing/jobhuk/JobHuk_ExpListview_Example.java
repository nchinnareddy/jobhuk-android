package com.startupsourcing.jobhuk;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

public class JobHuk_ExpListview_Example extends Activity {
	
	private ExpandableListAdapter listAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
    
    DrawerLayout mDrawerLayout;
    ActionBarDrawerToggle mDrawerToggle;
    CharSequence mDrawerTitle;
    CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobhuk_main_expview);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //Navigation Drawer
		expListView = (ExpandableListView) findViewById(R.id.lvExp); //Expandable list view
			
		getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);			
     
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
 
        // Adding group data
        listDataHeader.add("Finder Fee");
        listDataHeader.add("Job Type");
        listDataHeader.add("Salary");
        listDataHeader.add("Location");
        listDataHeader.add("Skills");
        listDataHeader.add("Industries");
 
        // Adding child data
        List<String> finderfee = new ArrayList<String>();
        finderfee.add("$ 3000+");
        finderfee.add("$ 1000+");
        finderfee.add("$ 500+");
        finderfee.add("$ 100+");
        List<String> type = new ArrayList<String>();
        type.add("FullTime");
        type.add("Contract");
        List<String> salary = new ArrayList<String>();
        salary.add("$ 100,000+");
        salary.add("$ 80,000+");
        salary.add("$ 60,000+");
        salary.add("$ 40,000+");
        List<String> location = new ArrayList<String>();
        location.add("Hyderabad");
        location.add("Banglore");
        location.add("Chennai");
        location.add("Trivandrum");
        List<String> skills = new ArrayList<String>();
        skills.add("Java");
        skills.add("Ruby on Rails");
        skills.add("HTML 5");
        skills.add("Ruby");
        List<String> industries = new ArrayList<String>();
        industries.add("Information");
        industries.add("Services");
        industries.add("Software");
        industries.add("Healthcare");

 
        listDataChild.put(listDataHeader.get(0), finderfee); 
        listDataChild.put(listDataHeader.get(1), type);
        listDataChild.put(listDataHeader.get(2), salary);
        listDataChild.put(listDataHeader.get(3), location);
        listDataChild.put(listDataHeader.get(4), skills);
        listDataChild.put(listDataHeader.get(5), industries);
        
        listAdapter = new ExpListAdapter1(JobHuk_ExpListview_Example.this,listDataHeader,listDataChild);
		expListView.setAdapter(listAdapter);
		((BaseExpandableListAdapter) listAdapter).notifyDataSetChanged();
		
		//This code is to get overflow menu on rightside in action bar in android..
        try {
        	  ViewConfiguration config = ViewConfiguration.get(this);
        	  Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");

        	  if (menuKeyField != null) {
        	    menuKeyField.setAccessible(true);
        	    menuKeyField.setBoolean(config, false);
        	  }
        	}
        	catch (Exception e) {
        		// presumably, not relevant
        	}
        // Till this point..
        
//		listAdapter.notifyDataSetChanged();
		
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

	}



    
