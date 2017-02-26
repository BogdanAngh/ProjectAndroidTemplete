package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.zzn;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.internal.zzko.zza;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@zzji
public class zziy extends zzit {
    private final zzdz zzalt;
    private zzgz zzamf;
    private final zzmd zzbnz;
    private zzgq zzbwc;
    zzgo zzchc;
    protected zzgu zzchd;
    private boolean zzche;

    /* renamed from: com.google.android.gms.internal.zziy.1 */
    class C13891 implements Runnable {
        final /* synthetic */ CountDownLatch zzamc;
        final /* synthetic */ zziy zzchf;

        C13891(zziy com_google_android_gms_internal_zziy, CountDownLatch countDownLatch) {
            this.zzchf = com_google_android_gms_internal_zziy;
            this.zzamc = countDownLatch;
        }

        public void run() {
            synchronized (this.zzchf.zzcgi) {
                this.zzchf.zzche = zzn.zza(this.zzchf.zzbnz, this.zzchf.zzchd, this.zzamc);
            }
        }
    }

    zziy(Context context, zza com_google_android_gms_internal_zzko_zza, zzgz com_google_android_gms_internal_zzgz, zziu.zza com_google_android_gms_internal_zziu_zza, zzdz com_google_android_gms_internal_zzdz, zzmd com_google_android_gms_internal_zzmd) {
        super(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zziu_zza);
        this.zzamf = com_google_android_gms_internal_zzgz;
        this.zzbwc = com_google_android_gms_internal_zzko_zza.zzcsk;
        this.zzalt = com_google_android_gms_internal_zzdz;
        this.zzbnz = com_google_android_gms_internal_zzmd;
    }

    private static String zza(zzgu com_google_android_gms_internal_zzgu) {
        String str = com_google_android_gms_internal_zzgu.zzbwm.zzbuv;
        int zzar = zzar(com_google_android_gms_internal_zzgu.zzbwl);
        return new StringBuilder(String.valueOf(str).length() + 33).append(str).append(".").append(zzar).append(".").append(com_google_android_gms_internal_zzgu.zzbwr).toString();
    }

    private static int zzar(int i) {
        switch (i) {
            case CommonStatusCodes.SUCCESS_CACHE /*-1*/:
                return 4;
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                return 0;
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return 1;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return 2;
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return 3;
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return 5;
            default:
                return 6;
        }
    }

    private static String zzg(List<zzgu> list) {
        String str = BuildConfig.FLAVOR;
        if (list == null) {
            return str.toString();
        }
        String str2 = str;
        for (zzgu com_google_android_gms_internal_zzgu : list) {
            if (!(com_google_android_gms_internal_zzgu == null || com_google_android_gms_internal_zzgu.zzbwm == null || TextUtils.isEmpty(com_google_android_gms_internal_zzgu.zzbwm.zzbuv))) {
                str2 = String.valueOf(str2);
                str = String.valueOf(zza(com_google_android_gms_internal_zzgu));
                str2 = new StringBuilder((String.valueOf(str2).length() + 1) + String.valueOf(str).length()).append(str2).append(str).append("_").toString();
            }
        }
        return str2.substring(0, Math.max(0, str2.length() - 1));
    }

    private void zzsg() throws zza {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        zzlb.zzcvl.post(new C13891(this, countDownLatch));
        try {
            countDownLatch.await(10, TimeUnit.SECONDS);
            synchronized (this.zzcgi) {
                if (!this.zzche) {
                    throw new zza("View could not be prepared", 0);
                } else if (this.zzbnz.isDestroyed()) {
                    throw new zza("Assets not loaded, web view is destroyed", 0);
                }
            }
        } catch (InterruptedException e) {
            String valueOf = String.valueOf(e);
            throw new zza(new StringBuilder(String.valueOf(valueOf).length() + 38).append("Interrupted while waiting for latch : ").append(valueOf).toString(), 0);
        }
    }

