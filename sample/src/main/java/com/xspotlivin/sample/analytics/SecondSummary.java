package com.xspotlivin.sample.analytics;

import android.support.annotation.NonNull;

import com.xspotlivin.cliffs.summary.AbstractSummary;

public class SecondSummary extends AbstractSummary {

    public SecondSummary(@NonNull String identifier, int startColor) {
        super(identifier);
        initCounters(AnalyticsConstants.COUNT_SET_COLOR_CLICKS);
        setAttribute(AnalyticsConstants.ATTRIBUTE_COLOR, startColor);
    }

    @NonNull
    @Override
    public String getName() {
        return "Second";
    }

    @Override
    public boolean shouldReportOnBackground() {
        return true;
    }
}
