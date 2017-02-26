package com.google.android.gms.analytics;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager.WakeLock;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzam;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzu;

public class CampaignTrackingService extends Service {
    private static Boolean zzIe;
    private Handler mHandler;

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.1 */
    class C01021 implements Runnable {
        final /* synthetic */ int zzIf;
        final /* synthetic */ zzaf zzIh;
        final /* synthetic */ CampaignTrackingService zzIo;
        final /* synthetic */ Handler zzt;

        C01021(CampaignTrackingService campaignTrackingService, zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
            this.zzIo = campaignTrackingService;
            this.zzIh = com_google_android_gms_analytics_internal_zzaf;
            this.zzt = handler;
            this.zzIf = i;
        }

        public void run() {
            this.zzIo.zza(this.zzIh, this.zzt, this.zzIf);
        }
    }

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.2 */
    class C01032 implements Runnable {
        final /* synthetic */ int zzIf;
        final /* synthetic */ zzaf zzIh;
        final /* synthetic */ CampaignTrackingService zzIo;
        final /* synthetic */ Handler zzt;

        C01032(CampaignTrackingService campaignTrackingService, zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
            this.zzIo = campaignTrackingService;
            this.zzIh = com_google_android_gms_analytics_internal_zzaf;
            this.zzt = handler;
            this.zzIf = i;
        }

        public void run() {
            this.zzIo.zza(this.zzIh, this.zzt, this.zzIf);
        }
    }

    /* renamed from: com.google.android.gms.analytics.CampaignTrackingService.3 */
    class C01043 implements Runnable {
        final /* synthetic */ int zzIf;
        final /* synthetic */ zzaf zzIh;
        final /* synthetic */ CampaignTrackingService zzIo;

        C01043(CampaignTrackingService campaignTrackingService, int i, zzaf com_google_android_gms_analytics_internal_zzaf) {
            this.zzIo = campaignTrackingService;
            this.zzIf = i;
            this.zzIh = com_google_android_gms_analytics_internal_zzaf;
        }

        public void run() {
            boolean stopSelfResult = this.zzIo.stopSelfResult(this.zzIf);
            if (stopSelfResult) {
                this.zzIh.zza("Install campaign broadcast processed", Boolean.valueOf(stopSelfResult));
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

    public static boolean zzU(Context context) {
        zzu.zzu(context);
        if (zzIe != null) {
            return zzIe.booleanValue();
        }
        boolean zza = zzam.zza(context, CampaignTrackingService.class);
        zzIe = Boolean.valueOf(zza);
        return zza;
    }

    private void zzhd() {
        try {
            synchronized (CampaignTrackingReceiver.zzoW) {
                WakeLock wakeLock = CampaignTrackingReceiver.zzIc;
                if (wakeLock != null && wakeLock.isHeld()) {
                    wakeLock.release();
                }
            }
        } catch (SecurityException e) {
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate() {
        super.onCreate();
        zzf.zzV(this).zzhQ().zzaT("CampaignTrackingService is starting up");
    }

    public void onDestroy() {
        zzf.zzV(this).zzhQ().zzaT("CampaignTrackingService is shutting down");
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        zzhd();
        zzf zzV = zzf.zzV(this);
        zzaf zzhQ = zzV.zzhQ();
        String str = null;
        if (zzV.zzhR().zziW()) {
            zzhQ.zzaX("Unexpected installation campaign (package side)");
        } else {
            str = intent.getStringExtra("referrer");
        }
        Handler handler = getHandler();
        if (TextUtils.isEmpty(str)) {
            if (!zzV.zzhR().zziW()) {
                zzhQ.zzaW("No campaign found on com.android.vending.INSTALL_REFERRER \"referrer\" extra");
            }
            zzV.zzhS().zze(new C01021(this, zzhQ, handler, startId));
        } else {
            int zzja = zzV.zzhR().zzja();
            if (str.length() > zzja) {
                zzhQ.zzc("Campaign data exceed the maximum supported size and will be clipped. size, limit", Integer.valueOf(str.length()), Integer.valueOf(zzja));
                str = str.substring(0, zzja);
            }
            zzhQ.zza("CampaignTrackingService called. startId, campaign", Integer.valueOf(startId), str);
            zzV.zzhl().zza(str, new C01032(this, zzhQ, handler, startId));
        }
        return 2;
    }

    protected void zza(zzaf com_google_android_gms_analytics_internal_zzaf, Handler handler, int i) {
        handler.post(new C01043(this, i, com_google_android_gms_analytics_internal_zzaf));
    }
}
