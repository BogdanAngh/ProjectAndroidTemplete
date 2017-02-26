package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

@zzji
@TargetApi(11)
public class zzml extends zzmn {
    public zzml(zzmd com_google_android_gms_internal_zzmd, boolean z) {
        super(com_google_android_gms_internal_zzmd, z);
    }

    public WebResourceResponse shouldInterceptRequest(WebView webView, String str) {
        return zza(webView, str, null);
    }
}
