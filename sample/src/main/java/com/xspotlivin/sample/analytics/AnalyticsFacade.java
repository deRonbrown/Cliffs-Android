package com.xspotlivin.sample.analytics;

import android.support.annotation.NonNull;

import com.xspotlivin.cliffs.Cliffs;
import com.xspotlivin.cliffs.summary.TrackingSummary;

public class AnalyticsFacade {

    private static final String SUMMARY_MAIN = "Main";
    private static final String SUMMARY_SECOND = "Second";

    @NonNull
    public static TrackingSummary getMainSummary() {
        TrackingSummary summary = Cliffs.getSummary(SUMMARY_MAIN);
        if (summary == null) {
            summary = Cliffs.addSummary(new MainSummary(SUMMARY_MAIN), false);
        }

        return summary;
    }

    @NonNull
    public static TrackingSummary getSecondSummary(int startColor) {
        TrackingSummary summary = Cliffs.getSummary(SUMMARY_SECOND);
        if (summary == null) {
            summary = Cliffs.addSummary(new SecondSummary(SUMMARY_SECOND, startColor), false);
        }

        return summary;
    }
}
