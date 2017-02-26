package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.ads.mediation.AbstractAdViewAdapter;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.reward.client.RewardedVideoAdRequestParcel;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzb;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzko.zza;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

@zzji
public class zzkb extends zzb implements zzkf {
    private static final zzgy zzcqy;
    private final Map<String, zzkj> zzcqz;
    private boolean zzcra;

    /* renamed from: com.google.android.gms.internal.zzkb.1 */
    class C14201 implements Runnable {
        final /* synthetic */ zzkb zzcrb;

        C14201(zzkb com_google_android_gms_internal_zzkb) {
            this.zzcrb = com_google_android_gms_internal_zzkb;
        }

        public void run() {
            this.zzcrb.zzh(1);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzkb.2 */
    class C14212 implements Runnable {
        final /* synthetic */ zza zzamk;
        final /* synthetic */ zzkb zzcrb;

        C14212(zzkb com_google_android_gms_internal_zzkb, zza com_google_android_gms_internal_zzko_zza) {
            this.zzcrb = com_google_android_gms_internal_zzkb;
            this.zzamk = com_google_android_gms_internal_zzko_zza;
        }

        public void run() {
            this.zzcrb.zzb(new zzko(this.zzamk, null, null, null, null, null, null, null));
        }
    }

    static {
        zzcqy = new zzgy();
    }

    public zzkb(Context context, zzd com_google_android_gms_ads_internal_zzd, AdSizeParcel adSizeParcel, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel) {
        super(context, adSizeParcel, null, com_google_android_gms_internal_zzgz, versionInfoParcel, com_google_android_gms_ads_internal_zzd);
        this.zzcqz = new HashMap();
    }

    private zza zzd(zza com_google_android_gms_internal_zzko_zza) {
        zzkx.m1697v("Creating mediation ad response for non-mediated rewarded ad.");
        try {
            String jSONObject = zzjm.zzc(com_google_android_gms_internal_zzko_zza.zzcsu).toString();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(AbstractAdViewAdapter.AD_UNIT_ID_PARAMETER, com_google_android_gms_internal_zzko_zza.zzcmx.zzarg);
            zzgp com_google_android_gms_internal_zzgp = new zzgp(jSONObject, null, Arrays.asList(new String[]{"com.google.ads.mediation.admob.AdMobAdapter"}), null, null, Collections.emptyList(), Collections.emptyList(), jSONObject2.toString(), null, Collections.emptyList(), Collections.emptyList(), null, null, null, null, null, Collections.emptyList());
            return new zza(com_google_android_gms_internal_zzko_zza.zzcmx, com_google_android_gms_internal_zzko_zza.zzcsu, new zzgq(Arrays.asList(new zzgp[]{com_google_android_gms_internal_zzgp}), -1, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), false, BuildConfig.FLAVOR, -1, 0, 1, null, 0, -1, -1, false), com_google_android_gms_internal_zzko_zza.zzarm, com_google_android_gms_internal_zzko_zza.errorCode, com_google_android_gms_internal_zzko_zza.zzcso, com_google_android_gms_internal_zzko_zza.zzcsp, com_google_android_gms_internal_zzko_zza.zzcsi);
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Unable to generate ad state for non-mediated rewarded video.", e);
            return zze(com_google_android_gms_internal_zzko_zza);
        }
    }

    private zza zze(zza com_google_android_gms_internal_zzko_zza) {
        return new zza(com_google_android_gms_internal_zzko_zza.zzcmx, com_google_android_gms_internal_zzko_zza.zzcsu, null, com_google_android_gms_internal_zzko_zza.zzarm, 0, com_google_android_gms_internal_zzko_zza.zzcso, com_google_android_gms_internal_zzko_zza.zzcsp, com_google_android_gms_internal_zzko_zza.zzcsi);
    }

    public void destroy() {
        zzaa.zzhs("destroy must be called on the main UI thread.");
        for (String str : this.zzcqz.keySet()) {
            String str2;
            try {
                zzkj com_google_android_gms_internal_zzkj = (zzkj) this.zzcqz.get(str2);
                if (!(com_google_android_gms_internal_zzkj == null || com_google_android_gms_internal_zzkj.zzuc() == null)) {
                    com_google_android_gms_internal_zzkj.zzuc().destroy();
                }
            } catch (RemoteException e) {
                String str3 = "Fail to destroy adapter: ";
                str2 = String.valueOf(str2);
                com.google.android.gms.ads.internal.util.client.zzb.zzdi(str2.length() != 0 ? str3.concat(str2) : new String(str3));
            }
        }
    }

    public boolean isLoaded() {
        zzaa.zzhs("isLoaded must be called on the main UI thread.");
        return this.zzaly.zzark == null && this.zzaly.zzarl == null && this.zzaly.zzarn != null && !this.zzcra;
    }

    public void onContextChanged(@NonNull Context context) {
        for (zzkj zzuc : this.zzcqz.values()) {
            try {
                zzuc.zzuc().zzj(zze.zzac(context));
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Unable to call Adapter.onContextChanged.", e);
            }
        }
    }

    public void onRewardedVideoAdClosed() {
        zzek();
    }

    public void onRewardedVideoAdLeftApplication() {
        zzel();
    }

    public void onRewardedVideoAdOpened() {
        zza(this.zzaly.zzarn, false);
        zzem();
    }

