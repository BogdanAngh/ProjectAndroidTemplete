package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.support.annotation.Nullable;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzji
@TargetApi(11)
public class zzmn extends zzme {
    public zzmn(zzmd com_google_android_gms_internal_zzmd, boolean z) {
        super(com_google_android_gms_internal_zzmd, z);
    }

    protected WebResourceResponse zza(WebView webView, String str, @Nullable Map<String, String> map) {
        Exception e;
        String valueOf;
        if (webView instanceof zzmd) {
            zzmd com_google_android_gms_internal_zzmd = (zzmd) webView;
            if (this.zzczo != null) {
                this.zzczo.zzb(str, map);
            }
            if (!"mraid.js".equalsIgnoreCase(new File(str).getName())) {
                return super.shouldInterceptRequest(webView, str);
            }
            if (com_google_android_gms_internal_zzmd.zzxc() != null) {
                com_google_android_gms_internal_zzmd.zzxc().zzpo();
            }
            String str2 = com_google_android_gms_internal_zzmd.zzeg().zzazr ? (String) zzdr.zzbep.get() : com_google_android_gms_internal_zzmd.zzxg() ? (String) zzdr.zzbeo.get() : (String) zzdr.zzben.get();
            try {
                return zzf(com_google_android_gms_internal_zzmd.getContext(), com_google_android_gms_internal_zzmd.zzxf().zzda, str2);
            } catch (IOException e2) {
                e = e2;
                str2 = "Could not fetch MRAID JS. ";
                valueOf = String.valueOf(e.getMessage());
                zzb.zzdi(valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                return null;
            } catch (ExecutionException e3) {
                e = e3;
                str2 = "Could not fetch MRAID JS. ";
                valueOf = String.valueOf(e.getMessage());
                if (valueOf.length() == 0) {
                }
                zzb.zzdi(valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                return null;
            } catch (InterruptedException e4) {
                e = e4;
                str2 = "Could not fetch MRAID JS. ";
                valueOf = String.valueOf(e.getMessage());
                if (valueOf.length() == 0) {
                }
                zzb.zzdi(valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                return null;
            } catch (TimeoutException e5) {
                e = e5;
                str2 = "Could not fetch MRAID JS. ";
                valueOf = String.valueOf(e.getMessage());
                if (valueOf.length() == 0) {
                }
                zzb.zzdi(valueOf.length() == 0 ? str2.concat(valueOf) : new String(str2));
                return null;
            }
        }
        zzb.zzdi("Tried to intercept request from a WebView that wasn't an AdWebView.");
        return null;
    }

    protected WebResourceResponse zzf(Context context, String str, String str2) throws IOException, ExecutionException, InterruptedException, TimeoutException {
        Map hashMap = new HashMap();
        hashMap.put("User-Agent", zzu.zzgm().zzh(context, str));
        hashMap.put("Cache-Control", "max-stale=3600");
        String str3 = (String) new zzli(context).zzd(str2, hashMap).get(60, TimeUnit.SECONDS);
        return str3 == null ? null : new WebResourceResponse("application/javascript", C0989C.UTF8_NAME, new ByteArrayInputStream(str3.getBytes(C0989C.UTF8_NAME)));
    }
}
