package com.google.android.gms.analytics.internal;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.common.internal.zzaa;

public class zzv extends zzd {
    private boolean ei;
    private boolean ej;
    private AlarmManager ek;

    protected zzv(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.ek = (AlarmManager) getContext().getSystemService(NotificationCompatApi24.CATEGORY_ALARM);
    }

    private PendingIntent zzafo() {
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"));
        return PendingIntent.getBroadcast(getContext(), 0, intent, 0);
    }

    public void cancel() {
        zzacj();
        this.ej = false;
        this.ek.cancel(zzafo());
    }

    public void schedule() {
        zzacj();
        zzaa.zza(zzafn(), (Object) "Receiver not registered");
        long zzaen = zzacb().zzaen();
        if (zzaen > 0) {
            cancel();
            long elapsedRealtime = zzabz().elapsedRealtime() + zzaen;
            this.ej = true;
            this.ek.setInexactRepeating(2, elapsedRealtime, 0, zzafo());
        }
    }

    public boolean zzafn() {
        return this.ei;
    }

    public boolean zzfy() {
        return this.ej;
    }

    protected void zzzy() {
        try {
            this.ek.cancel(zzafo());
            if (zzacb().zzaen() > 0) {
                ActivityInfo receiverInfo = getContext().getPackageManager().getReceiverInfo(new ComponentName(getContext(), "com.google.android.gms.analytics.AnalyticsReceiver"), 2);
                if (receiverInfo != null && receiverInfo.enabled) {
                    zzes("Receiver registered. Using alarm for local dispatch.");
                    this.ei = true;
                }
            }
        } catch (NameNotFoundException e) {
        }
    }
}
