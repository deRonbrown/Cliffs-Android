package com.xspotlivin.cliffs.summary;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xspotlivin.cliffs.Constants;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractSummary implements TrackingSummary {

    private final String mIdentifier;
    private final HashMap<String, Boolean> mFlags;
    private final HashMap<String, Long> mCounters;
    private final HashMap<String, String> mAttributes;
    private final HashMap<String, Timer> mTimers;

    protected boolean mIsReported;

    public AbstractSummary(@NonNull final String identifier) {
        mIdentifier = identifier;
        mFlags = new HashMap<>();
        mCounters = new HashMap<>();
        mAttributes = new HashMap<>();
        mTimers = new HashMap<>();

        Timer timer = new Timer(Constants.TIME_SPENT);
        timer.start();
        mTimers.put(Constants.TIME_SPENT, timer);

        setPreviousScreen(Constants.NONE);
    }

    @NonNull
    @Override
    public String getIdentifier() {
        return mIdentifier;
    }

    protected void initFlags(@NonNull final String... names) {
        for (String s : names) {
            setFlag(s, false);
        }
    }

    protected void initCounters(@NonNull final String... names) {
        for (String s : names) {
            initCounter(s);
        }
    }

    protected void initCounter(@NonNull final String name) {
        setCounter(name, 0);
    }

    private void setCounter(@NonNull final String name, final long value) {
        mCounters.put(name, value);
    }

    @Override
    public void setPreviousScreen(@NonNull String screen) {
        setAttribute(Constants.PREVIOUS_SCREEN, screen);
    }

    @Override
    public void setFlag(@NonNull String name) {
        setFlag(name, true);
    }

    @Override
    public void setFlag(@NonNull String name, boolean enabled) {
        mFlags.put(name, enabled);
    }

    @Override
    public void setAttribute(@NonNull String key, @NonNull String value) {
        mAttributes.put(key, value);
    }

    @Override
    public void setAttribute(@NonNull String key, int value) {
        mAttributes.put(key, Integer.toString(value));
    }

    @Override
    public void setAttribute(@NonNull String key, float value) {
        mAttributes.put(key, Float.toString(value));
    }

    @Override
    public void setAttribute(@NonNull String key, long value) {
        mAttributes.put(key, Long.toString(value));
    }

    @Override
    public void incrementCounter(@NonNull String name) {
        incrementCounter(name, 1);
    }

    @Override
    public void incrementCounter(@NonNull String name, long incrementValue) {
        Long previousValue = mCounters.get(name);
        if (previousValue == null) {
            setCounter(name, incrementValue);
        } else {
            setCounter(name, previousValue + incrementValue);
        }
    }

    @Nullable
    @Override
    public Timer getTimer(@NonNull String identifier) {
        return mTimers.get(identifier);
    }

    @Override
    public void addTimer(@NonNull Timer timer) {
        mTimers.put(timer.getIdentifier(), timer);
    }

    @Override
    public void startAllTimers() {
        for (Map.Entry<String, Timer> entry : mTimers.entrySet()) {
            Timer value = entry.getValue();
            value.start();
        }
    }

    @Override
    public void stopAllTimers() {
        for (Map.Entry<String, Timer> entry : mTimers.entrySet()) {
            Timer value = entry.getValue();
            value.stop();
        }
    }

    @NonNull
    @Override
    public Map<String, String> getReport() {
        mIsReported = true;
        final HashMap<String, String> report = new HashMap<>();

        for (Map.Entry<String, Boolean> entry : mFlags.entrySet()) {
            report.put(entry.getKey(), entry.getValue() ? Constants.YES : Constants.NO);
        }

        for (Map.Entry<String, Long> entry : mCounters.entrySet()) {
            report.put(entry.getKey(), entry.getValue().toString());
        }

        for (Map.Entry<String, Timer> entry : mTimers.entrySet()) {
            Timer value = entry.getValue();
            value.stop();
            report.put(entry.getKey(), String.valueOf(value.getTimeSeconds()));
        }

        report.putAll(mAttributes);

        return report;
    }

    @Override
    public boolean isReported() {
        return mIsReported;
    }
}
