package com.xspotlivin.cliffs;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xspotlivin.cliffs.summary.TrackingSummary;
import com.xspotlivin.cliffs.util.AppStateListener;

import java.util.Map;

public class Cliffs {

    private static boolean sLoggingEnabled;

    public static void initialize(@NonNull final Application application, @NonNull final Analytics analytics) {
        CliffsInstance.get().initialize(application, analytics);
    }

    public static void setAppStateListener(AppStateListener appStateListener) {
        CliffsInstance.get().setAppStateListener(appStateListener);
    }

    public static String trackScreen(@Nullable final String screen) {
        return CliffsInstance.get().trackScreen(screen);
    }

    public static void tagEvent(@NonNull final String name, @Nullable final Map<String, String> attributes) {
        CliffsInstance.get().tagEvent(name, attributes);
    }

    public static TrackingSummary addSummary(@NonNull final TrackingSummary summary, final boolean overwrite) {
        return CliffsInstance.get().addSummary(summary, overwrite);
    }

    @Nullable
    public static TrackingSummary getSummary(@NonNull final String identifier) {
        return CliffsInstance.get().getSummary(identifier);
    }

    public static void reportSummary(@NonNull final TrackingSummary summary) {
        CliffsInstance.get().reportSummary(summary);
    }

    public static void setLoggingEnabled(boolean enabled) {
        sLoggingEnabled = enabled;
    }

    public static boolean isLoggingEnabled() {
        return sLoggingEnabled;
    }

    private Cliffs() {
        throw new UnsupportedOperationException("Instances are not allowed");
    }
}
