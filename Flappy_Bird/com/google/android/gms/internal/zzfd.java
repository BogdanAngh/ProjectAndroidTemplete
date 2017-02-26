package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@zzgd
public class zzfd implements zzfb {
    private final Context mContext;
    final Set<WebView> zzAt;

    /* renamed from: com.google.android.gms.internal.zzfd.1 */
    class C02281 implements Runnable {
        final /* synthetic */ String zzAu;
        final /* synthetic */ String zzAv;
        final /* synthetic */ zzfd zzAw;

        /* renamed from: com.google.android.gms.internal.zzfd.1.1 */
        class C02271 extends WebViewClient {
            final /* synthetic */ C02281 zzAx;
            final /* synthetic */ WebView zzrG;

            C02271(C02281 c02281, WebView webView) {
                this.zzAx = c02281;
                this.zzrG = webView;
            }

            public void onPageFinished(WebView view, String url) {
                zzb.zzay("Loading assets have finished");
                this.zzAx.zzAw.zzAt.remove(this.zzrG);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                zzb.zzaC("Loading assets have failed.");
                this.zzAx.zzAw.zzAt.remove(this.zzrG);
            }
        }

        C02281(zzfd com_google_android_gms_internal_zzfd, String str, String str2) {
            this.zzAw = com_google_android_gms_internal_zzfd;
            this.zzAu = str;
            this.zzAv = str2;
        }

        public void run() {
            WebView zzeZ = this.zzAw.zzeZ();
            zzeZ.setWebViewClient(new C02271(this, zzeZ));
            this.zzAw.zzAt.add(zzeZ);
            zzeZ.loadDataWithBaseURL(this.zzAu, this.zzAv, "text/html", "UTF-8", null);
            zzb.zzay("Fetching assets finished.");
        }
    }

    public zzfd(Context context) {
        this.zzAt = Collections.synchronizedSet(new HashSet());
        this.mContext = context;
    }

    public void zza(String str, String str2, String str3) {
        zzb.zzay("Fetching assets for the given html");
        zzhl.zzGk.post(new C02281(this, str2, str3));
    }

    public WebView zzeZ() {
        WebView webView = new WebView(this.mContext);
        webView.getSettings().setJavaScriptEnabled(true);
        return webView;
    }
}
