package com.xspotlivin.sample.analytics;

import android.support.annotation.NonNull;

import com.xspotlivin.cliffs.summary.AbstractSummary;

public class MainSummary extends AbstractSummary {

    public MainSummary(@NonNull String identifier) {
        super(identifier);
        initFlags(AnalyticsConstants.FLAG_PRESS_ME_CLICKED, AnalyticsConstants.FLAG_CHANGE_COLOR);
        initCounters(AnalyticsConstants.COUNT_PRESS_ME_CLICKS);
    }

    @NonNull
    @Override
    public String getName() {
        return "Main";
    }

    @Override
    public boolean shouldReportOnBackground() {
        return true;
    }
}
