package com.startupsourcing.jobhuk;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class JobHuk_Main_Expview extends Activity implements OnClickListener {
	
	private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    
    private ExpandableListAdapter listAdapter;
	private ExpandableListView expListView;
	private List<String> listDataHeader;
	private HashMap<String, List<String>> listDataChild;
	
	String[] values = null;
	String jobs_count,posted_ago;
	public ArrayList<String> Title = new ArrayList<String>();
	public ArrayList<String> Comp_name = new ArrayList<String>();
	public ArrayList<String> Location = new ArrayList<String>();
	public ArrayList<String> Job_type = new ArrayList<String>();
	public ArrayList<String> Contract_rate = new ArrayList<String>();
	public ArrayList<String> Duration_hours = new ArrayList<String>();
	public ArrayList<String> Description = new ArrayList<String>();
	public ArrayList<String> Finders_fee = new ArrayList<String>();
	public ArrayList<String> Date_posted = new ArrayList<String>();
	public ArrayList<String> Compensation = new ArrayList<String>();
	
	ArrayList<String> spinnerArray = new ArrayList<String>();

	public ListView listview;
	public JobsListView adapter;
	Button prev,one,two,three,four,five,next,update,amount,job_type,salary,filters,popup_close,finders_fee;
	LinearLayout layout1, layout2, layout3;
	int Pagenum,TotalPages,no_of_jobs,update_count=1,jobtype_count=1,finderfee_count=1;
	ArrayList<HashMap<String,String>> list2 = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> list3 = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> Fulltime = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> Contract = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> Finders_Fee = new ArrayList<HashMap<String,String>>();
	
	long Unix_time,Database_time;
	SharedPreferences prefs;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobhuk_main_expview);
	    
	    mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); //NavigationDrawer
	    expListView = (ExpandableListView) findViewById(R.id.lvExp); //ExpandableListView
               
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
        
        listAdapter = new expListAdapter(JobHuk_Main_Expview.this,listDataHeader,listDataChild);
	    expListView.setAdapter(listAdapter);
        ((BaseExpandableListAdapter) listAdapter).notifyDataSetChanged();
        
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

//		String URL = "http://192.168.0.111:3000/api/jobs/";
		String URL = "http://staging.jobhuk.com/api/jobs";
