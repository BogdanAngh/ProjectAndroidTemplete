package com.google.android.gms.tagmanager;

import android.util.LruCache;
import com.google.android.gms.tagmanager.zzm.zza;

class zzba<K, V> implements zzl<K, V> {
    private LruCache<K, V> zzaMe;

    /* renamed from: com.google.android.gms.tagmanager.zzba.1 */
    class C02931 extends LruCache<K, V> {
        final /* synthetic */ zza zzaMf;
        final /* synthetic */ zzba zzaMg;

        C02931(zzba com_google_android_gms_tagmanager_zzba, int i, zza com_google_android_gms_tagmanager_zzm_zza) {
            this.zzaMg = com_google_android_gms_tagmanager_zzba;
            this.zzaMf = com_google_android_gms_tagmanager_zzm_zza;
            super(i);
        }

        protected int sizeOf(K key, V value) {
            return this.zzaMf.sizeOf(key, value);
        }
    }

    zzba(int i, zza<K, V> com_google_android_gms_tagmanager_zzm_zza_K__V) {
        this.zzaMe = new C02931(this, i, com_google_android_gms_tagmanager_zzm_zza_K__V);
    }

    public V get(K key) {
        return this.zzaMe.get(key);
    }

    public void zzf(K k, V v) {
        this.zzaMe.put(k, v);
    }
}
