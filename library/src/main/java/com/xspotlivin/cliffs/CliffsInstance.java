package com.xspotlivin.cliffs;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.xspotlivin.cliffs.summary.SummaryHashMap;
import com.xspotlivin.cliffs.summary.TrackingSummary;
import com.xspotlivin.cliffs.util.AppStateListener;
import com.xspotlivin.cliffs.util.AppStateTracker;
import com.xspotlivin.cliffs.util.CliffsLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum CliffsInstance implements AppStateTracker.Callback {
    INSTANCE;

    static CliffsInstance get() {
        return INSTANCE;
    }

    private SummaryHashMap mSummaries;
    private Analytics mAnalytics;
    private String mCurrentScreen;
    private AppStateListener mAppStateListener;

    CliffsInstance() {
        mSummaries = new SummaryHashMap();
    }

    public void initialize(@NonNull final Application application, @NonNull final Analytics analytics) {
        mAnalytics = analytics;
        application.registerActivityLifecycleCallbacks(new AppStateTracker(this));
    }

    public void setAppStateListener(AppStateListener appStateListener) {
        mAppStateListener = appStateListener;
    }

    @Override
    public void onApplicationBackgrounded() {
        CliffsLog.d("application backgrounded");
        if (mAppStateListener != null) {
            mAppStateListener.onBackground();
        }

        final ArrayList<TrackingSummary> toReport = new ArrayList<>();
        for (TrackingSummary summary : mSummaries.values()) {
            if (summary.shouldReportOnBackground()) {
                toReport.add(summary);
            } else {
                summary.stopAllTimers();
            }
        }

        reportSummaries(toReport);
        mCurrentScreen = null;
    }

    @Override
    public void onApplicationForegrounded() {
        CliffsLog.d("application foregrounded");
        if (mAppStateListener != null) {
            mAppStateListener.onForeground();
        }

        for (TrackingSummary summary : mSummaries.values()) {
            summary.startAllTimers();
        }
    }

    public String trackScreen(@Nullable final String screen) {
        if (!TextUtils.isEmpty(screen)) {
            final String lastScreen = mCurrentScreen;
            mCurrentScreen = screen;
            final String previousScreen = TextUtils.isEmpty(lastScreen) ? Constants.NONE : lastScreen;

            final Map<String, String> attributes = new HashMap<>();
            attributes.put(Constants.SCREEN, screen);
            attributes.put(Constants.PREVIOUS_SCREEN, previousScreen);
            mAnalytics.trackScreen(screen);
            mAnalytics.tagEvent(Constants.VIEWED_SCREEN, attributes);

            return previousScreen;
        } else {
            return TextUtils.isEmpty(mCurrentScreen) ? Constants.NONE : mCurrentScreen;
        }
    }

    public void tagEvent(@NonNull final String name, @Nullable final Map<String, String> attributes) {
        mAnalytics.tagEvent(name, attributes);
    }

    public TrackingSummary addSummary(@NonNull final TrackingSummary summary, final boolean overwrite) {
        String identifier = summary.getIdentifier();
        TrackingSummary existingSummary = mSummaries.get(identifier);
        if (overwrite || existingSummary == null) {
            if (existingSummary != null) {
                reportSummary(existingSummary);
            }
            mSummaries.put(identifier, summary);
            return summary;
        } else {
            return existingSummary;
        }
    }

    @Nullable
    public TrackingSummary getSummary(@NonNull final String identifier) {
        return mSummaries.get(identifier);
    }

    private void reportSummaries(@NonNull final List<TrackingSummary> summaries) {
        for (TrackingSummary summary : summaries) {
            reportSummary(summary);
        }
    }

    public void reportSummary(@NonNull final TrackingSummary summary) {
        if (!summary.isReported()) {
            final Map<String, String> report = summary.getReport();
            final String name = summary.getName();
            tagEvent(name, report);
            mSummaries.remove(summary.getIdentifier());
            CliffsLog.d("Reported '" + name + "': " + report);
        }
    }
}
