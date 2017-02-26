package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import com.facebook.internal.NativeProtocol;
import com.google.ads.mediation.AbstractAdViewAdapter;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzcu;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzfi;
import com.google.android.gms.internal.zzfn;
import com.google.android.gms.internal.zzgi;
import com.google.android.gms.internal.zzgp;
import com.google.android.gms.internal.zzgq;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzjm;
import com.google.android.gms.internal.zzko;
import com.google.android.gms.internal.zzkw;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzme;
import com.google.android.gms.internal.zzme.zzc;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Collections;
import java.util.concurrent.Future;
import org.json.JSONObject;

@zzji
public class zzl extends zzc implements zzfi, com.google.android.gms.internal.zzfn.zza {
    protected transient boolean zzaoc;
    private int zzaod;
    private boolean zzaoe;
    private float zzaof;

    /* renamed from: com.google.android.gms.ads.internal.zzl.1 */
    class C11251 implements zzc {
        final /* synthetic */ zzko zzamz;
        final /* synthetic */ zzl zzaog;

        C11251(zzl com_google_android_gms_ads_internal_zzl, zzko com_google_android_gms_internal_zzko) {
            this.zzaog = com_google_android_gms_ads_internal_zzl;
            this.zzamz = com_google_android_gms_internal_zzko;
        }

        public void zzfg() {
            new zzcu(this.zzaog.zzaly.zzahs, this.zzamz.zzcbm.getView()).zza(this.zzamz.zzcbm);
        }
    }

    @zzji
    private class zza extends zzkw {
        final /* synthetic */ zzl zzaog;
        private final int zzaoh;

        /* renamed from: com.google.android.gms.ads.internal.zzl.zza.1 */
        class C11261 implements Runnable {
            final /* synthetic */ AdOverlayInfoParcel zzaoi;
            final /* synthetic */ zza zzaoj;

            C11261(zza com_google_android_gms_ads_internal_zzl_zza, AdOverlayInfoParcel adOverlayInfoParcel) {
                this.zzaoj = com_google_android_gms_ads_internal_zzl_zza;
                this.zzaoi = adOverlayInfoParcel;
            }

            public void run() {
                zzu.zzgk().zza(this.zzaoj.zzaog.zzaly.zzahs, this.zzaoi);
            }
        }

        public zza(zzl com_google_android_gms_ads_internal_zzl, int i) {
            this.zzaog = com_google_android_gms_ads_internal_zzl;
            this.zzaoh = i;
        }

        public void onStop() {
        }

        public void zzfp() {
            InterstitialAdParameterParcel interstitialAdParameterParcel = new InterstitialAdParameterParcel(this.zzaog.zzaly.zzaok, this.zzaog.zzfm(), this.zzaog.zzaoe, this.zzaog.zzaof, this.zzaog.zzaly.zzaok ? this.zzaoh : -1);
            int requestedOrientation = this.zzaog.zzaly.zzarn.zzcbm.getRequestedOrientation();
            zzlb.zzcvl.post(new C11261(this, new AdOverlayInfoParcel(this.zzaog, this.zzaog, this.zzaog, this.zzaog.zzaly.zzarn.zzcbm, requestedOrientation == -1 ? this.zzaog.zzaly.zzarn.orientation : requestedOrientation, this.zzaog.zzaly.zzari, this.zzaog.zzaly.zzarn.zzclg, interstitialAdParameterParcel)));
        }
    }

