package com.example.airportstatus;

import java.util.ArrayList;
import java.util.Observable;

import android.os.Bundle;
import android.util.Log;

public class NetworkTaskCollection extends Observable {
	private Bundle result;
	private int completedTaskCount;
	private ArrayList<NetworkTask> myTasks;
	
	public NetworkTaskCollection() {
		result = new Bundle();
		myTasks = new ArrayList<NetworkTask>();
		completedTaskCount = 0;
	}
	
	public void addTask(NetworkTask task) {
		myTasks.add(task);
	}
	
	public void markTaskComplete() {
		completedTaskCount += 1;
	}
	
	public void startAll() {
		for (NetworkTask t : myTasks) {
			t.setHandler();
			t.execute();
		}
	}
	
	public void setResult(String key, String value) {
		this.result.putString(key, value);
	}
	
	public void checkTaskStatus() {
		Log.d("TASK", "Checking task status");
		boolean allComplete = false;
		if (this.completedTaskCount == myTasks.size()) {
			allComplete = true;
		}
		
		if (allComplete) {		
			myTasks.clear();
			Log.d("TASK", "All tasks have finished");
			this.result.putBoolean("success", true);
			setChanged();
			notifyObservers(this.result);
		}
	}
	
}
