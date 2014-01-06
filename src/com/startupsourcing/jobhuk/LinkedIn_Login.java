package com.startupsourcing.jobhuk;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class LinkedIn_Login extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_linked_in__login);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.linked_in__login, menu);
		return true;
	}

}
