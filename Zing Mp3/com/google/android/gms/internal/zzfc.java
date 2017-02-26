package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.util.Map;

@zzji
public final class zzfc implements zzfe {
    private void zzc(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get("label");
        String str2 = (String) map.get("start_label");
        String str3 = (String) map.get("timestamp");
        if (TextUtils.isEmpty(str)) {
            zzb.zzdi("No label given for CSI tick.");
        } else if (TextUtils.isEmpty(str3)) {
            zzb.zzdi("No timestamp given for CSI tick.");
        } else {
            try {
                long zzd = zzd(Long.parseLong(str3));
                if (TextUtils.isEmpty(str2)) {
                    str2 = "native:view_load";
                }
                com_google_android_gms_internal_zzmd.zzxm().zza(str, str2, zzd);
            } catch (Throwable e) {
                zzb.zzc("Malformed timestamp for CSI tick.", e);
            }
        }
    }

    private long zzd(long j) {
        return (j - zzu.zzgs().currentTimeMillis()) + zzu.zzgs().elapsedRealtime();
    }

    private void zzd(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get("value");
        if (TextUtils.isEmpty(str)) {
            zzb.zzdi("No value given for CSI experiment.");
            return;
        }
        zzdz zzly = com_google_android_gms_internal_zzmd.zzxm().zzly();
        if (zzly == null) {
            zzb.zzdi("No ticker for WebView, dropping experiment ID.");
        } else {
            zzly.zzg("e", str);
        }
    }

    private void zze(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get(ShareConstants.WEB_DIALOG_PARAM_NAME);
        String str2 = (String) map.get("value");
        if (TextUtils.isEmpty(str2)) {
            zzb.zzdi("No value given for CSI extra.");
        } else if (TextUtils.isEmpty(str)) {
            zzb.zzdi("No name given for CSI extra.");
        } else {
            zzdz zzly = com_google_android_gms_internal_zzmd.zzxm().zzly();
            if (zzly == null) {
                zzb.zzdi("No ticker for WebView, dropping extra parameter.");
            } else {
                zzly.zzg(str, str2);
            }
        }
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get(NativeProtocol.WEB_DIALOG_ACTION);
        if ("tick".equals(str)) {
            zzc(com_google_android_gms_internal_zzmd, map);
        } else if ("experiment".equals(str)) {
            zzd(com_google_android_gms_internal_zzmd, map);
        } else if ("extra".equals(str)) {
            zze(com_google_android_gms_internal_zzmd, map);
        }
    }
}
