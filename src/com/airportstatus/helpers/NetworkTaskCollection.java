package com.airportstatus.helpers;

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
	
	public void startAll() {
		for (NetworkTask t : myTasks) {
			t.setHandler();
			t.execute();
		}
	}
	
	public void addResult(String key, String value) {
		this.setResult(key, value);
	} 
	
	/**
	 * Handler for an unsuccessful response,
	 * like in an exception handler or an onFailure method.
	 * 
	 * Use this method to finish a task in the queue when you have
	 * not received any usable data.
	 * 
	 * This method completes the currently finishing task,
	 * and kicks off a call to see if all other tasks are done yet.
	 * 
	 * Either this method OR finishWithResult must be called for every task
	 * in order for the waiting activity to trigger the search result activity.
	 */
	public void finishOneTask() {
		this.markTaskComplete();
		this.checkTaskStatus();
	}
	
	/**
	 * Handler for a successful response that returns data.
	 * This method sets the response data into the bundle of all results,
	 * completes the currently finishing task,
	 * and kicks off a call to see if all other tasks are done yet.
	 * 
	 * @param key
	 * @param value
	 */
	public void finishWithResult(String key, String value) {
		this.setResult(key, value);
		this.markTaskComplete();
		this.checkTaskStatus();
	}

	/**
	 * Indicates that a task is finished. 
	 * Don't call it directly. Use
	 * finishOneTask or finishWithResult.
	 */
	private void markTaskComplete() {
		completedTaskCount += 1;
	}
	
	/**
	 * Wrapper method to put new results into the result bundle.
	 * @param key The key that should go into the result. This will be used by later activities.
	 * @param value The result value.
	 */
	private void setResult(String key, String value) {
		this.result.putString(key, value);
	}
	
	/**
	 * This method is run whenever a task is finished.
	 * It checks to see whether any other unfinished tasks are left
	 * (if the count of finished tasks matches all tasks)
	 * and if all have finished, sends a notification to the calling activity
	 * along with all result data in a Bundle.
	 */
	private void checkTaskStatus() {
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
