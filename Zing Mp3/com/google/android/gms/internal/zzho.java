package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsIntent.Builder;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.overlay.AdLauncherIntentInfoParcel;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzg;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.internal.zzef.zza;

@zzji
public class zzho implements MediationInterstitialAdapter {
    private Uri mUri;
    private Activity zzbxt;
    private zzef zzbxu;
    private MediationInterstitialListener zzbxv;

    /* renamed from: com.google.android.gms.internal.zzho.1 */
    class C13741 implements zza {
        final /* synthetic */ zzho zzbxw;

        C13741(zzho com_google_android_gms_internal_zzho) {
            this.zzbxw = com_google_android_gms_internal_zzho;
        }

        public void zzmh() {
            zzb.zzdg("Hinting CustomTabsService for the load of the new url.");
        }

        public void zzmi() {
            zzb.zzdg("Disconnecting from CustomTabs service.");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzho.2 */
    class C13752 implements zzg {
        final /* synthetic */ zzho zzbxw;

        C13752(zzho com_google_android_gms_internal_zzho) {
            this.zzbxw = com_google_android_gms_internal_zzho;
        }

        public void onPause() {
            zzb.zzdg("AdMobCustomTabsAdapter overlay is paused.");
        }

        public void onResume() {
            zzb.zzdg("AdMobCustomTabsAdapter overlay is resumed.");
        }

        public void zzeq() {
            zzb.zzdg("AdMobCustomTabsAdapter overlay is closed.");
            this.zzbxw.zzbxv.onAdClosed(this.zzbxw);
            this.zzbxw.zzbxu.zzd(this.zzbxw.zzbxt);
        }

        public void zzer() {
            zzb.zzdg("Opening AdMobCustomTabsAdapter overlay.");
            this.zzbxw.zzbxv.onAdOpened(this.zzbxw);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzho.3 */
    class C13763 implements Runnable {
        final /* synthetic */ AdOverlayInfoParcel zzaoi;
        final /* synthetic */ zzho zzbxw;

        C13763(zzho com_google_android_gms_internal_zzho, AdOverlayInfoParcel adOverlayInfoParcel) {
            this.zzbxw = com_google_android_gms_internal_zzho;
            this.zzaoi = adOverlayInfoParcel;
        }

        public void run() {
            zzu.zzgk().zza(this.zzbxw.zzbxt, this.zzaoi);
        }
    }

    public static boolean zzo(Context context) {
        return zzef.zzn(context);
    }

    public void onDestroy() {
        zzb.zzdg("Destroying AdMobCustomTabsAdapter adapter.");
        try {
            this.zzbxu.zzd(this.zzbxt);
        } catch (Throwable e) {
            zzb.zzb("Exception while unbinding from CustomTabsService.", e);
        }
    }

    public void onPause() {
        zzb.zzdg("Pausing AdMobCustomTabsAdapter adapter.");
    }

    public void onResume() {
        zzb.zzdg("Resuming AdMobCustomTabsAdapter adapter.");
    }

    public void requestInterstitialAd(Context context, MediationInterstitialListener mediationInterstitialListener, Bundle bundle, MediationAdRequest mediationAdRequest, Bundle bundle2) {
        this.zzbxv = mediationInterstitialListener;
        if (this.zzbxv == null) {
            zzb.zzdi("Listener not set for mediation. Returning.");
        } else if (!(context instanceof Activity)) {
            zzb.zzdi("AdMobCustomTabs can only work with Activity context. Bailing out.");
            this.zzbxv.onAdFailedToLoad(this, 0);
        } else if (zzo(context)) {
            Object string = bundle.getString("tab_url");
            if (TextUtils.isEmpty(string)) {
                zzb.zzdi("The tab_url retrieved from mediation metadata is empty. Bailing out.");
                this.zzbxv.onAdFailedToLoad(this, 0);
                return;
            }
            this.zzbxt = (Activity) context;
            this.mUri = Uri.parse(string);
            this.zzbxu = new zzef();
            this.zzbxu.zza(new C13741(this));
            this.zzbxu.zze(this.zzbxt);
            this.zzbxv.onAdLoaded(this);
        } else {
            zzb.zzdi("Default browser does not support custom tabs. Bailing out.");
            this.zzbxv.onAdFailedToLoad(this, 0);
        }
    }

    public void showInterstitial() {
        CustomTabsIntent build = new Builder(this.zzbxu.zzmf()).build();
        build.intent.setData(this.mUri);
        zzlb.zzcvl.post(new C13763(this, new AdOverlayInfoParcel(new AdLauncherIntentInfoParcel(build.intent), null, new C13752(this), null, new VersionInfoParcel(0, 0, false))));
        zzu.zzgq().zzah(false);
    }
}
