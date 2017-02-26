package com.google.android.gms.internal;

import java.util.HashMap;

public class zzam extends zzak<Integer, Object> {
    public String zzcn;
    public String zzcp;
    public String zzcq;
    public String zzcr;
    public long zzyi;

    public zzam() {
        this.zzcn = "E";
        this.zzyi = -1;
        this.zzcp = "E";
        this.zzcq = "E";
        this.zzcr = "E";
    }

    public zzam(String str) {
        this();
        zzk(str);
    }

    protected HashMap<Integer, Object> zzat() {
        HashMap<Integer, Object> hashMap = new HashMap();
        hashMap.put(Integer.valueOf(0), this.zzcn);
        hashMap.put(Integer.valueOf(4), this.zzcr);
        hashMap.put(Integer.valueOf(3), this.zzcq);
        hashMap.put(Integer.valueOf(2), this.zzcp);
        hashMap.put(Integer.valueOf(1), Long.valueOf(this.zzyi));
        return hashMap;
    }

    protected void zzk(String str) {
        HashMap zzl = zzak.zzl(str);
        if (zzl != null) {
            this.zzcn = zzl.get(Integer.valueOf(0)) == null ? "E" : (String) zzl.get(Integer.valueOf(0));
            this.zzyi = zzl.get(Integer.valueOf(1)) == null ? -1 : ((Long) zzl.get(Integer.valueOf(1))).longValue();
            this.zzcp = zzl.get(Integer.valueOf(2)) == null ? "E" : (String) zzl.get(Integer.valueOf(2));
            this.zzcq = zzl.get(Integer.valueOf(3)) == null ? "E" : (String) zzl.get(Integer.valueOf(3));
            this.zzcr = zzl.get(Integer.valueOf(4)) == null ? "E" : (String) zzl.get(Integer.valueOf(4));
        }
    }
}
