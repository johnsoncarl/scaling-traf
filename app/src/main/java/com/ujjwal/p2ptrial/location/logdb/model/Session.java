package com.ujjwal.p2ptrial.location.logdb.model;

public class Session {
    private int _id;
    private String peer_id;
    private long invoke_ts;
    private long response_ts;

    public Session() {}

    @Override
    public String toString() {
        return "Session{" +
                "_id=" + get_id() +
                ", peer_id='" + getPeer_id() + '\'' +
                ", invoke_ts=" + getInvoke_ts() +
                ", response_ts=" + getResponse_ts() +
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

    public long getInvoke_ts() {
        return invoke_ts;
    }

    public void setInvoke_ts(long invoke_ts) {
        this.invoke_ts = invoke_ts;
    }

    public long getResponse_ts() {
        return response_ts;
    }

    public void setResponse_ts(long response_ts) {
        this.response_ts = response_ts;
    }
}
