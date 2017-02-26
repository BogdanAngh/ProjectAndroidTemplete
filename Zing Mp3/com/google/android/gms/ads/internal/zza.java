package com.google.android.gms.ads.internal;

import android.os.Debug;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.ThinAdSizeParcel;
import com.google.android.gms.ads.internal.client.VideoOptionsParcel;
import com.google.android.gms.ads.internal.client.zzab;
import com.google.android.gms.ads.internal.client.zzf;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.client.zzw;
import com.google.android.gms.ads.internal.client.zzy;
import com.google.android.gms.ads.internal.overlay.zzp;
import com.google.android.gms.ads.internal.reward.client.zzd;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzco;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzdx;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzfa;
import com.google.android.gms.internal.zzig;
import com.google.android.gms.internal.zzik;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzjz;
import com.google.android.gms.internal.zzko;
import com.google.android.gms.internal.zzkp;
import com.google.android.gms.internal.zzkt;
import com.google.android.gms.internal.zzku;
import com.mp3download.zingmp3.BuildConfig;
import java.util.HashSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

@zzji
public abstract class zza extends com.google.android.gms.ads.internal.client.zzu.zza implements com.google.android.gms.ads.internal.client.zza, zzp, com.google.android.gms.ads.internal.request.zza.zza, zzfa, com.google.android.gms.internal.zziu.zza, zzkt {
    protected zzdz zzalt;
    protected zzdx zzalu;
    protected zzdx zzalv;
    protected boolean zzalw;
    protected final zzr zzalx;
    protected final zzv zzaly;
    @Nullable
    protected transient AdRequestParcel zzalz;
    protected final zzco zzama;
    protected final zzd zzamb;

    /* renamed from: com.google.android.gms.ads.internal.zza.1 */
    class C11091 extends TimerTask {
        final /* synthetic */ CountDownLatch zzamc;
        final /* synthetic */ Timer zzamd;
        final /* synthetic */ zza zzame;

        C11091(zza com_google_android_gms_ads_internal_zza, CountDownLatch countDownLatch, Timer timer) {
            this.zzame = com_google_android_gms_ads_internal_zza;
            this.zzamc = countDownLatch;
            this.zzamd = timer;
        }

        public void run() {
            if (((long) ((Integer) zzdr.zzbjn.get()).intValue()) != this.zzamc.getCount()) {
                zzb.zzdg("Stopping method tracing");
                Debug.stopMethodTracing();
                if (this.zzamc.getCount() == 0) {
                    this.zzamd.cancel();
                    return;
                }
            }
            String concat = String.valueOf(this.zzame.zzaly.zzahs.getPackageName()).concat("_adsTrace_");
            try {
                zzb.zzdg("Starting method tracing");
                this.zzamc.countDown();
                Debug.startMethodTracing(new StringBuilder(String.valueOf(concat).length() + 20).append(concat).append(zzu.zzgs().currentTimeMillis()).toString(), ((Integer) zzdr.zzbjo.get()).intValue());
            } catch (Throwable e) {
                zzb.zzc("Exception occurred while starting method tracing.", e);
            }
        }
    }

    zza(zzv com_google_android_gms_ads_internal_zzv, @Nullable zzr com_google_android_gms_ads_internal_zzr, zzd com_google_android_gms_ads_internal_zzd) {
        this.zzalw = false;
        this.zzaly = com_google_android_gms_ads_internal_zzv;
        if (com_google_android_gms_ads_internal_zzr == null) {
            com_google_android_gms_ads_internal_zzr = new zzr(this);
        }
        this.zzalx = com_google_android_gms_ads_internal_zzr;
        this.zzamb = com_google_android_gms_ads_internal_zzd;
        zzu.zzgm().zzz(this.zzaly.zzahs);
        zzu.zzgq().zzc(this.zzaly.zzahs, this.zzaly.zzari);
        zzu.zzgr().initialize(this.zzaly.zzahs);
        this.zzama = zzu.zzgq().zzvg();
        zzu.zzgp().initialize(this.zzaly.zzahs);
        zzed();
    }

