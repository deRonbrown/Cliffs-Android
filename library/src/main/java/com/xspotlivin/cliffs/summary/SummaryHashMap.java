package com.xspotlivin.cliffs.summary;

import com.xspotlivin.cliffs.util.CliffsLog;

import java.util.HashMap;

public class SummaryHashMap extends HashMap<String, TrackingSummary> {

    @Override
    public TrackingSummary put(String key, TrackingSummary value) {
        CliffsLog.d("Created Summary: " + key);
        return super.put(key, value);
    }
}
