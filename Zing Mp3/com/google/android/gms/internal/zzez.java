package com.google.android.gms.internal;

import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;

@zzji
public final class zzez implements zzfe {
    private final zzfa zzbpi;

    public zzez(zzfa com_google_android_gms_internal_zzfa) {
        this.zzbpi = com_google_android_gms_internal_zzfa;
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get(ShareConstants.WEB_DIALOG_PARAM_NAME);
        if (str == null) {
            zzb.zzdi("App event with no name parameter.");
        } else {
            this.zzbpi.onAppEvent(str, (String) map.get("info"));
        }
    }
}
