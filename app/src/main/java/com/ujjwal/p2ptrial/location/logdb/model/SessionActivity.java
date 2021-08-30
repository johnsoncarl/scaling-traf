package com.ujjwal.p2ptrial.location.logdb.model;

public class SessionActivity {
    private int _id;
    private String peer_id;
    private long go_ts;
    private long java_ts;
    private double lat;
    private double log;
    private long peer_ts;
    private double speed;
    private String loc_provider;
    private double accuracy;
    private long fix_ts;

    public SessionActivity() {}

    @Override
    public String toString() {
        return "SessionActivity{" +
                "_id=" + get_id() +
                ", peer_id='" + getPeer_id() + '\'' +
                ", go_ts=" + getGo_ts() +
                ", java_ts=" + getJava_ts() +
                ", lat=" + getLat() +
                ", log=" + getLog() +
                ", peer_ts=" + getPeer_ts() +
                ", speed=" + getSpeed() +
                ", loc_provider=" + getLoc_provider() +
                ", accuracy=" + getAccuracy() +
                ", fix_ts=" + getFix_ts() +
                '}';
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getPeer_id() {
        return peer_id;
    }

    public void setPeer_id(String peer_id) {
        this.peer_id = peer_id;
    }

    public long getGo_ts() {
        return go_ts;
    }

    public void setGo_ts(long go_ts) {
        this.go_ts = go_ts;
    }

    public long getJava_ts() {
        return java_ts;
    }

    public void setJava_ts(long java_ts) {
        this.java_ts = java_ts;
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

    public long getPeer_ts() {
        return peer_ts;
    }

    public void setPeer_ts(long peer_ts) {
        this.peer_ts = peer_ts;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getLoc_provider() {
        return loc_provider;
    }

    public void setLoc_provider(String loc_provider) {
        this.loc_provider = loc_provider;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public long getFix_ts() {
        return fix_ts;
    }

    public void setFix_ts(long fix_ts) {
        this.fix_ts = fix_ts;
    }
}
