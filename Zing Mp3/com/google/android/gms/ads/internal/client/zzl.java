package com.google.android.gms.ads.internal.client;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.widget.FrameLayout;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.reward.client.zzf;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzei;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzhx;
import com.google.android.gms.internal.zzhy;
import com.google.android.gms.internal.zzih;
import com.google.android.gms.internal.zzim;
import com.google.android.gms.internal.zzji;

@zzji
public class zzl {
    private final Object zzako;
    private zzx zzazy;
    private final zze zzazz;
    private final zzd zzbaa;
    private final zzai zzbab;
    private final zzeu zzbac;
    private final zzf zzbad;
    private final zzim zzbae;
    private final zzhx zzbaf;

    @VisibleForTesting
    abstract class zza<T> {
        final /* synthetic */ zzl zzbai;

        zza(zzl com_google_android_gms_ads_internal_client_zzl) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
        }

        @Nullable
        protected abstract T zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException;

        @Nullable
        protected abstract T zzkh() throws RemoteException;

        @Nullable
        protected final T zzko() {
            T t = null;
            zzx zza = this.zzbai.zzkf();
            if (zza == null) {
                zzb.zzdi("ClientApi class cannot be loaded.");
            } else {
                try {
                    t = zzb(zza);
                } catch (Throwable e) {
                    zzb.zzc("Cannot invoke local loader using ClientApi class", e);
                }
            }
            return t;
        }

