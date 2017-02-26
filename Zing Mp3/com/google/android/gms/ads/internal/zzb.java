package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.webkit.CookieManager;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.overlay.zzg;
import com.google.android.gms.ads.internal.overlay.zzm;
import com.google.android.gms.ads.internal.purchase.GInAppPurchaseManagerInfoParcel;
import com.google.android.gms.ads.internal.purchase.zzc;
import com.google.android.gms.ads.internal.purchase.zzd;
import com.google.android.gms.ads.internal.purchase.zzf;
import com.google.android.gms.ads.internal.purchase.zzj;
import com.google.android.gms.ads.internal.purchase.zzk;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel.zza;
import com.google.android.gms.ads.internal.request.CapabilityParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzfg;
import com.google.android.gms.internal.zzgr;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzid;
import com.google.android.gms.internal.zzif;
import com.google.android.gms.internal.zzig;
import com.google.android.gms.internal.zzik;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzko;
import com.google.android.gms.internal.zzkp;
import com.google.android.gms.internal.zzkq;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzmd;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

@zzji
public abstract class zzb extends zza implements zzg, zzj, zzs, zzfg, zzgr {
    private final Messenger mMessenger;
    protected final zzgz zzamf;
    protected transient boolean zzamg;

    /* renamed from: com.google.android.gms.ads.internal.zzb.1 */
    class C11101 implements Runnable {
        final /* synthetic */ Intent zzamh;
        final /* synthetic */ zzb zzami;

        C11101(zzb com_google_android_gms_ads_internal_zzb, Intent intent) {
            this.zzami = com_google_android_gms_ads_internal_zzb;
            this.zzamh = intent;
        }

