package com.startupsourcing.jobhuk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
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

public class MainActivity_JobHuk extends Activity {
	
	String[][] values = null;
	public ArrayList<String> Title = new ArrayList<String>();
	public ArrayList<String> Comp_name = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity__job_huk);
		
		ListView listview = (ListView) findViewById(R.id.listview);
		
//		String URL = "http://192.168.0.114:3000/api/jobs/";
		String URL = "http://www.jobhuk.com/api/jobs";

			new jobslist().execute(URL);
		
		final String[][] values = new String[][]{{"iOS Developer","CDAC","Delhi","Permanent","$90/hr","12 Months"},{"Android Developer","HatchStation","Hyd","Contract"," $56/hr"," 5 Months "},
				{"C++ Developer","Startup Sourcing","Hyd","Contract"," $60/hr","6 Months"}};
		
		Log.i("Hello",""+Title.size());
		
        for(int i=0;i<Title.size();i++)
        {
        	 Log.i("Hello",Title.get(i)+"    "+Comp_name.get(i));
        }
		
		myAdapter adapter = new myAdapter(MainActivity_JobHuk.this, R.layout.row, values);
		  	adapter.notifyDataSetChanged();
	        listview.setAdapter(adapter);
   	

	        
	        listview.setOnItemClickListener(new OnItemClickListener() {
	        	  public void onItemClick(AdapterView<?> parent, View view,
	        	    int position, long id) {
/*	        		Toast.makeText(getApplicationContext(),
	        	      "Click ListItem Number " + values[position][0], Toast.LENGTH_SHORT)
	        	      .show();*/
	        		  Intent hello = new Intent(MainActivity_JobHuk.this,Hello.class);
	        		  hello.putExtra("Title", values[position][0]);
	        		  startActivity(hello);
	        	  }
	        	}); 
	}
	
	public class jobslist extends AsyncTask<String, Void, JSONArray>
	{
		String url;
		JSONArray jobs;
		ArrayList<String> list = new ArrayList<String>();
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
			for(int i=0;i<jobs.length();i++)
			{

				JSONObject jobs_Sub;
				try {
					jobs_Sub = jobs.getJSONObject(i);
					Title.add(jobs_Sub.getString("title"));
					Comp_name.add(jobs_Sub.getString("company_name"));
					Log.i("Hello",Title.get(i)+"    "+Comp_name.get(i));
					} 
				catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
}
