package com.google.android.gms.internal;

import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.common.internal.zzaa;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class zznc extends zzg<zznc> {
    private String cq;
    private int cr;
    private int cs;
    private String ct;
    private String cu;
    private boolean cv;
    private boolean cw;

    public zznc() {
        this(false);
    }

    public zznc(boolean z) {
        this(z, zzabg());
    }

    public zznc(boolean z, int i) {
        zzaa.zzgp(i);
        this.cr = i;
        this.cw = z;
    }

    static int zzabg() {
        UUID randomUUID = UUID.randomUUID();
        int leastSignificantBits = (int) (randomUUID.getLeastSignificantBits() & 2147483647L);
        if (leastSignificantBits != 0) {
            return leastSignificantBits;
        }
        leastSignificantBits = (int) (randomUUID.getMostSignificantBits() & 2147483647L);
        if (leastSignificantBits != 0) {
            return leastSignificantBits;
        }
        Log.e("GAv4", "UUID.randomUUID() returned 0.");
        return ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
    }

    private void zzabk() {
    }

    public void setScreenName(String str) {
        zzabk();
        this.cq = str;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put("screenName", this.cq);
        hashMap.put("interstitial", Boolean.valueOf(this.cv));
        hashMap.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC, Boolean.valueOf(this.cw));
        hashMap.put("screenId", Integer.valueOf(this.cr));
        hashMap.put("referrerScreenId", Integer.valueOf(this.cs));
        hashMap.put("referrerScreenName", this.ct);
        hashMap.put("referrerUri", this.cu);
        return zzg.zzj(hashMap);
    }

    public void zza(zznc com_google_android_gms_internal_zznc) {
        if (!TextUtils.isEmpty(this.cq)) {
            com_google_android_gms_internal_zznc.setScreenName(this.cq);
        }
        if (this.cr != 0) {
            com_google_android_gms_internal_zznc.zzcd(this.cr);
        }
        if (this.cs != 0) {
            com_google_android_gms_internal_zznc.zzce(this.cs);
        }
        if (!TextUtils.isEmpty(this.ct)) {
            com_google_android_gms_internal_zznc.zzek(this.ct);
        }
        if (!TextUtils.isEmpty(this.cu)) {
            com_google_android_gms_internal_zznc.zzel(this.cu);
        }
        if (this.cv) {
            com_google_android_gms_internal_zznc.zzav(this.cv);
        }
        if (this.cw) {
            com_google_android_gms_internal_zznc.zzau(this.cw);
        }
    }

    public String zzabh() {
        return this.cq;
    }

    public int zzabi() {
        return this.cr;
    }

    public String zzabj() {
        return this.cu;
    }

    public void zzau(boolean z) {
        zzabk();
        this.cw = z;
    }

    public void zzav(boolean z) {
        zzabk();
        this.cv = z;
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zznc) com_google_android_gms_analytics_zzg);
    }

    public void zzcd(int i) {
        zzabk();
        this.cr = i;
    }

    public void zzce(int i) {
        zzabk();
        this.cs = i;
    }

    public void zzek(String str) {
        zzabk();
        this.ct = str;
    }

    public void zzel(String str) {
        zzabk();
        if (TextUtils.isEmpty(str)) {
            this.cu = null;
        } else {
            this.cu = str;
        }
    }
}
