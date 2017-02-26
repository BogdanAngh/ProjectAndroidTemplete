package com.google.android.gms.internal;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.internal.zzjv.zza;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

@zzji
public class zzjj {
    @Nullable
    public Location zzayt;
    @Nullable
    public String zzcjw;
    @Nullable
    public Bundle zzckb;
    @Nullable
    public List<String> zzckj;
    @Nullable
    public Bundle zzcmu;
    @Nullable
    public zza zzcmv;
    @Nullable
    public String zzcmw;
    public AdRequestInfoParcel zzcmx;
    public zzjr zzcmy;
    public JSONObject zzcmz;

    public zzjj() {
        this.zzcmz = new JSONObject();
        this.zzckj = new ArrayList();
    }

    public zzjj zza(zzjr com_google_android_gms_internal_zzjr) {
        this.zzcmy = com_google_android_gms_internal_zzjr;
        return this;
    }

    public zzjj zza(zza com_google_android_gms_internal_zzjv_zza) {
        this.zzcmv = com_google_android_gms_internal_zzjv_zza;
        return this;
    }

    public zzjj zzc(Location location) {
        this.zzayt = location;
        return this;
    }

    public zzjj zzcm(String str) {
        this.zzcjw = str;
        return this;
    }

    public zzjj zzcn(String str) {
        this.zzcmw = str;
        return this;
    }

    public zzjj zze(Bundle bundle) {
        this.zzcmu = bundle;
        return this;
    }

    public zzjj zzf(Bundle bundle) {
        this.zzckb = bundle;
        return this;
    }

    public zzjj zzf(AdRequestInfoParcel adRequestInfoParcel) {
        this.zzcmx = adRequestInfoParcel;
        return this;
    }

    public zzjj zzi(JSONObject jSONObject) {
        this.zzcmz = jSONObject;
        return this;
    }

    public zzjj zzk(List<String> list) {
        if (list == null) {
            this.zzckj.clear();
        }
        this.zzckj = list;
        return this;
    }
}
