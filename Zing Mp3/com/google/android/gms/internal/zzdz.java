package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.zzu;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@zzji
public class zzdz {
    private final Object zzako;
    boolean zzblg;
    private final List<zzdx> zzblx;
    private final Map<String, String> zzbly;
    private String zzblz;
    private zzdx zzbma;
    @Nullable
    private zzdz zzbmb;

    public zzdz(boolean z, String str, String str2) {
        this.zzblx = new LinkedList();
        this.zzbly = new LinkedHashMap();
        this.zzako = new Object();
        this.zzblg = z;
        this.zzbly.put(NativeProtocol.WEB_DIALOG_ACTION, str);
        this.zzbly.put("ad_format", str2);
    }

    public boolean zza(zzdx com_google_android_gms_internal_zzdx, long j, String... strArr) {
        synchronized (this.zzako) {
            for (String com_google_android_gms_internal_zzdx2 : strArr) {
                this.zzblx.add(new zzdx(j, com_google_android_gms_internal_zzdx2, com_google_android_gms_internal_zzdx));
            }
        }
        return true;
    }

    public boolean zza(@Nullable zzdx com_google_android_gms_internal_zzdx, String... strArr) {
        return (!this.zzblg || com_google_android_gms_internal_zzdx == null) ? false : zza(com_google_android_gms_internal_zzdx, zzu.zzgs().elapsedRealtime(), strArr);
    }

    public void zzaz(String str) {
        if (this.zzblg) {
            synchronized (this.zzako) {
                this.zzblz = str;
            }
        }
    }

    @Nullable
    public zzdx zzc(long j) {
        return !this.zzblg ? null : new zzdx(j, null, null);
    }

    public void zzc(@Nullable zzdz com_google_android_gms_internal_zzdz) {
        synchronized (this.zzako) {
            this.zzbmb = com_google_android_gms_internal_zzdz;
        }
    }

    public void zzg(String str, String str2) {
        if (this.zzblg && !TextUtils.isEmpty(str2)) {
            zzdt zzuu = zzu.zzgq().zzuu();
            if (zzuu != null) {
                synchronized (this.zzako) {
                    zzuu.zzax(str).zza(this.zzbly, str, str2);
                }
            }
        }
    }

    public zzdx zzlz() {
        return zzc(zzu.zzgs().elapsedRealtime());
    }

    public void zzma() {
        synchronized (this.zzako) {
            this.zzbma = zzlz();
        }
    }

    public String zzmb() {
        String stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder();
        synchronized (this.zzako) {
            for (zzdx com_google_android_gms_internal_zzdx : this.zzblx) {
                long time = com_google_android_gms_internal_zzdx.getTime();
                String zzlw = com_google_android_gms_internal_zzdx.zzlw();
                zzdx com_google_android_gms_internal_zzdx2 = com_google_android_gms_internal_zzdx2.zzlx();
                if (com_google_android_gms_internal_zzdx2 != null && time > 0) {
                    stringBuilder2.append(zzlw).append('.').append(time - com_google_android_gms_internal_zzdx2.getTime()).append(',');
                }
            }
            this.zzblx.clear();
            if (!TextUtils.isEmpty(this.zzblz)) {
                stringBuilder2.append(this.zzblz);
            } else if (stringBuilder2.length() > 0) {
                stringBuilder2.setLength(stringBuilder2.length() - 1);
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }

    Map<String, String> zzmc() {
        Map<String, String> map;
        synchronized (this.zzako) {
            zzdt zzuu = zzu.zzgq().zzuu();
            if (zzuu == null || this.zzbmb == null) {
                map = this.zzbly;
            } else {
                map = zzuu.zza(this.zzbly, this.zzbmb.zzmc());
            }
        }
        return map;
    }

    public zzdx zzmd() {
        zzdx com_google_android_gms_internal_zzdx;
        synchronized (this.zzako) {
            com_google_android_gms_internal_zzdx = this.zzbma;
        }
        return com_google_android_gms_internal_zzdx;
    }
}
