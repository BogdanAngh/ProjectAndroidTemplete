package com.google.android.gms.internal;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.Base64;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzjr.zza;
import com.mp3download.zingmp3.BuildConfig;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;

@zzji
public class zzfz {
    private final Map<zzga, zzgb> zzbsl;
    private final LinkedList<zzga> zzbsm;
    @Nullable
    private zzfw zzbsn;

    public zzfz() {
        this.zzbsl = new HashMap();
        this.zzbsm = new LinkedList();
    }

    private static void zza(String str, zzga com_google_android_gms_internal_zzga) {
        if (zzb.zzbi(2)) {
            zzkx.m1697v(String.format(str, new Object[]{com_google_android_gms_internal_zzga}));
        }
    }

    private String[] zzbl(String str) {
        try {
            String[] split = str.split("\u0000");
            for (int i = 0; i < split.length; i++) {
                split[i] = new String(Base64.decode(split[i], 0), C0989C.UTF8_NAME);
            }
            return split;
        } catch (UnsupportedEncodingException e) {
            return new String[0];
        }
    }

    private boolean zzbm(String str) {
        try {
            return Pattern.matches((String) zzdr.zzbgn.get(), str);
        } catch (Throwable e) {
            zzu.zzgq().zza(e, "InterstitialAdPool.isExcludedAdUnit");
            return false;
        }
    }

    private static void zzc(Bundle bundle, String str) {
        String[] split = str.split("/", 2);
        if (split.length != 0) {
            String str2 = split[0];
            if (split.length == 1) {
                bundle.remove(str2);
                return;
            }
            Bundle bundle2 = bundle.getBundle(str2);
            if (bundle2 != null) {
                zzc(bundle2, split[1]);
            }
        }
    }

    @Nullable
    static Bundle zzk(AdRequestParcel adRequestParcel) {
        Bundle bundle = adRequestParcel.zzayv;
        return bundle == null ? null : bundle.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
    }

    static AdRequestParcel zzl(AdRequestParcel adRequestParcel) {
        AdRequestParcel zzo = zzo(adRequestParcel);
        Bundle zzk = zzk(zzo);
        if (zzk == null) {
            zzk = new Bundle();
            zzo.zzayv.putBundle("com.google.ads.mediation.admob.AdMobAdapter", zzk);
        }
        zzk.putBoolean("_skipMediation", true);
        return zzo;
    }

    static boolean zzm(AdRequestParcel adRequestParcel) {
        Bundle bundle = adRequestParcel.zzayv;
        if (bundle == null) {
            return false;
        }
        bundle = bundle.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        return bundle != null && bundle.containsKey("_skipMediation");
    }

    private static AdRequestParcel zzn(AdRequestParcel adRequestParcel) {
        AdRequestParcel zzo = zzo(adRequestParcel);
        for (String zzc : ((String) zzdr.zzbgj.get()).split(",")) {
            zzc(zzo.zzayv, zzc);
        }
        return zzo;
    }

