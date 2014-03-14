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
		final ViewHolder holder;

		if(row == null)
		{
			Log.i("Position.",""+position);
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.activity_jobslistview,null);
			holder = new ViewHolder();
			
			holder.tv1 = (TextView)row.findViewById(R.id.jobtitle);
			holder.tv2 = (TextView)row.findViewById(R.id.comp_name);
			holder.tv3 = (TextView)row.findViewById(R.id.place);
			holder.tv4 = (TextView)row.findViewById(R.id.emp_type);
			holder.tv5 = (TextView)row.findViewById(R.id.pay_hr);
			holder.tv6 = (TextView)row.findViewById(R.id.time);
			holder.tv7 = (TextView)row.findViewById(R.id.posted);
			
			row.setTag(holder);
		}
		
		else {
			holder = (ViewHolder) convertView.getTag();
		}


		HashMap<String,String> map = values.get(position);
		if(map.get("Title"+position)!=null)
		{
			Log.i("hello",map.get("Title"+position));
			
			holder.tv1.setText(map.get("Title"+position));
			holder.tv2.setText(map.get("Comp_name"+position));
			holder.tv3.setText(map.get("Location"+position));
			holder.tv4.setText(map.get("Job_type"+position));
			holder.tv5.setText("$ "+map.get("Compensation"+position));

			int amount =(int) Double.parseDouble(map.get("Finders_fee"+position));
			holder.tv6.setText("$ "+amount);

			holder.tv7.setText(map.get("Posted_ago"+position));
			
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
		return position;
	}
	
	public class ViewHolder
	{
		private TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7;
	}


}
