package com.example.airportstatus;

import java.util.ArrayList;
import java.util.Observable;
import android.os.AsyncTask;
import android.util.Log;

public class NetworkTaskCollection extends Observable {
	private ArrayList<AsyncTask> myTasks;
	public NetworkTaskCollection() {
		myTasks = new ArrayList<AsyncTask>();
	}
	
	public void addTask(AsyncTask<Void, Void, Boolean> task) {
		myTasks.add(task);
		task.execute();
	}
	
	public void checkTaskStatus() {
		boolean allComplete = true;	
		for (AsyncTask<Void, Void, Boolean> task : myTasks) {
			if (!task.getStatus().equals(AsyncTask.Status.FINISHED)) {
				allComplete = false;
				break;
			}
		}
		if (allComplete) {
			myTasks.clear();
			Log.d("TASK", "All tasks have finished");
			notifyObservers("SWEET");
		}
	}
}
