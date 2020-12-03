package com.ujjwal.p2ptrial.location.logdb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;

import com.ujjwal.p2ptrial.location.logdb.LogDatabase.*;
import com.ujjwal.p2ptrial.location.logdb.model.ErrorLog;
import com.ujjwal.p2ptrial.location.logdb.model.Session;
import com.ujjwal.p2ptrial.location.logdb.model.SessionActivity;

public class LogDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "p2p_log.db";

    public LogDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        // TODO: check schema, take backup before creating new table
        db.execSQL("CREATE TABLE IF NOT EXISTS " + Sessions.TABLE_NAME + " (" +
                Sessions._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Sessions.COLUMN_1 + " CHAR(46), " +
                Sessions.COLUMN_2 + " INTEGER NOT NULL, " +
                Sessions.COLUMN_3 + " INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SessionActivities.TABLE_NAME + " (" +
                SessionActivities._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                SessionActivities.COLUMN_0 + " CHAR(46), " +
                SessionActivities.COLUMN_1 + " INTEGER NOT NULL, " +
                SessionActivities.COLUMN_2 + " INTEGER NOT NULL, " +
                SessionActivities.COLUMN_4 + " NUMERIC, " +
                SessionActivities.COLUMN_5 + " NUMERIC, " +
                SessionActivities.COLUMN_6 + " INTEGER NOT NULL, " +
                SessionActivities.COLUMN_7 + " NUMERIC, " +
                SessionActivities.COLUMN_8 + " CHAR(20), " +
                SessionActivities.COLUMN_9 + " NUMERIC, " +
                SessionActivities.COLUMN_10 + " INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + Errors.TABLE_NAME + " (" +
                Errors._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Errors.COLUMN_1 + " CHAR(46), " +
                Errors.COLUMN_2 + " BLOB NOT NULL)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private boolean createSessionInstance() {
        // TODO: check schema, take backup before creating new table
        return true;
    }

    private boolean createSession() {
        // TODO: check schema, take backup before creating new table
        return true;
    }

    private boolean createErrors() {
        // TODO: check schema, take backup before creating new table
        return true;
    }

    public boolean insert(SQLiteDatabase db, @NonNull Session... sessions) {
        boolean res = true;
        for (Session s :
                sessions) {
            if (!insertSession(db, s))
                res = false;
        }
        return res;
    }

    public boolean insert(SQLiteDatabase db, @NonNull SessionActivity... activities) {
        boolean res = true;
        for (SessionActivity s :
                activities) {
            if (!insertSessionInstance(db, s))
                res = false;
        }
        return res;
    }

    public boolean insert(SQLiteDatabase db, @NonNull ErrorLog... errorLogs) {
        boolean res = true;
        for (ErrorLog e :
                errorLogs) {
            if (!insertError(db, e))
                res = false;
        }
        return res;
    }

    private boolean insertSession(SQLiteDatabase db, Session session) {
        db.execSQL("INSERT INTO " + Sessions.TABLE_NAME + " (" +
                Sessions.COLUMN_1 + ", " + Sessions.COLUMN_2 + ", " + Sessions.COLUMN_3 + ") VALUES('" +
                session.getPeer_id() + "', " + session.getInvoke_ts() + ", " + session.getResponse_ts() + ")");
        return true;
    }

    private boolean insertSessionInstance(SQLiteDatabase db, SessionActivity activity) {
        db.execSQL("INSERT INTO " + SessionActivities.TABLE_NAME + " (" +
                SessionActivities.COLUMN_0 + ", " + SessionActivities.COLUMN_1 + ", " +
                SessionActivities.COLUMN_2 + ", " + SessionActivities.COLUMN_4 + ", " +
                SessionActivities.COLUMN_5 + ", " + SessionActivities.COLUMN_6 + ", " +
                SessionActivities.COLUMN_7 + ", " + SessionActivities.COLUMN_8 + ", " +
                SessionActivities.COLUMN_9 + ", " + SessionActivities.COLUMN_10 + ") VALUES('" +
                activity.getPeer_id() + "', " + activity.getGo_ts() + ", " + activity.getJava_ts() + ", " +
                activity.getLat() + ", " + activity.getLog() + ", " +
                activity.getPeer_ts() + ", " + activity.getSpeed() + ", '" + activity.getLoc_provider() + "', " +
                activity.getAccuracy() + ", " + activity.getFix_ts() + ")");
        return true;
    }

    private boolean insertError(SQLiteDatabase db, ErrorLog errorLog) {
        db.execSQL("INSERT INTO " + Errors.TABLE_NAME + " (" + Errors.COLUMN_1 + ", " + Errors.COLUMN_2 +
                ") VALUES('" + errorLog.getPeer_id() + "', '" + errorLog.getErr() + "')");
        return true;
    }
}