    private String zznn() {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator it = this.zzbsm.iterator();
            while (it.hasNext()) {
                stringBuilder.append(Base64.encodeToString(((zzga) it.next()).toString().getBytes(C0989C.UTF8_NAME), 0));
                if (it.hasNext()) {
                    stringBuilder.append("\u0000");
                }
            }
            return stringBuilder.toString();
        } catch (UnsupportedEncodingException e) {
            return BuildConfig.FLAVOR;
        }
    }

    static AdRequestParcel zzo(AdRequestParcel adRequestParcel) {
        Parcel obtain = Parcel.obtain();
        adRequestParcel.writeToParcel(obtain, 0);
        obtain.setDataPosition(0);
        AdRequestParcel adRequestParcel2 = (AdRequestParcel) AdRequestParcel.CREATOR.createFromParcel(obtain);
        obtain.recycle();
        AdRequestParcel.zzj(adRequestParcel2);
        return adRequestParcel2;
    }

    void flush() {
        while (this.zzbsm.size() > 0) {
            zzga com_google_android_gms_internal_zzga = (zzga) this.zzbsm.remove();
            zzgb com_google_android_gms_internal_zzgb = (zzgb) this.zzbsl.get(com_google_android_gms_internal_zzga);
            zza("Flushing interstitial queue for %s.", com_google_android_gms_internal_zzga);
            while (com_google_android_gms_internal_zzgb.size() > 0) {
                com_google_android_gms_internal_zzgb.zzp(null).zzbss.zzfn();
            }
            this.zzbsl.remove(com_google_android_gms_internal_zzga);
        }
    }

    void restore() {
        if (this.zzbsn != null) {
            SharedPreferences sharedPreferences = this.zzbsn.getApplicationContext().getSharedPreferences("com.google.android.gms.ads.internal.interstitial.InterstitialAdPool", 0);
            flush();
            try {
                Map hashMap = new HashMap();
                for (Entry entry : sharedPreferences.getAll().entrySet()) {
                    if (!((String) entry.getKey()).equals("PoolKeys")) {
                        zzgd zzbn = zzgd.zzbn((String) entry.getValue());
                        zzga com_google_android_gms_internal_zzga = new zzga(zzbn.zzapj, zzbn.zzant, zzbn.zzbsq);
                        if (!this.zzbsl.containsKey(com_google_android_gms_internal_zzga)) {
                            this.zzbsl.put(com_google_android_gms_internal_zzga, new zzgb(zzbn.zzapj, zzbn.zzant, zzbn.zzbsq));
                            hashMap.put(com_google_android_gms_internal_zzga.toString(), com_google_android_gms_internal_zzga);
                            zza("Restored interstitial queue for %s.", com_google_android_gms_internal_zzga);
                        }
                    }
                }
                for (Object obj : zzbl(sharedPreferences.getString("PoolKeys", BuildConfig.FLAVOR))) {
                    zzga com_google_android_gms_internal_zzga2 = (zzga) hashMap.get(obj);
                    if (this.zzbsl.containsKey(com_google_android_gms_internal_zzga2)) {
                        this.zzbsm.add(com_google_android_gms_internal_zzga2);
                    }
                }
            } catch (Throwable th) {
                zzu.zzgq().zza(th, "InterstitialAdPool.restore");
                zzb.zzc("Malformed preferences value for InterstitialAdPool.", th);
                this.zzbsl.clear();
                this.zzbsm.clear();
            }
        }
    }

    void save() {
        if (this.zzbsn != null) {
            Editor edit = this.zzbsn.getApplicationContext().getSharedPreferences("com.google.android.gms.ads.internal.interstitial.InterstitialAdPool", 0).edit();
            edit.clear();
            for (Entry entry : this.zzbsl.entrySet()) {
                zzga com_google_android_gms_internal_zzga = (zzga) entry.getKey();
                zzgb com_google_android_gms_internal_zzgb = (zzgb) entry.getValue();
                if (com_google_android_gms_internal_zzgb.zzns()) {
                    edit.putString(com_google_android_gms_internal_zzga.toString(), new zzgd(com_google_android_gms_internal_zzgb).zznv());
                    zza("Saved interstitial queue for %s.", com_google_android_gms_internal_zzga);
                }
            }
            edit.putString("PoolKeys", zznn());
            edit.apply();
        }
    }

    @Nullable
    zza zza(AdRequestParcel adRequestParcel, String str) {
        if (zzbm(str)) {
            return null;
        }
        zzgb com_google_android_gms_internal_zzgb;
        int i = new zza(this.zzbsn.getApplicationContext()).zztr().zzcqe;
        AdRequestParcel zzn = zzn(adRequestParcel);
        zzga com_google_android_gms_internal_zzga = new zzga(zzn, str, i);
        zzgb com_google_android_gms_internal_zzgb2 = (zzgb) this.zzbsl.get(com_google_android_gms_internal_zzga);
        if (com_google_android_gms_internal_zzgb2 == null) {
            zza("Interstitial pool created at %s.", com_google_android_gms_internal_zzga);
            com_google_android_gms_internal_zzgb2 = new zzgb(zzn, str, i);
            this.zzbsl.put(com_google_android_gms_internal_zzga, com_google_android_gms_internal_zzgb2);
            com_google_android_gms_internal_zzgb = com_google_android_gms_internal_zzgb2;
        } else {
            com_google_android_gms_internal_zzgb = com_google_android_gms_internal_zzgb2;
        }
        this.zzbsm.remove(com_google_android_gms_internal_zzga);
        this.zzbsm.add(com_google_android_gms_internal_zzga);
        com_google_android_gms_internal_zzgb.zznr();
        while (this.zzbsm.size() > ((Integer) zzdr.zzbgk.get()).intValue()) {
            zzga com_google_android_gms_internal_zzga2 = (zzga) this.zzbsm.remove();
            zzgb com_google_android_gms_internal_zzgb3 = (zzgb) this.zzbsl.get(com_google_android_gms_internal_zzga2);
            zza("Evicting interstitial queue for %s.", com_google_android_gms_internal_zzga2);
            while (com_google_android_gms_internal_zzgb3.size() > 0) {
                com_google_android_gms_internal_zzgb3.zzp(null).zzbss.zzfn();
            }
            this.zzbsl.remove(com_google_android_gms_internal_zzga2);
        }
        while (com_google_android_gms_internal_zzgb.size() > 0) {
            zza zzp = com_google_android_gms_internal_zzgb.zzp(zzn);
            if (!zzp.zzbsw || zzu.zzgs().currentTimeMillis() - zzp.zzbsv <= 1000 * ((long) ((Integer) zzdr.zzbgm.get()).intValue())) {
                String str2 = zzp.zzbst != null ? " (inline) " : " ";
                zza(new StringBuilder(String.valueOf(str2).length() + 34).append("Pooled interstitial").append(str2).append("returned at %s.").toString(), com_google_android_gms_internal_zzga);
                return zzp;
            }
            zza("Expired interstitial at %s.", com_google_android_gms_internal_zzga);
        }
        return null;
    }

    void zza(zzfw com_google_android_gms_internal_zzfw) {
        if (this.zzbsn == null) {
            this.zzbsn = com_google_android_gms_internal_zzfw.zznl();
            restore();
        }
    }

    void zzb(AdRequestParcel adRequestParcel, String str) {
        if (this.zzbsn != null) {
            int i = new zza(this.zzbsn.getApplicationContext()).zztr().zzcqe;
            AdRequestParcel zzn = zzn(adRequestParcel);
            zzga com_google_android_gms_internal_zzga = new zzga(zzn, str, i);
            zzgb com_google_android_gms_internal_zzgb = (zzgb) this.zzbsl.get(com_google_android_gms_internal_zzga);
            if (com_google_android_gms_internal_zzgb == null) {
                zza("Interstitial pool created at %s.", com_google_android_gms_internal_zzga);
                com_google_android_gms_internal_zzgb = new zzgb(zzn, str, i);
                this.zzbsl.put(com_google_android_gms_internal_zzga, com_google_android_gms_internal_zzgb);
            }
            com_google_android_gms_internal_zzgb.zza(this.zzbsn, adRequestParcel);
            com_google_android_gms_internal_zzgb.zznr();
            zza("Inline entry added to the queue at %s.", com_google_android_gms_internal_zzga);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    void zznm() {
        /*
        r9 = this;
        r8 = 2;
        r0 = r9.zzbsn;
        if (r0 != 0) goto L_0x0006;
    L_0x0005:
        return;
    L_0x0006:
        r0 = r9.zzbsl;
        r0 = r0.entrySet();
        r3 = r0.iterator();
    L_0x0010:
        r0 = r3.hasNext();
        if (r0 == 0) goto L_0x0076;
    L_0x0016:
        r0 = r3.next();
        r0 = (java.util.Map.Entry) r0;
        r1 = r0.getKey();
        r1 = (com.google.android.gms.internal.zzga) r1;
        r0 = r0.getValue();
        r0 = (com.google.android.gms.internal.zzgb) r0;
        r2 = com.google.android.gms.ads.internal.util.client.zzb.zzbi(r8);
        if (r2 == 0) goto L_0x0056;
    L_0x002e:
        r2 = r0.size();
        r4 = r0.zznp();
        if (r4 >= r2) goto L_0x0056;
    L_0x0038:
        r5 = "Loading %s/%s pooled interstitials for %s.";
        r6 = 3;
        r6 = new java.lang.Object[r6];
        r7 = 0;
        r4 = r2 - r4;
        r4 = java.lang.Integer.valueOf(r4);
        r6[r7] = r4;
        r4 = 1;
        r2 = java.lang.Integer.valueOf(r2);
        r6[r4] = r2;
        r6[r8] = r1;
        r2 = java.lang.String.format(r5, r6);
        com.google.android.gms.internal.zzkx.m1697v(r2);
    L_0x0056:
        r0.zznq();
    L_0x0059:
        r4 = r0.size();
        r2 = com.google.android.gms.internal.zzdr.zzbgl;
        r2 = r2.get();
        r2 = (java.lang.Integer) r2;
        r2 = r2.intValue();
        if (r4 >= r2) goto L_0x0010;
    L_0x006b:
        r2 = "Pooling and loading one new interstitial for %s.";
        zza(r2, r1);
        r2 = r9.zzbsn;
        r0.zzb(r2);
        goto L_0x0059;
    L_0x0076:
        r9.save();
        goto L_0x0005;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzfz.zznm():void");
    }
}
