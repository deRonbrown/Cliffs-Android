package com.xspotlivin.cliffs.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.xspotlivin.cliffs.Cliffs;

public final class CliffsLog {

    private static final String TAG = "Cliffs";

    public static void v(@NonNull String msg) {
        if (Cliffs.isLoggingEnabled()) {
            Log.v(TAG, msg);
        }
    }

    public static void d(@NonNull String msg) {
        if (Cliffs.isLoggingEnabled()) {
            Log.d(TAG, msg);
        }
    }

    public static void i(@NonNull String msg) {
        if (Cliffs.isLoggingEnabled()) {
            Log.i(TAG, msg);
        }
    }

    public static void w(@NonNull String msg) {
        if (Cliffs.isLoggingEnabled()) {
            Log.w(TAG, msg);
        }
    }

    public static void e(@NonNull String msg) {
        if (Cliffs.isLoggingEnabled()) {
            Log.e(TAG, msg);
        }
    }
}
