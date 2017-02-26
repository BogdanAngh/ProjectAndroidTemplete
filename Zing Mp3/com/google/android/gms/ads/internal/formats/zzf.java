package com.google.android.gms.ads.internal.formats;

import android.support.v4.util.SimpleArrayMap;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzeg;
import com.google.android.gms.internal.zzeo.zza;
import com.google.android.gms.internal.zzji;
import java.util.Arrays;
import java.util.List;

@zzji
public class zzf extends zza implements zzi.zza {
    private final Object zzako;
    private final zza zzbng;
    private zzi zzbnj;
    private final String zzbnm;
    private final SimpleArrayMap<String, zzc> zzbnn;
    private final SimpleArrayMap<String, String> zzbno;

    public zzf(String str, SimpleArrayMap<String, zzc> simpleArrayMap, SimpleArrayMap<String, String> simpleArrayMap2, zza com_google_android_gms_ads_internal_formats_zza) {
        this.zzako = new Object();
        this.zzbnm = str;
        this.zzbnn = simpleArrayMap;
        this.zzbno = simpleArrayMap2;
        this.zzbng = com_google_android_gms_ads_internal_formats_zza;
    }

    public List<String> getAvailableAssetNames() {
        int i = 0;
        String[] strArr = new String[(this.zzbnn.size() + this.zzbno.size())];
        int i2 = 0;
        for (int i3 = 0; i3 < this.zzbnn.size(); i3++) {
            strArr[i2] = (String) this.zzbnn.keyAt(i3);
            i2++;
        }
        while (i < this.zzbno.size()) {
            strArr[i2] = (String) this.zzbno.keyAt(i);
            i++;
            i2++;
        }
        return Arrays.asList(strArr);
    }

    public String getCustomTemplateId() {
        return this.zzbnm;
    }

    public void performClick(String str) {
        synchronized (this.zzako) {
            if (this.zzbnj == null) {
                zzb.m1695e("Attempt to call performClick before ad initialized.");
                return;
            }
            this.zzbnj.zza(null, str, null, null, null);
        }
    }

    public void recordImpression() {
        synchronized (this.zzako) {
            if (this.zzbnj == null) {
                zzb.m1695e("Attempt to perform recordImpression before ad initialized.");
                return;
            }
            this.zzbnj.zzb(null, null);
        }
    }

    public void zzb(zzi com_google_android_gms_ads_internal_formats_zzi) {
        synchronized (this.zzako) {
            this.zzbnj = com_google_android_gms_ads_internal_formats_zzi;
        }
    }

    public String zzba(String str) {
        return (String) this.zzbno.get(str);
    }

    public zzeg zzbb(String str) {
        return (zzeg) this.zzbnn.get(str);
    }

    public String zzmq() {
        return "3";
    }

    public zza zzmr() {
        return this.zzbng;
    }
}
