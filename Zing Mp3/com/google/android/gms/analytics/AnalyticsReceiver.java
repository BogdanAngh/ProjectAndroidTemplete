package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.analytics.internal.zzaj;

public final class AnalyticsReceiver extends BroadcastReceiver {
    private zzaj aq;

    private zzaj zzyv() {
        if (this.aq == null) {
            this.aq = new zzaj();
        }
        return this.aq;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context context, Intent intent) {
        zzyv().onReceive(context, intent);
    }
}
