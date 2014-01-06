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
	
	 private static final int PICKFILE_RESULT_CODE = 1;
	 TextView result;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_refer);
		
		File listFile[] = android.os.Environment.getExternalStorageDirectory().listFiles();
		
		Button upload  = (Button) findViewById(R.id.upload);
		upload.setOnClickListener(this);
/*		
		if (listFile != null) 
			{
	        for (int i = 0; i < listFile.length; i++) 
	        	{
	        	Log.i("Result",listFile[i].getName());
	        	
	        	File localFile[] = listFile[i].listFiles();
	        	
	        	for(int j=0; j<localFile.length; j++)
	        	{
	        		Log.i("Result",localFile[j].getName());
		              if (localFile[j].getName().endsWith(".pdf"))
		              {
		                Log.i("Result",".pdf file found");   
		              }
	        	}       	
	            }
	        }*/
		
		Log.i("dir",""+listFile.length);
		}


		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId())
			{
			case R.id.upload:
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		        intent.setType("file/*");
		        startActivityForResult(intent,PICKFILE_RESULT_CODE);
				break;
			}
			
		}
		
		 @Override
		 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  // TODO Auto-generated method stub
			 super.onActivityResult(requestCode, resultCode, data);
			 if(requestCode == PICKFILE_RESULT_CODE )
			 {
			   if(resultCode==RESULT_OK){
			    String FilePath = data.getData().getPath();
			    Log.i("Result",FilePath);
			   }
		  }
		 }
}