    public void onRewardedVideoStarted() {
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzbwm == null)) {
            zzu.zzhf().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, this.zzaly.zzarn, this.zzaly.zzarg, false, this.zzaly.zzarn.zzbwm.zzbvb);
        }
        zzeo();
    }

    public void pause() {
        zzaa.zzhs("pause must be called on the main UI thread.");
        for (String str : this.zzcqz.keySet()) {
            String str2;
            try {
                zzkj com_google_android_gms_internal_zzkj = (zzkj) this.zzcqz.get(str2);
                if (!(com_google_android_gms_internal_zzkj == null || com_google_android_gms_internal_zzkj.zzuc() == null)) {
                    com_google_android_gms_internal_zzkj.zzuc().pause();
                }
            } catch (RemoteException e) {
                String str3 = "Fail to pause adapter: ";
                str2 = String.valueOf(str2);
                com.google.android.gms.ads.internal.util.client.zzb.zzdi(str2.length() != 0 ? str3.concat(str2) : new String(str3));
            }
        }
    }

    public void resume() {
        zzaa.zzhs("resume must be called on the main UI thread.");
        for (String str : this.zzcqz.keySet()) {
            String str2;
            try {
                zzkj com_google_android_gms_internal_zzkj = (zzkj) this.zzcqz.get(str2);
                if (!(com_google_android_gms_internal_zzkj == null || com_google_android_gms_internal_zzkj.zzuc() == null)) {
                    com_google_android_gms_internal_zzkj.zzuc().resume();
                }
            } catch (RemoteException e) {
                String str3 = "Fail to resume adapter: ";
                str2 = String.valueOf(str2);
                com.google.android.gms.ads.internal.util.client.zzb.zzdi(str2.length() != 0 ? str3.concat(str2) : new String(str3));
            }
        }
    }

    public void zza(RewardedVideoAdRequestParcel rewardedVideoAdRequestParcel) {
        zzaa.zzhs("loadAd must be called on the main UI thread.");
        if (TextUtils.isEmpty(rewardedVideoAdRequestParcel.zzarg)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("Invalid ad unit id. Aborting.");
            zzlb.zzcvl.post(new C14201(this));
            return;
        }
        this.zzcra = false;
        this.zzaly.zzarg = rewardedVideoAdRequestParcel.zzarg;
        super.zzb(rewardedVideoAdRequestParcel.zzcju);
    }

    public void zza(zza com_google_android_gms_internal_zzko_zza, zzdz com_google_android_gms_internal_zzdz) {
        if (com_google_android_gms_internal_zzko_zza.errorCode != -2) {
            zzlb.zzcvl.post(new C14212(this, com_google_android_gms_internal_zzko_zza));
            return;
        }
        this.zzaly.zzaro = com_google_android_gms_internal_zzko_zza;
        if (com_google_android_gms_internal_zzko_zza.zzcsk == null) {
            this.zzaly.zzaro = zzd(com_google_android_gms_internal_zzko_zza);
        }
        this.zzaly.zzasi = 0;
        this.zzaly.zzarl = zzu.zzgl().zza(this.zzaly.zzahs, this.zzaly.zzaro, this);
    }

    protected boolean zza(AdRequestParcel adRequestParcel, zzko com_google_android_gms_internal_zzko, boolean z) {
        return false;
    }

    public boolean zza(zzko com_google_android_gms_internal_zzko, zzko com_google_android_gms_internal_zzko2) {
        return true;
    }

    public void zzc(@Nullable RewardItemParcel rewardItemParcel) {
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzbwm == null)) {
            zzu.zzhf().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, this.zzaly.zzarn, this.zzaly.zzarg, false, this.zzaly.zzarn.zzbwm.zzbvc);
        }
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzcsk == null || TextUtils.isEmpty(this.zzaly.zzarn.zzcsk.zzbvr))) {
            rewardItemParcel = new RewardItemParcel(this.zzaly.zzarn.zzcsk.zzbvr, this.zzaly.zzarn.zzcsk.zzbvs);
        }
        zza(rewardItemParcel);
    }

    @Nullable
    public zzkj zzcp(String str) {
        Throwable th;
        String str2;
        String valueOf;
        zzkj com_google_android_gms_internal_zzkj = (zzkj) this.zzcqz.get(str);
        if (com_google_android_gms_internal_zzkj != null) {
            return com_google_android_gms_internal_zzkj;
        }
        zzkj com_google_android_gms_internal_zzkj2;
        try {
            com_google_android_gms_internal_zzkj2 = new zzkj(("com.google.ads.mediation.admob.AdMobAdapter".equals(str) ? zzcqy : this.zzamf).zzbu(str), this);
            try {
                this.zzcqz.put(str, com_google_android_gms_internal_zzkj2);
                return com_google_android_gms_internal_zzkj2;
            } catch (Throwable e) {
                th = e;
                str2 = "Fail to instantiate adapter ";
                valueOf = String.valueOf(str);
                com.google.android.gms.ads.internal.util.client.zzb.zzc(valueOf.length() == 0 ? new String(str2) : str2.concat(valueOf), th);
                return com_google_android_gms_internal_zzkj2;
            }
        } catch (Throwable e2) {
            th = e2;
            com_google_android_gms_internal_zzkj2 = com_google_android_gms_internal_zzkj;
            str2 = "Fail to instantiate adapter ";
            valueOf = String.valueOf(str);
            if (valueOf.length() == 0) {
            }
            com.google.android.gms.ads.internal.util.client.zzb.zzc(valueOf.length() == 0 ? new String(str2) : str2.concat(valueOf), th);
            return com_google_android_gms_internal_zzkj2;
        }
    }

    public void zztu() {
        zzaa.zzhs("showAd must be called on the main UI thread.");
        if (isLoaded()) {
            this.zzcra = true;
            zzkj zzcp = zzcp(this.zzaly.zzarn.zzbwo);
            if (zzcp != null && zzcp.zzuc() != null) {
                try {
                    zzcp.zzuc().showVideo();
                    return;
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not call showVideo.", e);
                    return;
                }
            }
            return;
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzdi("The reward video has not loaded.");
    }

    public void zztv() {
        onAdClicked();
    }
}