        public void run() {
            int zzd = zzu.zzha().zzd(this.zzamh);
            zzu.zzha();
            if (!(zzd != 0 || this.zzami.zzaly.zzarn == null || this.zzami.zzaly.zzarn.zzcbm == null || this.zzami.zzaly.zzarn.zzcbm.zzxa() == null)) {
                this.zzami.zzaly.zzarn.zzcbm.zzxa().close();
            }
            this.zzami.zzaly.zzasj = false;
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzb.2 */
    class C11112 implements Runnable {
        final /* synthetic */ zzb zzami;

        C11112(zzb com_google_android_gms_ads_internal_zzb) {
            this.zzami = com_google_android_gms_ads_internal_zzb;
        }

        public void run() {
            this.zzami.zzalx.pause();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzb.3 */
    class C11123 implements Runnable {
        final /* synthetic */ zzb zzami;

        C11123(zzb com_google_android_gms_ads_internal_zzb) {
            this.zzami = com_google_android_gms_ads_internal_zzb;
        }

        public void run() {
            this.zzami.zzalx.resume();
        }
    }

    public zzb(Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        this(new zzv(context, adSizeParcel, str, versionInfoParcel), com_google_android_gms_internal_zzgz, null, com_google_android_gms_ads_internal_zzd);
    }

    protected zzb(zzv com_google_android_gms_ads_internal_zzv, zzgz com_google_android_gms_internal_zzgz, @Nullable zzr com_google_android_gms_ads_internal_zzr, zzd com_google_android_gms_ads_internal_zzd) {
        super(com_google_android_gms_ads_internal_zzv, com_google_android_gms_ads_internal_zzr, com_google_android_gms_ads_internal_zzd);
        this.zzamf = com_google_android_gms_internal_zzgz;
        this.mMessenger = new Messenger(new zzid(this.zzaly.zzahs));
        this.zzamg = false;
    }

    private zza zza(AdRequestParcel adRequestParcel, Bundle bundle, zzkq com_google_android_gms_internal_zzkq) {
        PackageInfo packageInfo;
        ApplicationInfo applicationInfo = this.zzaly.zzahs.getApplicationInfo();
        try {
            packageInfo = this.zzaly.zzahs.getPackageManager().getPackageInfo(applicationInfo.packageName, 0);
        } catch (NameNotFoundException e) {
            packageInfo = null;
        }
        DisplayMetrics displayMetrics = this.zzaly.zzahs.getResources().getDisplayMetrics();
        Bundle bundle2 = null;
        if (!(this.zzaly.zzarj == null || this.zzaly.zzarj.getParent() == null)) {
            int[] iArr = new int[2];
            this.zzaly.zzarj.getLocationOnScreen(iArr);
            int i = iArr[0];
            int i2 = iArr[1];
            int width = this.zzaly.zzarj.getWidth();
            int height = this.zzaly.zzarj.getHeight();
            int i3 = 0;
            if (this.zzaly.zzarj.isShown() && i + width > 0 && i2 + height > 0 && i <= displayMetrics.widthPixels && i2 <= displayMetrics.heightPixels) {
                i3 = 1;
            }
            bundle2 = new Bundle(5);
            bundle2.putInt("x", i);
            bundle2.putInt("y", i2);
            bundle2.putInt("width", width);
            bundle2.putInt("height", height);
            bundle2.putInt("visible", i3);
        }
        String zzus = zzu.zzgq().zzus();
        this.zzaly.zzarp = new zzkp(zzus, this.zzaly.zzarg);
        this.zzaly.zzarp.zzt(adRequestParcel);
        String zza = zzu.zzgm().zza(this.zzaly.zzahs, this.zzaly.zzarj, this.zzaly.zzarm);
        long j = 0;
        if (this.zzaly.zzart != null) {
            try {
                j = this.zzaly.zzart.getValue();
            } catch (RemoteException e2) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("Cannot get correlation id, default to 0.");
            }
        }
        String uuid = UUID.randomUUID().toString();
        Bundle zza2 = zzu.zzgq().zza(this.zzaly.zzahs, this, zzus);
        List arrayList = new ArrayList();
        for (i = 0; i < this.zzaly.zzarz.size(); i++) {
            arrayList.add((String) this.zzaly.zzarz.keyAt(i));
        }
        boolean z = this.zzaly.zzaru != null;
        boolean z2 = this.zzaly.zzarv != null && zzu.zzgq().zzvi();
        zzm com_google_android_gms_ads_internal_overlay_zzm = this.zzamb.zzamr;
        Context context = this.zzaly.zzahs;
        String str = BuildConfig.FLAVOR;
        if (((Boolean) zzdr.zzbkn.get()).booleanValue()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Getting webview cookie from CookieManager.");
            CookieManager zzal = zzu.zzgo().zzal(this.zzaly.zzahs);
            if (zzal != null) {
                str = zzal.getCookie("googleads.g.doubleclick.net");
            }
        }
        String str2 = null;
        if (com_google_android_gms_internal_zzkq != null) {
            str2 = com_google_android_gms_internal_zzkq.zzuo();
        }
        return new zza(bundle2, adRequestParcel, this.zzaly.zzarm, this.zzaly.zzarg, applicationInfo, packageInfo, zzus, zzu.zzgq().getSessionId(), this.zzaly.zzari, zza2, this.zzaly.zzase, arrayList, bundle, zzu.zzgq().zzuw(), this.mMessenger, displayMetrics.widthPixels, displayMetrics.heightPixels, displayMetrics.density, zza, j, uuid, zzdr.zzlq(), this.zzaly.zzarf, this.zzaly.zzasa, new CapabilityParcel(z, z2, false), this.zzaly.zzht(), zzu.zzgm().zzfr(), zzu.zzgm().zzft(), zzu.zzgm().zzai(this.zzaly.zzahs), zzu.zzgm().zzt(this.zzaly.zzarj), this.zzaly.zzahs instanceof Activity, zzu.zzgq().zzvb(), str, str2, zzu.zzgq().zzve(), zzu.zzhj().zzni(), zzu.zzgm().zzvv(), zzu.zzgu().zzwf());
    }

    public String getMediationAdapterClassName() {
        return this.zzaly.zzarn == null ? null : this.zzaly.zzarn.zzbwo;
    }

    public void onAdClicked() {
        if (this.zzaly.zzarn == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("Ad state was null when trying to ping click URLs.");
            return;
        }
        if (!(this.zzaly.zzarn.zzcsk == null || this.zzaly.zzarn.zzcsk.zzbvk == null)) {
            zzu.zzhf().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, this.zzaly.zzarn, this.zzaly.zzarg, false, this.zzaly.zzarn.zzcsk.zzbvk);
        }
        if (!(this.zzaly.zzarn.zzbwm == null || this.zzaly.zzarn.zzbwm.zzbux == null)) {
            zzu.zzhf().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, this.zzaly.zzarn, this.zzaly.zzarg, false, this.zzaly.zzarn.zzbwm.zzbux);
        }
        super.onAdClicked();
    }

