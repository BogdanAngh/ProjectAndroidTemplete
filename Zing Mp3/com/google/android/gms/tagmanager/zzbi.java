package com.google.android.gms.tagmanager;

import android.annotation.TargetApi;
import android.util.LruCache;
import com.google.android.gms.tagmanager.zzm.zza;

@TargetApi(12)
class zzbi<K, V> implements zzl<K, V> {
    private LruCache<K, V> aFK;

    /* renamed from: com.google.android.gms.tagmanager.zzbi.1 */
    class C15331 extends LruCache<K, V> {
        final /* synthetic */ zza aFL;
        final /* synthetic */ zzbi aFM;

        C15331(zzbi com_google_android_gms_tagmanager_zzbi, int i, zza com_google_android_gms_tagmanager_zzm_zza) {
            this.aFM = com_google_android_gms_tagmanager_zzbi;
            this.aFL = com_google_android_gms_tagmanager_zzm_zza;
            super(i);
        }

        protected int sizeOf(K k, V v) {
            return this.aFL.sizeOf(k, v);
        }
    }

    zzbi(int i, zza<K, V> com_google_android_gms_tagmanager_zzm_zza_K__V) {
        this.aFK = new C15331(this, i, com_google_android_gms_tagmanager_zzm_zza_K__V);
    }

    public V get(K k) {
        return this.aFK.get(k);
    }

    public void zzi(K k, V v) {
        this.aFK.put(k, v);
    }
}
