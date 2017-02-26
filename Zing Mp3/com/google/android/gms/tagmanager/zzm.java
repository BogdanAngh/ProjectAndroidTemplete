package com.google.android.gms.tagmanager;

import android.os.Build.VERSION;

class zzm<K, V> {
    final zza<K, V> aDW;

    public interface zza<K, V> {
        int sizeOf(K k, V v);
    }

    /* renamed from: com.google.android.gms.tagmanager.zzm.1 */
    class C15481 implements zza<K, V> {
        final /* synthetic */ zzm aDX;

        C15481(zzm com_google_android_gms_tagmanager_zzm) {
            this.aDX = com_google_android_gms_tagmanager_zzm;
        }

        public int sizeOf(K k, V v) {
            return 1;
        }
    }

    public zzm() {
        this.aDW = new C15481(this);
    }

    public zzl<K, V> zza(int i, zza<K, V> com_google_android_gms_tagmanager_zzm_zza_K__V) {
        if (i > 0) {
            return zzazh() < 12 ? new zzde(i, com_google_android_gms_tagmanager_zzm_zza_K__V) : new zzbi(i, com_google_android_gms_tagmanager_zzm_zza_K__V);
        } else {
            throw new IllegalArgumentException("maxSize <= 0");
        }
    }

    int zzazh() {
        return VERSION.SDK_INT;
    }
}
