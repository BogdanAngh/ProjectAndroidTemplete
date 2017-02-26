package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.reward.client.RewardedVideoAdRequestParcel;
import com.google.android.gms.ads.internal.reward.client.zzb.zza;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.dynamic.zze;

@zzji
public class zzka extends zza {
    private final Context mContext;
    private final Object zzako;
    private final VersionInfoParcel zzanu;
    private final zzkb zzcqx;

    zzka(Context context, VersionInfoParcel versionInfoParcel, zzkb com_google_android_gms_internal_zzkb) {
        this.zzako = new Object();
        this.mContext = context;
        this.zzanu = versionInfoParcel;
        this.zzcqx = com_google_android_gms_internal_zzkb;
    }

    public zzka(Context context, zzd com_google_android_gms_ads_internal_zzd, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel) {
        this(context, versionInfoParcel, new zzkb(context, com_google_android_gms_ads_internal_zzd, AdSizeParcel.zzkc(), com_google_android_gms_internal_zzgz, versionInfoParcel));
    }

    public void destroy() {
        zzh(null);
    }

    public boolean isLoaded() {
        boolean isLoaded;
        synchronized (this.zzako) {
            isLoaded = this.zzcqx.isLoaded();
        }
        return isLoaded;
    }

    public void pause() {
        zzf(null);
    }

    public void resume() {
        zzg(null);
    }

    public void setUserId(String str) {
        zzb.zzdi("RewardedVideoAd.setUserId() is deprecated. Please do not call this method.");
    }

    public void show() {
        synchronized (this.zzako) {
            this.zzcqx.zztu();
        }
    }

    public void zza(RewardedVideoAdRequestParcel rewardedVideoAdRequestParcel) {
        synchronized (this.zzako) {
            this.zzcqx.zza(rewardedVideoAdRequestParcel);
        }
    }

    public void zza(com.google.android.gms.ads.internal.reward.client.zzd com_google_android_gms_ads_internal_reward_client_zzd) {
        synchronized (this.zzako) {
            this.zzcqx.zza(com_google_android_gms_ads_internal_reward_client_zzd);
        }
    }

    public void zzf(com.google.android.gms.dynamic.zzd com_google_android_gms_dynamic_zzd) {
        synchronized (this.zzako) {
            this.zzcqx.pause();
        }
    }

    public void zzg(com.google.android.gms.dynamic.zzd com_google_android_gms_dynamic_zzd) {
        synchronized (this.zzako) {
            Context context = com_google_android_gms_dynamic_zzd == null ? null : (Context) zze.zzae(com_google_android_gms_dynamic_zzd);
            if (context != null) {
                try {
                    this.zzcqx.onContextChanged(context);
                } catch (Throwable e) {
                    zzb.zzc("Unable to extract updated context.", e);
                }
            }
            this.zzcqx.resume();
        }
    }

    public void zzh(com.google.android.gms.dynamic.zzd com_google_android_gms_dynamic_zzd) {
        synchronized (this.zzako) {
            this.zzcqx.destroy();
        }
    }
}
