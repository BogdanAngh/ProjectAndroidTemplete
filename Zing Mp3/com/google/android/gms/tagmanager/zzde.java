package com.google.android.gms.tagmanager;

import com.google.android.gms.tagmanager.zzm.zza;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

class zzde<K, V> implements zzl<K, V> {
    private final Map<K, V> aHA;
    private final int aHB;
    private final zza<K, V> aHC;
    private int aHD;

    zzde(int i, zza<K, V> com_google_android_gms_tagmanager_zzm_zza_K__V) {
        this.aHA = new HashMap();
        this.aHB = i;
        this.aHC = com_google_android_gms_tagmanager_zzm_zza_K__V;
    }

    public synchronized V get(K k) {
        return this.aHA.get(k);
    }

    public synchronized void zzi(K k, V v) {
        if (k == null || v == null) {
            throw new NullPointerException("key == null || value == null");
        }
        this.aHD += this.aHC.sizeOf(k, v);
        if (this.aHD > this.aHB) {
            Iterator it = this.aHA.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry = (Entry) it.next();
                this.aHD -= this.aHC.sizeOf(entry.getKey(), entry.getValue());
                it.remove();
                if (this.aHD <= this.aHB) {
                    break;
                }
            }
        }
        this.aHA.put(k, v);
    }
}
