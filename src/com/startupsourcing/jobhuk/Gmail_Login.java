package com.startupsourcing.jobhuk;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Gmail_Login extends Activity implements OnClickListener{
	
	HttpPost httpPost;
	HttpClient httpClient;
	HttpResponse response;
	String URL;
	EditText email_edit, password_edit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gmail_login);
		
		httpPost = new HttpPost("http://staging.jobhuk.com/api/users/authentication");
		
		TextView email = (TextView) findViewById(R.id.email);
		TextView password = (TextView) findViewById(R.id.password);
		
		email_edit = (EditText) findViewById(R.id.email_edit);
		password_edit = (EditText) findViewById(R.id.password_edit);
		
		Button submit = (Button) findViewById(R.id.submit);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{ 
		case R.id.submit:
			List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(3);
			nameValuePair.add(new BasicNameValuePair("email",email_edit.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("password",password_edit.getText().toString()));
			nameValuePair.add(new BasicNameValuePair("login_type","direct"));
			
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
				new loginprocess().execute();
				email_edit.setText("");
				password_edit.setText("");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	
	public class loginprocess extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
//			String Url = params[0];
			httpClient = new DefaultHttpClient();
			try {
				response = httpClient.execute(httpPost);
				HttpEntity entity = response.getEntity();
				Object content = EntityUtils.toString(entity);
				JSONObject jsonResponse;
				try {
					jsonResponse = new JSONObject(content.toString());
					String result = jsonResponse.getString("successful");
					if(result.equals("false"))
					{
						Log.i("Result","Login Failed");
					}
					else
					{
						Log.i("Result","Login Success");
						Intent resume = new Intent(Gmail_Login.this,FB_Login.class);
						startActivity(resume);
					}
						
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
			return null;
		}
		
	}

}
