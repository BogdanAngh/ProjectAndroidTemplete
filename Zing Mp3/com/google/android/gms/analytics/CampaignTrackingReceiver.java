package com.google.android.gms.analytics;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.RequiresPermission;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzaf;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzxr;

public class CampaignTrackingReceiver extends BroadcastReceiver {
    static zzxr ax;
    static Boolean ay;
    static Object zzaox;

    static {
        zzaox = new Object();
    }

    public static boolean zzat(Context context) {
        zzaa.zzy(context);
        if (ay != null) {
            return ay.booleanValue();
        }
        boolean zza = zzao.zza(context, "com.google.android.gms.analytics.CampaignTrackingReceiver", true);
        ay = Boolean.valueOf(zza);
        return zza;
    }

    @RequiresPermission(allOf = {"android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"})
    public void onReceive(Context context, Intent intent) {
        zzf zzaw = zzf.zzaw(context);
        zzaf zzaca = zzaw.zzaca();
        if (intent == null) {
            zzaca.zzev("CampaignTrackingReceiver received null intent");
            return;
        }
        String stringExtra = intent.getStringExtra("referrer");
        String action = intent.getAction();
        zzaca.zza("CampaignTrackingReceiver received", action);
        if (!"com.android.vending.INSTALL_REFERRER".equals(action) || TextUtils.isEmpty(stringExtra)) {
            zzaca.zzev("CampaignTrackingReceiver received unexpected intent without referrer extra");
            return;
        }
        boolean zzau = CampaignTrackingService.zzau(context);
        if (!zzau) {
            zzaca.zzev("CampaignTrackingService not registered or disabled. Installation tracking not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
        zzp(context, stringExtra);
        zzaw.zzacb();
        Class zzyy = zzyy();
        zzaa.zzy(zzyy);
        Intent intent2 = new Intent(context, zzyy);
        intent2.putExtra("referrer", stringExtra);
        synchronized (zzaox) {
            context.startService(intent2);
            if (zzau) {
                try {
                    if (ax == null) {
                        ax = new zzxr(context, 1, "Analytics campaign WakeLock");
                        ax.setReferenceCounted(false);
                    }
                    ax.acquire(1000);
                } catch (SecurityException e) {
                    zzaca.zzev("CampaignTrackingService service at risk of not starting. For more reliable installation campaign reports, add the WAKE_LOCK permission to your manifest. See http://goo.gl/8Rd3yj for instructions.");
                }
                return;
            }
        }
    }

    protected void zzp(Context context, String str) {
    }

    protected Class<? extends CampaignTrackingService> zzyy() {
        return CampaignTrackingService.class;
    }
}
