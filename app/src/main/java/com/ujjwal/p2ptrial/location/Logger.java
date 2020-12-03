package com.ujjwal.p2ptrial.location;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.ujjwal.p2ptrial.location.logdb.LogDbHelper;
import com.ujjwal.p2ptrial.location.logdb.model.ErrorLog;
import com.ujjwal.p2ptrial.location.logdb.model.Session;
import com.ujjwal.p2ptrial.location.logdb.model.SessionActivity;

public class Logger {
    public final LogDbHelper logDb;
    public final SQLiteDatabase db;

    public Logger(Context context) {
        this.logDb = new LogDbHelper(context);
        this.db = logDb.getWritableDatabase();
    }

    public void logSession(String msg) {
        Session session = new Session();
        String [] parts = msg.split(",");

        try {
            session.setPeer_id(parts[0]);
            session.setInvoke_ts(Long.parseLong(parts[1]));
            session.setResponse_ts(Long.parseLong(parts[2]));
            this.logDb.insert(this.db, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logSessionActivity(String msg) {
        SessionActivity activity = new SessionActivity();
        String [] parts = msg.split(",");

        try {
            activity.setJava_ts(Long.parseLong(parts[0]));
            activity.setGo_ts(Long.parseLong(parts[1]));
            activity.setPeer_id(parts[2]);
            activity.setLat(Double.parseDouble(parts[3]));
            activity.setLog(Double.parseDouble(parts[4]));
            activity.setSpeed(Double.parseDouble(parts[5]));
            activity.setLoc_provider(parts[6]);
            activity.setAccuracy(Double.parseDouble(parts[7]));
            activity.setPeer_ts(Long.parseLong(parts[8]));
            activity.setFix_ts(Long.parseLong(parts[9]));
            this.logDb.insert(this.db, activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logError(String msg) {
        ErrorLog errorLog = new ErrorLog();
        String [] parts = msg.split(",");

        errorLog.setPeer_id(parts[0]);
        errorLog.setErr(parts[1]);
        this.logDb.insert(this.db, errorLog);
    }

    public void clean() {
        this.db.close();
    }
}
