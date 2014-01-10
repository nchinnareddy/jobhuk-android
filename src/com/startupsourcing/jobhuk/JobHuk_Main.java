package com.startupsourcing.jobhuk;

import java.io.IOException;
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
import android.widget.TextView;
import android.widget.Toast;

public class JobHuk_Main extends Activity implements OnClickListener {
	
	String[] values = null;
	String jobs_count;
	public ArrayList<String> Title = new ArrayList<String>();
	public ArrayList<String> Comp_name = new ArrayList<String>();
	public ArrayList<String> Location = new ArrayList<String>();
	public ArrayList<String> Job_type = new ArrayList<String>();
	public ArrayList<String> Contract_rate = new ArrayList<String>();
	public ArrayList<String> Duration_hours = new ArrayList<String>();
	public ArrayList<String> Description = new ArrayList<String>();
	public ArrayList<String> Finders_fee = new ArrayList<String>();
	
	ListView listview;
	Button prev,one,two,three,four,five,next;
	int Pagenum,TotalPages,no_of_jobs;
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
		
		prev.setOnClickListener(this);
		one.setOnClickListener(this);
		two.setOnClickListener(this);
		three.setOnClickListener(this);
		four.setOnClickListener(this);
		five.setOnClickListener(this);
		next.setOnClickListener(this);
	
	}
	
	public void show_Pagelist(int Pageno)
	{
		Pagenum = Pageno;
		if(Pagenum<TotalPages)
		{
			if(Pagenum==1)
				prev.setVisibility(View.GONE);
			else
				prev.setVisibility(View.VISIBLE);
			
			Log.i("If...Pagenum",""+Pagenum);
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
		ArrayList<HashMap<String,String>> list2 = new ArrayList<HashMap<String,String>>();
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
			
			hash = new HashMap<String,String>() ;
			list2.removeAll(list2);
			
			for(int i=0;i<jobs.length();i++)
			{

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

					hash.put("Title"+i,jobs_Sub.getString("title"));
					hash.put("Comp_name"+i,jobs_Sub.getString("company_name"));
					hash.put("Location"+i, jobs_Sub.getString("location"));
					hash.put("Job_type"+i, jobs_Sub.getString("job_type"));
					hash.put("Duration_hours"+i,jobs_Sub.getString("duration_hours"));
					hash.put("Contract_rate"+i,jobs_Sub.getString("contract_rate"));
					
					list2.add(hash);

					} 
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			JobsListView adapter = new JobsListView(JobHuk_Main.this, R.layout.activity_jobslistview, list2);
		  	listview.setAdapter(adapter);
		  	
	        adapter.notifyDataSetChanged();
	        
	        listview.setOnItemClickListener(new OnItemClickListener() {
	        	  public void onItemClick(AdapterView<?> parent, View view,
	        	    int position, long id) {
	        		Toast.makeText(getApplicationContext(),
	        	      "Click ListItem Number " + position, Toast.LENGTH_SHORT)
	        	      .show();
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
	
	public class Pair
	{
	    public int Pages;
	    public int[] Jobs;
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
		}
	}
}
