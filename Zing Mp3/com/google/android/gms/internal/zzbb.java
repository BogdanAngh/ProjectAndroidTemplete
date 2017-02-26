package com.google.android.gms.internal;

import java.util.HashMap;

public class zzbb extends zzak<Integer, Long> {
    public Long zzahq;
    public Long zzahr;

    public zzbb(String str) {
        zzk(str);
    }

    protected HashMap<Integer, Long> zzat() {
        HashMap<Integer, Long> hashMap = new HashMap();
        hashMap.put(Integer.valueOf(0), this.zzahq);
        hashMap.put(Integer.valueOf(1), this.zzahr);
        return hashMap;
    }

    protected void zzk(String str) {
        HashMap zzl = zzak.zzl(str);
        if (zzl != null) {
            this.zzahq = (Long) zzl.get(Integer.valueOf(0));
            this.zzahr = (Long) zzl.get(Integer.valueOf(1));
        }
    }
}
