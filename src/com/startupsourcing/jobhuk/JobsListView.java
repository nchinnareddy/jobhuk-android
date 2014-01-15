package com.startupsourcing.jobhuk;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class JobsListView extends ArrayAdapter<Object>{

	ArrayList<HashMap<String,String>> values;
	
	public JobsListView(Context context, int textViewResourceId,
			  ArrayList<HashMap<String,String>> list) {
	
		 super(context, textViewResourceId);
		 values = list;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
//		Log.i("Hello","Call from Update");
		View row = convertView;
		if(row == null)
		{
//			Log.i("Position.",""+position);
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.activity_jobslistview,parent,false);
		}

		HashMap<String,String> map = values.get(position);
		if(map.get("Title"+position)!=null)
		{
			TextView tv1 = (TextView)row.findViewById(R.id.jobtitle);
			tv1.setText(map.get("Title"+position));
			TextView tv2 = (TextView)row.findViewById(R.id.comp_name);
			tv2.setText(map.get("Comp_name"+position));
			TextView tv3 = (TextView)row.findViewById(R.id.place);
			tv3.setText(map.get("Location"+position));
			TextView tv4 = (TextView)row.findViewById(R.id.emp_type);
			tv4.setText(map.get("Job_type"+position));
			TextView tv5 = (TextView)row.findViewById(R.id.pay_hr);
			tv5.setText(map.get("Duration_hours"+position)+" hrs");
			TextView tv6 = (TextView)row.findViewById(R.id.time);

			int amount =(int) Double.parseDouble(map.get("Finders_fee"+position));
			tv6.setText("$ "+amount);
			
			TextView tv7 = (TextView)row.findViewById(R.id.posted);
			tv7.setText(map.get("Posted_ago"+position));
			
		}
		return row;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		  return values.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return values.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
