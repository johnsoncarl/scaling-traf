package com.ujjwal.p2ptrial.location;

import androidx.annotation.NonNull;

public class PeerLocation {
    private String peerId;
    private double lat;
    private double log;
    private long lastUpdate;

    public PeerLocation () {}

    public PeerLocation (@NonNull String peerId, double lat, double log, long lastUpdate) throws IllegalArgumentException {
        if (peerId.equals(""))
            throw new IllegalArgumentException("PeerID \"" + peerId + "\" is invalid.");
        this.setPeerId(peerId);
        this.setLat(lat);
        this.setLog(log);
        this.setLastUpdate(lastUpdate);
    }

    @Override
    public String toString() {
        return "PeerLocation{" +
                "peerId='" + peerId + '\'' +
                ", lat=" + lat +
                ", log=" + log +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLog() {
        return log;
    }

    public void setLog(double log) {
        this.log = log;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
