package com.airportstatus.helpers;

import android.location.Location;

public abstract class LocationResult{
    public abstract void receivedLocation(Location location);
}