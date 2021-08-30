package com.ujjwal.p2ptrial.location.logdb.model;

public class ErrorLog {
    private int _id;
    private String peer_id;
    private String err;

    public ErrorLog() {}

    @Override
    public String toString() {
        return "Error{" +
                "_id=" + get_id() +
                ", peer_id='" + getPeer_id() + '\'' +
                ", err='" + getErr() + '\'' +
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

    public String getErr() {
        return err;
    }

    public void setErr(String err) {
        this.err = err;
    }
}
