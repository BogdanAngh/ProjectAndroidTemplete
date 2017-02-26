package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.zzg;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzcu.zza;
import java.util.Map;
import org.json.JSONObject;

@zzji
class zzmg extends FrameLayout implements zzmd {
    private static final int zzcak;
    private final zzmd zzczv;
    private final zzmc zzczw;

    static {
        zzcak = Color.argb(0, 0, 0, 0);
    }

    public zzmg(zzmd com_google_android_gms_internal_zzmd) {
        super(com_google_android_gms_internal_zzmd.getContext());
        this.zzczv = com_google_android_gms_internal_zzmd;
        this.zzczw = new zzmc(com_google_android_gms_internal_zzmd.zzwz(), this, this);
        zzme zzxc = this.zzczv.zzxc();
        if (zzxc != null) {
            zzxc.zzo(this);
        }
        addView(this.zzczv.getView());
    }

    public void destroy() {
        this.zzczv.destroy();
    }

    public String getRequestId() {
        return this.zzczv.getRequestId();
    }

    public int getRequestedOrientation() {
        return this.zzczv.getRequestedOrientation();
    }

    public View getView() {
        return this;
    }

    public WebView getWebView() {
        return this.zzczv.getWebView();
    }

    public boolean isDestroyed() {
        return this.zzczv.isDestroyed();
    }

    public void loadData(String str, String str2, String str3) {
        this.zzczv.loadData(str, str2, str3);
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        this.zzczv.loadDataWithBaseURL(str, str2, str3, str4, str5);
    }

    public void loadUrl(String str) {
        this.zzczv.loadUrl(str);
    }

    public void onPause() {
        this.zzczw.onPause();
        this.zzczv.onPause();
    }

    public void onResume() {
        this.zzczv.onResume();
    }

    public void setContext(Context context) {
        this.zzczv.setContext(context);
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.zzczv.setOnClickListener(onClickListener);
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.zzczv.setOnTouchListener(onTouchListener);
    }

    public void setRequestedOrientation(int i) {
        this.zzczv.setRequestedOrientation(i);
    }

    public void setWebChromeClient(WebChromeClient webChromeClient) {
        this.zzczv.setWebChromeClient(webChromeClient);
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        this.zzczv.setWebViewClient(webViewClient);
    }

    public void stopLoading() {
        this.zzczv.stopLoading();
    }

    public void zza(Context context, AdSizeParcel adSizeParcel, zzdz com_google_android_gms_internal_zzdz) {
        this.zzczw.onDestroy();
        this.zzczv.zza(context, adSizeParcel, com_google_android_gms_internal_zzdz);
    }

    public void zza(AdSizeParcel adSizeParcel) {
        this.zzczv.zza(adSizeParcel);
    }

    public void zza(zza com_google_android_gms_internal_zzcu_zza) {
        this.zzczv.zza(com_google_android_gms_internal_zzcu_zza);
    }

    public void zza(zzmi com_google_android_gms_internal_zzmi) {
        this.zzczv.zza(com_google_android_gms_internal_zzmi);
    }

    public void zza(String str, zzfe com_google_android_gms_internal_zzfe) {
        this.zzczv.zza(str, com_google_android_gms_internal_zzfe);
    }

    public void zza(String str, Map<String, ?> map) {
        this.zzczv.zza(str, (Map) map);
    }

    public void zza(String str, JSONObject jSONObject) {
        this.zzczv.zza(str, jSONObject);
    }

    public void zzak(int i) {
        this.zzczv.zzak(i);
    }

    public void zzak(boolean z) {
        this.zzczv.zzak(z);
    }

    public void zzal(boolean z) {
        this.zzczv.zzal(z);
    }

    public void zzam(boolean z) {
        this.zzczv.zzam(z);
    }

    public void zzan(boolean z) {
        this.zzczv.zzan(z);
    }

    public void zzb(@Nullable zzg com_google_android_gms_ads_internal_formats_zzg) {
        this.zzczv.zzb(com_google_android_gms_ads_internal_formats_zzg);
    }

    public void zzb(zzd com_google_android_gms_ads_internal_overlay_zzd) {
        this.zzczv.zzb(com_google_android_gms_ads_internal_overlay_zzd);
    }

    public void zzb(String str, zzfe com_google_android_gms_internal_zzfe) {
        this.zzczv.zzb(str, com_google_android_gms_internal_zzfe);
    }

    public void zzb(String str, JSONObject jSONObject) {
        this.zzczv.zzb(str, jSONObject);
    }

    public void zzc(zzd com_google_android_gms_ads_internal_overlay_zzd) {
        this.zzczv.zzc(com_google_android_gms_ads_internal_overlay_zzd);
    }

    public void zzdj(String str) {
        this.zzczv.zzdj(str);
    }

    public void zzdk(String str) {
        this.zzczv.zzdk(str);
    }

    public com.google.android.gms.ads.internal.zzd zzec() {
        return this.zzczv.zzec();
    }

    public AdSizeParcel zzeg() {
        return this.zzczv.zzeg();
    }

    public void zzey() {
        this.zzczv.zzey();
    }

    public void zzez() {
        this.zzczv.zzez();
    }

    public void zzi(String str, String str2) {
        this.zzczv.zzi(str, str2);
    }

    public void zzps() {
        this.zzczv.zzps();
    }

    public void zzww() {
        this.zzczv.zzww();
    }

    public void zzwx() {
        this.zzczv.zzwx();
    }

    public Activity zzwy() {
        return this.zzczv.zzwy();
    }

    public Context zzwz() {
        return this.zzczv.zzwz();
    }

    public zzd zzxa() {
        return this.zzczv.zzxa();
    }

    public zzd zzxb() {
        return this.zzczv.zzxb();
    }

    public zzme zzxc() {
        return this.zzczv.zzxc();
    }

    public boolean zzxd() {
        return this.zzczv.zzxd();
    }

    public zzav zzxe() {
        return this.zzczv.zzxe();
    }

    public VersionInfoParcel zzxf() {
        return this.zzczv.zzxf();
    }

    public boolean zzxg() {
        return this.zzczv.zzxg();
    }

    public void zzxh() {
        this.zzczw.onDestroy();
        this.zzczv.zzxh();
    }

    public boolean zzxi() {
        return this.zzczv.zzxi();
    }

    public boolean zzxj() {
        return this.zzczv.zzxj();
    }

    public zzmc zzxk() {
        return this.zzczw;
    }

    public zzdx zzxl() {
        return this.zzczv.zzxl();
    }

    public zzdy zzxm() {
        return this.zzczv.zzxm();
    }

    public zzmi zzxn() {
        return this.zzczv.zzxn();
    }

    public boolean zzxo() {
        return this.zzczv.zzxo();
    }

    public void zzxp() {
        this.zzczv.zzxp();
    }

    public void zzxq() {
        this.zzczv.zzxq();
    }

    public OnClickListener zzxr() {
        return this.zzczv.zzxr();
    }

    @Nullable
    public zzg zzxs() {
        return this.zzczv.zzxs();
    }

    public void zzxt() {
        setBackgroundColor(zzcak);
        this.zzczv.setBackgroundColor(zzcak);
    }
}
