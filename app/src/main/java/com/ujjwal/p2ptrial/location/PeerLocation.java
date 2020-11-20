package com.ujjwal.p2ptrial.location;

public class PeerLocation {
    private String peerId;
    private double lat;
    private double log;
    private long lastUpdate;

    PeerLocation (String peerId, double lat, double log, long lastUpdate) throws RuntimeException {
        if (peerId.equals(""))
            throw new RuntimeException("PeerID \"" + peerId + "\" is invalid.");
        this.setPeerId(peerId);
        this.setLat(lat);
        this.setLog(log);
        this.setLastUpdate(lastUpdate);
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
