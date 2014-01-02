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

public class MainActivity_JobHuk extends Activity {
	
	String[] values = null;
	public ArrayList<String> Title = new ArrayList<String>();
	public ArrayList<String> Comp_name = new ArrayList<String>();
	public ArrayList<String> Location = new ArrayList<String>();
	public ArrayList<String> Job_type = new ArrayList<String>();
	public ArrayList<String> Contract_rate = new ArrayList<String>();
	public ArrayList<String> Duration_hours = new ArrayList<String>();
	public ArrayList<String> Description = new ArrayList<String>();
	public ArrayList<String> result = new ArrayList<String>();
	
	ListView listview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity__job_huk);

//		String URL = "http://192.168.0.114:3000/api/jobs/";
		String URL = "http://staging.jobhuk.com/api/jobs";
//		String URL = "http://jobhuk.com/api/jobs";

		new jobslist().execute(URL);
		
		listview	= (ListView) findViewById(R.id.listview);
	
	        listview.setOnItemClickListener(new OnItemClickListener() {
	        	  public void onItemClick(AdapterView<?> parent, View view,
	        	    int position, long id) {
/*	        		Toast.makeText(getApplicationContext(),
	        	      "Click ListItem Number " + values[position][0], Toast.LENGTH_SHORT)
	        	      .show();*/
	        		  Intent hello = new Intent(MainActivity_JobHuk.this,Hello.class);
	        		  hello.putExtra("Title", values[0]);
	        		  startActivity(hello);
	        	  }
	        	}); 
	}
	
	public class jobslist extends AsyncTask<String, Void, JSONArray>
	{
		String url;
		JSONArray jobs;
		ArrayList<String> list = new ArrayList<String>();
		ArrayList<HashMap> list2 = new ArrayList<HashMap>();
		@SuppressWarnings("rawtypes")
		HashMap hash[];
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
			hash = new HashMap[10] ;
			
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
					
					
					
					result.add(Title.get(i));
					result.add(Comp_name.get(i));
//					result.add(Description.get(i));
					result.add(Location.get(i));
					result.add(Job_type.get(i));
					result.add(Duration_hours.get(i));
					result.add(Contract_rate.get(i));
					
					
					hash[i].put("Title",Title.get(i));
					hash[i].put("Comp_name",Comp_name.get(i));
					hash[i].put("Location", Location.get(i));
					hash[i].put("Job_type", Job_type.get(i));
					hash[i].put("Duration_hours",Duration_hours.get(i));
					hash[i].put("Contract_rate",Contract_rate.get(i));
					
					list2.add(hash[i]);
					
//					Log.i("Hello",result.get(i)+"   "+result.get(i)+"  "+result.get(i));
					} 
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			myAdapter adapter = new myAdapter(MainActivity_JobHuk.this, R.layout.row, list2);
		  	adapter.notifyDataSetChanged();
	        listview.setAdapter(adapter);
	        
	        listview.setOnItemClickListener(new OnItemClickListener() {
	        	  public void onItemClick(AdapterView<?> parent, View view,
	        	    int position, long id) {
/*	        		Toast.makeText(getApplicationContext(),
	        	      "Click ListItem Number " + values[position][0], Toast.LENGTH_SHORT)
	        	      .show();*/
	        		  Intent hello = new Intent(MainActivity_JobHuk.this,Hello.class);
	        		  hello.putExtra("Title", result.get(position+0));
	        		  startActivity(hello);
	        	  }
	        	});
		}
		
	}
}
