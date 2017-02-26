package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.doubleclick.OnCustomRenderedAdLoadedListener;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.purchase.InAppPurchaseListener;
import com.google.android.gms.ads.purchase.PlayStorePurchaseListener;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzee;
import com.google.android.gms.internal.zzgy;
import com.google.android.gms.internal.zzil;
import com.google.android.gms.internal.zzip;
import com.google.android.gms.internal.zzji;
import java.util.concurrent.atomic.AtomicBoolean;

@zzji
public class zzae {
    private final zzh zzakc;
    private VideoOptions zzalc;
    private boolean zzamv;
    private String zzant;
    private zza zzayj;
    private AdListener zzayk;
    private AppEventListener zzazw;
    private AdSize[] zzazx;
    private final zzgy zzbba;
    private final AtomicBoolean zzbbb;
    private final VideoController zzbbc;
    final zzo zzbbd;
    private Correlator zzbbe;
    private zzu zzbbf;
    private InAppPurchaseListener zzbbg;
    private OnCustomRenderedAdLoadedListener zzbbh;
    private PlayStorePurchaseListener zzbbi;
    private String zzbbj;
    private ViewGroup zzbbk;
    private int zzbbl;

    /* renamed from: com.google.android.gms.ads.internal.client.zzae.1 */
    class C10541 extends zzo {
        final /* synthetic */ zzae zzbbm;

        C10541(zzae com_google_android_gms_ads_internal_client_zzae) {
            this.zzbbm = com_google_android_gms_ads_internal_client_zzae;
        }

        public void onAdFailedToLoad(int i) {
            this.zzbbm.zzbbc.zza(this.zzbbm.zzdw());
            super.onAdFailedToLoad(i);
        }

        public void onAdLoaded() {
            this.zzbbm.zzbbc.zza(this.zzbbm.zzdw());
            super.onAdLoaded();
        }
    }

    public zzae(ViewGroup viewGroup) {
        this(viewGroup, null, false, zzh.zzkb(), 0);
    }

    public zzae(ViewGroup viewGroup, int i) {
        this(viewGroup, null, false, zzh.zzkb(), i);
    }

    public zzae(ViewGroup viewGroup, AttributeSet attributeSet, boolean z) {
        this(viewGroup, attributeSet, z, zzh.zzkb(), 0);
    }

    public zzae(ViewGroup viewGroup, AttributeSet attributeSet, boolean z, int i) {
        this(viewGroup, attributeSet, z, zzh.zzkb(), i);
    }

    zzae(ViewGroup viewGroup, AttributeSet attributeSet, boolean z, zzh com_google_android_gms_ads_internal_client_zzh, int i) {
        this(viewGroup, attributeSet, z, com_google_android_gms_ads_internal_client_zzh, null, i);
    }

    zzae(ViewGroup viewGroup, AttributeSet attributeSet, boolean z, zzh com_google_android_gms_ads_internal_client_zzh, zzu com_google_android_gms_ads_internal_client_zzu, int i) {
        this.zzbba = new zzgy();
        this.zzbbc = new VideoController();
        this.zzbbd = new C10541(this);
        this.zzbbk = viewGroup;
        this.zzakc = com_google_android_gms_ads_internal_client_zzh;
        this.zzbbf = com_google_android_gms_ads_internal_client_zzu;
        this.zzbbb = new AtomicBoolean(false);
        this.zzbbl = i;
        if (attributeSet != null) {
            Context context = viewGroup.getContext();
            try {
                zzk com_google_android_gms_ads_internal_client_zzk = new zzk(context, attributeSet);
                this.zzazx = com_google_android_gms_ads_internal_client_zzk.zzm(z);
                this.zzant = com_google_android_gms_ads_internal_client_zzk.getAdUnitId();
                if (viewGroup.isInEditMode()) {
                    zzm.zzkr().zza(viewGroup, zza(context, this.zzazx[0], this.zzbbl), "Ads by Google");
                }
            } catch (IllegalArgumentException e) {
                zzm.zzkr().zza(viewGroup, new AdSizeParcel(context, AdSize.BANNER), e.getMessage(), e.getMessage());
            }
        }
    }

    private static AdSizeParcel zza(Context context, AdSize adSize, int i) {
        AdSizeParcel adSizeParcel = new AdSizeParcel(context, adSize);
        adSizeParcel.zzl(zzy(i));
        return adSizeParcel;
    }

