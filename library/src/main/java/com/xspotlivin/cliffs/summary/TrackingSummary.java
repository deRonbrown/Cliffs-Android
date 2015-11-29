package com.xspotlivin.cliffs.summary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Map;

public interface TrackingSummary {
    @NonNull
    String getIdentifier();

    @NonNull
    String getName();

    void setPreviousScreen(@NonNull String screen);

    void setFlag(@NonNull String name);

    void setFlag(@NonNull String name, boolean enabled);

    void setAttribute(@NonNull String key, @NonNull String value);

    void setAttribute(@NonNull String key, int value);

    void setAttribute(@NonNull String key, long value);

    void setAttribute(@NonNull String key, float value);

    void incrementCounter(@NonNull String name);

    void incrementCounter(@NonNull String name, long incrementValue);

    @Nullable
    Timer getTimer(@NonNull String identifier);

    void addTimer(@NonNull Timer timer);

    void startAllTimers();

    void stopAllTimers();

    @NonNull
    Map<String, String> getReport();

    boolean isReported();

    boolean shouldReportOnBackground();
}
