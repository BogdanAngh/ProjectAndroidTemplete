package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.zzg;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzs;
import com.google.android.gms.internal.zzcu.zzb;
import java.util.Map;
import org.json.JSONObject;

@zzji
public interface zzmd extends zzs, zzb, zzgi {
    void destroy();

    Context getContext();

    LayoutParams getLayoutParams();

    void getLocationOnScreen(int[] iArr);

    int getMeasuredHeight();

    int getMeasuredWidth();

    ViewParent getParent();

    String getRequestId();

    int getRequestedOrientation();

    View getView();

    WebView getWebView();

    boolean isDestroyed();

    void loadData(String str, String str2, String str3);

    void loadDataWithBaseURL(String str, String str2, String str3, String str4, @Nullable String str5);

    void loadUrl(String str);

    void measure(int i, int i2);

    void onPause();

    void onResume();

    void setBackgroundColor(int i);

    void setContext(Context context);

    void setOnClickListener(OnClickListener onClickListener);

    void setOnTouchListener(OnTouchListener onTouchListener);

    void setRequestedOrientation(int i);

    void setWebChromeClient(WebChromeClient webChromeClient);

    void setWebViewClient(WebViewClient webViewClient);

    void stopLoading();

    void zza(Context context, AdSizeParcel adSizeParcel, zzdz com_google_android_gms_internal_zzdz);

    void zza(AdSizeParcel adSizeParcel);

    void zza(zzmi com_google_android_gms_internal_zzmi);

    void zza(String str, Map<String, ?> map);

    void zza(String str, JSONObject jSONObject);

    void zzak(int i);

    void zzak(boolean z);

    void zzal(boolean z);

    void zzam(boolean z);

    void zzan(boolean z);

    void zzb(zzg com_google_android_gms_ads_internal_formats_zzg);

    void zzb(zzd com_google_android_gms_ads_internal_overlay_zzd);

    void zzc(zzd com_google_android_gms_ads_internal_overlay_zzd);

    void zzdj(String str);

    void zzdk(String str);

    com.google.android.gms.ads.internal.zzd zzec();

    AdSizeParcel zzeg();

    void zzi(String str, String str2);

    void zzps();

    void zzww();

    void zzwx();

    Activity zzwy();

    Context zzwz();

    zzd zzxa();

    zzd zzxb();

    @Nullable
    zzme zzxc();

    boolean zzxd();

    zzav zzxe();

    VersionInfoParcel zzxf();

    boolean zzxg();

    void zzxh();

    boolean zzxi();

    boolean zzxj();

    @Nullable
    zzmc zzxk();

    @Nullable
    zzdx zzxl();

    zzdy zzxm();

    @Nullable
    zzmi zzxn();

    boolean zzxo();

    void zzxp();

    void zzxq();

    @Nullable
    OnClickListener zzxr();

    zzg zzxs();

    void zzxt();
}