    public void onPause() {
        this.zzama.zzl(this.zzaly.zzarn);
    }

    public void onResume() {
        this.zzama.zzm(this.zzaly.zzarn);
    }

    public void pause() {
        zzaa.zzhs("pause must be called on the main UI thread.");
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzcbm == null || !this.zzaly.zzhp())) {
            zzu.zzgo().zzl(this.zzaly.zzarn.zzcbm);
        }
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzbwn == null)) {
            try {
                this.zzaly.zzarn.zzbwn.pause();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("Could not pause mediation adapter.");
            }
        }
        this.zzama.zzl(this.zzaly.zzarn);
        this.zzalx.pause();
    }

    public void recordImpression() {
        zza(this.zzaly.zzarn, false);
    }

    public void resume() {
        zzaa.zzhs("resume must be called on the main UI thread.");
        zzmd com_google_android_gms_internal_zzmd = null;
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzcbm == null)) {
            com_google_android_gms_internal_zzmd = this.zzaly.zzarn.zzcbm;
        }
        if (com_google_android_gms_internal_zzmd != null && this.zzaly.zzhp()) {
            zzu.zzgo().zzm(this.zzaly.zzarn.zzcbm);
        }
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzbwn == null)) {
            try {
                this.zzaly.zzarn.zzbwn.resume();
            } catch (RemoteException e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("Could not resume mediation adapter.");
            }
        }
        if (com_google_android_gms_internal_zzmd == null || !com_google_android_gms_internal_zzmd.zzxj()) {
            this.zzalx.resume();
        }
        this.zzama.zzm(this.zzaly.zzarn);
    }

    public void showInterstitial() {
        throw new IllegalStateException("showInterstitial is not supported for current ad type");
    }

    public void zza(zzig com_google_android_gms_internal_zzig) {
        zzaa.zzhs("setInAppPurchaseListener must be called on the main UI thread.");
        this.zzaly.zzaru = com_google_android_gms_internal_zzig;
    }

    public void zza(zzik com_google_android_gms_internal_zzik, @Nullable String str) {
        zzaa.zzhs("setPlayStorePurchaseParams must be called on the main UI thread.");
        this.zzaly.zzasf = new zzk(str);
        this.zzaly.zzarv = com_google_android_gms_internal_zzik;
        if (!zzu.zzgq().zzuv() && com_google_android_gms_internal_zzik != null) {
            Future future = (Future) new zzc(this.zzaly.zzahs, this.zzaly.zzarv, this.zzaly.zzasf).zzrz();
        }
    }

    protected void zza(@Nullable zzko com_google_android_gms_internal_zzko, boolean z) {
        if (com_google_android_gms_internal_zzko == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("Ad state was null when trying to ping impression URLs.");
            return;
        }
        super.zzc(com_google_android_gms_internal_zzko);
        if (!(com_google_android_gms_internal_zzko.zzcsk == null || com_google_android_gms_internal_zzko.zzcsk.zzbvl == null)) {
            zzu.zzhf().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, com_google_android_gms_internal_zzko, this.zzaly.zzarg, z, com_google_android_gms_internal_zzko.zzcsk.zzbvl);
        }
        if (com_google_android_gms_internal_zzko.zzbwm != null && com_google_android_gms_internal_zzko.zzbwm.zzbuy != null) {
            zzu.zzhf().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, com_google_android_gms_internal_zzko, this.zzaly.zzarg, z, com_google_android_gms_internal_zzko.zzbwm.zzbuy);
        }
    }

    public void zza(String str, ArrayList<String> arrayList) {
        zzif com_google_android_gms_ads_internal_purchase_zzd = new zzd(str, arrayList, this.zzaly.zzahs, this.zzaly.zzari.zzda);
        if (this.zzaly.zzaru == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("InAppPurchaseListener is not set. Try to launch default purchase flow.");
            if (!com.google.android.gms.ads.internal.client.zzm.zzkr().zzap(this.zzaly.zzahs)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("Google Play Service unavailable, cannot launch default purchase flow.");
                return;
            } else if (this.zzaly.zzarv == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("PlayStorePurchaseListener is not set.");
                return;
            } else if (this.zzaly.zzasf == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("PlayStorePurchaseVerifier is not initialized.");
                return;
            } else if (this.zzaly.zzasj) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("An in-app purchase request is already in progress, abort");
                return;
            } else {
                this.zzaly.zzasj = true;
                try {
                    if (this.zzaly.zzarv.isValidPurchase(str)) {
                        zzu.zzha().zza(this.zzaly.zzahs, this.zzaly.zzari.zzcyc, new GInAppPurchaseManagerInfoParcel(this.zzaly.zzahs, this.zzaly.zzasf, com_google_android_gms_ads_internal_purchase_zzd, this));
                        return;
                    } else {
                        this.zzaly.zzasj = false;
                        return;
                    }
                } catch (RemoteException e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzdi("Could not start In-App purchase.");
                    this.zzaly.zzasj = false;
                    return;
                }
            }
        }
        try {
            this.zzaly.zzaru.zza(com_google_android_gms_ads_internal_purchase_zzd);
        } catch (RemoteException e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("Could not start In-App purchase.");
        }
    }

    public void zza(String str, boolean z, int i, Intent intent, zzf com_google_android_gms_ads_internal_purchase_zzf) {
        try {
            if (this.zzaly.zzarv != null) {
                this.zzaly.zzarv.zza(new com.google.android.gms.ads.internal.purchase.zzg(this.zzaly.zzahs, str, z, i, intent, com_google_android_gms_ads_internal_purchase_zzf));
            }
        } catch (RemoteException e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("Fail to invoke PlayStorePurchaseListener.");
        }
        zzlb.zzcvl.postDelayed(new C11101(this, intent), 500);
    }

    public boolean zza(AdRequestParcel adRequestParcel, zzdz com_google_android_gms_internal_zzdz) {
        if (!zzep()) {
            return false;
        }
        Bundle zzak = zzu.zzgm().zzak(this.zzaly.zzahs);
        this.zzalx.cancel();
        this.zzaly.zzasi = 0;
        zzkq com_google_android_gms_internal_zzkq = null;
        if (((Boolean) zzdr.zzbjv.get()).booleanValue()) {
            com_google_android_gms_internal_zzkq = zzu.zzgq().zzvf();
            zzu.zzhi().zza(this.zzaly.zzahs, this.zzaly.zzari, false, com_google_android_gms_internal_zzkq, com_google_android_gms_internal_zzkq.zzup(), this.zzaly.zzarg);
        }
        zza zza = zza(adRequestParcel, zzak, com_google_android_gms_internal_zzkq);
        com_google_android_gms_internal_zzdz.zzg("seq_num", zza.zzcjx);
        com_google_android_gms_internal_zzdz.zzg("request_id", zza.zzcki);
        com_google_android_gms_internal_zzdz.zzg("session_id", zza.zzcjy);
        if (zza.zzcjv != null) {
            com_google_android_gms_internal_zzdz.zzg("app_version", String.valueOf(zza.zzcjv.versionCode));
        }
        this.zzaly.zzark = zzu.zzgi().zza(this.zzaly.zzahs, zza, this.zzaly.zzarh, this);
        return true;
    }

    protected boolean zza(AdRequestParcel adRequestParcel, zzko com_google_android_gms_internal_zzko, boolean z) {
        if (!z && this.zzaly.zzhp()) {
            if (com_google_android_gms_internal_zzko.zzbvq > 0) {
                this.zzalx.zza(adRequestParcel, com_google_android_gms_internal_zzko.zzbvq);
            } else if (com_google_android_gms_internal_zzko.zzcsk != null && com_google_android_gms_internal_zzko.zzcsk.zzbvq > 0) {
                this.zzalx.zza(adRequestParcel, com_google_android_gms_internal_zzko.zzcsk.zzbvq);
            } else if (!com_google_android_gms_internal_zzko.zzclb && com_google_android_gms_internal_zzko.errorCode == 2) {
                this.zzalx.zzh(adRequestParcel);
            }
        }
        return this.zzalx.zzfy();
    }

    boolean zza(zzko com_google_android_gms_internal_zzko) {
        AdRequestParcel adRequestParcel;
        boolean z = false;
        if (this.zzalz != null) {
            adRequestParcel = this.zzalz;
            this.zzalz = null;
        } else {
            adRequestParcel = com_google_android_gms_internal_zzko.zzcju;
            if (adRequestParcel.extras != null) {
                z = adRequestParcel.extras.getBoolean("_noRefresh", false);
            }
        }
        return zza(adRequestParcel, com_google_android_gms_internal_zzko, z);
    }

    protected boolean zza(@Nullable zzko com_google_android_gms_internal_zzko, zzko com_google_android_gms_internal_zzko2) {
        int i;
        int i2 = 0;
        if (!(com_google_android_gms_internal_zzko == null || com_google_android_gms_internal_zzko.zzbwp == null)) {
            com_google_android_gms_internal_zzko.zzbwp.zza(null);
        }
        if (com_google_android_gms_internal_zzko2.zzbwp != null) {
            com_google_android_gms_internal_zzko2.zzbwp.zza((zzgr) this);
        }
        if (com_google_android_gms_internal_zzko2.zzcsk != null) {
            i = com_google_android_gms_internal_zzko2.zzcsk.zzbvw;
            i2 = com_google_android_gms_internal_zzko2.zzcsk.zzbvx;
        } else {
            i = 0;
        }
        this.zzaly.zzasg.zzj(i, i2);
        return true;
    }

    public void zzb(zzko com_google_android_gms_internal_zzko) {
        super.zzb(com_google_android_gms_internal_zzko);
        if (com_google_android_gms_internal_zzko.zzbwm != null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Disable the debug gesture detector on the mediation ad frame.");
            if (this.zzaly.zzarj != null) {
                this.zzaly.zzarj.zzhx();
            }
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Pinging network fill URLs.");
            zzu.zzhf().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, com_google_android_gms_internal_zzko, this.zzaly.zzarg, false, com_google_android_gms_internal_zzko.zzbwm.zzbuz);
            if (!(com_google_android_gms_internal_zzko.zzcsk == null || com_google_android_gms_internal_zzko.zzcsk.zzbvn == null || com_google_android_gms_internal_zzko.zzcsk.zzbvn.size() <= 0)) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdg("Pinging urls remotely");
                zzu.zzgm().zza(this.zzaly.zzahs, com_google_android_gms_internal_zzko.zzcsk.zzbvn);
            }
        } else {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Enable the debug gesture detector on the admob ad frame.");
            if (this.zzaly.zzarj != null) {
                this.zzaly.zzarj.zzhw();
            }
        }
        if (com_google_android_gms_internal_zzko.errorCode == 3 && com_google_android_gms_internal_zzko.zzcsk != null && com_google_android_gms_internal_zzko.zzcsk.zzbvm != null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Pinging no fill URLs.");
            zzu.zzhf().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, com_google_android_gms_internal_zzko, this.zzaly.zzarg, false, com_google_android_gms_internal_zzko.zzcsk.zzbvm);
        }
    }

    protected boolean zzc(AdRequestParcel adRequestParcel) {
        return super.zzc(adRequestParcel) && !this.zzamg;
    }

    protected boolean zzep() {
        return zzu.zzgm().zza(this.zzaly.zzahs.getPackageManager(), this.zzaly.zzahs.getPackageName(), "android.permission.INTERNET") && zzu.zzgm().zzy(this.zzaly.zzahs);
    }

    public void zzeq() {
        this.zzama.zzj(this.zzaly.zzarn);
        this.zzamg = false;
        zzek();
        this.zzaly.zzarp.zzui();
    }

    public void zzer() {
        this.zzamg = true;
        zzem();
    }

    public void zzes() {
        onAdClicked();
    }

    public void zzet() {
        zzeq();
    }

    public void zzeu() {
        zzeh();
    }

    public void zzev() {
        zzer();
    }

    public void zzew() {
        if (this.zzaly.zzarn != null) {
            String str = this.zzaly.zzarn.zzbwo;
            com.google.android.gms.ads.internal.util.client.zzb.zzdi(new StringBuilder(String.valueOf(str).length() + 74).append("Mediation adapter ").append(str).append(" refreshed, but mediation adapters should never refresh.").toString());
        }
        zza(this.zzaly.zzarn, true);
        zzen();
    }

    public void zzex() {
        recordImpression();
    }

    public void zzey() {
        zzu.zzgm().runOnUiThread(new C11112(this));
    }

    public void zzez() {
        zzu.zzgm().runOnUiThread(new C11123(this));
    }
}
