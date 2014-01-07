package com.startupsourcing.jobhuk;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Refer extends Activity implements OnClickListener{

	Button fb,twitter,linkedin,gmail,upload,submit;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refer);
		
		fb = (Button) findViewById(R.id.fb);
		linkedin = (Button) findViewById(R.id.linked);
		twitter = (Button) findViewById(R.id.twitter);
		gmail = (Button) findViewById(R.id.google);
		upload = (Button) findViewById(R.id.upload);
		submit = (Button) findViewById(R.id.submit);
		
		fb.setOnClickListener(this);
		linkedin.setOnClickListener(this);
		twitter.setOnClickListener(this);
		gmail.setOnClickListener(this);
		upload.setOnClickListener(this);
		submit.setOnClickListener(this);
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.fb:
			Intent fb = new Intent(Refer.this,FB_Login.class);
			startActivity(fb);
			break;
		case R.id.linked:
			Intent linked = new Intent(Refer.this,LinkedIn_Login.class);
			startActivity(linked);
			break;
		case R.id.twitter:
			Intent twitter = new Intent(Refer.this,Twitter_Login.class);
			startActivity(twitter);
			break;
		case R.id.google:
			Intent google = new Intent(Refer.this,Gmail_Login.class);
			startActivity(google);
			break;
		case R.id.upload:
			break;
		case R.id.submit:
			break;
		}
	}
}