//		String URL = "https://www.jobhuk.com/api/jobs";
		
		prefs =getSharedPreferences("my_prefs", MODE_WORLD_READABLE);
		editor = prefs.edit();
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
	        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	        StrictMode.setThreadPolicy(policy);
	        }

		new jobslist().execute(URL);
		
		listview	= (ListView) findViewById(R.id.listview);
		layout1 = (LinearLayout) findViewById(R.id.layout1);
		layout2 = (LinearLayout) findViewById(R.id.layout2);

		update = (Button) findViewById(R.id.update);
		amount = (Button) findViewById(R.id.amount);

		finders_fee = (Button) findViewById(R.id.finders_fee);

		update.setOnClickListener(this);
		amount.setOnClickListener(this);
		
		Spinner spinner = (Spinner) findViewById(R.id.spinner);
		List<String> list1 = new ArrayList<String>();
		list1.add("FullTime");
		list1.add("Contract");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list1);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
		
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
	        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) 
	        {
	            String jobtype =parent.getItemAtPosition(pos).toString();
	            editor.putString("jobtype", jobtype);
	            editor.commit();
	            	String URL = "http://staging.jobhuk.com/api/jobs?job_type="+jobtype+"";
					new jobslist().execute(URL);
	        }
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}});
		
		Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);
		List<String> list4 = new ArrayList<String>();
		list4.add("$ 100,000+");
		list4.add("$ 80,000+");
		list4.add("$ 60,000+");
		list4.add("$ 40,000+");
		ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list4);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(dataAdapter2);
		
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
	        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) 
	        {
	            	int compensation = Integer.parseInt(parent.getItemAtPosition(pos).toString().replaceAll("\\D", ""));
	            	editor.putInt("compensation", compensation);
		            editor.commit();
	            	String URL = "http://staging.jobhuk.com/api/jobs?compensation="+compensation+"";
					new jobslist().execute(URL);
	        }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}});

		Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
		List<String> list11 = new ArrayList<String>();
		list11.add("$ 3000+");
		list11.add("$ 2000+");
		list11.add("$ 1000+");
		list11.add("$ 500+");
		ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list11);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(dataAdapter1);
		
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
	        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) 
	        {
	            	int finders_fee_amount = Integer.parseInt(parent.getItemAtPosition(pos).toString().replaceAll("\\D", ""));	            	
	            	editor.putInt("finders_fee_amount", finders_fee_amount);
		            editor.commit();
	            	String URL = "http://staging.jobhuk.com/api/jobs?finders_fee_amount="+finders_fee_amount+"";
					new jobslist().execute(URL);
	            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}});

		Spinner spinner3 = (Spinner) findViewById(R.id.spinner3);
		List<String> list5 = new ArrayList<String>();
		list5.add("Hyderabad");
		list5.add("Chennai");
		list5.add("Banglore");
		list5.add("Mumbai");
		ArrayAdapter<String> dataAdapter5 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list5);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner3.setAdapter(dataAdapter5);
		
		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
	        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) 
	        {
	        	String location =parent.getItemAtPosition(pos).toString().toLowerCase();
	        	editor.putString("location", location);
	            editor.commit();
	            	String URL = "http://staging.jobhuk.com/api/jobs?location="+location+"";
					new jobslist().execute(URL);
	        }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}});

		Spinner spinner4 = (Spinner) findViewById(R.id.spinner4);
		List<String> list6 = new ArrayList<String>();
		list6.add("Java");
		list6.add("Ruby On Rails");
		list6.add("Html5");
		list6.add("Ruby");
		ArrayAdapter<String> dataAdapter6 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list6);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner4.setAdapter(dataAdapter6);
		
		spinner4.setOnItemSelectedListener(new OnItemSelectedListener() 
		{
	        public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) 
	        {
	        	String job_skills =parent.getItemAtPosition(pos).toString().toLowerCase().replaceAll("\\W", "");	
	        	editor.putString("job_skills", job_skills);
	            editor.commit();
	            	String URL = "http://staging.jobhuk.com/api/jobs?job_skills="+job_skills+"";
					new jobslist().execute(URL);
	            }

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}});
	
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
	    boolean drawerOpen = mDrawerLayout.isDrawerOpen(expListView);
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
	
	public void show_Pagelist(int Pageno)
	{
				
		Pagenum = Pageno;

		if(Pagenum!=TotalPages)
		{
			Log.i("Hello Page",""+Pagenum);
			
		String URL = "http://staging.jobhuk.com/api/jobs?page="+Pagenum+"";
			new jobslist().execute(URL);
		}
		
		else
		{
			Log.i("Else...Pagenum",""+Pagenum);
			String URL = "http://staging.jobhuk.com/api/jobs";
    		Toast.makeText(getApplicationContext(),
	        	      "Last page has been reached ", Toast.LENGTH_SHORT).show();
		}
	}
	
	public class jobslist extends AsyncTask<String, Void, JSONArray>
	{
		String url;
		JSONArray jobs;
		ArrayList<String> list = new ArrayList<String>();
		HashMap<String,String> hash;
		HttpResponse response;
		HttpClient httpClient;
		
		protected JSONArray doInBackground(String... params) {
			 HttpClient client = new DefaultHttpClient();
			 HttpGet httpGet = new HttpGet(params[0]);
			 
			 try {
				HttpResponse response = client.execute(httpGet);
				HttpEntity entity = response.getEntity();
	            Object content = EntityUtils.toString(entity);
	            
	            try {
					JSONObject jsonResponse = new JSONObject(content.toString());
					JSONObject resultObject = jsonResponse.getJSONObject("resultObject");
					
					jobs_count = resultObject.getString("jobs_count");
					jobs = resultObject.getJSONArray("jobs");
					
					no_of_jobs = Integer.parseInt(jobs_count);
					
					if(no_of_jobs%10==0)
						TotalPages = no_of_jobs/10;
					else
						TotalPages = no_of_jobs/10 +1;
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// TODO Auto-generated method stub
			return jobs;
		}
//		
		protected void onPostExecute(JSONArray jobs)
		{
			
			hash = new HashMap<String,String>();
			HashMap<String,String> fulltime = new HashMap<String,String>();
			HashMap<String,String> contract = new HashMap<String,String>();
			
			list2.removeAll(list2);
			list3.removeAll(list3);
			Contract.removeAll(Contract);
			Fulltime.removeAll(Fulltime);
			Finders_Fee.removeAll(Finders_Fee);

			for(int i=0;i<jobs.length();i++)
			{
				Unix_time = (int) (System.currentTimeMillis() / 1000L);
				
				JSONObject jobs_Sub;
				try {
					jobs_Sub = jobs.getJSONObject(i);
					
					Title.add(jobs_Sub.getString("title"));
					Comp_name.add(jobs_Sub.getString("company_name"));
					Description.add(jobs_Sub.getString("description"));
					Location.add(jobs_Sub.getString("location"));
					Job_type.add(jobs_Sub.getString("job_type"));
					Duration_hours.add(jobs_Sub.getString("duration_hours"));
					Contract_rate.add(jobs_Sub.getString("contract_rate"));
					Finders_fee.add(jobs_Sub.getString("finders_fee_amount"));
					Compensation.add(jobs_Sub.getString("compensation"));
					
					String str= jobs_Sub.getString("updated_at");
					String str1[] = str.split("T");
					String str2[] = str1[1].split("-");
					
					Timestamp timestamp = Timestamp.valueOf(str1[0] + " " +str2[0]+ ".000");
					Database_time = timestamp.getTime()/1000L;
					long diff = Unix_time - Database_time;
					int No_of_days = (int) diff/86400;
					
					int q = No_of_days/30;
					int r = No_of_days%30;
					
					if(q!=0&&(r==0||r!=0))
					{
						if(q==1)
							posted_ago = "posted about "+q+" month ago" ;
						else
							posted_ago = "posted about "+q+" months ago" ;
					}
					else if(q==0&&r==0)
					{
						posted_ago = "posted today" ;
					}
					else
					{
						posted_ago = "posted "+No_of_days+" days ago";
					}
					
					hash.put("Title"+i,jobs_Sub.getString("title"));
					hash.put("Comp_name"+i,jobs_Sub.getString("company_name"));
					hash.put("Location"+i, jobs_Sub.getString("location"));
					hash.put("Job_type"+i, jobs_Sub.getString("job_type"));
					hash.put("Duration_hours"+i,jobs_Sub.getString("duration_hours"));
					hash.put("Compensation"+i, jobs_Sub.getString("compensation"));
					hash.put("Finders_fee"+i,jobs_Sub.getString("finders_fee_amount"));
					
					hash.put("Posted_ago"+i, posted_ago);				
					
					list2.add(hash);
					Fulltime.add(fulltime);
					Contract.add(contract);
					
					} 
				catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
					
			adapter = new JobsListView(JobHuk_Main_Expview.this, R.layout.activity_jobslistview, list2);
		  	listview.setAdapter(adapter);
	        adapter.notifyDataSetChanged();
	        
	        listview.setOnItemClickListener(new OnItemClickListener() {
	        	  public void onItemClick(AdapterView<?> parent, View view,
	        	    int position, long id) {

	        		  Intent hello = new Intent(JobHuk_Main_Expview.this,JobsDescription.class);
	        		  hello.putExtra("Title", Title.get(position));
	        		  hello.putExtra("Comp_name", Comp_name.get(position));
	        		  hello.putExtra("Location", Location.get(position));
	        		  hello.putExtra("Job_type", Job_type.get(position));
	        		  hello.putExtra("Duration_hours", Duration_hours.get(position));
	        		  hello.putExtra("Description", Description.get(position));
	        		  hello.putExtra("Finders_fee",Finders_fee.get(position));
	        		  hello.putExtra("Compensation",Compensation.get(position));
	        		  startActivity(hello);
	        	  }
	        	});
		}
	}
 
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{

		case R.id.update:
			update_count++;
				String hello = prefs.getString("jobtype", ""),URL;
				if(!hello.equals(Contract))
				{
					 URL = "http://staging.jobhuk.com/api/jobs?job_type="+hello+"&sort_by=date_posted&sort_type=desc";
				}
				else
				{
					URL = "http://staging.jobhuk.com/api/jobs?job_type="+hello+"sort_by=date_posted&sort_type=asc";	
				}
				new jobslist().execute(URL);
				adapter = new JobsListView(JobHuk_Main_Expview.this, R.layout.activity_jobslistview, list2);
			  	listview.setAdapter(adapter);
		        adapter.notifyDataSetChanged();
			break;
		case R.id.amount:
			finderfee_count++;
			if(finderfee_count%2==0)
			{
				String URL1 = "http://staging.jobhuk.com/api/jobs?sort_by=amount&sort_type=asc";
				new jobslist().execute(URL1);
				adapter = new JobsListView(JobHuk_Main_Expview.this, R.layout.activity_jobslistview, list2);
			  	listview.setAdapter(adapter);
		        adapter.notifyDataSetChanged();
			}
			else
			{
				String URL2 = "http://staging.jobhuk.com/api/jobs?sort_by=amount&sort_type=desc";
				new jobslist().execute(URL2);
				adapter = new JobsListView(JobHuk_Main_Expview.this, R.layout.activity_jobslistview, list2);
			  	listview.setAdapter(adapter);
		        adapter.notifyDataSetChanged();
			}
			break;
		
		case R.id.popup_close:
				layout1.setVisibility(View.VISIBLE);
				layout2.setVisibility(View.VISIBLE);
				listview.setVisibility(View.VISIBLE);
				layout3.setVisibility(View.VISIBLE);
			break;
		}
	}

}
