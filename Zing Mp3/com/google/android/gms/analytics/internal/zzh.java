package com.google.android.gms.analytics.internal;

import com.google.android.gms.common.internal.zzaa;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class zzh {
    private final String cj;
    private final long dj;
    private final String dk;
    private final boolean dl;
    private long dm;
    private final Map<String, String> zzbly;

    public zzh(long j, String str, String str2, boolean z, long j2, Map<String, String> map) {
        zzaa.zzib(str);
        zzaa.zzib(str2);
        this.dj = j;
        this.cj = str;
        this.dk = str2;
        this.dl = z;
        this.dm = j2;
        if (map != null) {
            this.zzbly = new HashMap(map);
        } else {
            this.zzbly = Collections.emptyMap();
        }
    }

    public long zzacr() {
        return this.dj;
    }

    public String zzacs() {
        return this.dk;
    }

    public boolean zzact() {
        return this.dl;
    }

    public long zzacu() {
        return this.dm;
    }

    public Map<String, String> zzmc() {
        return this.zzbly;
    }

    public void zzr(long j) {
        this.dm = j;
    }

    public String zzze() {
        return this.cj;
    }
}