    public zzl(Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzgz, versionInfoParcel, com_google_android_gms_ads_internal_zzd);
        this.zzaod = -1;
        this.zzaoc = false;
    }

    private void zzb(Bundle bundle) {
        zzu.zzgm().zzb(this.zzaly.zzahs, this.zzaly.zzari.zzda, "gmob-apps", bundle, false);
    }

    static com.google.android.gms.internal.zzko.zza zzc(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza) {
        try {
            String jSONObject = zzjm.zzc(com_google_android_gms_internal_zzko_zza.zzcsu).toString();
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put(AbstractAdViewAdapter.AD_UNIT_ID_PARAMETER, com_google_android_gms_internal_zzko_zza.zzcmx.zzarg);
            zzgp com_google_android_gms_internal_zzgp = new zzgp(jSONObject, null, Collections.singletonList("com.google.ads.mediation.admob.AdMobAdapter"), null, null, Collections.emptyList(), Collections.emptyList(), jSONObject2.toString(), null, Collections.emptyList(), Collections.emptyList(), null, null, null, null, null, Collections.emptyList());
            AdResponseParcel adResponseParcel = com_google_android_gms_internal_zzko_zza.zzcsu;
            zzgq com_google_android_gms_internal_zzgq = new zzgq(Collections.singletonList(com_google_android_gms_internal_zzgp), -1, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), adResponseParcel.zzbvn, adResponseParcel.zzbvo, BuildConfig.FLAVOR, -1, 0, 1, null, 0, -1, -1, false);
            return new com.google.android.gms.internal.zzko.zza(com_google_android_gms_internal_zzko_zza.zzcmx, new AdResponseParcel(com_google_android_gms_internal_zzko_zza.zzcmx, adResponseParcel.zzcbo, adResponseParcel.body, Collections.emptyList(), Collections.emptyList(), adResponseParcel.zzcla, true, adResponseParcel.zzclc, Collections.emptyList(), adResponseParcel.zzbvq, adResponseParcel.orientation, adResponseParcel.zzcle, adResponseParcel.zzclf, adResponseParcel.zzclg, adResponseParcel.zzclh, adResponseParcel.zzcli, null, adResponseParcel.zzclk, adResponseParcel.zzazt, adResponseParcel.zzckc, adResponseParcel.zzcll, adResponseParcel.zzclm, adResponseParcel.zzclp, adResponseParcel.zzazu, adResponseParcel.zzazv, null, Collections.emptyList(), Collections.emptyList(), adResponseParcel.zzclt, adResponseParcel.zzclu, adResponseParcel.zzcks, adResponseParcel.zzckt, adResponseParcel.zzbvn, adResponseParcel.zzbvo, adResponseParcel.zzclv, null, adResponseParcel.zzclx, adResponseParcel.zzcly), com_google_android_gms_internal_zzgq, com_google_android_gms_internal_zzko_zza.zzarm, com_google_android_gms_internal_zzko_zza.errorCode, com_google_android_gms_internal_zzko_zza.zzcso, com_google_android_gms_internal_zzko_zza.zzcsp, null);
        } catch (Throwable e) {
            zzb.zzb("Unable to generate ad state for an interstitial ad with pooling.", e);
            return com_google_android_gms_internal_zzko_zza;
        }
    }

    public void showInterstitial() {
        zzaa.zzhs("showInterstitial must be called on the main UI thread.");
        if (this.zzaly.zzarn == null) {
            zzb.zzdi("The interstitial has not loaded.");
            return;
        }
        if (((Boolean) zzdr.zzbgx.get()).booleanValue()) {
            Bundle bundle;
            String packageName = this.zzaly.zzahs.getApplicationContext() != null ? this.zzaly.zzahs.getApplicationContext().getPackageName() : this.zzaly.zzahs.getPackageName();
            if (!this.zzaoc) {
                zzb.zzdi("It is not recommended to show an interstitial before onAdLoaded completes.");
                bundle = new Bundle();
                bundle.putString("appid", packageName);
                bundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "show_interstitial_before_load_finish");
                zzb(bundle);
            }
            if (!zzu.zzgm().zzae(this.zzaly.zzahs)) {
                zzb.zzdi("It is not recommended to show an interstitial when app is not in foreground.");
                bundle = new Bundle();
                bundle.putString("appid", packageName);
                bundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "show_interstitial_app_not_in_foreground");
                zzb(bundle);
            }
        }
        if (!this.zzaly.zzhq()) {
            if (this.zzaly.zzarn.zzclb && this.zzaly.zzarn.zzbwn != null) {
                try {
                    this.zzaly.zzarn.zzbwn.showInterstitial();
                } catch (Throwable e) {
                    zzb.zzc("Could not show interstitial.", e);
                    zzfn();
                }
            } else if (this.zzaly.zzarn.zzcbm == null) {
                zzb.zzdi("The interstitial failed to load.");
            } else if (this.zzaly.zzarn.zzcbm.zzxg()) {
                zzb.zzdi("The interstitial is already showing.");
            } else {
                this.zzaly.zzarn.zzcbm.zzak(true);
                if (this.zzaly.zzarn.zzcsi != null) {
                    this.zzama.zza(this.zzaly.zzarm, this.zzaly.zzarn);
                }
                if (zzs.zzayq()) {
                    zzko com_google_android_gms_internal_zzko = this.zzaly.zzarn;
                    if (com_google_android_gms_internal_zzko.zzic()) {
                        new zzcu(this.zzaly.zzahs, com_google_android_gms_internal_zzko.zzcbm.getView()).zza(com_google_android_gms_internal_zzko.zzcbm);
                    } else {
                        com_google_android_gms_internal_zzko.zzcbm.zzxc().zza(new C11251(this, com_google_android_gms_internal_zzko));
                    }
                }
                Bitmap zzaf = this.zzaly.zzaok ? zzu.zzgm().zzaf(this.zzaly.zzahs) : null;
                this.zzaod = zzu.zzhh().zzb(zzaf);
                if (!((Boolean) zzdr.zzbip.get()).booleanValue() || zzaf == null) {
                    InterstitialAdParameterParcel interstitialAdParameterParcel = new InterstitialAdParameterParcel(this.zzaly.zzaok, zzfm(), false, 0.0f, -1);
                    int requestedOrientation = this.zzaly.zzarn.zzcbm.getRequestedOrientation();
                    if (requestedOrientation == -1) {
                        requestedOrientation = this.zzaly.zzarn.orientation;
                    }
                    zzu.zzgk().zza(this.zzaly.zzahs, new AdOverlayInfoParcel(this, this, this, this.zzaly.zzarn.zzcbm, requestedOrientation, this.zzaly.zzari, this.zzaly.zzarn.zzclg, interstitialAdParameterParcel));
                    return;
                }
                Future future = (Future) new zza(this, this.zzaod).zzrz();
            }
        }
    }

    protected zzmd zza(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, @Nullable zze com_google_android_gms_ads_internal_zze, @Nullable com.google.android.gms.ads.internal.safebrowsing.zzc com_google_android_gms_ads_internal_safebrowsing_zzc) {
        zzmd zza = zzu.zzgn().zza(this.zzaly.zzahs, this.zzaly.zzarm, false, false, this.zzaly.zzarh, this.zzaly.zzari, this.zzalt, this, this.zzamb);
        zza.zzxc().zza(this, null, this, this, ((Boolean) zzdr.zzbfn.get()).booleanValue(), this, this, com_google_android_gms_ads_internal_zze, null, com_google_android_gms_ads_internal_safebrowsing_zzc);
        zza((zzgi) zza);
        zza.zzdk(com_google_android_gms_internal_zzko_zza.zzcmx.zzcki);
        zzfn.zza(zza, (com.google.android.gms.internal.zzfn.zza) this);
        return zza;
    }

    public void zza(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, zzdz com_google_android_gms_internal_zzdz) {
        Object obj = 1;
        if (!((Boolean) zzdr.zzbgg.get()).booleanValue()) {
            super.zza(com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzdz);
        } else if (com_google_android_gms_internal_zzko_zza.errorCode != -2) {
            super.zza(com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzdz);
        } else {
            Bundle bundle = com_google_android_gms_internal_zzko_zza.zzcmx.zzcju.zzayv.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
            Object obj2 = (bundle == null || !bundle.containsKey("gw")) ? 1 : null;
            if (com_google_android_gms_internal_zzko_zza.zzcsu.zzclb) {
                obj = null;
            }
            if (!(obj2 == null || r2 == null)) {
                this.zzaly.zzaro = zzc(com_google_android_gms_internal_zzko_zza);
            }
            super.zza(this.zzaly.zzaro, com_google_android_gms_internal_zzdz);
        }
    }

    public void zza(boolean z, float f) {
        this.zzaoe = z;
        this.zzaof = f;
    }

    public boolean zza(AdRequestParcel adRequestParcel, zzdz com_google_android_gms_internal_zzdz) {
        if (this.zzaly.zzarn == null) {
            return super.zza(adRequestParcel, com_google_android_gms_internal_zzdz);
        }
        zzb.zzdi("An interstitial is already loading. Aborting.");
        return false;
    }

    protected boolean zza(AdRequestParcel adRequestParcel, zzko com_google_android_gms_internal_zzko, boolean z) {
        if (this.zzaly.zzhp() && com_google_android_gms_internal_zzko.zzcbm != null) {
            zzu.zzgo().zzl(com_google_android_gms_internal_zzko.zzcbm);
        }
        return this.zzalx.zzfy();
    }

    public boolean zza(@Nullable zzko com_google_android_gms_internal_zzko, zzko com_google_android_gms_internal_zzko2) {
        if (!super.zza(com_google_android_gms_internal_zzko, com_google_android_gms_internal_zzko2)) {
            return false;
        }
        if (!(this.zzaly.zzhp() || this.zzaly.zzash == null || com_google_android_gms_internal_zzko2.zzcsi == null)) {
            this.zzama.zza(this.zzaly.zzarm, com_google_android_gms_internal_zzko2, this.zzaly.zzash);
        }
        return true;
    }

    public void zzb(RewardItemParcel rewardItemParcel) {
        if (this.zzaly.zzarn != null) {
            if (this.zzaly.zzarn.zzcls != null) {
                zzu.zzgm().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, this.zzaly.zzarn.zzcls);
            }
            if (this.zzaly.zzarn.zzclq != null) {
                rewardItemParcel = this.zzaly.zzarn.zzclq;
            }
        }
        zza(rewardItemParcel);
    }

    protected void zzek() {
        zzfn();
        super.zzek();
    }

    protected void zzen() {
        super.zzen();
        this.zzaoc = true;
    }

    public void zzer() {
        recordImpression();
        super.zzer();
        if (this.zzaly.zzarn != null && this.zzaly.zzarn.zzcbm != null) {
            zzme zzxc = this.zzaly.zzarn.zzcbm.zzxc();
            if (zzxc != null) {
                zzxc.zzya();
            }
        }
    }

    protected boolean zzfm() {
        if (!(this.zzaly.zzahs instanceof Activity)) {
            return false;
        }
        Window window = ((Activity) this.zzaly.zzahs).getWindow();
        if (window == null || window.getDecorView() == null) {
            return false;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        window.getDecorView().getGlobalVisibleRect(rect, null);
        window.getDecorView().getWindowVisibleDisplayFrame(rect2);
        boolean z = (rect.bottom == 0 || rect2.bottom == 0 || rect.top != rect2.top) ? false : true;
        return z;
    }

    public void zzfn() {
        zzu.zzhh().zzb(Integer.valueOf(this.zzaod));
        if (this.zzaly.zzhp()) {
            this.zzaly.zzhm();
            this.zzaly.zzarn = null;
            this.zzaly.zzaok = false;
            this.zzaoc = false;
        }
    }

    public void zzfo() {
        if (!(this.zzaly.zzarn == null || this.zzaly.zzarn.zzcsn == null)) {
            zzu.zzgm().zza(this.zzaly.zzahs, this.zzaly.zzari.zzda, this.zzaly.zzarn.zzcsn);
        }
        zzeo();
    }

    public void zzg(boolean z) {
        this.zzaly.zzaok = z;
    }
}
