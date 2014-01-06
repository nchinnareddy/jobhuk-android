package com.startupsourcing.jobhuk;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Twitter_Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter__login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.twitter__login, menu);
		return true;
	}

}
