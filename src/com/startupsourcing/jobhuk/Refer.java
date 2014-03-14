package com.startupsourcing.jobhuk;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
	}
}

