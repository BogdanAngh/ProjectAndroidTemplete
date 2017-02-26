package com.google.android.gms.ads.internal.formats;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.google.android.gms.ads.internal.formats.zzi.zza;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzq;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzav;
import com.google.android.gms.internal.zzhd;
import com.google.android.gms.internal.zzhe;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzmd;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.json.JSONObject;

@zzji
public class zzh extends zzj {
    private Object zzako;
    @Nullable
    private zzhd zzbnp;
    @Nullable
    private zzhe zzbnq;
    private final zzq zzbnr;
    @Nullable
    private zzi zzbns;
    private boolean zzbnt;

    private zzh(Context context, zzq com_google_android_gms_ads_internal_zzq, zzav com_google_android_gms_internal_zzav, zza com_google_android_gms_ads_internal_formats_zzi_zza) {
        super(context, com_google_android_gms_ads_internal_zzq, null, com_google_android_gms_internal_zzav, null, com_google_android_gms_ads_internal_formats_zzi_zza, null, null);
        this.zzbnt = false;
        this.zzako = new Object();
        this.zzbnr = com_google_android_gms_ads_internal_zzq;
    }

    public zzh(Context context, zzq com_google_android_gms_ads_internal_zzq, zzav com_google_android_gms_internal_zzav, zzhd com_google_android_gms_internal_zzhd, zza com_google_android_gms_ads_internal_formats_zzi_zza) {
        this(context, com_google_android_gms_ads_internal_zzq, com_google_android_gms_internal_zzav, com_google_android_gms_ads_internal_formats_zzi_zza);
        this.zzbnp = com_google_android_gms_internal_zzhd;
    }

    public zzh(Context context, zzq com_google_android_gms_ads_internal_zzq, zzav com_google_android_gms_internal_zzav, zzhe com_google_android_gms_internal_zzhe, zza com_google_android_gms_ads_internal_formats_zzi_zza) {
        this(context, com_google_android_gms_ads_internal_zzq, com_google_android_gms_internal_zzav, com_google_android_gms_ads_internal_formats_zzi_zza);
        this.zzbnq = com_google_android_gms_internal_zzhe;
    }

    @Nullable
    public zzb zza(OnClickListener onClickListener) {
        return null;
    }

    public void zza(View view, Map<String, WeakReference<View>> map, OnTouchListener onTouchListener, OnClickListener onClickListener) {
        synchronized (this.zzako) {
            this.zzbnt = true;
            try {
                if (this.zzbnp != null) {
                    this.zzbnp.zzl(zze.zzac(view));
                } else if (this.zzbnq != null) {
                    this.zzbnq.zzl(zze.zzac(view));
                }
            } catch (Throwable e) {
                zzb.zzc("Failed to call prepareAd", e);
            }
            this.zzbnt = false;
        }
    }

    public void zza(View view, Map<String, WeakReference<View>> map, JSONObject jSONObject, View view2) {
        zzaa.zzhs("performClick must be called on the main UI thread.");
        synchronized (this.zzako) {
            if (this.zzbns != null) {
                this.zzbns.zza(view, map, jSONObject, view2);
                this.zzbnr.onAdClicked();
            } else {
                try {
                    if (!(this.zzbnp == null || this.zzbnp.getOverrideClickHandling())) {
                        this.zzbnp.zzk(zze.zzac(view));
                        this.zzbnr.onAdClicked();
                    }
                    if (!(this.zzbnq == null || this.zzbnq.getOverrideClickHandling())) {
                        this.zzbnq.zzk(zze.zzac(view));
                        this.zzbnr.onAdClicked();
                    }
                } catch (Throwable e) {
                    zzb.zzc("Failed to call performClick", e);
                }
            }
        }
    }

    public void zzb(View view, Map<String, WeakReference<View>> map) {
        zzaa.zzhs("recordImpression must be called on the main UI thread.");
        synchronized (this.zzako) {
            zzr(true);
            if (this.zzbns != null) {
                this.zzbns.zzb(view, map);
                this.zzbnr.recordImpression();
            } else {
                try {
                    if (this.zzbnp != null && !this.zzbnp.getOverrideImpressionRecording()) {
                        this.zzbnp.recordImpression();
                        this.zzbnr.recordImpression();
                    } else if (!(this.zzbnq == null || this.zzbnq.getOverrideImpressionRecording())) {
                        this.zzbnq.recordImpression();
                        this.zzbnr.recordImpression();
                    }
                } catch (Throwable e) {
                    zzb.zzc("Failed to call recordImpression", e);
                }
            }
        }
    }

    public void zzc(View view, Map<String, WeakReference<View>> map) {
        synchronized (this.zzako) {
            try {
                if (this.zzbnp != null) {
                    this.zzbnp.zzm(zze.zzac(view));
                } else if (this.zzbnq != null) {
                    this.zzbnq.zzm(zze.zzac(view));
                }
            } catch (Throwable e) {
                zzb.zzc("Failed to call untrackView", e);
            }
        }
    }

    public void zzc(@Nullable zzi com_google_android_gms_ads_internal_formats_zzi) {
        synchronized (this.zzako) {
            this.zzbns = com_google_android_gms_ads_internal_formats_zzi;
        }
    }

    public boolean zzmv() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzbnt;
        }
        return z;
    }

    public zzi zzmw() {
        zzi com_google_android_gms_ads_internal_formats_zzi;
        synchronized (this.zzako) {
            com_google_android_gms_ads_internal_formats_zzi = this.zzbns;
        }
        return com_google_android_gms_ads_internal_formats_zzi;
    }

    @Nullable
    public zzmd zzmx() {
        return null;
    }
}
