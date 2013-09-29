package com.example.airportstatus;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.airportstatus.models.Checkpoint;
import com.example.airportstatus.models.WaitTime;



public class WaitTimeAdapter extends ArrayAdapter<WaitTime>{
	ArrayList<Checkpoint> checkpoints;
	
	public WaitTimeAdapter(Context context, List<WaitTime> waitTimes, ArrayList<Checkpoint> gates) {
		super(context, 0, waitTimes);
		checkpoints = gates;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (view == null) {
	    	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	view = inflater.inflate(R.layout.gate_item, null);
	    }
	     
        final WaitTime waitTime = getItem(position);
        
        
        Integer myIndex = waitTime.getCheckpoint();
        if (myIndex >= 0 && myIndex < checkpoints.size()){
        	Checkpoint myCheckpoint = checkpoints.get(myIndex);
        	if (myCheckpoint != null){
        		String myName = myCheckpoint.getLongName();
        		TextView gateView = (TextView) view.findViewById(R.id.tvCheckpoint);
        		String formattedName = "<small>" + myName + "</small>";
                gateView.setText(Html.fromHtml(formattedName));
                
                TextView waitTimeView = (TextView) view.findViewById(R.id.tvWaitTime);
                waitTimeView.setText(Html.fromHtml(waitTime.getWaitTime()));
                
                TextView timeView = (TextView) view.findViewById(R.id.tvTimestamp);
                String formattedTime = " <small><font color='#777777'> Posted on " + waitTime.getTimestamp() +
                		"</font></small>";
                timeView.setText(Html.fromHtml(formattedTime));
        		
        		if (myName == null) {
        			Log.d("DEBUG", "myname is null");
        			view.setVisibility(View.GONE);
        		}
        	} else {
        		Log.d("DEBUG", "checkpoint is null");
        		view.setVisibility(View.GONE);
        	}
        } else {
        	Log.d("DEBUG", "index is incorrect "+ myIndex);
        	
        	
        }
        
        
        return view;
	}

}
