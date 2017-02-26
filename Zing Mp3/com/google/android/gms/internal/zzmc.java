package com.google.android.gms.internal;

import android.content.Context;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import com.google.android.gms.ads.internal.overlay.zzk;
import com.google.android.gms.common.internal.zzaa;

@zzji
public class zzmc {
    private final Context mContext;
    private final zzmd zzbnz;
    private zzk zzcep;
    private final ViewGroup zzcyz;

    public zzmc(Context context, ViewGroup viewGroup, zzmd com_google_android_gms_internal_zzmd) {
        this(context, viewGroup, com_google_android_gms_internal_zzmd, null);
    }

    zzmc(Context context, ViewGroup viewGroup, zzmd com_google_android_gms_internal_zzmd, zzk com_google_android_gms_ads_internal_overlay_zzk) {
        this.mContext = context;
        this.zzcyz = viewGroup;
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzcep = com_google_android_gms_ads_internal_overlay_zzk;
    }

    public void onDestroy() {
        zzaa.zzhs("onDestroy must be called from the UI thread.");
        if (this.zzcep != null) {
            this.zzcep.destroy();
            this.zzcyz.removeView(this.zzcep);
            this.zzcep = null;
        }
    }

    public void onPause() {
        zzaa.zzhs("onPause must be called from the UI thread.");
        if (this.zzcep != null) {
            this.zzcep.pause();
        }
    }

    public void zza(int i, int i2, int i3, int i4, int i5, boolean z) {
        if (this.zzcep == null) {
            zzdv.zza(this.zzbnz.zzxm().zzly(), this.zzbnz.zzxl(), "vpr2");
            this.zzcep = new zzk(this.mContext, this.zzbnz, i5, z, this.zzbnz.zzxm().zzly());
            this.zzcyz.addView(this.zzcep, 0, new LayoutParams(-1, -1));
            this.zzcep.zzd(i, i2, i3, i4);
            this.zzbnz.zzxc().zzao(false);
        }
    }

    public void zze(int i, int i2, int i3, int i4) {
        zzaa.zzhs("The underlay may only be modified from the UI thread.");
        if (this.zzcep != null) {
            this.zzcep.zzd(i, i2, i3, i4);
        }
    }

    public zzk zzwv() {
        zzaa.zzhs("getAdVideoUnderlay must be called from the UI thread.");
        return this.zzcep;
    }
}
