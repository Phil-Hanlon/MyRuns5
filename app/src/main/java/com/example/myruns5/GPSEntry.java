package com.example.myruns5;

import com.google.android.gms.maps.model.Marker;

public class GPSEntry {

    private int duration;
    private Marker startMarker;
    private Marker currentMarker;

    public GPSEntry(int duration, Marker startMarker, Marker currentMarker) {

        this.duration = duration;
        this.startMarker = startMarker;
        this.currentMarker = currentMarker;
    }

    public void setStartMarker(Marker startMarker) {
        this.startMarker = startMarker;
    }

    public int[] getStartMarkerCoords() {

        int[] coords = new int[2];

        coords[0] = (int)startMarker.getPosition().latitude;
        coords[1] = (int)startMarker.getPosition().longitude;

        return coords;
    }

    public void setCurrentMarker(Marker currentMarker) {
        this.currentMarker = currentMarker;
    }

    public int[] getCurrentMarkerCoords() {

        int[] coords = new int[2];

        coords[0] = (int)currentMarker.getPosition().latitude;
        coords[1] = (int)currentMarker.getPosition().longitude;

        return coords;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }
}
