package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.reward.mediation.client.zza.zza;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;

@zzji
public class zzkg extends zza {
    private volatile zzkh zzcrf;
    private volatile zzke zzcrr;
    private volatile zzkf zzcrs;

    public zzkg(zzkf com_google_android_gms_internal_zzkf) {
        this.zzcrs = com_google_android_gms_internal_zzkf;
    }

    public void zza(zzd com_google_android_gms_dynamic_zzd, RewardItemParcel rewardItemParcel) {
        if (this.zzcrs != null) {
            this.zzcrs.zzc(rewardItemParcel);
        }
    }

    public void zza(zzke com_google_android_gms_internal_zzke) {
        this.zzcrr = com_google_android_gms_internal_zzke;
    }

    public void zza(zzkh com_google_android_gms_internal_zzkh) {
        this.zzcrf = com_google_android_gms_internal_zzkh;
    }

    public void zzb(zzd com_google_android_gms_dynamic_zzd, int i) {
        if (this.zzcrr != null) {
            this.zzcrr.zzbb(i);
        }
    }

    public void zzc(zzd com_google_android_gms_dynamic_zzd, int i) {
        if (this.zzcrf != null) {
            this.zzcrf.zza(zze.zzae(com_google_android_gms_dynamic_zzd).getClass().getName(), i);
        }
    }

    public void zzq(zzd com_google_android_gms_dynamic_zzd) {
        if (this.zzcrr != null) {
            this.zzcrr.zzty();
        }
    }

    public void zzr(zzd com_google_android_gms_dynamic_zzd) {
        if (this.zzcrf != null) {
            this.zzcrf.zzcq(zze.zzae(com_google_android_gms_dynamic_zzd).getClass().getName());
        }
    }

    public void zzs(zzd com_google_android_gms_dynamic_zzd) {
        if (this.zzcrs != null) {
            this.zzcrs.onRewardedVideoAdOpened();
        }
    }

    public void zzt(zzd com_google_android_gms_dynamic_zzd) {
        if (this.zzcrs != null) {
            this.zzcrs.onRewardedVideoStarted();
        }
    }

    public void zzu(zzd com_google_android_gms_dynamic_zzd) {
        if (this.zzcrs != null) {
            this.zzcrs.onRewardedVideoAdClosed();
        }
    }

    public void zzv(zzd com_google_android_gms_dynamic_zzd) {
        if (this.zzcrs != null) {
            this.zzcrs.zztv();
        }
    }

    public void zzw(zzd com_google_android_gms_dynamic_zzd) {
        if (this.zzcrs != null) {
            this.zzcrs.onRewardedVideoAdLeftApplication();
        }
    }
}
