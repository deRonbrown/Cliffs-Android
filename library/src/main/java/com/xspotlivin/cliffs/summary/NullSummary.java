package com.xspotlivin.cliffs.summary;

import android.support.annotation.NonNull;

import com.xspotlivin.cliffs.Constants;

import java.util.HashMap;
import java.util.Map;

public class NullSummary extends AbstractSummary {

    public NullSummary() {
        super(Constants.NONE);
    }

    @NonNull
    @Override
    public Map<String, String> getReport() {
        return new HashMap<>();
    }

    @Override
    public boolean isReported() {
        return true;
    }

    @NonNull
    @Override
    public String getName() {
        return Constants.NONE;
    }

    @Override
    public boolean shouldReportOnBackground() {
        return false;
    }
}