        @Nullable
        protected final T zzkp() {
            try {
                return zzkh();
            } catch (Throwable e) {
                zzb.zzc("Cannot invoke remote loader", e);
                return null;
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.1 */
    class C10591 extends zza<zzu> {
        final /* synthetic */ String zzane;
        final /* synthetic */ Context zzang;
        final /* synthetic */ AdSizeParcel zzbag;
        final /* synthetic */ zzgz zzbah;
        final /* synthetic */ zzl zzbai;

        C10591(zzl com_google_android_gms_ads_internal_client_zzl, Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.zzang = context;
            this.zzbag = adSizeParcel;
            this.zzane = str;
            this.zzbah = com_google_android_gms_internal_zzgz;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public zzu zza(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.createBannerAdManager(zze.zzac(this.zzang), this.zzbag, this.zzane, this.zzbah, 9877000);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zza(com_google_android_gms_ads_internal_client_zzx);
        }

        public zzu zzkg() {
            zzu zza = this.zzbai.zzazz.zza(this.zzang, this.zzbag, this.zzane, this.zzbah, 1);
            if (zza != null) {
                return zza;
            }
            this.zzbai.zzc(this.zzang, "banner");
            return new zzak();
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzkg();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.2 */
    class C10602 extends zza<zzu> {
        final /* synthetic */ String zzane;
        final /* synthetic */ Context zzang;
        final /* synthetic */ AdSizeParcel zzbag;
        final /* synthetic */ zzl zzbai;

        C10602(zzl com_google_android_gms_ads_internal_client_zzl, Context context, AdSizeParcel adSizeParcel, String str) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.zzang = context;
            this.zzbag = adSizeParcel;
            this.zzane = str;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public zzu zza(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.createSearchAdManager(zze.zzac(this.zzang), this.zzbag, this.zzane, 9877000);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zza(com_google_android_gms_ads_internal_client_zzx);
        }

        public zzu zzkg() {
            zzu zza = this.zzbai.zzazz.zza(this.zzang, this.zzbag, this.zzane, null, 3);
            if (zza != null) {
                return zza;
            }
            this.zzbai.zzc(this.zzang, "search");
            return new zzak();
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzkg();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.3 */
    class C10613 extends zza<zzu> {
        final /* synthetic */ String zzane;
        final /* synthetic */ Context zzang;
        final /* synthetic */ AdSizeParcel zzbag;
        final /* synthetic */ zzgz zzbah;
        final /* synthetic */ zzl zzbai;

        C10613(zzl com_google_android_gms_ads_internal_client_zzl, Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.zzang = context;
            this.zzbag = adSizeParcel;
            this.zzane = str;
            this.zzbah = com_google_android_gms_internal_zzgz;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public zzu zza(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.createInterstitialAdManager(zze.zzac(this.zzang), this.zzbag, this.zzane, this.zzbah, 9877000);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zza(com_google_android_gms_ads_internal_client_zzx);
        }

        public zzu zzkg() {
            zzu zza = this.zzbai.zzazz.zza(this.zzang, this.zzbag, this.zzane, this.zzbah, 2);
            if (zza != null) {
                return zza;
            }
            this.zzbai.zzc(this.zzang, "interstitial");
            return new zzak();
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzkg();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.4 */
    class C10624 extends zza<zzs> {
        final /* synthetic */ String zzane;
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzgz zzbah;
        final /* synthetic */ zzl zzbai;

        C10624(zzl com_google_android_gms_ads_internal_client_zzl, Context context, String str, zzgz com_google_android_gms_internal_zzgz) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.zzang = context;
            this.zzane = str;
            this.zzbah = com_google_android_gms_internal_zzgz;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zzc(com_google_android_gms_ads_internal_client_zzx);
        }

        public zzs zzc(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.createAdLoaderBuilder(zze.zzac(this.zzang), this.zzane, this.zzbah, 9877000);
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzki();
        }

        public zzs zzki() {
            zzs zza = this.zzbai.zzbaa.zza(this.zzang, this.zzane, this.zzbah);
            if (zza != null) {
                return zza;
            }
            this.zzbai.zzc(this.zzang, "native_ad");
            return new zzaj();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.5 */
    class C10635 extends zza<zzz> {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzl zzbai;

        C10635(zzl com_google_android_gms_ads_internal_client_zzl, Context context) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.zzang = context;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zzd(com_google_android_gms_ads_internal_client_zzx);
        }

        public zzz zzd(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.getMobileAdsSettingsManagerWithClientJarVersion(zze.zzac(this.zzang), 9877000);
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzkj();
        }

        public zzz zzkj() {
            zzz zzl = this.zzbai.zzbab.zzl(this.zzang);
            if (zzl != null) {
                return zzl;
            }
            this.zzbai.zzc(this.zzang, "mobile_ads_settings");
            return new zzal();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.6 */
    class C10646 extends zza<zzei> {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzl zzbai;
        final /* synthetic */ FrameLayout zzbaj;
        final /* synthetic */ FrameLayout zzbak;

        C10646(zzl com_google_android_gms_ads_internal_client_zzl, FrameLayout frameLayout, FrameLayout frameLayout2, Context context) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.zzbaj = frameLayout;
            this.zzbak = frameLayout2;
            this.zzang = context;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zze(com_google_android_gms_ads_internal_client_zzx);
        }

        public zzei zze(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.createNativeAdViewDelegate(zze.zzac(this.zzbaj), zze.zzac(this.zzbak));
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzkk();
        }

        public zzei zzkk() {
            zzei zzb = this.zzbai.zzbac.zzb(this.zzang, this.zzbaj, this.zzbak);
            if (zzb != null) {
                return zzb;
            }
            this.zzbai.zzc(this.zzang, "native_ad_view_delegate");
            return new zzam();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.7 */
    class C10657 extends zza<com.google.android.gms.ads.internal.reward.client.zzb> {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzgz zzbah;
        final /* synthetic */ zzl zzbai;

        C10657(zzl com_google_android_gms_ads_internal_client_zzl, Context context, zzgz com_google_android_gms_internal_zzgz) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.zzang = context;
            this.zzbah = com_google_android_gms_internal_zzgz;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zzf(com_google_android_gms_ads_internal_client_zzx);
        }

        public com.google.android.gms.ads.internal.reward.client.zzb zzf(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.createRewardedVideoAd(zze.zzac(this.zzang), this.zzbah, 9877000);
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzkl();
        }

        public com.google.android.gms.ads.internal.reward.client.zzb zzkl() {
            com.google.android.gms.ads.internal.reward.client.zzb zzb = this.zzbai.zzbad.zzb(this.zzang, this.zzbah);
            if (zzb != null) {
                return zzb;
            }
            this.zzbai.zzc(this.zzang, "rewarded_video");
            return new zzan();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.8 */
    class C10668 extends zza<zzih> {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ zzl zzbai;

        C10668(zzl com_google_android_gms_ads_internal_client_zzl, Activity activity) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.val$activity = activity;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zzg(com_google_android_gms_ads_internal_client_zzx);
        }

        public zzih zzg(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.createInAppPurchaseManager(zze.zzac(this.val$activity));
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzkm();
        }

        public zzih zzkm() {
            zzih zzg = this.zzbai.zzbae.zzg(this.val$activity);
            if (zzg != null) {
                return zzg;
            }
            this.zzbai.zzc(this.val$activity, "iap");
            return null;
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.client.zzl.9 */
    class C10679 extends zza<zzhy> {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ zzl zzbai;

        C10679(zzl com_google_android_gms_ads_internal_client_zzl, Activity activity) {
            this.zzbai = com_google_android_gms_ads_internal_client_zzl;
            this.val$activity = activity;
            super(com_google_android_gms_ads_internal_client_zzl);
        }

        public /* synthetic */ Object zzb(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return zzh(com_google_android_gms_ads_internal_client_zzx);
        }

        public zzhy zzh(zzx com_google_android_gms_ads_internal_client_zzx) throws RemoteException {
            return com_google_android_gms_ads_internal_client_zzx.createAdOverlay(zze.zzac(this.val$activity));
        }

        public /* synthetic */ Object zzkh() throws RemoteException {
            return zzkn();
        }

        public zzhy zzkn() {
            zzhy zzf = this.zzbai.zzbaf.zzf(this.val$activity);
            if (zzf != null) {
                return zzf;
            }
            this.zzbai.zzc(this.val$activity, "ad_overlay");
            return null;
        }
    }

    public zzl(zze com_google_android_gms_ads_internal_client_zze, zzd com_google_android_gms_ads_internal_client_zzd, zzai com_google_android_gms_ads_internal_client_zzai, zzeu com_google_android_gms_internal_zzeu, zzf com_google_android_gms_ads_internal_reward_client_zzf, zzim com_google_android_gms_internal_zzim, zzhx com_google_android_gms_internal_zzhx) {
        this.zzako = new Object();
        this.zzazz = com_google_android_gms_ads_internal_client_zze;
        this.zzbaa = com_google_android_gms_ads_internal_client_zzd;
        this.zzbab = com_google_android_gms_ads_internal_client_zzai;
        this.zzbac = com_google_android_gms_internal_zzeu;
        this.zzbad = com_google_android_gms_ads_internal_reward_client_zzf;
        this.zzbae = com_google_android_gms_internal_zzim;
        this.zzbaf = com_google_android_gms_internal_zzhx;
    }

    private static boolean zza(Activity activity, String str) {
        Intent intent = activity.getIntent();
        if (intent.hasExtra(str)) {
            return intent.getBooleanExtra(str, false);
        }
        zzb.m1695e("useClientJar flag not found in activity intent extras.");
        return false;
    }

    private void zzc(Context context, String str) {
        Bundle bundle = new Bundle();
        bundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "no_ads_fallback");
        bundle.putString("flow", str);
        zzm.zzkr().zza(context, null, "gmob-apps", bundle, true);
    }

    @Nullable
    private static zzx zzke() {
        try {
            Object newInstance = zzl.class.getClassLoader().loadClass("com.google.android.gms.ads.internal.ClientApi").newInstance();
            if (newInstance instanceof IBinder) {
                return com.google.android.gms.ads.internal.client.zzx.zza.asInterface((IBinder) newInstance);
            }
            zzb.zzdi("ClientApi class is not an instance of IBinder");
            return null;
        } catch (Throwable e) {
            zzb.zzc("Failed to instantiate ClientApi class.", e);
            return null;
        }
    }

    @Nullable
    private zzx zzkf() {
        zzx com_google_android_gms_ads_internal_client_zzx;
        synchronized (this.zzako) {
            if (this.zzazy == null) {
                this.zzazy = zzke();
            }
            com_google_android_gms_ads_internal_client_zzx = this.zzazy;
        }
        return com_google_android_gms_ads_internal_client_zzx;
    }

    public zzu zza(Context context, AdSizeParcel adSizeParcel, String str) {
        return (zzu) zza(context, false, new C10602(this, context, adSizeParcel, str));
    }

    public zzu zza(Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz) {
        return (zzu) zza(context, false, new C10591(this, context, adSizeParcel, str, com_google_android_gms_internal_zzgz));
    }

    public com.google.android.gms.ads.internal.reward.client.zzb zza(Context context, zzgz com_google_android_gms_internal_zzgz) {
        return (com.google.android.gms.ads.internal.reward.client.zzb) zza(context, false, new C10657(this, context, com_google_android_gms_internal_zzgz));
    }

    public zzei zza(Context context, FrameLayout frameLayout, FrameLayout frameLayout2) {
        return (zzei) zza(context, false, new C10646(this, frameLayout, frameLayout2, context));
    }

    @VisibleForTesting
    <T> T zza(Context context, boolean z, zza<T> com_google_android_gms_ads_internal_client_zzl_zza_T) {
        if (!(z || zzm.zzkr().zzap(context))) {
            zzb.zzdg("Google Play Services is not available");
            z = true;
        }
        T zzko;
        if (z) {
            zzko = com_google_android_gms_ads_internal_client_zzl_zza_T.zzko();
            return zzko == null ? com_google_android_gms_ads_internal_client_zzl_zza_T.zzkp() : zzko;
        } else {
            zzko = com_google_android_gms_ads_internal_client_zzl_zza_T.zzkp();
            return zzko == null ? com_google_android_gms_ads_internal_client_zzl_zza_T.zzko() : zzko;
        }
    }

    public zzs zzb(Context context, String str, zzgz com_google_android_gms_internal_zzgz) {
        return (zzs) zza(context, false, new C10624(this, context, str, com_google_android_gms_internal_zzgz));
    }

    public zzu zzb(Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz) {
        return (zzu) zza(context, false, new C10613(this, context, adSizeParcel, str, com_google_android_gms_internal_zzgz));
    }

    @Nullable
    public zzih zzb(Activity activity) {
        return (zzih) zza((Context) activity, zza(activity, "com.google.android.gms.ads.internal.purchase.useClientJar"), new C10668(this, activity));
    }

    @Nullable
    public zzhy zzc(Activity activity) {
        return (zzhy) zza((Context) activity, zza(activity, "com.google.android.gms.ads.internal.overlay.useClientJar"), new C10679(this, activity));
    }

    public zzz zzk(Context context) {
        return (zzz) zza(context, false, new C10635(this, context));
    }
}
