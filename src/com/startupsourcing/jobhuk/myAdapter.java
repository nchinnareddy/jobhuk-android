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

public class myAdapter extends ArrayAdapter<Object>{
	
	private String[] values2;
	private Context context;
	ArrayList<HashMap> values;
	
	public myAdapter(Context context, int textViewResourceId,
			  ArrayList<HashMap> list) {
	
		 super(context, textViewResourceId);
		 values = list;
		 this.context = context;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		if(row == null)
		{
//			Log.i("Position.",""+position);
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.row,parent,false);
		}

		Log.i("Position",""+position);
		HashMap<String,String> map = values.get(position);
		TextView tv1 = (TextView)row.findViewById(R.id.jobtitle);
		tv1.setText(map.get("Title"));
		TextView tv2 = (TextView)row.findViewById(R.id.comp_name);
		tv2.setText(map.get("Comp_name"));
		TextView tv3 = (TextView)row.findViewById(R.id.place);
		tv3.setText(map.get("Location"));
		TextView tv4 = (TextView)row.findViewById(R.id.emp_type);
		tv4.setText(map.get("Job_type"));
		TextView tv5 = (TextView)row.findViewById(R.id.pay_hr);
		tv5.setText(map.get("Duration_hours"));
		TextView tv6 = (TextView)row.findViewById(R.id.time);
		tv6.setText(map.get("Contract_rate"));
		
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
