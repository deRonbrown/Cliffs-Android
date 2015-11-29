package com.xspotlivin.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.xspotlivin.cliffs.activity.SummaryActivity;
import com.xspotlivin.cliffs.summary.TrackingSummary;
import com.xspotlivin.sample.analytics.AnalyticsConstants;
import com.xspotlivin.sample.analytics.AnalyticsFacade;

import java.util.Random;

public class SecondActivity extends SummaryActivity {

    private FrameLayout mFrameLayout;
    private int mStartColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mStartColor = getRandomColor();
        mFrameLayout = (FrameLayout) findViewById(R.id.fl_main);
        mFrameLayout.setBackgroundColor(mStartColor);
    }

    @Override
    protected String getScreenName() {
        return "Second";
    }

    @Override
    protected boolean isSimpleSummary() {
        return true;
    }

    @Override
    protected TrackingSummary createSummary(String lastScreen) {
        TrackingSummary summary = AnalyticsFacade.getSecondSummary(mStartColor);
        summary.setPreviousScreen(lastScreen);
        return summary;
    }

    public void setColorClicked(View view) {
        getSummary().incrementCounter(AnalyticsConstants.COUNT_SET_COLOR_CLICKS);
        AnalyticsFacade.getMainSummary().setFlag(AnalyticsConstants.FLAG_CHANGE_COLOR);
        mFrameLayout.setBackgroundColor(getRandomColor());
    }

    private int getRandomColor() {
        Random random = new Random();
        return Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
    }
}
