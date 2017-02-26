package com.google.android.gms.ads.internal.overlay;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import android.view.TextureView;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzdv;
import com.google.android.gms.internal.zzdx;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzlh;
import com.google.android.gms.internal.zzlh.zza;
import com.google.android.gms.internal.zzlh.zzb;
import java.util.concurrent.TimeUnit;

@zzji
public class zzy {
    private final Context mContext;
    private final VersionInfoParcel zzapc;
    @Nullable
    private final zzdz zzcbz;
    private boolean zzccd;
    private final String zzcec;
    @Nullable
    private final zzdx zzced;
    private final zzlh zzcee;
    private final long[] zzcef;
    private final String[] zzceg;
    private boolean zzceh;
    private boolean zzcei;
    private boolean zzcej;
    private boolean zzcek;
    private zzi zzcel;
    private boolean zzcem;
    private boolean zzcen;
    private long zzceo;

    public zzy(Context context, VersionInfoParcel versionInfoParcel, String str, @Nullable zzdz com_google_android_gms_internal_zzdz, @Nullable zzdx com_google_android_gms_internal_zzdx) {
        this.zzcee = new zzb().zza("min_1", Double.MIN_VALUE, 1.0d).zza("1_5", 1.0d, 5.0d).zza("5_10", 5.0d, 10.0d).zza("10_20", 10.0d, 20.0d).zza("20_30", 20.0d, 30.0d).zza("30_max", 30.0d, Double.MAX_VALUE).zzwi();
        this.zzceh = false;
        this.zzcei = false;
        this.zzcej = false;
        this.zzcek = false;
        this.zzceo = -1;
        this.mContext = context;
        this.zzapc = versionInfoParcel;
        this.zzcec = str;
        this.zzcbz = com_google_android_gms_internal_zzdz;
        this.zzced = com_google_android_gms_internal_zzdx;
        String str2 = (String) zzdr.zzbdv.get();
        if (str2 == null) {
            this.zzceg = new String[0];
            this.zzcef = new long[0];
            return;
        }
        String[] split = TextUtils.split(str2, ",");
        this.zzceg = new String[split.length];
        this.zzcef = new long[split.length];
        for (int i = 0; i < split.length; i++) {
            try {
                this.zzcef[i] = Long.parseLong(split[i]);
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzc("Unable to parse frame hash target time number.", e);
                this.zzcef[i] = -1;
            }
        }
    }

    private void zzc(zzi com_google_android_gms_ads_internal_overlay_zzi) {
        long longValue = ((Long) zzdr.zzbdw.get()).longValue();
        long currentPosition = (long) com_google_android_gms_ads_internal_overlay_zzi.getCurrentPosition();
        int i = 0;
        while (i < this.zzceg.length) {
            if (this.zzceg[i] == null && longValue > Math.abs(currentPosition - this.zzcef[i])) {
                this.zzceg[i] = zza((TextureView) com_google_android_gms_ads_internal_overlay_zzi);
                return;
            }
            i++;
        }
    }

    private void zzrd() {
        if (this.zzcej && !this.zzcek) {
            zzdv.zza(this.zzcbz, this.zzced, "vff2");
            this.zzcek = true;
        }
        long nanoTime = zzu.zzgs().nanoTime();
        if (this.zzccd && this.zzcen && this.zzceo != -1) {
            this.zzcee.zza(((double) TimeUnit.SECONDS.toNanos(1)) / ((double) (nanoTime - this.zzceo)));
        }
        this.zzcen = this.zzccd;
        this.zzceo = nanoTime;
    }

    public void onStop() {
        if (((Boolean) zzdr.zzbdu.get()).booleanValue() && !this.zzcem) {
            String valueOf;
            String valueOf2;
            Bundle bundle = new Bundle();
            bundle.putString(ShareConstants.MEDIA_TYPE, "native-player-metrics");
            bundle.putString(ShareConstants.WEB_DIALOG_RESULT_PARAM_REQUEST_ID, this.zzcec);
            bundle.putString("player", this.zzcel.zzpg());
            for (zza com_google_android_gms_internal_zzlh_zza : this.zzcee.getBuckets()) {
                valueOf = String.valueOf("fps_c_");
                valueOf2 = String.valueOf(com_google_android_gms_internal_zzlh_zza.name);
                bundle.putString(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), Integer.toString(com_google_android_gms_internal_zzlh_zza.count));
                valueOf = String.valueOf("fps_p_");
                valueOf2 = String.valueOf(com_google_android_gms_internal_zzlh_zza.name);
                bundle.putString(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), Double.toString(com_google_android_gms_internal_zzlh_zza.zzcwo));
            }
            for (int i = 0; i < this.zzcef.length; i++) {
                valueOf2 = this.zzceg[i];
                if (valueOf2 != null) {
                    String valueOf3 = String.valueOf("fh_");
                    valueOf = String.valueOf(Long.valueOf(this.zzcef[i]));
                    bundle.putString(new StringBuilder((String.valueOf(valueOf3).length() + 0) + String.valueOf(valueOf).length()).append(valueOf3).append(valueOf).toString(), valueOf2);
                }
            }
            zzu.zzgm().zza(this.mContext, this.zzapc.zzda, "gmob-apps", bundle, true);
            this.zzcem = true;
        }
    }

    @TargetApi(14)
    String zza(TextureView textureView) {
        Bitmap bitmap = textureView.getBitmap(8, 8);
        long j = 0;
        long j2 = 63;
        int i = 0;
        while (i < 8) {
            long j3 = j;
            j = j2;
            for (int i2 = 0; i2 < 8; i2++) {
                int pixel = bitmap.getPixel(i2, i);
                j3 |= (Color.green(pixel) + (Color.blue(pixel) + Color.red(pixel)) > AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS ? 1 : 0) << ((int) j);
                j--;
            }
            i++;
            j2 = j;
            j = j3;
        }
        return String.format("%016X", new Object[]{Long.valueOf(j)});
    }

    public void zza(zzi com_google_android_gms_ads_internal_overlay_zzi) {
        zzdv.zza(this.zzcbz, this.zzced, "vpc2");
        this.zzceh = true;
        if (this.zzcbz != null) {
            this.zzcbz.zzg("vpn", com_google_android_gms_ads_internal_overlay_zzi.zzpg());
        }
        this.zzcel = com_google_android_gms_ads_internal_overlay_zzi;
    }

    public void zzb(zzi com_google_android_gms_ads_internal_overlay_zzi) {
        zzrd();
        zzc(com_google_android_gms_ads_internal_overlay_zzi);
    }

    public void zzqc() {
        if (this.zzceh && !this.zzcei) {
            zzdv.zza(this.zzcbz, this.zzced, "vfr2");
            this.zzcei = true;
        }
    }

    public void zzre() {
        this.zzccd = true;
        if (this.zzcei && !this.zzcej) {
            zzdv.zza(this.zzcbz, this.zzced, "vfp2");
            this.zzcej = true;
        }
    }

    public void zzrf() {
        this.zzccd = false;
    }
}