    private static AdSizeParcel zza(Context context, AdSize[] adSizeArr, int i) {
        AdSizeParcel adSizeParcel = new AdSizeParcel(context, adSizeArr);
        adSizeParcel.zzl(zzy(i));
        return adSizeParcel;
    }

    private void zzlf() {
        try {
            zzd zzef = this.zzbbf.zzef();
            if (zzef != null) {
                this.zzbbk.addView((View) zze.zzae(zzef));
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to get an ad frame.", e);
        }
    }

    private static boolean zzy(int i) {
        return i == 1;
    }

    public void destroy() {
        try {
            if (this.zzbbf != null) {
                this.zzbbf.destroy();
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to destroy AdView.", e);
        }
    }

    public AdListener getAdListener() {
        return this.zzayk;
    }

    public AdSize getAdSize() {
        try {
            if (this.zzbbf != null) {
                AdSizeParcel zzeg = this.zzbbf.zzeg();
                if (zzeg != null) {
                    return zzeg.zzkd();
                }
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to get the current AdSize.", e);
        }
        return this.zzazx != null ? this.zzazx[0] : null;
    }

    public AdSize[] getAdSizes() {
        return this.zzazx;
    }

    public String getAdUnitId() {
        return this.zzant;
    }

    public AppEventListener getAppEventListener() {
        return this.zzazw;
    }

    public InAppPurchaseListener getInAppPurchaseListener() {
        return this.zzbbg;
    }

    public String getMediationAdapterClassName() {
        try {
            if (this.zzbbf != null) {
                return this.zzbbf.getMediationAdapterClassName();
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to get the mediation adapter class name.", e);
        }
        return null;
    }

    public OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener() {
        return this.zzbbh;
    }

    public VideoController getVideoController() {
        return this.zzbbc;
    }

    public VideoOptions getVideoOptions() {
        return this.zzalc;
    }

    public boolean isLoading() {
        try {
            if (this.zzbbf != null) {
                return this.zzbbf.isLoading();
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to check if ad is loading.", e);
        }
        return false;
    }

    public void pause() {
        try {
            if (this.zzbbf != null) {
                this.zzbbf.pause();
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to call pause.", e);
        }
    }

    public void recordManualImpression() {
        if (!this.zzbbb.getAndSet(true)) {
            try {
                if (this.zzbbf != null) {
                    this.zzbbf.zzei();
                }
            } catch (Throwable e) {
                zzb.zzc("Failed to record impression.", e);
            }
        }
    }

    public void resume() {
        try {
            if (this.zzbbf != null) {
                this.zzbbf.resume();
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to call resume.", e);
        }
    }

    public void setAdListener(AdListener adListener) {
        this.zzayk = adListener;
        this.zzbbd.zza(adListener);
    }

    public void setAdSizes(AdSize... adSizeArr) {
        if (this.zzazx != null) {
            throw new IllegalStateException("The ad size can only be set once on AdView.");
        }
        zza(adSizeArr);
    }

    public void setAdUnitId(String str) {
        if (this.zzant != null) {
            throw new IllegalStateException("The ad unit ID can only be set once on AdView.");
        }
        this.zzant = str;
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        try {
            this.zzazw = appEventListener;
            if (this.zzbbf != null) {
                this.zzbbf.zza(appEventListener != null ? new zzj(appEventListener) : null);
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set the AppEventListener.", e);
        }
    }

    public void setCorrelator(Correlator correlator) {
        this.zzbbe = correlator;
        try {
            if (this.zzbbf != null) {
                this.zzbbf.zza(this.zzbbe == null ? null : this.zzbbe.zzdu());
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set correlator.", e);
        }
    }

    public void setInAppPurchaseListener(InAppPurchaseListener inAppPurchaseListener) {
        if (this.zzbbi != null) {
            throw new IllegalStateException("Play store purchase parameter has already been set.");
        }
        try {
            this.zzbbg = inAppPurchaseListener;
            if (this.zzbbf != null) {
                this.zzbbf.zza(inAppPurchaseListener != null ? new zzil(inAppPurchaseListener) : null);
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set the InAppPurchaseListener.", e);
        }
    }

    public void setManualImpressionsEnabled(boolean z) {
        this.zzamv = z;
        try {
            if (this.zzbbf != null) {
                this.zzbbf.setManualImpressionsEnabled(this.zzamv);
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set manual impressions.", e);
        }
    }

    public void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.zzbbh = onCustomRenderedAdLoadedListener;
        try {
            if (this.zzbbf != null) {
                this.zzbbf.zza(onCustomRenderedAdLoadedListener != null ? new zzee(onCustomRenderedAdLoadedListener) : null);
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set the onCustomRenderedAdLoadedListener.", e);
        }
    }

    public void setPlayStorePurchaseParams(PlayStorePurchaseListener playStorePurchaseListener, String str) {
        if (this.zzbbg != null) {
            throw new IllegalStateException("InAppPurchaseListener has already been set.");
        }
        try {
            this.zzbbi = playStorePurchaseListener;
            this.zzbbj = str;
            if (this.zzbbf != null) {
                this.zzbbf.zza(playStorePurchaseListener != null ? new zzip(playStorePurchaseListener) : null, str);
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set the play store purchase parameter.", e);
        }
    }

    public void setVideoOptions(VideoOptions videoOptions) {
        this.zzalc = videoOptions;
        try {
            if (this.zzbbf != null) {
                this.zzbbf.zza(videoOptions == null ? null : new VideoOptionsParcel(videoOptions));
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set video options.", e);
        }
    }

    public void zza(zza com_google_android_gms_ads_internal_client_zza) {
        try {
            this.zzayj = com_google_android_gms_ads_internal_client_zza;
            if (this.zzbbf != null) {
                this.zzbbf.zza(com_google_android_gms_ads_internal_client_zza != null ? new zzb(com_google_android_gms_ads_internal_client_zza) : null);
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set the AdClickListener.", e);
        }
    }

    public void zza(zzad com_google_android_gms_ads_internal_client_zzad) {
        try {
            if (this.zzbbf == null) {
                zzlg();
            }
            if (this.zzbbf.zzb(this.zzakc.zza(this.zzbbk.getContext(), com_google_android_gms_ads_internal_client_zzad))) {
                this.zzbba.zzi(com_google_android_gms_ads_internal_client_zzad.zzlb());
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to load ad.", e);
        }
    }

    public void zza(AdSize... adSizeArr) {
        this.zzazx = adSizeArr;
        try {
            if (this.zzbbf != null) {
                this.zzbbf.zza(zza(this.zzbbk.getContext(), this.zzazx, this.zzbbl));
            }
        } catch (Throwable e) {
            zzb.zzc("Failed to set the ad size.", e);
        }
        this.zzbbk.requestLayout();
    }

    public boolean zzb(AdSizeParcel adSizeParcel) {
        return "search_v2".equals(adSizeParcel.zzazq);
    }

    public zzab zzdw() {
        zzab com_google_android_gms_ads_internal_client_zzab = null;
        if (this.zzbbf != null) {
            try {
                com_google_android_gms_ads_internal_client_zzab = this.zzbbf.zzej();
            } catch (Throwable e) {
                zzb.zzc("Failed to retrieve VideoController.", e);
            }
        }
        return com_google_android_gms_ads_internal_client_zzab;
    }

    void zzlg() throws RemoteException {
        if ((this.zzazx == null || this.zzant == null) && this.zzbbf == null) {
            throw new IllegalStateException("The ad size and ad unit ID must be set before loadAd is called.");
        }
        this.zzbbf = zzlh();
        this.zzbbf.zza(new zzc(this.zzbbd));
        if (this.zzayj != null) {
            this.zzbbf.zza(new zzb(this.zzayj));
        }
        if (this.zzazw != null) {
            this.zzbbf.zza(new zzj(this.zzazw));
        }
        if (this.zzbbg != null) {
            this.zzbbf.zza(new zzil(this.zzbbg));
        }
        if (this.zzbbi != null) {
            this.zzbbf.zza(new zzip(this.zzbbi), this.zzbbj);
        }
        if (this.zzbbh != null) {
            this.zzbbf.zza(new zzee(this.zzbbh));
        }
        if (this.zzbbe != null) {
            this.zzbbf.zza(this.zzbbe.zzdu());
        }
        if (this.zzalc != null) {
            this.zzbbf.zza(new VideoOptionsParcel(this.zzalc));
        }
        this.zzbbf.setManualImpressionsEnabled(this.zzamv);
        zzlf();
    }

    protected zzu zzlh() throws RemoteException {
        Context context = this.zzbbk.getContext();
        AdSizeParcel zza = zza(context, this.zzazx, this.zzbbl);
        return zzb(zza) ? zzm.zzks().zza(context, zza, this.zzant) : zzm.zzks().zza(context, zza, this.zzant, this.zzbba);
    }
}
