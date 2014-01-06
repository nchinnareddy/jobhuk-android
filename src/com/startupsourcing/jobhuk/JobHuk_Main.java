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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class JobHuk_Main extends Activity {
	
	String[] values = null;
	public ArrayList<String> Title = new ArrayList<String>();
	public ArrayList<String> Comp_name = new ArrayList<String>();
	public ArrayList<String> Location = new ArrayList<String>();
	public ArrayList<String> Job_type = new ArrayList<String>();
	public ArrayList<String> Contract_rate = new ArrayList<String>();
	public ArrayList<String> Duration_hours = new ArrayList<String>();
	public ArrayList<String> Description = new ArrayList<String>();
	public ArrayList<String> Finders_fee = new ArrayList<String>();
	
	ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobhuk_main);

//		String URL = "http://192.168.0.114:3000/api/jobs/";
		String URL = "http://staging.jobhuk.com/api/jobs";
//		String URL = "https://www.jobhuk.com/api/jobs";

		new jobslist().execute(URL);
		
		listview	= (ListView) findViewById(R.id.listview);
	
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
	            Log.i("Hello",content.toString());
	            
	            try {
					JSONObject jsonResponse = new JSONObject(content.toString());
					JSONObject resultObject = jsonResponse.getJSONObject("resultObject");
					jobs = resultObject.getJSONArray("jobs");
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
					
					hash.put("Title"+i,Title.get(i));
					hash.put("Comp_name"+i,Comp_name.get(i));
					hash.put("Location"+i, Location.get(i));
					hash.put("Job_type"+i, Job_type.get(i));
					hash.put("Duration_hours"+i,Duration_hours.get(i));
					hash.put("Contract_rate"+i,Contract_rate.get(i));
					
					list2.add(hash);
					
//					Log.i("Hello",result.get(i)+"   "+result.get(i)+"  "+result.get(i));
					} 
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			JobsListView adapter = new JobsListView(JobHuk_Main.this, R.layout.activity_jobslistview, list2);
		  	adapter.notifyDataSetChanged();
	        listview.setAdapter(adapter);
	        
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
}
