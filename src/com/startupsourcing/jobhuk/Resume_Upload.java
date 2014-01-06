package com.startupsourcing.jobhuk;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

public class Resume_Upload extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resume__upload);
		
		File dir = Environment.getExternalStorageDirectory();
	}

}
