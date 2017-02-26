package com.google.android.gms.ads.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.zzz.zza;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzle;

@zzji
public class zzo extends zza {
    private static final Object zzaox;
    @Nullable
    private static zzo zzaoy;
    private final Context mContext;
    private final Object zzako;
    private boolean zzaoz;
    private boolean zzapa;
    private float zzapb;
    private VersionInfoParcel zzapc;

    static {
        zzaox = new Object();
    }

    zzo(Context context, VersionInfoParcel versionInfoParcel) {
        this.zzako = new Object();
        this.zzapb = -1.0f;
        this.mContext = context;
        this.zzapc = versionInfoParcel;
        this.zzaoz = false;
    }

    public static zzo zza(Context context, VersionInfoParcel versionInfoParcel) {
        zzo com_google_android_gms_ads_internal_zzo;
        synchronized (zzaox) {
            if (zzaoy == null) {
                zzaoy = new zzo(context.getApplicationContext(), versionInfoParcel);
            }
            com_google_android_gms_ads_internal_zzo = zzaoy;
        }
        return com_google_android_gms_ads_internal_zzo;
    }

    @Nullable
    public static zzo zzfq() {
        zzo com_google_android_gms_ads_internal_zzo;
        synchronized (zzaox) {
            com_google_android_gms_ads_internal_zzo = zzaoy;
        }
        return com_google_android_gms_ads_internal_zzo;
    }

    public void initialize() {
        synchronized (zzaox) {
            if (this.zzaoz) {
                zzb.zzdi("Mobile ads is initialized already.");
                return;
            }
            this.zzaoz = true;
            zzdr.initialize(this.mContext);
            zzu.zzgq().zzc(this.mContext, this.zzapc);
            zzu.zzgr().initialize(this.mContext);
        }
    }

    public void setAppMuted(boolean z) {
        synchronized (this.zzako) {
            this.zzapa = z;
        }
    }

    public void setAppVolume(float f) {
        synchronized (this.zzako) {
            this.zzapb = f;
        }
    }

    public void zzb(zzd com_google_android_gms_dynamic_zzd, String str) {
        zzle zzc = zzc(com_google_android_gms_dynamic_zzd, str);
        if (zzc == null) {
            zzb.m1695e("Context is null. Failed to open debug menu.");
        } else {
            zzc.showDialog();
        }
    }

    @Nullable
    protected zzle zzc(zzd com_google_android_gms_dynamic_zzd, String str) {
        if (com_google_android_gms_dynamic_zzd == null) {
            return null;
        }
        Context context = (Context) zze.zzae(com_google_android_gms_dynamic_zzd);
        if (context == null) {
            return null;
        }
        zzle com_google_android_gms_internal_zzle = new zzle(context);
        com_google_android_gms_internal_zzle.setAdUnitId(str);
        return com_google_android_gms_internal_zzle;
    }

    public float zzfr() {
        float f;
        synchronized (this.zzako) {
            f = this.zzapb;
        }
        return f;
    }

    public boolean zzfs() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzapb >= 0.0f;
        }
        return z;
    }

    public boolean zzft() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzapa;
        }
        return z;
    }

    public void zzz(String str) {
        zzdr.initialize(this.mContext);
        if (!TextUtils.isEmpty(str) && ((Boolean) zzdr.zzbjv.get()).booleanValue()) {
            zzu.zzhi().zza(this.mContext, this.zzapc, true, null, str, null);
        }
    }
}
