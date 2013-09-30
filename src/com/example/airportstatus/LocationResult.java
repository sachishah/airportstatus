package com.example.airportstatus;

import android.location.Location;

public abstract class LocationResult{
    public abstract void receivedLocation(Location location);
}