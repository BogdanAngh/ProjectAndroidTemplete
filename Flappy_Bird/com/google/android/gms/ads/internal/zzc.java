package com.google.android.gms.ads.internal;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzd.zzb;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzcf;
import com.google.android.gms.internal.zzch;
import com.google.android.gms.internal.zzci;
import com.google.android.gms.internal.zzef;
import com.google.android.gms.internal.zzev;
import com.google.android.gms.internal.zzgd;
import com.google.android.gms.internal.zzha;
import com.google.android.gms.internal.zzha.zza;
import com.google.android.gms.internal.zzid;

@zzgd
public abstract class zzc extends zzb implements zzf, zzev {

    /* renamed from: com.google.android.gms.ads.internal.zzc.1 */
    class C00931 implements OnTouchListener {
        final /* synthetic */ zzd zzou;
        final /* synthetic */ zzc zzov;

        C00931(zzc com_google_android_gms_ads_internal_zzc, zzd com_google_android_gms_ads_internal_zzd) {
            this.zzov = com_google_android_gms_ads_internal_zzc;
            this.zzou = com_google_android_gms_ads_internal_zzd;
        }

        public boolean onTouch(View v, MotionEvent event) {
            this.zzou.recordClick();
            return false;
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzc.2 */
    class C00942 implements OnClickListener {
        final /* synthetic */ zzd zzou;
        final /* synthetic */ zzc zzov;

        C00942(zzc com_google_android_gms_ads_internal_zzc, zzd com_google_android_gms_ads_internal_zzd) {
            this.zzov = com_google_android_gms_ads_internal_zzc;
            this.zzou = com_google_android_gms_ads_internal_zzd;
        }

        public void onClick(View v) {
            this.zzou.recordClick();
        }
    }

    public zzc(Context context, AdSizeParcel adSizeParcel, String str, zzef com_google_android_gms_internal_zzef, VersionInfoParcel versionInfoParcel) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzef, versionInfoParcel);
    }

    public void recordClick() {
        onAdClicked();
    }

    public void recordImpression() {
        zza(this.zzon.zzpO, false);
    }

    protected zzid zza(zzd com_google_android_gms_ads_internal_zzd) {
        zzid com_google_android_gms_internal_zzid;
        View nextView = this.zzon.zzpK.getNextView();
        if (nextView instanceof zzid) {
            com_google_android_gms_internal_zzid = (zzid) nextView;
            com_google_android_gms_internal_zzid.zza(this.zzon.zzpH, this.zzon.zzpN);
        } else {
            if (nextView != null) {
                this.zzon.zzpK.removeView(nextView);
            }
            com_google_android_gms_internal_zzid = zzo.zzbw().zza(this.zzon.zzpH, this.zzon.zzpN, false, false, this.zzon.zzpI, this.zzon.zzpJ);
            if (this.zzon.zzpN.zzso == null) {
                zzb(com_google_android_gms_internal_zzid.getWebView());
            }
        }
        com_google_android_gms_internal_zzid.zzgF().zzb(this, this, this, this, false, this, null, com_google_android_gms_ads_internal_zzd, this);
        return com_google_android_gms_internal_zzid;
    }

    public void zza(int i, int i2, int i3, int i4) {
        zzaS();
    }

    public void zza(zzci com_google_android_gms_internal_zzci) {
        zzu.zzbY("setOnCustomRenderedAdLoadedListener must be called on the main UI thread.");
        this.zzon.zzqc = com_google_android_gms_internal_zzci;
    }

    protected boolean zza(zzha com_google_android_gms_internal_zzha, zzha com_google_android_gms_internal_zzha2) {
        if (this.zzon.zzbM() && this.zzon.zzpK != null) {
            this.zzon.zzpK.zzbP().zzaw(com_google_android_gms_internal_zzha2.zzCP);
        }
        return super.zza(com_google_android_gms_internal_zzha, com_google_android_gms_internal_zzha2);
    }

    protected boolean zzb(zza com_google_android_gms_internal_zzha_zza) {
        String str = null;
        zzd com_google_android_gms_ads_internal_zzd = new zzd();
        zzid zza = zza(com_google_android_gms_ads_internal_zzd);
        com_google_android_gms_ads_internal_zzd.zza(new zzb(com_google_android_gms_internal_zzha_zza, zza));
        zza.setOnTouchListener(new C00931(this, com_google_android_gms_ads_internal_zzd));
        zza.setOnClickListener(new C00942(this, com_google_android_gms_ads_internal_zzd));
        if (com_google_android_gms_internal_zzha_zza.zzpN != null) {
            this.zzon.zzpN = com_google_android_gms_internal_zzha_zza.zzpN;
        }
        if (com_google_android_gms_internal_zzha_zza.errorCode != -2) {
            zzb(new zzha(com_google_android_gms_internal_zzha_zza, zza, null, null, null, null, null));
            return false;
        }
        if (!com_google_android_gms_internal_zzha_zza.zzFs.zzCK && com_google_android_gms_internal_zzha_zza.zzFs.zzCT) {
            if (com_google_android_gms_internal_zzha_zza.zzFs.zzzG != null) {
                str = zzo.zzbv().zzat(com_google_android_gms_internal_zzha_zza.zzFs.zzzG);
            }
            zzch com_google_android_gms_internal_zzcf = new zzcf(this, str, com_google_android_gms_internal_zzha_zza.zzFs.zzCI);
            try {
                if (this.zzon.zzqc != null) {
                    this.zzon.zzqh = 1;
                    this.zzon.zzqc.zza(com_google_android_gms_internal_zzcf);
                    return false;
                }
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not call the onCustomRenderedAdLoadedListener.", e);
            }
        }
        this.zzon.zzqh = 0;
        this.zzon.zzpM = zzo.zzbu().zza(this.zzon.zzpH, this, com_google_android_gms_internal_zzha_zza, this.zzon.zzpI, zza, this.zzoq, this);
        return true;
    }

    public void zzbc() {
        zzaQ();
    }

    public void zzc(View view) {
        this.zzon.zzqg = view;
        zzb(new zzha(this.zzon.zzpP, null, null, null, null, null, null));
    }
}
