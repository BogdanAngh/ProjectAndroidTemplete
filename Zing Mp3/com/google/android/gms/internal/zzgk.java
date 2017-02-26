package com.google.android.gms.internal;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONObject;

@zzji
public class zzgk implements zzgj {
    private final zzgi zzbuq;
    private final HashSet<SimpleEntry<String, zzfe>> zzbur;

    public zzgk(zzgi com_google_android_gms_internal_zzgi) {
        this.zzbuq = com_google_android_gms_internal_zzgi;
        this.zzbur = new HashSet();
    }

    public void zza(String str, zzfe com_google_android_gms_internal_zzfe) {
        this.zzbuq.zza(str, com_google_android_gms_internal_zzfe);
        this.zzbur.add(new SimpleEntry(str, com_google_android_gms_internal_zzfe));
    }

    public void zza(String str, JSONObject jSONObject) {
        this.zzbuq.zza(str, jSONObject);
    }

    public void zzb(String str, zzfe com_google_android_gms_internal_zzfe) {
        this.zzbuq.zzb(str, com_google_android_gms_internal_zzfe);
        this.zzbur.remove(new SimpleEntry(str, com_google_android_gms_internal_zzfe));
    }

    public void zzb(String str, JSONObject jSONObject) {
        this.zzbuq.zzb(str, jSONObject);
    }

    public void zzi(String str, String str2) {
        this.zzbuq.zzi(str, str2);
    }

    public void zzod() {
        Iterator it = this.zzbur.iterator();
        while (it.hasNext()) {
            SimpleEntry simpleEntry = (SimpleEntry) it.next();
            String str = "Unregistering eventhandler: ";
            String valueOf = String.valueOf(((zzfe) simpleEntry.getValue()).toString());
            zzkx.m1697v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            this.zzbuq.zzb((String) simpleEntry.getKey(), (zzfe) simpleEntry.getValue());
        }
        this.zzbur.clear();
    }
}
