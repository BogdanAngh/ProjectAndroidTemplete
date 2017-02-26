package com.google.android.gms.internal;

import java.util.HashMap;

public class zzbd extends zzak<Integer, Long> {
    public Long zzain;
    public Long zzaio;
    public Long zzaip;
    public Long zzaiq;
    public Long zzair;
    public Long zzais;
    public Long zzait;
    public Long zzfd;
    public Long zzff;
    public Long zzfj;
    public Long zzfk;

    public zzbd(String str) {
        zzk(str);
    }

    protected HashMap<Integer, Long> zzat() {
        HashMap<Integer, Long> hashMap = new HashMap();
        hashMap.put(Integer.valueOf(0), this.zzain);
        hashMap.put(Integer.valueOf(1), this.zzaio);
        hashMap.put(Integer.valueOf(2), this.zzaip);
        hashMap.put(Integer.valueOf(3), this.zzff);
        hashMap.put(Integer.valueOf(4), this.zzfd);
        hashMap.put(Integer.valueOf(5), this.zzaiq);
        hashMap.put(Integer.valueOf(6), this.zzair);
        hashMap.put(Integer.valueOf(7), this.zzais);
        hashMap.put(Integer.valueOf(8), this.zzfk);
        hashMap.put(Integer.valueOf(9), this.zzfj);
        hashMap.put(Integer.valueOf(10), this.zzait);
        return hashMap;
    }

    protected void zzk(String str) {
        HashMap zzl = zzak.zzl(str);
        if (zzl != null) {
            this.zzain = (Long) zzl.get(Integer.valueOf(0));
            this.zzaio = (Long) zzl.get(Integer.valueOf(1));
            this.zzaip = (Long) zzl.get(Integer.valueOf(2));
            this.zzff = (Long) zzl.get(Integer.valueOf(3));
            this.zzfd = (Long) zzl.get(Integer.valueOf(4));
            this.zzaiq = (Long) zzl.get(Integer.valueOf(5));
            this.zzair = (Long) zzl.get(Integer.valueOf(6));
            this.zzais = (Long) zzl.get(Integer.valueOf(7));
            this.zzfk = (Long) zzl.get(Integer.valueOf(8));
            this.zzfj = (Long) zzl.get(Integer.valueOf(9));
            this.zzait = (Long) zzl.get(Integer.valueOf(10));
        }
    }
}
