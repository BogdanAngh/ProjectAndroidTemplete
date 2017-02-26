package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzaj.zza;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

abstract class zzam {
    private final Set<String> aFo;
    private final String aFp;

    public zzam(String str, String... strArr) {
        this.aFp = str;
        this.aFo = new HashSet(strArr.length);
        for (Object add : strArr) {
            this.aFo.add(add);
        }
    }

    public abstract zza zzay(Map<String, zza> map);

    public abstract boolean zzcdu();

    public String zzcfg() {
        return this.aFp;
    }

    public Set<String> zzcfh() {
        return this.aFo;
    }

    boolean zzf(Set<String> set) {
        return set.containsAll(this.aFo);
    }
}
