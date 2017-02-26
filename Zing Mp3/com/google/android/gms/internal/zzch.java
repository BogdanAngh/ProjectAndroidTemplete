package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.view.MotionEvent;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzcj.zza;

@zzji
public final class zzch extends zza {
    private final zzau zzakt;
    private final zzav zzaku;
    private final zzas zzakv;
    private boolean zzakw;

    public zzch(String str, Context context, boolean z) {
        this.zzakw = false;
        this.zzakt = zzau.zza(str, context, z);
        this.zzaku = new zzav(this.zzakt);
        this.zzakv = z ? null : zzas.zzc(context);
    }

    private zzd zza(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2, boolean z) {
        try {
            Uri uri = (Uri) zze.zzae(com_google_android_gms_dynamic_zzd);
            Context context = (Context) zze.zzae(com_google_android_gms_dynamic_zzd2);
            return zze.zzac(z ? this.zzaku.zza(uri, context) : this.zzaku.zzb(uri, context));
        } catch (zzaw e) {
            return null;
        }
    }

    public zzd zza(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2) {
        return zza(com_google_android_gms_dynamic_zzd, com_google_android_gms_dynamic_zzd2, true);
    }

    public String zza(zzd com_google_android_gms_dynamic_zzd, String str) {
        return this.zzakt.zzb((Context) zze.zzae(com_google_android_gms_dynamic_zzd), str);
    }

    public String zza(zzd com_google_android_gms_dynamic_zzd, byte[] bArr) {
        Context context = (Context) zze.zzae(com_google_android_gms_dynamic_zzd);
        String zza = this.zzakt.zza(context, bArr);
        if (this.zzakv == null || !this.zzakw) {
            return zza;
        }
        String zza2 = this.zzakv.zza(zza, this.zzakv.zza(context, bArr));
        this.zzakw = false;
        return zza2;
    }

    public boolean zza(zzd com_google_android_gms_dynamic_zzd) {
        return this.zzaku.zza((Uri) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public zzd zzb(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2) {
        return zza(com_google_android_gms_dynamic_zzd, com_google_android_gms_dynamic_zzd2, false);
    }

    public void zzb(String str, String str2) {
        this.zzaku.zzb(str, str2);
    }

    public boolean zzb(zzd com_google_android_gms_dynamic_zzd) {
        return this.zzaku.zzc((Uri) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public boolean zzb(String str, boolean z) {
        if (this.zzakv == null) {
            return false;
        }
        this.zzakv.zza(new Info(str, z));
        this.zzakw = true;
        return true;
    }

    public String zzc(zzd com_google_android_gms_dynamic_zzd) {
        return zza(com_google_android_gms_dynamic_zzd, null);
    }

    public void zzd(zzd com_google_android_gms_dynamic_zzd) {
        this.zzaku.zza((MotionEvent) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public String zzdx() {
        return "ms";
    }

    public void zzm(String str) {
        this.zzaku.zzm(str);
    }
}
