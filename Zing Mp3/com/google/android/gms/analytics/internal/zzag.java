package com.google.android.gms.analytics.internal;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;
import com.google.android.gms.common.internal.zzaa;

class zzag extends BroadcastReceiver {
    static final String fx;
    private final zzf cQ;
    private boolean fy;
    private boolean fz;

    static {
        fx = zzag.class.getName();
    }

    zzag(zzf com_google_android_gms_analytics_internal_zzf) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzf);
        this.cQ = com_google_android_gms_analytics_internal_zzf;
    }

    private Context getContext() {
        return this.cQ.getContext();
    }

    private zzaf zzaca() {
        return this.cQ.zzaca();
    }

    private void zzagi() {
        zzaca();
        zzzg();
    }

    private zzb zzzg() {
        return this.cQ.zzzg();
    }

    public boolean isConnected() {
        if (!this.fy) {
            this.cQ.zzaca().zzev("Connectivity unknown. Receiver not registered");
        }
        return this.fz;
    }

    public boolean isRegistered() {
        return this.fy;
    }

    public void onReceive(Context context, Intent intent) {
        zzagi();
        String action = intent.getAction();
        this.cQ.zzaca().zza("NetworkBroadcastReceiver received action", action);
        if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
            boolean zzagk = zzagk();
            if (this.fz != zzagk) {
                this.fz = zzagk;
                zzzg().zzaw(zzagk);
            }
        } else if (!"com.google.analytics.RADIO_POWERED".equals(action)) {
            this.cQ.zzaca().zzd("NetworkBroadcastReceiver received unknown action", action);
        } else if (!intent.hasExtra(fx)) {
            zzzg().zzabv();
        }
    }

    public void unregister() {
        if (isRegistered()) {
            this.cQ.zzaca().zzes("Unregistering connectivity change receiver");
            this.fy = false;
            this.fz = false;
            try {
                getContext().unregisterReceiver(this);
            } catch (IllegalArgumentException e) {
                zzaca().zze("Failed to unregister the network broadcast receiver", e);
            }
        }
    }

    public void zzagh() {
        zzagi();
        if (!this.fy) {
            Context context = getContext();
            context.registerReceiver(this, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            IntentFilter intentFilter = new IntentFilter("com.google.analytics.RADIO_POWERED");
            intentFilter.addCategory(context.getPackageName());
            context.registerReceiver(this, intentFilter);
            this.fz = zzagk();
            this.cQ.zzaca().zza("Registering connectivity change receiver. Network connected", Boolean.valueOf(this.fz));
            this.fy = true;
        }
    }

    public void zzagj() {
        if (VERSION.SDK_INT > 10) {
            Context context = getContext();
            Intent intent = new Intent("com.google.analytics.RADIO_POWERED");
            intent.addCategory(context.getPackageName());
            intent.putExtra(fx, true);
            context.sendOrderedBroadcast(intent, null);
        }
    }

    protected boolean zzagk() {
        try {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) getContext().getSystemService("connectivity")).getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (SecurityException e) {
            return false;
        }
    }
}
