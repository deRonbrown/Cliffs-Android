package com.xspotlivin.cliffs.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

public class AppStateTracker implements Application.ActivityLifecycleCallbacks {

    public interface Callback {
        void onApplicationBackgrounded();

        void onApplicationForegrounded();
    }

    private Callback mCallback;
    private boolean mFirstActivityStarted;
    private int mActivityCounter;

    public AppStateTracker(@NonNull Callback callback) {
        mCallback = callback;
        mFirstActivityStarted = true;
    }

    public boolean isInBackground() {
        return mActivityCounter == 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        if (!mFirstActivityStarted && isInBackground()) {
            mCallback.onApplicationForegrounded();
        }

        mActivityCounter++;
        mFirstActivityStarted = false;
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        mActivityCounter--;
        if (isInBackground()) {
            mCallback.onApplicationBackgrounded();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }
}
