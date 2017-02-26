package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzxr;

public class CampaignTrackingService extends Service {
    private static Boolean az;
    private Handler mHandler;

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.1 */
    class C11421 implements Runnable {
        final /* synthetic */ zzaf aA;
        final /* synthetic */ int aB;
        final /* synthetic */ CampaignTrackingService aC;
        final /* synthetic */ Handler zzs;

        C11421(CampaignTrackingService campaignTrackingService, zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
            this.aC = campaignTrackingService;
            this.aA = com_google_android_gms_analytics_internal_zzaf;
            this.zzs = handler;
            this.aB = i;
        }

        public void run() {
            this.aC.zza(this.aA, this.zzs, this.aB);
        }
    }

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.2 */
    class C11432 implements Runnable {
        final /* synthetic */ zzaf aA;
        final /* synthetic */ int aB;
        final /* synthetic */ CampaignTrackingService aC;
        final /* synthetic */ Handler zzs;

        C11432(CampaignTrackingService campaignTrackingService, zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
            this.aC = campaignTrackingService;
            this.aA = com_google_android_gms_analytics_internal_zzaf;
            this.zzs = handler;
            this.aB = i;
        }

        public void run() {
            this.aC.zza(this.aA, this.zzs, this.aB);
        }
    }

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.3 */
    class C11443 implements Runnable {
        final /* synthetic */ zzaf aA;
        final /* synthetic */ int aB;
        final /* synthetic */ CampaignTrackingService aC;

        C11443(CampaignTrackingService campaignTrackingService, int i, zzaf com_google_android_gms_analytics_internal_zzaf) {
            this.aC = campaignTrackingService;
            this.aB = i;
            this.aA = com_google_android_gms_analytics_internal_zzaf;
        }

        public void run() {
            boolean stopSelfResult = this.aC.stopSelfResult(this.aB);
            if (stopSelfResult) {
                this.aA.zza("Install campaign broadcast processed", Boolean.valueOf(stopSelfResult));
            }
        }
    }

    private Handler getHandler() {
        Handler handler = this.mHandler;
        if (handler != null) {
            return handler;
        }
        handler = new Handler(getMainLooper());
        this.mHandler = handler;
        return handler;
    }

    public static boolean zzau(Context context) {
        zzaa.zzy(context);
        if (az != null) {
            return az.booleanValue();
        }
        boolean zzr = zzao.zzr(context, "com.google.android.gms.analytics.CampaignTrackingService");
        az = Boolean.valueOf(zzr);
        return zzr;
    }

    private void zzyz() {
        try {
            synchronized (CampaignTrackingReceiver.zzaox) {
                zzxr com_google_android_gms_internal_zzxr = CampaignTrackingReceiver.ax;
                if (com_google_android_gms_internal_zzxr != null && com_google_android_gms_internal_zzxr.isHeld()) {
                    com_google_android_gms_internal_zzxr.release();
                }
            }
        } catch (SecurityException e) {
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onCreate() {
        super.onCreate();
        zzf.zzaw(this).zzaca().zzes("CampaignTrackingService is starting up");
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onDestroy() {
        zzf.zzaw(this).zzaca().zzes("CampaignTrackingService is shutting down");
        super.onDestroy();
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public int onStartCommand(Intent intent, int i, int i2) {
        zzyz();
        zzf zzaw = zzf.zzaw(this);
        zzaf zzaca = zzaw.zzaca();
        zzaw.zzacb();
        String stringExtra = intent.getStringExtra("referrer");
        Handler handler = getHandler();
        if (TextUtils.isEmpty(stringExtra)) {
            zzaw.zzacb();
            zzaca.zzev("No campaign found on com.android.vending.INSTALL_REFERRER \"referrer\" extra");
            zzaw.zzacc().zzg(new C11421(this, zzaca, handler, i2));
        } else {
            int zzaei = zzaw.zzacb().zzaei();
            if (stringExtra.length() > zzaei) {
                zzaca.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(stringExtra.length()), Integer.valueOf(zzaei));
                stringExtra = stringExtra.substring(0, zzaei);
            }
            zzaca.zza("CampaignTrackingService called. startId, campaign", Integer.valueOf(i2), stringExtra);
            zzaw.zzzg().zza(stringExtra, new C11432(this, zzaca, handler, i2));
        }
        return 2;
    }

    protected void zza(zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
        handler.post(new C11443(this, i, com_google_android_gms_analytics_internal_zzaf));
    }
}
