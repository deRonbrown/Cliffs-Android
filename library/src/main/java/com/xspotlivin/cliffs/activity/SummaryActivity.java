package com.xspotlivin.cliffs.activity;

import android.support.v7.app.AppCompatActivity;

import com.xspotlivin.cliffs.Cliffs;
import com.xspotlivin.cliffs.summary.NullSummary;
import com.xspotlivin.cliffs.summary.TrackingSummary;

public abstract class SummaryActivity extends AppCompatActivity {

    private TrackingSummary mTrackingSummary;

    @Override
    protected void onResume() {
        super.onResume();

        final String lastScreen = Cliffs.trackScreen(getScreenName());
        if (isSimpleSummary() || mTrackingSummary == null || mTrackingSummary.isReported()) {
            mTrackingSummary = createSummary(lastScreen);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mTrackingSummary != null && isSimpleSummary()) {
            Cliffs.reportSummary(mTrackingSummary);
        }
    }

    protected TrackingSummary getSummary() {
        return mTrackingSummary != null ? mTrackingSummary : new NullSummary();
    }

    protected abstract String getScreenName();

    protected abstract boolean isSimpleSummary();

    protected abstract TrackingSummary createSummary(String lastScreen);

}
