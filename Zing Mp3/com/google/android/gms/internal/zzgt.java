package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.ads.mediation.AdUrlAdapter;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAdOptions.Builder;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzgu.zza;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzgt implements zza {
    private final Context mContext;
    private final Object zzako;
    private final zzgz zzamf;
    private final NativeAdOptionsParcel zzanq;
    private final List<String> zzanr;
    private final VersionInfoParcel zzanu;
    private AdRequestParcel zzapj;
    private final AdSizeParcel zzapp;
    private final boolean zzasz;
    private final String zzbwa;
    private final long zzbwb;
    private final zzgq zzbwc;
    private final zzgp zzbwd;
    private final boolean zzbwe;
    private zzha zzbwf;
    private int zzbwg;
    private zzhc zzbwh;

    /* renamed from: com.google.android.gms.internal.zzgt.1 */
    class C13591 implements Runnable {
        final /* synthetic */ zzgs zzbwi;
        final /* synthetic */ zzgt zzbwj;

        C13591(zzgt com_google_android_gms_internal_zzgt, zzgs com_google_android_gms_internal_zzgs) {
            this.zzbwj = com_google_android_gms_internal_zzgt;
            this.zzbwi = com_google_android_gms_internal_zzgs;
        }

        public void run() {
            synchronized (this.zzbwj.zzako) {
                if (this.zzbwj.zzbwg != -2) {
                    return;
                }
                this.zzbwj.zzbwf = this.zzbwj.zzoh();
                if (this.zzbwj.zzbwf == null) {
                    this.zzbwj.zzad(4);
                } else if (!this.zzbwj.zzoi() || this.zzbwj.zzae(1)) {
                    this.zzbwi.zza(this.zzbwj);
                    this.zzbwj.zza(this.zzbwi);
                } else {
                    String zzf = this.zzbwj.zzbwa;
                    zzb.zzdi(new StringBuilder(String.valueOf(zzf).length() + 56).append("Ignoring adapter ").append(zzf).append(" as delayed impression is not supported").toString());
                    this.zzbwj.zzad(2);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzgt.2 */
    class C13602 extends zzhc.zza {
        final /* synthetic */ int zzbwk;

        C13602(int i) {
            this.zzbwk = i;
        }

        public int zzok() throws RemoteException {
            return this.zzbwk;
        }
    }

    public zzgt(Context context, String str, zzgz com_google_android_gms_internal_zzgz, zzgq com_google_android_gms_internal_zzgq, zzgp com_google_android_gms_internal_zzgp, AdRequestParcel adRequestParcel, AdSizeParcel adSizeParcel, VersionInfoParcel versionInfoParcel, boolean z, boolean z2, NativeAdOptionsParcel nativeAdOptionsParcel, List<String> list) {
        this.zzako = new Object();
        this.zzbwg = -2;
        this.mContext = context;
        this.zzamf = com_google_android_gms_internal_zzgz;
        this.zzbwd = com_google_android_gms_internal_zzgp;
        if ("com.google.ads.mediation.customevent.CustomEventAdapter".equals(str)) {
            this.zzbwa = zzof();
        } else {
            this.zzbwa = str;
        }
        this.zzbwc = com_google_android_gms_internal_zzgq;
        this.zzbwb = com_google_android_gms_internal_zzgq.zzbvj != -1 ? com_google_android_gms_internal_zzgq.zzbvj : 10000;
        this.zzapj = adRequestParcel;
        this.zzapp = adSizeParcel;
        this.zzanu = versionInfoParcel;
        this.zzasz = z;
        this.zzbwe = z2;
        this.zzanq = nativeAdOptionsParcel;
        this.zzanr = list;
    }

    private long zza(long j, long j2, long j3, long j4) {
        while (this.zzbwg == -2) {
            zzb(j, j2, j3, j4);
        }
        return zzu.zzgs().elapsedRealtime() - j;
    }

    private void zza(zzgs com_google_android_gms_internal_zzgs) {
        String zzbr = zzbr(this.zzbwd.zzbva);
        try {
            if (this.zzanu.zzcyb < 4100000) {
                if (this.zzapp.zzazr) {
                    this.zzbwf.zza(zze.zzac(this.mContext), this.zzapj, zzbr, com_google_android_gms_internal_zzgs);
                } else {
                    this.zzbwf.zza(zze.zzac(this.mContext), this.zzapp, this.zzapj, zzbr, (zzhb) com_google_android_gms_internal_zzgs);
                }
            } else if (this.zzasz) {
                this.zzbwf.zza(zze.zzac(this.mContext), this.zzapj, zzbr, this.zzbwd.zzbus, com_google_android_gms_internal_zzgs, this.zzanq, this.zzanr);
            } else if (this.zzapp.zzazr) {
                this.zzbwf.zza(zze.zzac(this.mContext), this.zzapj, zzbr, this.zzbwd.zzbus, (zzhb) com_google_android_gms_internal_zzgs);
            } else if (!this.zzbwe) {
                this.zzbwf.zza(zze.zzac(this.mContext), this.zzapp, this.zzapj, zzbr, this.zzbwd.zzbus, com_google_android_gms_internal_zzgs);
            } else if (this.zzbwd.zzbvd != null) {
                this.zzbwf.zza(zze.zzac(this.mContext), this.zzapj, zzbr, this.zzbwd.zzbus, com_google_android_gms_internal_zzgs, new NativeAdOptionsParcel(zzbs(this.zzbwd.zzbvh)), this.zzbwd.zzbvg);
            } else {
                this.zzbwf.zza(zze.zzac(this.mContext), this.zzapp, this.zzapj, zzbr, this.zzbwd.zzbus, com_google_android_gms_internal_zzgs);
            }
        } catch (Throwable e) {
            zzb.zzc("Could not request ad from mediation adapter.", e);
            zzad(5);
        }
    }

    private boolean zzae(int i) {
        try {
            Bundle zzop = this.zzasz ? this.zzbwf.zzop() : this.zzapp.zzazr ? this.zzbwf.getInterstitialAdapterInfo() : this.zzbwf.zzoo();
            if (zzop == null) {
                return false;
            }
            return (zzop.getInt("capabilities", 0) & i) == i;
        } catch (RemoteException e) {
            zzb.zzdi("Could not get adapter info. Returning false");
            return false;
        }
    }

    private static zzhc zzaf(int i) {
        return new C13602(i);
    }

    private void zzb(long j, long j2, long j3, long j4) {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        long j5 = j2 - (elapsedRealtime - j);
        elapsedRealtime = j4 - (elapsedRealtime - j3);
        if (j5 <= 0 || elapsedRealtime <= 0) {
            zzb.zzdh("Timed out waiting for adapter.");
            this.zzbwg = 3;
            return;
        }
        try {
            this.zzako.wait(Math.min(j5, elapsedRealtime));
        } catch (InterruptedException e) {
            this.zzbwg = -1;
        }
    }

    private String zzbr(String str) {
        if (!(str == null || !zzoi() || zzae(2))) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                jSONObject.remove("cpm_floor_cents");
                str = jSONObject.toString();
            } catch (JSONException e) {
                zzb.zzdi("Could not remove field. Returning the original value");
            }
        }
        return str;
    }

    private static NativeAdOptions zzbs(String str) {
        Builder builder = new Builder();
        if (str == null) {
            return builder.build();
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            builder.setRequestMultipleImages(jSONObject.optBoolean("multiple_images", false));
            builder.setReturnUrlsForImageAssets(jSONObject.optBoolean("only_urls", false));
            builder.setImageOrientation(zzbt(jSONObject.optString("native_image_orientation", "any")));
        } catch (Throwable e) {
            zzb.zzc("Exception occurred when creating native ad options", e);
        }
        return builder.build();
    }

    private static int zzbt(String str) {
        return "landscape".equals(str) ? 2 : "portrait".equals(str) ? 1 : 0;
    }

    private String zzof() {
        try {
            if (!TextUtils.isEmpty(this.zzbwd.zzbuw)) {
                return this.zzamf.zzbv(this.zzbwd.zzbuw) ? "com.google.android.gms.ads.mediation.customevent.CustomEventAdapter" : "com.google.ads.mediation.customevent.CustomEventAdapter";
            }
        } catch (RemoteException e) {
            zzb.zzdi("Fail to determine the custom event's version, assuming the old one.");
        }
        return "com.google.ads.mediation.customevent.CustomEventAdapter";
    }

    private zzhc zzog() {
        if (this.zzbwg != 0 || !zzoi()) {
            return null;
        }
        try {
            if (!(!zzae(4) || this.zzbwh == null || this.zzbwh.zzok() == 0)) {
                return this.zzbwh;
            }
        } catch (RemoteException e) {
            zzb.zzdi("Could not get cpm value from MediationResponseMetadata");
        }
        return zzaf(zzoj());
    }

    private zzha zzoh() {
        String str = "Instantiating mediation adapter: ";
        String valueOf = String.valueOf(this.zzbwa);
        zzb.zzdh(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        if (!this.zzasz) {
            if (((Boolean) zzdr.zzbhh.get()).booleanValue() && "com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzbwa)) {
                return zza(new AdMobAdapter());
            }
            if (((Boolean) zzdr.zzbhi.get()).booleanValue() && "com.google.ads.mediation.AdUrlAdapter".equals(this.zzbwa)) {
                return zza(new AdUrlAdapter());
            }
            if ("com.google.ads.mediation.admob.AdMobCustomTabsAdapter".equals(this.zzbwa)) {
                return new zzhg(new zzho());
            }
        }
        try {
            return this.zzamf.zzbu(this.zzbwa);
        } catch (Throwable e) {
            Throwable th = e;
            String str2 = "Could not instantiate mediation adapter: ";
            valueOf = String.valueOf(this.zzbwa);
            zzb.zza(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), th);
            return null;
        }
    }

    private boolean zzoi() {
        return this.zzbwc.zzbvt != -1;
    }

    private int zzoj() {
        if (this.zzbwd.zzbva == null) {
            return 0;
        }
        try {
            JSONObject jSONObject = new JSONObject(this.zzbwd.zzbva);
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzbwa)) {
                return jSONObject.optInt("cpm_cents", 0);
            }
            int optInt = zzae(2) ? jSONObject.optInt("cpm_floor_cents", 0) : 0;
            return optInt == 0 ? jSONObject.optInt("penalized_average_cpm_cents", 0) : optInt;
        } catch (JSONException e) {
            zzb.zzdi("Could not convert to json. Returning 0");
            return 0;
        }
    }

    public void cancel() {
        synchronized (this.zzako) {
            try {
                if (this.zzbwf != null) {
                    this.zzbwf.destroy();
                }
            } catch (Throwable e) {
                zzb.zzc("Could not destroy mediation adapter.", e);
            }
            this.zzbwg = -1;
            this.zzako.notify();
        }
    }

    public zzgu zza(long j, long j2) {
        zzgu com_google_android_gms_internal_zzgu;
        synchronized (this.zzako) {
            long elapsedRealtime = SystemClock.elapsedRealtime();
            zzgs com_google_android_gms_internal_zzgs = new zzgs();
            zzlb.zzcvl.post(new C13591(this, com_google_android_gms_internal_zzgs));
            zzgs com_google_android_gms_internal_zzgs2 = com_google_android_gms_internal_zzgs;
            com_google_android_gms_internal_zzgu = new zzgu(this.zzbwd, this.zzbwf, this.zzbwa, com_google_android_gms_internal_zzgs2, this.zzbwg, zzog(), zza(elapsedRealtime, this.zzbwb, j, j2));
        }
        return com_google_android_gms_internal_zzgu;
    }

    protected zzha zza(MediationAdapter mediationAdapter) {
        return new zzhg(mediationAdapter);
    }

    public void zza(int i, zzhc com_google_android_gms_internal_zzhc) {
        synchronized (this.zzako) {
            this.zzbwg = i;
            this.zzbwh = com_google_android_gms_internal_zzhc;
            this.zzako.notify();
        }
    }

    public void zzad(int i) {
        synchronized (this.zzako) {
            this.zzbwg = i;
            this.zzako.notify();
        }
    }
}
