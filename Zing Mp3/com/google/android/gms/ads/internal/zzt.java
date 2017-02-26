package com.google.android.gms.ads.internal;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.actions.SearchIntents;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.VideoOptionsParcel;
import com.google.android.gms.ads.internal.client.zzab;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.client.zzp;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.client.zzw;
import com.google.android.gms.ads.internal.client.zzy;
import com.google.android.gms.ads.internal.reward.client.zzd;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzcf;
import com.google.android.gms.internal.zzcg;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzig;
import com.google.android.gms.internal.zzik;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzla;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzji
public class zzt extends com.google.android.gms.ads.internal.client.zzu.zza {
    private final Context mContext;
    @Nullable
    private zzq zzanl;
    private final VersionInfoParcel zzanu;
    private final AdSizeParcel zzapp;
    private final Future<zzcf> zzapq;
    private final zzb zzapr;
    @Nullable
    private WebView zzaps;
    @Nullable
    private zzcf zzapt;
    private AsyncTask<Void, Void, String> zzapu;

    /* renamed from: com.google.android.gms.ads.internal.zzt.1 */
    class C11371 extends WebViewClient {
        final /* synthetic */ zzt zzapv;

        C11371(zzt com_google_android_gms_ads_internal_zzt) {
            this.zzapv = com_google_android_gms_ads_internal_zzt;
        }

        public void onReceivedError(WebView webView, WebResourceRequest webResourceRequest, WebResourceError webResourceError) {
            if (this.zzapv.zzanl != null) {
                try {
                    this.zzapv.zzanl.onAdFailedToLoad(0);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not call AdListener.onAdFailedToLoad().", e);
                }
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            if (str.startsWith(this.zzapv.zzgb())) {
                return false;
            }
            if (str.startsWith((String) zzdr.zzbka.get())) {
                if (this.zzapv.zzanl != null) {
                    try {
                        this.zzapv.zzanl.onAdFailedToLoad(3);
                    } catch (Throwable e) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not call AdListener.onAdFailedToLoad().", e);
                    }
                }
                this.zzapv.zzj(0);
                return true;
            } else if (str.startsWith((String) zzdr.zzbkb.get())) {
                if (this.zzapv.zzanl != null) {
                    try {
                        this.zzapv.zzanl.onAdFailedToLoad(0);
                    } catch (Throwable e2) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not call AdListener.onAdFailedToLoad().", e2);
                    }
                }
                this.zzapv.zzj(0);
                return true;
            } else if (str.startsWith((String) zzdr.zzbkc.get())) {
                if (this.zzapv.zzanl != null) {
                    try {
                        this.zzapv.zzanl.onAdLoaded();
                    } catch (Throwable e22) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not call AdListener.onAdLoaded().", e22);
                    }
                }
                this.zzapv.zzj(this.zzapv.zzab(str));
                return true;
            } else if (str.startsWith("gmsg://")) {
                return true;
            } else {
                if (this.zzapv.zzanl != null) {
                    try {
                        this.zzapv.zzanl.onAdLeftApplication();
                    } catch (Throwable e222) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not call AdListener.onAdLeftApplication().", e222);
                    }
                }
                this.zzapv.zzad(this.zzapv.zzac(str));
                return true;
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzt.2 */
    class C11382 implements OnTouchListener {
        final /* synthetic */ zzt zzapv;

