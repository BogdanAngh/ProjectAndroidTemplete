package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzxr;

public final class zzak {
    private static Boolean az;
    private final zza fL;
    private final Context mContext;
    private final Handler mHandler;

    public interface zza {
        boolean callServiceStopSelfResult(int i);

        Context getContext();
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzak.1 */
    class C11491 implements zzw {
        final /* synthetic */ zzaf aA;
        final /* synthetic */ int aB;
        final /* synthetic */ zzf fM;
        final /* synthetic */ zzak fN;

        /* renamed from: com.google.android.gms.analytics.internal.zzak.1.1 */
        class C11481 implements Runnable {
            final /* synthetic */ C11491 fO;

            C11481(C11491 c11491) {
                this.fO = c11491;
            }

            public void run() {
                if (this.fO.fN.fL.callServiceStopSelfResult(this.fO.aB)) {
                    this.fO.fM.zzacb();
                    this.fO.aA.zzes("Local AnalyticsService processed last dispatch request");
                }
            }
        }

        C11491(zzak com_google_android_gms_analytics_internal_zzak, int i, zzf com_google_android_gms_analytics_internal_zzf, zzaf com_google_android_gms_analytics_internal_zzaf) {
            this.fN = com_google_android_gms_analytics_internal_zzak;
            this.aB = i;
            this.fM = com_google_android_gms_analytics_internal_zzf;
            this.aA = com_google_android_gms_analytics_internal_zzaf;
        }

        public void zzf(Throwable th) {
            this.fN.mHandler.post(new C11481(this));
        }
    }

    public zzak(zza com_google_android_gms_analytics_internal_zzak_zza) {
        this.mContext = com_google_android_gms_analytics_internal_zzak_zza.getContext();
        zzaa.zzy(this.mContext);
        this.fL = com_google_android_gms_analytics_internal_zzak_zza;
        this.mHandler = new Handler();
    }

    public static boolean zzau(Context context) {
        zzaa.zzy(context);
        if (az != null) {
            return az.booleanValue();
        }
        boolean zzr = zzao.zzr(context, "com.google.android.gms.analytics.AnalyticsService");
        az = Boolean.valueOf(zzr);
        return zzr;
    }

    private void zzyz() {
        try {
            synchronized (zzaj.zzaox) {
                zzxr com_google_android_gms_internal_zzxr = zzaj.ax;
                if (com_google_android_gms_internal_zzxr != null && com_google_android_gms_internal_zzxr.isHeld()) {
                    com_google_android_gms_internal_zzxr.release();
                }
            }
        } catch (SecurityException e) {
        }
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onCreate() {
        zzf zzaw = zzf.zzaw(this.mContext);
        zzaf zzaca = zzaw.zzaca();
        zzaw.zzacb();
        zzaca.zzes("Local AnalyticsService is starting up");
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onDestroy() {
        zzf zzaw = zzf.zzaw(this.mContext);
        zzaf zzaca = zzaw.zzaca();
        zzaw.zzacb();
        zzaca.zzes("Local AnalyticsService is shutting down");
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public int onStartCommand(Intent intent, int i, int i2) {
        zzyz();
        zzf zzaw = zzf.zzaw(this.mContext);
        zzaf zzaca = zzaw.zzaca();
        if (intent == null) {
            zzaca.zzev("AnalyticsService started with null intent");
        } else {
            String action = intent.getAction();
            zzaw.zzacb();
            zzaca.zza("Local AnalyticsService called. startId, action", Integer.valueOf(i2), action);
            if ("com.google.android.gms.analytics.ANALYTICS_DISPATCH".equals(action)) {
                zzaw.zzzg().zza(new C11491(this, i2, zzaw, zzaca));
            }
        }
        return 2;
    }
}
