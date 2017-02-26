package com.mp3download.zingmp3;

import android.app.Application;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

public class GoogleAnalyticsApplication extends Application {
    private Tracker mTracker;

    public synchronized Tracker getDefaultTracker() {
        if (this.mTracker == null) {
            this.mTracker = GoogleAnalytics.getInstance(this).newTracker((int) C1569R.xml.app_tracker);
        }
        return this.mTracker;
    }
}
