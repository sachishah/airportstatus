package com.airportstatus.activities;

import android.content.Context;

public class AirportStatus extends com.activeandroid.app.Application {
	private static Context context;
	
    @Override
    public void onCreate() {
        super.onCreate();
        AirportStatus.context = this;
    }
}
