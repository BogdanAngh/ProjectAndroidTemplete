package com.google.android.gms.internal;

import java.util.HashMap;

public class zzba extends zzak<Integer, Object> {
    public Long zzahn;
    public Boolean zzaho;
    public Boolean zzahp;

    public zzba(String str) {
        zzk(str);
    }

    protected HashMap<Integer, Object> zzat() {
        HashMap<Integer, Object> hashMap = new HashMap();
        hashMap.put(Integer.valueOf(0), this.zzahn);
        hashMap.put(Integer.valueOf(1), this.zzaho);
        hashMap.put(Integer.valueOf(2), this.zzahp);
        return hashMap;
    }

    protected void zzk(String str) {
        HashMap zzl = zzak.zzl(str);
        if (zzl != null) {
            this.zzahn = (Long) zzl.get(Integer.valueOf(0));
            this.zzaho = (Boolean) zzl.get(Integer.valueOf(1));
            this.zzahp = (Boolean) zzl.get(Integer.valueOf(2));
        }
    }
}
