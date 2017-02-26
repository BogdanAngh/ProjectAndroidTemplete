package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzko.zza;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Future;
import org.json.JSONObject;

@zzji
public class zzki extends zzkw implements zzkh {
    private final Context mContext;
    private final Object zzako;
    private final zza zzcgf;
    private final long zzcri;
    private final ArrayList<Future> zzcrt;
    private final ArrayList<String> zzcru;
    private final HashMap<String, zzkc> zzcrv;
    private final List<zzkd> zzcrw;
    private final HashSet<String> zzcrx;
    private final zzkb zzcry;

    /* renamed from: com.google.android.gms.internal.zzki.1 */
    class C14251 implements Runnable {
        final /* synthetic */ zzko zzamz;
        final /* synthetic */ zzki zzcrz;

        C14251(zzki com_google_android_gms_internal_zzki, zzko com_google_android_gms_internal_zzko) {
            this.zzcrz = com_google_android_gms_internal_zzki;
            this.zzamz = com_google_android_gms_internal_zzko;
        }

        public void run() {
            this.zzcrz.zzcry.zzb(this.zzamz);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzki.2 */
    class C14262 implements Runnable {
        final /* synthetic */ zzko zzamz;
        final /* synthetic */ zzki zzcrz;

        C14262(zzki com_google_android_gms_internal_zzki, zzko com_google_android_gms_internal_zzko) {
            this.zzcrz = com_google_android_gms_internal_zzki;
            this.zzamz = com_google_android_gms_internal_zzko;
        }

        public void run() {
            this.zzcrz.zzcry.zzb(this.zzamz);
        }
    }

    public zzki(Context context, zza com_google_android_gms_internal_zzko_zza, zzkb com_google_android_gms_internal_zzkb) {
        this(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzkb, ((Long) zzdr.zzbfy.get()).longValue());
    }

    zzki(Context context, zza com_google_android_gms_internal_zzko_zza, zzkb com_google_android_gms_internal_zzkb, long j) {
        this.zzcrt = new ArrayList();
        this.zzcru = new ArrayList();
        this.zzcrv = new HashMap();
        this.zzcrw = new ArrayList();
        this.zzcrx = new HashSet();
        this.zzako = new Object();
        this.mContext = context;
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzcry = com_google_android_gms_internal_zzkb;
        this.zzcri = j;
    }

    private zzko zza(int i, @Nullable String str, @Nullable zzgp com_google_android_gms_internal_zzgp) {
        return new zzko(this.zzcgf.zzcmx.zzcju, null, this.zzcgf.zzcsu.zzbvk, i, this.zzcgf.zzcsu.zzbvl, this.zzcgf.zzcsu.zzcld, this.zzcgf.zzcsu.orientation, this.zzcgf.zzcsu.zzbvq, this.zzcgf.zzcmx.zzcjx, this.zzcgf.zzcsu.zzclb, com_google_android_gms_internal_zzgp, null, str, this.zzcgf.zzcsk, null, this.zzcgf.zzcsu.zzclc, this.zzcgf.zzarm, this.zzcgf.zzcsu.zzcla, this.zzcgf.zzcso, this.zzcgf.zzcsu.zzclf, this.zzcgf.zzcsu.zzclg, this.zzcgf.zzcsi, null, this.zzcgf.zzcsu.zzclq, this.zzcgf.zzcsu.zzclr, this.zzcgf.zzcsu.zzcls, this.zzcgf.zzcsu.zzclt, this.zzcgf.zzcsu.zzclu, zzub(), this.zzcgf.zzcsu.zzbvn, this.zzcgf.zzcsu.zzclx);
    }

    private zzko zza(String str, zzgp com_google_android_gms_internal_zzgp) {
        return zza(-2, str, com_google_android_gms_internal_zzgp);
    }

    private static String zza(zzkd com_google_android_gms_internal_zzkd) {
        String str = com_google_android_gms_internal_zzkd.zzbuv;
        int zzar = zzar(com_google_android_gms_internal_zzkd.errorCode);
        return new StringBuilder(String.valueOf(str).length() + 33).append(str).append(".").append(zzar).append(".").append(com_google_android_gms_internal_zzkd.zzbwr).toString();
    }

    private void zza(String str, String str2, zzgp com_google_android_gms_internal_zzgp) {
        synchronized (this.zzako) {
            zzkj zzcp = this.zzcry.zzcp(str);
            if (zzcp == null || zzcp.zzud() == null || zzcp.zzuc() == null) {
                this.zzcrw.add(new zzkd.zza().zzcs(com_google_android_gms_internal_zzgp.zzbuv).zzcr(str).zzl(0).zzbc(7).zztz());
                return;
            }
            zzkw zza = zza(str, str2, com_google_android_gms_internal_zzgp, zzcp);
            this.zzcrt.add((Future) zza.zzrz());
            this.zzcru.add(str);
            this.zzcrv.put(str, zza);
        }
    }

    private static int zzar(int i) {
        switch (i) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return 1;
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return 2;
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return 4;
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                return 0;
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                return 3;
            default:
                return 6;
        }
    }

