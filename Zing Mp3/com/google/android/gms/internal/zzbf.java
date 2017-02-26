package com.google.android.gms.internal;

import java.util.HashMap;

public class zzbf extends zzak<Integer, Long> {
    public Long zzaiv;

    public zzbf(String str) {
        zzk(str);
    }

    protected HashMap<Integer, Long> zzat() {
        HashMap<Integer, Long> hashMap = new HashMap();
        hashMap.put(Integer.valueOf(0), this.zzaiv);
        return hashMap;
    }

    protected void zzk(String str) {
        HashMap zzl = zzak.zzl(str);
        if (zzl != null) {
            this.zzaiv = (Long) zzl.get(Integer.valueOf(0));
        }
    }
}
