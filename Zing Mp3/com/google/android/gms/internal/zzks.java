package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;

@zzji
public class zzks {
    private final Object zzako;
    final String zzctq;
    int zzcui;
    long zzcuj;
    long zzcuk;
    int zzcul;
    int zzcum;
    int zzcun;

    public zzks(String str) {
        this.zzcuj = -1;
        this.zzcuk = -1;
        this.zzcul = -1;
        this.zzcui = -1;
        this.zzako = new Object();
        this.zzcum = 0;
        this.zzcun = 0;
        this.zzctq = str;
    }

    public static boolean zzx(Context context) {
        int identifier = context.getResources().getIdentifier("Theme.Translucent", TtmlNode.TAG_STYLE, "android");
        if (identifier == 0) {
            zzb.zzdh("Please set theme of AdActivity to @android:style/Theme.Translucent to enable transparent background interstitial ad.");
            return false;
        }
        try {
            if (identifier == context.getPackageManager().getActivityInfo(new ComponentName(context.getPackageName(), AdActivity.CLASS_NAME), 0).theme) {
                return true;
            }
            zzb.zzdh("Please set theme of AdActivity to @android:style/Theme.Translucent to enable transparent background interstitial ad.");
            return false;
        } catch (NameNotFoundException e) {
            zzb.zzdi("Fail to fetch AdActivity theme");
            zzb.zzdh("Please set theme of AdActivity to @android:style/Theme.Translucent to enable transparent background interstitial ad.");
            return false;
        }
    }

    public void zzb(AdRequestParcel adRequestParcel, long j) {
        synchronized (this.zzako) {
            if (this.zzcuk == -1) {
                if (j - zzu.zzgq().zzvc() > ((Long) zzdr.zzbgb.get()).longValue()) {
                    zzbg(-1);
                } else {
                    zzbg(zzu.zzgq().zzvd());
                }
                this.zzcuk = j;
                this.zzcuj = this.zzcuk;
            } else {
                this.zzcuj = j;
            }
            if (adRequestParcel.extras == null || adRequestParcel.extras.getInt("gw", 2) != 1) {
                this.zzcul++;
                this.zzcui++;
                return;
            }
        }
    }

    public void zzbg(int i) {
        this.zzcui = i;
    }

    public Bundle zze(Context context, String str) {
        Bundle bundle;
        synchronized (this.zzako) {
            bundle = new Bundle();
            bundle.putString("session_id", this.zzctq);
            bundle.putLong("basets", this.zzcuk);
            bundle.putLong("currts", this.zzcuj);
            bundle.putString("seq_num", str);
            bundle.putInt("preqs", this.zzcul);
            bundle.putInt("preqs_in_session", this.zzcui);
            bundle.putInt("pclick", this.zzcum);
            bundle.putInt("pimp", this.zzcun);
            bundle.putBoolean("support_transparent_background", zzx(context));
        }
        return bundle;
    }

    public void zzug() {
        synchronized (this.zzako) {
            this.zzcun++;
        }
    }

    public void zzuh() {
        synchronized (this.zzako) {
            this.zzcum++;
        }
    }

    public int zzvd() {
        return this.zzcui;
    }

    public long zzvk() {
        return this.zzcuk;
    }
}
