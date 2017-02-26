package com.google.android.gms.internal;

import android.content.Context;
import com.facebook.ads.AudienceNetworkActivity;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzme.zza;

@zzji
public class zziv extends zziq implements zza {
    zziv(Context context, zzko.zza com_google_android_gms_internal_zzko_zza, zzmd com_google_android_gms_internal_zzmd, zziu.zza com_google_android_gms_internal_zziu_zza) {
        super(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zziu_zza);
    }

    protected void zzrx() {
        if (this.zzcgg.errorCode == -2) {
            this.zzbnz.zzxc().zza((zza) this);
            zzse();
            zzb.zzdg("Loading HTML in WebView.");
            this.zzbnz.loadDataWithBaseURL(this.zzcgg.zzcbo, this.zzcgg.body, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME, null);
        }
    }

    protected void zzse() {
    }
}
