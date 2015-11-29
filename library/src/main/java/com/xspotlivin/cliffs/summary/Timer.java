package com.xspotlivin.cliffs.summary;

import android.support.annotation.NonNull;

public class Timer {
    private final String mIdentifier;
    private long mTimerStart;
    private long mTimerStop;
    private boolean mIsRunning;
    private long mCumulativeMillis;

    public Timer(@NonNull final String identifier) {
        mIdentifier = identifier;
        reset();
    }

    public String getIdentifier() {
        return mIdentifier;
    }

    public void reset() {
        mTimerStart = 0;
        mTimerStop = 0;
        mIsRunning = false;
    }

    public void start() {
        if (mTimerStart == 0) {
            mTimerStart = System.currentTimeMillis();
        }
        mIsRunning = true;
    }

    public void stop() {
        if (mIsRunning) {
            mTimerStop = System.currentTimeMillis();
            mCumulativeMillis += mTimerStop - mTimerStart;
        }

        reset();
    }

    public boolean isRunning() {
        return mIsRunning;
    }

    public long getTimeMillis() {
        if (mIsRunning) {
            return mCumulativeMillis + (System.currentTimeMillis() - mTimerStart);
        } else {
            return mCumulativeMillis;
        }
    }

    public long getTimeSeconds() {
        return getTimeMillis() / 1000;
    }

    public long getTimeMinutes() {
        return getTimeSeconds() / 60;
    }

    @Override
    public String toString() {
        return "Timer " + mIdentifier + ", " + (mIsRunning ? "Running" : "Stopped") + ", Current Time: "
                + getTimeMillis() + "ms";
    }
}
