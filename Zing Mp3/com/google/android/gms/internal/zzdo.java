package com.google.android.gms.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@zzji
public class zzdo {
    private final Collection<zzdn> zzbcp;
    private final Collection<zzdn<String>> zzbcq;
    private final Collection<zzdn<String>> zzbcr;

    public zzdo() {
        this.zzbcp = new ArrayList();
        this.zzbcq = new ArrayList();
        this.zzbcr = new ArrayList();
    }

    public void zza(zzdn com_google_android_gms_internal_zzdn) {
        this.zzbcp.add(com_google_android_gms_internal_zzdn);
    }

    public void zzb(zzdn<String> com_google_android_gms_internal_zzdn_java_lang_String) {
        this.zzbcq.add(com_google_android_gms_internal_zzdn_java_lang_String);
    }

    public void zzc(zzdn<String> com_google_android_gms_internal_zzdn_java_lang_String) {
        this.zzbcr.add(com_google_android_gms_internal_zzdn_java_lang_String);
    }

    public List<String> zzlq() {
        List<String> arrayList = new ArrayList();
        for (zzdn com_google_android_gms_internal_zzdn : this.zzbcq) {
            String str = (String) com_google_android_gms_internal_zzdn.get();
            if (str != null) {
                arrayList.add(str);
            }
        }
        return arrayList;
    }

    public List<String> zzlr() {
        List<String> zzlq = zzlq();
        for (zzdn com_google_android_gms_internal_zzdn : this.zzbcr) {
            String str = (String) com_google_android_gms_internal_zzdn.get();
            if (str != null) {
                zzlq.add(str);
            }
        }
        return zzlq;
    }
}
