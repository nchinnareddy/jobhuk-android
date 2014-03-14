package com.startupsourcing.jobhuk;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class listAdapter extends ArrayAdapter<Object> {
	
    private Context context;
    ArrayList<String> values;

	public listAdapter(Context context, int drawerListItem, ArrayList<String> values) {
		// TODO Auto-generated constructor stub
		super(context, drawerListItem);
		this.context = context;
        this.values = values;
	}

    @Override
    public int getCount() {
        return values.size();
    }
 
    @Override
    public Object getItem(int position) {       
        return values.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }

        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);    
        txtTitle.setText(values.get(position));
        txtTitle.setTextColor(Color.BLUE);
         
        return convertView;
    }
 }
