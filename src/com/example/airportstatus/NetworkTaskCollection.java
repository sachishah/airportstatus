package com.example.airportstatus;

import java.util.ArrayList;
import java.util.Observable;
import android.os.AsyncTask;
import android.util.Log;

public class NetworkTaskCollection extends Observable {
	private int completedTaskCount;
	private ArrayList<AsyncTask> myTasks;
	
	public NetworkTaskCollection() {
		myTasks = new ArrayList<AsyncTask>();
		completedTaskCount = myTasks.size();
	}
	
	public void addTask(AsyncTask<Void, Void, Boolean> task) {
		myTasks.add(task);
	}
	
	public void markTaskComplete() {
		completedTaskCount += 1;
	}
	
	public void startAll() {
		for (AsyncTask<Void, Void, Boolean> t : myTasks) {
			t.execute();
		}
		
	}
	
	public void checkTaskStatus() {
		boolean allComplete = false;
		if (this.completedTaskCount == myTasks.size()) {
			allComplete = true;
		}
		
		if (allComplete) {
			myTasks.clear();
			Log.d("TASK", "All tasks have finished");
			setChanged();
			notifyObservers(new String("SWEET"));
		}
	}
}
