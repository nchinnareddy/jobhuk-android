package com.startupsourcing.jobhuk;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class myAdapter extends ArrayAdapter<Object>{
	
	private String[][] values;
	private Context context;
	
	public myAdapter(Context context, int textViewResourceId,
			  String[][] values2) {
	
		 super(context, textViewResourceId,values2);
		 this.values = values2;
		 this.context = context;
		// TODO Auto-generated constructor stub
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View row = convertView;
		if(row == null)
		{
			Log.i("Position.",""+position);
			LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
				      (Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.row,parent,false);
		}
		
		Log.i("Position",""+position);
		TextView tv1 = (TextView)row.findViewById(R.id.jobtitle);
		tv1.setText(values[position][0]);
		TextView tv2 = (TextView)row.findViewById(R.id.comp_name);
		tv2.setText(values[position][1]);
		TextView tv3 = (TextView)row.findViewById(R.id.place);
		tv3.setText(values[position][2]);
		TextView tv4 = (TextView)row.findViewById(R.id.emp_type);
		tv4.setText(values[position][3]);
		TextView tv5 = (TextView)row.findViewById(R.id.pay_hr);
		tv5.setText(values[position][4]);
		TextView tv6 = (TextView)row.findViewById(R.id.time);
		tv6.setText(values[position][5]);
		
		return row;
	
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		  return values.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}