    private zzko zzua() {
        return zza(3, null, null);
    }

    private String zzub() {
        StringBuilder stringBuilder = new StringBuilder(BuildConfig.FLAVOR);
        if (this.zzcrw == null) {
            return stringBuilder.toString();
        }
        for (zzkd com_google_android_gms_internal_zzkd : this.zzcrw) {
            if (!(com_google_android_gms_internal_zzkd == null || TextUtils.isEmpty(com_google_android_gms_internal_zzkd.zzbuv))) {
                stringBuilder.append(String.valueOf(zza(com_google_android_gms_internal_zzkd)).concat("_"));
            }
        }
        return stringBuilder.substring(0, Math.max(0, stringBuilder.length() - 1));
    }

    public void onStop() {
    }

    protected zzkc zza(String str, String str2, zzgp com_google_android_gms_internal_zzgp, zzkj com_google_android_gms_internal_zzkj) {
        return new zzkc(this.mContext, str, str2, com_google_android_gms_internal_zzgp, this.zzcgf, com_google_android_gms_internal_zzkj, this, this.zzcri);
    }

    public void zza(String str, int i) {
    }

    public void zzcq(String str) {
        synchronized (this.zzako) {
            this.zzcrx.add(str);
        }
    }

    public void zzfp() {
        String str;
        zzkc com_google_android_gms_internal_zzkc;
        for (zzgp com_google_android_gms_internal_zzgp : this.zzcgf.zzcsk.zzbvi) {
            String str2 = com_google_android_gms_internal_zzgp.zzbva;
            for (String str3 : com_google_android_gms_internal_zzgp.zzbuu) {
                String str32;
                if ("com.google.android.gms.ads.mediation.customevent.CustomEventAdapter".equals(str32) || "com.google.ads.mediation.customevent.CustomEventAdapter".equals(str32)) {
                    try {
                        str32 = new JSONObject(str2).getString("class_name");
                    } catch (Throwable e) {
                        zzb.zzb("Unable to determine custom event class name, skipping...", e);
                    }
                }
                zza(str32, str2, com_google_android_gms_internal_zzgp);
            }
        }
        int i = 0;
        while (i < this.zzcrt.size()) {
            try {
                ((Future) this.zzcrt.get(i)).get();
                synchronized (this.zzako) {
                    str = (String) this.zzcru.get(i);
                    if (!TextUtils.isEmpty(str)) {
                        com_google_android_gms_internal_zzkc = (zzkc) this.zzcrv.get(str);
                        if (com_google_android_gms_internal_zzkc != null) {
                            this.zzcrw.add(com_google_android_gms_internal_zzkc.zztw());
                        }
                    }
                }
                synchronized (this.zzako) {
                    if (this.zzcrx.contains(this.zzcru.get(i))) {
                        str = (String) this.zzcru.get(i);
                        com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C14251(this, zza(str, this.zzcrv.get(str) != null ? ((zzkc) this.zzcrv.get(str)).zztx() : null)));
                        return;
                    }
                    i++;
                }
            } catch (InterruptedException e2) {
                Thread.currentThread().interrupt();
                synchronized (this.zzako) {
                }
                str = (String) this.zzcru.get(i);
                if (!TextUtils.isEmpty(str)) {
                    com_google_android_gms_internal_zzkc = (zzkc) this.zzcrv.get(str);
                    if (com_google_android_gms_internal_zzkc != null) {
                        this.zzcrw.add(com_google_android_gms_internal_zzkc.zztw());
                    }
                }
            } catch (Throwable e3) {
                zzb.zzc("Unable to resolve rewarded adapter.", e3);
                synchronized (this.zzako) {
                }
                str = (String) this.zzcru.get(i);
                if (!TextUtils.isEmpty(str)) {
                    com_google_android_gms_internal_zzkc = (zzkc) this.zzcrv.get(str);
                    if (com_google_android_gms_internal_zzkc != null) {
                        this.zzcrw.add(com_google_android_gms_internal_zzkc.zztw());
                    }
                }
            } catch (Throwable e32) {
                Throwable th = e32;
                synchronized (this.zzako) {
                }
                str = (String) this.zzcru.get(i);
                if (!TextUtils.isEmpty(str)) {
                    com_google_android_gms_internal_zzkc = (zzkc) this.zzcrv.get(str);
                    if (com_google_android_gms_internal_zzkc != null) {
                        this.zzcrw.add(com_google_android_gms_internal_zzkc.zztw());
                    }
                }
            }
        }
        com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C14262(this, zzua()));
    }
}