    public void onStop() {
        synchronized (this.zzcgi) {
            super.onStop();
            if (this.zzchc != null) {
                this.zzchc.cancel();
            }
        }
    }

    protected zzko zzap(int i) {
        AdRequestInfoParcel adRequestInfoParcel = this.zzcgf.zzcmx;
        return new zzko(adRequestInfoParcel.zzcju, this.zzbnz, this.zzcgg.zzbvk, i, this.zzcgg.zzbvl, this.zzcgg.zzcld, this.zzcgg.orientation, this.zzcgg.zzbvq, adRequestInfoParcel.zzcjx, this.zzcgg.zzclb, this.zzchd != null ? this.zzchd.zzbwm : null, this.zzchd != null ? this.zzchd.zzbwn : null, this.zzchd != null ? this.zzchd.zzbwo : AdMobAdapter.class.getName(), this.zzbwc, this.zzchd != null ? this.zzchd.zzbwp : null, this.zzcgg.zzclc, this.zzcgf.zzarm, this.zzcgg.zzcla, this.zzcgf.zzcso, this.zzcgg.zzclf, this.zzcgg.zzclg, this.zzcgf.zzcsi, null, this.zzcgg.zzclq, this.zzcgg.zzclr, this.zzcgg.zzcls, this.zzbwc != null ? this.zzbwc.zzbvv : false, this.zzcgg.zzclu, this.zzchc != null ? zzg(this.zzchc.zzoe()) : null, this.zzcgg.zzbvn, this.zzcgg.zzclx);
    }

    protected void zzh(long j) throws zza {
        boolean z;
        ListIterator listIterator;
        synchronized (this.zzcgi) {
            this.zzchc = zzi(j);
        }
        List arrayList = new ArrayList(this.zzbwc.zzbvi);
        Bundle bundle = this.zzcgf.zzcmx.zzcju.zzayv;
        String str = "com.google.ads.mediation.admob.AdMobAdapter";
        if (bundle != null) {
            bundle = bundle.getBundle(str);
            if (bundle != null) {
                z = bundle.getBoolean("_skipMediation");
                if (z) {
                    listIterator = arrayList.listIterator();
                    while (listIterator.hasNext()) {
                        if (!((zzgp) listIterator.next()).zzbuu.contains(str)) {
                            listIterator.remove();
                        }
                    }
                }
                this.zzchd = this.zzchc.zzd(arrayList);
                switch (this.zzchd.zzbwl) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        if (this.zzchd.zzbwm != null && this.zzchd.zzbwm.zzbvd != null) {
                            zzsg();
                            return;
                        }
                    case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                        throw new zza("No fill from any mediation ad networks.", 3);
                    default:
                        throw new zza("Unexpected mediation result: " + this.zzchd.zzbwl, 0);
                }
            }
        }
        z = false;
        if (z) {
            listIterator = arrayList.listIterator();
            while (listIterator.hasNext()) {
                if (!((zzgp) listIterator.next()).zzbuu.contains(str)) {
                    listIterator.remove();
                }
            }
        }
        this.zzchd = this.zzchc.zzd(arrayList);
        switch (this.zzchd.zzbwl) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                if (this.zzchd.zzbwm != null) {
                }
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                throw new zza("No fill from any mediation ad networks.", 3);
            default:
                throw new zza("Unexpected mediation result: " + this.zzchd.zzbwl, 0);
        }
    }

    zzgo zzi(long j) {
        if (this.zzbwc.zzbvt != -1) {
            return new zzgw(this.mContext, this.zzcgf.zzcmx, this.zzamf, this.zzbwc, this.zzcgg.zzazt, this.zzcgg.zzazv, j, ((Long) zzdr.zzbhk.get()).longValue(), 2);
        }
        return new zzgx(this.mContext, this.zzcgf.zzcmx, this.zzamf, this.zzbwc, this.zzcgg.zzazt, this.zzcgg.zzazv, j, ((Long) zzdr.zzbhk.get()).longValue(), this.zzalt);
    }
}
