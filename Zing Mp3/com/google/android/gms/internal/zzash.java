package com.google.android.gms.internal;

import android.content.ComponentName;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;
import java.lang.ref.WeakReference;

public class zzash extends CustomTabsServiceConnection {
    private WeakReference<zzasi> buE;

    public zzash(zzasi com_google_android_gms_internal_zzasi) {
        this.buE = new WeakReference(com_google_android_gms_internal_zzasi);
    }

    public void onCustomTabsServiceConnected(ComponentName componentName, CustomTabsClient customTabsClient) {
        zzasi com_google_android_gms_internal_zzasi = (zzasi) this.buE.get();
        if (com_google_android_gms_internal_zzasi != null) {
            com_google_android_gms_internal_zzasi.zza(customTabsClient);
        }
    }

    public void onServiceDisconnected(ComponentName componentName) {
        zzasi com_google_android_gms_internal_zzasi = (zzasi) this.buE.get();
        if (com_google_android_gms_internal_zzasi != null) {
            com_google_android_gms_internal_zzasi.zzmg();
        }
    }
}
