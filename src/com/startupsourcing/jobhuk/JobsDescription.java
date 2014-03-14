package com.startupsourcing.jobhuk;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JobsDescription extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jobsdescription);
		
		Intent intent = getIntent();
		String Title = intent.getStringExtra("Title");
		String Comp_name = intent.getStringExtra("Comp_name");
		String Job_type = intent.getStringExtra("Job_type");
		String Duration_hours = intent.getStringExtra("Duration_hours");
		String Description = intent.getStringExtra("Description");
		String Location = intent.getStringExtra("Location");
		String Contract_rate = intent.getStringExtra("Contract_rate");
		String Finders_fee = intent.getStringExtra("Finders_fee");
		String Compensation = intent.getStringExtra("Compensation");
		
		double foo =Double.parseDouble(Finders_fee);
		int x = (int)foo;
		
		TextView tv1 = (TextView)findViewById(R.id.jobtitle);
		TextView tv2 = (TextView)findViewById(R.id.comp_name);
		TextView tv3 = (TextView)findViewById(R.id.place);
		TextView tv4 = (TextView)findViewById(R.id.emp_type);
		TextView tv5 = (TextView)findViewById(R.id.time);
		TextView tv6 = (TextView)findViewById(R.id.pay_hr);
		TextView tv7 = (TextView)findViewById(R.id.description);
		
		Button b1 = (Button) findViewById(R.id.b1);
		Button b2 = (Button) findViewById(R.id.b2);
		
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		
		tv1.setText(Title);
		tv2.setText(Comp_name);
		tv3.setText(Location);
		tv4.setText(Job_type);
		tv5.setText(Duration_hours);
		tv6.setText(""+Contract_rate);
		tv7.setText(Html.fromHtml(Description));
		
		b1.setText("$"+x);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.b1:
			Intent refer = new Intent(JobsDescription.this,Refer.class);
			startActivity(refer);
			break;
		case R.id.b2:
			Intent refer1 = new Intent(JobsDescription.this,Refer.class);
			startActivity(refer1);
			break;
		}
	}
	}


