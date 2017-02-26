package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzht.zza;
import java.util.Map;

@zzji
public class zzhu extends zzhv implements zzfe {
    private final Context mContext;
    private final WindowManager zzati;
    DisplayMetrics zzaur;
    private final zzmd zzbnz;
    private final zzdj zzbzb;
    private float zzbzc;
    int zzbzd;
    int zzbze;
    private int zzbzf;
    int zzbzg;
    int zzbzh;
    int zzbzi;
    int zzbzj;

    public zzhu(zzmd com_google_android_gms_internal_zzmd, Context context, zzdj com_google_android_gms_internal_zzdj) {
        super(com_google_android_gms_internal_zzmd);
        this.zzbzd = -1;
        this.zzbze = -1;
        this.zzbzg = -1;
        this.zzbzh = -1;
        this.zzbzi = -1;
        this.zzbzj = -1;
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.mContext = context;
        this.zzbzb = com_google_android_gms_internal_zzdj;
        this.zzati = (WindowManager) context.getSystemService("window");
    }

    private void zzox() {
        this.zzaur = new DisplayMetrics();
        Display defaultDisplay = this.zzati.getDefaultDisplay();
        defaultDisplay.getMetrics(this.zzaur);
        this.zzbzc = this.zzaur.density;
        this.zzbzf = defaultDisplay.getRotation();
    }

    private void zzpc() {
        int[] iArr = new int[2];
        this.zzbnz.getLocationOnScreen(iArr);
        zze(zzm.zzkr().zzc(this.mContext, iArr[0]), zzm.zzkr().zzc(this.mContext, iArr[1]));
    }

    private zzht zzpf() {
        zza zzx = new zza().zzv(this.zzbzb.zzlj()).zzu(this.zzbzb.zzlk()).zzw(this.zzbzb.zzln()).zzx(this.zzbzb.zzll());
        zzdj com_google_android_gms_internal_zzdj = this.zzbzb;
        return zzx.zzy(true).zzow();
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        zzpa();
    }

    public void zze(int i, int i2) {
        int i3 = this.mContext instanceof Activity ? zzu.zzgm().zzk((Activity) this.mContext)[0] : 0;
        if (this.zzbnz.zzeg() == null || !this.zzbnz.zzeg().zzazr) {
            this.zzbzi = zzm.zzkr().zzc(this.mContext, this.zzbnz.getMeasuredWidth());
            this.zzbzj = zzm.zzkr().zzc(this.mContext, this.zzbnz.getMeasuredHeight());
        }
        zzc(i, i2 - i3, this.zzbzi, this.zzbzj);
        this.zzbnz.zzxc().zzd(i, i2);
    }

    void zzoy() {
        this.zzbzd = zzm.zzkr().zzb(this.zzaur, this.zzaur.widthPixels);
        this.zzbze = zzm.zzkr().zzb(this.zzaur, this.zzaur.heightPixels);
        Activity zzwy = this.zzbnz.zzwy();
        if (zzwy == null || zzwy.getWindow() == null) {
            this.zzbzg = this.zzbzd;
            this.zzbzh = this.zzbze;
            return;
        }
        int[] zzh = zzu.zzgm().zzh(zzwy);
        this.zzbzg = zzm.zzkr().zzb(this.zzaur, zzh[0]);
        this.zzbzh = zzm.zzkr().zzb(this.zzaur, zzh[1]);
    }

    void zzoz() {
        if (this.zzbnz.zzeg().zzazr) {
            this.zzbzi = this.zzbzd;
            this.zzbzj = this.zzbze;
            return;
        }
        this.zzbnz.measure(0, 0);
    }

    public void zzpa() {
        zzox();
        zzoy();
        zzoz();
        zzpd();
        zzpe();
        zzpc();
        zzpb();
    }

    void zzpb() {
        if (zzb.zzbi(2)) {
            zzb.zzdh("Dispatching Ready Event.");
        }
        zzcc(this.zzbnz.zzxf().zzda);
    }

    void zzpd() {
        zza(this.zzbzd, this.zzbze, this.zzbzg, this.zzbzh, this.zzbzc, this.zzbzf);
    }

    void zzpe() {
        this.zzbnz.zzb("onDeviceFeaturesReceived", zzpf().toJson());
    }
}