    private AdRequestParcel zza(AdRequestParcel adRequestParcel) {
        return (!zzi.zzcj(this.zzaly.zzahs) || adRequestParcel.zzayt == null) ? adRequestParcel : new zzf(adRequestParcel).zza(null).zzka();
    }

    private TimerTask zza(Timer timer, CountDownLatch countDownLatch) {
        return new C11091(this, countDownLatch, timer);
    }

    private void zzd(zzko com_google_android_gms_internal_zzko) {
        if (zzu.zzgu().zzwg() && !com_google_android_gms_internal_zzko.zzcst && !TextUtils.isEmpty(com_google_android_gms_internal_zzko.zzclx)) {
            zzb.zzdg("Sending troubleshooting signals to the server.");
            zzu.zzgu().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, com_google_android_gms_internal_zzko.zzclx, this.zzaly.zzarg);
            com_google_android_gms_internal_zzko.zzcst = true;
        }
    }

    private void zzed() {
        if (((Boolean) zzdr.zzbjl.get()).booleanValue()) {
            Timer timer = new Timer();
            timer.schedule(zza(timer, new CountDownLatch(((Integer) zzdr.zzbjn.get()).intValue())), 0, ((Long) zzdr.zzbjm.get()).longValue());
        }
    }

    public void destroy() {
        zzaa.zzhs("destroy must be called on the main UI thread.");
        this.zzalx.cancel();
        this.zzama.zzk(this.zzaly.zzarn);
        this.zzaly.destroy();
    }

    public boolean isLoading() {
        return this.zzalw;
    }

    public boolean isReady() {
        zzaa.zzhs("isLoaded must be called on the main UI thread.");
        return this.zzaly.zzark == null && this.zzaly.zzarl == null && this.zzaly.zzarn != null;
    }

    public void onAdClicked() {
        if (this.zzaly.zzarn == null) {
            zzb.zzdi("Ad state was null when trying to ping click URLs.");
            return;
        }
        zzb.zzdg("Pinging click URLs.");
        if (this.zzaly.zzarp != null) {
            this.zzaly.zzarp.zzuh();
        }
        if (this.zzaly.zzarn.zzbvk != null) {
            zzu.zzgm().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, this.zzaly.zzarn.zzbvk);
        }
        if (this.zzaly.zzarq != null) {
            try {
                this.zzaly.zzarq.onAdClicked();
            } catch (Throwable e) {
                zzb.zzc("Could not notify onAdClicked event.", e);
            }
        }
    }

    public void onAppEvent(String str, @Nullable String str2) {
        if (this.zzaly.zzars != null) {
            try {
                this.zzaly.zzars.onAppEvent(str, str2);
            } catch (Throwable e) {
                zzb.zzc("Could not call the AppEventListener.", e);
            }
        }
    }

    public void pause() {
        zzaa.zzhs("pause must be called on the main UI thread.");
    }

    public void resume() {
        zzaa.zzhs("resume must be called on the main UI thread.");
    }

    public void setManualImpressionsEnabled(boolean z) {
        throw new UnsupportedOperationException("Attempt to call setManualImpressionsEnabled for an unsupported ad type.");
    }

    public void setUserId(String str) {
        zzb.zzdi("RewardedVideoAd.setUserId() is deprecated. Please do not call this method.");
    }

    public void stopLoading() {
        zzaa.zzhs("stopLoading must be called on the main UI thread.");
        this.zzalw = false;
        this.zzaly.zzi(true);
    }

    public void zza(AdSizeParcel adSizeParcel) {
        zzaa.zzhs("setAdSize must be called on the main UI thread.");
        this.zzaly.zzarm = adSizeParcel;
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzcbm == null || this.zzaly.zzasi != 0)) {
            this.zzaly.zzarn.zzcbm.zza(adSizeParcel);
        }
        if (this.zzaly.zzarj != null) {
            if (this.zzaly.zzarj.getChildCount() > 1) {
                this.zzaly.zzarj.removeView(this.zzaly.zzarj.getNextView());
            }
            this.zzaly.zzarj.setMinimumWidth(adSizeParcel.widthPixels);
            this.zzaly.zzarj.setMinimumHeight(adSizeParcel.heightPixels);
            this.zzaly.zzarj.requestLayout();
        }
    }

    public void zza(@Nullable VideoOptionsParcel videoOptionsParcel) {
        zzaa.zzhs("setVideoOptions must be called on the main UI thread.");
        this.zzaly.zzasb = videoOptionsParcel;
    }

    public void zza(com.google.android.gms.ads.internal.client.zzp com_google_android_gms_ads_internal_client_zzp) {
        zzaa.zzhs("setAdListener must be called on the main UI thread.");
        this.zzaly.zzarq = com_google_android_gms_ads_internal_client_zzp;
    }

    public void zza(zzq com_google_android_gms_ads_internal_client_zzq) {
        zzaa.zzhs("setAdListener must be called on the main UI thread.");
        this.zzaly.zzarr = com_google_android_gms_ads_internal_client_zzq;
    }

    public void zza(zzw com_google_android_gms_ads_internal_client_zzw) {
        zzaa.zzhs("setAppEventListener must be called on the main UI thread.");
        this.zzaly.zzars = com_google_android_gms_ads_internal_client_zzw;
    }

    public void zza(zzy com_google_android_gms_ads_internal_client_zzy) {
        zzaa.zzhs("setCorrelationIdProvider must be called on the main UI thread");
        this.zzaly.zzart = com_google_android_gms_ads_internal_client_zzy;
    }

    public void zza(zzd com_google_android_gms_ads_internal_reward_client_zzd) {
        zzaa.zzhs("setRewardedVideoAdListener can only be called from the UI thread.");
        this.zzaly.zzasd = com_google_android_gms_ads_internal_reward_client_zzd;
    }

    protected void zza(@Nullable RewardItemParcel rewardItemParcel) {
        if (this.zzaly.zzasd != null) {
            try {
                String str = BuildConfig.FLAVOR;
                int i = 0;
                if (rewardItemParcel != null) {
                    str = rewardItemParcel.type;
                    i = rewardItemParcel.zzcsc;
                }
                this.zzaly.zzasd.zza(new zzjz(str, i));
            } catch (Throwable e) {
                zzb.zzc("Could not call RewardedVideoAdListener.onRewarded().", e);
            }
        }
    }

    public void zza(zzed com_google_android_gms_internal_zzed) {
        throw new IllegalStateException("setOnCustomRenderedAdLoadedListener is not supported for current ad type");
    }

    public void zza(zzig com_google_android_gms_internal_zzig) {
        throw new IllegalStateException("setInAppPurchaseListener is not supported for current ad type");
    }

    public void zza(zzik com_google_android_gms_internal_zzik, String str) {
        throw new IllegalStateException("setPlayStorePurchaseParams is not supported for current ad type");
    }

    public void zza(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza) {
        if (!(com_google_android_gms_internal_zzko_zza.zzcsu.zzclf == -1 || TextUtils.isEmpty(com_google_android_gms_internal_zzko_zza.zzcsu.zzclo))) {
            long zzx = zzx(com_google_android_gms_internal_zzko_zza.zzcsu.zzclo);
            if (zzx != -1) {
                zzdx zzc = this.zzalt.zzc(zzx + com_google_android_gms_internal_zzko_zza.zzcsu.zzclf);
                this.zzalt.zza(zzc, "stc");
            }
        }
        this.zzalt.zzaz(com_google_android_gms_internal_zzko_zza.zzcsu.zzclo);
        this.zzalt.zza(this.zzalu, "arf");
        this.zzalv = this.zzalt.zzlz();
        this.zzalt.zzg("gqi", com_google_android_gms_internal_zzko_zza.zzcsu.zzclp);
        this.zzaly.zzark = null;
        this.zzaly.zzaro = com_google_android_gms_internal_zzko_zza;
        zza(com_google_android_gms_internal_zzko_zza, this.zzalt);
    }

    protected abstract void zza(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, zzdz com_google_android_gms_internal_zzdz);

    public void zza(HashSet<zzkp> hashSet) {
        this.zzaly.zza(hashSet);
    }

    protected abstract boolean zza(AdRequestParcel adRequestParcel, zzdz com_google_android_gms_internal_zzdz);

    boolean zza(zzko com_google_android_gms_internal_zzko) {
        return false;
    }

    protected abstract boolean zza(@Nullable zzko com_google_android_gms_internal_zzko, zzko com_google_android_gms_internal_zzko2);

    protected void zzb(View view) {
        com.google.android.gms.ads.internal.zzv.zza com_google_android_gms_ads_internal_zzv_zza = this.zzaly.zzarj;
        if (com_google_android_gms_ads_internal_zzv_zza != null) {
            com_google_android_gms_ads_internal_zzv_zza.addView(view, zzu.zzgo().zzvz());
        }
    }

    public void zzb(zzko com_google_android_gms_internal_zzko) {
        this.zzalt.zza(this.zzalv, "awr");
        this.zzaly.zzarl = null;
        if (!(com_google_android_gms_internal_zzko.errorCode == -2 || com_google_android_gms_internal_zzko.errorCode == 3)) {
            zzu.zzgq().zzb(this.zzaly.zzhl());
        }
        if (com_google_android_gms_internal_zzko.errorCode == -1) {
            this.zzalw = false;
            return;
        }
        if (zza(com_google_android_gms_internal_zzko)) {
            zzb.zzdg("Ad refresh scheduled.");
        }
        if (com_google_android_gms_internal_zzko.errorCode != -2) {
            zzh(com_google_android_gms_internal_zzko.errorCode);
            return;
        }
        if (this.zzaly.zzasg == null) {
            this.zzaly.zzasg = new zzku(this.zzaly.zzarg);
        }
        this.zzama.zzj(this.zzaly.zzarn);
        if (zza(this.zzaly.zzarn, com_google_android_gms_internal_zzko)) {
            this.zzaly.zzarn = com_google_android_gms_internal_zzko;
            this.zzaly.zzhu();
            this.zzalt.zzg("is_mraid", this.zzaly.zzarn.zzic() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
            this.zzalt.zzg("is_mediation", this.zzaly.zzarn.zzclb ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
            if (!(this.zzaly.zzarn.zzcbm == null || this.zzaly.zzarn.zzcbm.zzxc() == null)) {
                this.zzalt.zzg("is_delay_pl", this.zzaly.zzarn.zzcbm.zzxc().zzxy() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
            }
            this.zzalt.zza(this.zzalu, "ttc");
            if (zzu.zzgq().zzuu() != null) {
                zzu.zzgq().zzuu().zza(this.zzalt);
            }
            if (this.zzaly.zzhp()) {
                zzen();
            }
        }
        if (com_google_android_gms_internal_zzko.zzbvn != null) {
            zzu.zzgm().zza(this.zzaly.zzahs, com_google_android_gms_internal_zzko.zzbvn);
        }
    }

    public boolean zzb(AdRequestParcel adRequestParcel) {
        zzaa.zzhs("loadAd must be called on the main UI thread.");
        zzu.zzgr().zzjt();
        if (((Boolean) zzdr.zzbge.get()).booleanValue()) {
            AdRequestParcel.zzj(adRequestParcel);
        }
        AdRequestParcel zza = zza(adRequestParcel);
        if (this.zzaly.zzark == null && this.zzaly.zzarl == null) {
            zzb.zzdh("Starting ad request.");
            zzee();
            this.zzalu = this.zzalt.zzlz();
            if (!zza.zzayo) {
                String valueOf = String.valueOf(zzm.zzkr().zzao(this.zzaly.zzahs));
                zzb.zzdh(new StringBuilder(String.valueOf(valueOf).length() + 71).append("Use AdRequest.Builder.addTestDevice(\"").append(valueOf).append("\") to get test ads on this device.").toString());
            }
            this.zzalx.zzg(zza);
            this.zzalw = zza(zza, this.zzalt);
            return this.zzalw;
        }
        if (this.zzalz != null) {
            zzb.zzdi("Aborting last ad request since another ad request is already in progress. The current request object will still be cached for future refreshes.");
        } else {
            zzb.zzdi("Loading already in progress, saving this object for future refreshes.");
        }
        this.zzalz = zza;
        return false;
    }

    protected void zzc(@Nullable zzko com_google_android_gms_internal_zzko) {
        if (com_google_android_gms_internal_zzko == null) {
            zzb.zzdi("Ad state was null when trying to ping impression URLs.");
            return;
        }
        zzb.zzdg("Pinging Impression URLs.");
        if (this.zzaly.zzarp != null) {
            this.zzaly.zzarp.zzug();
        }
        if (com_google_android_gms_internal_zzko.zzbvl != null && !com_google_android_gms_internal_zzko.zzcsr) {
            zzu.zzgm().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, com_google_android_gms_internal_zzko.zzbvl);
            com_google_android_gms_internal_zzko.zzcsr = true;
            zzd(com_google_android_gms_internal_zzko);
        }
    }

    protected boolean zzc(AdRequestParcel adRequestParcel) {
        if (this.zzaly.zzarj == null) {
            return false;
        }
        ViewParent parent = this.zzaly.zzarj.getParent();
        if (!(parent instanceof View)) {
            return false;
        }
        View view = (View) parent;
        return zzu.zzgm().zza(view, view.getContext());
    }

    public void zzd(AdRequestParcel adRequestParcel) {
        if (zzc(adRequestParcel)) {
            zzb(adRequestParcel);
            return;
        }
        zzb.zzdh("Ad is not visible. Not refreshing ad.");
        this.zzalx.zzh(adRequestParcel);
    }

    public zzd zzec() {
        return this.zzamb;
    }

    public void zzee() {
        this.zzalt = new zzdz(((Boolean) zzdr.zzbeq.get()).booleanValue(), "load_ad", this.zzaly.zzarm.zzazq);
        this.zzalu = new zzdx(-1, null, null);
        this.zzalv = new zzdx(-1, null, null);
    }

    public com.google.android.gms.dynamic.zzd zzef() {
        zzaa.zzhs("getAdFrame must be called on the main UI thread.");
        return zze.zzac(this.zzaly.zzarj);
    }

    @Nullable
    public AdSizeParcel zzeg() {
        zzaa.zzhs("getAdSize must be called on the main UI thread.");
        return this.zzaly.zzarm == null ? null : new ThinAdSizeParcel(this.zzaly.zzarm);
    }

    public void zzeh() {
        zzel();
    }

    public void zzei() {
        zzaa.zzhs("recordManualImpression must be called on the main UI thread.");
        if (this.zzaly.zzarn == null) {
            zzb.zzdi("Ad state was null when trying to ping manual tracking URLs.");
            return;
        }
        zzb.zzdg("Pinging manual tracking URLs.");
        if (this.zzaly.zzarn.zzcld != null && !this.zzaly.zzarn.zzcss) {
            zzu.zzgm().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, this.zzaly.zzarn.zzcld);
            this.zzaly.zzarn.zzcss = true;
            zzd(this.zzaly.zzarn);
        }
    }

    public zzab zzej() {
        return null;
    }

    protected void zzek() {
        zzb.zzdh("Ad closing.");
        if (this.zzaly.zzarr != null) {
            try {
                this.zzaly.zzarr.onAdClosed();
            } catch (Throwable e) {
                zzb.zzc("Could not call AdListener.onAdClosed().", e);
            }
        }
        if (this.zzaly.zzasd != null) {
            try {
                this.zzaly.zzasd.onRewardedVideoAdClosed();
            } catch (Throwable e2) {
                zzb.zzc("Could not call RewardedVideoAdListener.onRewardedVideoAdClosed().", e2);
            }
        }
    }

    protected void zzel() {
        zzb.zzdh("Ad leaving application.");
        if (this.zzaly.zzarr != null) {
            try {
                this.zzaly.zzarr.onAdLeftApplication();
            } catch (Throwable e) {
                zzb.zzc("Could not call AdListener.onAdLeftApplication().", e);
            }
        }
        if (this.zzaly.zzasd != null) {
            try {
                this.zzaly.zzasd.onRewardedVideoAdLeftApplication();
            } catch (Throwable e2) {
                zzb.zzc("Could not call  RewardedVideoAdListener.onRewardedVideoAdLeftApplication().", e2);
            }
        }
    }

    protected void zzem() {
        zzb.zzdh("Ad opening.");
        if (this.zzaly.zzarr != null) {
            try {
                this.zzaly.zzarr.onAdOpened();
            } catch (Throwable e) {
                zzb.zzc("Could not call AdListener.onAdOpened().", e);
            }
        }
        if (this.zzaly.zzasd != null) {
            try {
                this.zzaly.zzasd.onRewardedVideoAdOpened();
            } catch (Throwable e2) {
                zzb.zzc("Could not call RewardedVideoAdListener.onRewardedVideoAdOpened().", e2);
            }
        }
    }

    protected void zzen() {
        zzb.zzdh("Ad finished loading.");
        this.zzalw = false;
        if (this.zzaly.zzarr != null) {
            try {
                this.zzaly.zzarr.onAdLoaded();
            } catch (Throwable e) {
                zzb.zzc("Could not call AdListener.onAdLoaded().", e);
            }
        }
        if (this.zzaly.zzasd != null) {
            try {
                this.zzaly.zzasd.onRewardedVideoAdLoaded();
            } catch (Throwable e2) {
                zzb.zzc("Could not call RewardedVideoAdListener.onRewardedVideoAdLoaded().", e2);
            }
        }
    }

    protected void zzeo() {
        if (this.zzaly.zzasd != null) {
            try {
                this.zzaly.zzasd.onRewardedVideoStarted();
            } catch (Throwable e) {
                zzb.zzc("Could not call RewardedVideoAdListener.onVideoStarted().", e);
            }
        }
    }

    protected void zzh(int i) {
        zzb.zzdi("Failed to load ad: " + i);
        this.zzalw = false;
        if (this.zzaly.zzarr != null) {
            try {
                this.zzaly.zzarr.onAdFailedToLoad(i);
            } catch (Throwable e) {
                zzb.zzc("Could not call AdListener.onAdFailedToLoad().", e);
            }
        }
        if (this.zzaly.zzasd != null) {
            try {
                this.zzaly.zzasd.onRewardedVideoAdFailedToLoad(i);
            } catch (Throwable e2) {
                zzb.zzc("Could not call RewardedVideoAdListener.onRewardedVideoAdFailedToLoad().", e2);
            }
        }
    }

    long zzx(String str) {
        int indexOf = str.indexOf("ufe");
        int indexOf2 = str.indexOf(44, indexOf);
        if (indexOf2 == -1) {
            indexOf2 = str.length();
        }
        try {
            return Long.parseLong(str.substring(indexOf + 4, indexOf2));
        } catch (IndexOutOfBoundsException e) {
            zzb.zzdi("Invalid index for Url fetch time in CSI latency info.");
            return -1;
        } catch (NumberFormatException e2) {
            zzb.zzdi("Cannot find valid format of Url fetch time in CSI latency info.");
            return -1;
        }
    }
}
