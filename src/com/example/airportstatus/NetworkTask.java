package com.example.airportstatus;

import com.loopj.android.http.JsonHttpResponseHandler;

public abstract class NetworkTask {
	public JsonHttpResponseHandler handler;
	
	public abstract void setHandler();
	
	public abstract void execute();
}
