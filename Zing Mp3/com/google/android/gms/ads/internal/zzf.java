package com.google.android.gms.ads.internal;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzab;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzcu;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzko;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzme;
import com.google.android.gms.internal.zzme.zzc;
import com.google.android.gms.internal.zzme.zze;
import com.google.android.gms.internal.zzmi;
import java.util.List;

@zzji
public class zzf extends zzc implements OnGlobalLayoutListener, OnScrollChangedListener {
    private boolean zzamv;

    /* renamed from: com.google.android.gms.ads.internal.zzf.1 */
    class C11181 implements Runnable {
        final /* synthetic */ zzf zzamw;

        C11181(zzf com_google_android_gms_ads_internal_zzf) {
            this.zzamw = com_google_android_gms_ads_internal_zzf;
        }

        public void run() {
            this.zzamw.zzf(this.zzamw.zzaly.zzarn);
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzf.2 */
    class C11192 implements zze {
        final /* synthetic */ zzf zzamw;
        final /* synthetic */ zzko zzamx;
        final /* synthetic */ Runnable zzamy;

        C11192(zzf com_google_android_gms_ads_internal_zzf, zzko com_google_android_gms_internal_zzko, Runnable runnable) {
            this.zzamw = com_google_android_gms_ads_internal_zzf;
            this.zzamx = com_google_android_gms_internal_zzko;
            this.zzamy = runnable;
        }

        public void zzff() {
            if (!this.zzamx.zzcsj) {
                zzu.zzgm();
                zzlb.zzb(this.zzamy);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzf.3 */
    class C11203 implements zzc {
        final /* synthetic */ zzf zzamw;
        final /* synthetic */ zzko zzamz;

        C11203(zzf com_google_android_gms_ads_internal_zzf, zzko com_google_android_gms_internal_zzko) {
            this.zzamw = com_google_android_gms_ads_internal_zzf;
            this.zzamz = com_google_android_gms_internal_zzko;
        }

        public void zzfg() {
            new zzcu(this.zzamw.zzaly.zzahs, this.zzamz.zzcbm.getView()).zza(this.zzamz.zzcbm);
        }
    }

    public class zza {
        final /* synthetic */ zzf zzamw;

        public zza(zzf com_google_android_gms_ads_internal_zzf) {
            this.zzamw = com_google_android_gms_ads_internal_zzf;
        }

        public void onClick() {
            this.zzamw.onAdClicked();
        }
    }

    public zzf(Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzgz, versionInfoParcel, com_google_android_gms_ads_internal_zzd);
    }

    private AdSizeParcel zzb(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza) {
        if (com_google_android_gms_internal_zzko_zza.zzcsu.zzazu) {
            return this.zzaly.zzarm;
        }
        AdSize adSize;
        String str = com_google_android_gms_internal_zzko_zza.zzcsu.zzcle;
        if (str != null) {
            String[] split = str.split("[xX]");
            split[0] = split[0].trim();
            split[1] = split[1].trim();
            adSize = new AdSize(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
        } else {
            adSize = this.zzaly.zzarm.zzkd();
        }
        return new AdSizeParcel(this.zzaly.zzahs, adSize);
    }

    private boolean zzb(@Nullable zzko com_google_android_gms_internal_zzko, zzko com_google_android_gms_internal_zzko2) {
        if (com_google_android_gms_internal_zzko2.zzclb) {
            View zzg = zzn.zzg(com_google_android_gms_internal_zzko2);
            if (zzg == null) {
                zzb.zzdi("Could not get mediation view");
                return false;
            }
            View nextView = this.zzaly.zzarj.getNextView();
            if (nextView != null) {
                if (nextView instanceof zzmd) {
                    ((zzmd) nextView).destroy();
                }
                this.zzaly.zzarj.removeView(nextView);
            }
            if (!zzn.zzh(com_google_android_gms_internal_zzko2)) {
                try {
                    zzb(zzg);
                } catch (Throwable th) {
                    zzb.zzc("Could not add mediation view to view hierarchy.", th);
                    return false;
                }
            }
        } else if (!(com_google_android_gms_internal_zzko2.zzcsm == null || com_google_android_gms_internal_zzko2.zzcbm == null)) {
            com_google_android_gms_internal_zzko2.zzcbm.zza(com_google_android_gms_internal_zzko2.zzcsm);
            this.zzaly.zzarj.removeAllViews();
            this.zzaly.zzarj.setMinimumWidth(com_google_android_gms_internal_zzko2.zzcsm.widthPixels);
            this.zzaly.zzarj.setMinimumHeight(com_google_android_gms_internal_zzko2.zzcsm.heightPixels);
            zzb(com_google_android_gms_internal_zzko2.zzcbm.getView());
        }
        if (this.zzaly.zzarj.getChildCount() > 1) {
            this.zzaly.zzarj.showNext();
        }
        if (com_google_android_gms_internal_zzko != null) {
            View nextView2 = this.zzaly.zzarj.getNextView();
            if (nextView2 instanceof zzmd) {
                ((zzmd) nextView2).zza(this.zzaly.zzahs, this.zzaly.zzarm, this.zzalt);
            } else if (nextView2 != null) {
                this.zzaly.zzarj.removeView(nextView2);
            }
            this.zzaly.zzho();
        }
        this.zzaly.zzarj.setVisibility(0);
        return true;
    }

    private void zze(zzko com_google_android_gms_internal_zzko) {
        if (!zzs.zzayq()) {
            return;
        }
        if (this.zzaly.zzhp()) {
            if (com_google_android_gms_internal_zzko.zzcbm != null) {
                if (com_google_android_gms_internal_zzko.zzcsi != null) {
                    this.zzama.zza(this.zzaly.zzarm, com_google_android_gms_internal_zzko);
                }
                if (com_google_android_gms_internal_zzko.zzic()) {
                    new zzcu(this.zzaly.zzahs, com_google_android_gms_internal_zzko.zzcbm.getView()).zza(com_google_android_gms_internal_zzko.zzcbm);
                } else {
                    com_google_android_gms_internal_zzko.zzcbm.zzxc().zza(new C11203(this, com_google_android_gms_internal_zzko));
                }
            }
        } else if (this.zzaly.zzash != null && com_google_android_gms_internal_zzko.zzcsi != null) {
            this.zzama.zza(this.zzaly.zzarm, com_google_android_gms_internal_zzko, this.zzaly.zzash);
        }
    }

    public void onGlobalLayout() {
        zzf(this.zzaly.zzarn);
    }

    public void onScrollChanged() {
        zzf(this.zzaly.zzarn);
    }

    public void setManualImpressionsEnabled(boolean z) {
        zzaa.zzhs("setManualImpressionsEnabled must be called from the main thread.");
        this.zzamv = z;
    }

    public void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by BannerAdManager.");
    }

    protected zzmd zza(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, @Nullable zze com_google_android_gms_ads_internal_zze, @Nullable com.google.android.gms.ads.internal.safebrowsing.zzc com_google_android_gms_ads_internal_safebrowsing_zzc) {
        if (this.zzaly.zzarm.zzazs == null && this.zzaly.zzarm.zzazu) {
            this.zzaly.zzarm = zzb(com_google_android_gms_internal_zzko_zza);
        }
        return super.zza(com_google_android_gms_internal_zzko_zza, com_google_android_gms_ads_internal_zze, com_google_android_gms_ads_internal_safebrowsing_zzc);
    }

    protected void zza(@Nullable zzko com_google_android_gms_internal_zzko, boolean z) {
        super.zza(com_google_android_gms_internal_zzko, z);
        if (zzn.zzh(com_google_android_gms_internal_zzko)) {
            zzn.zza(com_google_android_gms_internal_zzko, new zza(this));
        }
    }

    public boolean zza(@Nullable zzko com_google_android_gms_internal_zzko, zzko com_google_android_gms_internal_zzko2) {
        if (!super.zza(com_google_android_gms_internal_zzko, com_google_android_gms_internal_zzko2)) {
            return false;
        }
        if (!this.zzaly.zzhp() || zzb(com_google_android_gms_internal_zzko, com_google_android_gms_internal_zzko2)) {
            zzmi zzxn;
            if (com_google_android_gms_internal_zzko2.zzclt) {
                zzf(com_google_android_gms_internal_zzko2);
                zzu.zzhk().zza(this.zzaly.zzarj, (OnGlobalLayoutListener) this);
                zzu.zzhk().zza(this.zzaly.zzarj, (OnScrollChangedListener) this);
                if (!com_google_android_gms_internal_zzko2.zzcsj) {
                    Runnable c11181 = new C11181(this);
                    zzme zzxc = com_google_android_gms_internal_zzko2.zzcbm != null ? com_google_android_gms_internal_zzko2.zzcbm.zzxc() : null;
                    if (zzxc != null) {
                        zzxc.zza(new C11192(this, com_google_android_gms_internal_zzko2, c11181));
                    }
                }
            } else if (!this.zzaly.zzhq() || ((Boolean) zzdr.zzbiw.get()).booleanValue()) {
                zza(com_google_android_gms_internal_zzko2, false);
            }
            if (com_google_android_gms_internal_zzko2.zzcbm != null) {
                zzxn = com_google_android_gms_internal_zzko2.zzcbm.zzxn();
                zzme zzxc2 = com_google_android_gms_internal_zzko2.zzcbm.zzxc();
                if (zzxc2 != null) {
                    zzxc2.zzya();
                }
            } else {
                zzxn = null;
            }
            if (!(this.zzaly.zzasb == null || zzxn == null)) {
                zzxn.zzaq(this.zzaly.zzasb.zzbck);
            }
            zze(com_google_android_gms_internal_zzko2);
            return true;
        }
        zzh(0);
        return false;
    }

    public boolean zzb(AdRequestParcel adRequestParcel) {
        return super.zzb(zze(adRequestParcel));
    }

    AdRequestParcel zze(AdRequestParcel adRequestParcel) {
        if (adRequestParcel.zzayq == this.zzamv) {
            return adRequestParcel;
        }
        int i = adRequestParcel.versionCode;
        long j = adRequestParcel.zzayl;
        Bundle bundle = adRequestParcel.extras;
        int i2 = adRequestParcel.zzaym;
        List list = adRequestParcel.zzayn;
        boolean z = adRequestParcel.zzayo;
        int i3 = adRequestParcel.zzayp;
        boolean z2 = adRequestParcel.zzayq || this.zzamv;
        return new AdRequestParcel(i, j, bundle, i2, list, z, i3, z2, adRequestParcel.zzayr, adRequestParcel.zzays, adRequestParcel.zzayt, adRequestParcel.zzayu, adRequestParcel.zzayv, adRequestParcel.zzayw, adRequestParcel.zzayx, adRequestParcel.zzayy, adRequestParcel.zzayz, adRequestParcel.zzaza);
    }

    @Nullable
    public zzab zzej() {
        zzaa.zzhs("getVideoController must be called from the main thread.");
        return (this.zzaly.zzarn == null || this.zzaly.zzarn.zzcbm == null) ? null : this.zzaly.zzarn.zzcbm.zzxn();
    }

    protected boolean zzep() {
        boolean z = true;
        if (!zzu.zzgm().zza(this.zzaly.zzahs.getPackageManager(), this.zzaly.zzahs.getPackageName(), "android.permission.INTERNET")) {
            zzm.zzkr().zza(this.zzaly.zzarj, this.zzaly.zzarm, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            z = false;
        }
        if (!zzu.zzgm().zzy(this.zzaly.zzahs)) {
            zzm.zzkr().zza(this.zzaly.zzarj, this.zzaly.zzarm, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            z = false;
        }
        if (!(z || this.zzaly.zzarj == null)) {
            this.zzaly.zzarj.setVisibility(0);
        }
        return z;
    }

    void zzf(@Nullable zzko com_google_android_gms_internal_zzko) {
        if (com_google_android_gms_internal_zzko != null && !com_google_android_gms_internal_zzko.zzcsj && this.zzaly.zzarj != null && zzu.zzgm().zza(this.zzaly.zzarj, this.zzaly.zzahs) && this.zzaly.zzarj.getGlobalVisibleRect(new Rect(), null)) {
            if (!(com_google_android_gms_internal_zzko == null || com_google_android_gms_internal_zzko.zzcbm == null || com_google_android_gms_internal_zzko.zzcbm.zzxc() == null)) {
                com_google_android_gms_internal_zzko.zzcbm.zzxc().zza(null);
            }
            zza(com_google_android_gms_internal_zzko, false);
            com_google_android_gms_internal_zzko.zzcsj = true;
        }
    }
}