        C11382(zzt com_google_android_gms_ads_internal_zzt) {
            this.zzapv = com_google_android_gms_ads_internal_zzt;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (this.zzapv.zzapt != null) {
                try {
                    this.zzapv.zzapt.zza(motionEvent);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzc("Unable to process ad data", e);
                }
            }
            return false;
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzt.3 */
    class C11393 implements Callable<zzcf> {
        final /* synthetic */ zzt zzapv;

        C11393(zzt com_google_android_gms_ads_internal_zzt) {
            this.zzapv = com_google_android_gms_ads_internal_zzt;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzgd();
        }

        public zzcf zzgd() throws Exception {
            return new zzcf(this.zzapv.zzanu.zzda, this.zzapv.mContext, false);
        }
    }

    private class zza extends AsyncTask<Void, Void, String> {
        final /* synthetic */ zzt zzapv;

        private zza(zzt com_google_android_gms_ads_internal_zzt) {
            this.zzapv = com_google_android_gms_ads_internal_zzt;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return zza((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            zzae((String) obj);
        }

        protected String zza(Void... voidArr) {
            Throwable e;
            try {
                this.zzapv.zzapt = (zzcf) this.zzapv.zzapq.get(((Long) zzdr.zzbkf.get()).longValue(), TimeUnit.MILLISECONDS);
            } catch (InterruptedException e2) {
                e = e2;
                com.google.android.gms.ads.internal.util.client.zzb.zzc("Failed to load ad data", e);
            } catch (ExecutionException e3) {
                e = e3;
                com.google.android.gms.ads.internal.util.client.zzb.zzc("Failed to load ad data", e);
            } catch (TimeoutException e4) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("Timed out waiting for ad data");
            }
            return this.zzapv.zzga();
        }

        protected void zzae(String str) {
            if (this.zzapv.zzaps != null && str != null) {
                this.zzapv.zzaps.loadUrl(str);
            }
        }
    }

    private static class zzb {
        private final String zzapw;
        private final Map<String, String> zzapx;
        private String zzapy;
        private String zzapz;

        public zzb(String str) {
            this.zzapw = str;
            this.zzapx = new TreeMap();
        }

        public String getQuery() {
            return this.zzapy;
        }

        public String zzge() {
            return this.zzapz;
        }

        public String zzgf() {
            return this.zzapw;
        }

        public Map<String, String> zzgg() {
            return this.zzapx;
        }

        public void zzi(AdRequestParcel adRequestParcel) {
            this.zzapy = adRequestParcel.zzays.zzbcj;
            Bundle bundle = adRequestParcel.zzayv != null ? adRequestParcel.zzayv.getBundle(AdMobAdapter.class.getName()) : null;
            if (bundle != null) {
                String str = (String) zzdr.zzbke.get();
                for (String str2 : bundle.keySet()) {
                    if (str.equals(str2)) {
                        this.zzapz = bundle.getString(str2);
                    } else if (str2.startsWith("csa_")) {
                        this.zzapx.put(str2.substring("csa_".length()), bundle.getString(str2));
                    }
                }
            }
        }
    }

    public zzt(Context context, AdSizeParcel adSizeParcel, String str, VersionInfoParcel versionInfoParcel) {
        this.mContext = context;
        this.zzanu = versionInfoParcel;
        this.zzapp = adSizeParcel;
        this.zzaps = new WebView(this.mContext);
        this.zzapq = zzgc();
        this.zzapr = new zzb(str);
        zzfz();
    }

    private String zzac(String str) {
        if (this.zzapt == null) {
            return str;
        }
        Uri parse = Uri.parse(str);
        try {
            parse = this.zzapt.zzd(parse, this.mContext);
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Unable to process ad data", e);
        } catch (Throwable e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Unable to parse ad click url", e2);
        }
        return parse.toString();
    }

    private void zzad(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        this.mContext.startActivity(intent);
    }

    private void zzfz() {
        zzj(0);
        this.zzaps.setVerticalScrollBarEnabled(false);
        this.zzaps.getSettings().setJavaScriptEnabled(true);
        this.zzaps.setWebViewClient(new C11371(this));
        this.zzaps.setOnTouchListener(new C11382(this));
    }

    private Future<zzcf> zzgc() {
        return zzla.zza(new C11393(this));
    }

    public void destroy() throws RemoteException {
        zzaa.zzhs("destroy must be called on the main UI thread.");
        this.zzapu.cancel(true);
        this.zzapq.cancel(true);
        this.zzaps.destroy();
        this.zzaps = null;
    }

    @Nullable
    public String getMediationAdapterClassName() throws RemoteException {
        return null;
    }

    public boolean isLoading() throws RemoteException {
        return false;
    }

    public boolean isReady() throws RemoteException {
        return false;
    }

    public void pause() throws RemoteException {
        zzaa.zzhs("pause must be called on the main UI thread.");
    }

    public void resume() throws RemoteException {
        zzaa.zzhs("resume must be called on the main UI thread.");
    }

    public void setManualImpressionsEnabled(boolean z) throws RemoteException {
    }

    public void setUserId(String str) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public void showInterstitial() throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public void stopLoading() throws RemoteException {
    }

    public void zza(AdSizeParcel adSizeParcel) throws RemoteException {
        throw new IllegalStateException("AdSize must be set before initialization");
    }

    public void zza(VideoOptionsParcel videoOptionsParcel) {
        throw new IllegalStateException("Unused method");
    }

    public void zza(zzp com_google_android_gms_ads_internal_client_zzp) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public void zza(zzq com_google_android_gms_ads_internal_client_zzq) throws RemoteException {
        this.zzanl = com_google_android_gms_ads_internal_client_zzq;
    }

    public void zza(zzw com_google_android_gms_ads_internal_client_zzw) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public void zza(zzy com_google_android_gms_ads_internal_client_zzy) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public void zza(zzd com_google_android_gms_ads_internal_reward_client_zzd) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public void zza(zzed com_google_android_gms_internal_zzed) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public void zza(zzig com_google_android_gms_internal_zzig) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    public void zza(zzik com_google_android_gms_internal_zzik, String str) throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    int zzab(String str) {
        int i = 0;
        Object queryParameter = Uri.parse(str).getQueryParameter("height");
        if (!TextUtils.isEmpty(queryParameter)) {
            try {
                i = zzm.zzkr().zzb(this.mContext, Integer.parseInt(queryParameter));
            } catch (NumberFormatException e) {
            }
        }
        return i;
    }

    public boolean zzb(AdRequestParcel adRequestParcel) throws RemoteException {
        zzaa.zzb(this.zzaps, (Object) "This Search Ad has already been torn down");
        this.zzapr.zzi(adRequestParcel);
        this.zzapu = new zza().execute(new Void[0]);
        return true;
    }

    public com.google.android.gms.dynamic.zzd zzef() throws RemoteException {
        zzaa.zzhs("getAdFrame must be called on the main UI thread.");
        return zze.zzac(this.zzaps);
    }

    public AdSizeParcel zzeg() throws RemoteException {
        return this.zzapp;
    }

    public void zzei() throws RemoteException {
        throw new IllegalStateException("Unused method");
    }

    @Nullable
    public zzab zzej() {
        return null;
    }

    String zzga() {
        String valueOf;
        Uri zzc;
        Throwable e;
        String valueOf2;
        Builder builder = new Builder();
        builder.scheme("https://").appendEncodedPath((String) zzdr.zzbkd.get());
        builder.appendQueryParameter(SearchIntents.EXTRA_QUERY, this.zzapr.getQuery());
        builder.appendQueryParameter("pubId", this.zzapr.zzgf());
        Map zzgg = this.zzapr.zzgg();
        for (String valueOf3 : zzgg.keySet()) {
            builder.appendQueryParameter(valueOf3, (String) zzgg.get(valueOf3));
        }
        Uri build = builder.build();
        if (this.zzapt != null) {
            try {
                zzc = this.zzapt.zzc(build, this.mContext);
            } catch (zzcg e2) {
                e = e2;
                com.google.android.gms.ads.internal.util.client.zzb.zzc("Unable to process ad data", e);
                zzc = build;
                valueOf2 = String.valueOf(zzgb());
                valueOf3 = String.valueOf(zzc.getEncodedQuery());
                return new StringBuilder((String.valueOf(valueOf2).length() + 1) + String.valueOf(valueOf3).length()).append(valueOf2).append("#").append(valueOf3).toString();
            } catch (RemoteException e3) {
                e = e3;
                com.google.android.gms.ads.internal.util.client.zzb.zzc("Unable to process ad data", e);
                zzc = build;
                valueOf2 = String.valueOf(zzgb());
                valueOf3 = String.valueOf(zzc.getEncodedQuery());
                return new StringBuilder((String.valueOf(valueOf2).length() + 1) + String.valueOf(valueOf3).length()).append(valueOf2).append("#").append(valueOf3).toString();
            }
            valueOf2 = String.valueOf(zzgb());
            valueOf3 = String.valueOf(zzc.getEncodedQuery());
            return new StringBuilder((String.valueOf(valueOf2).length() + 1) + String.valueOf(valueOf3).length()).append(valueOf2).append("#").append(valueOf3).toString();
        }
        zzc = build;
        valueOf2 = String.valueOf(zzgb());
        valueOf3 = String.valueOf(zzc.getEncodedQuery());
        return new StringBuilder((String.valueOf(valueOf2).length() + 1) + String.valueOf(valueOf3).length()).append(valueOf2).append("#").append(valueOf3).toString();
    }

    String zzgb() {
        String str;
        CharSequence zzge = this.zzapr.zzge();
        if (TextUtils.isEmpty(zzge)) {
            str = "www.google.com";
        } else {
            CharSequence charSequence = zzge;
        }
        String valueOf = String.valueOf("https://");
        String str2 = (String) zzdr.zzbkd.get();
        return new StringBuilder(((String.valueOf(valueOf).length() + 0) + String.valueOf(str).length()) + String.valueOf(str2).length()).append(valueOf).append(str).append(str2).toString();
    }

    void zzj(int i) {
        if (this.zzaps != null) {
            this.zzaps.setLayoutParams(new LayoutParams(-1, i));
        }
    }
}
