package com.ujjwal.p2ptrial.location;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ujjwal.p2ptrial.location.logdb.model.SessionActivity;

import static com.ujjwal.p2ptrial.location.P2pNetwork.PEER_ID;

public class FirebaseLogger {
    private static final String TAG = "FirebaseLogger";

    private final FirebaseDatabase logDb;
    private final String dbRoot;

    FirebaseLogger(Context context, FirebaseDatabase logDb) {
        this.logDb = logDb;
        int stringId = context.getApplicationInfo().labelRes;
        this.dbRoot = stringId == 0 ? context.getApplicationInfo().nonLocalizedLabel.toString() : context.getString(stringId);
    }

    public void logSessionActivity(String msg) {
        if (this.logDb == null)
            return;
        SessionActivity activity = new SessionActivity();
        String [] parts = msg.split(",");
        DatabaseReference ref = this.logDb.getReference(this.dbRoot + "/devices/" + PEER_ID);

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
            ref.setValue(activity);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
