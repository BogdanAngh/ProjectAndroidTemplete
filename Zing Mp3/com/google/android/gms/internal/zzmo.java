package com.google.android.gms.internal;

import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzz;
import java.net.URI;
import java.net.URISyntaxException;

@zzji
public class zzmo extends WebViewClient {
    private final String f1563N;
    private boolean f1564O;
    private final zzmd zzbnz;
    private final zzir zzcgu;

    public zzmo(zzir com_google_android_gms_internal_zzir, zzmd com_google_android_gms_internal_zzmd, String str) {
        this.f1563N = zzdq(str);
        this.f1564O = false;
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzcgu = com_google_android_gms_internal_zzir;
    }

    private String zzdq(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                if (str.endsWith("/")) {
                    str = str.substring(0, str.length() - 1);
                }
            } catch (IndexOutOfBoundsException e) {
                zzb.m1695e(e.getMessage());
            }
        }
        return str;
    }

    public void onLoadResource(WebView webView, String str) {
        String str2 = "JavascriptAdWebViewClient::onLoadResource: ";
        String valueOf = String.valueOf(str);
        zzb.zzdg(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        if (!zzdp(str)) {
            this.zzbnz.zzxc().onLoadResource(this.zzbnz.getWebView(), str);
        }
    }

    public void onPageFinished(WebView webView, String str) {
        String str2 = "JavascriptAdWebViewClient::onPageFinished: ";
        String valueOf = String.valueOf(str);
        zzb.zzdg(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        if (!this.f1564O) {
            this.zzcgu.zzsa();
            this.f1564O = true;
        }
    }

    public boolean shouldOverrideUrlLoading(WebView webView, String str) {
        String str2 = "JavascriptAdWebViewClient::shouldOverrideUrlLoading: ";
        String valueOf = String.valueOf(str);
        zzb.zzdg(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        if (!zzdp(str)) {
            return this.zzbnz.zzxc().shouldOverrideUrlLoading(this.zzbnz.getWebView(), str);
        }
        zzb.zzdg("shouldOverrideUrlLoading: received passback url");
        return true;
    }

    protected boolean zzdp(String str) {
        Object zzdq = zzdq(str);
        if (TextUtils.isEmpty(zzdq)) {
            return false;
        }
        try {
            URI uri = new URI(zzdq);
            if ("passback".equals(uri.getScheme())) {
                zzb.zzdg("Passback received");
                this.zzcgu.zzsb();
                return true;
            } else if (TextUtils.isEmpty(this.f1563N)) {
                return false;
            } else {
                URI uri2 = new URI(this.f1563N);
                String host = uri2.getHost();
                String host2 = uri.getHost();
                String path = uri2.getPath();
                String path2 = uri.getPath();
                if (!zzz.equal(host, host2) || !zzz.equal(path, path2)) {
                    return false;
                }
                zzb.zzdg("Passback received");
                this.zzcgu.zzsb();
                return true;
            }
        } catch (URISyntaxException e) {
            zzb.m1695e(e.getMessage());
            return false;
        }
    }
}
