package com.google.android.gms.ads.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.client.zzr.zza;
import com.google.android.gms.ads.internal.client.zzy;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzeq;
import com.google.android.gms.internal.zzer;
import com.google.android.gms.internal.zzes;
import com.google.android.gms.internal.zzet;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzlb;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@zzji
public class zzj extends zza {
    private final Context mContext;
    private final Object zzako;
    private final zzd zzamb;
    private final zzgz zzamf;
    private final zzq zzanl;
    @Nullable
    private final zzeq zzanm;
    @Nullable
    private final zzer zzann;
    private final SimpleArrayMap<String, zzet> zzano;
    private final SimpleArrayMap<String, zzes> zzanp;
    private final NativeAdOptionsParcel zzanq;
    private final List<String> zzanr;
    private final zzy zzans;
    private final String zzant;
    private final VersionInfoParcel zzanu;
    @Nullable
    private WeakReference<zzq> zzanv;

    /* renamed from: com.google.android.gms.ads.internal.zzj.1 */
    class C11241 implements Runnable {
        final /* synthetic */ AdRequestParcel zzanw;
        final /* synthetic */ zzj zzanx;

        C11241(zzj com_google_android_gms_ads_internal_zzj, AdRequestParcel adRequestParcel) {
            this.zzanx = com_google_android_gms_ads_internal_zzj;
            this.zzanw = adRequestParcel;
        }

        public void run() {
            synchronized (this.zzanx.zzako) {
                zzq zzfk = this.zzanx.zzfk();
                this.zzanx.zzanv = new WeakReference(zzfk);
                zzfk.zzb(this.zzanx.zzanm);
                zzfk.zzb(this.zzanx.zzann);
                zzfk.zza(this.zzanx.zzano);
                zzfk.zza(this.zzanx.zzanl);
                zzfk.zzb(this.zzanx.zzanp);
                zzfk.zzb(this.zzanx.zzfj());
                zzfk.zzb(this.zzanx.zzanq);
                zzfk.zza(this.zzanx.zzans);
                zzfk.zzb(this.zzanw);
            }
        }
    }

    zzj(Context context, String str, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel, zzq com_google_android_gms_ads_internal_client_zzq, zzeq com_google_android_gms_internal_zzeq, zzer com_google_android_gms_internal_zzer, SimpleArrayMap<String, zzet> simpleArrayMap, SimpleArrayMap<String, zzes> simpleArrayMap2, NativeAdOptionsParcel nativeAdOptionsParcel, zzy com_google_android_gms_ads_internal_client_zzy, zzd com_google_android_gms_ads_internal_zzd) {
        this.zzako = new Object();
        this.mContext = context;
        this.zzant = str;
        this.zzamf = com_google_android_gms_internal_zzgz;
        this.zzanu = versionInfoParcel;
        this.zzanl = com_google_android_gms_ads_internal_client_zzq;
        this.zzann = com_google_android_gms_internal_zzer;
        this.zzanm = com_google_android_gms_internal_zzeq;
        this.zzano = simpleArrayMap;
        this.zzanp = simpleArrayMap2;
        this.zzanq = nativeAdOptionsParcel;
        this.zzanr = zzfj();
        this.zzans = com_google_android_gms_ads_internal_client_zzy;
        this.zzamb = com_google_android_gms_ads_internal_zzd;
    }

    private List<String> zzfj() {
        List<String> arrayList = new ArrayList();
        if (this.zzann != null) {
            arrayList.add(AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
        if (this.zzanm != null) {
            arrayList.add("2");
        }
        if (this.zzano.size() > 0) {
            arrayList.add("3");
        }
        return arrayList;
    }

    @Nullable
    public String getMediationAdapterClassName() {
        synchronized (this.zzako) {
            if (this.zzanv != null) {
                zzq com_google_android_gms_ads_internal_zzq = (zzq) this.zzanv.get();
                String mediationAdapterClassName = com_google_android_gms_ads_internal_zzq != null ? com_google_android_gms_ads_internal_zzq.getMediationAdapterClassName() : null;
                return mediationAdapterClassName;
            }
            return null;
        }
    }

    public boolean isLoading() {
        synchronized (this.zzako) {
            if (this.zzanv != null) {
                zzq com_google_android_gms_ads_internal_zzq = (zzq) this.zzanv.get();
                boolean isLoading = com_google_android_gms_ads_internal_zzq != null ? com_google_android_gms_ads_internal_zzq.isLoading() : false;
                return isLoading;
            }
            return false;
        }
    }

    protected void runOnUiThread(Runnable runnable) {
        zzlb.zzcvl.post(runnable);
    }

    public void zzf(AdRequestParcel adRequestParcel) {
        runOnUiThread(new C11241(this, adRequestParcel));
    }

    protected zzq zzfk() {
        return new zzq(this.mContext, this.zzamb, AdSizeParcel.zzj(this.mContext), this.zzant, this.zzamf, this.zzanu);
    }
}
