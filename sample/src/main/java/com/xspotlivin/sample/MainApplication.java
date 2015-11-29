package com.xspotlivin.sample;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.localytics.android.Localytics;
import com.localytics.android.LocalyticsActivityLifecycleCallbacks;
import com.xspotlivin.cliffs.Analytics;
import com.xspotlivin.cliffs.Cliffs;
import com.xspotlivin.cliffs.util.AppStateListenerAdapter;
import com.xspotlivin.sample.analytics.AnalyticsFacade;

import java.util.Map;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        registerActivityLifecycleCallbacks(new LocalyticsActivityLifecycleCallbacks(this));
        Cliffs.setLoggingEnabled(BuildConfig.DEBUG);
        Cliffs.initialize(this, new Analytics() {
            @Override
            public void trackScreen(@NonNull String screen) {
                Localytics.tagScreen(screen);
            }

            @Override
            public void tagEvent(@NonNull String name, @Nullable Map<String, String> attributes) {
                Localytics.tagEvent(name, attributes);
            }
        });

        Cliffs.setAppStateListener(new AppStateListenerAdapter() {

            @Override
            public void onForeground() {

                // Always create Main summary on foreground
                AnalyticsFacade.getMainSummary();
            }
        });
    }
}
