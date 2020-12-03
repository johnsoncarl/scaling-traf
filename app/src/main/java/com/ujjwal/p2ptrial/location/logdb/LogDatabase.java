package com.ujjwal.p2ptrial.location.logdb;

import android.provider.BaseColumns;

public final class LogDatabase {
    private LogDatabase() {}

    /* Inner class that defines the table contents */
    public static class Sessions implements BaseColumns {
        public static final String TABLE_NAME = "session";
        public static final String COLUMN_1 = "peer_id";
        public static final String COLUMN_2 = "invoke_ts";
        public static final String COLUMN_3 = "response_ts";
    }

    /* Inner class that defines the table contents */
    public static class SessionActivities implements BaseColumns {
        public static final String TABLE_NAME = "session_activity";
        public static final String COLUMN_0 = "peer_id";
        public static final String COLUMN_1 = "go_ts";
        public static final String COLUMN_2 = "java_ts";
        public static final String COLUMN_4 = "lat";
        public static final String COLUMN_5 = "long";
        public static final String COLUMN_6 = "peer_ts";
        public static final String COLUMN_7 = "speed";
        public static final String COLUMN_8 = "loc_provider";
        public static final String COLUMN_9 = "accuracy";
        public static final String COLUMN_10 = "fix_ts";
    }

    /* Inner class that defines the table contents */
    public static class Errors implements BaseColumns {
        public static final String TABLE_NAME = "error";
        public static final String COLUMN_1 = "peer_id";
        public static final String COLUMN_2 = "err";
    }
}