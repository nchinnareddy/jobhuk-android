package com.startupsourcing.jobhuk;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class JobHuk_Main extends Activity implements OnClickListener {
	
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
	
	
	ListView listview;
	JobsListView adapter;
	Button prev,one,two,three,four,five,next,update,amount,job_type;
	int Pagenum,TotalPages,no_of_jobs,button_count;
	ArrayList<HashMap<String,String>> list2 = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> list3 = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> Fulltime = new ArrayList<HashMap<String,String>>();
	ArrayList<HashMap<String,String>> Contract = new ArrayList<HashMap<String,String>>();
	
	long Unix_time,Database_time;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobhuk_main);

//		String URL = "http://192.168.0.114:3000/api/jobs/";
		String URL = "http://staging.jobhuk.com/api/jobs";
//		String URL = "https://www.jobhuk.com/api/jobs";

		new jobslist().execute(URL);
		
		listview	= (ListView) findViewById(R.id.listview);
		
		prev = (Button) findViewById(R.id.prev);
		one = (Button) findViewById(R.id.one);
		two = (Button) findViewById(R.id.two);
		three = (Button) findViewById(R.id.three);
		four = (Button) findViewById(R.id.four);
		five = (Button) findViewById(R.id.five);
		next = (Button) findViewById(R.id.next);
		update = (Button) findViewById(R.id.update);
		amount = (Button) findViewById(R.id.amount);
		job_type = (Button) findViewById(R.id.type);
		
		prev.setOnClickListener(this);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		next.setOnClickListener(this);
		update.setOnClickListener(this);
		amount.setOnClickListener(this);
		job_type.setOnClickListener(this);
		
		prev.setEnabled(false);
	
	}
	
	public void show_Pagelist(int Pageno)
	{
		list3.removeAll(list3);
		
		Pagenum = Pageno;
		if(Pagenum<=TotalPages)
		{
			if(Pagenum==1)
				prev.setEnabled(false);
			else if(Pagenum==TotalPages)
				next.setEnabled(false);
			else
			{
				prev.setEnabled(true);
				next.setEnabled(true);
			}
//			Log.i("If...Pagenum",""+Pagenum);
			String URL = "http://staging.jobhuk.com/api/jobs?page="+Pagenum+"";
			new jobslist().execute(URL);
		}
		
		else
		{
			Log.i("Else...Pagenum",""+Pagenum);
			String URL = "http://staging.jobhuk.com/api/jobs";
    		Toast.makeText(getApplicationContext(),
	        	      "Last page has been reached ", Toast.LENGTH_SHORT).show();
			new jobslist().execute(URL);
		}
	}
	
	public class jobslist extends AsyncTask<String, Void, JSONArray>
	{
		String url;
		JSONArray jobs;
		ArrayList<String> list = new ArrayList<String>();
//		ArrayList<HashMap<String,String>> list2 = new ArrayList<HashMap<String,String>>();
//		ArrayList<HashMap<String,String>> list3 = new ArrayList<HashMap<String,String>>();
		HashMap<String,String> hash;
		HttpResponse response;
		HttpClient httpClient;
		
		@Override
		protected JSONArray doInBackground(String... params) {
			 HttpClient client = new DefaultHttpClient();
			 HttpGet httpGet = new HttpGet(params[0]);
			 
			 try {
				HttpResponse response = client.execute(httpGet);
				HttpEntity entity = response.getEntity();
	            Object content = EntityUtils.toString(entity);
//	            Log.i("Hello",content.toString());
	            
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
					
					if(jobs_Sub.getString("job_type").equals(Fulltime))
					{
//						Log.i("Full","hello");
						fulltime.put("Title"+i,jobs_Sub.getString("title"));
						fulltime.put("Comp_name"+i,jobs_Sub.getString("company_name"));
						fulltime.put("Location"+i, jobs_Sub.getString("location"));
						fulltime.put("Job_type"+i, jobs_Sub.getString("job_type"));
						fulltime.put("Duration_hours"+i,jobs_Sub.getString("duration_hours"));
						fulltime.put("Contract_rate"+i,jobs_Sub.getString("contract_rate"));
//						fulltime.put("Posted_ago"+i, posted_ago);
					}
					else
					{
//						Log.i("Contract","hello");
						contract.put("Title"+i,jobs_Sub.getString("title"));
						contract.put("Comp_name"+i,jobs_Sub.getString("company_name"));
						contract.put("Location"+i, jobs_Sub.getString("location"));
						contract.put("Job_type"+i, jobs_Sub.getString("job_type"));
						contract.put("Duration_hours"+i,jobs_Sub.getString("duration_hours"));
						contract.put("Contract_rate"+i,jobs_Sub.getString("contract_rate"));
//						contract.put("Posted_ago"+i, posted_ago);
					}
					
					
					String str= jobs_Sub.getString("updated_at");
					String str1[] = str.split("T");
					Log.i("Date",str1[0]);
					String str2[] = str1[1].split("-");
					Log.i("Time",str2[0]);
					
					Timestamp timestamp = Timestamp.valueOf(str1[0] + " " +str2[0]+ ".000");
					Database_time = timestamp.getTime()/1000L;
					long diff = Unix_time - Database_time;
					int No_of_days = (int) diff/86400;
					
					if(No_of_days>=30&&No_of_days<60)
					{
						posted_ago = "posted about 1 month ago" ;
					}
					else if(No_of_days>=60&&No_of_days<90)
					{
						posted_ago = "posted about 2 months ago" ;
					}
					else if(No_of_days>=90&&No_of_days<120)
					{
						posted_ago = "posted about 3 months ago" ;
					}
					else if(No_of_days>=120&&No_of_days<150)
					{
						posted_ago = "posted about 4 months ago" ;
					}
					else if(No_of_days>=150&&No_of_days<180)
					{
						posted_ago = "posted about 5 months ago" ;
					}
					else if(No_of_days>=180&&No_of_days<=210)
					{
						posted_ago = "posted about 6 months ago" ;
					}
					else if(No_of_days>=210&&No_of_days<240)
					{
						posted_ago = "posted about 7 months ago" ;
					}
					else if(No_of_days>=240&&No_of_days<270)
					{
						posted_ago = "posted about 8 month ago" ;
					}
					else if(No_of_days>=270&&No_of_days<300)
					{
						posted_ago = "posted about 9 months ago" ;
					}
					else if(No_of_days>=300&&No_of_days<330)
					{
						posted_ago = "posted about 10 months ago" ;
					}
					else if(No_of_days>=330&&No_of_days<360)
					{
						posted_ago = "posted about 11 months ago" ;
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
					hash.put("Contract_rate"+i,jobs_Sub.getString("contract_rate"));
					hash.put("Posted_ago"+i, posted_ago);
					
					list2.add(hash);
					Fulltime.add(fulltime);
					Contract.add(contract);
					
					} 
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			int j,k;
			for(j=list2.size()-1,k=0;j>=0&&k<list2.size();j--,k++)
			{
				HashMap<String,String> map = list2.get(j);
				HashMap<String,String> map1 = new HashMap<String,String>();
				
				map1.put("Title"+k, map.get("Title"+j));
				map1.put("Comp_name"+k,map.get("Comp_name"+j));
				map1.put("Location"+k, map.get("Location"+j));
				map1.put("Job_type"+k, map.get("Job_type"+j));
				map1.put("Duration_hours"+k,map.get("Duration_hours"+j));
				map1.put("Contract_rate"+k,map.get("Contract_rate"+j));
				map1.put("Posted_ago"+k,map.get("Posted_ago"+j));
				
				list3.add(map1);
			}

			adapter = new JobsListView(JobHuk_Main.this, R.layout.activity_jobslistview, list2);
		  	listview.setAdapter(adapter);
	        adapter.notifyDataSetChanged();
	        
	        listview.setOnItemClickListener(new OnItemClickListener() {
	        	  public void onItemClick(AdapterView<?> parent, View view,
	        	    int position, long id) {
/*	        		Toast.makeText(getApplicationContext(),
	        	      "Click ListItem Number " + position, Toast.LENGTH_SHORT)
	        	      .show();*/
	        		  Intent hello = new Intent(JobHuk_Main.this,JobsDescription.class);
	        		  hello.putExtra("Title", Title.get(position));
	        		  hello.putExtra("Comp_name", Comp_name.get(position));
	        		  hello.putExtra("Location", Location.get(position));
	        		  hello.putExtra("Job_type", Job_type.get(position));
	        		  hello.putExtra("Duration_hours", Duration_hours.get(position));
	        		  hello.putExtra("Contract_rate", Contract_rate.get(position));
	        		  hello.putExtra("Description", Description.get(position));
	        		  hello.putExtra("Finders_fee",Finders_fee.get(position));
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
		case R.id.prev:
			int prev = Pagenum-1;
			show_Pagelist(prev);
			break;
		case R.id.one:
			show_Pagelist(Integer.parseInt(one.getText().toString()));
			break;
		case R.id.two:
			show_Pagelist(Integer.parseInt(two.getText().toString()));
			break;
		case R.id.three:
			show_Pagelist(Integer.parseInt(three.getText().toString()));
			break;
		case R.id.four:
			show_Pagelist(Integer.parseInt(four.getText().toString()));
			break;
		case R.id.five:
			show_Pagelist(Integer.parseInt(five.getText().toString()));
			break;
		case R.id.next:
			int next = Pagenum+1;
			show_Pagelist(next);
			break;	
		case R.id.update:
			button_count++;
			Log.i("Time",""+(int) (System.currentTimeMillis() / 1000L));
			if(button_count%2==0)
			{
				adapter = new JobsListView(JobHuk_Main.this, R.layout.activity_jobslistview, list3);
			  	listview.setAdapter(adapter);
		        adapter.notifyDataSetChanged();
			}
			else
			{
				adapter = new JobsListView(JobHuk_Main.this, R.layout.activity_jobslistview, list2);
			  	listview.setAdapter(adapter);
		        adapter.notifyDataSetChanged();
			}
			break;
		case R.id.amount:
			adapter = new JobsListView(JobHuk_Main.this, R.layout.activity_jobslistview, list3);
		  	listview.setAdapter(adapter);
	        adapter.notifyDataSetChanged();
			break;
		case R.id.type:
			adapter = new JobsListView(JobHuk_Main.this, R.layout.activity_jobslistview, Contract);
		  	listview.setAdapter(adapter);
	        adapter.notifyDataSetChanged();
			break;
		}
	}
}
