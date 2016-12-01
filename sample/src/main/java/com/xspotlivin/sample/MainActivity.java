package com.xspotlivin.sample;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.xspotlivin.cliffs.activity.SummaryActivity;
import com.xspotlivin.cliffs.summary.TrackingSummary;
import com.xspotlivin.sample.analytics.AnalyticsConstants;
import com.xspotlivin.sample.analytics.AnalyticsFacade;

public class MainActivity extends SummaryActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected String getScreenName() {
        return "Main";
    }

    @Override
    protected boolean isSimpleSummary() {
        return false;
    }

    @Override
    protected TrackingSummary createSummary() {
        return AnalyticsFacade.getMainSummary();
    }

    public void pressMeClicked(View view) {
        getSummary().setFlag(AnalyticsConstants.FLAG_PRESS_ME_CLICKED);
        getSummary().incrementCounter(AnalyticsConstants.COUNT_PRESS_ME_CLICKS);
    }

    public void goToSecondClicked(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
